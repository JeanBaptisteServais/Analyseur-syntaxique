package fr.jbaw.programme2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Main {


	//Recuperate paths
	private static String TEXTTREATPATH = "C://Users//jeanbaptiste//Desktop//MON JAVA//workplace//Programme1//texts//analyse//textsTreat//";
	private static String SYNTAXEPATH 	= "C://Users//jeanbaptiste//Desktop//MON JAVA//workplace//Programme1//texts//analyse//textsWordsPonct//";
	private static String SAVEPATH      = "C://Users//jeanbaptiste//Desktop//";
	
	private static ArrayList<ArrayList<ArrayList<String>>> currentSyntaxeTreat = new ArrayList<ArrayList<ArrayList<String>>>();
	private static ArrayList<ArrayList<String>> 			currentTextTreat    = new ArrayList<ArrayList<String>>();

	private static Map<String, ArrayList<ArrayList<String>>> ou = new HashMap<String, ArrayList<ArrayList<String>>>();
	private static Map<String, ArrayList<String>>  qui          = new HashMap<String, ArrayList<String>>();
	private static Map<String, ArrayList<String>>  quoi         = new HashMap<String, ArrayList<String>>();
	private static Map<Integer, ArrayList<String>> quand        = new HashMap<Integer, ArrayList<String>>();
	private static Map<String, ArrayList<String>>  discours     = new HashMap<String, ArrayList<String>>();
	private static Map<String, ArrayList<String>>  details      = new HashMap<String, ArrayList<String>>();
	
	
	
	
	private static ArrayList<ArrayList<ArrayList<String>>> saveSyntaxe 		    = new ArrayList<ArrayList<ArrayList<String>>>();
	private static ArrayList<ArrayList<String>>            saveText    		    = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>>            saveSchema  		    = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>>            saveCod    		    = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>>            saveVerb  		    = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>>            saveGn    		    = new ArrayList<ArrayList<String>>();

	private static ArrayList<ArrayList<String>>            inSearchingGnPronom  = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>>            allGns               = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>>            utilsInformation     = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>>            saveGardeCmplt       = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>>            prepAdv              = new ArrayList<ArrayList<String>>();
	
	



	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IndexOutOfBoundsException, IOException {
		 
		String    nameText  = "met.txt";

		RecupFile fileText  = new RecupFile(nameText, TEXTTREATPATH, SYNTAXEPATH);
		String[]  pathsText = fileText.getTextPath();
		

		List<String> text 				     = fileText.recuperateFiles(pathsText[0]);
		List<String> textsElementSyntaxe     = fileText.recuperateFiles(pathsText[1]);
		List<ArrayList<String>> syntaxeText  = fileText.recuperateSentenceText(textsElementSyntaxe, ",");
		List<ArrayList<String>> sentenceText = fileText.recuperateSentenceText(text, "[ ,]");


		//Treatement of the lists.
		TreatData treatment = new TreatData();

		//Treatment of splaces.
		treatmentText1(treatment, syntaxeText, sentenceText); 
		
		//Treatment of doublons and verbes words.
		List<ArrayList<ArrayList<String>>> sentenceWordSplit = treatment.treatWordSyntaxeToList("Syntaxe word to list", syntaxeText);
		treatmentText2(treatment, sentenceWordSplit);
		
		//Choice to decomment
		//makeAnalyseSyntaxique(syntaxeText, sentenceWordSplit, sentenceText);
		//makeAnalyseSentence(syntaxeText, sentenceWordSplit, sentenceText);
		//makeAnalyseInterSubject(syntaxeText, sentenceWordSplit, sentenceText);
		
		

	}
	
	

	
	
	
	
	

	private static void makeAnalyseInterSubject(List<ArrayList<String>> syntaxeText,
			List<ArrayList<ArrayList<String>>> sentenceWordSplit, List<ArrayList<String>> sentenceText) throws IOException {
		for (int sentence=31; sentence < syntaxeText.size(); sentence++) {
			
			int sentenceNumber = sentence;

			System.out.println(sentenceNumber);
			
			ChooseWordFunction chooseFunction = new ChooseWordFunction();
			chooseFunction.ChooseWord(sentenceWordSplit, sentenceText, currentSyntaxeTreat, currentTextTreat, 
									  sentenceNumber, saveSyntaxe, saveSchema, "");


			//System.out.println("------------------------------------------------------------------------------ group nominal");
			Map<Integer, String> groupNominalFunctionEndIndex   = new HashMap<Integer, String>();
			Map<Integer, String> groupNominalFunctionBeginIndex = new HashMap<Integer, String>();
			groupNominal         groupNominal = new groupNominal(currentSyntaxeTreat.get(0), currentTextTreat.get(0), 
																 groupNominalFunctionEndIndex, groupNominalFunctionBeginIndex);

			groupNominal.nominalGroup();
	

			//System.out.println("------------------------------------------------------------------------------ groupe verbal");
			Map<Integer, String> groupsVerbalsMap                = new HashMap<Integer, String>();
			Map<Integer, String> groupsVerbalsFunctionEndIndex   = new HashMap<Integer, String>();
			Map<Integer, String> groupsVerbalsFunctionBeginIndex = new HashMap<Integer, String>();
			Map<Integer, String> pronomToGNBeginIndex            = new HashMap<Integer, String>();
	
			groupVerbal groupVerbal = new groupVerbal(currentSyntaxeTreat.get(0), currentTextTreat.get(0),
													  groupsVerbalsMap, pronomToGNBeginIndex, groupsVerbalsFunctionEndIndex,
													  groupsVerbalsFunctionBeginIndex);
			groupVerbal.verbalGroup();
	

	
			//System.out.println("------------------------------------------------------------------------------ groupe adverbial");
			Map<Integer, String> adverbeFunction = new HashMap<Integer, String>();
			groupAdverbial       groupAdverbial  = new groupAdverbial(currentSyntaxeTreat.get(0), currentTextTreat.get(0),
																	  adverbeFunction);
	

			//System.out.println("------------------------------------------------------------------------------ proposition subordonée");
			Map<Integer, String>      propositionRelativeFunction = new HashMap<Integer, String>();
			groupePropositionRelative Relative                    = new groupePropositionRelative(currentSyntaxeTreat.get(0), 
																								  currentTextTreat.get(0), 
																								  propositionRelativeFunction);
			
			
			
			//System.out.println("------------------------------------------------------------------------------groupe prepositionnel");
			Map<Integer, String> prepositionFunction = new HashMap<Integer, String>();
			groupPrepositionel   groupPrepositionel  = new groupPrepositionel(currentSyntaxeTreat.get(0), currentTextTreat.get(0),
													  					      prepositionFunction);
			

			
	
			//System.out.println("------------------------------------------------------------------------------conjonction coordination");
			Map<Integer, String> conjonctionLocalisation  = new HashMap<Integer, String>();
			ConjonctionDeCoordination conjonction         = new ConjonctionDeCoordination(currentSyntaxeTreat.get(0), 
																						  currentTextTreat.get(0),
																						  conjonctionLocalisation);
	
	

	
			//System.out.println("------------------------------------------------------------------------------ dépendances");
			Map<String, String> GROUPEINFORMATIONS  = new HashMap<String, String>();
			ArrayList<String> 	GROUPS 				= new ArrayList<String>();


			System.out.println(currentTextTreat.get(0) + "\n\n\n");
			defineGroups defineGroups = new defineGroups(groupNominalFunctionBeginIndex,
														 groupsVerbalsFunctionBeginIndex,
														 pronomToGNBeginIndex,
														 adverbeFunction,
														 propositionRelativeFunction,
														 prepositionFunction,
														 conjonctionLocalisation,
														 currentSyntaxeTreat.get(0),
														 currentTextTreat.get(0),
														 sentenceText.get(sentenceNumber),
														 conjonction,
														 GROUPEINFORMATIONS,
														 GROUPS, "");
			
			System.out.println(currentTextTreat.get(0));


			System.out.println("------------------------------------------------------------------------------ quiquoiou ?");
			searchWords searchWords = new searchWords(groupNominalFunctionBeginIndex,
													  groupsVerbalsFunctionBeginIndex,
													  pronomToGNBeginIndex,
													  adverbeFunction,
													  propositionRelativeFunction,
													  prepositionFunction,
													  conjonctionLocalisation,
													  currentSyntaxeTreat.get(0),
													  currentTextTreat.get(0),
													  sentenceText.get(sentenceNumber),
													  conjonction,
													  quoi, ou, quand, qui, sentenceNumber,
													  GROUPEINFORMATIONS,
													  GROUPS,
													  saveSyntaxe, saveSchema, saveText,
													  discours, details, saveCod, saveVerb, saveGn, 
													  inSearchingGnPronom, allGns, utilsInformation,
													  saveGardeCmplt, prepAdv);
			
		
			System.out.println(currentTextTreat);

			
			clearNominal(groupNominalFunctionEndIndex, groupNominalFunctionBeginIndex);
			clearVerbal(groupsVerbalsMap, groupsVerbalsFunctionEndIndex, groupsVerbalsFunctionBeginIndex, pronomToGNBeginIndex);
			clearOtherGroup(adverbeFunction, propositionRelativeFunction, prepositionFunction, conjonctionLocalisation);
			clearGroups(GROUPEINFORMATIONS, GROUPS);
			clearSentence(currentSyntaxeTreat, currentTextTreat);

		}
		
	}









	private static void makeAnalyseSentence(List<ArrayList<String>> syntaxeText, List<ArrayList<ArrayList<String>>> sentenceWordSplit, 
			List<ArrayList<String>> sentenceText) throws IOException {
		
	
		for (int sentence=0; sentence < syntaxeText.size(); sentence++) {
			
			int sentenceNumber = sentence;

			System.out.println(sentenceNumber);
			
			ChooseWordFunction chooseFunction = new ChooseWordFunction();
			chooseFunction.ChooseWord(sentenceWordSplit, sentenceText, currentSyntaxeTreat, currentTextTreat, 
									  sentenceNumber, saveSyntaxe, saveSchema, "");


			Map<Integer, String> groupNominalFunctionEndIndex   = new HashMap<Integer, String>();
			Map<Integer, String> groupNominalFunctionBeginIndex = new HashMap<Integer, String>();
			groupNominal         groupNominal = new groupNominal(currentSyntaxeTreat.get(0), currentTextTreat.get(0), 
																 groupNominalFunctionEndIndex, groupNominalFunctionBeginIndex);

			groupNominal.nominalGroup();
	

			//------------------------------------------------------------------------------ groupe verbal
			Map<Integer, String> groupsVerbalsMap                = new HashMap<Integer, String>();
			Map<Integer, String> groupsVerbalsFunctionEndIndex   = new HashMap<Integer, String>();
			Map<Integer, String> groupsVerbalsFunctionBeginIndex = new HashMap<Integer, String>();
			Map<Integer, String> pronomToGNBeginIndex            = new HashMap<Integer, String>();
	
			groupVerbal groupVerbal = new groupVerbal(currentSyntaxeTreat.get(0), currentTextTreat.get(0),
													  groupsVerbalsMap, pronomToGNBeginIndex, groupsVerbalsFunctionEndIndex,
													  groupsVerbalsFunctionBeginIndex);
			groupVerbal.verbalGroup();
	

	
			//------------------------------------------------------------------------------ groupe adverbial
			Map<Integer, String> adverbeFunction = new HashMap<Integer, String>();
			groupAdverbial       groupAdverbial  = new groupAdverbial(currentSyntaxeTreat.get(0), currentTextTreat.get(0),
																	  adverbeFunction);
	

			//------------------------------------------------------------------------------ proposition subordonée
			Map<Integer, String>      propositionRelativeFunction = new HashMap<Integer, String>();
			groupePropositionRelative Relative                    = new groupePropositionRelative(currentSyntaxeTreat.get(0), 
																								  currentTextTreat.get(0), 
																								  propositionRelativeFunction);
			
			
			
			//------------------------------------------------------------------------------groupe prepositionnel
			Map<Integer, String> prepositionFunction = new HashMap<Integer, String>();
			groupPrepositionel   groupPrepositionel  = new groupPrepositionel(currentSyntaxeTreat.get(0), currentTextTreat.get(0),
													  					      prepositionFunction);
			

			
	
			//------------------------------------------------------------------------------conjonction coordination
			Map<Integer, String> conjonctionLocalisation  = new HashMap<Integer, String>();
			ConjonctionDeCoordination conjonction         = new ConjonctionDeCoordination(currentSyntaxeTreat.get(0), 
																						  currentTextTreat.get(0),
																						  conjonctionLocalisation);
	
	

	
			//------------------------------------------------------------------------------ dépendances"
			Map<String, String> GROUPEINFORMATIONS  = new HashMap<String, String>();
			ArrayList<String> 	GROUPS 				= new ArrayList<String>();


			System.out.println(currentTextTreat.get(0) + "\n\n\n");
			defineGroups defineGroups = new defineGroups(groupNominalFunctionBeginIndex,
														 groupsVerbalsFunctionBeginIndex,
														 pronomToGNBeginIndex,
														 adverbeFunction,
														 propositionRelativeFunction,
														 prepositionFunction,
														 conjonctionLocalisation,
														 currentSyntaxeTreat.get(0),
														 currentTextTreat.get(0),
														 sentenceText.get(sentenceNumber),
														 conjonction,
														 GROUPEINFORMATIONS,
														 GROUPS, SAVEPATH);
			
			System.out.println(currentTextTreat.get(0));
			
			
			clearNominal(groupNominalFunctionEndIndex, groupNominalFunctionBeginIndex);
			clearVerbal(groupsVerbalsMap, groupsVerbalsFunctionEndIndex, groupsVerbalsFunctionBeginIndex, pronomToGNBeginIndex);
			clearOtherGroup(adverbeFunction, propositionRelativeFunction, prepositionFunction, conjonctionLocalisation);
			clearGroups(GROUPEINFORMATIONS, GROUPS);
			clearSentence(currentSyntaxeTreat, currentTextTreat);
			
		}
		ChooseWordFunction chooseFunctionLogo = new ChooseWordFunction();
		chooseFunctionLogo.writtingLogo(SAVEPATH, "analyseSentence.txt");
	}









	private static void makeAnalyseSyntaxique(List<ArrayList<String>> syntaxeText, List<ArrayList<ArrayList<String>>> sentenceWordSplit,
			List<ArrayList<String>> sentenceText) throws IOException {

		for (int sentence=0; sentence < syntaxeText.size(); sentence++) {
			
			int sentenceNumber = sentence;

			System.out.println(sentenceNumber);
			
			ChooseWordFunction chooseFunction = new ChooseWordFunction();
			chooseFunction.ChooseWord(sentenceWordSplit, sentenceText, currentSyntaxeTreat, currentTextTreat, 
									  sentenceNumber, saveSyntaxe, saveSchema, SAVEPATH);
			
			clearSentence(currentSyntaxeTreat, currentTextTreat);

		}
		
		ChooseWordFunction chooseFunctionLogo = new ChooseWordFunction();
		chooseFunctionLogo.writtingLogo(SAVEPATH, "analyseSyntaxique.txt");
		
	}









	private static void clearSentence(ArrayList<ArrayList<ArrayList<String>>> currentSyntaxeTreat,
									  ArrayList<ArrayList<String>> currentTextTreat) {
		currentSyntaxeTreat.clear();
		currentTextTreat.clear();
		
	}


	private static void clearGroups(Map<String, String> GROUPEINFORMATIONS, ArrayList<String> GROUPS) {
		GROUPEINFORMATIONS.clear();
		GROUPS.clear();
		
	}



	private static void clearOtherGroup(Map<Integer, String> adverbeFunction,
										Map<Integer, String> propositionRelativeFunction,
										Map<Integer, String> prepositionFunction,
										Map<Integer, String> conjonctionLocalisation) {
		adverbeFunction.clear();
		propositionRelativeFunction.clear();
		prepositionFunction.clear();
		conjonctionLocalisation.clear();
		
	}






	private static void clearVerbal(Map<Integer, String> groupsVerbalsMap,
									Map<Integer, String> groupsVerbalsFunctionEndIndex, 
									Map<Integer, String> groupsVerbalsFunctionBeginIndex,
									Map<Integer, String> pronomToGNBeginIndex) {
		groupsVerbalsMap.clear();
		groupsVerbalsFunctionEndIndex.clear();
		groupsVerbalsFunctionBeginIndex.clear();
		pronomToGNBeginIndex.clear();
		
	}



	private static void clearNominal(Map<Integer, String> groupNominalFunctionEndIndex, Map<Integer, String> groupNominalFunctionBeginIndex) {
		groupNominalFunctionEndIndex.clear();
		groupNominalFunctionBeginIndex.clear();
	}
	
	
	
	

	private static void treatmentText2(TreatData treatment, List<ArrayList<ArrayList<String>>> sentenceWordSplit) {
		treatment.treatementSyntaxe("Raise doublon", sentenceWordSplit);
		treatment.treatementSyntaxe("Raise no found word", sentenceWordSplit);
		treatment.treatementSyntaxe("Verbe treatment", sentenceWordSplit);
	}





	private static void treatmentText1(TreatData treatment, List<ArrayList<String>> syntaxeText, List<ArrayList<String>> sentenceText) {
		
		treatment.deleteCharacter("Remove first space", syntaxeText);
		treatment.deleteCharacter("Remove space beetween words", sentenceText);
	}
	
	
	
	
	
	
	
	
}

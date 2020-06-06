package fr.jbaw.programme2.localisationByPrepositionAdverbe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.jbaw.programme2.groupAdverbial;
import fr.jbaw.programme2.groupPrepositionel;
import fr.jbaw.programme2.searchWordUtils;

public class localisationByPrepoAdverbe extends localisationPrepAdvUtils{

	private ArrayList<String>            currentText;
	private ArrayList<ArrayList<String>> currentSyntaxe;
	private ArrayList<String>            GROUPS;
	private ArrayList<String> 			 dataSubject;
	private ArrayList<String> 			 dataAction;
	private ArrayList<String> 			 dataActionCmplt;
	private ArrayList<String> 			 dataActionGn;
	private ArrayList<ArrayList<String>> partOfSentence;
	private int 						 inSentence;
	private Map<int[], String> 			 cmpltDico;
	private ArrayList<ArrayList<String>> prepAdv;
	private Map<String, ArrayList<String>> qui;


	private String[] DETERMINANTS = {"Article indéfini", "Article défini", "Article défini numéral",
								     "Pronom indéfini", "Forme de pronom indéfini", 
								     "pronom défini",
								     "Adjectif indéfini", "Adjectif possessif", "Adjectif démonstratif",
								     "Forme d'Adjectif possessif", "Forme d'Adjectif démonstratif",
							         "déterminants interrogatifs", 
								     "Forme d'article défini", "Forme d'article indéfini",
								     "Forme d’Adjectif possessif", "Forme d’Adjectif démonstratif",
								     "Forme d’article défini", "Forme d’article indéfini", "Adjectif interrogatif",
								     "Adjectif exclamatif", "Adjectif", "Forme d’adjectif", "Préposition", "Article défini",
								     "Forme d’article défini"};

	
	public localisationByPrepoAdverbe(ArrayList<String> currentText, ArrayList<ArrayList<String>> currentSyntaxe, 
			ArrayList<String> GROUPS, ArrayList<String> dataSubject, ArrayList<String> dataAction,
			ArrayList<String> dataActionCmplt, ArrayList<String> dataActionGn, 
			ArrayList<ArrayList<String>> partOfSentence, int inSentence, Map<int[], String> cmpltDico, 
			ArrayList<ArrayList<String>> prepAdv, Map<String, ArrayList<String>> qui) {

	
		
		
		
		
		
		this.currentText     = currentText;
		this.currentSyntaxe  = currentSyntaxe;
		this.GROUPS          = GROUPS;

		this.dataSubject     = dataSubject;
		this.dataAction      = dataAction;
		this.dataActionCmplt = dataActionCmplt;
		this.dataActionGn    = dataActionGn;

		this.partOfSentence  = partOfSentence;
		this.inSentence      = inSentence;
		this.cmpltDico       = cmpltDico;
		this.prepAdv         = prepAdv;
		this.qui             = qui;

		
		System.out.println("\n\nlocalisationByPrepoAdverbe - localisationByPrepoAdverbe");

		System.out.println(currentText);

		

		for(Entry<int[], String> groups: cmpltDico.entrySet()) {

			int[] indexs = groups.getKey();


			List<ArrayList<String>> syntaxe = currentSyntaxe.subList(indexs[0], indexs[1]);

			int index=indexs[0];
			for (ArrayList<String> current: syntaxe) {

				
				ArrayList<String> containerPreposition = new ArrayList<String>();
				ArrayList<String> containerAdverbe     = new ArrayList<String>();


				String[] adverbe     = recuperateAdverbe(index, currentText, current);
				String   preposition = searchPrepoFunction(index, currentText, current, containerPreposition);

				boolean haveFoundPrep  = containerPreposition.size() > 0;
				boolean haveFoundAdv   = adverbe[0]  != "" && adverbe[1] != "";


				if (haveFoundPrep) { 
					System.out.println("Preposition: " + preposition + " " + containerPreposition);
					conditionPreposition(preposition, currentSyntaxe, index, dataSubject,
										 dataActionCmplt, qui, prepAdv, containerPreposition);
				}


				if (haveFoundAdv)  { 
					System.out.println("adverbe: "     + Arrays.toString(adverbe));
					modifyCmplt(currentSyntaxe, index, dataActionCmplt, adverbe, "Adv");
				}


				
				
				index++;
			}
		}
		inGroupVerbal(currentSyntaxe, currentText, dataAction);
	}




	private void conditionPreposition(String prepositionWord, ArrayList<ArrayList<String>> currentSyntaxe, 
			int index, ArrayList<String> dataSubject, ArrayList<String> dataActionCmplt, Map<String, ArrayList<String>> qui, 
			ArrayList<ArrayList<String>> prepAdv, ArrayList<String> containerPreposition) {

	
		String   prepositionFunction = definePreposition(containerPreposition, currentSyntaxe, index);

		boolean  haveOne     = !(prepositionFunction.equals(""));

		String[] preposition = {prepositionWord, prepositionFunction};


		if (haveOne) { modifyCmplt(currentSyntaxe, index, dataActionCmplt, preposition, "Prep"); }
		if (haveOne) { afterVerbePreposition(currentSyntaxe, index, qui, dataSubject, preposition, prepAdv); }
		


	}


	
	
	





	private String definePreposition(ArrayList<String> containerPreposition, ArrayList<ArrayList<String>> currentSyntaxe, int index) {



		boolean prepositionIsOne = containerPreposition.size() == 1;

		if (prepositionIsOne) { return containerPreposition.get(0); }


		String[] temps  	   = {"Adjectif numéral", "Adverbe"};
		String[] lieu   	   = {"Nom commun", "Nom propre", "nom propre"};
		String[] but    	   = {"Pronom personnel", "Nom propre", "nom propre"};
		String[] appartenance  = {"Nom propre", "Pronom personnel", "Adjectif possessif", "défini"};
		String[] origine       = {"Nom propre", "Adjectif possessif"};
		String[] matière       = {"Nom propre"};
		String[] maniere       = {"Nom commun", "verbe#"};
		String[] agent         = {"Nom commun"};
		String[] opposition    = {"verbe#"};
		String[] moyen         = {"verbe#"};
		String[] accompgnmt    = {"verbe#"};
		
		String[] notAgent      = {"verbe#"};
		String[] notTime       = {"Nom propre", "nom propre"};
		
		ArrayList<String> propositions = new ArrayList<String>();

		for (String preposition: containerPreposition) {

			boolean isTime    = false;
			boolean isLieu    = false;
			boolean isAptnc   = false;
			boolean isAgent   = false;
			boolean isManiere = false;
			boolean isMatière = false;
			boolean isOrigine = false;
			boolean isBut	  = false;
			boolean isOppo    = false;
			boolean isMoyen   = false;
			boolean isAcc     = false;
			boolean isDest    = false;
			
			boolean isNotAgent = false;
			boolean isNotTime  = false;
			boolean isNotManie = false;
			
			boolean containsTime    = preposition.equals("temps");
			boolean containsLieu    = preposition.equals("lieu");
			boolean containsDest    = preposition.equals("destination");
			boolean containsAptnc   = preposition.equals("appartenance");
			boolean containsAgent   = preposition.equals("agent");
			boolean containsManiere = preposition.equals("maniere");
			boolean containsMatière = preposition.equals("matière");
			boolean containsOrigine = preposition.equals("origine");
			boolean containsBut     = preposition.equals("but");
			boolean containsOppo    = preposition.equals("opposition");
			boolean containsMoyen   = preposition.equals("moyen");
			boolean containsAcc     = preposition.equals("accompagnement");
			
			
			
			if (containsLieu)     { isLieu    = runSchema(currentSyntaxe, lieu,  index + 1, index + 4); }
			if (containsAptnc)    { isAptnc   = runSchema(currentSyntaxe, appartenance,  index + 1, index + 2); }
			if (containsAgent)    { isAgent   = runSchema(currentSyntaxe, agent,    index + 1, index + 4); }
			if (containsManiere)  { isManiere = runSchema(currentSyntaxe, maniere,  index - 1, index + 1); }
			if (containsMatière)  { isMatière = runSchema(currentSyntaxe, matière,  index + 1, index + 2); }
			if (containsOrigine)  { isOrigine = runSchema(currentSyntaxe, origine,  index + 1, index + 2); }
			if (containsBut)      { isBut     = runSchema(currentSyntaxe, but,      index + 1, index + 2); }
			if (containsOppo)     { isOppo    = runSchema(currentSyntaxe, opposition,  index - 1, index + 2); }
			if (containsMoyen)    { isMoyen   = runSchema(currentSyntaxe, moyen,       index, index + 2); }
			if (containsAcc)      { isAcc     = runSchema(currentSyntaxe, accompgnmt,  index, index + 2); }
			if (containsDest)     { isDest    = runSchema(currentSyntaxe, lieu,  index, index + 4); }
			if (containsTime) 	  { isTime    = runSchema(currentSyntaxe, temps, index + 1, index + 2); }
			
			
			if (containsAgent)    { isNotAgent   = runSchema(currentSyntaxe, notAgent, index - 1, index); }
			if (containsTime)     { isNotTime    = runSchema(currentSyntaxe, notTime,  index - 1, index + 4); }
			if (containsManiere)  { isNotManie   = runSchema(currentSyntaxe, notTime,  index - 1, index + 4); }
			
			
			

			if (isLieu)    { propositions.add("lieu");  }
			if (isDest)    { propositions.add("destination");  }

			if (isAptnc)   { propositions.add("appartenance"); }

			if (isMatière) { propositions.add("matière"); }
			if (isOrigine) { propositions.add("origine"); }
			if (isBut)     { propositions.add("but");     }
			if (isOppo)    { propositions.add("opposition"); }
			if (isMoyen)   { propositions.add("moyen"); }
			if (isAcc)     { propositions.add("accompagnement"); }
			
			if (isManiere && !isNotManie) { propositions.add("manière"); }
			if (isTime  && !isNotTime)    { propositions.add("temps"); }
			if (isAgent && !isNotAgent)   { propositions.add("agent"); }
		}


		
		boolean oneFound = propositions.size() == 1;
		boolean Found    = propositions.size() > 1;

		if (oneFound) { 
			System.out.println("found: " + propositions);
			return propositions.get(0); 
		}

		else if (Found){
	
			String increment = "";
			for (String element: propositions) { increment += (element + ", "); } 
			return increment.substring(0, increment.length() - 2);
		}


		return "";
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	private void inGroupVerbal(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText, 
							   ArrayList<String> dataAction) {


		ArrayList<String> current = partOfSentence.get(inSentence);

		int[] indexs = recuperateIndexFromGroupInList(current, "GVERBAL");
		int   begin  = indexs[0];
		int   end    = indexs[1];

		boolean inRange = begin >= 1;
		
		if (inRange) {
		
			List<ArrayList<String>> partSytnaxe = currentSyntaxe.subList(begin - 1, end + 1);
	
			
			String preposition = "";
			int index=0;
			for (ArrayList<String> syntaxe: partSytnaxe) {
				boolean isPreposition = listEqualsElement(syntaxe, "Préposition");
				if (isPreposition) { preposition = currentText.get(index); break; }
				index++;
			}
	
		
			
			ArrayList<String>  containerPreposition = new ArrayList<String>();
			groupPrepositionel serchPrep  			= new groupPrepositionel(preposition);
			serchPrep.searchPrepoFunction(containerPreposition);
	
			String prepoFunction = definePreposition(containerPreposition, currentSyntaxe, index);

			boolean haveFound = !(prepoFunction.equals(""));
			if (haveFound) {
				String action = dataAction.get(0);
				dataAction.set(0, "(" + prepoFunction + ") " + preposition + " " + action);
			}
		}
	}
	
	


	private void modifyCmplt(ArrayList<ArrayList<String>> currentSyntaxe, int index,
						     ArrayList<String> dataActionCmplt, String[] preposition, String function) {
		

		boolean inRangeCurrent = index < currentText.size() - 1;

		if (inRangeCurrent) {
			String  current =  currentText.get(index);
			String  next    =  currentText.get(index + 1);
	
			String[] dataSplit = dataActionCmplt.get(0).split(" ");
			
			String  increment       = "";
			boolean notIncremented = false;
			for (int indexWord=0; indexWord < dataSplit.length; indexWord++) {
	
				boolean inRange = indexWord < dataSplit.length - 1;
	
				String word     = dataSplit[indexWord];
				String nextWord = ""; if (inRange) { nextWord = dataSplit[indexWord + 1]; } 
	
				boolean isLast = (nextWord.equalsIgnoreCase(next) && 
								  word.equalsIgnoreCase(current)) && !notIncremented;
	
				if   (isLast) { increment += (word + " (" + function + " " + preposition[1] + ") "); notIncremented = true; }
				else 		  { increment += (word + " "); }
			}
	
			boolean notEmpty = !(increment.equals(""));
			if (notEmpty) { dataActionCmplt.set(0, increment.substring(0, increment.length() - 1)); }
		}
	}




	protected void afterVerbePreposition(ArrayList<ArrayList<String>> currentSyntaxe, int index,
			Map<String, ArrayList<String>> qui, ArrayList<String> dataSubject, String[] prepositonWord, 
			ArrayList<ArrayList<String>> prepAdv) {

		boolean inRange = index + 3 < currentSyntaxe.size() && index > 1;
		
		if (inRange) {
		
			ArrayList<String> lastSyntaxe   = currentSyntaxe.get(index - 1);
			ArrayList<String> nextSyntaxe   = currentSyntaxe.get(index + 1);

			ArrayList<String> nextX2Syntaxe = currentSyntaxe.get(index + 2);
			ArrayList<String> nextX3Syntaxe = currentSyntaxe.get(index + 3);
			
			String  last   =  currentText.get(index - 1);
			String  next   =  currentText.get(index + 1);
			String  nextX2 =  currentText.get(index + 2);
			String  nextX3 =  currentText.get(index + 3);
	
			
			boolean lastIsVerb   = listContains(lastSyntaxe, "verbe#");
	
			boolean nextIsArt    = thisListEqualList(nextSyntaxe, DETERMINANTS);
			boolean nextIsNc     = listEqualsElement(nextSyntaxe, "Nom commun");
			boolean nextIsAdj    = listContains(nextSyntaxe, "djectif");
	
			boolean nextX2IsNc   = listEqualsElement(nextX2Syntaxe, "Nom commun");
			boolean nextX2IsAdj  = listContains(nextX2Syntaxe, "djectif");
	
			boolean nextX3IsNc   = listEqualsElement(nextX3Syntaxe, "Nom commun");
	
	
			String increment = "";
			String space     = " ";
			String prepoInc  = "(" + prepositonWord[1] + ") " + prepositonWord[0] + space;
	
			String sentence1 = prepoInc + next + space + nextX2;
			String sentence2 = prepoInc + next;
			String sentence3 = prepoInc + next + space + nextX2;
			String sentence4 = prepoInc + next + space + nextX2 + space + nextX3;
			
	
			
			if      (lastIsVerb && nextIsArt && nextX2IsNc) 			       { 
				increment = sentence1;
				incrementSubjectData(dataSubject, qui, prepAdv, increment);
			}
	
			else if (lastIsVerb && nextIsNc) 				 			   { 
				increment = sentence2;
				incrementSubjectData(dataSubject, qui, prepAdv, increment);
			}
	
			else if (lastIsVerb && nextIsAdj && nextX2IsNc)  			   { 
				increment = sentence3; 
				incrementSubjectData(dataSubject, qui, prepAdv, increment);
			}
	
			else if (lastIsVerb && nextIsArt && nextX2IsAdj && nextX3IsNc) { 
				increment = sentence4;
				incrementSubjectData(dataSubject, qui, prepAdv, increment);
			}
		}
	}
	
	
	
	
	


	private String searchPrepoFunction(int index, ArrayList<String> currentText2, ArrayList<String> current,
										 ArrayList<String> containerPreposition) {

		String word = currentText2.get(index);
		boolean isPreposition = listEqualsElement(current, "Préposition");

		if (isPreposition) {

			groupPrepositionel serchPrep  = new groupPrepositionel(word);
			serchPrep.searchPrepoFunction(containerPreposition);

		}
		return word;
	}



	private String[] recuperateAdverbe(int index,  ArrayList<String> currentText2, ArrayList<String> current) {

		String[] adverbeInformations = {"", ""};

		String word = currentText2.get(index);
		boolean isAdverbe = listEqualsElement(current, "Adverbe");

		if (isAdverbe) {

			groupAdverbial serchAdvb  = new groupAdverbial(word);
			String 		   advFind    = serchAdvb.searchAdvbInDefineGroupe();

			adverbeInformations[0] = word;
			adverbeInformations[1] = advFind;
		}

		return adverbeInformations;
	}

	
	
}

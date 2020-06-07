package fr.jbaw.programme2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class groupVerbal extends searchingInSentence implements RecuperateIndexAndGroupFromText{

	
	private ArrayList<ArrayList<String>> currentSyntaxe;
	private ArrayList<String> currentText;

	private Map<Integer, String> groupsVerbalsMap;
	
	private String[] GROUPVERBAL  = {"Pronom personnel", "verbe#", "Pronom démonstratif", 
									 "Particule", "Pronom interrogatif"};

	private Map<Integer, String>  pronomToGN;

	private Map<Integer, String>  groupsVerbalsFunction;
	private Map<Integer, String>  groupsVerbalsFunctionBeginIndex;
	
	
	
	public groupVerbal(ArrayList<ArrayList<String>> currentSyntaxe, 
					   ArrayList<String> currentText, Map<Integer, String> groupsVerbalsMap, 
					   Map<Integer, String> pronomToGN, Map<Integer, String> groupsVerbalsFunction, 
					   Map<Integer, String> groupsVerbalsFunctionBeginIndex) {
		
		
		this.currentSyntaxe                  = currentSyntaxe;
		this.currentText                     = currentText;
		this.groupsVerbalsMap                = groupsVerbalsMap;

		this.pronomToGN                      = pronomToGN;

		this.groupsVerbalsFunction           = groupsVerbalsFunction;
		this.groupsVerbalsFunctionBeginIndex = groupsVerbalsFunctionBeginIndex;
	}
	
	


	void verbalGroup () {

		//0le 1chat 2mange 3des 4oiseaux. -> [0, 1, 3, 4]
		ArrayList<Integer> recupIndex = new ArrayList<Integer>();
		RecuperateIndex(GROUPVERBAL, recupIndex, "contains");

		boolean groupNomIsEmpty = recupIndex.size() == 0;

		if (!groupNomIsEmpty) {

			RecuperateGroup(this.groupsVerbalsMap, recupIndex);
			decoupageGroupVerbal(recupIndex);
			reajustBeginIndexVerbal();
		}
	}
	
	

	void reajustBeginIndexVerbal() {

		for(Entry<Integer, String> groups: this.groupsVerbalsFunction.entrySet()) {

			int      index         = groups.getKey();
			String   function      = groups.getValue();
			String[] functionSplit = function.split("/");

			String   toPronomFun   = this.pronomToGN.get(index);

			int length = -1;
			for (String el: functionSplit) {if (el.length() > 1) {length += 1;}}

			int begening   = index - length;
			boolean isZero = begening == -1;

			if (isZero) { this.groupsVerbalsFunctionBeginIndex.put(0, function); }
			else        { this.groupsVerbalsFunctionBeginIndex.put(begening, function); }
			//this.groupsVerbalsFunctionBeginIndex.put(begening, function);
			 
		}
	}



		
		
		
		
		

	
	
	
	private void decoupageGroupVerbal(ArrayList<Integer> recupIndex) {


		for(Entry<Integer, String> groupVerbal: this.groupsVerbalsMap.entrySet()) {

			int    indexGroup = groupVerbal.getKey();
			String group      = groupVerbal.getValue();

			String[] groupsVerbalsSplit = group.split("/");

			boolean isVerb    	       = thisListContainsWord(groupsVerbalsSplit, "verbe#");
			boolean isPronom  		   = thisListEqualWord(groupsVerbalsSplit, 	"Pronom personnel");
			boolean isPronomDemons	   = thisListEqualWord(groupsVerbalsSplit, "Pronom démonstratif");
			boolean isPronomIntero	   = thisListEqualWord(groupsVerbalsSplit, "Pronom intérrogatif");
			
			boolean isPronomIndef	   = thisListEqualWord(groupsVerbalsSplit, "Pronom indéfini") ||
									     thisListEqualWord(groupsVerbalsSplit, "Forme de pronom indéfini");
			
			boolean isParticule 	   = thisListEqualWord(groupsVerbalsSplit, "Particule");


			if (isPronom) 		{ pronom(indexGroup, groupsVerbalsSplit); }
			if (isPronom) 		{ pronomPersonnelCompletment(indexGroup, groupsVerbalsSplit); }
			if (isPronomDemons) { pronomDemonstratif(indexGroup, groupsVerbalsSplit); }
			if (isVerb)   		{ verb(indexGroup, groupsVerbalsSplit); }
			if (isParticule)    { particule(indexGroup, groupsVerbalsSplit); }
			if (isPronomIntero) { pronomDemonstratif(indexGroup, groupsVerbalsSplit); }
			if (isPronomIndef)  { pronomIndéfini(indexGroup, groupsVerbalsSplit); }

		}
	}


	
	
	














	void verb(int indexGroup, String[] groupsVerbalsSplit) {

		int begeinOfGroup = indexGroup - (groupsVerbalsSplit.length - 1);
		
		int indexINGroup = 0;
		for(int indexSyntaxe=begeinOfGroup; indexSyntaxe <= indexGroup; indexSyntaxe++) {

			String text = this.currentText.get(indexSyntaxe);

			boolean isVerb = groupsVerbalsSplit[indexINGroup].contains("verbe#");

			if (isVerb) {
				modificateGroup(indexGroup, text, "/verbe=", this.groupsVerbalsMap, this.groupsVerbalsFunction);
				String timeOfVerb = tempsVerb(indexSyntaxe);
				searchSubjectOfVerb(timeOfVerb, indexSyntaxe);
			}
			indexINGroup++;
		}
		 didntFoundADesign(indexGroup, groupsVerbalsSplit);
	}

	void didntFoundADesign(int indexGroup, String[] groupsVerbalsSplit) {

		boolean isInRange = indexGroup < this.pronomToGN.size();

		if (isInRange) {
			boolean isDesign    = this.pronomToGN.get(indexGroup).contains("design");
			boolean isInfinitif = this.pronomToGN.get(indexGroup).contains("infinitif");
			boolean isNotEmpty  = this.pronomToGN.get(indexGroup).length() > 1;
	
			boolean dicoDntContainsDesign = dicoContains(this.pronomToGN, "design");
			
			
			if ( !isDesign && !isInfinitif && isNotEmpty && !dicoDntContainsDesign) { 
				
				String[] definitionVerbSplit = groupsVerbalsSplit[0].split("[#]");
				boolean  defIsNotEmpty       = definitionVerbSplit.length > 1;
				if (defIsNotEmpty) {
					String[] partVerb            = definitionVerbSplit[1].split(" ");
					String   pronom              = partVerb[0];
					String   valueRecuperate     = this.pronomToGN.get(indexGroup) + "=isDesign=" + pronom;
		
					this.pronomToGN.put(indexGroup, valueRecuperate); 
				}
			}
			if ( !isDesign &&  isInfinitif ) { 
	
				String   valueRecupInfi      = this.pronomToGN.get(indexGroup) + "=isDesign=" + "None";
				this.pronomToGN.put(indexGroup, valueRecupInfi); 
			}
		}
	}
	


	void searchSubjectOfVerb(String timeOfVerb, int indexText) {

		String word = this.currentText.get(indexText);

		String[] presentSame         = {"je-il-elle-on=e", "je-tu=rs", "je-tu=ds"};
		String[] imparfaitSame       = {"je+tu=ais", };
		String[] plusQueParfaitSame  = {"je+tu=ais", };
		String[] passéAntérieur      = {"je+tu=eus", };
		String[] conditionelPresent  = {"je+tu=erais",};
		String[] passéPremiereForme  = {"je+te=rais", };


		verifyPersonByTerm(presentSame, word, indexText);
	}

	private void verifyPersonByTerm(String[] termTime, String word, int indexText) {

		for (String termineason: termTime) {

			String[] persons         = termineason.split("=")[0].split("-");
			String   personsToString = "";
			String   term            = termineason.split("=")[1];

			String[] splitWord = word.split("");

			int lengthWord = splitWord.length;
			int lengthTerm = term.length();
			int begening   = lengthWord - lengthTerm;
		
			boolean lengthNotOne = lengthWord > 1;
			
			if (lengthNotOne) {
			
				String currentWordTerm = word.substring(begening, lengthWord);
	
				boolean termAndWordTermEqual = currentWordTerm.equalsIgnoreCase(term);
	
				if (termAndWordTermEqual) {
					for(String p: persons) {personsToString += (p + "-");}
					modificateGroup(indexText, personsToString, "=design=", this.groupsVerbalsMap, this.pronomToGN);
				}
			}
		}
	}
	

	String tempsVerb(int indexText) {

		String   syntaxe      = this.currentSyntaxe.get(indexText).get(0);
		String   word         = this.currentText.get(indexText);

		String[] passé        = {"assé composé", "mparfait", "lus-que-parfait", "assé simple",
								 "assé antérieur", "Conditionnel passé", "Participe passé", "Impératif passé"};
		String[] present      = {"présent", "Conditionnel présent", "Participe present", "Impératif présent"};
		String[] futur        = {"utur simple", "utur antérieur", };

		String  timePresent = wordContainsThisListRecuperate(syntaxe, present);
		String  timePast    = wordContainsThisListRecuperate(syntaxe, passé);
		String  timeFutur   = wordContainsThisListRecuperate(syntaxe, futur);
		boolean infinitif   = syntaxe.equalsIgnoreCase("verbe#");

		String[] recuperateTime = {timePresent, timePast, timeFutur};
		String timeLocalisation = time(recuperateTime, infinitif);


		for (String time: recuperateTime) {if (time != "") {
			String timePart = syntaxe.split("#")[2];
			modificateGroup(indexText, word, "/"+timeLocalisation, this.groupsVerbalsMap, this.pronomToGN);
			return timePart;}
		}

		if (infinitif) {
			modificateGroup(indexText, word, "/"+timeLocalisation, this.groupsVerbalsMap, this.pronomToGN);
			return "infinitif";
		}

		return "";
	}

	private String time(String[] recuperateTime, boolean infinitif) {

		if 		(recuperateTime[0] != "") {return "temps.présent=";}
		else if (recuperateTime[1] != "") {return "temps.passé=";}
		else if (recuperateTime[2] != "") {return "temps.futur=";}
		else if (infinitif) {return "temps.infinitif=";}

		return "";
	}

	


	
	

	
	
	private void pronomDemonstratif(int indexGroup, String[] groupsVerbalsSplit) {
		String[] Demonstratif = {"celui", "celui-ci", "celui-là", "contraction=ce", "celle", "celle-ci", "celle-là",
								 "ça", "ce", "ceci", "cela", "celles", "celles-ci", "celles-là", "ces", "ceux", "ceux-la", "ceux-ci"};


		int begeinOfGroup 			= indexGroup - (groupsVerbalsSplit.length - 1);
		String functionDemonstratif = "/Pronom demonstratif=";

		for(int index=begeinOfGroup; index <= indexGroup; index++) {
			
			String text  = this.currentText.get(index);
			
			modifiateGroupInFunctionOfGroup(Demonstratif, indexGroup, text, functionDemonstratif);
			incrementDictionnaryPronomDemonstratifToGN(text, index);
		}
	}
	
	private void incrementDictionnaryPronomDemonstratifToGN(String text, int index) {

		String[] DemonMasc      = {"celui", "celui-ci", "celui-là"};
		String[] DemonFem    	= {"celle"};
		String[] DemonNeutre    = {"ce", "ça", "ceci", "contraction=ce", "cela"};
		
		String[] DemonMascPlur  = {"ceux"};
		String[] DemonFemPlur   = {"celles"};


		modifiatePronomComplementToGN(DemonMasc,       index, text, "designMasc=");
		modifiatePronomComplementToGN(DemonFem, 	   index, text, "designFem=");
		
		modifiatePronomComplementToGN(DemonNeutre,     index, text, "designNeutre=");
		modifiatePronomComplementToGN(DemonMascPlur,   index, text, "designMascPlur=");
		modifiatePronomComplementToGN(DemonFemPlur,    index, text, "desginFemPlur=");

	}
	
	

	private void particule(int indexGroup, String[] groupsVerbalsSplit) {
		
		String[] PARTICULE = {"contraction=ne", "ne"};
		
		int begeinOfGroup = indexGroup - (groupsVerbalsSplit.length - 1);

		for(int index=begeinOfGroup; index <= indexGroup; index++) {

			String text     	     = this.currentText.get(index);
			String functionParticule = "/négation=";

			
			modifiateGroupInFunctionOfGroup(PARTICULE, indexGroup, text, functionParticule);

			incrementDictionnaryPronomComplementToGN(text, index);


		}
	}
	
	
	void pronomPersonnelCompletment(int indexGroup, String[] groupsVerbalsSplit) {

		String[] PRNMREFLECHIS = {"me", "contraction=me", "te", "contraction=te", "se", "contraction=se"};
		String[] PRNMOBJT = {"contraction=le", "en", "contraction=la", "les", "un", "une", "y"}; //orienté objet
		String[] PRNMPRSN = {"lui", "leur", "moi", "toi", "soi", "eux", "le", "la",
							 "lui-même", "soi-même", "il-même", "elle-même", "nous-même", "vous-même", "ils-même", "elles-même",
							 "toi-même", }; //orienté personne

		int begeinOfGroup = indexGroup - (groupsVerbalsSplit.length - 1);
		int nous = 0;
		int vous = 0;

		for(int index=begeinOfGroup; index <= indexGroup; index++) {

			String text     	   = this.currentText.get(index);
			String functionReflech = "/complement reflechi=";
			String functionCod 	   = "/complement OBJT=";
			String functionCoi 	   = "/complement PRSN=";


			modifiateGroupInFunctionOfGroup(PRNMREFLECHIS, indexGroup, text, functionReflech);
			modifiateGroupInFunctionOfGroup(PRNMOBJT,      indexGroup, text, functionCod);
			modifiateGroupInFunctionOfGroup(PRNMPRSN,      indexGroup, text, functionCoi);
			incrementDictionnaryPronomComplementToGN(text, index);
	
		}
	}

	private void modifiateGroupInFunctionOfGroup(String[] GROUP, int indexGroup, String text, String function) {

		for (String i: GROUP){
			boolean isEqual = text.equalsIgnoreCase(i);
			if (isEqual) {modificateGroup(indexGroup, text, function, this.groupsVerbalsMap, this.groupsVerbalsFunction);}
		}
	}
	

	private void incrementDictionnaryPronomComplementToGN(String text, int index) {

		String[] enonciateur     = {"me", "contraction=me", "moi"};
		String[] destinataire    = {"te", "contraction=te", "toi"};

		String[] designFem       = {"se", "contraction=se", "la", "contraction=la"};
		String[] designMasc      = {"se", "contraction=se", "le", "contraction=le", "y", "soi"};
		String[] desginGen       = {"se", "contraction=se", "soi", "lui"};

		String[] designFemPlur   = {"se", "contraction=se", "se", "contraction=se", "leur"};
		String[] designMascPlur  = {"se", "contraction=se", "leur", "eux"};
		String[] enonciateurPlur = {"nous"};//Nous
		String[] designPlurDest  = {"vous"};//Vous
		
		modifiatePronomComplementToGN(enonciateur,  index, text, "complement reflechi=");
		modifiatePronomComplementToGN(destinataire, index, text, "complement reflechi=");
		
		modifiatePronomComplementToGN(designFem,    index, text, "complement reflechi=");
		modifiatePronomComplementToGN(designMasc,   index, text, "complement reflechi=");
		modifiatePronomComplementToGN(desginGen,    index, text, "complement reflechi=");
		
		modifiatePronomComplementToGN(designFemPlur,   index, text, "complement reflechi=");
		modifiatePronomComplementToGN(designMascPlur,  index, text, "complement reflechi=");
		modifiatePronomComplementToGN(enonciateurPlur, index, text, "complement reflechi=");
		modifiatePronomComplementToGN(designPlurDest,  index, text, "complement reflechi=");
	}

	private void modifiatePronomComplementToGN(String[] GROUP, int indexGroup, String text, String function) {

		for (String i: GROUP){
			boolean isEqual = text.equalsIgnoreCase(i);
			if (isEqual) {modificateGroup(indexGroup, text, function, this.groupsVerbalsMap, this.pronomToGN);}
		}
	}
	
	


	void pronomIndéfini(int indexGroup, String[] groupsVerbalsSplit) {

		 int begin = indexGroup - (groupsVerbalsSplit.length - 1);


		 for(int index=begin; index <= indexGroup; index++) {

			 List<String> partInterest = currentSyntaxe.get(index);
			 
			 boolean isPrnm1 = partInterest.get(0).equalsIgnoreCase("Pronom indéfini");
			 boolean isPrnm2 = partInterest.get(0).equalsIgnoreCase("Forme de pronom indéfini");
			 
			 if (isPrnm1 || isPrnm2) {
				 
				 String text     = this.currentText.get(index);
				 String function = "sujet=";
	
				 incrementDictionnaryPronomToGN("indéfini", indexGroup);
	
				 modificateGroup(indexGroup, text, function, this.groupsVerbalsMap, this.groupsVerbalsFunction);
			 }
		}
	}
	

	
	
	
	void pronom(int indexGroup, String[] groupsVerbalsSplit) {

		 int begin = indexGroup - (groupsVerbalsSplit.length - 1);


		 for(int index=begin; index <= indexGroup; index++) {

			 String text     = this.currentText.get(index);
			 String function = "sujet=";

			 incrementDictionnaryPronomToGN(text, indexGroup);

			 String[] PRONOM = {"je", "tu", "il", "elle", "on", "vous", "nous", "ils", "elles"};

			 for (String i: PRONOM) {

				 boolean isEquals = text.equalsIgnoreCase(i);
				 if (isEquals) {modificateGroup(indexGroup, text, function, this.groupsVerbalsMap, this.groupsVerbalsFunction);}
			 }
		}
	}




	private void modificateGroup(int indexGroup, String text, String function,
								 Map<Integer, String> thisReferenciel, Map<Integer, String> thisList) {

		boolean groupVerbalFunctionIsEmpty = thisList.size() == 0;

		if (groupVerbalFunctionIsEmpty) {
			for(Entry<Integer, String> groupVerbal: thisReferenciel.entrySet()) {
				thisList.put(groupVerbal.getKey(), "");
			}
		}

		String currentData = thisList.get(indexGroup);
		if (currentData    == null) {currentData = "";}

		String toChange    = currentData += (function + text.toLowerCase() + "/");
		thisList.put(indexGroup, toChange);
	}
	
	private void incrementDictionnaryPronomToGN(String text, int index) {
		 if     (text.equalsIgnoreCase("je"))    {modificateGroup(index, text, "/enonciateur=",    this.groupsVerbalsMap, this.pronomToGN);}
		 else if(text.equalsIgnoreCase("tu"))    {modificateGroup(index, text, "/destinataire=",   this.groupsVerbalsMap, this.pronomToGN);}
		 else if(text.equalsIgnoreCase("elle"))  {modificateGroup(index, text, "/designFem=",      this.groupsVerbalsMap, this.pronomToGN);}
		 else if(text.equalsIgnoreCase("il"))    {modificateGroup(index, text, "/designMasc=",     this.groupsVerbalsMap, this.pronomToGN);}
		 else if(text.equalsIgnoreCase("on"))    {modificateGroup(index, text, "/desginGen=",      this.groupsVerbalsMap, this.pronomToGN);} //groupe ou général (on frappe a la porte)
		 else if(text.equalsIgnoreCase("elles")) {modificateGroup(index, text, "/designFemPlur=",  this.groupsVerbalsMap, this.pronomToGN);}
		 else if(text.equalsIgnoreCase("ils"))   {modificateGroup(index, text, "/designMascPlur=", this.groupsVerbalsMap, this.pronomToGN);}
		 else if(text.equalsIgnoreCase("nous"))  {modificateGroup(index, text, "/enonciateurPlur=",this.groupsVerbalsMap, this.pronomToGN);} //+ il + elle? + tu?
		 else if(text.equalsIgnoreCase("vous"))  {modificateGroup(index, text, "/designPlurDest=", this.groupsVerbalsMap, this.pronomToGN);} //+ tu ? + il + elle ?
		 
		 else if(text.equalsIgnoreCase("indéfini"))  {modificateGroup(index, text, "/undesign=", this.groupsVerbalsMap, this.pronomToGN);}
	}
	
	

	@Override
	public void RecuperateIndex(String[] groupSyntaxe, ArrayList<Integer> recupIndex, String mode) {

		boolean isEqualMode    = mode.equalsIgnoreCase("equals");
		boolean isContainsMode = mode.equalsIgnoreCase("contains");

		for (int index=0; index < this.currentSyntaxe.size(); index ++) {

			ArrayList<String> currentList = this.currentSyntaxe.get(index);
			String word = this.currentText.get(index);

			if (isContainsMode) {listContainsIndex(currentList, groupSyntaxe, recupIndex, index);}

		}
	}


	@Override
	public void RecuperateGroup(Map<Integer, String> GroupMap, ArrayList<Integer> thisList) {

		String  increment = "";

		for (int index=0; index < thisList.size(); index++) {

			int current = thisList.get(index);

			boolean isFirstElement = index == 0;
			boolean isLastElement  = index == thisList.size() - 1;

			if      ( isFirstElement && !isLastElement)  {increment = isFirstElement(thisList, index, increment);}
			else if (!isFirstElement && !isLastElement)  {increment = isInList(thisList, index, increment);}
			else if (!isFirstElement &&  isLastElement)  {increment = isLastElement(thisList, index, increment);}
			else    								     {increment = this.currentSyntaxe.get(current).get(0);}

			boolean isEndGroup = increment.contains("+");
			String toAdd       = increment.substring(0, increment.length() - 1);

			if (isEndGroup) {GroupMap.put(current, toAdd); increment = "";}
		}

		boolean noIncremnted = increment != "";
		int     lastElement  = thisList.get(thisList.size() - 1);

		if (noIncremnted) {GroupMap.put(lastElement, increment);}
	}

	private String isFirstElement(ArrayList<Integer> thisList, int index, String increment) {

		int current = thisList.get(index);
		int next    = thisList.get(index + 1);

		boolean isNextPlusOne = (current + 1 == next);

		String currentSyntaxe = this.currentSyntaxe.get(current).get(0);

		if      ( isNextPlusOne) {increment += (currentSyntaxe + "/");}
		else if (!isNextPlusOne) {increment += (currentSyntaxe + "+");}

		return increment;
	}

	private String isInList(ArrayList<Integer> thisList, int index, String increment) {

		int last    = thisList.get(index - 1);
		int current = thisList.get(index);
		int next    = thisList.get(index + 1);

		boolean lastIsLessOne = (current - 1 == last);
		boolean nextIsPlusOne = (current + 1 == next);

		String lastSyntaxe    = this.currentSyntaxe.get(last).get(0);
		String currentSyntaxe = this.currentSyntaxe.get(current).get(0);
		String nextSyntaxe    = this.currentSyntaxe.get(next).get(0);

		if      ( nextIsPlusOne && !lastIsLessOne) {increment += (currentSyntaxe + "/");}
		else if ( lastIsLessOne && !nextIsPlusOne) {increment += (currentSyntaxe + "+");}
		else if ( lastIsLessOne &&  nextIsPlusOne) {increment += (currentSyntaxe + "/" );}
		else if (!lastIsLessOne && !nextIsPlusOne) {increment += (currentSyntaxe + "+");}

		return increment;
	}

	private String isLastElement(ArrayList<Integer> thisList, int index, String increment) {

		int last    = thisList.get(index - 1);
		int current = thisList.get(index);

		boolean lastIsLessOne = (current - 1 == last);

		String lastSyntaxe    = this.currentSyntaxe.get(last).get(0);
		String currentSyntaxe = this.currentSyntaxe.get(current).get(0);

		if 	    ( lastIsLessOne) {increment += (currentSyntaxe + "/");}
		else if (!lastIsLessOne) {increment += (currentSyntaxe + "+");}

		return increment;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

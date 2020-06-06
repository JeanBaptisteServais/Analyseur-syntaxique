package fr.jbaw.programme2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class groupNominal extends searchingInSentence implements RecuperateIndexAndGroupFromText{


	private String[] DETERMINANTCOMMUN   = {"Article indéfini", "Article défini", "Article défini numéral",
										    "Pronom indéfini", "Forme de pronom indéfini", "pronom défini",
										    "Adjectif indéfini", "Adjectif possessif", "Adjectif démonstratif",
										    "Forme d'Adjectif possessif", "Forme d'Adjectif démonstratif", "Forme d’adjectif indéfini",
										    "déterminants interrogatifs", "Forme d'article défini", "Forme d'article indéfini", 
										    "Forme d’Adjectif possessif", "Forme d’Adjectif démonstratif", "Forme d’article défini",
										    "Forme d’article indéfini", "Adjectif interrogatif","Adjectif exclamatif", "Adjectif numéral",
										    "Forme d’article défini"};
	

	private String[] EPITHETE    	    = {"Adjectif", "Forme d’adjectif"};

	private String[] NOMIGP 		    = {"Nom commun", "Nom propre", "Adjectif", "Article indéfini", "Article défini", 
										   "Article défini numéral", "Adjectif","Adjectif indéfini", "Adjectif possessif",
										   "Adjectif démonstratif", "Forme d'Adjectif possessif", "Forme d'Adjectif démonstratif", 
										   "Forme d'article défini", "Forme d'article indéfini", "Forme d’Adjectif possessif", 
										   "Forme d’Adjectif démonstratif", "Forme d’article défini", "Forme d’article indéfini", 
										   "Forme d’adjectif", "Adjectif numéral", "Forme d’adjectif indéfini", "Pronom indéfini",
										   "Locution",
										   "Forme d’article défini", "Forme de pronom indéfini"};


	private Map<Integer, String> 		  groupNominalFunctionEndIndex;
	private ArrayList<ArrayList<String>>  currentSyntaxe;
	private ArrayList<String> 			  currentText;

	
	private ArrayList<Integer> 			  GROUPNOMINAL 		   = new ArrayList<Integer>();
	private Map<Integer, String>          groupNominalFunction = new HashMap<Integer, String>();
	


	public groupNominal(ArrayList<ArrayList<String>> currentSyntaxe, 
						ArrayList<String> 			 currentText, 
						Map<Integer, String> 		 groupNominalFunction, 
						Map<Integer, String>         groupNominalFunctionEndIndex) {


		this.currentSyntaxe               = currentSyntaxe;
		this.currentText                  = currentText;
		this.groupNominalFunction         = groupNominalFunction;
		this.groupNominalFunctionEndIndex = groupNominalFunctionEndIndex;
	}


	void nominalGroup () {

		Map<Integer, String> groupsNominalsMap = new HashMap<Integer, String>();

		//0le 1chat 2mange 3des 4oiseaux. -> [0, 1, 3, 4]
		RecuperateIndex(NOMIGP, GROUPNOMINAL, "equals");

		boolean groupNomIsEmpty = GROUPNOMINAL.size() == 0;

		if (!groupNomIsEmpty) {
			RecuperateGroup(groupsNominalsMap, GROUPNOMINAL);
			decoupageGroupNominal(groupsNominalsMap);
			reajustBeginIndex();
		}
	}


	
	
	
	void reajustBeginIndex() {


		for(Entry<Integer, String> groups: this.groupNominalFunction.entrySet()) {

			int      index         = groups.getKey();
			String   function      = groups.getValue();
			String[] functionSplit = function.split("/");


			int length = -1;
			for (String el: functionSplit) {if (el.length() > 1) {length += 1;}}

			int begening = index - length;

			boolean isZero = index == 1 || begening == -1;
			//37 -> hum
			//if (isZero) { this.groupNominalFunctionEndIndex.put(0, function); }
			//else        { this.groupNominalFunctionEndIndex.put(begening, function); }
			this.groupNominalFunctionEndIndex.put(begening, function);
		}
	}
	
	
	
	
	

	void decoupageGroupNominal(Map<Integer, String> groupsNominalsMap) {

		ArrayList<String> noyeau            = new ArrayList<String>();
		ArrayList<String> determinantNoyeau = new ArrayList<String>();


		for(Entry<Integer, String> groupsNominals: groupsNominalsMap.entrySet()) {


			String[] GP = groupsNominals.getValue().split("/");
			//System.err.println(groupsNominals);
			//System.out.println(this.currentText.subList((groupsNominals.getKey() - GP.length) + 1, groupsNominals.getKey() + 1));


			ArrayList<String> elementGP = new ArrayList<String>();
			for(int index=0; index < GP.length; index++) {elementGP.add("_ ");}

			ArrayList<Integer> nomCommunIndex  = new ArrayList<Integer>();			//Nom commun
			thisListEqualsIndex(GP, "Nom commun", nomCommunIndex, "equal");
			thisListEqualsIndex(GP, "Forme de nom commun", nomCommunIndex, "equal");
			groupNominalElementFound(GP, nomCommunIndex, elementGP, "nc");

			
			ArrayList<Integer> locutionIndex  = new ArrayList<Integer>();			//Locution
			thisListEqualsIndex(GP, "Locution", locutionIndex, "equal");
			groupNominalElementFound(GP, locutionIndex, elementGP, "nc");
			
			
			
			ArrayList<Integer> nomPropreIndex  = new ArrayList<Integer>();			//Nom propre
			thisListEqualsIndex(GP, "Nom propre", nomPropreIndex, "equal");
			groupNominalElementFound( GP, nomPropreIndex, elementGP, "np");
			
			ArrayList<Integer> detIndex  = new ArrayList<Integer>();				//Determinant
			thisListEqualsThisListIndex(GP, DETERMINANTCOMMUN, detIndex, "equal");
			groupNominalElementFound(GP, detIndex, elementGP, "determinant");

			
			
			ArrayList<Integer> epitheteIndex  = new ArrayList<Integer>();			//Epithete
			thisListEqualsThisListIndex(GP, EPITHETE, epitheteIndex, "equal");
			groupNominalElementFound(GP, epitheteIndex, elementGP, "épithète");


			attributeFunction(elementGP, groupsNominals, GP);

			//for(int index=0; index < 3;index++) {System.out.println("");}
		}
	}

	

	void attributeFunction(ArrayList<String> elementGP, Entry<Integer, String> groupsNominals, String[] GP) {

		for (int index=0; index < elementGP.size(); index++) {

			int groupIndex    = recuperateIndexGroupNominal(groupsNominals, GP);
			int groupIndexEnd = groupsNominals.getKey();

			String 	  element = elementGP.get(index);

			boolean isDeterminant  = element.equalsIgnoreCase("Determinant");
			boolean isEpithete     = element.equalsIgnoreCase("épithète");
			boolean isNoyeauNP     = element.equalsIgnoreCase("np");
			boolean isNoyeauNC     = element.equalsIgnoreCase("nc");

			if 		(isDeterminant)            {determinant(index, elementGP, groupIndex, groupIndexEnd);}
			else if (isEpithete)               {epithete(   index, elementGP, groupIndex, groupIndexEnd);}
			else if (isNoyeauNP || isNoyeauNC) {noyeau(     index, elementGP, groupIndex, groupIndexEnd);}
		}
	}
	private int recuperateIndexGroupNominal(Entry<Integer, String> groupsNominals, String[] GP) {

		int end 		  = groupsNominals.getKey();
		int lengthGroup   = GP.length;
		int begeningGroup = (end - lengthGroup) + 1;

		return begeningGroup;
	}




	private void noyeau(int index, ArrayList<String> elementGP, int groupIndex, int groupIndexEnd) {

		String currentSyntaxe = elementGP.get(index);

		boolean isNomPropre = currentSyntaxe.equalsIgnoreCase("Nom propre");
		boolean isNomCommun = currentSyntaxe.equalsIgnoreCase("Nom commun");
		int     indexInSentence = groupIndex + index;

		String outFunction = "Noyeau=" + this.currentText.get(indexInSentence);
		incrementGroupNominalFunction(groupIndexEnd, outFunction);

		//System.out.println("Noyeau du groupe nominal " + this.currentText.get(indexInSentence));
	}

	

	private void determinant(int index,  ArrayList<String> elementGP, int groupIndex, int groupIndexEnd) {
		String outFunction = "determinant=" + this.currentText.get(groupIndex + index);
		incrementGroupNominalFunction(groupIndexEnd, outFunction);

	}




	private void epithete(int index, ArrayList<String> elementGP, int groupIndex, int groupIndexEnd) {

		String outFunction = "epithete=" + this.currentText.get(groupIndex + index);
		incrementGroupNominalFunction(groupIndexEnd, outFunction);

	}



	void groupNominalElementFound(String[] GP,  ArrayList<Integer> complementIndex, ArrayList<String> elementGPList, 
								  String elementGP) {

		for(int index=0; index < GP.length; index++) {
			for (int found: complementIndex) {if (index == found) {elementGPList.set(index, elementGP);}}
		}
	}



	
	

	private void incrementGroupNominalFunction(int indexEnd, String function) {

		boolean dictionnaryIsEmpty = groupNominalFunction.size() == 0;

		if (!dictionnaryIsEmpty) {

			String recupFonction = this.groupNominalFunction.get(indexEnd);
			if (recupFonction == null) {recupFonction = "";}
			String toChange      = recupFonction += ("/" + function);

			this.groupNominalFunction.put(indexEnd, toChange);
		}

		else {this.groupNominalFunction.put(indexEnd, function);}
	}

	
	public void RecuperateGroup(Map<Integer, String> GroupMap, ArrayList<Integer> thisList) {

		String  increment = "";

		for (int index=0; index < thisList.size(); index++) {

			int current = thisList.get(index);

			boolean isFirstElement = index == 0;
			boolean isLastElement  = index == thisList.size() - 1;

			if      ( isFirstElement && !isLastElement)  {increment = isFirstElement(thisList, index, increment);}
			else if (!isFirstElement && !isLastElement)  {increment = isInList(thisList, index, increment);}
			else if (!isFirstElement &&  isLastElement)  {increment = isLastElement(thisList, index, increment);}
			else    { increment = this.currentSyntaxe.get(current).get(0);}


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



	public void RecuperateIndex(String[] groupSyntaxe, ArrayList<Integer> recupIndex, String mode) {

		boolean isEqualMode    = mode.equalsIgnoreCase("equals");
		boolean isContainsMode = mode.equalsIgnoreCase("contains");
		
		for (int index=0; index < this.currentSyntaxe.size(); index ++) {

			ArrayList<String> currentList = this.currentSyntaxe.get(index);
			String word = this.currentText.get(index);

			if (isEqualMode)    {listEqualsIndex(currentList,   groupSyntaxe, recupIndex, index);}
			if (isContainsMode) {listContainsIndex(currentList, groupSyntaxe, recupIndex, index);}
		}
	}
	
	
	
	

	
	private String determinant;
	private String word;
	private int numberSentence;


	public groupNominal(String determinant, String word, int numberSentence, ArrayList<String> currentText, 
						ArrayList<ArrayList<String>> currentSyntaxe) {

		this.determinant = determinant;
		this.word        = word;
		this.numberSentence = numberSentence;
		this.currentText    = currentText;
		this.currentSyntaxe = currentSyntaxe;
	}
	
	
	public String kindOfDeterminant() {

		int     indexWord   = currentText.indexOf(determinant);
		boolean isFound     = indexWord != -1;
		if (isFound) {
			String syntaxeWord = currentSyntaxe.get(indexWord).get(0);
			
			String[] artInd   = {"Article indéfini", "Adjectif indéfini", "Forme d’adjectif indéfini", "Forme d’article indéfini",
							     "Forme d'article indéfini", "Forme d’article défini" };
			String[] artDef   = {"Pronom indéfini", "Forme de pronom indéfini", "pronom défini",
							     "Article défini", "Article défini numéral", "Forme d'article défini", "Adjectif numéral"};
			String[] artPoss  = {"Adjectif possessif", "Forme d'Adjectif possessif", "Forme d’Adjectif possessif"};
			String[] artDems  = {"Adjectif démonstratif","Forme d'Adjectif démonstratif", "Forme d’Adjectif démonstratif",};
			String[] artInt   = {"déterminants interrogatifs", "Adjectif interrogatif"};
			String[] artExcla = {"Adjectif exclamatif"};
			
		
			String[] returnDefinition = {null, null, null, null, null, null};
			
			returnDefinition[0] = searchKindOfDet(artInd,   syntaxeWord, "en général");
			returnDefinition[1] = searchKindOfDet(artDef,   syntaxeWord, "défini");
			returnDefinition[2] = searchKindOfDet(artPoss,  syntaxeWord, "appartenance");
			returnDefinition[3] = searchKindOfDet(artDems,  syntaxeWord, "designation");
			returnDefinition[4] = searchKindOfDet(artInt,   syntaxeWord, "interrogation");
			returnDefinition[5] = searchKindOfDet(artExcla, syntaxeWord, "exclamation");
	
			for (String element: returnDefinition) { if (element != null) { return element; } }
		}
		return null;
	}
	
	private String searchKindOfDet(String[] art, String word, String function) {
		
		for (String article: art) {
			boolean isMatching = article.equalsIgnoreCase(word);
			if (isMatching) { return function; }
		}

		return null;
	}
	
	
	
	
	
}

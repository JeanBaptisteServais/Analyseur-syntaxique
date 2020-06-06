package fr.jbaw.programme2;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;




public abstract class searchingInSentence {


	//COD PARTS
	int[] indexsSyntaxeOfCod(Map<Integer, String> WORKLISTSORTED, int beginFunction, int endFunction) {
		
		//For know if it's a verbal préposition.
		int GprepIndex = localise(WORKLISTSORTED, "Prep", beginFunction);  

		//If a pronom's before verbe, it's cod.
		int verb    = localisationOfIndex(WORKLISTSORTED, beginFunction, endFunction, "vrb");
		int pronom  = localisationOfIndex(WORKLISTSORTED, beginFunction, endFunction, "Prn");
		int nc      = localisationOfIndex(WORKLISTSORTED, beginFunction, endFunction, "Nm");
		if (nc == -1) { nc = localisationOfIndex(WORKLISTSORTED, beginFunction, endFunction, "(Nc)"); }

		int[] indexs = {GprepIndex, verb, pronom, nc};
		return indexs;
	}
	
	boolean localiseSubjetByNextSchema(ArrayList<String> sentencePartInterest, int index) {

		boolean nextIsGv = false;
		boolean lessLengthTwo = index + 1 < sentencePartInterest.size(); //Verify index not hight size.

		if (lessLengthTwo) {
			String   next          = sentencePartInterest.get(index + 1); //Next schema of current.
			String[] nextGroups    = next.split("[+]"); //Split because schema as: Gverbal+0+1.
			String   nextFunction  = nextGroups[0];     //

			nextIsGv = nextFunction.equalsIgnoreCase("GVerbal");
		}
		return nextIsGv;
	}

	boolean[] hasSchemaIncremented(String increment2) {
		
		//If we have already increment for have a very super max beautiful visual.
		boolean sujetAlready         = increment2.contains("(Groupe Sujet)");
		boolean groupNoyeauAlready   = increment2.contains("(Groupe Verbal)");
		boolean cmpltDirectAlready   = increment2.contains("(Complément directe)");
		
		boolean prepSub1  			 = increment2.contains("(Préposition du Groupe Sujet Nominal)");
		boolean prepSub2  			 = increment2.contains("(Préposition du Groupe Sujet Verbal)");
		
		boolean cmpltPrep            = increment2.contains("(Groupe Prépositionel Nominal)");
		boolean prnmDirect           = increment2.contains("(Pronom avec fonction de Complément directe)");
		boolean prepAlr              = increment2.contains("(Groupe Prépositionel Nominal)");
		

		boolean[] increments = {sujetAlready, groupNoyeauAlready, 
							    cmpltDirectAlready, prepSub1, prepSub2, cmpltPrep, prnmDirect, prepAlr};
		
		return increments;
	}

	
	//COD PARTS
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private int localisationOfIndex(Map<Integer, String> wORKLISTSORTED, int beginFunction, int endFunction, String string) {

		string = string.toLowerCase();

		for(Entry<Integer, String> groups: wORKLISTSORTED.entrySet()) {
			int    key   = groups.getKey();
			String value = groups.getValue().toLowerCase();
			
			boolean inRange    = key >= beginFunction && key <= endFunction;
			boolean isMatching = value.contains(string) || string.contains(value);

			if (inRange && isMatching) { return value.indexOf(string); }
			
		}
		return -1;
	}

	private static int localise(Map<Integer, String> WORKLISTSORTED, String what, int beginFunction) {
		
		int     gvIndex     = -1;

		for(Entry<Integer, String> searchGV: WORKLISTSORTED.entrySet()) {

			boolean groupV1  = searchGV.getValue().contains(what) && searchGV.getKey() >= beginFunction;
			boolean groupV2  = searchGV.getValue().contains(what) && searchGV.getKey() >= beginFunction;
			if (groupV1 || groupV2) {gvIndex = searchGV.getKey(); break;}
		}

		return gvIndex;
	}
	
	
	//---------------------------------------------------------------------------------------------------
	
	
	boolean searchPronom(String nextBegin, String nextEnd, ArrayList<ArrayList<String>> currentSyntaxe) {
		
		
		
		int begin = Integer.parseInt(nextBegin);
		int end   = Integer.parseInt(nextEnd);
		
		for (int index=begin; index <= end; index++) {
			
			ArrayList<String> current = currentSyntaxe.get(index);
			
			boolean listContainsPrnm = listEqualsElement(current, "Pronom personnel");
			if (listContainsPrnm) { return true; }
		}
		return false;
	}
	
	
	

	boolean dicoContains(Map<Integer, String> dico, String element) {
		
		boolean isMatching = false;
		
		for(Entry<Integer, String> el: dico.entrySet()) {
			
			boolean contains = el.getValue().contains(element);
			
			if (!isMatching && contains) {isMatching = true;}
		}
		return isMatching;
	}
	
	boolean areElementBefore(ArrayList<Boolean> list, int index) {
		
		boolean out = true;
		for (int el=0; el < index; el++) {
			boolean element = list.get(el);
			
			if (!element) { out = false; }
			
		}
		return out;
	}
	
	
	boolean listContains(ArrayList<String> List, String elementToMatch) {

		boolean isEqual = false; 

		for (String element: List) {if (element.contains(elementToMatch) && !isEqual) {isEqual = true;}}
		return isEqual;
	}


	boolean MoreThanOne(ArrayList<String> List, String elementToMatch) {

		int howMany = 0; 

		for (String element: List) {if (element.contains(elementToMatch)) {howMany += 1;}}
		
		if (howMany >= 2) { return true; } 
		return false;
	}
	

	int howListContains(ArrayList<String> List, String elementToMatch) {

		int howMany = 0; 

		for (String element: List) {if (element.contains(elementToMatch)) {howMany += 1;}}
		return howMany;
	}


	

	boolean thisListContains(ArrayList<String> List, String[] thisList) {

		boolean isEqual = false;

		for (String element1: List) {
			for(String element2: thisList) {
				if (element1.contains(element2) && !isEqual) {isEqual = true;}
			}
		}
		return isEqual;
	}

	
	void listContainsIndex(ArrayList<String> List, String[] elementToMatch, ArrayList<Integer> recuperate, int index) {

		for (String ListElement: List) {
			for (String match: elementToMatch) {if (ListElement.contains(match)) {recuperate.add(index); break;}}
		}
	}
	
	

	boolean listEqualsElement(ArrayList<String> List, String elementToMatch) {

		boolean contains = false;

		for (String element: List) {if (element.equalsIgnoreCase(elementToMatch) && !contains) {contains = true;}}
		return contains;
	}
	

	boolean listEquals(ArrayList<String> List, String[] elementToMatch) {

		boolean contains = false;

		boolean isNotNull = elementToMatch != null;
		
		if (isNotNull) {
		
			for (String element: List) {
				for (String match: elementToMatch) {
					if (element.equalsIgnoreCase(match) && !contains) {contains = true;}
				}
			}
		}
		return contains;
	}

	
	void listEqualsIndex(ArrayList<String> List, String[] elementToMatch, ArrayList<Integer> recuperate, int index) {

		for (String ListElement: List) {
			for (String match: elementToMatch) {if (ListElement.equalsIgnoreCase(match)) {recuperate.add(index); break;}}
		}
	}

	
	
	
	void thisListEqualsThisListIndex(String[] thisList1, String[] thisList2, ArrayList<Integer> recuperate, String mode) {

		boolean modeIsEqual    = mode.equalsIgnoreCase("equal");
		boolean modeIsContains = mode.equalsIgnoreCase("contains");

		for(int index=0; index < thisList1.length; index++) {

			String wordThisList = thisList1[index];

			for (String toMatch: thisList2) {

				boolean isEqual    = wordThisList.equalsIgnoreCase(toMatch);
				boolean isContains = wordThisList.contains(toMatch);

				if (modeIsEqual && isEqual) 	  {recuperate.add(index);}
				if (modeIsContains && isContains) {recuperate.add(index);}
			}
		}
	}


	void thisListEqualsIndex(String[] thisList, String elementToMatch, ArrayList<Integer> recuperate, String mode) {

		boolean modeIsEqual    = mode.equalsIgnoreCase("equal");
		boolean modeIsContains = mode.equalsIgnoreCase("contains");

		for(int index=0; index < thisList.length; index++) {

			String wordThisList = thisList[index];

			boolean isEqual    = wordThisList.equalsIgnoreCase(elementToMatch);
			boolean isContains = wordThisList.contains(elementToMatch);

			if (modeIsEqual && isEqual) 	  {recuperate.add(index);}
			if (modeIsContains && isContains) {recuperate.add(index);}
			
		}
	}
	


	boolean TableauEquals(String[] List, String elementToMatch) {
		boolean contains = false;

		for (String element: List) {if (element.equalsIgnoreCase(elementToMatch) && !contains) {contains = true;}}
		return contains;
	}
	
	

	boolean thisListEqualList(ArrayList<String> List, String[] thisList) {

		boolean isEqual = false;

		for (String element1: List) {
			for(String element2: thisList) {
				if (element1.equalsIgnoreCase(element2) && !isEqual) {isEqual = true;}
			}
		}
		return isEqual;
	}
	

	
	String thisListEqualListRecup(ArrayList<String> List, String[] thisList) {

		boolean isEqual = false;
		String  syntaxe = "";

		for (String element1: List) {
			for(String element2: thisList) {
				if (element1.equalsIgnoreCase(element2) && !isEqual) {
					isEqual = true;
					syntaxe = element1;
				}
			}
		}
		return syntaxe;
	}
	


	String listContainsRecuperate(ArrayList<String> List, String elementToMatch) {

		String recuperate = ""; 
		
		for (String element: List) {if (element.contains(elementToMatch)) {recuperate = element;}}
		return recuperate;
	}
	
	String listEqualRecuperate(ArrayList<String> List, String elementToMatch) {

		String recuperate = ""; 
		
		for (String element: List) {if (element.equalsIgnoreCase(elementToMatch)) {recuperate = element;}}
		return recuperate;
	}
	
	boolean thisListEqualWord(String[] thisList, String word) {

		boolean isEqual = false;

		for (String element: thisList) {if (element.equalsIgnoreCase(word) && !isEqual) {isEqual = true;}}
		return isEqual;
	}
	
	
	boolean thisListContainsWord(String[] thisList, String word) {

		boolean isEqual = false;

		for (String element: thisList) {if (element.contains(word) && !isEqual) {isEqual = true;}}
		return isEqual;
	}
	
	boolean thisListContainsWordTwoCase(String[] thisList, String word) {

		boolean isEqual = false;

		for (String element: thisList) {if ((element.contains(word) || word.contains(element)) && !isEqual) {isEqual = true;}}
		return isEqual;
	}
	
	String wordContainsThisListRecuperate(String word, String[] thisList) {
		String recuperate = "";

		for (String element: thisList) {if (word.contains(element)) {recuperate = element;}}
		return recuperate;
	}
	
	String thisContainsEqualRecupString(String[] thisList, String word, String mode) {

		String wordSyntaxe = "";

		boolean modeIsEqual    = mode.equalsIgnoreCase("equal");
		boolean modeIsContains = mode.equalsIgnoreCase("contains");

		if (modeIsEqual) 		{for (String element: thisList) {if (element.equalsIgnoreCase(word)) {wordSyntaxe = element;}}}
		else if(modeIsContains) {for (String element: thisList) {if (element.contains(word)) {wordSyntaxe = element;}}}

		return wordSyntaxe;
	}
	
	
	String thisContainsEqualRecupOtherList(String[] thisList, String[] word, String mode) {

		String wordSyntaxe = "";

		boolean modeIsEqual    = mode.equalsIgnoreCase("equal");
		boolean modeIsContains = mode.equalsIgnoreCase("contains");

		if (modeIsEqual) 		{
			for (String element: thisList) {
				for (String match: word) { if (element.equalsIgnoreCase(match)) {wordSyntaxe = element;} break;}
			}
		}

		else if(modeIsContains) {
			for (String element: thisList) {
				for (String match: word) {if (element.contains(match)) {wordSyntaxe = element;} break;}
			}
		}
		return wordSyntaxe;
	}
	
	


	void removeFromList(ArrayList<String> List, String element) {

		Iterator<String> i = List.iterator();
		while (i.hasNext()) {
			String str = (String) i.next();
			if (str.equals(element)) {i.remove();}
		}
	}

	

	void removeTableauAndRecupIndex(ArrayList<String> List, String[] elementTableau, String mode,
											ArrayList<Integer> indexPonctuation, int indexageList) {

		for (int index = 0; index < List.size(); index++) {

			String sytnaxe = List.get(index);

			switch(mode) {

			case "equals":
				for (String element: elementTableau) {if (sytnaxe.equals(element))   {indexPonctuation.add(indexageList);}} break;

			case "contains":
				for (String element: elementTableau) {if (sytnaxe.contains(element)) {indexPonctuation.add(indexageList);}} break;
			}
		}
	}


	void removeFromListFromList(ArrayList<String> List, String[] elements) {

		Iterator<String> i = List.iterator();
		while (i.hasNext()) {
			
			String str = (String) i.next();
			boolean isEqual = false;
			
			for (String element: elements) {
				if (element.equalsIgnoreCase(str) && !isEqual) {
					isEqual = true;
				}
			}
			if (isEqual) {i.remove();}
		}
	}
	
	

	void removeFromListContains(ArrayList<String> List, String element) {

		Iterator<String> i = List.iterator();
		while (i.hasNext()) {
			String str = (String) i.next();
			if (str.contains(element)) {i.remove();}
		}
	}
	

	
	void dontRemoveFromThisList(ArrayList<String> List, String[] elements) {

		Iterator<String> i = List.iterator();

		while (i.hasNext()) {

			String str = (String) i.next();

			boolean isEquals = false;
			for (String el: elements) {if (str.contains(el) && !isEquals) {isEquals = true;}}
			if (!isEquals) {i.remove();}
		}
	}
	


	void dontRemoveFromList(ArrayList<String> List, String[] elements) {

		Iterator<String> i = List.iterator();

		while (i.hasNext()) {
			String str = (String) i.next();

			boolean isEquals = false;

			for (String el: elements) {if (str.equalsIgnoreCase(el) && !isEquals) {isEquals = true;}}
			if (!isEquals) {i.remove();}
		}
	}
	
	

	void dontRemoveFromListElement(ArrayList<String> List, String element) {

		Iterator<String> i = List.iterator();

		while (i.hasNext()) {

			String str = (String) i.next();
			if (!str.equals(element)) {i.remove();}
		}
	}
	
	

	void dontRemoveFromListContains(ArrayList<String> List, String element) {

		Iterator<String> i = List.iterator();
		while (i.hasNext()) {
			String str = (String) i.next();
			if (!str.contains(element)) {i.remove();}
		}
	}
	

	boolean whileNotIndexEquals(ArrayList<ArrayList<String>> currentSyntaxe, String word, int indexBegening, int indexEnd) {
		
		boolean isEqual = false;

		for (int elementBefore = indexBegening; elementBefore < indexEnd - 1; elementBefore++) {

			ArrayList<String> List = currentSyntaxe.get(elementBefore);
			for (String element: List) {if (element.equalsIgnoreCase(word) && !isEqual) {isEqual = true;}}
		}
		return isEqual;
	}
	

	int whileNotIndexEqualsRecupIndex(ArrayList<ArrayList<String>> currentSyntaxe, String word, int indexBegening, int indexEnd) {
		
		int indexage = -1;

		for (int elementBefore = indexBegening; elementBefore < indexEnd - 1; elementBefore++) {

			ArrayList<String> List = currentSyntaxe.get(elementBefore);
			for (String element: List) {if (element.equalsIgnoreCase(word)) {indexage = elementBefore;}}
		}
		return indexage;
	}
	
	int whileNotIndexContainsRecupIndex(ArrayList<ArrayList<String>> currentSyntaxe, String word, int indexBegening, int indexEnd) {
		
		int indexage = -1;

		for (int elementBefore = indexBegening; elementBefore < indexEnd - 1; elementBefore++) {

			ArrayList<String> List = currentSyntaxe.get(elementBefore);
			for (String element: List) {if (element.contains(word)) {indexage = elementBefore;}}
		}
		return indexage;
	}
	

	
	boolean whileNotIndexContains(ArrayList<ArrayList<String>> currentSyntaxe, String word, int indexBegening, int indexEnd) {
		

		boolean isEqual = false;

		for (int elementBefore = indexBegening; elementBefore < indexEnd - 1; elementBefore++) {

			ArrayList<String> List = currentSyntaxe.get(elementBefore);
			for (String element: List) {if (element.contains(word) && !isEqual) {isEqual = true;}}
		}
		return isEqual;
	}
	
	

	void otherElementMatch(ArrayList<ArrayList<String>> currentSyntaxe, String word, ArrayList<String> recup) {

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			for (String syn: currentList) {if (syn.contains(word) && currentList.size() == 1) {recup.add(syn);}}
		}
	}
	
	
	
	//--------------------------------------------------------------------------------------------------------
	
	
	int whileNotIndexEqualsRecupFirstIndexDesincrement(ArrayList<String> List, 
			   String match, int indexBegening) {

		int indexage = 0;

		for (int index=indexBegening; index >= 0; index--) {

			String elementList = List.get(index);

			boolean isEqual = elementList.equalsIgnoreCase(match);
			if (isEqual) {return indexBegening - index;}
		}
		return indexage;
	}


	int whileNotIndexEqualsRecupFirstIndexIncrement(ArrayList<String> List, 
				String match, int indexBegening) {
	
		int indexage = 0;
	
		for (int index=indexBegening; index < List.size() - indexBegening; index++) {
	
			String elementList = List.get(index);
			boolean isEqual = elementList.equalsIgnoreCase(match);
			if (isEqual) {return index;}
		}
		return indexage;
	}
	
	
	String searchInDictionnary(Map<String, String> dictionnary, String element) {
		
		String found = "";
		
		return found;
	}
	
	
	
	
	
	
	
	
}

package fr.jbaw.programme2.identificationOfAction;

import java.util.ArrayList;

public abstract class utilsAction {

	
	
	private String[] PRONOMDESMONSTRATIFLAST = {"cela",};
	private String[] DEMONSTRATIF 			 = {"ce", "contraction=ce", "cela"};

	
	
	

	protected String incrementData(String[] actionCmplt) {

		String increment = "";
		for (String element: actionCmplt) {
			boolean notEmpty = element != "";
			if (notEmpty) { increment += element + " "; }
		}

		boolean incrementNotEmpty = increment != "";
		if (incrementNotEmpty) {
			increment = increment.substring(0, increment.length() - 1);
		}
		return increment;
	}
	
	
	 void recuperateInformation(ArrayList<String> containerData, ArrayList<String> recuperateData, String data) {

		 recuperateData.clear();

		 for (String element: containerData) {

			 String   function = "";
			 String   word     = "";
			 
			 String[] elementSplit = element.split("=");
			 boolean notEmptyEqual = elementSplit.length > 1;
			 if (notEmptyEqual) {
				 function = elementSplit[0];
				 word     = elementSplit[1];
			 }

			 String[] elementSplit2 = element.split("[:]");
			 boolean notEmptyTwoPoints = elementSplit2.length > 1;
			 if (notEmptyTwoPoints) {
				 function = elementSplit2[0];
				 word     = elementSplit2[1];
			 }

			 boolean isElementSearch = function.equalsIgnoreCase(data);
			 if     (isElementSearch) { recuperateData.add(word); }
		 }

	 }
	
	
	
	protected void DemGeneralite(String generalite, ArrayList<String> dataSubject) {

		boolean actionContainsDemons = thisListEqualWord(DEMONSTRATIF, generalite);
		if (actionContainsDemons) { dataSubject.clear(); dataSubject.add("en généralité"); }
	}
	
	
	
	
	protected void DemPasrElement(String generalite, ArrayList<String> dataSubject, ArrayList<ArrayList<String>> saveSentence) {
			

		boolean actionContainsDemons = thisListEqualWord(PRONOMDESMONSTRATIFLAST, generalite);
		String increment = "";
		if (actionContainsDemons) { 
			for (ArrayList<String> element: saveSentence) { increment += (element.get(0) + " "); }
			
			boolean notLastGn = increment.equalsIgnoreCase("");

			if      (!notLastGn) { dataSubject.clear(); dataSubject.add(increment); }
			else if ( notLastGn) { dataSubject.clear(); dataSubject.add("dans le passé"); }
		}	
	}


	protected void recuperateFromVerbInText(String increment, ArrayList<ArrayList<String>> partOfSentence) {

		String[] incrementSplit = increment.split("[ ]");
		boolean  notEmpty       = incrementSplit.length >=  1;

		if (notEmpty) { String firstWord = incrementSplit[0]; }
	}
	

	static boolean thisListEqualWord(String[] thisList, String word) {

		boolean isEqual = false;

		for (String element: thisList) {if (element.equalsIgnoreCase(word) && !isEqual) {isEqual = true;}}
		return isEqual;
	}
	
	
}

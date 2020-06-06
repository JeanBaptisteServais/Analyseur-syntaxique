package fr.jbaw.programme2.incrementAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import fr.jbaw.programme2.searchWordUtils;


public class incrementAction extends searchWordUtils{

	
	private String[] PRONOM = {"je", "tu", "il", "nous", "vous", "ils", "elle", "on", "elles"};

	public incrementAction(ArrayList<String> dataSubject, ArrayList<String> dataAction, 
			ArrayList<String> dataActionCmplt, ArrayList<String> dataActionGn, Map<String, ArrayList<String>> quoi, 
			ArrayList<ArrayList<String>> partOfSentence, 
			int inSentence, boolean needIncrementSentence, ArrayList<String> currentText) {


		String    subject    = dataSubject.get(0);
		boolean[] conditions = conditionForPassIncrementation(subject, quoi);                             //1)
		boolean   SNotEmpty  = conditions[0]; 
		boolean   keyExists  = conditions[1]; 

		if (SNotEmpty) {

			ArrayList<String> containerAction = new ArrayList<String>();
			String  increment = "";
			String  cmplt     = "";
			
			increment = incrementDataAction(dataAction, increment, cmplt);                                //2)



			increment = incrementData(dataActionCmplt, increment);									      //4)
			increment = incrementData(dataActionGn, increment);


			increment = removeSpaces1(increment);														  //5)
			increment = removeSpaces2(increment);                                                         //6)

			increment = incrementEmpty(increment, needIncrementSentence, currentText);					  //7)

			containerAction.add(increment);

			if      (!keyExists) { quoi.put(subject, containerAction); }
			else if ( keyExists) { addToDictionnaryQuoi(quoi, subject, containerAction); }                //8)
		}
	}



	
	

	//8)
	private void addToDictionnaryQuoi(Map<String, ArrayList<String>> quoi, String subject, ArrayList<String> containerAction) {

		ArrayList<String> data = quoi.get(subject);
		removeFromList(data, "");
		for (String element: containerAction) { data.add(element); }
		quoi.put(subject, data);
	}

	
	
	private String incrementEmpty(String increment, boolean needIncrementSentence, ArrayList<String> currentText) {

		String actualIncrement = increment;
		
		if (needIncrementSentence) {
			increment = increment + " ";
			for (String el: currentText) { increment += (el + " "); }
			increment = increment.substring(0, increment.length());
			
			boolean isContains = actualIncrement.contains(increment) ||
								 increment.contains(actualIncrement);
			
			if (!isContains){ return increment; }

		}

		return actualIncrement;
	}
	

	
	//6)
	private String removeSpaces2(String increment) {

		ArrayList<Integer> indexToremove = new ArrayList<Integer>();

		for (int index=increment.length() - 1; index > 0; index--) {
			String  space   = Character.toString(increment.charAt(index));
			boolean isSpace = space.equalsIgnoreCase(" ");
			if (isSpace) { indexToremove.add(index); }
			else { break; }
		}

		boolean notEmpty = indexToremove.size() > 0;
		if (notEmpty) {
			int minimum = Collections.min(indexToremove);
			increment = increment.substring(0, minimum);
		}

		return increment;
	}
	
	

	//5)
	private String removeSpaces1(String increment) {

		ArrayList<Integer> indexToremove = new ArrayList<Integer>();

		for (int index=0; index < increment.length(); index++) {
			String  space   = Character.toString(increment.charAt(index));
			boolean isSpace = space.equalsIgnoreCase(" ");
			if (isSpace) { indexToremove.add(index); }
			else { break; }
		}

		boolean notEmpty = indexToremove.size() > 0;
		if (notEmpty) {
			int maximum = Collections.max(indexToremove);
			increment = increment.substring(maximum + 1, increment.length());
		}
		return increment;
	}
	
	
	
	
	//4)
	private String incrementData(ArrayList<String> data, String increment) {
		boolean dataNotEmpty = data.size() > 0;

		if (dataNotEmpty) {
			for (String element: data) { increment += element + " "; }
			return increment.substring(0, increment.length() - 1);

		} else { return increment; }
	}





	//2)
	private String incrementDataAction(ArrayList<String> dataAction, String increment, String cmplt) {

		boolean haveToIncrement = false;

		for (String element: dataAction) {
			boolean isCmplt = element.contains("=");
			if  (isCmplt) { cmplt = element; haveToIncrement = true; } else { increment += element + " "; }
		}
		if (haveToIncrement) { increment += " " + cmplt + " "; }

		return increment;
	}



	//1)
	boolean[] conditionForPassIncrementation(String subject, Map<String, ArrayList<String>> quoi) {

		boolean SNotEmpty = !subject.equalsIgnoreCase(""); 
		boolean keyExists = quoi.get(subject) != null;
		
		boolean[] conditions = {SNotEmpty, keyExists};

		return conditions;
	}
	
	
	
}

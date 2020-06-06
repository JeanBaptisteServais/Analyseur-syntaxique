package fr.jbaw.programme2.discoursTreatment;

import java.util.ArrayList;
import java.util.Map;

import fr.jbaw.programme2.searchWordUtils;

public class discoursTreatment extends searchWordUtils{

	
	private Map<String, ArrayList<String>> discours;
	private int 						   numberSentence;
	private ArrayList<String> 			   currentText;
	
	
	private String[] guillemetOuvrante = {"«"};
	private String[] guillemetFermante = {"»"};

	

	public discoursTreatment(Map<String, ArrayList<String>> discours, int numberSentence, ArrayList<String> currentText) {
		this.discours       = discours;
		this.numberSentence = numberSentence;
		this.currentText    = currentText;
	}


	public void isGuillemet(ArrayList<String> containerText) {

		//If dicours in the sentence. Put into discours dictionnary true and increment the discours.
		//Open discours  = find guillemetOuvrante1.
		//Closed dicours = find guillemetOuvrante2.
		//If closed put the number of the sentence.
	


		boolean increment = discours.get("true") != null;

		increment = searchGuillement(increment, containerText);

		incrementDiscoursDico(increment, containerText, discours);

	}
	
	
	
	private void incrementDiscoursDico(boolean increment, ArrayList<String> containerText, Map<String, ArrayList<String>> discours) {
		
		if (increment) { 
			boolean haveElement = discours.get("true") != null;
			ArrayList<String> recup = new ArrayList<String>();
			if (haveElement) { recup = discours.get("true"); }

			for (String element: containerText) { recup.add(element); }
			discours.put("true", recup); 
		}
	}
	
	
	private boolean searchGuillement(boolean increment, ArrayList<String> containerText) { //1)

		boolean isGuillemet1    = false;
		boolean isGuillemet2    = false;

		for (String word: currentText) {

			isGuillemet1 = thisListEqualWord(guillemetOuvrante, word);
			isGuillemet2 = thisListEqualWord(guillemetFermante, word);

			if (isGuillemet1) { increment = true; }

			if (increment)    { containerText.add(word); }

			if (isGuillemet2) { 

				ArrayList<String> recup = new ArrayList<String>();
				boolean isOnSameLine = discours.get("true") == null;
				if (!isOnSameLine) { recup = discours.remove("true"); }

				
				for (String element: containerText) { recup.add(element); }
				discours.put(Integer.toString(numberSentence), recup);

				containerText.clear(); increment = false;
			}
		}
		return increment;
	}
}

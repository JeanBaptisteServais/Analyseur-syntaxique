package fr.jbaw.programme2.incrementSubject;

import java.util.ArrayList;
import java.util.Arrays;

import fr.jbaw.programme2.searchWordUtils;

public class incrementSubject extends searchWordUtils{

	private ArrayList<String> 			 container;
	private ArrayList<String>			 containerData;
	private ArrayList<ArrayList<String>> partOfSentence;
	private int 						 inSentence;


	public incrementSubject(ArrayList<String> container, ArrayList<String> containerData) {
	
		this.container      = container;
		this.containerData  = containerData;
		this.partOfSentence = partOfSentence;
		this.inSentence     = inSentence;
		
		
		String[] groupSujet = {"detPreci=", "qui=", "precisionQui="};

		for (String data: container) {

			boolean[] conditons = intraCondition(data);
			boolean   isArt     = conditons[0];
			boolean   isNc      = conditons[1];
			boolean   isAdj     = conditons[2];

			String[] dataInfo = treatementDataEqual(data);
			
			if      (isArt) { groupSujet[0] += dataInfo[1]; groupSujet[1] += dataInfo[1] + " ";}
			else if (isNc)  { groupSujet[1] += dataInfo[1] + " "; }
			else if (isAdj) { groupSujet[2] += dataInfo[1]; groupSujet[1] += dataInfo[1] + " ";}

		}
		
		addDataInContainer(groupSujet);
	}



	
	

	//3)
	private void addDataInContainer(String[] groupSujet) {


		for (String element: groupSujet) { 

			String  lastLetter = Character.toString(element.charAt(element.length() - 1));
			boolean lastCharacterIsSpace = lastLetter.equalsIgnoreCase(" ");
			if (lastCharacterIsSpace) { element = element.substring(0, element.length() - 1); }

			containerData.add(element);
		}
	}
	

	//1)
	private boolean[] intraCondition(String data) {

		boolean isArt = data.contains("determinant");
		boolean isNc  = data.contains("Noyeau");
		boolean isAdj = data.contains("epithete");
	
	
		boolean[] conditons = {isArt, isNc, isAdj};
		return    conditons;
	}
	
	
	
}

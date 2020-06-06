package fr.jbaw.programme2;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class ConjonctionDeCoordination extends searchingInSentence{


	private ArrayList<ArrayList<String>> currentSyntaxe;
	private ArrayList<String>            currentText;
	private Map<Integer, String>         conjonctionLocalisation;

	

	
	public ConjonctionDeCoordination(ArrayList<ArrayList<String>> currentSyntaxe,
									 ArrayList<String> 			  currentText, 
									 Map<Integer, String> 		  conjonctionLocalisation) {
		
		
		this.currentSyntaxe 		 = currentSyntaxe;
		this.currentText    		 = currentText;
		this.conjonctionLocalisation = conjonctionLocalisation;
		
		localiseConjonction();


	}



	

	public String[] etCase(ArrayList<String> groupsSentence, int index) {
		
		//System.out.println(groupsSentence);
		//System.out.println(index);
		
		String   increment       = "";
		String   mode            = "";

		String[] last            = groupsSentence.get(index - 1).split("[+]");
		String   lastFunction    = last[0];
		String   beginLast       = last[1];
		
		String[] current         = groupsSentence.get(index).split("[+]");
		String   currentFunction = current[0];
		String   indexCurrent    = current[1];
		
		String[] next            = groupsSentence.get(index + 1).split("[+]");
		String   nextFunction    = next[0];
		String   endNext         = next[2];

		
		boolean elementsAreSame  = lastFunction.equalsIgnoreCase(nextFunction);

		boolean isVerbe  		 = lastFunction.equalsIgnoreCase("[GVerbal");
		boolean isAdverb 		 = nextFunction.equalsIgnoreCase("[GAdverb");

		
		//[GPrep+18+19], [Gcnjonc+20+20], [GPrep+21+22]
		if (elementsAreSame)     {increment += nextFunction + "+" + indexCurrent + "+" + endNext + "]"; mode = "same";}

		//[GVerbal+0+3], [Gcnjonc+4+4], [GAdverb+5+5]
		if (isVerbe && isAdverb) {increment += lastFunction + "+" + beginLast + "+" + endNext + "]";    mode = "verbAdverb";}
		
		String[] out = {increment, mode};
		return out;
	}


	
	public void localiseConjonction() {

		for (int syntaxe = 0; syntaxe < this.currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList = this.currentSyntaxe.get(syntaxe);
			String word                   = this.currentText.get(syntaxe);

			boolean isConjonction1 = listEqualsElement(currentList, "Conjonction de coordination");


			if (isConjonction1) {conjonctionLocalisation.put(syntaxe, word);}
		}	
	}
	
}

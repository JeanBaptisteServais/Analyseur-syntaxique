package fr.jbaw.programme2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class groupePropositionRelative {

	
	
	
	
	
	
	private ArrayList<ArrayList<String>> currentSyntaxe;
	private ArrayList<String> 			 currentText;
	private Map<Integer, String> 		 prepoFunction;




	public groupePropositionRelative(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText,
									 Map<Integer, String> prepoFunction) {
		
		this.currentSyntaxe  = currentSyntaxe;
		this.currentText     = currentText;
		this.prepoFunction = prepoFunction;
		
		
		findPreposition();

	}



	public void findPreposition(){

		for (int syntaxe = 0; syntaxe < this.currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList = this.currentSyntaxe.get(syntaxe);
			String word                   = this.currentText.get(syntaxe);

			boolean isPreposition = currentList.get(0).equalsIgnoreCase("Pronom relatif") ||
									currentList.get(0).equalsIgnoreCase("Pronom interrogatif");

			if (isPreposition) {this.prepoFunction.put(syntaxe, word);}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void subordon�e(int index, ArrayList<String> elementGP, String function) {

		Map<String, String> subordon�eMap = new HashMap<String, String>();
		incrementSubordon�eMap(subordon�eMap);


		for (int from=index; from < elementGP.size(); from++) {
			String elementFunction = elementGP.get(index);

			boolean nextIsNothing = false;
			boolean isQui 		  = false;
			boolean isQue         = false;
			boolean isOu   		  = false;
			boolean isDont 		  = false;

			elementGP.set(from, function);
		}
	}
	private void incrementSubordon�eMap(Map<String, String> subordon�eMap) {
		//apres + avant						  
		subordon�eMap.put("qui",  "np/verbe/np+");
		subordon�eMap.put("que",  "ou/nc+");
		subordon�eMap.put("ou",   "verbe/np/Adverbe+");
		subordon�eMap.put("dont", "nc/np/contraction/pronom personnel+np");
	}
	
	
	
	
	
	
	
}

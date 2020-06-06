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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void subordonée(int index, ArrayList<String> elementGP, String function) {

		Map<String, String> subordonéeMap = new HashMap<String, String>();
		incrementSubordonéeMap(subordonéeMap);


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
	private void incrementSubordonéeMap(Map<String, String> subordonéeMap) {
		//apres + avant						  
		subordonéeMap.put("qui",  "np/verbe/np+");
		subordonéeMap.put("que",  "ou/nc+");
		subordonéeMap.put("ou",   "verbe/np/Adverbe+");
		subordonéeMap.put("dont", "nc/np/contraction/pronom personnel+np");
	}
	
	
	
	
	
	
	
}

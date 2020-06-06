package fr.jbaw.programme2.complementAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import fr.jbaw.programme2.searchWordUtils;

public class complementAction extends searchWordUtils{

	private ArrayList<String> 			 dataActionCmplt;
	private ArrayList<String>		     dataAction;
	private ArrayList<ArrayList<String>> partOfSentence;
	private int 						 inSentence;
	private ArrayList<String> 			 currentText;
	private Map<int[], String> 			 cmpltDico;
	private boolean 					 haveAction;


	

	

	public complementAction(ArrayList<String> dataActionCmplt, ArrayList<String> dataAction,
							ArrayList<ArrayList<String>> partOfSentence, int inSentence,
							ArrayList<String> currentText, Map<int[], String> cmpltDico, boolean haveAction) {



		this.dataActionCmplt = dataActionCmplt;
		this.dataAction      = dataAction;
		this.partOfSentence  = partOfSentence;
		this.inSentence      = inSentence;
		this.currentText     = currentText;
		this.cmpltDico       = cmpltDico;
		this.haveAction      = haveAction;

		ArrayList<Integer> cmplts      = new ArrayList<Integer>();
		ArrayList<String>  currentPart = partOfSentence.get(inSentence);

	
		incrementDictionary(currentPart, cmplts);
	
		if (haveAction) {

			Map<Integer, String> cmpltIncrement = new HashMap<>();
			recuperateText(cmpltDico, currentText, cmpltIncrement);
	
			
			Map<Integer, String> sortedCmpltIncrement = new TreeMap<Integer, String>(cmpltIncrement);
			incrementDataActionCmplt(dataActionCmplt, sortedCmpltIncrement);
	
			System.out.println("\n\n");
		}
	}

	private void incrementDictionary(ArrayList<String> currentPart, ArrayList<Integer> cmplts) {
		
		recuperateAllIndexFromGroupInList(currentPart, "GCMPLT", cmplts);
		recuperateAllIndexFromGroupInList(currentPart, "GPREP", cmplts);
		recuperateAllIndexFromGroupInList(currentPart, "GNOMINAL", cmplts);
		
		recuperateIndexs(cmplts, cmpltDico);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void incrementDataActionCmplt(ArrayList<String> dataActionCmplt, Map<Integer, String> sortedCmpltIncrement) {

		String increment = "";
		for(Entry<Integer, String> groups: sortedCmpltIncrement.entrySet()) {

			int    key  = groups.getKey();
			String text = groups.getValue();
			
			//System.out.println(key + " " + text);
			
			increment += (text + " ");

		}
		
		boolean isIncrement = !(increment.equals(""));
		if (isIncrement) { increment = increment.substring(0, increment.length() - 1); }

		dataActionCmplt.add(increment);
	}



	private void recuperateText(Map<int[], String> cmpltDico, ArrayList<String> currentText2, Map<Integer, String> cmpltIncrement) {
	
		for(Entry<int[], String> groups: cmpltDico.entrySet()) {
			int[] key = groups.getKey();

			List<String> text = currentText2.subList(key[0], key[1]);
			String increment = ""; for (String word: text) { increment += (word + " "); }
			
			boolean isIncrement = !(increment.equals(""));
			if (isIncrement) { increment = increment.substring(0, increment.length() - 1); }

			cmpltIncrement.put(key[1], increment);

		}
	}

	
	private void recuperateIndexs(ArrayList<Integer> cmplts, Map<int[], String> cmpltDico) {

		System.out.println("\n\nidentifiateCmpltAction - complementAction - recuperateIndexs");
		System.out.println("there are: " + cmplts.size() / 2 + " Cmplts");

		for (int index=0; index < cmplts.size(); index+=2) {

			int begin = cmplts.get(index);
			int end   = cmplts.get(index + 1) + 1;

			int[] indexs = {begin, end};
			
			cmpltDico.put(indexs, "");
		}
	}
	

	
	

}

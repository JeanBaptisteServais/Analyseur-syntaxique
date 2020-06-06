package fr.jbaw.programme2.haveIncrementedByGprepPrep;

import java.util.ArrayList;
import java.util.List;

import fr.jbaw.programme2.searchWordUtils;

public class haveIncrementedByGprepPrep extends searchWordUtils{

	
	//We have schemas of proposition as: 0+1+Gverbal.
	// 0 -> begin; 1 -> end; Gverbal -> function.
	//je mange du poulet.


	private ArrayList<ArrayList<String>> partOfSentence;
	private ArrayList<String> 			 GROUPS;
	private int 						 begening;
	private int 						 end;
	private ArrayList<ArrayList<String>> currentSyntaxe;
	private ArrayList<String> 			 currentText;
	private ArrayList<String> 			 containerDataGroupSujet;


	
	public haveIncrementedByGprepPrep(ArrayList<ArrayList<String>> partOfSentence, ArrayList<String> GROUPS, int begening, int end, 
			ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText, ArrayList<String> containerDataGroupSujet) {
		
		this.partOfSentence 		 = partOfSentence;
		this.GROUPS 				 = GROUPS;
		this.begening 				 = begening;
		this.end 					 = begening;
		this.currentSyntaxe 		 = currentSyntaxe;
		this.currentText 			 = currentText;
		this.containerDataGroupSujet = containerDataGroupSujet;
	}
	


	public boolean subjectIsAGprep() {

		boolean thereIsntGs                = ifGroupContains(partOfSentence, "GSUJET");
		boolean haveIncrementedByGprepPrep = false;


		if (thereIsntGs) {

			for (int index=1; index < GROUPS.size(); index++) {
				

				String[] last = recuperateLastSchema(GROUPS, index);
				String   lastFunction   = last[0];
				int      lastGroupBegin = Integer.parseInt(last[1]);
				int      lastGroupEnd   = Integer.parseInt(last[2]);

				String[] current = recuperateCurrentSchema(GROUPS, index);
				String   currentFunction   = current[0]; 
				int      currentBegin      = Integer.parseInt(current[1]);
				int      currentGroupEnd   = Integer.parseInt(current[2]);


				boolean lastGpIsOnlyPrep = (lastGroupBegin - lastGroupEnd == 0);
				boolean lastGprep      	 = lastFunction.contains("GPrep");
				boolean currentGv   	 = currentFunction.contains("GVerbal");

				if (lastGprep && currentGv) {
					boolean inRange = currentBegin >= begening && currentGroupEnd <= end;
					if (inRange) {

						List<String> partText = new ArrayList<String>();
						String       text     = "qui=";

						if ( lastGpIsOnlyPrep) { 
							text += searchFirstLastNcFromGPrep(currentSyntaxe, currentText, lastGroupBegin); 
						}

						else if (!lastGpIsOnlyPrep) { 
							partText = currentText.subList(lastGroupBegin, lastGroupEnd + 1);   
							for (String element: partText) { text += (element + " "); }
						}


						boolean haveText = !text.equalsIgnoreCase(text);

						if (haveText) { 
						
							containerDataGroupSujet.add(text);
							System.out.println("isGroupPrepPrep - subjectIsAGprep " + text);
							
							haveIncrementedByGprepPrep = true;
						}
					}
				}
			}
		}
		return haveIncrementedByGprepPrep;
	}

	
	
	

	
	private String[] recuperateLastSchema(ArrayList<String> GROUPS, int index) {

		String[] lastGroupSplit = GROUPS.get(index - 1).split("[+]");
		boolean  lengthNotOne1  = lastGroupSplit.length > 1;

		String   lastFunction   = "";
		String   lastGroupBegin = "-1";
		String   lastGroupEnd   = "-1";

		if (lengthNotOne1) {
			lastFunction   = lastGroupSplit[0];
			lastGroupBegin = lastGroupSplit[1];
			lastGroupEnd   = lastGroupSplit[2];
		}
		String[] data = {lastFunction, lastGroupBegin, lastGroupEnd};
		return   data;
	}

	
	private String[] recuperateCurrentSchema(ArrayList<String> GROUPS, int index) {
		
		String[] currentSplit    = GROUPS.get(index).split("[+]");
		boolean  lengthNotOne2   = currentSplit.length > 1;
		
		String   currentFunction   = "";
		String   currentBegin      = "-1";
		String   currentGroupEnd   = "-1";

		if (lengthNotOne2) { 
			currentFunction   = currentSplit[0]; 
			currentBegin      = currentSplit[1];
			currentGroupEnd   = currentSplit[2];
		}

		String[] data = {currentFunction, currentBegin, currentGroupEnd};
		return   data;
	}
	

	
	
	
	protected String searchFirstLastNcFromGPrep(ArrayList<ArrayList<String>> currentSyntaxe, 
			ArrayList<String> currentText, int lastGroupBegin) {

		String[] out = {"", ""};
		for (int index=lastGroupBegin; index > 0; index--) {

			ArrayList<String> current = currentSyntaxe.get(index); 

			boolean containsSearch1 = listEqualsElement(current, "Nom commun");
			boolean containsSearch2 = thisListEqualList(current, DETERMINANTSWITHOUTPREP);

			if (containsSearch1) { out[0] = currentText.get(index); }
			if (containsSearch2) { out[1] = currentText.get(index); break; }
		}
		return out[1] + " " + out[0];
	}
	
	
	protected boolean ifGroupContains(ArrayList<ArrayList<String>> partOfSentence, String search) {
		for (ArrayList<String> current: partOfSentence) {
			for (String element: current) {
				boolean contains1 = element.contains(search);
				if (contains1) { return true; }
			}
		}
		return false;
	}
}

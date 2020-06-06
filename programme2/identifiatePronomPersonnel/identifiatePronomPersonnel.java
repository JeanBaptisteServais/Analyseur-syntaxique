package fr.jbaw.programme2.identifiatePronomPersonnel;

import java.util.ArrayList;

import fr.jbaw.programme2.searchWordUtils;

public class identifiatePronomPersonnel extends searchWordUtils{

	private ArrayList<String> 			 dataSubject;
	private ArrayList<ArrayList<String>> saveGn;
	private ArrayList<String> 			 containerDataGroupVerbal;
	private ArrayList<ArrayList<String>> currentSyntaxe;
	private ArrayList<String>			 currentText;
	private ArrayList<String> 			 dataAction;


	
	public identifiatePronomPersonnel(ArrayList<String> dataSubject, ArrayList<ArrayList<String>> saveGn, 
			ArrayList<String> containerDataGroupVerbal, ArrayList<ArrayList<String>> currentSyntaxe, 
			ArrayList<String> currentText, ArrayList<String> dataAction) {
		
		this.dataSubject			  = dataSubject;
		this.saveGn 	 			  = saveGn;
		this.containerDataGroupVerbal = containerDataGroupVerbal;
		this.currentSyntaxe 		  = currentSyntaxe;
		this.currentText 			  = currentText;
		this.dataAction               = dataAction;

		
		String  subject   		 = dataSubject.get(0);
		boolean noSubject 		 = subject.equalsIgnoreCase("");
		boolean saveGnNotEmpty   = saveGn.size() > 0 && saveGn.get(saveGn.size() - 1).size() > 0;
		if (noSubject && saveGnNotEmpty) { subject = saveGn.get(saveGn.size() - 1).get(0); }
		
		ArrayList<String> recup = new ArrayList<String>();
		recuperateInformation(containerDataGroupVerbal,   recup, "subject?");

		
		
		
		boolean haveToSearch = recup.size() > 0;

		
		//Recuperate cmplt cod by the other nom commun in sentence.
		ArrayList<String> NomCommun   = new ArrayList<String>();
		ArrayList<String> determinant = new ArrayList<String>();
		
		
		if (haveToSearch) { one(subject, NomCommun, determinant); }
		

		boolean haveFoundOtherNm = NomCommun.size() > 0;
		if (haveFoundOtherNm) { two(recup, NomCommun, dataAction); }


		boolean testLastGroupNominal = NomCommun.size() == 0;
		if (testLastGroupNominal && saveGnNotEmpty && haveToSearch) {

			String lastGn = saveGn.get(saveGn.size() - 1).get(0);

			boolean areSame1 = lastGn.contains(subject);
			boolean areSame2 = subject.contains(lastGn);

			boolean isAverb   = searchIfThereIsFunctionInSyntaxe(currentSyntaxe, "verbe#");
			boolean isPrnmRel = searchIfThereIsFunctionInSyntaxe(currentSyntaxe, "Pronom relatif");;

			
			if 		(!areSame1 && !areSame2)   						  { threeOne(recup, lastGn); }
			else if ((areSame1 || areSame2) && isAverb && !isPrnmRel) { threeTwo(saveGn, lastGn, recup); }
			else if (!isAverb && isPrnmRel) 						  { lastGnIsCurrentRefl(recup, lastGn); }
			
		}

	}
	
	
	
	















	private void threeTwo(ArrayList<ArrayList<String>> saveGn, String lastGn, ArrayList<String> recup) {

		System.out.println("identifiatePronomPersonnel - threeTwo");
		
		String[] pronoms = {"il", "elles", "elle", "ils", "le", "la", "un", "une", "y",
				"je", "tu", "il", "elle", "on", "nous", "vous", "ils", "elles", "lui", "contraction=le",
				"se", "contraction=se", "me", "te", "nous", "vous", "leur"};

		
		for (int index=saveGn.size() -1; index >= 0; index--) {

			String  current     = "";
			boolean saveGnNotEmpty = saveGn.get(index).size() > 0;
			if (saveGnNotEmpty) { current = saveGn.get(index).get(0); }

			ArrayList<String> listCurrent = saveGn.get(index);

			boolean isNotMatching = !lastGn.contains(current);
			boolean isPrnm        = thisListEqualList(listCurrent, pronoms);
			if  (isNotMatching && !isPrnm) { 
				String increment = recup.get(0) + "=" + current;
				dataAction.add(increment);
				break; 
			}
		}
	}






	private void lastGnIsCurrentRefl(ArrayList<String> recup, String lastGn) {
		System.out.println("identifiatePronomPersonnel - threeFourth");
		
		String increment = recup.get(0) + "=" + lastGn;
		dataAction.add(increment);
		
	}


	private void threeOne(ArrayList<String> recup, String lastGn) {
		
		System.out.println("identifiatePronomPersonnel - threeOne");
		
		String increment = recup.get(0) + "=" + lastGn;
		dataAction.add(increment);
		
	}









	private void two(ArrayList<String> recup, ArrayList<String> nomCommun, ArrayList<String> dataAction2) {
		
		System.out.println("identifiatePronomPersonnel - two");
		
		String increment = recup.get(0) + "=" +  nomCommun.get(0);
		dataAction.add(increment);
	}




	private void one(String subject, ArrayList<String> nomCommun, ArrayList<String> determinant) {

		System.out.println("\n\nidentifiatePronomsPersonnel - one");
			
		for (int index=1; index < currentSyntaxe.size(); index++) {
	
			String            lastWord  = currentText.get(index - 1);
			String            word      = currentText.get(index);
			ArrayList<String> last      = currentSyntaxe.get(index - 1);
			ArrayList<String> current   = currentSyntaxe.get(index);
	
			boolean isNomCommun   = listEqualsElement(current, "Nom commun");
			boolean isNomPropre   = listEqualsElement(current, "Nom propre");
			boolean isSubject1    = word.contains(subject);
			boolean isSubject2    = subject.contains(word);
			boolean lastIsDet     = thisListEqualList(last, DETERMINANTS);
	
			if ((isNomCommun || isNomPropre) && !isSubject1 && !isSubject2) {
				String increment = "";
				if (lastIsDet) { determinant.add(lastWord); }
				nomCommun.add(increment += word);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}


package fr.jbaw.programme2.identifiateVerbOfGroupNominal;

import java.util.ArrayList;
import java.util.Map;

import fr.jbaw.programme2.searchWordUtils;

public class verbOfGn extends searchWordUtils{

	private ArrayList<String> 			   dataSubject;
	private ArrayList<String> 		       dataAction;
	private ArrayList<ArrayList<String>>   saveVerb;
	private ArrayList<ArrayList<String>>   saveGn;
	private ArrayList<ArrayList<String>>   saveSchema;
	private ArrayList<String>			   currentText;
	private ArrayList<String> 			   containerDataGroupNominal;
	private ArrayList<String> 			   dataActionCmplt;
	private Map<String, ArrayList<String>> discours;
	private boolean[] 					   actionDone;
	private ArrayList<ArrayList<String>> saveGardeCmplt;

	

	public verbOfGn(ArrayList<String> dataSubject, ArrayList<String> dataAction, ArrayList<String> containerDataGroupNominal, 
			ArrayList<ArrayList<String>> saveVerb, ArrayList<ArrayList<String>> saveGn, ArrayList<ArrayList<String>> saveSchema,
			ArrayList<String> currentText, ArrayList<String> dataActionCmplt, Map<String, ArrayList<String>> discours,
			boolean[] actionDone, ArrayList<ArrayList<String>> saveGardeCmplt) {

		this.actionDone                = actionDone;
		this.dataSubject               = dataSubject;
		this.dataAction  			   = dataAction;
		this.saveVerb 	 			   = saveVerb;
		this.saveGn 				   = saveGn;
		this.saveSchema  			   = saveSchema;
		this.currentText 			   = currentText;
		this.discours                  = discours;
		this.dataActionCmplt           = dataActionCmplt;
		this.saveGardeCmplt            = saveGardeCmplt;
		this.containerDataGroupNominal = containerDataGroupNominal;
		
		

		boolean[] sentenceC      = sentenceConditions(currentText);																	 //1)
		boolean   notNewSentence = sentenceC[0];

		boolean[] containC             = containConditions(saveSchema, discours);													 //2)
		boolean   isNotPrepoAndGn      = containC[0];
		boolean   isNotDiscoursInCours = containC[1];


		boolean[] emptyC        = emptyConditions(dataSubject, dataAction, containerDataGroupNominal, saveVerb, saveGn, saveSchema); //3)
		boolean   haveGn        = emptyC[0]; //saveVerb, saveGn, saveSchema.
		boolean   notEmpty      = emptyC[1];
		boolean   haveNoSubject = emptyC[2];
		boolean   haveNoAction  = emptyC[3];



		if (notEmpty && notNewSentence && !haveNoSubject && !haveNoAction && isNotPrepoAndGn && isNotDiscoursInCours) {
			System.out.println("iciiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");

			ArrayList<String> lastSchema = saveSchema.get(saveSchema.size() - 2);
			boolean lastIsntCmplt = lastSchema.get(lastSchema.size() - 1).contains("GCMPLT");

			if (!lastIsntCmplt) {
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				ArrayList<String> lastGn       = saveGn.get(saveGn.size() - 3);
				ArrayList<String> lastVerb     = saveVerb.get(saveVerb.size() - 2);
				String            groupNominal = recuperateInformation(containerDataGroupNominal, "quoi");
	
				saveVerb.add(lastVerb);
				dataAction.clear();  dataAction.add(lastVerb.get(lastVerb.size() - 1));
				dataSubject.clear(); dataSubject.add(lastGn.get(lastGn.size() - 1));
				System.out.println("GN + GPrep => recup from saveverbe + saveGn");
			}


			//Current is Gn, last is CMPLT (gn of verbe).
			else if (lastIsntCmplt) {
				System.out.println("iciiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
				ArrayList<String> lastGn       = saveGn.get(saveGn.size() - 3);
				ArrayList<String> lastVerb     = saveVerb.get(saveVerb.size() - 2);
				String            groupNominal = recuperateInformation(containerDataGroupNominal, "quoi");

				saveVerb.add(lastVerb);
				dataAction.clear();  dataAction.add(lastVerb.get(lastVerb.size() - 1));
				dataSubject.clear(); dataSubject.add(lastGn.get(lastGn.size() - 1));
				System.out.println("GN + GPrep => recup from saveverbe + saveGn");
				
			}
		}
		
		

		else if (notEmpty && notNewSentence && !haveNoSubject && !haveNoAction && haveGn && !isNotPrepoAndGn) {
			System.out.println("\n\nidentifiateVerbOfGroupNominal - verbOfGn - Idenfiate group nominal without verb");

			identifiateGroupNominalWithoutVerb();
		}



		boolean saveNotEmpty = saveSchema.get(saveSchema.size() - 1).size() >= 1;
		String  currentSchem = "";
		if (saveNotEmpty) {
			currentSchem = saveSchema.get(saveSchema.size() - 1).get(0);
		}

		
		boolean isGprepAndOnlyThat = currentSchem.contains("GPREP") &&
									 saveSchema.get(saveSchema.size() - 1).size() == 1;
		


		if (notEmpty && notNewSentence && !haveNoSubject && !haveNoAction && isGprepAndOnlyThat) {
			System.out.println("\n\nIdentiate subject and verb from lastGn && saveVerb");

			ArrayList<String> lastGn       = saveGn.get(saveGn.size() - 1);
			ArrayList<String> lastVerb     = saveVerb.get(saveVerb.size() - 2);
			String            groupNominal = recuperateInformation(containerDataGroupNominal, "quoi");
			
			boolean haveLastGn  = !lastGn.get(0).equalsIgnoreCase("");
			boolean haveVerb 	= !lastVerb.get(0).equalsIgnoreCase("");
			boolean haveGroupN  = !groupNominal.equalsIgnoreCase("");
			
			boolean TwoSubjectButOnVerb = lastGn.size() > lastVerb.size();
			

			
			if (haveLastGn && haveVerb && haveGroupN && !TwoSubjectButOnVerb) {
				saveVerb.add(lastVerb);
				dataAction.clear();  dataAction.add(lastVerb.get(lastVerb.size() - 1));
				dataSubject.clear(); dataSubject.add(lastGn.get(lastGn.size() - 1));
				System.out.println("identifiateVerbOfGroupNominal - verbOfGn - GREP WITHOUT VERB NI SUBJECT 1");

			}
			
			else if (haveLastGn && haveVerb && haveGroupN && TwoSubjectButOnVerb) {
				dataSubject.clear(); dataSubject.add(lastGn.get(lastGn.size() - 1));
				System.out.println("identifiateVerbOfGroupNominal - verbOfGn -  GREP WITHOUT VERB NI SUBJECT 2");

			}
			
			
		}
		
		
		
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	

	private void identifiateGroupNominalWithoutVerb() {
		System.out.println("\n\n\nidentifiateVerbOfGroupNominal - verbOfGn - identifiateGroupNominalWithoutVerb");

		ArrayList<String> lastGroupNomi = saveGn.get(saveGn.size() - 2);
		ArrayList<String> lastGroupVerb = saveVerb.get(saveVerb.size() - 2);

		String  currentGn = recuperateInformation(containerDataGroupNominal, "quoi");


		boolean lastGnIsCurrentGn       = lastGroupNomi.size() > 0 && currentGn.contains(lastGroupNomi.get(0));
		boolean currentGnIsLastGn       = lastGroupNomi.size() > 0 && lastGroupNomi.get(0).contains(currentGn);
		boolean currentContainCmpltAct  = dataActionCmplt.size() > 0 &&
										  (currentGn.contains(dataActionCmplt.get(0)) ||
										   dataActionCmplt.get(0).contains(currentGn));

		boolean actCmpltContainscurrent = dataActionCmplt.size() > 0 && lastGroupNomi.contains(dataActionCmplt.get(0));

		boolean contening = lastGnIsCurrentGn || currentGnIsLastGn || currentContainCmpltAct || actCmpltContainscurrent;

		if (contening) { lastGroupNomi = saveGn.get(saveGn.size() - 3); }

		boolean notEmpty = lastGroupNomi.size() > 0;
		
		if (notEmpty) {
		
			//Increment subject
			dataSubject.clear(); dataSubject.add(lastGroupNomi.get(0));
	
			//Recuperate group nominal and associate it to verbe.
			String groupNominal = recuperateInformation(containerDataGroupNominal, "quoi");
	
	
			//Replace lastVerb and lastGn.
			boolean saveGnLastEmpty = saveGn.get(saveGn.size() - 1).size() == 0;
			if (saveGnLastEmpty) {saveGn.get(saveGn.size() - 1).add(0, lastGroupNomi.get(0));}
			else 				 {saveGn.get(saveGn.size() - 1).set(0, lastGroupNomi.get(0));}
			
			saveVerb.set(saveVerb.size() - 1, saveVerb.get(saveVerb.size() - 2));
	
	
			//Verify we got a verb last sentence.
			boolean haveASaveVerb  = !lastGroupVerb.get(0).equalsIgnoreCase("");
			if (haveASaveVerb) {
				String incrementAction = lastGroupVerb.get(0) + " " + groupNominal;
	
				dataAction.clear();  dataAction.add(incrementAction); dataActionCmplt.clear();
				System.out.println(incrementAction + "\n");
				actionDone[0] = true;
			}
		}
		
	}













	//3)
	private boolean[] emptyConditions(ArrayList<String> dataSubject, ArrayList<String> dataAction,
									  ArrayList<String> containerDataGroupNominal, ArrayList<ArrayList<String>> saveVerb,
									  ArrayList<ArrayList<String>> saveGn, ArrayList<ArrayList<String>> saveSchema) {

		boolean haveNoSubject  = !dataSubject.get(0).equalsIgnoreCase("");
		boolean haveNoAction   = !dataAction.get(0).equalsIgnoreCase("");
		boolean haveGn         = listContains(containerDataGroupNominal, "quoi=");

		boolean saveVerbNotEmpty1  = saveVerb.size() > 2 && saveVerb.get(saveVerb.size() - 2).size() > 0;

		boolean saveGnNotEmpty1    = saveGn.size() > 2;
		boolean saveSchemaNotEmpty = saveSchema.size() > 1 && saveSchema.get(saveSchema.size() - 2).size() > 0;
		boolean notEmpty           = saveVerbNotEmpty1 && saveGnNotEmpty1 && saveSchemaNotEmpty;


		boolean[] conditions = {haveGn, notEmpty, haveNoSubject, haveNoAction};
		return    conditions;
	}


	//2)
	private boolean[] containConditions(ArrayList<ArrayList<String>> saveSchema, Map<String, ArrayList<String>> discours) {

		boolean saveContainsGrep = listContains(saveSchema.get(saveSchema.size() - 1), "GPREP");
		boolean saveContainsGnom = listContains(saveSchema.get(saveSchema.size() - 1), "GNOMINAL");
		boolean saveNotEmpty     = saveSchema.get(saveSchema.size() - 1).size() == 2;
		boolean isNotPrepoAndGn  = saveContainsGrep && saveContainsGnom && saveNotEmpty;

		boolean isNotDiscoursInCours = discours.get("true") == null;
		
		
		
		boolean[] conditions = {isNotPrepoAndGn, isNotDiscoursInCours};
		return    conditions;
		
	}


	//1)
	private boolean[] sentenceConditions(ArrayList<String> currentText) {
		String  word           = currentText.get(0);
		String  firstLetter    = Character.toString(word.charAt(0));
		boolean notNewSentence = !firstLetter.equals(firstLetter.toUpperCase());
		
		boolean[] conditions = {notNewSentence};
		return    conditions;
	}
	
}

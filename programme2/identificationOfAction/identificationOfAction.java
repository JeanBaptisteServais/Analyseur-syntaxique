package fr.jbaw.programme2.identificationOfAction;

import java.util.ArrayList;

public class identificationOfAction extends utilsAction{
	
	
	
	
	private ArrayList<String> 			 containerDataGroupVerbal;
	private ArrayList<String> 			 dataSubject;
	private ArrayList<ArrayList<String>> saveSentence;
	private ArrayList<ArrayList<String>> partOfSentence;
	private ArrayList<String> 			 dataAction;
	private ArrayList<ArrayList<String>> inSearchingGnPronom;

	

	public identificationOfAction(ArrayList<String> containerDataGroupVerbal, ArrayList<String> dataSubject, 
								  ArrayList<ArrayList<String>> saveSentence,
								  ArrayList<ArrayList<String>> partOfSentence, ArrayList<String> dataAction, 
								  ArrayList<ArrayList<String>> inSearchingGnPronom) {
		
		
		this.containerDataGroupVerbal = containerDataGroupVerbal;
		this.dataSubject              = dataSubject;
		this.saveSentence 			  = saveSentence;
		this.partOfSentence 		  = partOfSentence;
		this.dataAction 			  = dataAction;
		this.inSearchingGnPronom      = inSearchingGnPronom;
		
		
	}
	
	
	
	public void identifiateAction() {
		

		//		  neg1 action neg2
		String[] action = {"",   "",   ""};

		ArrayList<String> actionFunction = new ArrayList<String>();
		
		
		
		
		
		recuperateInformation(containerDataGroupVerbal,   actionFunction, "action");
		boolean isNotEmpty1 = actionFunction.size() > 0;
		if (isNotEmpty1) { action[1] = actionFunction.get(0); }
		
		
		
		
		
		recuperateInformation(containerDataGroupVerbal,   actionFunction, "negation");
		boolean isNotEmpty2 = actionFunction.size() > 0;
		if (isNotEmpty2) { action[0] = actionFunction.get(0);  action[2] = "pas"; }
		
		
		
		
		
		
		String generalite = "";
		recuperateInformation(containerDataGroupVerbal,   actionFunction, "dem");
		boolean isNotEmpty3 = actionFunction.size() > 0;
		if (isNotEmpty3) { generalite = actionFunction.get(0); }
		
		
		
		
		boolean subjectEmpty = dataSubject.get(0).equals("") || dataSubject.get(0).equals(" ");

		if (subjectEmpty) {
			//Pronom demonstrastif: cela (recuperate last gn) et c'était (generality).
			DemGeneralite(generalite, dataSubject);
			DemPasrElement(generalite, dataSubject, saveSentence);
		}
		
		
		
		String increment = incrementData(action);
		
	

		recuperateFromVerbInText(increment, partOfSentence);
		
		
		
		
		dataAction.add(increment);

		
		
		
	}

	
}

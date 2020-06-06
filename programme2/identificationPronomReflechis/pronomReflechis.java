package fr.jbaw.programme2.identificationPronomReflechis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;




public class pronomReflechis {


	private String[] PRONOM 		 = {"je", "tu", "il", "nous", "vous", "ils", "elle", "on", "elles",
									    "Pronom personnel", "me", "te", "se", "lui",
									    "moi", "toi", "soi", "leur", "eux", "contraction=que", "contraction=se",
									    "contraction=me", "contraction=te"};
	
	private String[] enonciateur     = {"me", "contraction=me", "moi"};
	private String[] destinataire    = {"te", "contraction=te", "toi"};

	private String[] designFem       = {"se", "contraction=se", "la", "contraction=la"};
	private String[] designMasc      = {"se", "contraction=se", "le", "contraction=le", "y", "soi"};
	private String[] desginGen       = {"se", "contraction=se", "soi", "lui"};

	private String[] designFemPlur   = {"se", "contraction=se", "se", "contraction=se", "leur"};
	private String[] designMascPlur  = {"se", "contraction=se", "leur", "eux"};
	private String[] enonciateurPlur = {"nous"};
	private String[] designPlurDest  = {"vous"};


	private ArrayList<String> 			   containerDataGroupVerbal;
	private ArrayList<String> 			   dataSubject;
	private ArrayList<String>			   dataAction;
	private Map<String, ArrayList<String>> qui;
	private ArrayList<ArrayList<String>>   saveGn;
	
	
	
	
	
	
	public pronomReflechis(ArrayList<String> containerDataGroupVerbal, ArrayList<String> dataSubject,
			ArrayList<String> dataAction, Map<String, ArrayList<String>> qui, ArrayList<ArrayList<String>> saveGn) {
	
		this.containerDataGroupVerbal = containerDataGroupVerbal;
		this.dataSubject              = dataSubject;
		this.dataAction               = dataAction;
		this.qui                      = qui;
		this.saveGn                   = saveGn;
	}
	

	private boolean[] extraConditions(ArrayList<String> container, String prnm) {


		boolean haveAprnm   	 = !prnm.equalsIgnoreCase("");
		boolean noSubject        = dataSubject.size() == 1 && dataSubject.get(0).equalsIgnoreCase("");
		boolean saveGnNotEmpty   = saveGn.size() > 1 && container.size() > 0;

		boolean subjectIsNotPrnm = !thisListEqualWord(PRONOM, dataSubject.get(0).toLowerCase());


		boolean conditionHaveAdefinatePrnm = haveAprnm && !noSubject && subjectIsNotPrnm;
		boolean conditionSaveGnNoSubject   = saveGnNotEmpty && noSubject;

		boolean[] conditions = {conditionHaveAdefinatePrnm, conditionSaveGnNoSubject};
		return    conditions;
	}
	
	
	
	public boolean[] searchingPrnmReflechis() {

		boolean 		  incrementedLast  = false;
		boolean 		  incrementedGenre = false;

		ArrayList<String> container   = new ArrayList<String>();
		String 			  prnm 		  = searchCmpltRefl(containerDataGroupVerbal, qui, container);

		boolean[] conditions                 = extraConditions(container, prnm);
		boolean   conditionHaveAdefinatePrnm = conditions[0];
		boolean   conditionSaveGnNoSubject   = conditions[1];


		if (conditionHaveAdefinatePrnm) { searchDefinateCmpltRefl(prnm, dataSubject, qui, dataAction); }

		if (conditionSaveGnNoSubject)   { incrementedLast  = searchIfLastCanCorrespondingCmpltRefl(saveGn, prnm, container, dataSubject); }
		if (conditionSaveGnNoSubject)   { incrementedGenre = verifygenreCmpltRefl(dataSubject, saveGn, prnm, container); }


		boolean[] incrementation = {incrementedLast, incrementedGenre};
		return incrementation;
		
	}
	

	
	public void matchPronomReflechisToLastSubject(String pronomFound, String GenrePronom, 
			  									  Map<String, ArrayList<String>> qui, ArrayList<String> container) {

		//Pronom reflechis - make a match with genre.
		for(Entry<String, ArrayList<String>> groups: qui.entrySet()) {

			ArrayList<String> current = groups.getValue();
			String 			  key     = groups.getKey();

			boolean haveMatchGenre = listContains(current, GenrePronom);
			if (haveMatchGenre) { container.add(GenrePronom); container.add(key); }
		}
	}
	



	
	
	
	
	
	public boolean verifygenreCmpltRefl(ArrayList<String> dataSubject, ArrayList<ArrayList<String>> saveGn,
			String prnm, ArrayList<String> container) {


			
		String genre   = container.get(0);
		String subject = container.get(1);
		
		boolean notEmpty = saveGn.get(saveGn.size() - 1).size() > 0;
		
		if (notEmpty) {
		
			String  lastGn     = saveGn.get(saveGn.size() - 1).get(0);	
			boolean isMatching = subject.equalsIgnoreCase(lastGn);
		
			if (isMatching) { 
				System.out.println("\n\nIdentifiate complement - verifygenreCmpltRefl - refléchis"); 
				System.out.println("Matching reflechis: " + subject + " by " + genre); 
				dataSubject.clear(); dataSubject.add(subject); 
				return true;
			}
		}
		return false;
	}
	
	
	
	
	

	public void searchDefinateCmpltRefl(String prnm, ArrayList<String> dataSubject, Map<String, 
										 ArrayList<String>> qui, ArrayList<String> dataAction) {

		System.out.println("\n\n\nidentifiateCmpltRefl - searchDefinateCmpltRefl");
		
		//Pronom definate -je tu nous vous

		String subject = dataSubject.get(0);
			
		String[] je   = {"me", "contraction=me"};
		String[] tu   = {"te", "contraction=te"};
		String[] nous = {"nous"};
		String[] vous = {"vous"};

		String 	 first      = isMatchingCmpltRef(je,   prnm, "je");
		String 	 second     = isMatchingCmpltRef(tu,   prnm, "tu");
		String 	 firstPlur  = isMatchingCmpltRef(nous, prnm, "nous");
		String 	 secondPlit = isMatchingCmpltRef(vous, prnm, "vous");


		Map<String, String> dico = new HashMap<String, String>();
		dico.put(first,		 "je");
		dico.put(second, 	 "tu");
		dico.put(firstPlur,  "nous");
		dico.put(secondPlit, "vous");

		String havePrnm = "?";
		for(Entry<String, String> groups: dico.entrySet()) {
			String key   = groups.getKey();
			String value = groups.getValue();

			boolean keyNotEmpty = !key.equalsIgnoreCase("");
			if (keyNotEmpty) { havePrnm += value; break; }
		}
			
		//trying to match with qui.
		boolean haveKey1 = qui.get(havePrnm) != null;
		boolean haveKey2 = qui.get(subject)  != null;
		boolean noSame   = !(havePrnm.contains(subject) || subject.contains(havePrnm));
		if (haveKey1 && haveKey2 && noSame) {

			ArrayList<String> recuperateRefl = qui.get(havePrnm);
			String increment1 = "relation avec: " + subject;
			recuperateRefl.add(increment1);
				
			ArrayList<String> recuperateSub = qui.get(subject);
			String increment2 = "relation avec: " + havePrnm;
			recuperateSub.add(increment2);
				

			String recuperateAction = dataAction.get(0);
			recuperateAction = "(" + prnm + "=" + havePrnm + ") " + recuperateAction;
			dataAction.set(0, recuperateAction);
		}
	}
	
	protected String isMatchingCmpltRef(String[] ilElleIlsEllesOn, String prnm, String personn) {
		for (String element: ilElleIlsEllesOn) {
			boolean isMatching = element.equalsIgnoreCase(prnm);
			if (isMatching) { return personn; }
		}
		return "";
	}


	
	

	
	
	
	
	
	//-------------------------------------------------------------------------------- searchIfLastCanCorrespondingCmpltRefl


	private boolean[] conditionSearchCmpltToLastGnPrnm(String prnm, String lastGnString) {

		String[][] ilElleIlsEllesOn = {{"se", "contraction=se", "te", "contraction=te", "ce", "contraction=ce"},
				   					   {"il", "elle", "ils", "elles", "on"}};

		String[][] je 				= {{"me", "contraction=me"}, {"je"}};
		String[][] tu 				= {{"te", "contraction=te"}, {"tu"}};


		boolean third  = isMatchingCmpltRef(ilElleIlsEllesOn[0], prnm, ilElleIlsEllesOn[1], lastGnString);
		boolean first  = isMatchingCmpltRef(je[0], prnm, je[1], lastGnString);
		boolean second = isMatchingCmpltRef(tu[0], prnm, tu[1], lastGnString);
		
		boolean[] conditions = {third, first, second};
		return    conditions;
		
	}


	protected boolean isMatchingCmpltRef(String[] listPartOne, String prnm, String[] listPartTwo, String lastGnString) {
		
		boolean stepOnePrnmRefl = false;
		for (String reflList: listPartOne) {
			boolean isMatching = reflList.equalsIgnoreCase(prnm);
			if (isMatching && !stepOnePrnmRefl) { stepOnePrnmRefl = true; }
		}

		boolean stepTwoPrnm = false;
		for (String prnmList: listPartTwo) {
			boolean prnmContainsInterro = lastGnString.contains("?");
			boolean isMatching          = prnmList.equalsIgnoreCase(lastGnString);
			if     (prnmContainsInterro)        { prnmList = "?" + prnmList; }
			if     (isMatching && !stepTwoPrnm) { stepTwoPrnm = true; }
		}

		if (stepOnePrnmRefl && stepTwoPrnm) { return true; }
		return false;
	}
	
	
	public boolean searchIfLastCanCorrespondingCmpltRefl(ArrayList<ArrayList<String>> saveGn, String prnmFound, 
														 ArrayList<String> container, ArrayList<String> dataSubject) {
	

		ArrayList<String> last 		   = saveGn.get(saveGn.size() - 1);
		
		boolean notEmpty = last.size() > 0;
		
		if (notEmpty) {
		
			String 			  lastGnString = last.get(last.size() - 1);
	
			boolean[] conditions = conditionSearchCmpltToLastGnPrnm(prnmFound, lastGnString);
			boolean   third      = conditions[0];
			boolean   first      = conditions[1];
			boolean   second     = conditions[2];
	
	
			if (third || first || second) {
				System.out.println("identifiateCmpltRefl - searchIfLastCanCorrespondingCmpltRefl - by matchjing with lastGn");
				System.out.println("Matching reflechis: " + lastGnString + " by matching"); 
				System.out.println("prnm to search: " + prnmFound);
	
				dataSubject.clear(); dataSubject.add(lastGnString);
				return true;
			}
		}
		return false;
	}
	
	

	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	//---------------------------------------------------------------------------- searchCmpltRefl
	
	private boolean[] searchingPrnm(String function, String pronom) {


		 boolean isDesin   = function.equalsIgnoreCase("refl");

		 boolean isJe      = thisListEqualWord(enonciateur,   pronom);
		 boolean isTu      = thisListEqualWord(destinataire,  pronom);
		 boolean isIl      = thisListEqualWord(designMasc,    pronom);
		 
		 boolean isElle    = thisListEqualWord(designFem,     pronom);
		 boolean isOn      = thisListEqualWord(desginGen,     pronom);

		 boolean isNous    = thisListEqualWord(enonciateurPlur,  pronom);
		 boolean isVous    = thisListEqualWord(designPlurDest,   pronom);
		 boolean isElles   = thisListEqualWord(designFemPlur,    pronom);
		 boolean isIls     = thisListEqualWord(designMascPlur,   pronom);
		 
		 boolean[] foundPrnm = {isDesin, isJe, isTu, isIl, isElle, isOn, isNous, isVous, isElles, isIls};
		 return    foundPrnm;
		 
	}

	public String searchCmpltRefl(ArrayList<String> containerDataGroupVerbal, 
								  Map<String, ArrayList<String>> qui, ArrayList<String> container) {


		String prnm = "";
		for (String   element: containerDataGroupVerbal) {

			 String[] elementSplit = element.split("=");
			 String   function     = elementSplit[0];
			 String   pronom       = elementSplit[1];
 
			 boolean[] foundPrnm = searchingPrnm(function, pronom);

			 boolean isDesin = foundPrnm[0];
			 boolean isJe    = foundPrnm[1];
			 boolean isTu    = foundPrnm[2];
			 boolean isIl    = foundPrnm[3];
			 boolean isElle  = foundPrnm[4];
			 boolean isOn    = foundPrnm[5];
			 boolean isNous  = foundPrnm[6];
			 boolean isVous  = foundPrnm[7];
			 boolean isElles = foundPrnm[8];
			 boolean isIls   = foundPrnm[9];

			 if      (isJe   && isDesin)   { matchPronomReflechisToLastSubject(pronom, "je",      qui, container); prnm=pronom; }
			 else if (isTu   && isDesin)   { matchPronomReflechisToLastSubject(pronom, "tu",      qui, container); prnm=pronom; }
			 else if (isElle && isDesin)   { matchPronomReflechisToLastSubject(pronom, "feminin", qui, container); prnm=pronom; }
			 
			 else if (isIl   && isDesin)   { matchPronomReflechisToLastSubject(pronom, "masculin",qui, container); prnm=pronom; }
			 else if (isNous && isDesin)   { matchPronomReflechisToLastSubject(pronom, "nous",    qui, container); prnm=pronom; }
			 
			 else if (isVous && isDesin)   { matchPronomReflechisToLastSubject(pronom, "vous",     qui, container); prnm=pronom; }
			 else if (isOn   && isDesin)   { matchPronomReflechisToLastSubject(pronom, "general",  qui, container); prnm=pronom; }
			 else if (isIls  && isDesin)   { matchPronomReflechisToLastSubject(pronom, "plurMasc", qui, container); prnm=pronom; }
			 else if (isElle && isDesin)   { matchPronomReflechisToLastSubject(pronom, "plurFem",  qui, container); prnm=pronom; }
 
		}

		return prnm;
	}
	
	
	
	
	
	
	
	

	
	static boolean listContains(ArrayList<String> List, String elementToMatch) {

		boolean isEqual = false; 

		for (String element: List) {if (element.contains(elementToMatch) && !isEqual) {isEqual = true;}}
		return isEqual;
	}

	static boolean thisListEqualWord(String[] thisList, String word) {

		boolean isEqual = false;

		for (String element: thisList) {if (element.equalsIgnoreCase(word) && !isEqual) {isEqual = true;}}
		return isEqual;
	}
}

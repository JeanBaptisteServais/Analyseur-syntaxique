package fr.jbaw.programme2.identificationOfSubjectWithoutVerb;

import java.util.ArrayList;
import java.util.Map;

public abstract class utilsSubject {

	
	private String[] GUILLEMETFERMANTE = {"»"};
	private String[] PRONOMS = {"je", "tu", "il", "elle", "on", "nous", "vous", "ils", "elles"};
	
	boolean subjectIsPrnmAndLastNoFoundBoolean(ArrayList<ArrayList<String>> inSearchingGnPronom, ArrayList<String> dataSubject) {
		
		
		boolean notEmpty = inSearchingGnPronom.size() > 0 && dataSubject.size() == 1 && !dataSubject.get(0).equalsIgnoreCase("");
		
		if (notEmpty) {

			ArrayList<String> lastSearching = inSearchingGnPronom.get(inSearchingGnPronom.size() - 1);System.out.println(lastSearching);
			boolean thereIsPrnm   = lastSearching.size() > 0;
			boolean subjectIsPrnm = thisListEqualWord(PRONOMS, dataSubject.get(0));

			if (thereIsPrnm && subjectIsPrnm) { return true; }
		}
		return false;
	}
	protected static boolean thisListEqualWord(String[] thisList, String word) {

		boolean isEqual = false;

		for (String element: thisList) {if (element.equalsIgnoreCase(word) && !isEqual) {isEqual = true;}}
		return isEqual;
	}
	
	
	boolean verifyIfFirstVerbInSentence(ArrayList<String> dataAction, ArrayList<ArrayList<String>> saveSchema, 
										ArrayList<String> currentText) {

		//Surginrent les canibales.
		//il lit et mange des donuts
		//-> Condition recuperate last or next gn for verb?

		String verbe = "";
		for (String element: dataAction) {
			boolean isntContainsEqual = !element.contains("=");
			if (isntContainsEqual) { verbe = element; break; }
		}

		String FirstWordText = currentText.get(0);

		boolean firstWordIsVerb = verbe.toLowerCase().contains(FirstWordText.toLowerCase());
		if (firstWordIsVerb) { return true; }

		return false;
	}
	
	
	protected int[] recuperateIndexFromGroupInList(ArrayList<String> groupsSyntaxe, String schema) { //cool
		
		int[]  indexs = {-1, -1};
		
		for (String group: groupsSyntaxe) {

			String[] schemaSplit = group.split("[+]");
			
			boolean  notEmpty    = schemaSplit.length > 1;
			boolean  matching    = group.contains(schema);

			if (notEmpty && matching) {
				
				indexs[0] = Integer.parseInt(schemaSplit[0]);
				indexs[1] = Integer.parseInt(schemaSplit[1]);
				return indexs;
			}
		}

		
		return indexs;
	}
	
	
	
	
	protected boolean searchIfLastWordLastSentenceIsGuillemet(ArrayList<ArrayList<String>> saveText){
		

		boolean notFirstSentence = saveText.size() > 2;

		if (notFirstSentence) {

			ArrayList<String> lastSentence = saveText.get(saveText.size() - 2);
			String            lastWord     = lastSentence.get(lastSentence.size() - 1);

			for (String guillemet: GUILLEMETFERMANTE) {
				boolean discoursIsFinish = lastWord.equalsIgnoreCase(guillemet);
				if (discoursIsFinish) { return true; }
			}
		}
		return false;
	}
	
	
	
	
	 void recuperateInformation(ArrayList<String> containerData, ArrayList<String> recuperateData,
								String data) {

		 recuperateData.clear();

		 for (String element: containerData) {

			 String   function = "";
			 String   word     = "";

			 String[] elementSplit = element.split("=");
			 boolean notEmptyEqual = elementSplit.length > 1;
			 if (notEmptyEqual) {
				 function = elementSplit[0];
				 word     = elementSplit[1];
			 }

			 String[] elementSplit2 = element.split("[:]");
			 boolean notEmptyTwoPoints = elementSplit2.length > 1;
			 if (notEmptyTwoPoints) {
				 function = elementSplit2[0];
				 word     = elementSplit2[1];
			 }

			 boolean isElementSearch = function.equalsIgnoreCase(data);
			 if     (isElementSearch) { recuperateData.add(word); }
		 }

	 }
	
	
	
	
	
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

	 protected void recuperateMatchingPronomVerbFromLastSentence(ArrayList<String> dataSubject, 
			 ArrayList<ArrayList<String>> inSearchingGnPronom, ArrayList<ArrayList<String>> saveVerb, 
			 ArrayList<ArrayList<String>> saveGardeCmplt, Map<String, ArrayList<String>> quoi, 
			 ArrayList<ArrayList<String>> prepAdv, Map<String, ArrayList<String>> qui, int numberSentence) {


		String            subject    = dataSubject.get(0);
		ArrayList<String> lastGnPrnm = inSearchingGnPronom.get(inSearchingGnPronom.size() - 1);

		boolean haveMatch  = false;

		for (String prnm: lastGnPrnm) { if (prnm.equalsIgnoreCase(subject)) { haveMatch = true; break; } }

		if (haveMatch) {

			ArrayList<String> container    = new ArrayList<String>();
			ArrayList<String> lastVerbList = saveVerb.get(saveVerb.size() - 1);
			ArrayList<String> lastGn       = saveGardeCmplt.get(saveGardeCmplt.size() - 2);

			String increment = "";
			for (String element: lastVerbList) { increment += (element + " "); }
			for (String element: lastGn)       { increment += element; }

			container.add(increment);
			quoi.put(numberSentence + "-" + subject, container);
			
			
			boolean havePrepData = prepAdv.get(prepAdv.size() - 1).size() > 0;
			if (havePrepData) {
				ArrayList<String> lastAdvPrep = prepAdv.get(prepAdv.size() - 1);
				qui.put(numberSentence + "-" + subject, lastAdvPrep);
			}
		}
	 }

	

	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

		 
	 
	static boolean listContains(ArrayList<String> List, String elementToMatch) {

		boolean isEqual = false; 

		for (String element: List) {if (element.contains(elementToMatch) && !isEqual) {isEqual = true;}}
		return isEqual;
	}
	
	
	boolean thisListEqualList(ArrayList<String> List, String[] thisList) {

		boolean isEqual = false;

		for (String element1: List) {
			for(String element2: thisList) {
				if (element1.equalsIgnoreCase(element2) && !isEqual) {isEqual = true;}
			}
		}
		return isEqual;
	}

	
	boolean listEqualsElement(ArrayList<String> List, String elementToMatch) {

		boolean contains = false;

		for (String element: List) {if (element.equalsIgnoreCase(elementToMatch) && !contains) {contains = true;}}
		return contains;
	}
	
	
	boolean listEqualsElementWithout(ArrayList<String> List, String elementToMatch, String without) {

		boolean contains = false;

		for (String element: List) {
			boolean containening = element.contains(without);
			if (!containening) {
				if (element.equalsIgnoreCase(elementToMatch) && !contains) {
					contains = true;
				}
			}
		}
		return contains;
	}
}

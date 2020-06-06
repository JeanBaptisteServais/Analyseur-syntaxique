package fr.jbaw.programme2.localisationByPrepositionAdverbe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;




public class localisationPrepAdvUtils {


	public localisationPrepAdvUtils() {}
	

	protected void incrementSubjectData(ArrayList<String> dataSubject, Map<String, ArrayList<String>> qui,
										ArrayList<ArrayList<String>> prepAdv, String increment) {

		String            subject     = dataSubject.get(0);
		ArrayList<String> lastPrepAdv = prepAdv.get(prepAdv.size() - 1);


		boolean haveSubject        = !(subject.equalsIgnoreCase("") || subject.equalsIgnoreCase(" "));
		boolean alreadyIncremented = qui.get(subject) != null;

		if (haveSubject && alreadyIncremented) { 
			ArrayList<String> recuperate = qui.get(subject);
			recuperate.add(increment);
			qui.put(subject, recuperate);
		}
		
		lastPrepAdv.add(increment);

	}



	
	
	
	
	
	
	
	
	
	
	
	
	protected boolean runSchema(ArrayList<ArrayList<String>> currentSyntaxe, String[] list, int begin, int end) {
	
		boolean isInRange1 = end < currentSyntaxe.size();
		boolean isInrange2 = begin >= 0;
		
		if (!isInRange1) { end   = currentSyntaxe.size() - 1; }
		if (!isInrange2) { begin = 0; }
		if (begin > end) { int save = end; end = begin; begin = save; }
		
		List<ArrayList<String>> current = currentSyntaxe.subList(begin, end);
		for (ArrayList<String> syntaxe: current) {
			boolean isMatching = listContainElement(syntaxe, list);
			if (isMatching) { return true; }
			
		}
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
	
	
	protected boolean listContainElement(ArrayList<String> List, String[] thisList) {
		boolean isEqual = false;
		for (String element1: List) {
			for(String element2: thisList) {
				if (element1.contains(element2) && !isEqual) {isEqual = true;}
			}
		}
		return isEqual;
	}
	
	protected boolean listContains(ArrayList<String> List, String elementToMatch) {
		boolean isEqual = false; 
		for (String element: List) {if (element.contains(elementToMatch) && !isEqual) {isEqual = true;}}
		return isEqual;
	}

	protected boolean thisListEqualList(ArrayList<String> List, String[] thisList) {
		boolean isEqual = false;
		for (String element1: List) {
			for(String element2: thisList) {
				if (element1.equalsIgnoreCase(element2) && !isEqual) {isEqual = true;}
			}
		}
		return isEqual;
	}

	protected boolean listEqualsElement(ArrayList<String> List, String elementToMatch) {
		boolean contains = false;
		for (String element: List) {if (element.equalsIgnoreCase(elementToMatch) && !contains) {contains = true;}}
		return contains;
	}
	
}

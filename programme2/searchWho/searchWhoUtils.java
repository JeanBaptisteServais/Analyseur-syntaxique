package fr.jbaw.programme2.searchWho;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class searchWhoUtils {

	
	
	
	protected static void recuperatePersonFromQuiWithoutInfo(Map<String, ArrayList<String>> qui, ArrayList<String> persons) {

		//From the genre of the determinant, recuperate subject genre interest from qui.

		String[] genre = {"plurMasc", "plurFem", "pluriel", "singMasc", "singFem", "sing", "feminin", "masculin"};
		
		//Search the subject found on qui.
		for(Entry<String, ArrayList<String>> groups: qui.entrySet()) {

			String 			  person     = groups.getKey();
			ArrayList<String> infoPerson = groups.getValue();
			
			boolean containsGenre = thisListEqualList(infoPerson, genre);
			if (!containsGenre) { persons.add(person); }
		}
	}
	
	
	protected String recuperateMaxFromDico(Map<String, Integer> dictionnaryPoints) {

		int    max    = 0;
		String keyMax = "";

		for(Entry<String, Integer> groups: dictionnaryPoints.entrySet()) {

			String  key   = groups.getKey();
			int     value = groups.getValue();
			
			System.out.println(key + " " + value);
			
			boolean isHighter = value > max;

			if (isHighter) { keyMax = key; max = value; }
		}
		return keyMax;
	}

	protected void recuperateFromSaveGn(ArrayList<ArrayList<String>> saveGn, String subject,
			Map<String, Integer> dictionnaryPoints, String[] PRONOMS) {

		boolean stop = false;
		for (int index=saveGn.size() - 1; index > 0; index--) {

			ArrayList<String> last = saveGn.get(index);

			for (String element: last) {
				boolean doNotMatch = (!element.contains(subject) || !subject.contains(element)) && !(thisListEqualWord(PRONOMS, element));

				if (doNotMatch) {
					int points = 0;
					try { points = dictionnaryPoints.get(element); } catch(Exception e) {}
					dictionnaryPoints.put(element,  points + 1);
					stop = true; break; 
				}
			}
			if (stop) { break; }
		}
	}

	protected void recuperateFromQui(Map<String, ArrayList<String>> qui, Map<String, Integer> dictionnaryPoints, String subject) {
		for(Entry<String, ArrayList<String>> groups: qui.entrySet()) {
			int points = 0;
			try { points = dictionnaryPoints.get(groups.getKey()); } catch(Exception e) {}
			boolean noSubject = groups.getKey() != subject;
			if (noSubject) { dictionnaryPoints.put(groups.getKey(),  1 + points); }
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	void removeFromListContains(ArrayList<String> List, String element) {

		Iterator<String> i = List.iterator();
		while (i.hasNext()) {
			String str = (String) i.next();
			if (str.contains(element)) {i.remove();}
		}
	}
	
	static boolean thisListEqualWord(String[] thisList, String word) {

		boolean isEqual = false;

		for (String element: thisList) {if (element.equalsIgnoreCase(word) && !isEqual) {isEqual = true;}}
		return isEqual;
	}
	
	
	static boolean listContains(ArrayList<String> List, String elementToMatch) {

		boolean isEqual = false; 

		for (String element: List) {if (element.contains(elementToMatch) && !isEqual) {isEqual = true;}}
		return isEqual;
	}
	
	
	protected static boolean thisListEqualList(ArrayList<String> List, String[] thisList) {
		boolean isEqual = false;
		for (String element1: List) {
			for(String element2: thisList) {
				if (element1.equalsIgnoreCase(element2) && !isEqual) {isEqual = true;}
			}
		}
		return isEqual;
	}
}

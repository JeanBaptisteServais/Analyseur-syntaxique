package fr.jbaw.programme2.priorityOfSubject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.jbaw.programme2.searchWordUtils;


public class prioritySubject extends searchWordUtils{
	
	private String[] PRONOMS = {"je", "tu", "il", "elle", "on", "nous", "vous", "ils", "elles"};
	
	private boolean[] makeConditions(ArrayList<ArrayList<String>> partOfSentence, 
			ArrayList<String> currentText, ArrayList<ArrayList<String>> currentSyntaxe, int inSentence) {

		boolean conditionCurrentPropoIsCmplt = false;
		
		boolean partHaveMulti = partOfSentence.size() > 1;
		if (partHaveMulti) {

			//[[0+3+GVERBAL, 4+5+GCMPLT], [6+6+GVERBAL, 7+9+GCMPLT]]
			ArrayList<String> last   		    = partOfSentence.get(partOfSentence.size() - 2);

			ArrayList<String> current 		    = partOfSentence.get(partOfSentence.size() - 1);
			String 			  lastSchemaCurrent = current.get(current.size() - 1);

			boolean inRange             = partOfSentence.get(inSentence) == current;

			boolean currentHavntSubject = current.contains("GSUJET");
			boolean currentHavntPrnm    = RecuperatePrnmFromSchema(current, currentSyntaxe, currentText);
			boolean currentSubject      = !currentHavntSubject && !currentHavntPrnm;

			boolean lastHavePrnm        = RecuperatePrnmFromSchema(last, currentSyntaxe, currentText);
	
			boolean lastSchemaLastCmplt = lastSchemaCurrent.contains("GCMPLT");
			boolean lastCutConainsGrel  = listContains(current, "GREL");

			conditionCurrentPropoIsCmplt = currentSubject && lastHavePrnm && lastSchemaLastCmplt && !lastCutConainsGrel && inRange;
		}

		
	
		boolean[] conditions = {conditionCurrentPropoIsCmplt};
		
		return conditions;
	}
	
	private boolean RecuperatePrnmFromSchema(ArrayList<String> partSentence, 
			ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		String schem = "";
		for (String schema: partSentence) {
			boolean schemaContainsGverbal = schema.contains("GVERBAL");
			if (schemaContainsGverbal) { schem = schema; }
		}
		boolean schemNotEmpty = !(schem.equalsIgnoreCase(""));
		
		if (schemNotEmpty) {
			String[] schemSplit = schem.split("[+]");
			int      begenin    = Integer.parseInt(schemSplit[0]);
			int      end        = Integer.parseInt(schemSplit[1]);
		
			List<ArrayList<String>> partSyntaxe = currentSyntaxe.subList(begenin, end);
			List<String> partText = currentText.subList(begenin, end);
			
			int index = 0;
			for (ArrayList<String> container: partSyntaxe) {
				String  word     = partText.get(index);
				boolean isPrnm   = listEqualsElement(container, "Pronom personnel");
				boolean isntRefl = thisListEqualWord(PRONOMS, word);

				if (isPrnm && isntRefl) { return true; }
				
				index++;
			}
		}
		return false;
	}
	
	
	public prioritySubject(ArrayList<ArrayList<String>> saveGn, 
						   ArrayList<ArrayList<String>> saveVerb, ArrayList<ArrayList<String>> partOfSentence,
						   ArrayList<String> currentText, boolean canNotBeTheMainSubject, 
						   ArrayList<String> dataSubject, ArrayList<String> dataAction, 
						   ArrayList<ArrayList<String>> currentSyntaxe, int inSentence) {


		boolean notEmpty = partOfSentence.size() > 0;
		
		if (notEmpty) {
		

			ArrayList<String> lastCut 		= partOfSentence.get(partOfSentence.size() - 1);
			String 			  lastSchemaCut = lastCut.get(lastCut.size() - 1);
		
			boolean haveRecuperateFromLastCut = canNotBeTheMainSubject;
			boolean lastCutConainsGrel        = listContains(lastCut, "GREL");
			boolean lastIsCmplt               = lastSchemaCut.contains("GCMPLT");

			boolean[] conditions = makeConditions(partOfSentence, currentText, currentSyntaxe, inSentence);
			
			boolean conditionCurrentPropoIsCmplt = conditions[0];
	
			
			//Only double sentence.
			if (haveRecuperateFromLastCut && !lastCutConainsGrel && lastIsCmplt) {

				removeSaveGn(saveGn, dataSubject.get(0));
				removeVerb(saveVerb, dataAction.get(0));

				System.out.println("remove from priority");
			}
			//[[0+3+GVERBAL, 4+5+GCMPLT], [6+6+GVERBAL, 7+9+GCMPLT]]
			else if (conditionCurrentPropoIsCmplt) {
				removeSaveGn(saveGn, dataSubject.get(0));
				removeVerb(saveVerb, dataAction.get(0));
			}
			
			
			
		}
	}

	
	
	
	
	
	
	
	
	private void removeVerb(ArrayList<ArrayList<String>> saveVerb, String action) {
		
		for (int index=saveVerb.size() -1; index > 0; index--) {
			
			ArrayList<String> current = saveVerb.get(index);
			ArrayList<String> toRemove = new ArrayList<String>();
			for (String element: current) {
				boolean isSame = element.equalsIgnoreCase(action);
				if (isSame) { toRemove.add(element); }
			}
			for (String element: toRemove) {
				current.remove(element);
			}
		}
	}

	private void removeSaveGn(ArrayList<ArrayList<String>> saveGn, String subject) {

		String toReplace = "";
		
		for (int index=saveGn.size() -1; index > 0; index--) {
			
			boolean notEmpty = saveGn.get(index).size() > 0;
			if (notEmpty) {
				String  element  = saveGn.get(index).get(0);
				boolean isLastIncremented = element.equalsIgnoreCase(subject);
				System.out.println(element);
				if (!isLastIncremented) { toReplace = element; break; }
			}
		}
		
		for (int index=saveGn.size() -1; index > 0; index--) {
			boolean notEmpty = saveGn.get(index).size() > 0;
			if (notEmpty) {
				String element = saveGn.get(index).get(0);
				boolean isLastIncremented = element.equalsIgnoreCase(subject);
				if (isLastIncremented) { saveGn.get(index).set(0, toReplace); }
			}
		}
	}







	public static void changeSubjectCauseLastGnIsGnOfLastX2Gn(ArrayList<ArrayList<String>> utilsInformation, ArrayList<String> dataSubject) {

		boolean notEmpty = utilsInformation.get(utilsInformation.size() - 2).size() > 0;
		if (notEmpty) {

			String[] priority  = utilsInformation.get(utilsInformation.size() - 2).get(0).split("=");
			boolean  haveAPrio = priority.length > 1;
			
			if (haveAPrio) { 
				dataSubject.clear(); 
				dataSubject.add(priority[1]); 
				System.out.println("\n\nchangeSubjectCauseLastGnIsGnOfLastX2Gn\n\n");
			}
		}
	}







	
	
	
}

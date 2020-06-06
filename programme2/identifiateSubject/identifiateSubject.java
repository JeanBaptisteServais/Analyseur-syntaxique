package fr.jbaw.programme2.identifiateSubject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.jbaw.programme2.searchWordUtils;

public class identifiateSubject extends searchWordUtils{

	
	private ArrayList<String> 			 containerDataGroupSujet;
	private ArrayList<String>			 containerDataGroupVerbal;
	private ArrayList<String> 			 dataSubject;
	private ArrayList<ArrayList<String>> saveVerb;
	private ArrayList<ArrayList<String>> partOfSentence;
	private int 						 inSentence;
	private ArrayList<ArrayList<String>> saveGn;
	private ArrayList<String> 			 currentText;
	private int 						 numberSentence;
	private ArrayList<ArrayList<String>> currentSyntaxe;
	private ArrayList<String> 			 groupsSyntaxeSorted;


	public identifiateSubject(ArrayList<String> containerDataGroupSujet, ArrayList<String> containerDataGroupVerbal,
			ArrayList<String> dataSubject, ArrayList<ArrayList<String>> saveVerb,
			ArrayList<ArrayList<String>> partOfSentence, int inSentence, ArrayList<ArrayList<String>> saveGn, 
			ArrayList<String> currentText, int numberSentence, ArrayList<ArrayList<String>> currentSyntaxe,
			ArrayList<String> groupsSyntaxeSorted) {
	
		this.containerDataGroupSujet  = containerDataGroupSujet;
		this.containerDataGroupVerbal = containerDataGroupVerbal;
		this.dataSubject			  = dataSubject;
		this.saveVerb 				  = saveVerb;
		this.partOfSentence			  = partOfSentence;
		this.inSentence				  = inSentence;
		this.saveGn                   = saveGn;
		this.currentText              = currentText;
		this.numberSentence           = numberSentence;
		this.currentSyntaxe           = currentSyntaxe;
		this.groupsSyntaxeSorted      = groupsSyntaxeSorted;
	}
	
	
	
	public boolean identification() {
		
		String subject = "";
		String toFound = "";


		ArrayList<String> quiFunction = new ArrayList<String>();

		recuperateInformation(containerDataGroupSujet,   quiFunction, "qui");
		boolean isNotEmpty1 = quiFunction.size() > 0;
		if (isNotEmpty1) { subject = quiFunction.get(0); }

		recuperateInformation(containerDataGroupVerbal,  quiFunction, "qui");
		boolean isNotEmpty2 = quiFunction.size() > 0;
		if (isNotEmpty2) { subject = quiFunction.get(0); }

		recuperateInformation(containerDataGroupVerbal,  quiFunction, "subject?");
		boolean isNotEmpty3 = quiFunction.size() > 0;
		if (isNotEmpty3) { toFound = quiFunction.get(0); }

		
		boolean isNotSubject = subject.equalsIgnoreCase("");
		boolean haveInc      = false;
		if (isNotSubject) {
			String[] fromLast = cmpltIsInFactSubjectOfNextVerb(partOfSentence, subject, inSentence);
			subject  = fromLast[0];
			haveInc = fromLast[1].equals("true");
		}

	

		boolean haveToSearchQui = toFound!= "";
		if (haveToSearchQui) {

			String firstLetter = Character.toString(currentText.get(0).charAt(0));
			boolean isntANewSentence = firstLetter.contentEquals(firstLetter.toUpperCase());
			boolean notFirstSentence = numberSentence > 0;
			
			
			ArrayList<String> toSearch = new ArrayList<String>();
			recuperateInformation(containerDataGroupVerbal, toSearch, "isDesign");
			boolean containsIl   = listEqualsElement(toSearch, "il");
			boolean containsElle = listEqualsElement(toSearch, "elle");
			
			if (containsIl && !containsElle) { toSearch.add("elle"); }

			if (!isntANewSentence && notFirstSentence) {

				String lastSubject = saveGn.get(saveGn.size() - 1).get(0);

				boolean isAMatchPronom = listEqualsElement(toSearch, lastSubject);

				
				if (isAMatchPronom) { subject = lastSubject; } 
			}

		}

		subject = isAGrepSubject(subject, groupsSyntaxeSorted, currentSyntaxe, currentText);
		
		
		
		dataSubject.add(subject);
		
		return haveInc;
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	

	private String isAGrepSubject(String subject, ArrayList<String> groupsSyntaxeSorted, 
			ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {



		boolean inRange 	 = inSentence > 0 && partOfSentence.size() > 1;
		int     indexSubject = recuperateIndexInSentenceSubject(subject);
		int     indexSchema  = recuperateLocalitionInSchema(indexSubject, groupsSyntaxeSorted);

		boolean isIndex      = indexSubject != -1 && indexSchema != -1 && indexSchema > 0;
		
		if (inRange && isIndex) {

			String currentSchema = groupsSyntaxeSorted.get(indexSchema);
			String lastSchema    = groupsSyntaxeSorted.get(indexSchema - 1);

			boolean currentIsGprep = currentSchema.contains("GPREP");
			boolean lastIsCmplt    = lastSchema.contains("GCMPLT");

			if (currentIsGprep && lastIsCmplt) { return makeIncrement(currentSchema, lastSchema, currentText); }

		}
		return subject;
	}
	
	
	

	private String makeIncrement(String currentSchema, String lastSchema, ArrayList<String> currentText) {

		int begin = recuperateIndexFromGroup(lastSchema)[0];
		int end   = recuperateIndexFromGroup(currentSchema)[1] + 1;

		List<String> text = currentText.subList(begin, end);

		String increment = ""; for (String word: text) { increment += (word + " "); }

		return increment.substring(0, increment.length() - 1);
	}



	private int recuperateLocalitionInSchema(int indexSubject, ArrayList<String> current) {


		for (int index=0; index < current.size(); index++) {
			
			String schema = current.get(index);

			int[]  indexs = recuperateIndexFromGroup(schema);
			int    begin  = indexs[0];
			int    end    = indexs[1];

			boolean isInSchema = indexSubject >= begin && indexSubject <= end;
			if (isInSchema) { return index; }
			
		}
		return -1;
	}
	
	private int recuperateIndexInSentenceSubject(String subject) {

		int lengthSubject = subject.split(" ").length;
		int matching      = 0;
		int indexSubject  = -1;
		
		
		for (int index=0; index < currentText.size(); index++) {
				
			String  word       = currentText.get(index);
			boolean isMatching = subject.contains(word);

			if (isMatching) { matching += 1; }
			else            { matching = 0;  }
				
			boolean isOurIndex = matching == lengthSubject;
			if (isOurIndex) { indexSubject = index - matching; return indexSubject; }
		}
		return indexSubject;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private String[] cmpltIsInFactSubjectOfNextVerb(ArrayList<ArrayList<String>> partOfSentence, String subject, int inSentence) {

		String[] out = {"", ""};
		
		boolean haveANoSubject = subject.equalsIgnoreCase("") || subject.equalsIgnoreCase(" ");
		boolean haveMulti    = partOfSentence.size() > 1 && inSentence > 0;

		//[[0+1+GSUJET, 2+2+GVERBAL, 3+3+GCNJNC, 4+6+GCMPLT], [7+7+GVERBAL]]
		if (haveANoSubject && haveMulti) {

			ArrayList<String> currentPart = partOfSentence.get(inSentence);
			ArrayList<String> lastPart    = partOfSentence.get(inSentence - 1);

			boolean currentPartHaveVerb = listContains(currentPart, "GVERBAL");
			boolean currentVerbIsFirst  = currentPart.get(0).contains("GVERBAL");
			boolean currentHaveSubject  = listContains(currentPart, "GSUJET");
			
			boolean verbIsWithoutSub    = currentPartHaveVerb && currentVerbIsFirst && !currentHaveSubject;
			boolean lastSchemIsCmplt    = lastPart.get(lastPart.size() - 1).contains("GCMPLT");

			if (verbIsWithoutSub && lastSchemIsCmplt) {
				out[0] = searchGnInLastCmplt(lastPart); out[1] = "true";
				return out;
			}
			
		}
		out[0] = subject; out[1] = "false";
		return out;
	}
	
	private String searchGnInLastCmplt(ArrayList<String> lastPart) {
		
		
		String increment = "";
		String lastSchema =  lastPart.get(lastPart.size() - 1);

		String[] dataLastPart = lastSchema.split("[+]");
		int      begin        = Integer.parseInt(dataLastPart[0]);
		int      end          = Integer.parseInt(dataLastPart[1]) + 1;

		List<ArrayList<String>> partInterestSyntaxe = currentSyntaxe.subList(begin, end);
		List<String> 			partText 			= currentText.subList(begin, end);
		
		for (int index=0; index < partInterestSyntaxe.size(); index++) {
			
			ArrayList<String> current = partInterestSyntaxe.get(index);

			boolean isArt = thisListEqualList(current, this.DETERMINANTS);
			boolean isNm  = listEqualsElement(current, "Nom commun");
			boolean isAdj = listContains(current, "djectif");
			
			String word = partText.get(index);
			
			if (isArt || isNm || isAdj) { increment += word + " "; }
		}
		boolean isIncremented = !(increment.equalsIgnoreCase(""));
		if (isIncremented) { return increment.substring(0, increment.length() - 1); }

		return "";
	}
}

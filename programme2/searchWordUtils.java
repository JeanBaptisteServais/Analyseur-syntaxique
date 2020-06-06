package fr.jbaw.programme2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class searchWordUtils {
	

	protected String[] DETERMINANTS = {"Article indéfini", "Article défini", "Article défini numéral",
			 "Pronom indéfini", "Forme de pronom indéfini", 
			 "pronom défini",
			 "Adjectif indéfini", "Adjectif possessif", "Adjectif démonstratif",
			 "Forme d'Adjectif possessif", "Forme d'Adjectif démonstratif",
		     "déterminants interrogatifs", 
			 "Forme d'article défini", "Forme d'article indéfini",
			 "Forme d’Adjectif possessif", "Forme d’Adjectif démonstratif",
			 "Forme d’article défini", "Forme d’article indéfini", "Adjectif interrogatif",
			 "Adjectif exclamatif", "Adjectif", "Forme d’adjectif", "Préposition", "Article défini",
			 "Forme d’article défini"};
	
	
	
	protected String[] DETERMINANTSWITHOUTPREP = {"Article indéfini", "Article défini", "Article défini numéral",
			 "Pronom indéfini", "Forme de pronom indéfini", 
			 "pronom défini",
			 "Adjectif indéfini", "Adjectif possessif", "Adjectif démonstratif",
			 "Forme d'Adjectif possessif", "Forme d'Adjectif démonstratif",
		     "déterminants interrogatifs", 
			 "Forme d'article défini", "Forme d'article indéfini",
			 "Forme d’Adjectif possessif", "Forme d’Adjectif démonstratif",
			 "Forme d’article défini", "Forme d’article indéfini", "Adjectif interrogatif",
			 "Adjectif exclamatif", "Adjectif", "Forme d’adjectif", "Article défini",
			 "Forme d’article défini"};
	
	

	private String[] PRONOMS = {"je", "tu", "il", "elle", "on", "nous", "vous", "ils", "elles"};


	
	//Proposition by order.
	//Sort the entrance (sentence) dictionary to list.
	//[6+8=GCMPLT, 10+11=GNOMINAL, 9+9=GADV, 0+3=GVERBAL, 4+5=GCMPLT ]   sort to:
	//[0+4+GVERBAL, 4+6+GCMPLT, 6+9+GCMPLT, 9+10+GADV, 10+12+GNOMINAL]

	protected void sortDictionnary(ArrayList<String> groups, Map<String, String> GROUPEINFORMATIONS) {

		ArrayList<Integer> indexList = new ArrayList<Integer>();

		//Recuperate schema key index -begening- .
		for(Entry<String, String> schema: GROUPEINFORMATIONS.entrySet()) {
			int begening = recuperateIndexFromGroup(schema.getKey())[0];
			indexList.add(begening);
		}

		//Sort it by ASC.
		Collections.sort(indexList); 
		
		//Recuperate all information by begening index.
		for (int index: indexList) {
			for(Entry<String, String> schema: GROUPEINFORMATIONS.entrySet()) {
				
				int[]  indexs        = recuperateIndexFromGroup(schema.getKey());
				int    begening      = indexs[0];
				int    end           = indexs[1];
				String groupFunction = schema.getValue().split("[+]")[0];

				boolean isMatching = index == begening;
				String  increment  = Integer.toString(begening) + "+" + Integer.toString(end) + "+" + groupFunction;
				if (isMatching) { groups.add(increment); }
			}
		}
	}




	protected void saveTheCurrentData(ArrayList<ArrayList<ArrayList<String>>> saveSyntaxe, ArrayList<ArrayList<String>> saveSchema, 
									  ArrayList<ArrayList<String>> saveText, ArrayList<String> groupsSyntaxe, 
									  ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//Save currentText, schema and currentSyntaxe.

		saveText.add(currentText);
		saveSyntaxe.add(currentSyntaxe);
		saveSchema.add(groupsSyntaxe);
	}
	
	
	
	
	protected void treatContractionCurrentText(ArrayList<String> currentText) {

		//Replace (contraction=) to (') from avoid error in treatment action. 

		for (int index=0; index < currentText.size(); index++) {

			String  word = currentText.get(index);
			boolean containsContraction = word.contains("contraction=");

			if (containsContraction) {
				String[] contraction = word.split("=");
				
				boolean isOneWord = contraction[1].length() == 1;
				if (isOneWord) { oneWordContraction(contraction,   currentText, index); }
				else 		   { multiWordContraction(contraction, currentText, index); }
			}
		}
	}

	private void oneWordContraction(String[] contraction, ArrayList<String> currentText, int index) {
		
		//
		
		String   wordContra  = contraction[1].substring(0, contraction[1].length() - 1);
		String   increment   = wordContra + "'";
		currentText.set(index, increment);
	}

	private void multiWordContraction(String[] contraction, ArrayList<String> currentText, int index) {
		
		//
		
		String[] words       = contraction[1].split(" ");
		String   wordContra  = words[0].substring(0, words[0].length() - 1);
		String   increment   = wordContra + "' ";
		for (int index1=1; index1 < words.length; index1++) {
			increment += (words[index1] + " ");
		}
		currentText.set(index, increment.substring(0, increment.length() - 1));
	}

	
	
	
	
	
	
	
	protected void cutSentenceInGroupVerbal(ArrayList<String> groupsSyntaxe, ArrayList<ArrayList<String>> partOfSentence) {


		//0+1+GSUJET, 2+4+GVERBAL, 5+11+GCMPLT, 12+12+GCNJNC, 13+13+GVERBAL, 15+16+GSUJET, 17+17+GVERBAL, 18+21+GCMPLT
		//to
		//[0+1+GSUJET, 2+4+GVERBAL, 5+11+GCMPLT, 12+12+GCNJNC,] [13+13+GVERBAL,] [15+16+GSUJET, 17+17+GVERBAL, 18+21+GCMPLT]


		//Make emplacement for propositions. -> proposition is constituate by verbe.
		addingArrayForEachGverbal(groupsSyntaxe, partOfSentence);


		int    index   = 0;
		String subject = "";

		for (String element: groupsSyntaxe) {

			boolean isGv = element.contains("GVERBAL");
			boolean isGs = element.contains("GSUJET");

			boolean containsAlreadyGv = listContains(partOfSentence.get(index), "GVERBAL");

			if (!containsAlreadyGv)          		  { partOfSentence.get(index).add(element); }
			
			if ( containsAlreadyGv && !isGv && !isGs) { partOfSentence.get(index).add(element); }

			if ( isGs && containsAlreadyGv) 		  { subject = element; }

			if ( containsAlreadyGv && isGv) { 
				index += 1;

				boolean hereSubject = !subject.equalsIgnoreCase("");
				if (hereSubject) { partOfSentence.get(index).add(subject);  }
				partOfSentence.get(index).add(element); 

				subject = "";
			}
		}
	}

	private void addingArrayForEachGverbal(ArrayList<String> groupsSyntaxe, ArrayList<ArrayList<String>> partOfSentence) {

		//Make emplacement for proposition.

		int indexGv = 0;
		for (String element: groupsSyntaxe) { 
			boolean isGv = element.contains("GVERBAL");
			if (isGv) { partOfSentence.add(new ArrayList<String>()); indexGv += 1; }
		}

		boolean notFoundGv = indexGv == 0;
		if (notFoundGv) { partOfSentence.add(new ArrayList<String>()); }
	}


	

	protected boolean conditionAddingPreposition(ArrayList<String> groupsSyntaxe, int index, ArrayList<String> GROUPS) {

		//
		
		boolean isNotFirstWord = index > 0;
		boolean lastIsntGv     = !GROUPS.get(GROUPS.size() - 1).contains("GVerbal");

		if (isNotFirstWord && lastIsntGv) {
 
			String  groups        = groupsSyntaxe.get(index - 1);
			String  groupFunction = groups.split("[+]")[2];
			boolean isConjonc     = groupFunction.contains("GCNJNC");
			if (isConjonc) { return true; }
		}
		return false;
	}

	protected void incrementQuiByPossession(Map<String, ArrayList<String>> qui, String subject, String incrementSubject) {
		
		//
		
		ArrayList<String> recuperateDataSubject = qui.get(subject);
		recuperateDataSubject.add(incrementSubject);
		qui.put(subject, recuperateDataSubject);
	}
	

	
	protected void identifiateSubjectOrDetailsOfSentence(boolean verifySubject, 
			ArrayList<String> dataSubject, ArrayList<ArrayList<String>> saveGn) {
		

		//We had a verb without subject.
		//We need to verify: it's a new subject key or a detail of sentence.
		//Like: il mange quand le vent, souffla.

		if (verifySubject) {
			System.out.println("\nHave incremented subject verify");

			ArrayList<String> lastGroupNomi = saveGn.get(saveGn.size() - 1);

			boolean lengthNotOne = lastGroupNomi.size() > 1;
			if (lengthNotOne) { dataSubject.set(0, dataSubject.get(0) + "(noneSubject)"); }

		}
	}
	
	
	
	
	
	
	
	
	
	
	protected void recuperateAllIndexFromGroupInList(ArrayList<String> groupsSyntaxe, String schema, ArrayList<Integer> recuperate) { 
		

		
		for (String group: groupsSyntaxe) {

			String[] schemaSplit = group.split("[+]");
			
			boolean  notEmpty    = schemaSplit.length > 1;
			boolean  matching    = group.contains(schema);

			if (notEmpty && matching) {
				
				int begin = Integer.parseInt(schemaSplit[0]);
				int end   = Integer.parseInt(schemaSplit[1]);
				recuperate.add(begin); recuperate.add(end);
			}
		}
	}
	
	protected boolean searchIfThereIsFunctionInSyntaxe(ArrayList<ArrayList<String>> currentSyntaxe, String search) {
		
		for (ArrayList<String> current: currentSyntaxe) {
			boolean contains = listContains(current, search);
			if (contains) { return true; }
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
	
	protected int[] recuperateIndexFromGroup(String schema) { //cool

		String[] schemaSplit = schema.split("[+]");

		int      begin       = Integer.parseInt(schemaSplit[0]);
		int      end         = Integer.parseInt(schemaSplit[1]);

		int[]  indexs = {begin, end};
		return indexs;
	}
	
	protected boolean searchPrnmInGverbal(String nextSchema, ArrayList<String> currentText, String[] PRONOM) {
		
		String[] schemaSplit = nextSchema.split("[+]");
		int      begin       = Integer.parseInt(schemaSplit[0]);
		int      end         = Integer.parseInt(schemaSplit[1]);
		
		List<String> current = currentText.subList(begin, end);
		
		for (String element: current) {
			boolean      isPrnm  = thisListEqualWord(PRONOMS, element);
			if (isPrnm) { return true; }
		}

		return false;
	}
	

	
	
	
	
	

	
	
	protected void addData(String cmpltSentence, String dataInfo, String adding) {
		cmpltSentence  += (dataInfo + adding);
	}
	
	
	protected String[] treatementDataEqual(String element) {
		
		String[] informations = {"function", "word"};
		
		String[] elementSplit = element.split("=");
		boolean  isNotOne     = elementSplit.length > 1;
		if (isNotOne) {
			informations[0] = elementSplit[0];
			informations[1] = elementSplit[1];
		}
		boolean isLengthThree = elementSplit.length == 3;
		if (isLengthThree) {
			informations[0] = elementSplit[0];
			informations[1] = elementSplit[2];
		}
		boolean containsPoint = elementSplit[0].contains(".");
		if (containsPoint && isNotOne) {
			String[] elementPoint = elementSplit[0].split("[.]");
			informations[0] = elementPoint[0];
			informations[1] = elementPoint[1];
			
		}
		return informations;
	}
	
	
	

	
	
	protected void removeFirstSpace(ArrayList<String> dataSubject) {

		String  subject = dataSubject.get(0);
		boolean subjectNotEmpty = !(subject.equalsIgnoreCase(""));

		if (subjectNotEmpty) {

			String  firstLetter = Character.toString(subject.charAt(0));
			boolean firstCharacterIsEqual = firstLetter.equalsIgnoreCase(" ");
			if (firstCharacterIsEqual) { subject = subject.substring(1, subject.length()); }

			dataSubject.set(0, subject);
		}
	}


	protected void infoGenreMatch(String canBe, Map<String, ArrayList<String>> qui, ArrayList<String> data) {
		String[] genreInfo = {"masculin", "feminin", "pluriel", "singMasc", "plurMasc", "plurFem", "singFem", "sing"};
		
		boolean keyExists = qui.get(canBe) != null;
		if (keyExists) {
			for (String elementQui: qui.get(canBe)) {
				for (String genre: genreInfo) {
					boolean matching = elementQui.equalsIgnoreCase(genre);
					if (matching) { data.add(genre); }
				}
			}
		}
	}




	
	
	

	

	
	
	protected void deletingFromDiscour(ArrayList<String> containerData, String elementDiscours) {
		
		boolean containerDataNotEmpty = containerData.size() > 0;

		if (containerDataNotEmpty) {
			
			Iterator<String> iteration = containerData.iterator();
			while (iteration.hasNext()) {
				String element = (String) iteration.next();
				
				boolean dataContainsDisc = element.toLowerCase().contains(elementDiscours.toLowerCase());
				if (dataContainsDisc) { iteration.remove(); }
			}
		}
	}
	
	
	
	
	 protected void recuperateInformation(ArrayList<String> containerData, ArrayList<String> recuperateData,
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


	
	

	
	
	
	
	
	
	
	






	protected String caseReflmatchingWithPronom(String pronom, String subject) {
		

	
		String[][] ilElleIlsEllesOn = {{"se", "contraction=se", "te", "contraction=te", "ce", "contraction=ce"},
									  {"il", "elle", "ils", "elles", "on"}};
		
		String[][] je = {{"me", "contraction=me"}, {"je"}};
		String[][] tu = {{"te", "contraction=te"}, {"tu"}};
		
		
		
		boolean third  = isMatchingCmpltRef(ilElleIlsEllesOn[0], pronom, ilElleIlsEllesOn[1], subject);
		boolean first  = isMatchingCmpltRef(je[0], pronom, je[1], subject);
		boolean second = isMatchingCmpltRef(tu[0], pronom, tu[1], subject);
		
		if (third || first || second) { return subject + " "; }
		
		return "";
	}
	
	
	protected String isMatchingCmpltRef(String[] ilElleIlsEllesOn, String prnm, String personn) {
	
		for (String element: ilElleIlsEllesOn) {
	
			boolean isMatching = element.equalsIgnoreCase(prnm);
			if (isMatching) { return personn; }
		}

		return "";
	}
	

	protected boolean isMatchingCmpltRef(String[] ilElleIlsEllesOn, String prnm, String[] ilElleIlsEllesOn2, String lastGnString) {
		
		boolean stepOne = false;
		
		for (String element: ilElleIlsEllesOn) {

			boolean isMatching = element.equalsIgnoreCase(prnm);
			if (isMatching && !stepOne) { stepOne = true; }

		}

		
		boolean stepTwo = false;
		
		for (String element: ilElleIlsEllesOn2) {
			
			boolean prnmContainsInterro = lastGnString.contains("?");
			if (prnmContainsInterro) { element = "?" + element; }

			boolean isMatching = element.equalsIgnoreCase(lastGnString);
			if (isMatching && !stepTwo) { stepTwo = true; }
		}
		
		if (stepOne && stepTwo) { return true; }
		
		return false;
	}

	
	
	
	
	

	

	
	




	
	
	protected boolean incrementOrNotInActionButInSaveGn(ArrayList<String> groupsSyntaxe, int index, String[] groupNominal) {
		
		
		boolean canVerify = index > 0;
		boolean canIncrementInAction = false;
		if (canVerify) { 
			String lastFunction  = groupsSyntaxe.get(index - 1);
			canIncrementInAction = !lastFunction.contains("GCNJNC");
		}

	
		boolean prepoNotEmpty = !groupNominal[0].equalsIgnoreCase("quoi=");
		if (prepoNotEmpty) { System.out.println("isGroupePreposi " + groupNominal[0]); }
		
		if (!canIncrementInAction) { System.out.println("add last gn to saveGn but not in action"); }
		
		
		return false;
	}




	protected void recuperatePronomOfVerbe(ArrayList<String> pronom, ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Search pronom in syntaxe in case we havnt got pronom from groupVerbal
		
		boolean pronomNotEmpty = pronom.size() > 0;

		if (!pronomNotEmpty) {
			for (ArrayList<String> current: currentSyntaxe) {
				boolean containsVerb = listContains(current, "verbe#");
				boolean notEmpty     = current.get(0).split("#").length > 1;
				if (containsVerb && notEmpty) { 

					String verbe    = current.get(0).split("#")[1];
					boolean partVerbNotEmpty = verbe.split(" ").length > 1;

					if (partVerbNotEmpty) {
						String partVerb = verbe.split(" ")[0];
						pronom.add(partVerb);
					}
				}
			}
		}
	}
	
	
	
	
	protected boolean textContains(ArrayList<String> currentText, String string) {
		for (String element: currentText) {
			boolean isMatching = element.contains(string);
			if (isMatching) { return true; }
		}
		return false;
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

	
	
	
	
	protected boolean verifyPosition(String subject, String otherSubject, ArrayList<String> currentText) {
		
		
		//We modify our lastGn savegarde. Here we need know subject position and an other subjet.
		
		int positionSubject = -1;
		
		int index=0;
		for (String element: currentText) {

			boolean elementContainsString = element.contains(subject);
			boolean stringContainsElement = subject.contains(element);

			if (elementContainsString || stringContainsElement) { positionSubject = index; }
			
			index++;
		}
	
		
		int positionOtherSubject = -1;
		
		int index1=0;
		for (String element: currentText) {

			boolean elementContainsString = element.contains(otherSubject);
			boolean stringContainsElement = otherSubject.contains(element);

			if (elementContainsString || stringContainsElement) { positionOtherSubject = index1; }
			
			index1++;
		}
		
		boolean areNotEmpty   = (positionSubject != -1) && (positionOtherSubject != -1);
		boolean subjectBefore = positionSubject < positionOtherSubject;
		
		if (subjectBefore && areNotEmpty) { return true; }
		return false;
	}
	
	

	
	
	 protected String recuperateInformation(ArrayList<String> containerData, String data) {


		 for (String element: containerData) {

			 String[] elementSplit = element.split("=");
			 String   function = elementSplit[0];
			 String   word     = elementSplit[1];

			 boolean isElementSearch = function.equalsIgnoreCase(data);
			 if     (isElementSearch) { return word; }
		 }
		 return "";
	 }
	
	
	
	
	protected void incrementation(ArrayList<String> containerPrep, ArrayList<String> containerVerb, 
			ArrayList<String> containerCmplt, ArrayList<String> containerSujet, ArrayList<String> containerGnomi,
			ArrayList<String> container, ArrayList<ArrayList<String>> saveGn) {
		

		boolean notEmpty1 = containerPrep.size() > 0;
		boolean notEmpty2 = containerVerb.size() > 0;
		boolean notEmpty3 = containerCmplt.size() > 0;
		boolean notEmpty4 = containerSujet.size() > 0;
		boolean notEmpty5 = containerGnomi.size() > 0;

	
		if (notEmpty1) { transfereData(containerPrep, container, saveGn); }
		if (notEmpty2) { transfereData(containerVerb, container, saveGn); }

		if (notEmpty3) { transfereData(containerCmplt, container, saveGn); }

		if (notEmpty4) { transfereData(containerSujet, container, saveGn); }
		if (notEmpty5) { transfereData(containerGnomi, container, saveGn); }
		

	}
	


	protected void incrementationVerbe(ArrayList<String> containerVerb, ArrayList<String> container, 
			ArrayList<ArrayList<String>> saveVerbe) {
		
	
		boolean notEmpty = containerVerb.size() > 0;

		if (notEmpty) { transfereData(containerVerb, container, saveVerbe); }

	}
	
	


	private void transfereData(ArrayList<String> container1, ArrayList<String> container2, ArrayList<ArrayList<String>> saveGn) {

		for (String element: container1) { container2.add(element); }

		saveGn.add(container2);
		System.out.println("SaveGn sauvegarde : " + container2);
	}
	
	protected String listContainsRecuperate(ArrayList<String> List, String elementToMatch) {

		String recuperate = ""; 
		
		for (String element: List) {if (element.contains(elementToMatch)) {recuperate = element;}}
		return recuperate;
	}
	

	protected int[] recuperateIndexOfSchema(ArrayList<String> sauvegarde, String string) {
		
		int[] indexs = {-1, -1};
		
		for (String schems: sauvegarde) {
			
			boolean contains = schems.contains(string);
			if (contains) {
				
				String[] schemaSplit = schems.split("[+]");
				indexs[0] = Integer.parseInt(schemaSplit[0]);
				indexs[1] = Integer.parseInt(schemaSplit[1]) + 1;

			}
		}
		return indexs;
	}
	
	protected void treatementIncrements(String increment, ArrayList<String> container) {
		
		boolean incrementIsntEmpty = !increment.equalsIgnoreCase("");
		if (incrementIsntEmpty) { increment = increment.substring(0, increment.length() - 1); }
		
		container.add(increment);
		
		if (!incrementIsntEmpty) { container.clear(); }
		
	}
	
	
	
	protected String incrementQuiSubject(String[] data, String increment) {
		

		for (String element: data) { 

			boolean elementNotEmpty = !element.equalsIgnoreCase("");

			if (elementNotEmpty) { increment += (element + " "); }
		}

		return increment;
	}
	
	protected String recuperateFromList(ArrayList<String> current, ArrayList<String> currentText, int index, String[] search) {
		
		
		boolean isContains = listEquals(current, search);
		if (isContains) { return currentText.get(index); }

		return "";
	}

	
	
	protected String recuperateContains(ArrayList<String> current, ArrayList<String> currentText, int index, String search) {
		
		
		boolean isContains = listContains(current, search);
		if (isContains) { return currentText.get(index); }
		

		return "";
	}
	
	
	protected String recuperateDataEqual(ArrayList<String> current, ArrayList<String> currentText, int index, String search) {
		
		boolean isContains = listEqualsElement(current, search);
		if (isContains) { return currentText.get(index); }
		

		return "";
	}
	
	
	
	protected boolean searchInGroup(ArrayList<String> saveSchema, String search) {
		

		for (String schema: saveSchema) {

			boolean isContains = schema.contains(search);
			if (isContains) { return true; }
		}

		return false;
	}
	
	protected boolean searchInSchema(ArrayList<ArrayList<String>> saveSchema, String search) {
		

		for (ArrayList<String> schema: saveSchema) {
			boolean isContains = listContains(schema, search);
			if (isContains) { return true; }
		}

		return false;
	}
	
	
	protected void removeFromList(ArrayList<String> List, String element) {

		Iterator<String> i = List.iterator();
		while (i.hasNext()) {
			String str = (String) i.next();
			if (str.equals(element)) {i.remove();}
		}
	}
	
	protected void removeFromListContains(ArrayList<String> List, String element) {

		Iterator<String> i = List.iterator();
		while (i.hasNext()) {
			String str = (String) i.next();
			if (str.contains(element)) {i.remove();}
		}
	}
	
	
	
	protected boolean elementNotEmpty(ArrayList<String> list) {
		
		boolean notEmpty = false;
		
		for (String element: list) {
			boolean lengthSupp = element.length() > 1;
			if (!notEmpty && lengthSupp) { notEmpty = true; }
		}
		return notEmpty;
	}
	
	
	
	// IN identifiateNonePersonButGroupNominal recuperate indexs of schemas
	protected void recuperateIndexsFromSchema(ArrayList<String> lastSchema, int[] indexs, String search) {

		for (String element: lastSchema) {

			boolean elementIsMatching = element.contains(search);
			if (elementIsMatching) {

				String[] elementSplit = element.split("[+]");
				indexs[0] = Integer.parseInt(elementSplit[0]);
				indexs[1] = Integer.parseInt(elementSplit[1]);
				break;
			}
		}
	}
	
	
	protected String[] recuperateElements(ArrayList<ArrayList<String>> lastSyntaxe, 
							   ArrayList<String> lastText, int[] indexs, 
							   Map<String, ArrayList<String>> qui) {
		
		//Recuperate from syntaxe elements interests.
		
		int begening = indexs[0];
		int end      = indexs[1] + 1;

		boolean isASentence = (begening != -1) && (end != -1); 
		
		String pronomFound = "";
		String groupVerbal = "";
		
		if (isASentence) {
		
			List<ArrayList<String>> syntaxe = lastSyntaxe.subList(begening, end);
			List<String> 			text    = lastText.subList(begening, end);
	
			
			for (int index=0; index < syntaxe.size(); index++) {

				ArrayList<String> current = syntaxe.get(index);
				String            word    = text.get(index).toLowerCase();
				
				boolean isPronom = listEqualsElement(current, "Pronom personnel");

				if (isPronom) {
					boolean keyExists1 = (qui.get(word) != null);
					boolean keyExists2 = (qui.get("?" + word) != null);
					if (keyExists1 || keyExists2) { pronomFound = word; }	
				}
				else { groupVerbal += (word + " "); }
	
			}
			
			boolean groupVerbalNotEmpty = groupVerbal.length() > 0;
			if (groupVerbalNotEmpty) { groupVerbal.substring(0, groupVerbal.length() - 1); }
		
		}
		
		String[] data = {groupVerbal, pronomFound};
		
		return data;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//COMPLEMENT COD
	String searchAGNIfsearchingCmpltCod(int numberSentence, 
										ArrayList<ArrayList<String>> saveSchema, 
										ArrayList<ArrayList<ArrayList<String>>> saveSyntaxe, 
										ArrayList<ArrayList<String>> saveText, 
										ArrayList<ArrayList<String>> saveCod) {

		boolean isNotFirstWord = numberSentence > 0 && saveSchema.get(saveSchema.size() - 1).size() > 0;
		String  NomCommun      = "";
		String  determinant    = "";


		if (isNotFirstWord) {

			int OneBeforeIndex = numberSentence - 1;
			ArrayList<String> lastSchems = saveSchema.get(saveSchema.size() - 1);
			String lastSchema = lastSchems.get(lastSchems.size() - 1);
			int    begin      = Integer.parseInt(lastSchema.split("[+]")[0]);
			int    end        = Integer.parseInt(lastSchema.split("[+]")[1]) + 1;
	

			List<ArrayList<String>> syntaxe = saveSyntaxe.get(saveSyntaxe.size() - 1).subList(begin, end);
			List<String>            text    = saveText.get(saveText.size() - 1).subList(begin, end);
	
			System.out.println(syntaxe);


			int index = 0;
			for (ArrayList<String> element: syntaxe) {
				boolean containsNc  = listEqualsElement(element, "Nom commun");
				boolean containsDet = listEquals(element, DETERMINANTS);
				if (containsNc)  { NomCommun   = text.get(index); }
				if (containsDet) { determinant = text.get(index); }
				index++;
			}
	
			boolean haveFoundNomCommun = NomCommun != "";
			if (haveFoundNomCommun)  { return determinant + " " + NomCommun; }
			
			boolean saveCodNotEmpty     = saveCod.size() > 0 && saveCod.size() > OneBeforeIndex;
			if (saveCodNotEmpty) {
				boolean lastSaveCodNotEmpty = saveCod.get(OneBeforeIndex).size() >= 1; 
				if (!haveFoundNomCommun && lastSaveCodNotEmpty) {
					String lastCod = saveCod.get(OneBeforeIndex).get(0);
					boolean isntEmpty = lastCod != "";
					boolean haveFound = lastCod.split("=").length > 1;
					if (isntEmpty && haveFound) { return lastCod.split("=")[1]; }
				}
			}
		}
		return NomCommun;
	}
	
	
	
	//identifiateTypeOfDeterminant
	
	//Sometimes multi determinant separate by virgule.
	protected void treatementDeterminantWithVirgule(ArrayList<String> multiDeterminant, 
													ArrayList<String> containerDataGroupCmplt) {
		
		for (String   element: containerDataGroupCmplt) {
			 String[] elementSplit = element.split("=");
			 String   function     = elementSplit[0];
			 String   determinant  = elementSplit[1];

			 boolean isSearch   	 = function.equalsIgnoreCase("detQuoi");
			 boolean containsVirgule = determinant.contains(","); 
			 
			 if (isSearch && containsVirgule) {
				 String[] determinantSplit = determinant.split("[,]");
				 for (String det: determinantSplit) { multiDeterminant.add("detQuoi=" + det); }
			 }
		}
	}


	

	
	
	
	
	
	
	
	//IN isComplement CASES
	
	
	protected String caseNomPropre(String data, ArrayList<ArrayList<String>> currentSyntaxe, 
								   ArrayList<String> currentText) {

		boolean containsEq    = data.contains("=");
		String  syntaxeWord   = "";

		if (containsEq) {
			int indexWord = currentText.indexOf(data.split("=")[1]);
			boolean isntEmpty = indexWord != -1;
			if (isntEmpty) { syntaxeWord   = currentSyntaxe.get(indexWord).get(0); }
		}
		return syntaxeWord;
	}

	
	protected boolean casePrepDetNc(int index, ArrayList<ArrayList<String>> currentSyntaxe, 
									ArrayList<String> currentText, String[] DETERMINANTS) {
		
		boolean canBePerson   = false;
		boolean isLessLength  = index + 2 < currentSyntaxe.size();

		if (isLessLength) {
			boolean isPreposition = listEqualsElement2(currentSyntaxe, index,     "Préposition");
			boolean isDeterminant = thisListEqualList2(currentSyntaxe, index + 1,  DETERMINANTS);
			boolean isNomCommun   = listEqualsElement2(currentSyntaxe, index + 2,  "Nom commun");

			boolean isà           = currentText.get(index).equalsIgnoreCase("à");
		
			if (isPreposition && isDeterminant && isNomCommun && isà) { canBePerson = true; }
		}
		return canBePerson;
	}
	
	
	
	
	

	
	
	
	
	
	
	//PARTIE SEARCH IN LIST
	
	
	
	boolean listEquals(ArrayList<String> List, String[] elementToMatch) {

		boolean contains = false;

		boolean isNotNull = elementToMatch != null;
		
		if (isNotNull) {
		
			for (String element: List) {
				for (String match: elementToMatch) {
					if (element.equalsIgnoreCase(match) && !contains) {contains = true;}
				}
			}
		}
		return contains;
	}
	
	

	String getInformationsEqual(java.util.List<String> container, String word, String function) {
	
		for (String  element: container) {
			String[] infoElement = element.split("=");
			boolean  isNotEmpty  = infoElement.length > 1;
	
			if (isNotEmpty) {
				boolean  isElement   = infoElement[1].equalsIgnoreCase(word);
				boolean  isFunction  = infoElement[0].equalsIgnoreCase(function);
				if (isElement && isFunction) { return infoElement[1]; }
			}
			
			boolean  caseContraction  = infoElement.length == 3;
			if (caseContraction) {
				String   contraction = infoElement[1] + "=" + infoElement[2];
	
				boolean  isElement   = contraction.equalsIgnoreCase(word);
				boolean  isFunction  = infoElement[0].equalsIgnoreCase(function);
				if (isElement && isFunction) { return infoElement[2]; }
			}
			
		}
	
		return null;
	}
	
	
	String getInformationsContains(java.util.List<String> container, String word, String function) {
	
		for (String  element: container) {
			String[] infoElement = element.split("=");
			boolean  isNotEmpty  = infoElement.length > 1;
	
			if (isNotEmpty) {
				boolean  isElement  = infoElement[1].equalsIgnoreCase(word);
				boolean  isFunction = infoElement[0].contains(function);
	
				if (isElement && isFunction) { return infoElement[0]; }
			}
		}
	
		return null;
	}
	
	
	//temps.passé=était
	String getInformationsPoint(java.util.List<String> container, String word, String function) {
	
		for (String  element: container) {
	
			String[] infoElement   = element.split("=");
			boolean  isNotEmpty    = infoElement.length > 1;
			boolean  containsPoint = infoElement[0].contains(".");
	
			if (isNotEmpty && containsPoint) {
				
				String   tempsInfo     = infoElement[0].split("[.]")[1];
	
				boolean  isElement  = infoElement[1].equalsIgnoreCase(word);
				boolean  isFunction = infoElement[0].split("[.]")[0].equalsIgnoreCase(function);
	
				if (isElement && isFunction) { return tempsInfo; }
			}
		}
	
		return null;
	}
	
	
	protected boolean multiSyntaxeFollowingContains(java.util.List<ArrayList<String>> syntaxe, int begening, String function, int aftering) {
		for (int index=begening; index < syntaxe.size(); index++) {
			ArrayList<String> current = syntaxe.get(index);
			for (String element: current) {

				boolean isMatching   = element.contains(function);
				boolean isIndexAfter = (index - begening) == aftering;
				if (isMatching && isIndexAfter) {
					return true;
				}
			}
		}
		return false;
	}

	
	
	
	protected boolean listEqualsElement2(ArrayList<ArrayList<String>> currentSyntaxe, int index, String elementToMatch) {
		boolean contains = false;
		ArrayList<String> List = currentSyntaxe.get(index);
		for (String element: List) {
			if (element.equalsIgnoreCase(elementToMatch) && !contains) {contains = true;}
		}
		return contains;
	}

	boolean thisListEqualList2(ArrayList<ArrayList<String>> currentSyntaxe, int index, String[] thisList) {
		boolean isEqual = false;
		ArrayList<String> List = currentSyntaxe.get(index);
		for (String element1: List) {
			for(String element2: thisList) {
				if (element1.equalsIgnoreCase(element2) && !isEqual) {isEqual = true;}
			}
		}
		return isEqual;
	}
	
	protected boolean listContains(ArrayList<String> List, String elementToMatch) {
		boolean isEqual = false; 
		for (String element: List) {if (element.contains(elementToMatch) && !isEqual) {isEqual = true;}}
		return isEqual;
	}
	
	
	protected boolean listEqualsElement(ArrayList<String> List, String elementToMatch) {
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
	

	protected static boolean thisListEqualWord(String[] thisList, String word) {
		boolean isEqual = false;
		for (String element: thisList) {if (element.equalsIgnoreCase(word) && !isEqual) {isEqual = true;}}
		return isEqual;
	}
	
	


	protected boolean thisListEqualContains(String[] thisList, String word) {
		boolean isEqual = false;
		for (String element: thisList) {if ((element.contains(word) || word.contains(element)) && !isEqual ) {isEqual = true;}}
		return isEqual;
	}

}

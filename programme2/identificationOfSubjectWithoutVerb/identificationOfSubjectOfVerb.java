package fr.jbaw.programme2.identificationOfSubjectWithoutVerb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;





public class identificationOfSubjectOfVerb extends utilsSubject{

	
	private ArrayList<ArrayList<String>> saveSchema;
	private ArrayList<ArrayList<String>> saveGn;
	private ArrayList<String>            dataAction;
	private ArrayList<ArrayList<String>> currentSyntaxe;
	private ArrayList<String>            currentText;
	private ArrayList<ArrayList<String>> saveText;
	private String[] 					 DETERMINANTS;
	private ArrayList<ArrayList<String>> inSearchingGnPronom;
	private ArrayList<String>            containerDataGroupVerbal;
	private ArrayList<ArrayList<String>> saveVerb;
	private ArrayList<String> 			 dataSubject;
	private ArrayList<ArrayList<String>> partOfSentence;
	private int 						 inSentence;
	private Map<String, ArrayList<String>> quoi;
	private ArrayList<ArrayList<String>> saveGardeCmplt;
	private ArrayList<ArrayList<String>> prepAdv;
	private Map<String, ArrayList<String>> qui;
	private int 						   numberSentence;


	public identificationOfSubjectOfVerb(ArrayList<ArrayList<String>> saveSchema, ArrayList<ArrayList<String>> saveGn, 
										 ArrayList<String> dataAction, ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText,
										 ArrayList<ArrayList<String>> saveText, String[] DETERMINANTS, 
										 ArrayList<String> containerDataGroupVerbal, ArrayList<ArrayList<String>> inSearchingGnPronom,
										 ArrayList<ArrayList<String>> saveVerb, ArrayList<String> dataSubject, boolean[] toReturn,
										 ArrayList<ArrayList<String>> partOfSentence, int inSentence, Map<String, ArrayList<String>> quoi, 
										 ArrayList<ArrayList<String>> saveGardeCmplt,
										 ArrayList<ArrayList<String>> prepAdv, Map<String, ArrayList<String>> qui,
										 int numberSentence) {
	

		this.saveSchema 	= saveSchema;
		this.saveGn     	= saveGn;
		this.dataAction	    = dataAction;
		this.currentSyntaxe = currentSyntaxe;
		this.currentText    = currentText;
		this.saveText       = saveText;
		this.DETERMINANTS   = DETERMINANTS;
		this.saveVerb       = saveVerb;
		this.dataSubject    = dataSubject;
		
		this.inSearchingGnPronom      = inSearchingGnPronom;
		this.containerDataGroupVerbal = containerDataGroupVerbal;
		
		this.partOfSentence = partOfSentence;
		this.inSentence     = inSentence;
		this.quoi		    = quoi;
		
		this.saveGardeCmplt = saveGardeCmplt;
		this.prepAdv        = prepAdv;
		this.qui            = qui;
		this.numberSentence = numberSentence;

		
		
		
		boolean dataSubjectEmpty            = dataSubject.size() == 1 &&  dataSubject.get(0).equalsIgnoreCase("");
		boolean dataActionNotEmpty          = dataAction.size()   > 0 &&  !dataAction.get(0).equalsIgnoreCase("");
		boolean subjectIsPrnmAndLastNoFound = subjectIsPrnmAndLastNoFoundBoolean(inSearchingGnPronom, dataSubject);

		
		if (subjectIsPrnmAndLastNoFound) {
			
			//assise. je mange

			toReturn[1] = recuperatePronom(dataSubject, saveText, quoi, numberSentence);
		}
		

		else if(dataSubjectEmpty && dataActionNotEmpty) {


			//Searching group nominals after verb.
			ArrayList<String> gn = new ArrayList<String>();

			boolean[] conditions = conditionToIdentifiateSubject(gn, partOfSentence, inSentence);

			boolean   gnNotEmpty               = conditions[0];
			boolean   isNotFirstVerbInSentence = conditions[1];
			boolean   weHaveLastSubject        = conditions[2];
			boolean   discoursIsFinish         = conditions[3];
			boolean   lastCmpltCurrentVrb      = conditions[5];
			boolean   lastGprepCurntVerb       = conditions[6];
			boolean   sameSentenceGrel         = conditions[7];
			
			
			boolean condition1     = (gnNotEmpty && isNotFirstVerbInSentence && weHaveLastSubject) || discoursIsFinish;
			boolean condition2     = !gnNotEmpty || !isNotFirstVerbInSentence;
			boolean inSameSentence = lastCmpltCurrentVrb || sameSentenceGrel;
			boolean condition4     = lastGprepCurntVerb;

			
			if      (condition4) {
				System.out.println("\n\nidentifiateSubjectToVerb - identificationOfSubjectOfVerb - 5");
				recuperatePrnmLastPartSentence(partOfSentence, inSentence, dataSubject);
			}

			
			

			else if (inSameSentence) { 
				
				if (lastCmpltCurrentVrb) {
					////[[0+1+GVERBAL, 2+10+GCMPLT], [12+12+GVERBAL, 13+14+GCMPLT]]
					System.out.println("\n\nidentifiateSubjectToVerb - identificationOfSubjectOfVerb - 4 - 0");
					toReturn[2] = recuperateLastGnFromPropositionInSameSentence(partOfSentence, inSentence, dataSubject); 
				}
				else if (sameSentenceGrel) {
					//[[0+1+GVERBAL, 2+10+GCMPLT, 11+11+GREL], [12+12+GVERBAL, 13+14+GCMPLT, 15+21+GPREP, 22+24+GPREP]]
					System.out.println("\n\nidentifiateSubjectToVerb - identificationOfSubjectOfVerb - 4 - 1");
					sameSentenceLastSchemIsGrel(partOfSentence, inSentence, dataSubject);
				}
			}

			
			
			else if (condition1) { 
				System.out.println("\n\nidentifiateSubjectToVerb - identificationOfSubjectOfVerb - 3");
				idefiateSubjectFromNextGn(dataSubject, gn); }

			
			
			

			else if (condition2) { 
				
				//Recuperate last group nominal from saveGn.


				System.out.println("\n\nidentifiateSubjectToVerb - identificationOfSubjectOfVerb - 2");
				boolean[] identifiate = idenfiateSubjectBySaveGn(dataSubject);
				toReturn[0] = identifiate[0];
				toReturn[3] = identifiate[1];
			}
			
			
			
			
			
			else    { 
				
				//Search pronom who conjugate verbe.
				
				System.out.println("\n\nidentifiateSubjectToVerb - identificationOfSubjectOfVerb - 1");
				toReturn[1] = recuperatePronom(dataSubject, saveText, quoi, numberSentence); }
			

		}
		
		

		//Increment or not pronomSavegarde.
		incrementinSearchingGnPronom(toReturn[1]);
		
		
		
		
		
		

	}
	





	//Make our conditions for search subject.
	public boolean[] conditionToIdentifiateSubject(ArrayList<String> gn, 
			ArrayList<ArrayList<String>> partOfSentence, int inSentence) {



		//After guillemet
		boolean discoursIsFinish = searchIfLastWordLastSentenceIsGuillemet(saveText);
		
		//Subject can be after verbe.
		searchIfGnAfterVerb(dataAction, currentSyntaxe, currentText, gn, discoursIsFinish);

		boolean gnNotEmpty               = gn.size() > 0;
		
		//Surginrent les cannibales. <- first word's verb.
		boolean isNotFirstVerbInSentence = verifyIfFirstVerbInSentence(dataAction, saveSchema, currentText);

		boolean weHaveLastSubject        = saveGn.size() > 0;

		boolean inCurntSentenceNoSubj    = listContains(partOfSentence.get(inSentence), "");
		boolean lastIsCmpltOrGn          = false;
		

		//No subject because cut in sentence.
		boolean lastCmpltCurrentVrb  = conditionCutSentenceSubject(partOfSentence, inSentence);

		boolean gprepSubject = conditionGrepSubject(partOfSentence, inSentence);
		
		boolean sameSentenceGrel = inSameSentenceGrel(partOfSentence);

		
		boolean[] conditions = {gnNotEmpty, isNotFirstVerbInSentence, weHaveLastSubject,
								discoursIsFinish, isNotFirstVerbInSentence, lastCmpltCurrentVrb,
								gprepSubject, sameSentenceGrel};

		return conditions;
		
	}

	private boolean conditionGrepSubject(ArrayList<ArrayList<String>> partOfSentence, int inSentence) {

		boolean           parSentenceNotOne = partOfSentence.size() > 1 && inSentence > 0;
		ArrayList<String> currentSentence   = new ArrayList<String>();
		ArrayList<String> lastSentence      = new ArrayList<String>();
		String 			  lastSchema        = "";
		boolean 		  verbeIsFirst 	    = false;

		if (parSentenceNotOne) {
			currentSentence = partOfSentence.get(inSentence);
			lastSentence    = partOfSentence.get(inSentence - 1);
			lastSchema      = lastSentence.get(lastSentence.size() - 1);
			verbeIsFirst    = currentSentence.get(0).contains("GVERBAL");
		}
		
		boolean lastIsGprep  = lastSchema.contains("GPREP");
		
		if (lastIsGprep && verbeIsFirst) { return true; }
		
		return false;
	}


	private boolean conditionCutSentenceSubject(ArrayList<ArrayList<String>> partOfSentence, int inSentence) {

		//[[0+2+GVERBAL, 4+6+GCMPLT], [7+10+GVERBAL, 11+12+GCMPLT, 13+15+GPREP, 16+16+GREL], [17+18+GVERBAL, 19+22+GCMPLT]]

		boolean           parSentenceNotOne = partOfSentence.size() > 1 && inSentence > 0;
		ArrayList<String> currentSentence   = new ArrayList<String>();
		ArrayList<String> lastSentence      = new ArrayList<String>();
		String 			  lastSchema        = "";
		boolean 		  verbeIsFirst 	    = false;

		if (parSentenceNotOne) {
			currentSentence = partOfSentence.get(inSentence);
			lastSentence    = partOfSentence.get(inSentence - 1);
			lastSchema      = lastSentence.get(lastSentence.size() - 1);
			verbeIsFirst    = currentSentence.get(0).contains("GVERBAL"); 
		}

		boolean noSubject    = dataSubject.get(0).equalsIgnoreCase("");
		boolean currentNoGs  = listContains(currentSentence, "GVERBAL");
		boolean lastGn       = lastSchema.contains("GCMPLT") || lastSchema.contains("GNOMINAL");


		boolean condition3 = noSubject && currentNoGs && lastGn && verbeIsFirst;
		return condition3;
	}
	
	
	private boolean conditionsLastOnlyGnLastx2IsGnSoItsSameGn(ArrayList<ArrayList<String>> saveSchema) {
		
		boolean notEmptySchema = saveSchema.size() > 2;
		
		if (notEmptySchema) {
		
			ArrayList<String> lastX2Schem     = saveSchema.get(saveSchema.size() - 3);
			String            lastX2LastSchem = "";
			boolean notEmpty = lastX2Schem.size() > 0;
			if (notEmpty) { lastX2Schem.get(lastX2Schem.size() - 1); }
			
			
			ArrayList<String> lastSchem       = saveSchema.get(saveSchema.size() - 2);
			
			String  lastLastSchem   = "";
			boolean inRangeOfSchema = saveSchema.get(saveSchema.size() - 2).size() > 0;
			if (inRangeOfSchema) { lastLastSchem   = lastSchem.get(lastSchem.size() - 1); }

			
			boolean lastX2IsGn    = lastX2LastSchem.contains("GNOMINAL");
			
			boolean lastIsOnlyOne = lastSchem.size() == 1;
			boolean lastIsGn      = lastLastSchem.contains("GNOMINAL");
			
			boolean conditions = lastX2IsGn && lastIsOnlyOne && lastIsGn;
			return  conditions;
		}
		return  false;
	}
	
	
	private boolean inSameSentenceGrel(ArrayList<ArrayList<String>> partOfSentence) {
		
		//[[0+1+GVERBAL, 2+10+GCMPLT, 11+11+GREL], [12+12+GVERBAL, 13+14+GCMPLT, 15+21+GPREP, 22+24+GPREP]]

		boolean notEmpty = partOfSentence.size() > 1 && inSentence > 0 && partOfSentence.get(inSentence - 1).size() > 1;
		
		if (notEmpty) {

			ArrayList<String> currentSentence = partOfSentence.get(inSentence);
			String currentVrb    = currentSentence.get(0);
			
			ArrayList<String> lastSentence    = partOfSentence.get(inSentence - 1);
			String lastSchema    = lastSentence.get(lastSentence.size() - 1);
			String lastSchemaX2  = lastSentence.get(lastSentence.size() - 2);
			
			boolean isGrel  = lastSchema.contains("GREL");
			boolean isCmplt = lastSchemaX2.contains("GCMPLT");
			boolean isVrb   = currentVrb.contains("GVERBAL");

			if (isGrel && isCmplt && isVrb) { return true; }
		}
		

		return false;
	}
	

	

	
	private boolean sameSentenceLastSchemIsGrel(ArrayList<ArrayList<String>> partOfSentence, int inSentence, ArrayList<String> dataSubject) {

		String[] toSearch = {"Nom commun", "Nom propre"};


		ArrayList<String> lastSentence    = partOfSentence.get(inSentence - 1);
		String lastSchemaX2Cmplt = lastSentence.get(lastSentence.size() - 2);


		String[] data = lastSchemaX2Cmplt.split("[+]");
		
		int begin = Integer.parseInt(data[0]);
		int end   = Integer.parseInt(data[1]) + 1;

		List<String> 			text 	= currentText.subList(begin, end);
		List<ArrayList<String>> syntaxe = currentSyntaxe.subList(begin, end);

		int    index     = 0;
		String increment = "";
		for (ArrayList<String> current: syntaxe) {
			
			boolean containsNc = thisListEqualList(current, toSearch);
			if (containsNc) { increment = text.get(index); }
			index++;
		}

		dataSubject.clear(); dataSubject.add(increment);
		System.out.println("identifiateSubjectToVerb - identificationOfSubjectOfVerb - sameSentenceLastSchemIsGrel");
	
		return false;
	}
	
	
	
	
	protected void searchIfGnAfterVerb(ArrayList<String> dataAction, ArrayList<ArrayList<String>> currentSyntaxe,
			   ArrayList<String> currentText, ArrayList<String> gn, boolean discoursIsFinish) {


		String  firstWord         = Character.toString(currentText.get(0).charAt(0));	
		boolean isFirstSentence   = firstWord.contentEquals(firstWord.toUpperCase());

		if (isFirstSentence || discoursIsFinish) {

			boolean searchingEnclench = false;
			int     enclenched = -1;

			for (int index=0; index < currentText.size(); index++) {

				boolean lessSize = index < currentSyntaxe.size();

				if (lessSize) {

					String            word    = currentText.get(index);
					ArrayList<String> current = currentSyntaxe.get(index);

					boolean isMatching = listEqualsElementWithout(dataAction, word, "=");
					if (isMatching) { searchingEnclench = true; enclenched = index; } 

					boolean isArt    = thisListEqualList(current, DETERMINANTS);
					boolean isNc     = listEqualsElement(current, "Nom commun");
					boolean isNp     = listEqualsElement(current, "Nom propre");
					boolean notToFar = (index - enclenched) <= 3 && enclenched != -1;

					
					if      ((isArt || isNc || isNp) && notToFar) { 
						System.out.println("identifiateSubjectToVerb - conditionToIdentifiateSubject - searchIfGnAfterVerb"); }
					
					if      ((isArt || isNc || isNp) && notToFar) { gn.add(word); }
					else if (isNc || isNp || !notToFar)           { searchingEnclench = false; }
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	




	public boolean[] idenfiateSubjectBySaveGn(ArrayList<String> dataSubject) {
		
		boolean[] outIdentifiate = {false, false};
		
		boolean saveSchemaNotEmpty = saveSchema.size() > 1;
		if (saveSchemaNotEmpty) {
	
			boolean isSameGn = conditionsLastOnlyGnLastx2IsGnSoItsSameGn(saveSchema);
			
			ArrayList<String> lastGroupNomi = new ArrayList<String>();
			ArrayList<String> lastSchem     = saveSchema.get(saveSchema.size() - 2);

			boolean lastSchemHaveVerb = listContains(saveSchema.get(saveSchema.size() - 2), "GVERBAL");
			boolean conditionPrepoCnj = lastIsConjonAndPrep(lastSchem);
			boolean saveGnNotEmpty    = saveGn.size() > 1;
			
			

			boolean condition1 = lastSchemHaveVerb && !conditionPrepoCnj && saveGnNotEmpty;
			boolean condition2 = !lastSchemHaveVerb && !conditionPrepoCnj;
			boolean condition3 = conditionPrepoCnj;
			
			boolean notEmpty1  = saveGn.size() >= 3 && saveGn.get(saveGn.size() - 3).size() > 0;
			boolean notEmpty2  = saveGn.size() >= 2 && saveGn.get(saveGn.size() - 2).size() > 0;
			boolean notEmpty3  = saveGn.size() >= 1 && saveGn.get(saveGn.size() - 1).size() > 0;
			boolean notEmpty4  = saveGn.size() >= 1 && saveGn.get(saveGn.size() - 1).size() > 0;
			

			if (isSameGn && notEmpty1)    	   { 
				lastGroupNomi = saveGn.get(saveGn.size() - 3);  
				outIdentifiate[1] = true;
				System.out.println("identifiateSubjectToVerb - identificationOfSubjectOfVerb - idenfiateSubjectBySaveGn - 0");
			}
				
			else if (condition1 && notEmpty2)   {
				lastGroupNomi = saveGn.get(saveGn.size() - 2); 
				System.out.println("identifiateSubjectToVerb - identificationOfSubjectOfVerb - idenfiateSubjectBySaveGn - 1"); 
			}

			else if (condition2 && notEmpty3)   {
				//No subject, recuperate last group nominal
				lastGroupNomi = saveGn.get(saveGn.size() - 1);
				System.out.println("identifiateSubjectToVerb - identificationOfSubjectOfVerb - idenfiateSubjectBySaveGn - 2");
			}
			
			else if (condition3 && notEmpty3)   {
				lastGroupNomi = saveGn.get(saveGn.size() - 1); 
				System.out.println("identifiateSubjectToVerb - identificationOfSubjectOfVerb - idenfiateSubjectBySaveGn - 3");
			}

			else  { lastGroupNomi.add(""); }
			

			

			boolean haveAGnSaved = lastGroupNomi.size() > 0 && 
								   !lastGroupNomi.get(lastGroupNomi.size() - 1).equalsIgnoreCase("");

			if (haveAGnSaved) { 
				//SaveGn can be compose by multiple elements.
				
				String newSubject = "";
				
				String lastElementOf = lastGroupNomi.get(lastGroupNomi.size() - 1);
				String firstElementOf = lastGroupNomi.get(0);
				
				String[] PRONOMS = {"je", "tu", "il", "elle", "on", "nous", "vous", "ils", "elles"};
				


				boolean firstIsPrnm = thisListEqualWord(PRONOMS, firstElementOf);
				if (firstIsPrnm) { newSubject = firstElementOf; }
				else 			 { newSubject = lastElementOf;  }
				
				dataSubject.clear(); dataSubject.add(newSubject); 
				outIdentifiate[0] = true;
				saveGn.add(lastGroupNomi);
				System.out.println("identifiateSubjectToVerb - identificationOfSubjectOfVerb - idenfiateSubjectBySaveGn - 4");
			}
		}
		
		return outIdentifiate;
	}

	









	private boolean lastIsConjonAndPrep(ArrayList<String> lastSchem) {

		boolean conditionPrepoCnj = false;
		boolean haveSize          = lastSchem.size() > 2;

		if (haveSize) {

			String lastGroupWord   = lastSchem.get(lastSchem.size() - 1);
			String lastGroupX2Word = lastSchem.get(lastSchem.size() - 2);

			boolean lastPrepo      = lastGroupWord.contains("GPREP");
			boolean lastX2Cnjnc    = lastGroupX2Word.contains("GCNJNC");

			conditionPrepoCnj      = lastPrepo && lastX2Cnjnc;
		}
		return conditionPrepoCnj;
	}


	
	
	
	
	
	

	public void idefiateSubjectFromNextGn(ArrayList<String> dataSubject, ArrayList<String> gn) {
		
		String increment = ""; for (String element: gn) { increment += (element + " "); }
		ArrayList<String> container = new ArrayList<String>(); container.add(increment);
		dataSubject.clear(); dataSubject.add(increment);
		
		System.out.println("identifiateSubjectToVerb - idefiateSubjectFromNextGn");
	}
	
	
	
	
	
	
	
	//Assise sur la table. Je mange.      (Assise = je, il, elle, on)
	public boolean recuperatePronom(ArrayList<String> dataSubject, ArrayList<ArrayList<String>> saveText, 
								    Map<String, ArrayList<String>> quoi, int numberSentence) {
	
		boolean haveRecuperateSubject = false;
		boolean noSubject = dataSubject.size() == 1 && dataSubject.get(0).equalsIgnoreCase("");

		//Recuperate pronom of conjugate verbe.
		if (noSubject) {
		
			ArrayList<String> pronomPotential = new ArrayList<String>();
			recuperateInformation(containerDataGroupVerbal,   pronomPotential, "target2");

			boolean isNotEmpty = pronomPotential.size() > 0;
			if (isNotEmpty) {

				String[] pronoms = pronomPotential.get(0).split("-");
				for (String prnm: pronoms) { pronomPotential.add(prnm); }
				pronomPotential.remove(0);
				inSearchingGnPronom.add(pronomPotential);

				System.out.println("identifiateSubjectToVerb - recuperatePronom 1");
				return true;
			}
		}

		//We have a pronom who's corresponding to the recuperate pronom.
		else if (!noSubject) {
			
			boolean notEmpty = inSearchingGnPronom.size() > 0;
			if (notEmpty) { 

				recuperateMatchingPronomVerbFromLastSentence(dataSubject, inSearchingGnPronom, saveVerb, saveGardeCmplt,
						quoi, prepAdv, qui, numberSentence);
	
				System.out.println("identifiateSubjectToVerb - recuperatePronom 2");
				return true;
			}
		}

		
		
		
		return haveRecuperateSubject;
	}
	

	public void incrementinSearchingGnPronom(boolean needToIncrement) {
		if (!needToIncrement) { inSearchingGnPronom.add(new ArrayList<String>()); }
	}
	
	
	
	
	
	


	public boolean recuperateLastGnFromPropositionInSameSentence(ArrayList<ArrayList<String>> partOfSentence,
												   				 int inSentence, ArrayList<String> dataSubject) {

		//[[0+2+GVERBAL, 4+6+GCMPLT], <- subject -> [7+10+GVERBAL, 11+12+GCMPLT, 13+15+GPREP, 16+16+GREL],


		ArrayList<String> currentSentence = new ArrayList<String>();
		ArrayList<String> lastSentence    = new ArrayList<String>();
		String 			  lastSchema      = "";
		int[]             indexs          = {-1, -1};

		boolean parSentenceNotOne = partOfSentence.size() > 1 && inSentence > 0;
		if (parSentenceNotOne) {

			lastSchema = recuperateSentences(currentSentence, lastSentence, partOfSentence, inSentence);
			treatmentSchemaIndex(lastSchema, indexs);

			int begin = indexs[0];
			int end   = indexs[1];

			makeIncrement(begin, end);

			System.out.println("identifiateSubjectToVerb - recuperateLastGnFromProposition");

			return true;
		}
		return false;
	}

	
	private String recuperateSentences(ArrayList<String> currentSentence, ArrayList<String> lastSentence, 
									   ArrayList<ArrayList<String>> partOfSentence, int inSentence) {

		currentSentence   = partOfSentence.get(inSentence);
		lastSentence      = partOfSentence.get(inSentence - 1);
		String lastSchema = lastSentence.get(lastSentence.size() - 1);
		
		return lastSchema;
	}
	
	private void treatmentSchemaIndex(String lastSchema, int[] indexs) {
		String[] lastSchemaSplit = lastSchema.split("[+]");
		int      begin           = Integer.parseInt(lastSchemaSplit[0]);
		int      end             = Integer.parseInt(lastSchemaSplit[1]) + 1;
		
		indexs[0] = begin; indexs[1] = end;
	}
	
	private void makeIncrement(int begin, int end) {

		List<String> textInterest = currentText.subList(begin, end);

		String  increment = ""; 
		for (String element: textInterest) { increment += (element + " "); }
	
		boolean incrementNotEmpty = !increment.equalsIgnoreCase("");

		if (incrementNotEmpty) { increment = increment.substring(0, increment.length() - 1); }

		dataSubject.clear(); dataSubject.add(increment);
	}

	
	
	
	
	public void recuperatePrnmLastPartSentence(ArrayList<ArrayList<String>> partOfSentence, int inSentence,
											   ArrayList<String> dataSubject) {

		boolean parSentenceNotOne = partOfSentence.size() > 1 && inSentence > 0 && 
									dataSubject.get(0).equals(" ") || dataSubject.get(0).equals("");


		if (parSentenceNotOne) {

			ArrayList<String> lastSentence    = partOfSentence.get(inSentence - 1);
			
			int[] indexsVerb = {-1, -1};
			indexsVerb = recuperateIndexFromGroupInList(lastSentence, "GVERBAL");

			int[] indexsSujet = {-1, -1};
			indexsSujet = recuperateIndexFromGroupInList(lastSentence, "GSUJET");
			
			boolean isIndexVerb = indexsVerb[0]  != -1 && indexsVerb[1]  != -1;
			boolean isIndexSujt = indexsSujet[0] != -1 && indexsSujet[1] != -1;
			
			
			String haveFound = "";
			
			if (!isIndexVerb && isIndexSujt) {     //le tableau en argent donnait l'impression -> tableau
				haveFound = makeASearchOfSyntaxe(indexsSujet[0], indexsSujet[1], "Nom commun", "Nom propre");
				System.out.println("identificationOfSubjectOfVerb - recuperatePrnmLastPartSentence - 1 - " + haveFound);
			}

			else if (isIndexVerb && !isIndexSujt) {//le tableau en argent donnait l'impression -> tableau
				haveFound = makeASearchOfSyntaxe(indexsVerb[0], indexsVerb[1], "Pronom personnel", "Pronom indéfini");
				System.out.println("identificationOfSubjectOfVerb - recuperatePrnmLastPartSentence - 2 - " + haveFound);
			}


			dataSubject.clear(); dataSubject.add(haveFound);
		}
	}

	
	
	

	
	
	
	private String makeASearchOfSyntaxe(int indexBegin, int indexEnd, String function1, String function2) {

		List<ArrayList<String>> syntaxe = currentSyntaxe.subList(indexBegin, indexEnd);
		List<String> 			text 	= currentText.subList(indexBegin, indexEnd);
		
		int index = 0;
		for (ArrayList<String> current: syntaxe) {
			boolean isMatching = listEqualsElement(current, function1) || listEqualsElement(current, function2);
			if (isMatching) { return text.get(index); }
			index++;
		}
		return "";
	}

	
}

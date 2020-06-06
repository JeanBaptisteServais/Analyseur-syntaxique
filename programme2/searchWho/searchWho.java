package fr.jbaw.programme2.searchWho;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;



public class searchWho extends searchWhoUtils{

	private String[] PRONOMS = {"je", "tu", "nous", "vous", "il", "elle", "ils", "elles"};
	
	public searchWho() {}


	
	
	
	
	//----------------------------------------- establishListOfPersonFound
	
	private boolean[] searchGenre(ArrayList<String> dataOtherSubject) {

		//Define the genre of the pronom.
		boolean isGeneral  = listContains(dataOtherSubject, "general");
		boolean isFeminin  = listContains(dataOtherSubject, "feminin");
		boolean isMasculin = listContains(dataOtherSubject, "masculin");
		boolean isPlurFem  = listContains(dataOtherSubject, "plurFem");
		
		boolean[] conditions = {isGeneral, isFeminin, isMasculin, isPlurFem};
		return    conditions;
	}

	
	private void recuperateFromQuiGenre(boolean[] genre, ArrayList<String> personsInterest,
			Map<String, ArrayList<String>> qui, ArrayList<String> cantBeByGenre) {
		
		boolean isGeneral  = genre[0];
		boolean isFeminin  = genre[1];
		boolean isMasculin = genre[2];
		boolean isPlurFem  = genre[3];
	
		if (isGeneral)  { recuperatePersonFromQui(qui, null,      		  personsInterest); }
		if (isFeminin)  { recuperatePersonFromQui(qui, "feminin", 		  personsInterest); }
		if (isMasculin) { recuperatePersonFromQui(qui, "masculin", 		  personsInterest); }
		if (isPlurFem)  { recuperatePersonFromQui(qui, "plurFem",		  personsInterest); }
		if (isPlurFem)  { recuperatePersonFromQui(qui, "pluriel", 		  personsInterest); }

		if (!isFeminin)  { recuperatePersonFromQui(qui, "feminin", 		   cantBeByGenre); }
		if (!isMasculin) { recuperatePersonFromQui(qui, "masculin",        cantBeByGenre); }
		if (!isPlurFem)  { recuperatePersonFromQui(qui, "plurFem",		   cantBeByGenre); }
		if (!isPlurFem)  { recuperatePersonFromQui(qui, "pluriel", 		   cantBeByGenre); }
		
	}

	private boolean[] conditions(ArrayList<ArrayList<String>> saveGn, ArrayList<ArrayList<String>> saveSentence, 
			ArrayList<String> dataSubject, ArrayList<String> personsInterest, ArrayList<String> toSearchGenre, 
			String lastSigni, Map<String, ArrayList<String>> qui) {

		
		boolean saveGnNotEmpty       = saveGn.size() > 0;
		boolean saveSentenceNotEmpty = saveSentence.size() > 0;
		boolean isNotImpossiblePrnm  = impossiblePrnm(saveSentence, dataSubject); 
		boolean personInterestNotEmpty = personsInterest.size() > 1;
		
		//from lastGn -> [le chat, le chien, le chien, le chien, il]
		boolean isLastMutltySubject = !lastSigni.equalsIgnoreCase("");

		boolean onePersonCanBeTarget = personsInterest.size() == 1;
		boolean haveAllDataWho       = toSearchGenre.size() == 0;
		boolean noSubject            = dataSubject.get(0).equals(" ");
		
		boolean isNotInDico          = qui.get("?" + dataSubject.get(0)) != null || qui.get(dataSubject.get(0)) != null;

		
		boolean condition1 = personInterestNotEmpty && saveGnNotEmpty && isLastMutltySubject && noSubject;
		boolean condition2 = saveSentenceNotEmpty   && isNotImpossiblePrnm;
		boolean condition3 = onePersonCanBeTarget   && haveAllDataWho;
		boolean condition4 = onePersonCanBeTarget   && !haveAllDataWho;


		
		boolean[] conditions = {condition1, condition2, condition3, condition4};
		
		return conditions;
	}




	public String establishListOfPersonFound(ArrayList<String> dataOtherSubject,  ArrayList<String> dataSubject, 
				Map<String, ArrayList<String>> qui,  String mode, ArrayList<ArrayList<String>> saveGn, 
				ArrayList<ArrayList<String>> saveSentence, ArrayList<ArrayList<String>> saveText) {

		//------------------------------------------------------------
		//Search genre of the current subject.
		boolean   isSearchCmpltPerson = mode.equalsIgnoreCase("prsn");
		boolean[] genre = searchGenre(dataOtherSubject);


		//------------------------------------------------------------
		//Search genre of the subject on qui.
		ArrayList<String> personsInterest = new ArrayList<String>();
		ArrayList<String> cantBeByGenre   = new ArrayList<String>();
		recuperateFromQuiGenre(genre, personsInterest, qui, cantBeByGenre);


		//------------------------------------------------------------
		//Search none informations on qui subject.
		ArrayList<String> toSearchGenre = new ArrayList<String>();
		recuperatePersonFromQuiWithoutInfo(qui, toSearchGenre);
		System.out.println("establishListOfPersonFound - Have to search info: " + toSearchGenre);


		//------------------------------------------------------------
		//Sacha lui parle. Remove sacha.
		if (isSearchCmpltPerson) { for (String element: dataSubject) { personsInterest.remove(element); } }
		removeFromListContains(personsInterest, "(noneSubject)");
		removeFromListContains(personsInterest, "?" + dataSubject.get(0));

		System.out.println("establishListOfPersonFound - Recuperate from qui and make a match. " + personsInterest);
		

		

		
		//------------------------------------------------------------
		//Make conditions.
		String    lastSigni  = isLastMutltySubjectfromSaveGn(saveGn, dataSubject.get(0));
		boolean[] conditions = conditions(saveGn, saveSentence, dataSubject, personsInterest, toSearchGenre, lastSigni, qui);
		boolean   condition1 = conditions[0];
		boolean   condition2 = conditions[1];
		boolean   condition3 = conditions[2];
		boolean   condition4 = conditions[3];



		if      (condition1) {
			System.out.println("found mulitple personn to decide here ! " + personsInterest);
			System.out.println("from last part sentence 1. " + lastSigni);
			return lastSigni;
		}


		else if (condition2) {
			System.out.println("from last part sentence 2. " + saveSentence.get(0).get(0));
			String lastSubject = saveSentence.get(0).get(0);
			return lastSubject;
		}
		


		else if (condition4) {
			System.out.println("Only one personn can be. - 4 " + personsInterest.get(0));
			return personsInterest.get(0); 
		}


		else if (condition3) { 
			System.out.println("Only one personn can be. - 3 " + personsInterest.get(0));
			return personsInterest.get(0); 
		}
		return null;
	}







	



	public static String searchWhoCanBeFromRelationSameSentence(ArrayList<ArrayList<String>> saveSentence, 
			ArrayList<String> dataSubject, Map<String, ArrayList<String>> qui) {

		boolean saveSentenceNotEmpty = saveSentence.size() >= 1;
		if (saveSentenceNotEmpty) {

			String lastSubjectSentence = saveSentence.get(saveSentence.size() - 1).get(0);
			boolean keyExists = qui.get(lastSubjectSentence) != null;
			if (keyExists) {

				ArrayList<String> dataQui = qui.get(lastSubjectSentence);

				for (String element: dataQui) {
					boolean ContainsRelation1 = element.contains("relationAvec:");
					boolean ContainsRelation2 = element.contains("relation avec:");

					if (ContainsRelation1 || ContainsRelation2) { return element.split(": ")[1]; }
				}	
			}
		}
		return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String establishAppartenant(ArrayList<String> dataPreciSubject, ArrayList<String> dataSubject,
									   Map<String, ArrayList<String>> qui, ArrayList<ArrayList<String>> saveVerb, 
									   ArrayList<ArrayList<ArrayList<String>>> saveSyntaxe) {


		String  currentSubject = dataSubject.get(0);
		boolean isAppartenance = listContains(dataPreciSubject, "appartenance");


		if (isAppartenance) {

			ArrayList<String> containerSubject = new ArrayList<String>();

			recuperateAppartenanceQui(containerSubject, qui, currentSubject);
			boolean isOnlyOneMatch = containerSubject.size() == 1;
			

			if (isOnlyOneMatch) { 
				System.out.println("\n\nestablishPossession - establish possession from determinant 1");
				return containerSubject.get(0); 
			}
			
			

			else if (!isOnlyOneMatch) {

				System.out.println("\n\nestablishPossession - establish possession from determinant 2");

				String  pronomFound = recuperateSubjectFromLastGnForPossesion(saveVerb, dataPreciSubject, saveSyntaxe);
				boolean notEmpty    = !pronomFound.equalsIgnoreCase("");
				
				if (notEmpty) { return pronomFound; } 
			}
		}
		return "";
	}
	

	private void recuperateAppartenanceQui(ArrayList<String> containerSubject, Map<String, ArrayList<String>> qui,                   
										   String currentSubject) {																	   //1)

		for(Entry<String, ArrayList<String>> groups: qui.entrySet()) {
			String person = groups.getKey();
			boolean isNotCurrentSubject = person != currentSubject;
			if (isNotCurrentSubject) { containerSubject.add(person); }
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//------------------------ recuperateSubjectFromLastGnForPossesion
	
	private String recuperateSubjectFromLastGnForPossesion(ArrayList<ArrayList<String>> saveVerb, ArrayList<String> dataPreciSubject, //2)
											   			   ArrayList<ArrayList<ArrayList<String>>> saveSyntaxe) {
		
		//#il mange#Present indicatif#
		boolean saverbeNotEmpty = saveVerb.size() > 1 && saveVerb.get(saveVerb.size() - 2).size() >= 1;

		if (saverbeNotEmpty) {

			int    lastSentenceWithVerb = recuperateIndexLastSentenceVerb(saveSyntaxe);
			String lastVerbSyntaxe      = recuperateVerbOfLastSyntaxe(saveSyntaxe, lastSentenceWithVerb);
			String sujetIntrest         = recuperatePronomFromLastSyntaxe(lastVerbSyntaxe);
	

			String genreDetFunction = recuperateDeterminantPossessif(dataPreciSubject);


			String[] sing  = {"il", "je", "tu"}; 
			String[] plur  = {"ils", "nous", "vous"};


			boolean isPlur     = makeConditions(genreDetFunction, "mixtPlur", plur, sujetIntrest);
			boolean isSingMasc = makeConditions(genreDetFunction, "masculin", sing, sujetIntrest);
			boolean isSingFem  = makeConditions(genreDetFunction, "feminin",  sing, sujetIntrest);

			boolean isIl       = sujetIntrest.equalsIgnoreCase("il");

			if      (isSingFem && isIl) { return "?elle"; }
			else if (isPlur || isSingMasc || isSingFem) { return "?" + sujetIntrest; }
		}
		return "";
	}



	private boolean makeConditions(String genreDetFunction, String genre,
								  String[] listPronom, String sujetIntrest) {                       //recuperateSubjectFromLastGn 5)

		return genreDetFunction.equalsIgnoreCase(genre) && thisListEqualWord(listPronom, sujetIntrest);
	}


	
	private String recuperateDeterminantPossessif(ArrayList<String> dataPreciSubject) {			    //recuperateSubjectFromLastGn 4)

		String  detFunction    = "";
		boolean isAppartenance = listContains(dataPreciSubject, "appartenance");
		for (String element: dataPreciSubject) {

			boolean notEmpty1   = element.split("=").length > 1;
			boolean isPossessif = element.contains("appartenance");
			if (isAppartenance && !isPossessif && notEmpty1) { detFunction = element.split("=")[1]; }
		}
		return detFunction;
	}


	
	private String recuperatePronomFromLastSyntaxe(String lastVerbSyntaxe) { 					    //recuperateSubjectFromLastGn 3)

		String sujetIntrest = "";

		for (String sujet: PRONOMS) {

			boolean isContainsSubjet = lastVerbSyntaxe.contains(sujet);
			if (isContainsSubjet) { sujetIntrest = sujet; }
		}
		return sujetIntrest;
	}


	
	
	private String recuperateVerbOfLastSyntaxe(ArrayList<ArrayList<ArrayList<String>>> saveSyntaxe, 
			int lastSentenceWithVerb) {																//recuperateSubjectFromLastGn 2)

		String  lastVerbSyntaxe = "";
		boolean notEmpty        = lastSentenceWithVerb != -1;
		if (notEmpty) {

			ArrayList<ArrayList<String>> lastSyntaxe  = saveSyntaxe.get(lastSentenceWithVerb);

			for (ArrayList<String> syntaxe: lastSyntaxe) {

				boolean isVerb = listContains(syntaxe, "verbe#");
				if (isVerb) { lastVerbSyntaxe = syntaxe.get(0); }
			}
		}
		return lastVerbSyntaxe;
	}


	
	
	private int recuperateIndexLastSentenceVerb(ArrayList<ArrayList<ArrayList<String>>> saveSyntaxe) {//recuperateSubjectFromLastGn 1)
		
		int begin = saveSyntaxe.size() - 2;
		for (int index=begin; index >= 0; index--) {
			ArrayList<ArrayList<String>> sentence = saveSyntaxe.get(index);
			for (ArrayList<String> syntaxe: sentence) {
				boolean isVerb = listContains(syntaxe, "verbe#");
				if (isVerb) { return index; }
			}
		}
		return -1;
	}
	

	//------------------------ recuperateSubjectFromLastGnForPossesion
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private boolean impossiblePrnm(ArrayList<ArrayList<String>> saveSentence, ArrayList<String> dataSubject) {


		String[] PRONOMS  = {"je", "tu", "nous", "vous", "il", "elle", "ils", "elles"};
		String[] PRONOMS2 = {"?je", "?tu", "?nous", "?vous", "?il", "?elle", "?ils", "?elles"};
		

		
		boolean notEmpty = saveSentence.size() >= 1;
		if (notEmpty) {

			String lastSubject    = saveSentence.get(0).get(0).toLowerCase();
			String currentSubject = dataSubject.get(0).toLowerCase();

			String last     = detectePronom(lastSubject,    "one");
			String current  = detectePronom(currentSubject, "two");
			
			boolean arePrnm = thisListEqualWord(PRONOMS,  lastSubject) || 
							  thisListEqualWord(PRONOMS2, lastSubject) && 
							  thisListEqualWord(PRONOMS,  currentSubject) || 
							  thisListEqualWord(PRONOMS2, currentSubject);
							  
			
			boolean areSame       = lastSubject.equalsIgnoreCase(currentSubject);
			boolean canAccorded   = current.contains(last);
			
			if	    (areSame) 				  { return true;  }
			else if	( canAccorded && arePrnm) { return true;  }
			else if (!canAccorded && arePrnm) { return false; }
			
		}
		
		return false;
	}

	
	private String detectePronom(String subject, String mode) {
		
		boolean two = mode.equalsIgnoreCase("two");
		
		boolean first  = subject.contains("je");
		if      (first && two) { return "first"; }
		else if (first)        { return "first"; }
		
		boolean second = subject.contains("tu");
		if (second && two) { return "second third"; }
		if (second) 	   { return "second"; }
		
		boolean third  = subject.contains("il") || subject.contains("elle") || subject.contains("on");
		if (third && two) { return "third second"; }
		if (third) 		  { return "third"; }
		
		boolean nous   = subject.contains("nous");
		if (nous && two) { return "nous vous"; }
		if (nous) 		 { return "nous"; }
		
		boolean vous   = subject.contains("vous");
		if (vous && two) { return "vous nous"; }
		if (vous) 		 { return "vous"; }
		
		boolean ils    = subject.contains("ils") || subject.contains("elles");
		if (ils && two) { return "ils nous"; }
		if (ils) 		{ return "ils"; }

		return "";
	}
	

	private String isLastMutltySubjectfromSaveGn(ArrayList<ArrayList<String>> saveGn, String currentSubject) {

		//We try to make a match with lastGn : like;
		//le veillard mange des fruit. Il dors. -> il is veillard.

		currentSubject         = currentSubject.toLowerCase();

		String  lastElement    = "";
		int     presenceLast   = 0;
		boolean saveGnNotEmpty = saveGn.size() > 1;
		if (saveGnNotEmpty) {

			for (int index=saveGn.size() - 1; index > 0; index--) {

				boolean notEmpty    = saveGn.get(index).size() > 0; 
				if (notEmpty) {
					String  element     = saveGn.get(index).get(0).toLowerCase();
					boolean isntCurrent = !currentSubject.contains(element);
					boolean isIncrement = !lastElement.equalsIgnoreCase("");
	
					if (isntCurrent && isIncrement) {
						boolean isntSameElement1 = !lastElement.equalsIgnoreCase(element);
						boolean isntSameElement2 = !lastElement.equalsIgnoreCase(element + " ");
						if (isntSameElement1 && isntSameElement2) { break; }
						else                 { presenceLast += 1; }
					}
					else if (!isIncrement && isntCurrent) { lastElement = element; }
				}
			}
		}
		
		boolean isSignificativ = presenceLast >= 2;
		if (!isSignificativ) { lastElement = ""; }
		return lastElement;
	}

	
	private static void recuperatePersonFromQui(Map<String, ArrayList<String>> qui, String search, ArrayList<String> persons) {

		//From the genre of the determinant, recuperate subject genre interest from qui.
		
		
		//Mixt pronom as: leur - Genre as: sa
		//Mixt pronom
		boolean haveToSearch = search != null;

		
		//Search the subject found on qui.
		for(Entry<String, ArrayList<String>> groups: qui.entrySet()) {

			String 			  person     = groups.getKey();
			ArrayList<String> infoPerson = groups.getValue();
			
			//Have found a genre of the pronom.
			if (haveToSearch) {
				boolean contains = listContains(infoPerson, search);
				if (contains)  { persons.add(person); }
			}

			//Mixt pronom
			if (!haveToSearch) {persons.add(person);}
		}
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	

}

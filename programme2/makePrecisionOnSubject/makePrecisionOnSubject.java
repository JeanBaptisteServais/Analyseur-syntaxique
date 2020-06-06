package fr.jbaw.programme2.makePrecisionOnSubject;

import java.util.ArrayList;

import fr.jbaw.programme2.searchWordUtils;

public class makePrecisionOnSubject extends searchWordUtils{

	
	public makePrecisionOnSubject(ArrayList<String> dataSubject, ArrayList<String> dataPreciSubject) {
		
		
		boolean haveGotAsubject = !dataSubject.get(0).equalsIgnoreCase("");
		boolean noPreci         = dataPreciSubject.size() == 0;
		String  subject         = dataSubject.get(0).toLowerCase();


		if (haveGotAsubject && noPreci) {
		

			boolean subjectIsComposed = subject.split(" ").length > 1;
			
			if (!subjectIsComposed) {

				System.out.println("makePrecisionOnSubjectIfNoPrecision");
				
				//majuscule -> sing
				String  firstLetter = Character.toString(subject.charAt(0));
				boolean isMajuscule = firstLetter.contentEquals(firstLetter.toUpperCase());
				boolean notChar     = firstLetter.equalsIgnoreCase("?");
				
				int     text        = -1;
				try { text = Integer.parseInt(firstLetter); } catch (Exception e) {}
				boolean isNotInt    = text != -1;
				
				
				if (isMajuscule && !notChar && !isNotInt) {
					dataPreciSubject.add("sing"); System.out.println("have add sing to quiDico - " + subject);
				}

			}
			

			else if (subjectIsComposed) {

				System.out.println("makePrecisionOnSubjectIfNoPrecision - " + subject);
				
				String[] articleSingMasc = {"le", "un", "contraction=le"};
				String[] articleSingFem  = {"la", "une"};
				String[] articlePLur     = {"les", "des"};
				
				boolean isSingMasc = thisListEqualContains(articleSingMasc, subject);
				boolean isSingFem  = thisListEqualContains(articleSingFem,  subject);
				boolean isPlur     = thisListEqualContains(articlePLur,     subject);
				
				if      (isPlur)     { dataPreciSubject.add("plur");     System.out.println("have add plur to quiDico");     }
				else if (isSingMasc) { dataPreciSubject.add("singMasc"); System.out.println("have add  mascSing to quiDico"); }
				else if (isSingFem)  { dataPreciSubject.add("singFem");  System.out.println("have add  singfem to quiDico");  }

			}
		}
	}
	
	
}

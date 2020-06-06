package fr.jbaw.programme2.analysingWho;

import java.util.ArrayList;

import fr.jbaw.programme2.searchWordUtils;

public class analysingWho extends searchWordUtils{
	
	public analysingWho(ArrayList<String> dataSubject, ArrayList<String> dataPreciSubject,
			boolean verifySubject, ArrayList<ArrayList<String>> partOfSentence, 
			ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
	
		String plurial = "";
		String feminin = "";
		String masc    = "";
		String sing    = "";
		

		for (String subject: dataSubject) {						//Pluriel

			subject = subject.toLowerCase();
			
			//Analyse from words.
			boolean plurEt  = subject.contains(" et ");
			boolean plurLes = subject.contains("les ");

			boolean plur      = plurEt || plurLes;
			boolean plurEmpty = plurial.equalsIgnoreCase("");
			if (plur && plurEmpty) { plurial = "pluriel"; }
		}
		
		
		
		for (String subject: dataSubject) {						//Feminin

			subject = subject.toLowerCase();
			
			//Analyse from words.
			boolean sse  = subject.contains("sse");

			
			boolean femNotEmpty = feminin.equalsIgnoreCase("");
			if (sse && femNotEmpty) { feminin = "feminin"; }
			if (sse && femNotEmpty) { System.out.println("Found feminin from word"); }
		}
		
		int[] indexsSchema = recuperateSubjectFromPartOfSentence(partOfSentence);
		boolean haveIndex = indexsSchema[0] != -1;
		
		if (haveIndex) {
		
			//Analyse from syntaxe.

			for (int index=indexsSchema[0]; index <= indexsSchema[1]; index++) {

				ArrayList<String> words = currentSyntaxe.get(index);
				
				String text = currentText.get(index);
	
				boolean isUn    = text.equalsIgnoreCase("un");
				boolean isUne   = text.equalsIgnoreCase("une");
				boolean isUnUne = isUn || isUne;
				
				boolean adjectifNum = listEqualsElement(words, "Adjectif numéral");
				
				boolean plurEmpty = plurial.equalsIgnoreCase("");
				boolean singEmpty = sing.equalsIgnoreCase("");
				boolean masEmpty  = masc.equalsIgnoreCase("");
				boolean femNotEmpty = feminin.equalsIgnoreCase("");
				
				
				
				if (adjectifNum && plurEmpty && !isUnUne) { plurial = "pluriel"; }

				if (adjectifNum && isUnUne && singEmpty)  { sing    = "singulier"; }
				if (adjectifNum && isUn && masEmpty)      { masc    = "masculin";  }
				if (adjectifNum && isUne && femNotEmpty)  { feminin = "feminin"; }
				

			}
			
		}
		
		
		boolean plurNotEmpty = !plurial.equalsIgnoreCase("");
		boolean mascNotEmpty = !masc.equalsIgnoreCase("");
		boolean femNotEmpty  = !feminin.equalsIgnoreCase("");
		boolean SingNotEmpty = !sing.equalsIgnoreCase("");
		
		
		
		if 		(plurNotEmpty && !femNotEmpty &&  mascNotEmpty) { dataPreciSubject.add("plurMasc");
			System.out.println("add plurMasc from word to qui");}
		
		else if (plurNotEmpty && femNotEmpty  && !mascNotEmpty) { dataPreciSubject.add("plurFem");
			System.out.println("add plurFem from word to qui");}

		else if (plurNotEmpty && !femNotEmpty && !mascNotEmpty) { dataPreciSubject.add("pluriel");
			System.out.println("add pluriel from word to qui");}
		
		else if (SingNotEmpty && !femNotEmpty &&  mascNotEmpty) { dataPreciSubject.add("singMasc"); 
			System.out.println("add singMasc from word to qui");}
		
		else if (SingNotEmpty && femNotEmpty  && !mascNotEmpty) { dataPreciSubject.add("singFem");  
			System.out.println("add singFem from word to qui");}
		
		else if (SingNotEmpty && !femNotEmpty && !mascNotEmpty) { dataPreciSubject.add("sing");     
			System.out.println("add sing from word to qui");}
		

	}
	
	int[] recuperateSubjectFromPartOfSentence(ArrayList<ArrayList<String>> partOfSentence) {

		int[] indexs = {-1, -1};
		String schema = "";
		for (ArrayList<String> current: partOfSentence) {
			for (String element: current) {
				boolean isSubjectSchema = element.contains("GSUJET");
				if (isSubjectSchema) { schema = element; break; }
			}
		}

		boolean haveASchemaSubject = !schema.equalsIgnoreCase("");
		if (haveASchemaSubject) {
			String[] schemaSplit = schema.split("[+]");
			int begin = Integer.parseInt(schemaSplit[0]);
			int end   = Integer.parseInt(schemaSplit[1]);
			
			indexs[0] = begin; indexs[1] = end;
		}
		
		return indexs;
	}
	
}

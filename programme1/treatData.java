package fr.jbaw.programme1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



public class treatData {


	public List<ArrayList<String>> treatListData(List<ArrayList<String>> dataTreat, char[] ponctuationList) {

		List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

		for (int sentence=0; sentence < dataTreat.size(); sentence++) {

			data.add(new ArrayList<String>());

			for (String wordString: dataTreat.get(sentence)) {

				String increment = "";

				for (int letter=0; letter < wordString.length(); letter++) {

					String verif = increment;

					increment  = contraction(wordString.charAt(letter), wordString, letter, increment);

					if (verif != increment) {letter += 1;}
					increment += wordString.charAt(letter);

					increment =  ponctuation(wordString.charAt(letter), ponctuationList, increment); 

				}
				
				String[] toIncrement = increment.split("_");

				for (String incr: toIncrement) {
					if (!incr.equalsIgnoreCase("")) {
						data.get(data.size() - 1).add(incr);
					}
				}
			}
		}
		return data;
	}


	


	public String ponctuation(char ponctCharacter, char[] ponctuationList, String incrementation) {

		String out = "_";

		for (char ponctuation: ponctuationList){

			if (Character.toString(ponctCharacter).equalsIgnoreCase(Character.toString(ponctuation))) {
				incrementation = incrementation.substring(0, incrementation.length() - 1);
				out = incrementation + "_" + ponctuation + "_";
			}
		}

		if (out != "_") {return out;}
		else {return incrementation;}
	}



	private String contraction(char apostrophe, String wordString, int indexLetter, String incrementation) {

		char letterBefore = 0;

		if      (wordString.length() == 1 || indexLetter == 0) {letterBefore = apostrophe;}
		else if (wordString.length() > 1)                      {letterBefore = Character.toLowerCase(wordString.charAt(indexLetter - 1));}

		
		
		
		Map<Character, String> contractionTransformation = new HashMap<Character, String>();

		contractionTransformation.put('m', "contraction=me");
		contractionTransformation.put('n', "contraction=ne");
		contractionTransformation.put('s', "contraction=se");
		contractionTransformation.put('t', "contraction=te");
		contractionTransformation.put('l', "contraction=le");
		contractionTransformation.put('d', "contraction=de");
		contractionTransformation.put('c', "contraction=ce");
		contractionTransformation.put('u', "contraction=que");
		contractionTransformation.put('j', "je");
		contractionTransformation.put('h', "he");
		contractionTransformation.put('t', "te");
		contractionTransformation.put('y', "y");

		String out = "_";

		if((apostrophe == '\'') || (apostrophe == '’') || (apostrophe == '’')) {
			for (Entry<Character, String> dico: contractionTransformation.entrySet()) {
				if (letterBefore == dico.getKey()) {
					out = dico.getValue() + "_";
				}
			}
		}

		
		boolean incrementationIsContraction = incrementation.length() > 2;
		
		if (out != "_" && incrementationIsContraction) { System.out.println(incrementation); }
		
		if (out != "_" && incrementationIsContraction) {return incrementation + "e_";}
		
		if (out != "_" && !incrementationIsContraction) {return out;}
		else {return incrementation;}
	}




	public void raiseEmptySentence(List<ArrayList<String>> dataTreat) {
		for (int sentence=0; sentence < dataTreat.size(); sentence++) {
			if (dataTreat.get(sentence).size() <= 1) {
				dataTreat.remove(dataTreat.get(sentence));
			}
		}
	}
}

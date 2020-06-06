package fr.jbaw.programme2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreatData {

	private String[] noWord = {"Aucun mot trouvé",
							   "donc aucune définition correspondante. Veuillez vérifiez l'orthographe ...",
	   						   "Définitions corespondante à votre recherche"};
	public TreatData() {
		this.noWord = noWord;
	}
	


	

	public void treatementSyntaxe(String what, List<ArrayList<ArrayList<String>>> syntaxeText) {

		for (ArrayList<ArrayList<String>> sentence: syntaxeText) {

			for (ArrayList<String> wordSyntaxe: sentence) {

				raiseDoublon(wordSyntaxe);
				raiseNofoundWord(wordSyntaxe);

			}
		}
	}



	public void raiseDoublon(ArrayList<String> wordSyntaxe) {
		Set set = new HashSet();
	    set.addAll(wordSyntaxe);
	    ArrayList noDoublon = new ArrayList(set);
	    wordSyntaxe.clear();
	    for (Object element: noDoublon) {wordSyntaxe.add(element.toString());}
	}

	public void raiseNofoundWord(ArrayList<String> wordSyntaxe) {

		boolean removeElement = false;
		boolean oneElement = wordSyntaxe.size() -1 == 0;

		if(oneElement) {
			for(String noFound: this.noWord) {
				if(noFound.equalsIgnoreCase(wordSyntaxe.get(0))) {removeElement = true;}
			}
		}

		else {
			for (int nb=0; nb < wordSyntaxe.size() - 1; nb++) {
				for(String noFound: this.noWord) {
					if (noFound.equalsIgnoreCase(wordSyntaxe.get(nb))) {wordSyntaxe.remove(nb);}
				}
			}
		}
		if (removeElement) {wordSyntaxe.set(0, "None");}
	}
	

	




	public List<ArrayList<ArrayList<String>>> treatWordSyntaxeToList(String what, List<ArrayList<String>> syntaxeSentence) {

		List<ArrayList<ArrayList<String>>> sentenceWordSplit = new ArrayList<ArrayList<ArrayList<String>>>();

		for (int sentence=0; sentence < syntaxeSentence.size(); sentence++) {

			sentenceWordSplit.add(new ArrayList<ArrayList<String>>());

			for (int word=0; word < syntaxeSentence.get(sentence).size(); word++) {

				String[] sentenceSplit = syntaxeSentence.get(sentence).get(word).split("/");

				List<String>container = new ArrayList<String>();

				for (int wordPossibility = 0; wordPossibility < sentenceSplit.length; wordPossibility++) {
					container.add(sentenceSplit[wordPossibility]);
				}
				sentenceWordSplit.get(sentenceWordSplit.size() - 1).add((ArrayList<String>) container);
			}
		}
		return sentenceWordSplit;
	}




	public void deleteCharacter(String what, List<ArrayList<String>> List) {

		switch(what) {
			case "Remove space beetween words":

				for (ArrayList<String> functionTreat: List) {
					for(int nb=0;nb < functionTreat.size(); nb++) {
						if (functionTreat.get(nb).equalsIgnoreCase("")) {
							functionTreat.remove(functionTreat.get(nb));
						}
					}
				}
			break;
			case "Remove first space":

				for (ArrayList<String> sentence: List) {

					int index = 0;
					for(String word: sentence) {
						if (word.charAt(0) == ' '){sentence.set(index, word.substring(1));}
						index++;
					}
				}
			break;
		}
	}
}

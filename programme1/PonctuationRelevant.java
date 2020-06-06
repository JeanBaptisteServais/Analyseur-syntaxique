package fr.jbaw.programme1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PonctuationRelevant {


	
	public List<ArrayList<String>> SentenceByponctuation(List<ArrayList<String>> stockWord, char[] endPonct) {

		List<ArrayList<String>> sentenceList = new ArrayList<ArrayList<String>>(); 
		sentenceList.add(new ArrayList<String>());
		
		boolean incrementation = false;
		
		for(int sentence = 0; sentence < stockWord.size() - 1; sentence++) {

			for (String words : stockWord.get(sentence)) {

				for (int nb = 0; nb < words.length(); nb ++) {
					for (char pnt: endPonct) {

						if (pnt == words.charAt(nb)) {
							incrementation = true;
						}
					}
				}
				sentenceList.get(sentenceList.size() - 1).add(words);
				if (incrementation) {
					sentenceList.add(new ArrayList<String>());
					incrementation = false;
				}
			}
		}
		return sentenceList; 
	}
	
	

	public void countPonctuation (List<ArrayList<String>> stockWord, Map<Character, Integer> dicoPonct, char[] endPonct) {
		
		for(int sentence = 0; sentence < stockWord.size(); sentence++) {

			for (String words : stockWord.get(sentence)) {

				boolean canAddToListWork = true;
				
				for (int letter = 0; letter < words.length(); letter ++) {

					for (char pnt: endPonct) {
						if (pnt == words.charAt(letter)) {
							dicoPonct.replace(pnt, dicoPonct.get(pnt) + 1);
							canAddToListWork = false;
						}
					}
					if (!canAddToListWork) {
						stockWord.add(new ArrayList<String>());
					}
				}	
			}
		}
	}
	


	public Map<Character, Integer> createDico(char[] ponct){
		
		Map<Character, Integer> dicoPonct = new HashMap<Character, Integer>();
		//Iterate the char[] ponctuation and put it to a Map.
		for (char pnt: ponct) {
			dicoPonct.put(pnt, 0);
		}
		return dicoPonct;
	}



	
}

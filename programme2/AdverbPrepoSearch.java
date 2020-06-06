package fr.jbaw.programme2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AdverbPrepoSearch {


	
	private String[] list;
	private ArrayList<String> MATCHINGLIST = new ArrayList<String>();
	private String toPutInSyntaxe;
	
	public AdverbPrepoSearch(String[] list, String toPutInSyntaxe) {
		this.list = list;
		this.toPutInSyntaxe = toPutInSyntaxe;
	}


	void matchElementList(ArrayList<String> currentText, ArrayList<ArrayList<String>> currentSyntaxe ){

		System.out.println(toPutInSyntaxe);
		int nb = 0;
		boolean isMatching = true;
		while (isMatching) {
			for (String element: list) {
				String[] elementSplit = splitElement(element);
				
				ArrayList<String> container = new ArrayList<String>();

				int index=0;
				for (String word: currentText) {
	
					isMatching = elementSplit[0].equalsIgnoreCase(word);

					boolean isAlreadyAdv = index < currentSyntaxe.size() &&
										   currentSyntaxe.get(index).get(0).equalsIgnoreCase(toPutInSyntaxe) &&  
										   currentSyntaxe.get(index).size() == 1;

					if (isMatching && !isAlreadyAdv) { 
						searchingMatching(container, elementSplit, currentText, index, element); 
					}
					index++;
				}
				
				boolean notEmpty = container.size() > 0;
				if (notEmpty) { remake(currentText, currentSyntaxe, container); }
			}
			if (nb > 50) { isMatching = false; }
			else { nb++; }
		}
		System.out.println("");
	}

	
	
	
	
	
	
	private void remake(ArrayList<String> currentText, ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> container) {

		int    index  = Integer.parseInt(container.get(1));
		String word   = container.get(0).toLowerCase();
		int    length = word.split(" ").length;
		
		System.out.println("have remkae list: " + index + " with: " + word);

		currentText.set(index, word);
		currentSyntaxe.get(index).clear(); currentSyntaxe.get(index).add(toPutInSyntaxe);


		int adding = 0;
		for (int indexage = index + 1; indexage < index + length; indexage++) {
			currentText.remove(indexage + adding);
			currentSyntaxe.remove(indexage + adding);
			adding -= 1;
		}
	}


	private void searchingMatching(ArrayList<String> container, String[] elementSplit, ArrayList<String> currentText,
			int index, String adverbe) {

		
		int length = elementSplit.length;
		boolean isPlafond = index + length < currentText.size() || index + length == currentText.size();

		if (isPlafond) {

			List<String> part = currentText.subList(index, index + length);

			String partWord = ""; for (String element: part) { partWord += (element + " "); }
			partWord = partWord.substring(0, partWord.length() - 1);
			
			boolean areSameWord = adverbe.equalsIgnoreCase(partWord);
			boolean haveASave   = container.size() > 0;
	
			if (areSameWord) { System.out.println("have a correspondance with: " + adverbe); }
			
			if (haveASave && areSameWord) {
				int lastElement = container.get(0).split(" ").length;
				boolean currentMoreLengrth = lastElement < length;
				if (currentMoreLengrth) { container.set(0, adverbe); container.set(1, Integer.toString(index)); }
			}
	
			else if (!haveASave && areSameWord) { container.add(adverbe); container.add(Integer.toString(index)); }
		}
	}





	private String[] splitElement(String element) {
		
		String[] elementSplit = element.split(" ");
		return elementSplit;
	}
	
	
	
}

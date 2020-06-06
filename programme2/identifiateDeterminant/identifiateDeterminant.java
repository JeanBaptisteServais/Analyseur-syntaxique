package fr.jbaw.programme2.identifiateDeterminant;

import java.util.ArrayList;

import fr.jbaw.programme2.groupNominal;
import fr.jbaw.programme2.searchWordUtils;

public class identifiateDeterminant extends searchWordUtils{

	public identifiateDeterminant(ArrayList<String> containerData, String search, 
			ArrayList<String> dataRecpteur, int numberSentence, ArrayList<String> currentText, 
			ArrayList<ArrayList<String>> currentSyntaxe) {
	
		
		String[] masc = {"son", "le", "un"};
		String[] fem  = {"sa", "la", "une"};
		String[] mixPlur  = {"leur", "ses", "les", "des"};

		String[] plurMasc = {"tout"};
		String[] plurFem  = {"toutes"};

		for (String   element: containerData) {
			 String[] elementSplit = element.split("=");
			 String   function     = elementSplit[0];
			 String   determinant  = elementSplit[1];
			 
			 boolean isSearch   = function.equalsIgnoreCase(search);
			 
			 boolean isMasc     = thisListEqualWord(masc, determinant);
			 boolean isFem      = thisListEqualWord(fem,  determinant);
			 boolean isMix      = thisListEqualWord(mixPlur,  determinant);

			 boolean isPlurMasc = thisListEqualWord(plurMasc, determinant);
			 boolean isPlurFem  = thisListEqualWord(plurFem,  determinant);

			 if      (isSearch && isMasc)     { dataRecpteur.add(determinant + "=masculin"); }
			 else if (isSearch && isFem)      { dataRecpteur.add(determinant + "=feminin"); }
			 else if (isSearch && isMix)      { dataRecpteur.add(determinant + "=mixtPlur"); }

			 else if (isSearch && isPlurMasc) { dataRecpteur.add(determinant + "=masculin pluriel"); }
			 else if (isSearch && isPlurFem)  { dataRecpteur.add(determinant + "=feminin pluriel"); }

			 //System.out.println(determinant);
			 
			 groupNominal groupNominal  = new groupNominal(determinant, "", numberSentence, currentText, currentSyntaxe);
			 String functionDeterminant = groupNominal.kindOfDeterminant();
			 
			 boolean isNotNull = functionDeterminant != null;
			 if (isNotNull)  { dataRecpteur.add(determinant + "=" + functionDeterminant); }
		}
		
	}
	
}

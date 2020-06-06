package fr.jbaw.programme2.complementTreatement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.jbaw.programme2.searchWordUtils;

public class complementTreatement extends searchWordUtils{

	
	private int begening;
	private int end;

	private ArrayList<String> 			 containerData;
	private ArrayList<String> 			 container;
	private ArrayList<ArrayList<String>> currentSyntaxe;
	private ArrayList<String>		     currentText;
	private ArrayList<String> 		     groupsSyntaxe;
	private ArrayList<ArrayList<String>> partOfSentence;


	public complementTreatement(int begening, int end, ArrayList<String> containerData, 
			ArrayList<String> container, ArrayList<ArrayList<String>> currentSyntaxe, 
			ArrayList<String> currentText, String[] DETERMINANTS, ArrayList<String> groupsSyntaxe, 
			ArrayList<ArrayList<String>> partOfSentence) {

		
		this.end 		    = end;
		this.begening       = begening;
		this.containerData  = containerData;
		this.container 	    = container;
		this.currentSyntaxe = currentSyntaxe;
		this.currentText    = currentText;
		this.DETERMINANTS   = DETERMINANTS;
		this.groupsSyntaxe  = groupsSyntaxe;
		this.partOfSentence = partOfSentence;
	}


	public void treatTheComplement() {
		

		String[] cmpltSentence = {"detQuoi=", "quoiNoyeau=", "Relationqui=", "qui2=", "precisionQui2=", "detPreci2=", "quoi=", "qui="};

		int indexVerb   = recuperateIndex(groupsSyntaxe, "GVERBAL", 1);
		int indexCmplt1 = recuperateIndex(groupsSyntaxe, "GCMPLT",  0);
		int indexCnjncB = recuperateIndex(groupsSyntaxe, "GCNJNC",  0);
		int indexCnjncE = recuperateIndex(groupsSyntaxe, "GCNJNC",  1);
		int indexCmplt2 = recuperateIndex(groupsSyntaxe, "GCMPLT", indexCmplt1, 0);

		boolean[] extraConditions  = extraConditions(indexVerb, indexCmplt1, indexCnjncB, indexCnjncE, 
													 indexCmplt2, begening,  end, partOfSentence);

		boolean   isNc        	   = extraConditions[0];
		boolean   haveFoundNp 	   = extraConditions[1];
		boolean   inRange1         = extraConditions[2];
		boolean   inRange2         = extraConditions[3];
		boolean   firstCmplt       = extraConditions[4];
		boolean   isCnjncBeetw     = extraConditions[5];
		boolean   isOneSentence    = extraConditions[6];

		boolean canImplementFirstCmplt = (inRange1 && firstCmplt);
		boolean canImplementSecondCmt  = (inRange2 && isCnjncBeetw);
		boolean canImplementAll        = isOneSentence;
	
		
		if (canImplementFirstCmplt || canImplementSecondCmt || canImplementAll) {

			for (String data: container) {

				boolean[] intraConditions = intraConditions(data);
				boolean   isDet       	  = intraConditions[0];
				boolean   isNoyeau    	  = intraConditions[1];
				boolean   isAdjectif  	  = intraConditions[2];
				boolean   isNomPropre 	  = intraConditions[3];
				boolean   isVerbe	  	  = intraConditions[4];
				boolean   isNeg 	  	  = intraConditions[5];

				String[] dataInfo = treatementDataEqual(data);

				cmpltSentence = saveData(isDet, isNoyeau, isAdjectif, isNomPropre, isVerbe, isNc, 
										 haveFoundNp, cmpltSentence, dataInfo, isNeg, canImplementAll);

			}
			cmpltSentence = removeLastSpaceString(cmpltSentence);
	
			if (haveFoundNp)     { cmpltSentence[1] = ""; }
	
			if (canImplementAll) { incrementAllCmplt(groupsSyntaxe, currentSyntaxe, currentText); }
			else                 { savegardeInContainer(cmpltSentence, currentText); }

		}
	}


	


	

	private void savegardeInContainer(String[] cmpltSentence, ArrayList<String> currentText) {
		
		System.out.println("Cmplt action: isComplement - savegardeInContainer");

		int indexVerb = recuperateIndexFromGroupInList(groupsSyntaxe, "GVERBAL")[1] + 1;

		for (String element: cmpltSentence) { 
			boolean containsAlready = listContains(containerData, element);
			if (!containsAlready) { containerData.add(element); }
		}

		String lastWord  = containerData.get(containerData.size() - 1);
		String increment = "quoi=";

		for (int index = indexVerb; index < currentText.size(); index++) {
			String  word       = currentText.get(index);
			boolean isLastWord = word.contains(lastWord) || lastWord.contains(word);
			boolean isMoreSizeOne = lastWord.split("=").length > 1;
			if (isLastWord && isMoreSizeOne) { increment += lastWord.split("=")[1]; break; }
			else            				 { increment += (word + " "); }
		}

		containerData.clear();
		containerData.add(increment);
	}
	
	
	

	private void incrementAllCmplt(ArrayList<String> groupsSyntaxe, ArrayList<ArrayList<String>> currentSyntaxe,
								   ArrayList<String> currentText) {

		System.out.println("Cmplt action: isComplement - incrementAllCmplt");


		int     indexVerb = recuperateIndexFromGroupInList(groupsSyntaxe, "GVERBAL")[1];
		boolean notEmpty  = indexVerb != -1;

		if (notEmpty) {

			String       increment       = "";
			List<String> currentTextPart = currentText.subList(indexVerb + 1, currentText.size() - 1);

			for (String element: currentTextPart) { increment += (element + " "); }

			boolean notEmpty1 = !(increment.equalsIgnoreCase(""));
			if (notEmpty1) {
				increment = increment.substring(0, increment.length() - 1);
			}
			containerData.add("quoi=" + increment);
		}
	}
	
	

	private String[] removeLastSpaceString(String[] cmpltSentence) {
		for (int index=0; index < cmpltSentence.length; index++) {
			String   lastCharac = cmpltSentence[index].substring(cmpltSentence[index].length() - 1);
			boolean  isEqual    = lastCharac.equalsIgnoreCase("=");
			if (!isEqual) { cmpltSentence[index] = cmpltSentence[index].substring(0, cmpltSentence[index].length() - 1); }
		}
		return cmpltSentence;
	}



	private String[] saveData(boolean isDet, boolean isNoyeau, boolean isAdjectif, 
						      boolean isNomPropre, boolean isVerbe, boolean isNc, boolean haveFoundNp,
						      String[] cmpltSentence, String[] dataInfo, boolean isNeg, boolean canImplementAll) {

		//String[] cmpltSentence = {"detQuoi=", "quoiNoyeau=", "Relationqui=", "qui2=", "precisionQui2=", "detPreci2=", "quoi=", "qui="};

		String virgule = ","; String space = " ";


		if 		(isDet)      	    	     { cmpltSentence[0] += (dataInfo[1] + virgule);
											   cmpltSentence[6] += (dataInfo[1] + space); }
		
		else if (isNoyeau &&    isNomPropre) { cmpltSentence[2] += (dataInfo[1] + space); 
											   cmpltSentence[6] += (dataInfo[1] + space); }

		else if (isNoyeau &&   !isNomPropre) { cmpltSentence[6] += (dataInfo[1] + space); }
		else if (isVerbe  &&   !isNomPropre) { cmpltSentence[6] += (dataInfo[1] + space); }
		
		else if (isAdjectif && !isNc) 		 { cmpltSentence[7] += (dataInfo[1] + space); 
											   cmpltSentence[6] += (dataInfo[1] + space); }

		else if (isAdjectif) 				 { cmpltSentence[6] += (dataInfo[1] + space); }
		
		else if (haveFoundNp && isNoyeau)    { cmpltSentence[3] += (dataInfo[1] + space); }
		else if (haveFoundNp && isAdjectif)  { cmpltSentence[4] += (dataInfo[1] + space); }
		else if (haveFoundNp && isDet)       { cmpltSentence[5] += (dataInfo[1] + space); 
											   cmpltSentence[6] += (dataInfo[1] + space); }
		else if (isNeg)                      { cmpltSentence[6] += (dataInfo[1] + space); }
		
		return cmpltSentence;
	}



	private boolean[] intraConditions(String data) {

		String  syntaxeWord   = caseNomPropre(data, currentSyntaxe, currentText);

		boolean isDet 	      = data.contains("determinant");
		boolean isNoyeau      = data.contains("Noyeau");
		boolean isAdjectif    = data.contains("epithete");

		boolean isNomPropre   = syntaxeWord.equalsIgnoreCase("Nom propre");
		boolean isVerbe       = data.contains("verbe");
		boolean isNeg	  	  = data.contains("négation");
		
		
		boolean[] conditions = {isDet, isNoyeau, isAdjectif, isNomPropre, isVerbe, isNeg};
		return conditions;
	}


	private boolean[] extraConditions(int indexVerb, int indexCmplt1, int indexCnjncB, int indexCnjncE, 
									  int indexCmplt2, int begening, int end, ArrayList<ArrayList<String>> partOfSentence) {



		boolean isNc         = listContains(container, "Noyeau");
		boolean haveFoundNp  = casePrepDetNc(begening, currentSyntaxe, currentText, DETERMINANTS);

		//[[0+2+GVERBAL, 3+4+GCMPLT, 5+5+GADV, 6+7+GCMPLT], [8+9+GVERBAL, 10+11+GCMPLT]]
		boolean inRange1     = (indexCmplt1 >= begening && indexCmplt1 <= end);
		boolean inRange2     = (indexCmplt2 >= begening && indexCmplt2 <= end);

		boolean firstCmplt   = (indexVerb + 1) == indexCmplt1;
		boolean isCnjncBeetw = (indexCnjncB - 1) == indexCmplt1 && (indexCnjncE + 1) == indexCmplt2;


		boolean isOne        =  partOfSentence.size() == 1;
		boolean isPoint      =  currentText.get(currentText.size() - 1).equalsIgnoreCase(".");
		boolean onePartSntce = isOne && isPoint;


		boolean[] conditions = {isNc, haveFoundNp, inRange1, inRange2, firstCmplt, isCnjncBeetw, onePartSntce};
		return 	  conditions;

	}


	private int recuperateIndex(ArrayList<String> groupsSyntaxe, String function, int indexPass, int position) {

		int indexFunction = -1;
		for(int index=0; index < groupsSyntaxe.size(); index++) {
			String  current      = groupsSyntaxe.get(index);
			boolean currentGroup = current.contains(function);
			
			int 	indexRecup   = recuperateIndexFromGroup(current)[position];
			boolean isIndexPass  = indexPass != indexRecup;

			if (currentGroup && isIndexPass) { indexFunction = indexRecup; break; }
		}
		return indexFunction;
	}


	private int recuperateIndex(ArrayList<String> groupsSyntaxe, String function, int position) {

		int indexFunction = -1;
		for(int index=0; index < groupsSyntaxe.size(); index++) {
			String  current      = groupsSyntaxe.get(index);
			boolean currentGroup = current.contains(function);
			if (currentGroup) { indexFunction = recuperateIndexFromGroup(current)[position]; break; }
		}
		return indexFunction;
	}
}












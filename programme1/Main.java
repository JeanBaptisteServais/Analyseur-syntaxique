package fr.jbaw.programme1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Main {


	public static void main(String[] args) throws Exception {


		//1) - Recuperate file.
		String textPath  = "C:\\Users\\jeanbaptiste\\Desktop\\MON JAVA\\workplace\\Programme1\\texts\\texts";
		String resumPath = "C:\\Users\\jeanbaptiste\\Desktop\\MON JAVA\\workplace\\Programme1\\texts\\resums";
		String savePath  = "C://Users//jeanbaptiste//Desktop//MON JAVA//workplace//Programme1//texts//analyse//";
		
		RU readUpdate = new RU(textPath, resumPath, savePath);
		File[] file = readUpdate.treatDocument();
		
		File textFile  = file[0];
		File resumFile = file[1];

		List<ArrayList<String>> stock = readUpdate.stockText (textFile, textPath);
		List<ArrayList<ArrayList<String>>> texts = readUpdate.textToWord (stock);



		int textNumberFile = 0;
		for (List<ArrayList<String>> stockWord : texts) {


			//2) - Recuperate text.
			PonctuationRelevant analyse = new PonctuationRelevant();
	
			char[] endPonct = {'.', ';', '/', ',', '!', '?'};

			Map<Character, Integer> dicoPonct = analyse.createDico(endPonct);
	
			analyse.countPonctuation(stock, dicoPonct, endPonct);
			List<ArrayList<String>> dataTreat = analyse.SentenceByponctuation(stockWord, endPonct);
	
	
			
			
			
			//3) - Treatement on text.
			char[] ponctuationList = {'"', ':', '(', ')', '<', '>', '.', ';',  '/', ',', '!', '?', '«', '»', '—', '%'};

			treatData treatmentList = new treatData();
			treatmentList.raiseEmptySentence(dataTreat);
	
			List<ArrayList<String>> dataText = treatmentList.treatListData(dataTreat, ponctuationList);


	
			//4) - Scrap text.
			String urlVerb = "https://leconjugueur.lefigaro.fr/conjugaison/verbe/";
			String urlWord = "https://www.le-dictionnaire.com/definition/";
			
			Scraping scrapWord = new Scraping(urlWord, "div.defbox", "span", urlVerb, "td");
			scrapWord.wordSyntaxe(dataText, readUpdate, textNumberFile);
		}
		textNumberFile += 1;
	}
}	

	
	
	

	
	
	
	
	


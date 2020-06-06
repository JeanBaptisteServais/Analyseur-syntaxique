package fr.jbaw.programme1;


import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;



//Ici je sais pas.
public class Scraping {


	private String urlWord;
	private String urlVerb;
	private String blocVerb;
	private String blocWord;
	private String baliseWord;
	private String verbError = "Recherche de l'orthographe d'un verbe";

	char[] ponctuationList = {'"', ':', '(', ')', '<', '>', '.', ';',  '/', ',', '!', '?', '«', '»', '—', '…', '%'};

	private Map<String, String> characterSpe = new HashMap<String, String>();
	private Map<Character, String> ponctuationMap = new HashMap<Character, String>();

	private String[] femPlur = {"es", "s", "e", "te", "x"};

	private String[] noWord = {"Aucun mot trouvé, donc aucune définition correspondante. Veuillez vérifiez l'orthographe .../",
							   "Aucun mot trouvé, donc aucune définition correspondante. Veuillez vérifiez l'orthographe ...",
	  						   "Définitions corespondante à votre recherche/",
	  						   "Définitions corespondante à votre recherche//",
	  						   "Définitions corespondante à votre recherche",
	  						   "Le mot exact n'a pas été trouvé",
	  						   "choisissez la bonne orthographe.", 
	  						   "donc aucune définition correspondante. Veuillez vérifier l'orthographe ...",
	  						   "Aucun mot trouvé"};

	

	public Scraping(String urlWord, String blocWord, String baliseWord, String urlVerb, String blocVerb) {

		this.noWord 		 = noWord;
		this.femPlur		 = femPlur;
		this.urlWord		 = urlWord;
		this.urlVerb 		 = urlVerb;
		this.blocVerb 		 = blocVerb;
		this.blocWord 		 = blocWord;
		this.verbError       = verbError;
		this.baliseWord      = baliseWord;
		this.characterSpe    = characterSpe;
		this.ponctuationMap  = ponctuationMap;
		this.ponctuationList = ponctuationList;
	}




	
	

	public List<ArrayList<String>> wordSyntaxe(List<ArrayList<String>> dataText, 
											   RU readUpdate, int textNumberFile) throws Exception {

		addValuePonctuation(this.ponctuationMap);
		addValueToDictionnayEncode(this.characterSpe);

		List<ArrayList<String>> Syntaxe = new ArrayList<ArrayList<String>>();

		for (int text=0; text < dataText.size(); text++) {

			Syntaxe.add(new ArrayList<String>());
			ArrayList<String> lastSentence = Syntaxe.get(Syntaxe.size() - 1);

			tirateCase(dataText.get(text));
			
			
			for (String wordSentence: dataText.get(text)) {

				wordSentence = threePoint(wordSentence);
				
				boolean isPonctuation 	 = Ponctuation(wordSentence);
				boolean isContraction    = wordSentence.contains("contraction=");

				if      (isPonctuation) {addPonctuation(lastSentence, wordSentence); }
				else if (!isPonctuation) {

					wordSentence             = contraction(wordSentence, isContraction);
					Document htmlWord  		 = connexionURLWord(this.urlWord, wordSentence);	
					String encodeWord        = encodeVerbForUrl(wordSentence);
					Document htmlVerb	 	 = connexionURLWord(this.urlVerb, encodeWord);
					String wordHtml 	     = recuperateWorFromdBalise(htmlWord, this.blocWord, this.baliseWord, wordSentence);
					String verb              = recuperateVerbFromBalise(htmlVerb, wordSentence);
					
					List<String> toReasearch = noFoundWord(wordHtml, wordSentence);
					if (toReasearch.size() > 0) { System.out.println(toReasearch); }
					wordHtml                 = researchIfNoFound(toReasearch, wordSentence, wordHtml);

					
							   
					String a = "Aucun mot trouvé, donc aucune définition correspondante. Veuillez vérifier l'orthographe";
					String b = "Le mot exact n'a pas été trouvé, choisissez la bonne orthographe.";
					if (wordHtml.contains(a) || wordHtml.contains(b)) { wordHtml = "Aucun mot trouvé"; }
	
					
					if      (!isContraction)  {lastSentence.add(wordHtml + "/" + verb);
											   System.out.println(wordSentence + " " + wordHtml + "/" + verb);}
					else if ( isContraction)  {lastSentence.add("contraction=" + wordSentence + "/" + wordHtml + "/" + verb);
											   System.out.println(wordSentence + " " + wordSentence + "/" + wordHtml + "/" + verb);}
				}
			}
			

			saveData(readUpdate, textNumberFile, lastSentence, dataText.get(text));
		}
		return Syntaxe;
	}
	

	
	

	
	
	
	
	private void tirateCase(ArrayList<String> sentence) {
		
		int index = 0;
		int mark  = 0;
		for (String word: sentence) {
			
			int     tirate        = 0;
			
			for (int letter=0; letter < word.length(); letter++) {
				boolean isTirate = Character.toString(word.charAt(letter)).equalsIgnoreCase("-");
				if (isTirate) { tirate += 1; }
			}
			
			boolean haveTwoTirate = tirate > 1;
			if (haveTwoTirate) {
				mark = index;
				break;
			}
			index++;
		}
		boolean haveLengthMoreOne = mark > 0 && sentence.get(mark).split("-").length > 0;
		if (haveLengthMoreOne) {
			String[] elementSplit = sentence.get(mark).split("-");
			sentence.remove(mark);
			int adding = 0;
			for (String el: elementSplit) {
				sentence.add(mark + adding, el);
				adding++;
			}
		}
	}


	public void saveData (RU readUpdate, int textNumber, ArrayList<String> syntaxe,
		ArrayList<String> sentence) throws IOException {

		readUpdate.writtingText("textsWordsPonct", textNumber, syntaxe);
		readUpdate.writtingText("textsTreat"     , textNumber, sentence);
	}



	
	
	public String researchIfNoFound(List<String> toReasearch, String wordSentence, String foundWord) throws Exception {

		boolean needResearch = toReasearch.size() > 0;
		List<String> container = new ArrayList<String>();

		if (needResearch) {

			for (String reasearch: toReasearch) {
				System.out.println("in course: " + reasearch);
				Document html = connexionURLWord(this.urlWord, reasearch);
				String wordHtml = recuperateWorFromdBalise(html, this.blocWord, this.baliseWord, reasearch);
				System.out.println("found " + wordHtml);
	
				String sentence1 = "Aucun mot trouvé, donc aucune définition correspondante. Veuillez vérifiez l'orthographe ...";
				String sentence2 = "Définitions corespondante à votre recherche";
				String sentence3 = "Le mot exact n'a pas été trouvé";
				String sentence4 = "Aucun mot trouvé, donc aucune définition correspondante. Veuillez vérifier l'orthographe";


				boolean noFoundOut1  = wordHtml.contains(sentence1) || sentence1.contains(wordHtml);
				boolean noFoundOut2  = wordHtml.contains(sentence2) || sentence2.contains(wordHtml);
				boolean noFoundOut3  = wordHtml.contains(sentence3) || sentence3.contains(wordHtml);
				boolean noFoundOut4  = wordHtml.contains(sentence4) || sentence4.contains(wordHtml);
			
				boolean isIn         = container.contains(wordHtml);

				boolean noFound = noFoundOut1 || noFoundOut2 || noFoundOut3 || noFoundOut4 || isIn;

				if (!noFound) { 
					container.add(wordHtml);
				}
			}
		}


		boolean containerIsEmpty = container.size() == 0;

		if (!containerIsEmpty) {
			String increment = "";
			for (String element: container) {increment += (element + "/");}
			return increment;
		}
		
		return foundWord;
	}
	
	
	
	
	
	public List<String> noFoundWord(String wordHtml, String word) {


		
		boolean wordFound = true;
		

		
		List<String> femPlurWord = new ArrayList<String>();

		for (String no: this.noWord) {if (no.contains(wordHtml) || wordHtml.contains(no)) {wordFound = false; break; }}

		if (!wordFound) {

			for (String terminaison: this.femPlur) {
				int length = terminaison.length();

				String end = "";
				switch(length) {
					case 1:
						end +=  word.substring(word.length() - 1);
						incrementOrNot(femPlurWord, end, terminaison, word);
					break;

					case 2:
						boolean wordLengthMoreTwo = word.length() > 1;
						if (wordLengthMoreTwo) {
							end +=  word.substring(word.length() - 2, word.length());
							incrementOrNot(femPlurWord, end, terminaison, word);
						}
					break;
				}
			}
		}
		return femPlurWord;
	}

	public static void incrementOrNot(List<String> femPlurWord, String end, String terminaison, String mot) {

		boolean termPossibility = end.equalsIgnoreCase(terminaison);

		if (termPossibility) {
			femPlurWord.add(mot.substring(0, mot.length() - terminaison.length()));
		}
	}
	
	
	
	
	
	
	public String recuperateWorFromdBalise(Document html, String bloc, String balise, String word) throws Exception {

		String toIncrement = "";
		
		for (org.jsoup.nodes.Element row: html.select(bloc)) {

			String workIncrement = "";
			String htmlText = row.select(balise).text();
			
			for (int nb = 0; nb < htmlText.length(); nb++) {
				
				boolean space          = htmlText.charAt(nb) == ' ';
				boolean wordSearch     = workIncrement.equalsIgnoreCase(word);
				boolean openParenthese = htmlText.charAt(nb) == ')' || htmlText.charAt(nb) == '(';

				if     (space && wordSearch) {workIncrement = "";}
				else if(space && !wordSearch) {toIncrement += workIncrement + " "; workIncrement = "";}
				else if(openParenthese) {}
				else   {workIncrement += htmlText.charAt(nb);}
			}
			toIncrement += workIncrement += "/";
		}
		return toIncrement;
	}
	
	
	
	
	
	
	public String recuperateVerbFromBalise(Document document, String word) {
		
		int lineIndex = 0;
		String verb   = "";

		boolean isWord = document.select("h2").text().toString().equalsIgnoreCase(this.verbError);

		if (!isWord) {
			for (org.jsoup.nodes.Element row: document.select(this.blocVerb)) {

				if      (lineIndex == 5 || lineIndex == 6) {verb += (row.text() + "#");}
				else if (lineIndex == 6)                   {verb += row.text();}
				lineIndex += 1;
			}
			verb = "verbe#" + verb;
		}
		return verb;
	}
	
	
	
	
	
	
	public String encodeVerbForUrl(String word) {

		String[] wordCharacter = word.split("");

		for (int nb = 0; nb < wordCharacter.length; nb++) {
			for (Entry<String, String> charac: this.characterSpe.entrySet()) {
				if (charac.getKey().equalsIgnoreCase(wordCharacter[nb])) {wordCharacter[nb] = charac.getValue();}
			}
		}
		String encodingWord = "";
		for (String charac: wordCharacter) {encodingWord += charac;}
		return encodingWord;
	}
	
	
	
	
	
	
	public Document connexionURLWord(String url, String word) throws IOException {
		final String connectUrl = url + word;
		//System.out.println("connecting in course: " + connectUrl);
		final Document document = Jsoup.connect(connectUrl).get();

		return document;
	}
	
	
	
	
	
	public String contraction(String wordSentence, boolean isContraction) {

		if (isContraction) {return wordSentence.substring("contraction=".length(), wordSentence.length());}
		else {return wordSentence;}
	}
	
	
	public String threePoint(String wordSentence) {
		
		boolean containsThreePoint1 = wordSentence.contains("...");
		boolean containsThreePoint2 = wordSentence.contains("…");
		boolean containsThreePoint3 = wordSentence.contains("…") || wordSentence.contains("…");

		boolean containsPurcent     = wordSentence.contains("%");
		
		boolean containsNum         = wordSentence.contains("°");
		

		if (containsThreePoint1) {
			wordSentence = wordSentence.substring(0, wordSentence.length() - 3); 
		}
		else if (containsThreePoint2 || containsThreePoint3) {
			wordSentence = wordSentence.substring(0, wordSentence.length() - 1) + " "; 
		}
		else if (containsNum) {
			wordSentence = "numéro";
		}
		else if (containsPurcent) {
			wordSentence = "pourcent";
		}
		return wordSentence;
	}
	
	public boolean Ponctuation(String wordSentence) {

		boolean isPonctuation = false;

		for (char ponctuation: this.ponctuationList) {
			if (Character.toString(ponctuation).equalsIgnoreCase(wordSentence)) {isPonctuation = true;}
		}
		
		return isPonctuation;
	}


	
	

	public void addPonctuation(ArrayList<String> lastSentence, String wordSentence) {

		for (Entry<Character, String> ponctuation: this.ponctuationMap.entrySet()) {

			boolean matchPonctuation = Character.toString(ponctuation.getKey()).equalsIgnoreCase(wordSentence);
			if (matchPonctuation) {lastSentence.add(ponctuation.getValue());}
		}
	}
	
	
	
	

	public void addValuePonctuation(Map<Character, String> ponctuationMap2) {

		ponctuationMap2.put('"', "guillement");
		ponctuationMap2.put(':', "deux points");
		ponctuationMap2.put('(', "parenthese ouvrante");
		ponctuationMap2.put(')', "parenthese fermante");
		ponctuationMap2.put('<', "bec ouvrant");
		ponctuationMap2.put('>', "bec fermant");
		ponctuationMap2.put('.', "point");
		ponctuationMap2.put(';', "point virgule");
		ponctuationMap2.put('/', "slash");
		ponctuationMap2.put(',', "virgule");
		ponctuationMap2.put('!', "point exclamation");
		ponctuationMap2.put('?', "point interrogation");
		ponctuationMap2.put('«', "guillement ouvrant");
		ponctuationMap2.put('»', "guillement fermant");
		ponctuationMap2.put('-', "tiret");
	}
	
	public void addValueToDictionnayEncode(Map<String, String> characterSpe) {
		characterSpe.put("é", "%E9");
		characterSpe.put("è", "%E8");
		characterSpe.put("ê", "%EA");
		characterSpe.put("â", "%E2");
		characterSpe.put("ç", "%E7");
		characterSpe.put("î", "%EE");
		characterSpe.put("à", "a");
		characterSpe.put("ô", "o");
		characterSpe.put("ù", "%F9");
		characterSpe.put("û", "%FB");
		characterSpe.put("œ", "oe");
		characterSpe.put("ï", "i");
		characterSpe.put("–", "");
		characterSpe.put("ë", "e");
		characterSpe.put("â", "a");
		characterSpe.put("ä", "a");
		characterSpe.put("î", "i");
		characterSpe.put("ï", "i");
		characterSpe.put("ö", "o");
		characterSpe.put("û", "u");
		characterSpe.put("ü", "u");
		
	}
	
	private static String indexagePonct(char[] ponctuationList2, String word) {

		for (char element: ponctuationList2) { 
			String elementString = Character.toString(element); 
			if (elementString.equalsIgnoreCase(word)) { word += " "; }
		}
		return word;
	}
	
	
	private static boolean thisListEqualWord(char[] ponctuationList2, String word) {
		boolean isEqual = false;
		for (char element: ponctuationList2) { 
			String elementString = Character.toString(element); 
			if (elementString.equalsIgnoreCase(word) && !isEqual) {isEqual = true;}
		}
		return isEqual;
	}
}

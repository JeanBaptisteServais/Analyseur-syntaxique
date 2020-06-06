package fr.jbaw.programme2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecupFile {


	private String text;
	private String pathTextTreated;
	private String pathSyntaxe;


	
	public RecupFile(String text, String pathTextTreated, String pathSyntaxe) {
		this.text = text;
		this.pathSyntaxe = pathSyntaxe;
		this.pathTextTreated = pathTextTreated;
	}



	public String[] getTextPath() {

		String[] pathsCore = {this.pathTextTreated, this.pathSyntaxe};

		for (int nb=0; nb < pathsCore.length; nb++) {pathsCore[nb] = pathsCore[nb] + this.text;}
		return pathsCore;
	}

	

	public List<String> recuperateFiles(String text) throws IOException {

		List<String> stockText = new ArrayList<String>();

		
		FileInputStream   file = new FileInputStream(text);
		InputStreamReader stream = new InputStreamReader(file, "WINDOWS-1252");
		BufferedReader 	  reader = new BufferedReader(stream);
		String 			  line = reader.readLine();

		while(line != null) {
			stockText.add(line.toString());
			line = reader.readLine();
		}

		reader.close();
		return stockText;
	}

	
	

	public List<ArrayList<String>> recuperateSentenceText(List<String> textList, String separateur) {

		List<ArrayList<String>> textRecuperation = new ArrayList<ArrayList<String>>();


		for (int nbSentence = 0; nbSentence < textList.size() - 1; nbSentence++) {

			String   sentenceList      = textList.get(nbSentence);
			String   sentenceSubstring = sentenceList.substring(1, sentenceList.length() - 1);
			String[] sentenceSplit     = sentenceSubstring.split(separateur);

			textRecuperation.add(new ArrayList<String>());

			for (String element: sentenceSplit) {textRecuperation.get(textRecuperation.size() - 1).add(element);}
		}
		return textRecuperation;
	}

}

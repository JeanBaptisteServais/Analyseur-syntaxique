package fr.jbaw.programme1;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


//Ici on read, upload
public class RU{

	
	private String textPath;
	private String resumPath;
	private String pathComuter;
	
	public RU(String textPath, String resumPath, String pathComuter) {
		this.textPath = textPath;
		this.resumPath = resumPath;
		this.pathComuter = pathComuter;
	}

	


	public File[] treatDocument() {

		File fileText = new File(textPath);
		File fileResum = new File(resumPath);
		

		File[] files = {fileText, fileResum};
		return files;
	}
	

	

	
	public List<ArrayList<String>> stockText (File data, String path) throws IOException {

		List<ArrayList<String>> Text = new ArrayList<ArrayList<String>>();
		
		if (data.list().length > 0) {
			
			for (int nb = 0; nb < 1; nb ++) {

				File pathFile = new File(path + "\\" + String.valueOf(nb) + ".txt");

				FileInputStream connectFile = new FileInputStream(pathFile); //Connexion to file.
				InputStreamReader decodeFile = new InputStreamReader(connectFile, "UTF-8"); //decodeur of file.
				BufferedReader readerFile = new BufferedReader(decodeFile);//Reading of the file.

				List<String> textArray = new ArrayList<String>();
				String lineFile = readerFile.readLine();

				while(lineFile != null) {
					textArray.add(lineFile);
					lineFile = readerFile.readLine();
				}

				readerFile.close();

				Text.add(new ArrayList<String>());
				for(String words: textArray) {Text.get(Text.size() - 1).add(words);}
				textArray.clear();
			}
		}
		return Text;
	}
	
	
	

	
 	public List<ArrayList<ArrayList<String>>> textToWord(List<ArrayList<String>> data) { //Treat the text

 		List<ArrayList<ArrayList<String>>> dataTreat = new ArrayList<ArrayList<ArrayList<String>>>();

		for (int nb = 0; nb < data.size(); nb ++) {

			dataTreat.add(new ArrayList<ArrayList<String>>());

			for (String lines : data.get(nb)) {

				dataTreat.get(dataTreat.size() - 1).add(new ArrayList<String>());

				String[] wordsSplit = lines.split(" ");

				ArrayList<String> container = new ArrayList<String>();
				for (String words : wordsSplit) {container.add(words);}

				dataTreat.get(dataTreat.size() - 1).add((ArrayList<String>) container);
			}
		}
		return dataTreat;
	}
	



	public void writtingText (String mode,  int nameText, 
							  ArrayList<String> listSentence) throws IOException{

		String path = this.pathComuter + mode + "//" + nameText + ".txt";

		File file = new File(path);
		if (!file.exists()) {file.createNewFile();}

		FileWriter fw = new FileWriter(file, true);

		fw.write(listSentence.toString());
		fw.write("\n");
		fw.close();

	}

}
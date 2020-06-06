package fr.jbaw.programme2;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class groupPrepositionel extends searchingInSentence{

	
	

	private ArrayList<ArrayList<String>> currentSyntaxe;
	private ArrayList<String> 			 currentText;
	private Map<Integer, String> 		 prepositionFunction;


	public groupPrepositionel(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText,
			  			  	  Map<Integer, String> prepositionFunction) {

		this.currentSyntaxe  = currentSyntaxe;
		this.currentText     = currentText;
		this.prepositionFunction = prepositionFunction;

		findPreposition();
	}





	private String entrance;


	public groupPrepositionel(String entrance) {
		this.entrance = entrance;
	}


	public void searchPrepoFunction(ArrayList<String> containerPreposition) {
		

		String[] matiere        = {"en"};

		String[] lieu           = {"dans", "�", "sous", "sur", "en", "devant", "derri�re", 
								   "devant", "entre", "face �", "contre", "chez", "jusque �"};

		String[] prospectif     = {"sur"};

		String[] accompagnement = {"avec", "sans"};

		String[] temps          = {"�", "dans", "depuis", "durant", "pendant", "jusque �", "avant" ,"apr�s"};

		String[] but            = {"pour", "afin", "envers", "dans le but de", "afin de"};

		String[] cause          = {"pour", "en raison de", "� cause de", "sous pr�texte de", "gr�ce �", "de peur de"};

		String[] maniere        = {"en", "avec", "�", "de", "par", "avec", "sans"};
		String[] appartenance   = {"de"};

		String[] origine        = {"de", };
		String[] mati�re        = {"de", "en"};
		String[] destination    = {"pour", "en destination de", "vers", "�"};
		String[] agent          = {"par", "de"};
		String[] opposition     = {"contre", "malgr�"};
		String[] moyen          = {"avec", "sans", "au moyen de", "gr�ce �"};
		
		
		
		
		String foundFunction = "";

		entrance = entrance.toLowerCase();

		
		foundFunction = matching(origine, entrance, "origine");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }
		
		foundFunction = matching(mati�re, entrance, "mati�re");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }
		
		foundFunction = matching(destination, entrance, "destination");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }
		
		foundFunction = matching(agent, entrance, "agent");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }

		foundFunction = matching(opposition, entrance, "opposition");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }
		
		foundFunction = matching(moyen, entrance, "moyen");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }

		foundFunction = matching(accompagnement, entrance, "accompagnement");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }
		
		foundFunction = matching(prospectif, entrance, "prospectif");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }
		

		foundFunction = matching(lieu,  entrance, "lieu");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }
		
		foundFunction = matching(temps, entrance, "temps");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }
		
		foundFunction = matching(but,  entrance, "but");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }
		
		foundFunction = matching(cause,  entrance, "cause");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }
		
		foundFunction = matching(maniere,  entrance, "maniere");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }
		
		foundFunction = matching(appartenance, entrance, "appartenance");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }
	}

	
	private String matching(String[] list, String entrance, String function) {

		String out = "";
		
		for (String element: list) {
			boolean isMatching = element.equalsIgnoreCase(entrance);
			if (isMatching) { return function; }
		}
		return out;
	}
	
	
	
	
	
	
	
	
	public void findPreposition(){

		for (int syntaxe = 0; syntaxe < this.currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList = this.currentSyntaxe.get(syntaxe);
			String word                   = this.currentText.get(syntaxe);

			boolean isPreposition = currentList.get(0).equalsIgnoreCase("Pr�position");

			if (isPreposition) {this.prepositionFunction.put(syntaxe, word);}
		}
	}

}

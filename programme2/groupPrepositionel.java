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

		String[] lieu           = {"dans", "à", "sous", "sur", "en", "devant", "derrière", 
								   "devant", "entre", "face à", "contre", "chez", "jusque à"};

		String[] prospectif     = {"sur"};

		String[] accompagnement = {"avec", "sans"};

		String[] temps          = {"à", "dans", "depuis", "durant", "pendant", "jusque à", "avant" ,"après"};

		String[] but            = {"pour", "afin", "envers", "dans le but de", "afin de"};

		String[] cause          = {"pour", "en raison de", "à cause de", "sous prétexte de", "grâce à", "de peur de"};

		String[] maniere        = {"en", "avec", "à", "de", "par", "avec", "sans"};
		String[] appartenance   = {"de"};

		String[] origine        = {"de", };
		String[] matière        = {"de", "en"};
		String[] destination    = {"pour", "en destination de", "vers", "à"};
		String[] agent          = {"par", "de"};
		String[] opposition     = {"contre", "malgré"};
		String[] moyen          = {"avec", "sans", "au moyen de", "grâce à"};
		
		
		
		
		String foundFunction = "";

		entrance = entrance.toLowerCase();

		
		foundFunction = matching(origine, entrance, "origine");
		if (foundFunction != "") { containerPreposition.add(foundFunction); }
		
		foundFunction = matching(matière, entrance, "matière");
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

			boolean isPreposition = currentList.get(0).equalsIgnoreCase("Préposition");

			if (isPreposition) {this.prepositionFunction.put(syntaxe, word);}
		}
	}

}

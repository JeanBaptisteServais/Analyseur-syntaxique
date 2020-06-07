package fr.jbaw.programme2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class groupAdverbial {

	
	
	



	private ArrayList<ArrayList<String>> currentSyntaxe;
	private ArrayList<String> 			 currentText;
	private Map<Integer, String> 	 	 adverbeFunction;
	
	
	
	private String advb;
	Map<String, String> ADVERBDICO = new HashMap<String, String>();


	
	
	public groupAdverbial(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText,
						  Map<Integer, String> adverbeFunction) {
	
		this.currentSyntaxe  = currentSyntaxe;
		this.currentText     = currentText;
		this.adverbeFunction = adverbeFunction;
		
		groupAdverbialSearch();

	}

	
	public void groupAdverbialSearch() {

		for (int syntaxe = 0; syntaxe < this.currentSyntaxe.size(); syntaxe++) {

			ArrayList<String> currentList = this.currentSyntaxe.get(syntaxe);
			String word                   = this.currentText.get(syntaxe);

			boolean isAdverbe = currentList.get(0).equalsIgnoreCase("Adverbe");

			if (isAdverbe) {this.adverbeFunction.put(syntaxe, word);}
		}	
	}
	
	
	
	
	
	
	private String[] maniere = {"remier", "peu", "bien", "vite", "mal", "gravement", "ncore", "amèrement",
			"gentilement", "doucement", "lentement", "tranquillement", "bien", "mal",
			"ainsi", "aussi", "partout", "comment", "pratiquement", "radicalement",
			"admirablement", "aussi", "ainsi", "bien", "comme", "comment", "debout", "doucement", "également",
			"ensemble", "exprès", "franco", "gratis", "impromptu", "incognito", "lentement", "mal",
			"mieux", "pis", "plutôt", "recta", "presque", "volontiers", "forcément", "officiellement", "même",
			"légè", "dangereusement"
			};
	
	
	private String[] quantité = {"beaucoup", "peu", "très", "trop", "assez", "autant", 
			"environ", "presque", "seulement", "tellement", "tant",
			"beaucoup", "moins", "encore", "trop", "ainsi", "à peine", 
			"à peu près", "absolument", "à demi", "assez", "aussi", "autrement", "approximativement","beaucoup",
			"carrément", "combien", "comme", "complètement", "davantage", "à demi", "diablement", "divinement",
			"drôlement", "entièrement", "extrêmement", "fort", "grandement", "guère", "insuffisamment",
			"joliment", "moins", "pas mal", "passablement", "peu", "plus", "plutôt", "presque",
			"prou", "quasi", "quasiment", "quelque", "rudement", "si", "suffisament", "tant",
			"tellement", "terriblement", "tout", "tout à fait", "très", "trop", "trop peu", "un peu", "tout à fait",
			};
	
	private String[] lieu = {"ici", "là", "là-bas", "ailleurs", "nulle part", "dedans",
			"dehors", "partout", "quelque part", "près", "à côté", "loin",
			"dedans", "dehors", "loins", "alentour", "arrière", "au-delà", "au-desosus", "au-dessus",
			"au-devant", "autour", "avant", "ça", "céans", "ci", "contre", "deçà", "dedans", "dehors", 
			"derrière", "dessus", "devant", "ici", "là", "Là", "là-haut", "Là-haut", "loin",
			"où", "outre", "partout", "prés", "proche", "sus", "y"};
	
		
	private String[] affirmation = {"oui", "si", "soit", "volontiers", "assurément", "aussi",
			"absolumenté", "certainement", "vraiment", "toutefois","assurément", "aussi", "bien", "bon", "certainement", "certes",
			"en vérité", "précisément", "tout à fait", "volontier", "vraiment", "exact", "sûrement"};

	private String[] temps = {"toujours", "hier", "aujourd'hui", "aujourde hui", "aujourde", "déjà",
			"avant-hier", "jadis", "passé", "depuis-peu", "désormais", "alors", "longtemps", "tandis", "auparavant",
			"tard"
			};
	
	private String[] négation = {"non", "jamais", "nullement", "pas", "point"};

	private String[] doute = {"apparaement", "peut-être", "probablement", "sans doute", "toutefois", "vraisemblablement"};
	
	
	public groupAdverbial(String advb) {
		this.advb = advb;
	}
	
	
	

	
	public String searchAdvbInDefineGroupe() {
		

		boolean quantit 	=  searchMatching(this.advb, this.quantité);
		if (quantit) { return "quantité"; }

		boolean maniere 	=  searchMatching(this.advb, this.maniere);
		if (maniere) { return "manière"; }

		boolean temps 		=  searchMatching(this.advb, this.temps);
		if (temps) { return "temps"; }
		
		boolean lieu 		=  searchMatching(this.advb, this.lieu);
		if (lieu) { return "lieu"; }
		
		boolean affirmation =  searchMatching(this.advb, this.affirmation);
		if (affirmation) { return "affirmation"; }
		
		boolean doute 		=  searchMatching(this.advb, this.doute);
		if (doute) { return "doute"; }
		
		boolean négation	=  searchMatching(this.advb, this.négation);
		if (négation) { return "négation"; }
		
		
		return "";
	}


	private boolean searchMatching(String adv, String[] list) {

		for (String element: list) { 
			boolean isMatching1 = element.contains(adv);
			boolean isMatching2 = adv.contains(element);
			
			if (isMatching1 || isMatching2) { return true; }
		}
		
		return false;
	}

	
	
	
	
	
	
	
	
	
	
	
	
}

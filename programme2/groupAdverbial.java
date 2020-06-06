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
	
	
	
	
	
	
	private String[] maniere = {"remier", "peu", "bien", "vite", "mal", "gravement", "ncore", "am�rement",
			"gentilement", "doucement", "lentement", "tranquillement", "bien", "mal",
			"ainsi", "aussi", "partout", "comment", "pratiquement", "radicalement",
			"admirablement", "aussi", "ainsi", "bien", "comme", "comment", "debout", "doucement", "�galement",
			"ensemble", "expr�s", "franco", "gratis", "impromptu", "incognito", "lentement", "mal",
			"mieux", "pis", "plut�t", "recta", "presque", "volontiers", "forc�ment", "officiellement", "m�me",
			"l�g�", "dangereusement"
			};
	
	
	private String[] quantit� = {"beaucoup", "peu", "tr�s", "trop", "assez", "autant", 
			"environ", "presque", "seulement", "tellement", "tant",
			"beaucoup", "moins", "encore", "trop", "ainsi", "� peine", 
			"� peu pr�s", "absolument", "� demi", "assez", "aussi", "autrement", "approximativement","beaucoup",
			"carr�ment", "combien", "comme", "compl�tement", "davantage", "� demi", "diablement", "divinement",
			"dr�lement", "enti�rement", "extr�mement", "fort", "grandement", "gu�re", "insuffisamment",
			"joliment", "moins", "pas mal", "passablement", "peu", "plus", "plut�t", "presque",
			"prou", "quasi", "quasiment", "quelque", "rudement", "si", "suffisament", "tant",
			"tellement", "terriblement", "tout", "tout � fait", "tr�s", "trop", "trop peu", "un peu", "tout � fait",
			};
	
	private String[] lieu = {"ici", "l�", "l�-bas", "ailleurs", "nulle part", "dedans",
			"dehors", "partout", "quelque part", "pr�s", "� c�t�", "loin",
			"dedans", "dehors", "loins", "alentour", "arri�re", "au-del�", "au-desosus", "au-dessus",
			"au-devant", "autour", "avant", "�a", "c�ans", "ci", "contre", "de��", "dedans", "dehors", 
			"derri�re", "dessus", "devant", "ici", "l�", "L�", "l�-haut", "L�-haut", "loin",
			"o�", "outre", "partout", "pr�s", "proche", "sus", "y"};
	
		
	private String[] affirmation = {"oui", "si", "soit", "volontiers", "assur�ment", "aussi",
			"absolument�", "certainement", "vraiment", "toutefois","assur�ment", "aussi", "bien", "bon", "certainement", "certes",
			"en v�rit�", "pr�cis�ment", "tout � fait", "volontier", "vraiment", "exact", "s�rement"};

	private String[] temps = {"toujours", "hier", "aujourd'hui", "aujourde hui", "aujourde", "d�j�",
			"avant-hier", "jadis", "pass�", "depuis-peu", "d�sormais", "alors", "longtemps", "tandis"
			};
	
	private String[] n�gation = {"non", "jamais", "nullement", "pas", "point"};

	private String[] doute = {"apparaement", "peut-�tre", "probablement", "sans doute", "toutefois", "vraisemblablement"};
	
	
	public groupAdverbial(String advb) {
		this.advb = advb;
	}
	
	
	

	
	public String searchAdvbInDefineGroupe() {
		

		boolean quantit 	=  searchMatching(this.advb, this.quantit�);
		if (quantit) { return "quantit�"; }

		boolean maniere 	=  searchMatching(this.advb, this.maniere);
		if (maniere) { return "mani�re"; }

		boolean lieu 		=  searchMatching(this.advb, this.lieu);
		if (lieu) { return "lieu"; }
		
		boolean affirmation =  searchMatching(this.advb, this.affirmation);
		if (affirmation) { return "affirmation"; }
		
		boolean doute 		=  searchMatching(this.advb, this.doute);
		if (doute) { return "doute"; }
		
		boolean temps 		=  searchMatching(this.advb, this.temps);
		if (temps) { return "temps"; }
		
		boolean n�gation	=  searchMatching(this.advb, this.n�gation);
		if (n�gation) { return "n�gation"; }
		
		
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

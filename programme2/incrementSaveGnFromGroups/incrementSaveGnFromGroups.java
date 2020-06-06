package fr.jbaw.programme2.incrementSaveGnFromGroups;

import java.util.ArrayList;

import fr.jbaw.programme2.searchWordUtils;

public class incrementSaveGnFromGroups extends searchWordUtils{

	private ArrayList<ArrayList<String>> saveSchema;
	private ArrayList<ArrayList<String>> currentSyntaxe;
	private ArrayList<String> 			 currentText;
	private ArrayList<ArrayList<String>> saveGn;

	
	
	public incrementSaveGnFromGroups(ArrayList<ArrayList<String>> saveSchema, ArrayList<ArrayList<String>> currentSyntaxe, 
			String[] DETERMINANTS, ArrayList<String> currentText, ArrayList<ArrayList<String>> saveGn) {
		
	
		this.saveSchema = saveSchema;
		this.currentSyntaxe = currentSyntaxe;
		this.DETERMINANTS = DETERMINANTS;
		this.currentText = currentText;
		this.saveGn = saveGn;
		
		

		boolean saveNotEmpty = saveSchema.get(saveSchema.size() - 1).size() >= 1;
		
		boolean isNotPrepo = false;
		if (saveNotEmpty) {
			isNotPrepo = saveSchema.get(saveSchema.size() - 1).get(0).contains("GPREP") && 
				         		 saveSchema.get(saveSchema.size() - 1).size() == 1;
		}
		ArrayList<String> sauvegarde = saveSchema.get(saveSchema.size() - 1);

		boolean isGroupVerbal = listContains(sauvegarde, "GVERBAL");
		boolean isGroupPrepo  = listContains(sauvegarde, "GPREP");
		boolean isGroupCmplt  = listContains(sauvegarde, "GCMPLT");
		boolean isGroupSujet  = listContains(sauvegarde, "GSUJET");
		boolean isGroupNomi   = listContains(sauvegarde, "GNOMINAL");
		boolean isGroupAdv    = listContains(sauvegarde, "GADV");


		ArrayList<String> containerPrep = new ArrayList<String>();
		String incrementGprep = "";
		if (!isGroupVerbal && isGroupPrepo && !isNotPrepo) {

			int[] indexs = recuperateIndexOfSchema(sauvegarde, "GPREP");

			for (int index=indexs[0]; index < indexs[1]; index++) {
				ArrayList<String> current = currentSyntaxe.get(index);

				String article  = recuperateFromList(current,  currentText, index, DETERMINANTS);
				String nom      = recuperateDataEqual(current, currentText, index, "Nom commun");
				String adjectif = recuperateDataEqual(current, currentText, index, "Adjectif");
				String préposi  = recuperateDataEqual(current, currentText, index, "Préposition");
				String Np       = recuperateDataEqual(current, currentText, index, "Nom propre");
				String Cnjnc    = recuperateDataEqual(current, currentText, index, "Conjonction de coordination");
			
				String[] search = {article, nom, adjectif, préposi, Np, Cnjnc};
				
				incrementGprep = incrementQuiSubject(search, incrementGprep);

			}
			treatementIncrements(incrementGprep, containerPrep);
		}
		

		ArrayList<String> containerVerb = new ArrayList<String>();
		String incrementVerb = "";
		if (isGroupVerbal && !isGroupSujet) {

			int[] indexs = recuperateIndexOfSchema(sauvegarde, "GVERBAL");
			
			for (int index=indexs[0]; index < indexs[1]; index++) {
				ArrayList<String> current = currentSyntaxe.get(index);
				
				String pronom   = recuperateDataEqual(current, currentText, index, "Pronom personnel");

				String[] search = {pronom};
				
				boolean firstPronom = incrementVerb.equalsIgnoreCase("");  //Recuperate only one pronom: je te.
				if (firstPronom) {
					incrementVerb = incrementQuiSubject(search, incrementVerb);
				}
				
			}
			treatementIncrements(incrementVerb, containerVerb);
		}
		
		

		ArrayList<String> containerSujet = new ArrayList<String>();
		String incrementSujet = "";
		if (isGroupSujet && !isGroupAdv) {

			int[] indexs = recuperateIndexOfSchema(sauvegarde, "GSUJET");
			
			for (int index=indexs[0]; index < indexs[1]; index++) {
				ArrayList<String> current = currentSyntaxe.get(index);

				String article  = recuperateFromList(current,  currentText, index, DETERMINANTS);
				String nom      = recuperateDataEqual(current, currentText, index, "Nom commun");
				String adjectif = recuperateDataEqual(current, currentText, index, "Adjectif");
				String préposi  = recuperateDataEqual(current, currentText, index, "Préposition");
				String Np       = recuperateDataEqual(current, currentText, index, "Nom propre");
				String Cnjnc    = recuperateDataEqual(current, currentText, index, "Conjonction de coordination");


				String[] search = {article, nom, adjectif, préposi, Np, Cnjnc};
				
				incrementSujet = incrementQuiSubject(search, incrementSujet);

			}
			treatementIncrements(incrementSujet, containerSujet);
		}
		
		
		
		
		ArrayList<String> containerGnomi = new ArrayList<String>();
		String incrementGNominal = "";
		if (isGroupNomi && !isGroupAdv) {
			
			int[] indexs = recuperateIndexOfSchema(sauvegarde, "GNOMINAL");

			for (int index=indexs[0]; index < indexs[1]; index++) {
				ArrayList<String> current = currentSyntaxe.get(index);

				String article  = recuperateFromList(current,  currentText, index, DETERMINANTS);
				String nom      = recuperateDataEqual(current, currentText, index, "Nom commun");
				String adjectif = recuperateDataEqual(current, currentText, index, "Adjectif");
				String préposi  = recuperateDataEqual(current, currentText, index, "Préposition");
				String Np       = recuperateDataEqual(current, currentText, index, "Nom propre");
				String Cnjnc    = recuperateDataEqual(current, currentText, index, "Conjonction de coordination");
				
				String[] search = {article, nom, adjectif, préposi, Np, Cnjnc};
				
				incrementGNominal = incrementQuiSubject(search, incrementGNominal);

			}
			treatementIncrements(incrementGNominal, containerGnomi);
		}


		
		
		
		ArrayList<String> containerCmpltWithAdv = new ArrayList<String>();
		String incrementGNominalWithAdv = "";
		if (isGroupNomi && isGroupAdv) {

			int[] indexs = recuperateIndexOfSchema(sauvegarde, "GNOMINAL");

			for (int index=indexs[0]; index < indexs[1]; index++) {
				ArrayList<String> current = currentSyntaxe.get(index);

				String article  = recuperateFromList(current,  currentText, index, DETERMINANTS);
				String nom      = recuperateDataEqual(current, currentText, index, "Nom commun");
				String adjectif = recuperateDataEqual(current, currentText, index, "Adjectif");
				String préposi  = recuperateDataEqual(current, currentText, index, "Préposition");
				String Np       = recuperateDataEqual(current, currentText, index, "Nom propre");
				String Cnjnc    = recuperateDataEqual(current, currentText, index, "Conjonction de coordination");
				
				String[] search = {article, nom, adjectif, préposi, Np, Cnjnc};
				
				incrementGNominalWithAdv = incrementQuiSubject(search, incrementGNominalWithAdv);
			}
			treatementIncrements(incrementGNominalWithAdv, containerCmpltWithAdv);
		}
		
		

		ArrayList<String> container = new ArrayList<String>();
		incrementation(containerPrep, containerVerb, containerCmpltWithAdv, containerSujet, containerGnomi, container, saveGn);
		
		
		
		
		
		
		
	}
	
	
}

package fr.jbaw.programme2.prepositionTreatment;

import java.util.ArrayList;
import java.util.List;

import fr.jbaw.programme2.searchWordUtils;

public class prepositionTreatment extends searchWordUtils{

	private ArrayList<String> container;
	private ArrayList<String> containerData;
	private ArrayList<String> containerPrepo;
	private ArrayList<String> groupsSyntaxe;
	private int               index;
	private ArrayList<String> GROUPS;

	
	public prepositionTreatment(ArrayList<String> container, ArrayList<String> containerData, 
			ArrayList<String> containerPrepo, ArrayList<String> groupsSyntaxe, 
			int index, ArrayList<String> GROUPS, int inSentence, ArrayList<String> currentText) {
	
		this.container      = container;
		this.containerData  = containerData;
		this.containerPrepo = containerPrepo;
		this.groupsSyntaxe  = groupsSyntaxe;
		this.index          = index;
		this.GROUPS         = GROUPS;
		

		String groupNominal = "quoi=";

		int[] indexs = recuperateIndexFromGroupInList(groupsSyntaxe, "GPREP");
		int   begin  = indexs[0];
		int   end    = indexs[1] + 1;

		List<String> text = currentText.subList(begin, end);

		for (String element: text) { groupNominal += (element + " "); }
		
		boolean incrementNotEmpty = !(groupNominal.equals(""));
		if (incrementNotEmpty) { groupNominal = groupNominal.substring(0, groupNominal.length() - 1); }
		
		containerData.add(groupNominal);
	}



	
	
	
	public prepositionTreatment(int begening, int end, ArrayList<String> containerData, ArrayList<String> container) {
		
		String groupDetail = "";
		String prepo       = "";

		for (String data: container) {

			String[] dataInfo   = treatementDataEqual(data);
			boolean  isNotPrepo = data.split("=").length > 1;
			
			if    (isNotPrepo) { groupDetail += (dataInfo[1] + " "); }
			else { prepo += data; }
		}

		String increment = "";
		boolean notEmpty = !groupDetail.equalsIgnoreCase("");
		if (notEmpty) { increment = "precisionQui=" + prepo + " " + groupDetail.substring(0, groupDetail.length() - 1); }
	
		containerData.add(increment);
	}

	
	
}

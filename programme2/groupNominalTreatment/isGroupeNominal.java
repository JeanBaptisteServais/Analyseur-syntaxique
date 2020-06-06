package fr.jbaw.programme2.groupNominalTreatment;

import java.util.ArrayList;

import fr.jbaw.programme2.searchWordUtils;

public class isGroupeNominal extends searchWordUtils{
	
	
	public isGroupeNominal(int begening, int end, ArrayList<String> containerData, ArrayList<String> container) {
		

		String[] groupNominal = {"noyeauQuoi=", "detQuoi=", "quoi=", "preciQuoi="};


		for (String data: container) {

			boolean isArt = data.contains("determinant");
			boolean isNc  = data.contains("Noyeau");
			boolean isAdj = data.contains("epithete");

			boolean havNc = !groupNominal[2].equalsIgnoreCase("quoi=");
			
			String[] dataInfo = treatementDataEqual(data);
			

			if      (isArt) { groupNominal[1] += (dataInfo[1] + " "); }
			else if (isNc)  { groupNominal[0] += (dataInfo[1] + " "); }
			else if (isAdj) { groupNominal[3] += (dataInfo[1] + " "); }

			if (isAdj && havNc)  { groupNominal[2] += (dataInfo[1] + " "); groupNominal[3] = "preciQuoi="; }


			if (isNc)            { groupNominal[3] = "preciQuoi="; }
		}

		for (String element: groupNominal) {
			element = element.substring(0, element.length() - 1);
			containerData.add(element);
		}
	}
	
}

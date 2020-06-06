package fr.jbaw.programme2.actionTreatment;

import java.util.ArrayList;

import fr.jbaw.programme2.searchWordUtils;

public class actionTreatment extends searchWordUtils{

	private ArrayList<String> container;
	private ArrayList<String> containerData;
	private ArrayList<String> currentText;
	
	
	
	public actionTreatment(ArrayList<String> container, ArrayList<String> containerData, ArrayList<String> currentText) {

		this.container     = container;
		this.containerData = containerData;
		this.currentText   = currentText;
		
		String[] groupVerbal = {"qui=", "action=", "aqui=", "quoi=", "negation=", "refl=", "target1=", 
								"target2=", "time1=", "time2=", "subject?=", "isDesign=", "dem="};

		boolean prnmFound = false;
		for (String data: container) {

			boolean[] conditions   = makeConditionsInData(data);
			boolean   isVerbe      = conditions[0];
			boolean   isPronom     = conditions[1];
			boolean   isCmpltCoi   = conditions[2];
			boolean   isCmpltCod   = conditions[3];
			boolean   isNegation   = conditions[4];
			boolean   isCmpltRefl  = conditions[5];
			
			boolean   targetPronm  = conditions[6];
			boolean   isTime       = conditions[7];
			boolean   toSearch     = conditions[8];
			boolean   demons       = conditions[9];

			boolean[] conditions2  = makeConditionEmptyData(groupVerbal);
			boolean   isEmpty1     = conditions2[0];
			boolean   isEmpty2     = conditions2[1];
			boolean   isEmpty3     = conditions2[2];

			String[] dataInfo = treatementDataEqual(data);


			if (demons)                            { groupVerbal[12] += dataInfo[1]; }

			else if (isPronom && !prnmFound)       { groupVerbal[0]  += dataInfo[1]; prnmFound = true; }

			else if (isVerbe)                      { groupVerbal[1]  += dataInfo[1] + " "; }
			else if (isCmpltCoi &&  prnmFound)     { groupVerbal[2]  += dataInfo[1]; }
			else if (isCmpltCoi && !prnmFound)     { groupVerbal[10] += dataInfo[1]; }

			else if (isCmpltCod)                   { groupVerbal[3]  += dataInfo[1]; }
			else if (isNegation)   	               { groupVerbal[4]  += "ne"; }
			else if (isCmpltRefl && isEmpty3)      { groupVerbal[5]  += dataInfo[1]; }

			else if (targetPronm &&  isEmpty2)     { groupVerbal[6]  += dataInfo[1]; }
			else if (targetPronm && !isEmpty2)     { groupVerbal[7]  += dataInfo[1]; }

			else if (isTime 		&&  isEmpty1)  { groupVerbal[8]  += dataInfo[1]; }
			else if (isTime 		&& !isEmpty1)  { groupVerbal[9]  += dataInfo[1]; }
			else if (toSearch)                     { groupVerbal[11] += dataInfo[1]; }
			
		}

		boolean isNotPrnm = groupVerbal[0].equalsIgnoreCase("qui=");
		boolean isDems    = groupVerbal[12].equalsIgnoreCase("dem=");
		
		boolean isDemNoPrnm = isNotPrnm && !isDems;
		if (isDemNoPrnm) { groupVerbal[0] += groupVerbal[12].split("=")[1]; }
		
		incrementPronomReflechisToAction(groupVerbal);

		groupVerbal[1] = groupVerbal[1].substring(0, groupVerbal[1].length() - 1);
		for (String data: groupVerbal) { containerData.add(data); }

	}
	


	//3)
	private void incrementPronomReflechisToAction(String[] groupVerbal) {

		boolean havePrnmRefl = !(groupVerbal[5].equalsIgnoreCase("refl="));
		boolean haveVerb     = !(groupVerbal[1].equalsIgnoreCase("action="));

		if (havePrnmRefl && haveVerb) {
			String reflechis = groupVerbal[5].split("=")[1];
			String verbe     = groupVerbal[1].split("=")[1];

			groupVerbal[1] = "action=" + reflechis + " " + verbe;
		}
	}

	


	//2)
	private boolean[] makeConditionEmptyData(String[] groupVerbal) {
		
		boolean isEmpty1    = groupVerbal[8].equalsIgnoreCase("time1=");
		boolean isEmpty2    = groupVerbal[7].equalsIgnoreCase("target1=");
		boolean isEmpty3    = groupVerbal[5].equalsIgnoreCase("refl=");
		
		boolean[] conditions = {isEmpty1, isEmpty2, isEmpty3};

		return conditions;
	}
	
	
	
	//1)
	private boolean[] makeConditionsInData(String data) {

		boolean isVerbe       = data.contains("verbe");
		boolean isPronom      = data.contains("sujet");
		boolean isCmpltCoi    = data.contains("complement PRSN");
		
		boolean isCmpltCod    = data.contains("complement OBJT");
		boolean isNegation    = data.contains("négation") && textContains(currentText, "pas");
		boolean isCmpltRefl   = data.contains("complement reflechi");
		
		boolean targetPronm   = data.contains("=design=");
		boolean isTime        = data.contains("temps.");
		boolean toSearch      = data.contains("isDesign");
		
		boolean demons        = data.contains("Pronom demonstratif");
		
		
		boolean[] conditions = {isVerbe, isPronom, isCmpltCoi, isCmpltCod, isNegation, isCmpltRefl, targetPronm,
							    isTime, toSearch, demons};
		
		return conditions;
	}
	
	
}

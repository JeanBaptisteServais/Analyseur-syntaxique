package fr.jbaw.programme2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class defineGroups  extends searchingInSentence {

	private String[] DETERMINANTS = {"Article indéfini", "Article défini", "Article défini numéral",
									 "Pronom indéfini", "Forme de pronom indéfini", "Forme d’article défini",
									 "pronom défini", "Adjectif indéfini", "Adjectif possessif", "Adjectif démonstratif",
									 "Forme d'Adjectif possessif", "Forme d'Adjectif démonstratif", "Forme d’adjectif indéfini",
									 "déterminants interrogatifs","Forme d'article défini", "Forme d'article indéfini",
									 "Forme d’Adjectif possessif", "Forme d’Adjectif démonstratif", "Forme d’adjectif démonstratif",
									 "Forme d’article défini", "Forme d’article indéfini", "Adjectif interrogatif",
								     "Adjectif exclamatif", "Adjectif", "Forme d’adjectif", "Adjectif numéral",
								     "Pronom indéfini", "Adjectif indéfini", "Adjectif défini"};
	
	String[] pronomPersonnelCOD = {")le ", ")la ", ")en ", ")un ", ")une ", ")les ", ")contraction=le ", ")me ", ")te ",
									")contraction=me ", ")contraction=te "};


	private Map<Integer, String>		 groupNominalFunctionBeginIndex;
	private Map<Integer, String>		 groupsVerbalsFunctionBeginIndex;
	private Map<Integer, String>		 pronomToGNBeginIndex;
	private Map<Integer, String>		 adverbeFunction;
	private Map<Integer, String>		 propositionRelativeFunction;
	private Map<Integer, String> 		 prepositionFunction;
	private Map<Integer, String> 		 conjonctionLocalisation;
	private ArrayList<String>            currentText;
	private ArrayList<ArrayList<String>> currentSyntaxe;
	
	
	private ConjonctionDeCoordination    conjonction;

	private ArrayList<String>            original;

	private Map<Integer, String> 		 WORKLIST    = new HashMap<Integer, String>();
	
	
	private ArrayList<String> 			 GROUPSOFGROUP;
	
	private Map<String, String> 		 GROUPEINFORMATIONS;


	private ArrayList<String> 			 containerSyntaxeSubject = new ArrayList<String>();
	private ArrayList<String> 			 containerSyntaxeVerbal  = new ArrayList<String>();

	private String SAVEPATH;





	

	public defineGroups(Map<Integer, String>         groupNominalFunctionBeginIndex, 
						Map<Integer, String>         groupsVerbalsFunctionBeginIndex, 
						Map<Integer, String>         pronomToGNBeginIndex, 
						Map<Integer, String>         adverbeFunction,
						Map<Integer, String>         propositionRelativeFunction,
						Map<Integer, String>         prepositionFunction, 
						Map<Integer, String>         conjonctionLocalisation, 
						ArrayList<ArrayList<String>> currentSyntaxe, 
						ArrayList<String>            currentText,
						ArrayList<String>            original,
						ConjonctionDeCoordination    conjonction,
						Map<String, String> 		 GROUPEINFORMATIONS,
						ArrayList<String>            GROUPSOFGROUP, String SAVEPATH) throws IOException {





		this.groupNominalFunctionBeginIndex  = groupNominalFunctionBeginIndex;
		this.groupsVerbalsFunctionBeginIndex = groupsVerbalsFunctionBeginIndex;
		this.pronomToGNBeginIndex 			 = pronomToGNBeginIndex;
		this.adverbeFunction 				 = adverbeFunction;
		this.propositionRelativeFunction 	 = propositionRelativeFunction;
		this.prepositionFunction 			 = prepositionFunction;
		this.conjonctionLocalisation 		 = conjonctionLocalisation;
		this.currentSyntaxe 				 = currentSyntaxe;
		this.currentText					 = currentText;
		this.original                        = original;

		this.conjonction                   = conjonction;
		
		this.GROUPSOFGROUP                 = GROUPSOFGROUP;
		
		this.GROUPEINFORMATIONS            = GROUPEINFORMATIONS;
		this.SAVEPATH                      = SAVEPATH;
		
		
		

		
		
		
		
		
		
		ArrayList<String> groups = new ArrayList<String>();
		int lengthSentence       = this.currentText.size();
		for (int index=0; index < lengthSentence; index++) {groups.add(Integer.toString(index));}

		recuperateGroup(groups);


		ArrayList<String> displayText = new ArrayList<String>(this.currentText);
		for (int index=0; index < this.currentText.size(); index++) {displayText.set(index, index+this.currentText.get(index));}
		System.out.println(this.currentSyntaxe + "\n" + displayText + "\n");

	
		

		LinkedHashSet<String> hashSet 		 = new LinkedHashSet<String>(groups);
        ArrayList<String> 	  groupsSentence = new ArrayList<String>(hashSet);
        Map<Integer, String>  infoGroups     = new HashMap<Integer, String>();
        ArrayList<String>     sentenceGroup  = new ArrayList<String>(hashSet);
        
        
        
        System.out.println("---------------------------------------------------------- Sentence origin");
        System.out.println(groupsSentence + "\n");


        
        
        

        //Regroup Group préposition with group nominal.
        regroupingGroupByPrepositionGn(groupsSentence);
        System.out.println(groupsSentence + "1\n");
        
        regroupingGroupByPrepositionGn(groupsSentence);
        System.out.println(groupsSentence + "4\n");

        regroupingGroupByPrepositionPreposition(groupsSentence);
        System.out.println(groupsSentence + "5\n");
       
        regroupingGroupPrepoCnjnPrepo(groupsSentence);
        System.out.println(groupsSentence + "6\n");
 
        regroupingGroupPrepoVerbeInfinitif(groupsSentence);
        System.out.println(groupsSentence + "7\n");

        regroupingGroupByVerbWithVerb(groupsSentence);
        System.out.println(groupsSentence + "8\n");
 
        regroupingGroupPrepoAdvGnomi(groupsSentence);
        System.out.println(groupsSentence + "9\n");

        regroupingGprepVerbAdv(groupsSentence);
        System.out.println(groupsSentence + "10\n");
		System.out.println("");
		System.out.println("");
		System.out.println("");
        
        
        
        
        
        ArrayList<String> saveSentenceGroups = new ArrayList<String>(groupsSentence);
        for (String element: saveSentenceGroups) { this.GROUPSOFGROUP.add(element); }


        
        
		for (int group = 0; group < groupsSentence.size(); group++) {


			String[] currentGp  = groupsSentence.get(group).split("[+]");
			String   functionGp = currentGp[0];
			int      beginGp 	= 0;
			int      endGp   	= 0;

			boolean isNotEmpty  = currentGp.length > 1;
			if (isNotEmpty) {
				beginGp 	= Integer.parseInt(currentGp[1]);
				endGp   	= Integer.parseInt(currentGp[2]);
			}
        
			//le chien, dire véritablement, le jolie chat,
   
			//save in WORKLIST
			inGroupVerbal(groupsSentence, functionGp, beginGp, endGp);
			inGroupNominal(groupsSentence, functionGp, beginGp, endGp);
			inGroupPrepositionelGn(groupsSentence, functionGp, beginGp, endGp);
			inGroupPrepositionelGv(groupsSentence, functionGp, beginGp, endGp);
			inGroupAdverbe(groupsSentence, functionGp, beginGp, endGp);
			inGroupRelative(groupsSentence, functionGp, beginGp, endGp);
			inGroupConjonction(groupsSentence, functionGp, beginGp, endGp);
			

			//System.out.println(WORKLIST);

			System.out.println("");	
		}


		
		
		
		
		System.out.println("");
		System.out.println("");
		System.out.println("");
		
		//[GNominal+0+2, GRel+3+3, GVerbal+4+5, GNominal+6+6]10

		for (int index=0; index < 3; index++) {
	        schemaSentenceGroupVerbalGroupNominal(groupsSentence);
	        //System.out.println(groupsSentence + "1\n");
	        schemaSentenceGroupNominalGroupPrepositionel(groupsSentence);
	        //System.out.println(groupsSentence + "2\n");
	        schemaSentenceGroupVerbalGroupPrepositionel(groupsSentence);
	        //System.out.println(groupsSentence + "3\n");
	        schemaSentenceGroupVerbal(groupsSentence);
	        //System.out.println(groupsSentence + "4\n");
	        schemaSentenceGroupNominal(groupsSentence);
	        //System.out.println(groupsSentence + "5\n");
	        schemaSentenceAdverbe(groupsSentence);
	        //System.out.println(groupsSentence + "6\n");
	        schemaSentencePropositionSubordonéeRelative(groupsSentence, saveSentenceGroups);
	        //System.out.println(groupsSentence + "7\n");
	        schemaSentenceCnjnc(groupsSentence);
	        //System.out.println(groupsSentence + "8\n");
			schemaSentenceCodPrep(groupsSentence);
			//System.out.println(groupsSentence + "9\n");
			schemaGprepAndPropo(groupsSentence);
			//System.out.println(groupsSentence + "10\n");
			schemaVerbPrepVerb(groupsSentence);
			//System.out.println(groupsSentence + "11\n");
			schemaGroupSujetVerbCod(groupsSentence);
			//System.out.println(groupsSentence + "12\n");
			schemaGroupSujetVerbCoi(groupsSentence);
			//System.out.println(groupsSentence + "13\n");
			schemaGroupCod(groupsSentence);
			//System.out.println(groupsSentence + "14\n");
			schemaSentenceGroupVerbalAdverb(groupsSentence);
			//System.out.println(groupsSentence + "15\n");
			
		}

		System.out.println("");
		System.out.println("");
		System.out.println("");

		System.out.println(groupsSentence);
		//for(Entry<Integer, String> el: WORKLIST.entrySet()) { System.out.println(el);}
		
		
		System.out.println("");
		System.out.println("");
		System.out.println("");

		displaySentence(groupsSentence, WORKLIST, saveSentenceGroups);
		definateSubjectPrincipal(containerSyntaxeSubject);
	}
	


	
	

	private void definateSubjectPrincipal(ArrayList<String> containerSyntaxe2) {
		System.out.println("sujet: " + containerSyntaxeSubject);
	}
		
		
	


	private void displaySentence(ArrayList<String> groupsSentence, Map<Integer, String> WORKLIST,
								 ArrayList<String> saveSentenceGroups) throws IOException {
		
		//System.out.println(this.currentText);

		for (int index=0; index < groupsSentence.size(); index++) {
			
			ArrayList<String> SentenceGroupInterest = new ArrayList<String>();


			String[] schemaIdentifiate = groupsSentence.get(index).split("[+]");
			
			boolean isNotEmpty         = schemaIdentifiate.length > 1;

			if (isNotEmpty) {

				String   shema             = schemaIdentifiate[0];
				int      begin             = Integer.parseInt(schemaIdentifiate[1]);
				int      end               = Integer.parseInt(schemaIdentifiate[2]);
				
				recuperateGroupFromSave(saveSentenceGroups, begin, end, SentenceGroupInterest);

				boolean isGCOI_gn_gv_p      = shema.equalsIgnoreCase("GCOI_gn_gv_p");
				boolean isGCOI_gv_p   		= shema.equalsIgnoreCase("GCOI_gv_p");
				boolean isGPrep_gn_p   		= shema.equalsIgnoreCase("GPrep_gn_p");
				boolean isGV           		= shema.equalsIgnoreCase("GV");
				boolean isGN          		= shema.equalsIgnoreCase("GN");
				boolean isCOD         		= shema.equalsIgnoreCase("GCOD");
				boolean isGPrep       		= shema.equalsIgnoreCase("GPrep");
				boolean isGAdv         	 	= shema.equalsIgnoreCase("GAdv");
				boolean isGRel        		= shema.equalsIgnoreCase("SRel");
				boolean isCnjnc        		= shema.equalsIgnoreCase("GCNJNC");
				boolean isGS_GV       	    = shema.equalsIgnoreCase("GS_GV");
				boolean isGCOD_GPREP  		= shema.equalsIgnoreCase("GCOD_GPREP");
				boolean GS_GV_CMPLT_via_rel = shema.equalsIgnoreCase("GS_GV_CMPLT_via_rel");
				boolean Gprep_gv            = shema.equalsIgnoreCase("Gprep_gv");
				boolean GPREP_GCOD          = shema.equalsIgnoreCase("GPREP_GCOD");
				boolean GPREP_GCOI          = shema.equalsIgnoreCase("GPREP_GCOI");
				boolean Gprep_gv_Cod        = shema.equalsIgnoreCase("Gprep_gv_Cod");
				boolean isGCOD_Grel_gv      = shema.equalsIgnoreCase("isGCOD_Grel_gv");
				boolean isGCOI              = shema.equalsIgnoreCase("GCOI");

						
				
				if      (isGCOI_gn_gv_p)      { COISentence(SentenceGroupInterest,       WORKLIST, begin); }
				else if (isGCOI_gv_p)    	  { COISentence(SentenceGroupInterest,       WORKLIST, begin); }
				else if (isCOD)    			  { CODSentence(SentenceGroupInterest,       WORKLIST, begin); }
				else if (isGPrep_gn_p)   	  { GPREPsentence(SentenceGroupInterest,     WORKLIST, begin); }
				else if (isGV)   	    	  { GVsentence(SentenceGroupInterest,        WORKLIST, begin); }
				else if (isGN)   	    	  { GNsentence(SentenceGroupInterest,        WORKLIST, begin); }
				else if (isGPrep)   	      { GPREPsentence(SentenceGroupInterest,     WORKLIST, begin); }
				else if (isGAdv)   	    	  { GADVsentence(SentenceGroupInterest,      WORKLIST, begin); }
				else if (isGRel)   			  { GRELsentence(SentenceGroupInterest,      WORKLIST, begin); }
				else if (isCnjnc)   		  { CNJNCsentence(SentenceGroupInterest,     WORKLIST, begin); }
				else if (isGS_GV)   		  { CODSentence(SentenceGroupInterest,       WORKLIST, begin); }
				else if (isGCOD_GPREP)   	  { CODSentence(SentenceGroupInterest,       WORKLIST, begin); }
				else if (GS_GV_CMPLT_via_rel) { GREL2sentence(SentenceGroupInterest,     WORKLIST, begin); }
				else if (Gprep_gv)            { GPREPsentence(SentenceGroupInterest,     WORKLIST, begin); }
				else if (GPREP_GCOD)          { GPREPsentenceVerb(SentenceGroupInterest, WORKLIST, begin); }
				else if (GPREP_GCOI)          { COISentence(SentenceGroupInterest,       WORKLIST, begin); }
				else if (Gprep_gv_Cod)        { GPREPsentence(SentenceGroupInterest,     WORKLIST, begin); }
				else if (isGCOD_Grel_gv)      { GRELCODsentence(SentenceGroupInterest,   WORKLIST, begin); }
				else if (isGCOI)    	  	  { COISentence(SentenceGroupInterest,       WORKLIST, begin); }

				
			}
		}
	}

	private void recuperateGroupFromSave(ArrayList<String> saveSentenceGroups, int begin, int end, 
			 							 ArrayList<String> sentenceGroupInterest) {

		for (int index=0; index < saveSentenceGroups.size(); index++) {

			String   current 		  = saveSentenceGroups.get(index);

			String[] groups   		  = current.split("[+]");

			boolean isNotEmpty        = groups.length > 1;

			if (isNotEmpty) {
				String   function 		  = groups[0];
				int      beginFunction    = Integer.parseInt(groups[1]);
				int      endFunction      = Integer.parseInt(groups[2]);

				boolean inRange = beginFunction >= begin && endFunction <= end;
				if (inRange) {sentenceGroupInterest.add(current);}
			}
		}
	}



	private void CNJNCsentence(ArrayList<String> sentencePartInterest, Map<Integer, String> WORKLIST, int indexPart) throws IOException {

		String increment1    = "";
		String increment2    = "";

		String groupCnjnc    = "(Conjonction)";

		for (int index=0; index < sentencePartInterest.size(); index++) {

			//GNominal+0+2
			String[] infoGroup = recuperateInfoGroups(sentencePartInterest, index);

			String   function 		  = infoGroup[0];
			int      beginFunction    = Integer.parseInt(infoGroup[1]);
			int      endFunction      = Integer.parseInt(infoGroup[2]);
			String   current          = infoGroup[3];


			Map<Integer, String> WORKLISTSORTED = new TreeMap<Integer, String>(WORKLIST);

			for(Entry<Integer, String> partSentence: WORKLISTSORTED.entrySet()) {

				int     key 		= partSentence.getKey();
				String  value       = partSentence.getValue() + "   ";
				boolean isInRange   = key >= beginFunction && key <= endFunction;
				boolean isCnjnc     = function.equalsIgnoreCase("Gcnjonc");

				if (isInRange && isCnjnc) { increment1 += value; increment2 += groupCnjnc; }
				
				String keyGroup = Integer.toString(beginFunction) + "+" + Integer.toString(endFunction);
				
				if (isInRange && isCnjnc) { GROUPEINFORMATIONS.put(keyGroup, "GCNJNC"); }

			}
		}
		System.out.println(increment1);
		System.out.println(Integer.toString(indexPart) + "-" + increment2 + "\n");
		writtingText(increment1, increment2);

	}

	
	
	private void GRELCODsentence(ArrayList<String> sentencePartInterest, Map<Integer, String> WORKLIST, int indexPart) throws IOException {

		String increment1    = "";
		String increment2    = "";

		String propoRel      = "(Proposition Relative)";
		
		String groupSujet    = "(Groupe Sujet)";
		String groupNoyeau   = "(Groupe Verbal)";
		String cmpltDirect   = "(Complément Objet directe)";
		
		String cos           = "(Complément second)";

		String pronomDirect  = "(Pronom avec fonction de Complément directe)";
		
		
		
		for (int index=0; index < sentencePartInterest.size(); index++) {
			
			//GNominal+0+2
			String[] infoGroup = recuperateInfoGroups(sentencePartInterest, index);

			String   function 		= infoGroup[0];
			int      beginFunction  = Integer.parseInt(infoGroup[1]);
			int      endFunction    = Integer.parseInt(infoGroup[2]);
			String   current        = infoGroup[3];


			Map<Integer, String> WORKLISTSORTED = new TreeMap<Integer, String>(WORKLIST);

		
			int    gvIndex = localiseVerb(WORKLISTSORTED, beginFunction);
			String isPrnm  = recuperateSyntaxe(WORKLISTSORTED, beginFunction, endFunction, "Prn");

			//For have a cod pronom no contains je tu il ...
			boolean isPrnmInterest      = thisListContainsWordTwoCase(pronomPersonnelCOD, isPrnm.toLowerCase());

			
			int verb   = localisationOfIndex(WORKLISTSORTED, beginFunction, endFunction, "vrb");
			int pronom = localisationOfIndex(WORKLISTSORTED, beginFunction, endFunction, "Prn");
			int nc     = localisationOfIndex(WORKLISTSORTED, beginFunction, endFunction, "Nm");
			if (nc == -1) { nc = localisationOfIndex(WORKLISTSORTED, beginFunction, endFunction, "(Nc)"); }
			
			
			for(Entry<Integer, String> partSentence: WORKLISTSORTED.entrySet()) {

				int     key 		 = partSentence.getKey();
				String  value        = partSentence.getValue();
				boolean isInRange    = key >= beginFunction && key <= endFunction;

				boolean isGN         = function.equalsIgnoreCase("GNominal");
				boolean isGV         = function.equalsIgnoreCase("GVerbal");
				boolean isPrep       = function.equalsIgnoreCase("GPrep");
				boolean isGrel       = function.equalsIgnoreCase("GRel");
				
				boolean GnBeforeGV = false;
				if (isGN) { GnBeforeGV = localiseSchema(sentencePartInterest, beginFunction, endFunction, current, "GVerbal"); }
				boolean GnAfterGV   = !GnBeforeGV; //For localise the complement of the proposition.
				boolean prnmBefore  = pronom < verb && pronom != -1 && verb != -1 && isPrnmInterest;
				
				boolean alreadyGS    = increment2.contains("(Groupe Sujet)");
				boolean alreadyGV    = increment2.contains("(Groupe Verbal)");
				boolean alreadyCmplt = increment2.contains("(Complément Objet Indirecte)");
				boolean prnmDirect   = increment2.contains("(Pronom avec fonction de Complément directe)");
				
				if      (isInRange && isGN)   			   { increment1 += value; }
				else if (isInRange && isGV)   			   { increment1 += value; }
				else if (isInRange && isPrep && GnAfterGV) { increment1 += value; }
				else if (isInRange && isPrep && GnAfterGV) { increment1 += value; }
				else if (isInRange && isGrel)              { increment1 += value; }
				
				
				if      (isInRange && isGN && !alreadyGS  && !GnAfterGV)  	{ increment2 += groupSujet;    }
				else if (isInRange && isGN && !prnmDirect && GnAfterGV)  	{ increment2 += cmpltDirect;    }
				else if (isInRange && isGV && !alreadyGV) 				    { increment2 += groupNoyeau;   }
				else if (isInRange && isPrep && GnAfterGV && !alreadyCmplt) { increment2 += cmpltDirect; }
				else if (isInRange && isPrep && GnAfterGV &&  alreadyCmplt) { increment2 += cos;           }
				else if (isInRange && isGrel)                               { increment2 += propoRel;      }

				
				if (isInRange && prnmBefore && !prnmDirect) { 
					increment2 = replacing(increment2, groupNoyeau, pronomDirect);
					increment2 += groupNoyeau;
				}
				
				String keyGroup = Integer.toString(beginFunction) + "+" + Integer.toString(endFunction);
				
				if (isInRange && isGN)   			  { GROUPEINFORMATIONS.put(keyGroup, "GSUJET" ); }
				if (isInRange && isGV)   			  { GROUPEINFORMATIONS.put(keyGroup, "GVERBAL"); }
				if (isInRange && isPrep && GnAfterGV) { GROUPEINFORMATIONS.put(keyGroup, "GCMPLT" ); }

			}
		}
		System.out.println(increment1);
		System.out.println(Integer.toString(indexPart) + "-" + increment2 + "\n");
		writtingText(increment1, increment2);
	}

	
	private void COISentence(ArrayList<String> sentencePartInterest, Map<Integer, String> WORKLIST, int indexPart) throws IOException {

		String increment1    = "";
		String increment2    = "";

		String groupSujet    = "(Groupe Sujet)";
		String groupNoyeau   = "(Groupe Verbal)";
		String cmpltIndirect = "(Complément Objet Indirecte)";
		String propoRel      = "(Proposition Relative)";
		String cos           = "(Complément second)";
		String pronomDirect  = "(Pronom avec fonction de Complément directe)";

		
		
		for (int index=0; index < sentencePartInterest.size(); index++) {
			
			//GNominal+0+2
			String[] infoGroup = recuperateInfoGroups(sentencePartInterest, index);

			String   function 		= infoGroup[0];
			int      beginFunction  = Integer.parseInt(infoGroup[1]);
			int      endFunction    = Integer.parseInt(infoGroup[2]);
			String   current        = infoGroup[3];


			Map<Integer, String> WORKLISTSORTED = new TreeMap<Integer, String>(WORKLIST);

		
			int    gvIndex = localiseVerb(WORKLISTSORTED, beginFunction);
			String isPrnm  = recuperateSyntaxe(WORKLISTSORTED, beginFunction, endFunction, "Prn");

			//For have a cod pronom no contains je tu il ...
			boolean isPrnmInterest      = thisListContainsWordTwoCase(pronomPersonnelCOD, isPrnm.toLowerCase());


			int verb   = localisationOfIndex(WORKLISTSORTED, beginFunction, endFunction, "vrb");
			int pronom = localisationOfIndex(WORKLISTSORTED, beginFunction, endFunction, "Prn");
			int nc     = localisationOfIndex(WORKLISTSORTED, beginFunction, endFunction, "Nm");
			if (nc == -1) { nc = localisationOfIndex(WORKLISTSORTED, beginFunction, endFunction, "(Nc)"); }
			
			
			for(Entry<Integer, String> partSentence: WORKLISTSORTED.entrySet()) {

				int     key 		 = partSentence.getKey();
				String  value        = partSentence.getValue();
				boolean isInRange    = key >= beginFunction && key <= endFunction;
	
				boolean isGN         = function.equalsIgnoreCase("GNominal");
				boolean isGV         = function.equalsIgnoreCase("GVerbal");
				boolean isPrep       = function.equalsIgnoreCase("GPrep") || function.equalsIgnoreCase("GPrepVerbal");
				boolean isGrel       = function.equalsIgnoreCase("GRel");
				boolean isAdv        = function.equalsIgnoreCase("GAdverb");
				
				boolean GnBeforeGV = false;
				if (isGN) { GnBeforeGV = localiseSchema(sentencePartInterest, beginFunction, endFunction, current, "GVerbal"); }
				boolean GnAfterGV   = !GnBeforeGV; //For localise the complement of the proposition.
				boolean prnmBefore  = pronom < verb && pronom != -1 && verb != -1 && isPrnmInterest;

				boolean alreadyGS    = increment2.contains("(Groupe Sujet)");
				boolean alreadyGV    = increment2.contains("(Groupe Verbal)");
				boolean alreadyCmplt = increment2.contains("(Complément Objet Indirecte)");
				boolean prnmDirect   = increment2.contains("(Pronom avec fonction de Complément directe)");
				
				if      (isInRange && isGN)   			   { increment1 += value; }
				else if (isInRange && isGV)   			   { increment1 += value; }
				else if (isInRange && isPrep && GnAfterGV) { increment1 += value; }
				else if (isInRange && isPrep && GnAfterGV) { increment1 += value; }
				else if (isInRange && isGrel)              { increment1 += value; }
				else if (isInRange && isAdv)               { increment1 += value; }
				
				if      (isInRange && (isGN || isPrep) && !alreadyGS && !GnAfterGV)  { increment2 += groupSujet;    }
				else if (isInRange && isGV && !alreadyGV) 				    		 { increment2 += groupNoyeau;   }
				else if (isInRange && isPrep && GnAfterGV && !alreadyCmplt) 		 { increment2 += cmpltIndirect; }
				else if (isInRange && isPrep && GnAfterGV &&  alreadyCmplt) 		 { increment2 += cos;           }
				else if (isInRange && isGrel)                               		 { increment2 += propoRel;      }
				
	
				if (isInRange && prnmBefore && !prnmDirect) { 
					increment2 = replacing(increment2, groupNoyeau, pronomDirect);
					increment2 += groupNoyeau;
				}
				
				
				boolean isEmpty = containerSyntaxeSubject.size() == 0;

				if 		(isInRange && (isGN || isPrep) && !alreadyGS && !GnAfterGV) { containerSyntaxeSubject.add(increment1); }
				else if (isInRange && isGV && !alreadyGV && isEmpty)        		{ containerSyntaxeSubject.add(increment1); }
				

				
				String keyGroup = Integer.toString(beginFunction) + "+" + Integer.toString(endFunction);
				
				if (isInRange && isGN )   			  { GROUPEINFORMATIONS.put(keyGroup, "GSUJET" ); }
				if (isInRange && isGV)   			  { GROUPEINFORMATIONS.put(keyGroup, "GVERBAL"); }
				if (isInRange && isPrep && GnAfterGV) { GROUPEINFORMATIONS.put(keyGroup, "GCMPLT" ); }

				
				
			}
		}
		System.out.println(increment1);
		System.out.println(Integer.toString(indexPart) + "-" + increment2 + "\n");
		writtingText(increment1, increment2);
	}

	
	
	

	//Are all schemas in function of the sentences schemas.
	String groupSujet        = "(Groupe Sujet)";
	String groupNoyeau       = "(Groupe Verbal)";
	String cmpltdirect       = "(Complément directe)";
	String pronomDirect      = "(Pronom avec fonction de Complément directe)";
	String cmpltSujetVerbal  = "(Préposition du Groupe Sujet Verbal)";
	String cmpltSujetNominal = "(Préposition du Groupe Sujet Nominal)";
	String Prep              = "(Groupe Prépositionel Nominal)";
	String COS               = "(Complément Objet Second)";
	String propoRel          = "(Proposition Relative)";

	private void CODSentence(ArrayList<String> sentencePartInterest, Map<Integer, String> WORKLIST, int indexPart) throws IOException {

		String  increment1 = "";  //In increment1 we adding syntaxe and words.
		String  increment2 = "";  //In increments2 we adding schemas:
		
		int     firstGv    = 0;
		
		
		for (int index=0; index < sentencePartInterest.size(); index++) {

			String[] infoGroup     = recuperateInfoGroups(sentencePartInterest, index);
			String   function      = infoGroup[0];
			String   current       = infoGroup[3];
			int      beginFunction = Integer.parseInt(infoGroup[1]);
			int      endFunction   = Integer.parseInt(infoGroup[2]);
			

			Map<Integer, String> WORKLISTSORTED = new TreeMap<Integer, String>(WORKLIST);

			//Localise index of the syntaxe for do some conditions.
			int[] indexs     = indexsSyntaxeOfCod(WORKLISTSORTED, beginFunction, endFunction);
			int   GprepIndex = indexs[0]; int verb = indexs[1]; int pronom = indexs[2];
			int   nc         = indexs[3];
			
			
			String  isPrnm = recuperateSyntaxe(WORKLISTSORTED, beginFunction, endFunction, "Prn");

			//For have a cod pronom no contains je tu il ...
			boolean isPrnmInterest      = thisListContainsWordTwoCase(pronomPersonnelCOD, isPrnm.toLowerCase());

		
			for(Entry<Integer, String> partSentence: WORKLISTSORTED.entrySet()) {

				int     key        = partSentence.getKey();
				String  value      = partSentence.getValue();
				boolean isInRange  = key >= beginFunction && key <= endFunction;

				boolean isGV       = function.equalsIgnoreCase("GVerbal");
				boolean isGN       = function.equalsIgnoreCase("GNominal");
				boolean isPrep     = function.equalsIgnoreCase("GPrep");
				boolean isADV      = function.equalsIgnoreCase("GAdverb");
				boolean isGrel     = function.equalsIgnoreCase("GRel");
				
				boolean isGprepIn  = GprepIndex >= beginFunction && GprepIndex <= endFunction;

				
				boolean nextIsGv   = localiseSubjetByNextSchema(sentencePartInterest, index);
				boolean GnBeforeGV = false;

				if (isGN) { GnBeforeGV = localiseSchema(sentencePartInterest, beginFunction, endFunction, current, "GVerbal"); }
				boolean GnAfterGV   = !GnBeforeGV; //For localise the complement of the proposition.

				
				boolean GprepBefore = false;
				if (isPrep) { GprepBefore = localiseSchema(sentencePartInterest, beginFunction, endFunction, current, "GVerbal"); }
			
				boolean prnmBefore  = pronom < verb && pronom != -1 && verb != -1 && isPrnmInterest;

				boolean isPrepV = false;
				if (isPrep && isInRange) { isPrepV = value.toLowerCase().contains("vrb"); }
				boolean isPrepN = !isPrepV;
				

				boolean[] incremented = hasSchemaIncremented(increment2);
				boolean   sujetAlready  = incremented[0]; boolean NoyoAlready = incremented[1]; 
			    boolean   DirectAlady   = incremented[2]; boolean prepSub1	  = incremented[3];
			    boolean   prepSub2	    = incremented[4]; boolean cmpltPrep   = incremented[5];
			    boolean	  prnmDirect    = incremented[6]; boolean prepAlr     = incremented[7];

				//Syntaxe display
				if 		(isInRange && isGN   && GnBeforeGV)   			   { increment1 += value; }
				else if (isInRange && isGV)    			    			   { increment1 += value; }
				else if (isInRange && isGN   && GnAfterGV)    			   { increment1 += value; }
				else if (isInRange && isPrep && GnAfterGV)  			   { increment1 += value; }
				else if (isInRange && isADV)                               { increment1 += value; }
				else if (isInRange && isGrel) 							   { increment1 += value; }
				
				
				boolean noFollow   = firstGv - index < 2;
				
				//Schema display
				if 		(isInRange && isGV     && (!NoyoAlready || noFollow) && !isGprepIn)   { increment2 += groupNoyeau; firstGv = index;}
				
				else if (isInRange && isGV     && !cmpltPrep   &&  isGprepIn && !DirectAlady) { increment2 += Prep; }
				else if (isInRange && isGV     && !cmpltPrep   &&  isGprepIn &&  DirectAlady) { increment2 += COS; }
				
				else if (isInRange && isGN     && GnBeforeGV   && !sujetAlready)              { increment2 += groupSujet; }
				else if (isInRange && isGN     && GnAfterGV    && !DirectAlady)               { increment2 += cmpltdirect; }
				else if (isInRange && isGN     && nextIsGv     && !sujetAlready)              { increment2 += groupSujet; }
				
				else if (isInRange && isPrepN  && GprepBefore  && !prepSub1)                  { increment2 += cmpltSujetNominal; }
				else if (isInRange && isPrepV  && GprepBefore  && !prepSub2)                  { increment2 += cmpltSujetVerbal; }
				
				else if (isInRange && isPrep   && !GprepBefore && !prepAlr && !DirectAlady)   { increment2 += Prep; }
				else if (isInRange && isPrep   && !GprepBefore && !prepAlr &&  DirectAlady)   { increment2 += COS; }
				else if (isInRange && isGN     && GnAfterGV    && !DirectAlady)     	      { increment2 += cmpltdirect; }
				else if (isInRange && isGrel)     	      									  { increment2 += propoRel; }
				
				
				if (isInRange && prnmBefore && !prnmDirect) { increment2 = replacing(increment2, cmpltdirect, pronomDirect); }

				
				if      (isInRange && isGN && GnBeforeGV   && !sujetAlready)  { containerSyntaxeSubject.add(increment1); }
				else if (isInRange && isGN && nextIsGv     && !sujetAlready)  { containerSyntaxeSubject.add(increment1); }
				else if (isInRange && isGV) 								  { containerSyntaxeSubject.add(increment1); }
				
				
				//Send schema to the last part (localise the subject in sentence with his action).
				String keyGroup = Integer.toString(beginFunction) + "+" + Integer.toString(endFunction);
	
				if      (isInRange && isGV)                                  { GROUPEINFORMATIONS.put(keyGroup, "GVERBAL");      }
				else if (isInRange && isGN && GnAfterGV)                     { GROUPEINFORMATIONS.put(keyGroup, "GCMPLT");       }
				else if (isInRange && isGN && GnBeforeGV)                    { GROUPEINFORMATIONS.put(keyGroup, "GSUJET");       }
				else if (isInRange && isPrep && GnBeforeGV && !sujetAlready) { GROUPEINFORMATIONS.put(keyGroup, "GSUJET");       }
				else if (isInRange && isPrep && GnBeforeGV && sujetAlready)  { GROUPEINFORMATIONS.put(keyGroup, "GPREPOFSUJET"); }
				else if (isInRange && isGN && nextIsGv && sujetAlready)      { GROUPEINFORMATIONS.put(keyGroup, "GSUJET");       }



	
			}
		}
		System.out.println(increment1);
		System.out.println(Integer.toString(indexPart) + "-" + increment2 + "\n");
		writtingText(increment1, increment2);
	}
	
	
















	private String replacing(String increment2, String cmpltdirect, String pronomDirect) {
		
		int begin  = increment2.indexOf(cmpltdirect);
		int length = cmpltdirect.length();
		String[] split = increment2.split("");
		
		boolean LessOne = begin == -1;

		if (LessOne) {
			begin  = increment2.indexOf("(Groupe Verbal)");
			length = "(Groupe Verbal)".length();
			split = increment2.split("");
		}
		

		
		for (int index=begin; index < begin + length; index++) {
			boolean noFound = begin == -1;
			if (!noFound) { split[index] = ""; }
			if (noFound)  { break; }
		}
		
		String increment = "";
		for (int index=0; index < split.length; index++) {
			if (index==begin) { increment += pronomDirect; }
			else { increment += split[index]; }
		}
		
		if (LessOne) { increment += " " + "(Groupe Verbal)"; }

		return increment;

	}





	private void GVsentence(ArrayList<String> sentencePartInterest, Map<Integer, String> WORKLIST, int indexPart) throws IOException {

		
		String increment1   = "";
		String increment2   = "";

		String groupVerbal  = "(Groupe Verbal)";
		String groupAdverb  = "(Groupe Adverbial)";
		String COD          = "(Pronom avec fonction Complement objet directe)";



		for (int index=0; index < sentencePartInterest.size(); index++) {
			
			String   current          = sentencePartInterest.get(index);
			String[] groups           = current.split("[+]");
			String   function 		  = groups[0];
			int      beginFunction    = Integer.parseInt(groups[1]);
			int      endFunction      = Integer.parseInt(groups[2]);
			

			Map<Integer, String> WORKLISTSORTED = new TreeMap<Integer, String>(WORKLIST);


			int    verb   = localisationOfIndex(WORKLISTSORTED, beginFunction, endFunction, "(vrb)");
			int    pronom = localisationOfIndex(WORKLISTSORTED, beginFunction, endFunction, "(Prn)");
			String isPrnm = recuperateSyntaxe(WORKLISTSORTED,   beginFunction, endFunction, "Prn");

			//For have a cod pronom no contains je tu il ...
			boolean isPrnmInterest      = thisListContainsWordTwoCase(pronomPersonnelCOD, isPrnm.toLowerCase());


			
			for(Entry<Integer, String> partSentence: WORKLISTSORTED.entrySet()) {

				String  value        = partSentence.getValue() + " ";
				int     key 		 = partSentence.getKey();

				boolean isInRange    = key >= beginFunction && key <= endFunction;
				boolean isVerbal     = function.equalsIgnoreCase("GVerbal");
				boolean isAdverb     = function.equalsIgnoreCase("GAdverb");
				
				
				boolean isAlreadyInc    = increment2.contains("(Groupe Verbal)");
				boolean isAlreadyIncAdv = increment2.contains("(Groupe Adverbial)");
				
				boolean prnmBefore  = pronom < verb && pronom != -1 && verb != -1 && isPrnmInterest;
				
				boolean alreadyPrnmCod    = increment2.contains("(Pronom avec fonction Complement objet directe)");
				
				
				if (isInRange && isVerbal)      { increment1 += value; }
				if (isInRange && isAdverb)      { increment1 += value; }
				
				if (isInRange && isVerbal &&   !isAlreadyInc)     { increment2 += groupVerbal; }
				if (isInRange && prnmBefore && !alreadyPrnmCod)   { increment2 = replacing(increment2, groupVerbal, COD); }
				if (isInRange && prnmBefore && !alreadyPrnmCod)   { increment2 += " " + groupVerbal; }
				if (isInRange && isAdverb &&   !isAlreadyIncAdv)  { increment2 += " " + groupAdverb; }
				
				

				
				
				String keyGroup = Integer.toString(beginFunction) + "+" + Integer.toString(endFunction);
				if (isInRange && isVerbal)    { GROUPEINFORMATIONS.put(keyGroup, "GVERBAL"); }
				
				
			}
		}
		System.out.println(increment1);
		System.out.println(Integer.toString(indexPart) + "-" + increment2 + "\n");
		writtingText(increment1, increment2);
	}




	private String recuperateSyntaxe(Map<Integer, String> wORKLISTSORTED, int beginFunction, int endFunction, String string) {
		
		string = string.toLowerCase();
		String prnm = "";

		for(Entry<Integer, String> groups: wORKLISTSORTED.entrySet()) {
			int    key   = groups.getKey();
			String value = groups.getValue().toLowerCase();

			boolean inRange    = key >= beginFunction && key <= endFunction;
			boolean isMatching = value.contains(string) || string.contains(value);

			if (isMatching && inRange) { 
				try{ prnm = value.substring(value.lastIndexOf(string), value.lastIndexOf(string) + 20); } catch (Exception e) {
					try{ prnm = value.substring(value.lastIndexOf(string), value.lastIndexOf(string) + 9); } catch (Exception e3) {
						try{ prnm = value.substring(value.lastIndexOf(string), value.lastIndexOf(string) + 5); } catch (Exception e1) {
							try{ prnm = value.substring(value.lastIndexOf(string), value.lastIndexOf(string) + 3); } catch (Exception e2) {}
						}
					}
				}
			}
		}
		return prnm;
	}




	private int localisationOfIndex(Map<Integer, String> wORKLISTSORTED, int beginFunction, int endFunction, String string) {
		
		string = string.toLowerCase();

		for(Entry<Integer, String> groups: wORKLISTSORTED.entrySet()) {
			int    key   = groups.getKey();
			String value = groups.getValue().toLowerCase();
			
			boolean inRange    = key >= beginFunction && key <= endFunction;
			boolean isMatching = value.contains(string) || string.contains(value);

			if (inRange && isMatching) { return value.indexOf(string); }
			
		}
		return -1;
	}














	private void GPREPsentenceVerb(ArrayList<String> sentencePartInterest, Map<Integer, String> WORKLIST, int indexPart)  throws IOException {

		String increment1    = "";
		String increment2    = "";

		String groupPrep     = "(Groupe Prépositionel Verbal)";
		String groupSujet    = "(Groupe Sujet)";
		String groupVerbal   = "(Groupe verbal)";
		String groupCod      = "(Compément du nom directe)";


		for (int index=0; index < sentencePartInterest.size(); index++) {
			
			String   current          = sentencePartInterest.get(index);
			String[] groups           = current.split("[+]");
			String   function 		  = groups[0];
			int      beginFunction    = Integer.parseInt(groups[1]);
			int      endFunction      = Integer.parseInt(groups[2]);


			Map<Integer, String> WORKLISTSORTED = new TreeMap<Integer, String>(WORKLIST);

			
			int gvIndex = localiseVerbWithoutIndex(WORKLISTSORTED);


			for(Entry<Integer, String> partSentence: WORKLISTSORTED.entrySet()) {

				int     key 		= partSentence.getKey();
				String  value       = partSentence.getValue();
				boolean isInRange   = key >= beginFunction && key <= endFunction;

				boolean isGN        = function.equalsIgnoreCase("GNominal");
				boolean GPrepVerbal = function.equalsIgnoreCase("GPrepVerbal");
				boolean isGPrep     = function.equalsIgnoreCase("GPrep");
				boolean isGVerbal   = function.equalsIgnoreCase("Gverbal");

	
				boolean GnBeforeGv  = key < gvIndex;
				boolean GnAfterGv   = key > gvIndex;

				boolean isAlreadyIn = increment2.contains("(Groupe Prépositionel Verbal)");
				boolean alreadySj   = increment2.contains("(Groupe Sujet)");
				boolean alreadyVrb  = increment2.contains("(Groupe verbal)");


				if 		(isInRange && isGPrep && !isAlreadyIn)           { increment1 += value; }
				else if (isInRange && isGN && GnAfterGv)   				 { increment1 += value; }
				else if (isInRange && isGN && GnBeforeGv)   			 { increment1 += value; }
				else if (isInRange && isGVerbal && !alreadyVrb)          { increment1 += value; }
				
				
				if 		(isInRange && isGPrep && !isAlreadyIn)           { increment2 += groupPrep; }
				else if (isInRange && isGN && GnAfterGv)   				 { increment2 += groupCod; }
				else if (isInRange && isGN && GnBeforeGv)   			 { increment2 += groupSujet; }
				else if (isInRange && isGVerbal && !alreadyVrb)          { increment2 += groupVerbal; }
				
				
				String  keyGroup   = Integer.toString(beginFunction) + "+" + Integer.toString(endFunction);

				boolean isBeforeGv = (endFunction + 1) == gvIndex;
	

				if      (isGPrep)                            {    }
				else if (isGN && isInRange && GnBeforeGv)    { GROUPEINFORMATIONS.put(keyGroup, "GSUJET");   }
				else if (isInRange && isGN)      			 { GROUPEINFORMATIONS.put(keyGroup, "GNOMINAL"); }
				else if (isInRange && isGVerbal) 	         { GROUPEINFORMATIONS.put(keyGroup, "GVERBAL");  }
				
			}
		}
		System.out.println(increment1);
		System.out.println(Integer.toString(indexPart) + "-" + increment2 + "\n");
		writtingText(increment1, increment2);
	}
	
	

	private void GPREPsentence(ArrayList<String> sentencePartInterest, Map<Integer, String> WORKLIST, int indexPart) throws IOException  {

		String increment1    = "";
		String increment2    = "";

		String groupNominal  = "(Groupe Nominal)";
		String Préposition   = "(Groupe Prépositionel Nominal)";
		String PrépoOfPrepo  = "(Préposition + (Groupe Nominal + Prepo)";
		String groupVerbal   = "(Groupe Prépositionel Verbal)";
		String complementDir = "(Groupe Complement Directe)";
		
		for (int index=0; index < sentencePartInterest.size(); index++) {
			
			String   current          = sentencePartInterest.get(index);
			String[] groups           = current.split("[+]");
			String   function 		  = groups[0];
			int      beginFunction    = Integer.parseInt(groups[1]);
			int      endFunction      = Integer.parseInt(groups[2]);


			Map<Integer, String> WORKLISTSORTED = new TreeMap<Integer, String>(WORKLIST);


			int gvIndex = localiseVerb(WORKLISTSORTED, beginFunction);
			int gvGprepWithVerb = localise(WORKLISTSORTED, "Vrb inf", beginFunction);

			for(Entry<Integer, String> partSentence: WORKLISTSORTED.entrySet()) {

				int     key 		= partSentence.getKey();
				String  value       = partSentence.getValue();
				boolean isInRange   = key >= beginFunction && key <= endFunction;

				boolean isGN        = function.equalsIgnoreCase("GNominal");
				boolean GPrepVerbal = function.equalsIgnoreCase("GPrepVerbal");
				boolean isGPrep     = function.equalsIgnoreCase("GPrep");

				
				boolean containsVrb = partSentence.getValue().contains("(Vrb inf)");
				
				boolean containsNm  = partSentence.getValue().contains("(Nc)");
				boolean GnBeforeGv  = key < gvIndex;
				boolean isAlreadyIn = increment2.contains("(Groupe Nominal)");

				boolean alreadyCntn = increment2.contains("Préposition + Groupe Nominal)");
				boolean alreadyVrb  = increment2.contains("(Groupe Prépositionel Verbal)");
				

				if 		(isInRange  && isGN && !isAlreadyIn)    { increment1 += value; }
				else if (isInRange  && isGPrep)                 { increment1 += value; }
				else if (isInRange  && GPrepVerbal)             { increment1 += value; }

				
				if      (isInRange  && isGN && !isAlreadyIn && alreadyVrb)     { increment2 += complementDir; }
				else if (isInRange  && isGN && !isAlreadyIn && !alreadyVrb)    { increment2 += groupNominal;  }
				else if (isInRange  && isGPrep && !alreadyCntn) 			   { increment2 += Préposition;   }
				else if (isInRange  && isGPrep &&  alreadyCntn) 			   { increment2  = PrépoOfPrepo;  }
				else if (isInRange  && GPrepVerbal)             			   { increment2 += groupVerbal;   }

				
				
				
				if (isInRange  && isGN && !isAlreadyIn && !alreadyVrb) { containerSyntaxeSubject.add(increment1); }
				
				
				String  keyGroup   = Integer.toString(beginFunction) + "+" + Integer.toString(endFunction);

				boolean isBeforeGv = (endFunction + 1) == gvIndex;
				boolean isBeforeGrepVerbInf = (endFunction + 1) == gvGprepWithVerb;
				
				boolean isGsPrep   = isInRange && isGPrep && !containsVrb && containsNm && GnBeforeGv && isBeforeGv;
				

				if      (isGsPrep)                             	   { GROUPEINFORMATIONS.put(keyGroup, "GSUJET");   }
				else if (isGN && isInRange && isBeforeGrepVerbInf) { GROUPEINFORMATIONS.put(keyGroup, "GSUJET");   }
				else if (isInRange && isGN)    				  	   { GROUPEINFORMATIONS.put(keyGroup, "GNOMINAL"); }
				else if (isInRange && isGPrep && !containsVrb) 	   { GROUPEINFORMATIONS.put(keyGroup, "GPREP");    }
				else if (isInRange && isGPrep &&  containsVrb) 	   { GROUPEINFORMATIONS.put(keyGroup, "GVERBAL");  }
				else if (isInRange && GPrepVerbal) 	   			   { GROUPEINFORMATIONS.put(keyGroup, "GVERBAL");  }
				
			}
		}
		System.out.println(increment1);
		System.out.println(Integer.toString(indexPart) + "-" + increment2 + "\n");
		writtingText(increment1, increment2);
	}
	
	
	
	
	private void GADVsentence(ArrayList<String> sentencePartInterest, Map<Integer, String> WORKLIST, int indexPart)  throws IOException {

		String increment1     = "";
		String increment2     = "";

		String groupAdverbial = "(Groupe Adverbial)";


		for (int index=0; index < sentencePartInterest.size(); index++) {
			
			String   current          = sentencePartInterest.get(index);
			String[] groups           = current.split("[+]");
			String   function 		  = groups[0];
			int      beginFunction    = Integer.parseInt(groups[1]);
			int      endFunction      = Integer.parseInt(groups[2]);
			
			
			Map<Integer, String> WORKLISTSORTED = new TreeMap<Integer, String>(WORKLIST);

			for(Entry<Integer, String> partSentence: WORKLISTSORTED.entrySet()) {

				int     key 		= partSentence.getKey();
				String  value       = partSentence.getValue() + "   ";
				boolean isInRange   = key >= beginFunction && key <= endFunction;
				boolean isGAdverb   = function.equalsIgnoreCase("GAdverb");

				
				if (isInRange && isGAdverb)    { increment1 += value; increment2 += groupAdverbial; }
				
				String keyGroup = Integer.toString(beginFunction) + "+" + Integer.toString(endFunction);
				
				if (isInRange && isGAdverb)    { GROUPEINFORMATIONS.put(keyGroup, "GADV"); }

			}
		}
		System.out.println(increment1);
		System.out.println(Integer.toString(indexPart) + "-" + increment2 + "\n");
		writtingText(increment1, increment2);
	}
	
	
	

	
	
	
	private void GRELsentence(ArrayList<String> sentencePartInterest, Map<Integer, String> WORKLIST, int indexPart) throws IOException  {

		String increment1   = "";
		String increment2   = "";

		String groupSubRel  = "(Préposition subordonée relative)";
		String groupCmplt1  = "(complément de l'antécédent Nominal)";
		String groupCmplt2  = "(complément de l'antécédent Verbal)";
		String groupSujet   = "(Groupe Sujet)";
		String groupVerbal  = "(Groupe groupVerbal)";
		String groupCmplt   = "(<- complément de l'antécédent)";
		
		for (int index=0; index < sentencePartInterest.size(); index++) {
			
			String   current       = sentencePartInterest.get(index);
			String[] groups        = current.split("[+]");
			String   function 	   = groups[0];
			int      beginFunction = Integer.parseInt(groups[1]);
			int      endFunction   = Integer.parseInt(groups[2]);


			Map<Integer, String> WORKLISTSORTED = new TreeMap<Integer, String>(WORKLIST);


			for(Entry<Integer, String> partSentence: WORKLISTSORTED.entrySet()) {

				int     key 		= partSentence.getKey();
				String  value       = partSentence.getValue();
				boolean isInRange   = key >= beginFunction && key <= endFunction;
				
				boolean isGRel      = function.equalsIgnoreCase("GRel");
				boolean isGVerbal   = function.equalsIgnoreCase("GVerbal");
				boolean isGNominal  = function.equalsIgnoreCase("GNominal");

				boolean Already1    = increment2.contains("(complément de l'antécédent Nominal)");
				boolean Already2    = increment2.contains("(complément de l'antécédent Verbal)");
				
				boolean inc1Already = increment1.equalsIgnoreCase(value);
				
				if      (isInRange && isGRel)                             { increment1 += value; }
				else if (isInRange && isGNominal && !inc1Already)         { increment1 += value; }
				else if (isInRange && isGVerbal  && !inc1Already)         { increment1 += value; }

				if      (isInRange && isGRel)                             { increment2 += groupSubRel; }
				else if (isInRange && isGNominal && !Already1)            { increment2 += groupCmplt1;  }
				else if (isInRange && isGVerbal  && !Already2)            { increment2 += groupCmplt2;  }

				
				
				Already1 = increment2.contains("(complément de l'antécédent Nominal)");
				Already2 = increment2.contains("(complément de l'antécédent Verbal)");
				if (Already1 && Already2) {increment2 = replacing(increment2, groupCmplt1, groupSujet) + groupCmplt;}
				if (Already1 && Already2) {increment2 = replacing(increment2, groupCmplt2, groupVerbal);}
				
				
				String keyGroup = Integer.toString(beginFunction) + "+" + Integer.toString(endFunction);
				if (isInRange && isGRel)       { GROUPEINFORMATIONS.put(keyGroup, "GREL");  }
				if (isInRange && isGNominal)   {   }
				if (isInRange && isGVerbal)    { GROUPEINFORMATIONS.put(keyGroup, "GVERBAL"); }
				
				
			}
		}
		System.out.println(increment1);
		System.out.println(Integer.toString(indexPart) + "-" + increment2 + "\n");
		writtingText(increment1, increment2);
	}
	
	
	
	
	private void GREL2sentence(ArrayList<String> sentencePartInterest, Map<Integer, String> WORKLIST, int indexPart)  throws IOException {

		String increment1   = "";
		String increment2   = "";

		String groupSujet   = "(Groupe sujet)";
		String groupVerb    = "(Groupe verbal)";
		String groupCmplt   = "(complément directe)";
		String groupPsr     = "(Psr)";

		boolean saveCmpltBeforeSubject = false;
		String  GroupVerbalCmpltBeforeSub = "";
		
		for (int index=0; index < sentencePartInterest.size(); index++) {
			
			String   current          = sentencePartInterest.get(index);
			String[] groups           = current.split("[+]");
			String   function 		  = groups[0];
			int      beginFunction    = Integer.parseInt(groups[1]);
			int      endFunction      = Integer.parseInt(groups[2]);

			
			Map<Integer, String> WORKLISTSORTED = new TreeMap<Integer, String>(WORKLIST);


			
			int indexPsr = localisePsr(WORKLISTSORTED);
			
			for(Entry<Integer, String> partSentence: WORKLISTSORTED.entrySet()) {

				int     key 		  = partSentence.getKey();
				String  value         = partSentence.getValue();
				boolean isInRange     = key >= beginFunction && key <= endFunction;

				boolean isGRel        = function.equalsIgnoreCase("GRel");
				boolean isGVerbal     = function.equalsIgnoreCase("GVerbal");
				boolean isGNominal    = function.equalsIgnoreCase("GNominal");

				boolean beforePsr     = key < indexPsr;
				boolean afterPsr      = key > indexPsr;
				
				
				if (isInRange && isGNominal && beforePsr)  { increment1 += value; increment2 += groupCmplt; }
				if (isInRange && isGNominal && afterPsr)   { increment1 += value; increment2 += groupSujet; }
				if (isInRange && isGRel)  				   { increment1 += value; increment2 += groupPsr; }
				if (isInRange && isGVerbal)    			   { increment1 += value; increment2 += groupVerb;  }

				
				String keyGroup = Integer.toString(beginFunction) + "+" + Integer.toString(endFunction);

				if (isInRange && isGNominal && beforePsr)  { GROUPEINFORMATIONS.put(keyGroup, "GCMPLT");  }
				if (isInRange && isGNominal && afterPsr)   { GROUPEINFORMATIONS.put(keyGroup, "GSUJET");  }
				if (isInRange && isGVerbal)    			   { GROUPEINFORMATIONS.put(keyGroup, "GVERBAL"); }
				
			}
		}
		

		System.out.println(increment1);
		System.out.println(Integer.toString(indexPart) + "-" + increment2 + "\n");
		writtingText(increment1, increment2);
	}
	
	
	
	
	
	

	
	
	
	
	
	private void GNsentence(ArrayList<String> sentencePartInterest, Map<Integer, String> WORKLIST, int indexPart) throws IOException {

		String increment1   = "";
		String increment2   = "";
		
		String groupNominal = "Groupe Nominal";

		for (int index=0; index < sentencePartInterest.size(); index++) {

			
			String   current          = sentencePartInterest.get(index);
			String[] groups           = current.split("[+]");
			String   function 		  = groups[0];
			int      beginFunction    = Integer.parseInt(groups[1]);
			int      endFunction      = Integer.parseInt(groups[2]);
			
			
			Map<Integer, String> WORKLISTSORTED = new TreeMap<Integer, String>(WORKLIST);


			

			for(Entry<Integer, String> partSentence: WORKLISTSORTED.entrySet()) {

				int     key 		= partSentence.getKey();
				String  value       = partSentence.getValue();
				boolean isInRange   = key >= beginFunction && key <= endFunction;
				boolean isGn         = function.equalsIgnoreCase("GNominal");

				boolean alreadyIncremented = increment2.contains("Groupe Nominal");

				if (isInRange && isGn) 						  { increment1 += value; }
				if (isInRange && isGn && !alreadyIncremented) { increment2 += groupNominal; }
				
				
				
				
				
				boolean fisrtGn = index == 0;
				if (isInRange && isGn && !alreadyIncremented && fisrtGn) { containerSyntaxeSubject.add(increment1); }
				

				
				String keyGroup = Integer.toString(beginFunction) + "+" + Integer.toString(endFunction);
				if (isInRange && isGn) { GROUPEINFORMATIONS.put(keyGroup, "GNOMINAL"); }

				
				
				
			}
		}
		System.out.println(increment1);
		System.out.println(Integer.toString(indexPart) + "-" + increment2 + "\n");
		writtingText(increment1, increment2);
		
	}
	

	

	private static int localisePsr(Map<Integer, String> WORKLISTSORTED) {
		
		int     psrIndex     = -1;

		for(Entry<Integer, String> searchGV: WORKLISTSORTED.entrySet()) {

			boolean groupV1  = searchGV.getValue().contains("(Psr)");
			boolean groupV2  = searchGV.getValue().contains("(Psr)");
			if (groupV1 || groupV2) {psrIndex = searchGV.getKey(); break;}
		}

		return psrIndex;
	}

	
	

	private static boolean localiseSchema(ArrayList<String> sentencePartInterest, int begin, int end, String gp1, String gp2) {

		//[GVerbal+0+1, GNominal+2+3]
		
		int index1 = -1;
		int index2 = -1;


		int indexage = 0;
		for (String schema: sentencePartInterest) {
			boolean containsG1 = schema.contains(gp1);
			boolean containsG2 = schema.contains(gp2);
			if      (containsG1) { index1 = indexage; } 
			else if (containsG2) { index2 = indexage; }
			indexage++;
		}

		boolean index1Before = index1 < index2;
		if (index1Before) { return true; }
		return false;
	}
	
	
	private static int localise(Map<Integer, String> WORKLISTSORTED, String what, int beginFunction) {
		
		int     gvIndex     = -1;

		for(Entry<Integer, String> searchGV: WORKLISTSORTED.entrySet()) {

			boolean groupV1  = searchGV.getValue().contains(what) && searchGV.getKey() >= beginFunction;
			boolean groupV2  = searchGV.getValue().contains(what) && searchGV.getKey() >= beginFunction;
			if (groupV1 || groupV2) {gvIndex = searchGV.getKey(); break;}
		}

		return gvIndex;
	}

	private static int localiseVerb(Map<Integer, String> WORKLISTSORTED, int beginFunction) {
		
		int     gvIndex     = -1;

		for(Entry<Integer, String> searchGV: WORKLISTSORTED.entrySet()) {

			boolean groupV1  = searchGV.getValue().contains("(vrb)") && searchGV.getKey() >= beginFunction;
			boolean groupV2  = searchGV.getValue().contains("(Vrb)") && searchGV.getKey() >= beginFunction;
			if (groupV1 || groupV2) {gvIndex = searchGV.getKey(); break;}
		}

		return gvIndex;
	}

	private static int localiseVerbWithoutIndex(Map<Integer, String> WORKLISTSORTED) {
		
		int     gvIndex     = -1;

		for(Entry<Integer, String> searchGV: WORKLISTSORTED.entrySet()) {

			boolean groupV1  = searchGV.getValue().contains("(vrb)");
			boolean groupV2  = searchGV.getValue().contains("(Vrb)");
			if (groupV1 || groupV2) {gvIndex = searchGV.getKey(); break;}
		}

		return gvIndex;
	}

	
	private static String[] recuperateInfoGroups(ArrayList<String> sentencePartInterest, int index) {

		String   current          = sentencePartInterest.get(index);
		String[] groups           = current.split("[+]");
		String   function 		  = groups[0];
		String   beginFunction    = groups[1];
		String   endFunction      = groups[2];
		
		String[] infoGroups = {function, beginFunction, endFunction, current};
		return infoGroups;
	}
	
	
	
	
	
	
	private void schemaSentenceGroupNominalGroupPrepositionel(ArrayList<String> groupsSentence) {
		
		ArrayList<String> toRemove = new ArrayList<String>();


		
		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {
			
			//[GCOI_gv_p+0+3, Gcnjonc+4+4, GAdverb+5+5, GNominal+6+8, GPrep+9+11, Gcnjonc+12+12, GCOI_gv_p+13+22, 23]
			String[] last    	   = recuperateLastGroupsSentence(syntaxe,    groupsSentence);
			String[] current 	   = recuperateCurrentGroupsSentence(syntaxe, groupsSentence);
			String[] next    	   = recuperateNextGroupsSentence(syntaxe,    groupsSentence);

			String   lastFunction  = last[0];
			String   lastBegin 	   = last[1];
			String   lastEnd   	   = last[2];
			
			String   function      = current[0];
			String   begin 	       = current[1];
			String   end   	       = current[2];
			
			String   nextFunction  = next[0];
			String   nextBegin 	   = next[1];
			String   nextEnd   	   = next[2];
			

			boolean lastIsNotRel   = lastFunction.equalsIgnoreCase("GRel");
			boolean lastIsGoupNom  = lastFunction.equalsIgnoreCase("GNominal");
			
			boolean isGroupNominal = function.equalsIgnoreCase("GNominal");
			boolean isGroupPrpeo   = function.equalsIgnoreCase("GPrep");
			
			boolean nextIsPrep     = nextFunction.equalsIgnoreCase("GPrep");
			


			
			if (isGroupNominal && nextIsPrep && !lastIsNotRel) {
				makeASchem("groupe nominal + groupe prépositionel ", "GPrep_gn_p+",
							begin, nextEnd, syntaxe, false, true,
							groupsSentence, toRemove);
			}
			


		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	

	
	
	

	
	
	private void schemaSentenceGroupVerbalGroupPrepositionel(ArrayList<String> groupsSentence) {
		
		ArrayList<String> toRemove = new ArrayList<String>();


		
		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {
			
			//[GCOI_gv_p+0+3, Gcnjonc+4+4, GAdverb+5+5, GNominal+6+8, GPrep+9+11, Gcnjonc+12+12, GCOI_gv_p+13+22, 23]
			String[] last    	  = recuperateLastGroupsSentence(syntaxe, groupsSentence);
			String[] current 	  = recuperateCurrentGroupsSentence(syntaxe, groupsSentence);
			String[] next    	  = recuperateNextGroupsSentence(syntaxe, groupsSentence);

			String   lastFunction = last[0];
			String   lastBegin    = last[1];
			String   lastEnd      = last[2];

			String   function     = current[0];
			String   begin 	      = current[1];
			String   end   	      = current[2];
			
			String   nextFunction = next[0];
			String   nextBegin    = next[1];
			String   nextEnd      = next[2];


			
			boolean currentGpVerb  = function.equalsIgnoreCase("GPrepVerbal");
			
			boolean currentIsGprep = function.equalsIgnoreCase("Gprep");
			boolean nextIsGverb    = nextFunction.equalsIgnoreCase("Gverbal");
			
			
			
			if (currentIsGprep && nextIsGverb) {
				makeASchem("Groupe Nominal + Groupe Prépositionel + Groupe Verbal ", "Gprep_gv+",
						begin, nextEnd, syntaxe, false, true, groupsSentence, toRemove);
			}
			
			if (currentGpVerb) {
				makeASchem("Groupe Nominal + Groupe Prépositionel + Groupe Verbal ", "Gprep_gv+",
						begin, end, syntaxe, false, false, groupsSentence, toRemove);
			}
			



			


		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	
	
	
	
	
	
	
	
	
	private void schemaSentenceCnjnc(ArrayList<String> groupsSentence) {
		
		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {

			//[GCOI_gv_p+0+3, Gcnjonc+4+4, GAdverb+5+5, GNominal+6+8, GPrep+9+11, Gcnjonc+12+12, GCOI_gv_p+13+22, 23]
			String[] current = recuperateCurrentGroupsSentence(syntaxe, groupsSentence);

			String   function     = current[0];
			String   begin 	      = current[1];
			String   end   	      = current[2];


			boolean isCnjnc = function.equalsIgnoreCase("Gcnjonc");

			if (isCnjnc) { makeASchem("Conjonction ", "GCNJNC+", begin, end, syntaxe, false, false, groupsSentence, toRemove); }

		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	
	


	private void schemaSentencePropositionSubordonéeRelative(ArrayList<String> groupsSentence, ArrayList<String> saveSentenceGroups) {

		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {

			String[] last    		= recuperateLastGroupsSentence(syntaxe,    groupsSentence);
			String[] current 		= recuperateCurrentGroupsSentence(syntaxe, groupsSentence);
			String[] next    		= recuperateNextGroupsSentence(syntaxe,    groupsSentence);
			String[] nextX2  		= recuperateNextX2GroupsSentence(syntaxe,  groupsSentence);
		
			
			String   lastFunction   = last[0];
			String   lastBegin 	    = last[1];
			String   lastEnd   	    = last[2];
			
			String   function       = current[0];
			String   begin 	        = current[1];
			String   end   	        = current[2];
			
			String   nextFunction   = next[0];
			String   nextBegin 	    = next[1];
			String   nextEnd   	    = next[2];
			
			String   nextX2Function = nextX2[0];
			String   nextX2Begin 	= nextX2[1];
			String   nextX2End   	= nextX2[2];


			
			boolean lastIsGroupN = lastFunction.equalsIgnoreCase("GNominal");
			boolean currentIsRel = function.equalsIgnoreCase("GRel");
			boolean currentIsGn  = function.equalsIgnoreCase("GN");
			
			boolean nextIsGroupN = nextFunction.equalsIgnoreCase("GNominal");
			boolean nextIsGroupV = nextFunction.equalsIgnoreCase("GVerbal");
			boolean nextCod      = nextFunction.equalsIgnoreCase("GCOD");
			boolean nextSrel     = nextFunction.equalsIgnoreCase("SRel");
			boolean nextIsGs_GV  = nextFunction.equalsIgnoreCase("GS_GV");
			
			boolean nextX2IsGroupV = nextX2Function.equalsIgnoreCase("GVerbal");
			


			if (currentIsRel && nextIsGs_GV) {
				System.out.println("Proposition Suboordonée + Groupe Sujet + Groupe Verbal +"  + begin + "-" + nextX2End);
				String newFunction = "SRel+" + begin + "+" + nextEnd;
				groupsSentence.set(syntaxe, newFunction);
				toRemove.add(groupsSentence.get(syntaxe + 1));

			}
			
			else if (currentIsRel && nextIsGroupV) { 
				makeASchem("Proposition Suboordonée + Groupe Verbal ", "SRel+", 
						begin, nextEnd, syntaxe, false, true, groupsSentence, toRemove); 
			}
			

			else if (currentIsRel && nextCod) {
				makeASchem("Proposition Suboordonée + Complément objet directe ", "isGCOD_Grel_gv+", 
						begin, nextEnd, syntaxe, false, true, groupsSentence, toRemove); 

			}
			
			else if (currentIsRel && nextIsGroupN) {
				System.out.println("Proposition Suboordonée + Groupe Nominal +"  + begin + "-" + nextEnd);
				String newFunction = "SRel+" + begin + "+" + nextEnd;
				groupsSentence.set(syntaxe, newFunction);
			}

			
			else if (currentIsRel) {
				System.out.println("Proposition Suboordonée +"  + begin + "-" + end);
				String newFunction = "SRel+" + begin + "+" + end;
				groupsSentence.set(syntaxe, newFunction);
			}
			
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	
	
	private void schemaSentenceGroupVerbalAdverb(ArrayList<String> groupsSentence) {

		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {


			String[] last    	  = recuperateLastGroupsSentence(syntaxe,    groupsSentence);
			String[] current 	  = recuperateCurrentGroupsSentence(syntaxe, groupsSentence);
			String[] next         = recuperateNextGroupsSentence(syntaxe,    groupsSentence);

			String   lastFunction = last[0];
			String   lastBegin 	  = last[1];
			String   lastEnd   	  = last[2];
			
			String   function     = current[0];
			String   begin 	      = current[1];
			String   end   	      = current[2];
			
			String   nextFunction = next[0];
			String   nextBegin 	  = next[1];
			String   nextEnd   	  = next[2];
			


			boolean isGv  = function.equalsIgnoreCase("GV");
			
			boolean isAdv = nextFunction.equalsIgnoreCase("GAdv");
			
			
	
			if (isGv && isAdv) { 
				makeASchem("Groupe Verbal ", "GV+",  begin, nextEnd, syntaxe, false, true, groupsSentence, toRemove);
			}

			

		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	
	
	
	
	private void schemaSentenceAdverbe(ArrayList<String> groupsSentence) {
		
		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {

			//[GCOI_gv_p+0+3, Gcnjonc+4+4, GAdverb+5+5, GNominal+6+8, GPrep+9+11, Gcnjonc+12+12, GCOI_gv_p+13+22, 23]
			String[] last    	  = recuperateLastGroupsSentence(syntaxe,    groupsSentence);
			String[] current 	  = recuperateCurrentGroupsSentence(syntaxe, groupsSentence);
			String[] next         = recuperateNextGroupsSentence(syntaxe,    groupsSentence);

			String   lastFunction = last[0];
			String   lastBegin 	  = last[1];
			String   lastEnd   	  = last[2];
			
			String   function     = current[0];
			String   begin 	      = current[1];
			String   end   	      = current[2];
			
			String   nextFunction = next[0];
			String   nextBegin 	  = next[1];
			String   nextEnd   	  = next[2];
			

			boolean lastIsGv         = lastFunction.equalsIgnoreCase("GVerbal");
			boolean isGroupAdverbial = function.equalsIgnoreCase("GAdverb");
			boolean nextIsGv         = nextFunction.equalsIgnoreCase("GVerbal");

			if (!lastIsGv && isGroupAdverbial && !nextIsGv) { 
				makeASchem("groupe Adverbial ", "GAdv+",  begin, end, syntaxe, false, false, groupsSentence, toRemove);
			}

			else if (isGroupAdverbial) {
				makeASchem("groupe Adverbial ", "GAdv+",  begin, end, syntaxe, false, false, groupsSentence, toRemove);
			}
			

		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	
	
	
	private void schemaSentenceGroupVerbalGroupNominal(ArrayList<String> groupsSentence) {
		
		ArrayList<String> toRemove = new ArrayList<String>();



		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {

			//[GCOI_gv_p+0+3, Gcnjonc+4+4, GAdverb+5+5, GNominal+6+8, GPrep+9+11, Gcnjonc+12+12, GCOI_gv_p+13+22, 23]
			
			String[] lastX2  		= recuperateLastX2GroupsSentence(syntaxe,  groupsSentence);
			String[] last    		= recuperateLastGroupsSentence(syntaxe,    groupsSentence);
			String[] current 		= recuperateCurrentGroupsSentence(syntaxe, groupsSentence);
			String[] next    	    = recuperateNextGroupsSentence(syntaxe,    groupsSentence);
			String[] nextX2         = recuperateNextX2GroupsSentence(syntaxe,  groupsSentence);
			
			
			String   lastX2Function = lastX2[0];
			String   lastX2Begin 	= lastX2[1];
			String   lastX2End   	= lastX2[2];
			
			String   lastFunction   = last[0];
			String   lastBegin 	    = last[1];
			String   lastEnd   	    = last[2];
			
			String   function       = current[0];
			String   begin 	        = current[1];
			String   end   	        = current[2];
			
			String   nextFunction   = next[0];
			String   nextBegin 	    = next[1];
			String   nextEnd   	    = next[2];

			String   nextX2Function = nextX2[0];
			String   nextX2Begin 	= nextX2[1];
			String   nextX2End   	= nextX2[2];
			
			
			boolean lastX2IsGn     = lastX2Function.equalsIgnoreCase("GNominal");

			boolean lastIsGn       = lastFunction.equalsIgnoreCase("GNominal");
			boolean lastIsPrep     = lastFunction.equalsIgnoreCase("GPrep");
			boolean lastIsCod      = lastFunction.contains("COD");
			
			boolean currentIsGn    = function.equalsIgnoreCase("GNominal");
			boolean isGroupVerbal  = function.equalsIgnoreCase("GVerbal");
			boolean currentIsPropo = function.equalsIgnoreCase("GRel");
			boolean currentIsPrep  = function.equalsIgnoreCase("GPrep");
			

			boolean nexrIsVerbal   = nextFunction.equalsIgnoreCase("GVerbal");
			boolean nextIsGn       = nextFunction.equalsIgnoreCase("GNominal");
			boolean nextIsPrep     = nextFunction.equalsIgnoreCase("GPrep");
			
			
			
			boolean nextX2IsGn     = nextX2Function.equalsIgnoreCase("GNominal");


			
			if (lastIsGn && currentIsPrep && nexrIsVerbal && nextX2IsGn) {
				System.out.println("Groupe Sujet + Groupe Prépositionel + Groupe Verbal + Group nominal " + lastBegin + "-" + nextX2End);
				String newFunction = "GCOD+" + lastBegin + "+" + nextX2End;
				groupsSentence.set(syntaxe - 1, newFunction); 
				toRemove.add(groupsSentence.get(syntaxe));
				toRemove.add(groupsSentence.get(syntaxe + 1));
				toRemove.add(groupsSentence.get(syntaxe + 2));
			}


			else if (lastIsGn && currentIsPrep && nexrIsVerbal) {
				System.out.println("Groupe Sujet + Groupe Prépositionel + Groupe Verbal " + lastBegin + "-" + nextEnd);
				String newFunction = "GCOD+" + lastBegin + "+" + nextEnd;
				groupsSentence.set(syntaxe - 1, newFunction); 
				toRemove.add(groupsSentence.get(syntaxe));
				toRemove.add(groupsSentence.get(syntaxe + 1));

			}


			else if (lastIsGn && isGroupVerbal && nextIsGn)   {System.out.println("groupe sujet + cod " + lastBegin + "-" + nextEnd);
															   String newFunction = "GCOD+" + lastBegin + "+" + nextEnd;
															   groupsSentence.set(syntaxe - 1, newFunction); 
															   toRemove.add(groupsSentence.get(syntaxe));
															   toRemove.add(groupsSentence.get(syntaxe + 1));}
			
			else if (currentIsGn && nexrIsVerbal && (!nextIsGn && !nextIsPrep && !lastIsCod)) {

				makeASchem("Groupe Sujet + Groupe Verbal ", "GS_GV+", 
						begin, nextEnd, syntaxe, false, true, groupsSentence, toRemove);
			}
			
			
			else if (isGroupVerbal && nextIsGn) 	    	  {System.out.println("cod " + begin + "-" + nextEnd);
															   String newFunction = "GCOD+" + begin + "+" + nextEnd;
															   groupsSentence.set(syntaxe, newFunction); 
															   toRemove.add(groupsSentence.get(syntaxe + 1));}
			

			else if (lastIsGn && isGroupVerbal && nextIsPrep) {	makeASchem("groupe sujet + coi ", "GCOI_gn_gv_p+",
																		   lastBegin, nextEnd, syntaxe, true, true,
															    		   groupsSentence, toRemove);
			                                                  }

			else if (isGroupVerbal && nextIsPrep)			  {	makeASchem("Complement Object Indirecte ", "GCOI_gv_p+",
																		   begin, nextEnd, syntaxe, false, true,
																		   groupsSentence, toRemove);
												  			  }
			
			


			

		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}

	}
	
	
	
	

	private void schemaGroupSujetVerbCoi(ArrayList<String> groupsSentence) {

		System.out.println( groupsSentence);
		
		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {


			String[] last         = recuperateLastGroupsSentence(syntaxe, groupsSentence);
			String[] current      = recuperateCurrentGroupsSentence(syntaxe, groupsSentence);
			String[] next         = recuperateNextGroupsSentence(syntaxe, groupsSentence);

			String   lastFunction = last[0];
			String   lastBegin 	  = last[1];
			String   lastEnd   	  = last[2];
			
			String   function     = current[0];
			String   begin 	      = current[1];
			String   end   	      = current[2];
			
			String   nextFunction = next[0];
			String   nextBegin 	  = next[1];
			String   nextEnd   	  = next[2];
			

			boolean currentIsGprep = function.equalsIgnoreCase("GPrep");
			boolean isGv           = function.equalsIgnoreCase("GV");
			boolean isCoi          = function.equalsIgnoreCase("GCOI");
			
			
			boolean nextCoi        = nextFunction.equalsIgnoreCase("GCOI_gv_p");
			boolean nextGprep      = nextFunction.equalsIgnoreCase("GPrep");
			boolean nextGprepGv    = nextFunction.equalsIgnoreCase("Gprep_gv_Cod");


			
			if (isGv && nextGprepGv) {
				System.out.println("Complément Objet Indirecte " + begin + "-" + nextEnd);
				String newFunction = "GPREP_GCOI+" + begin + "+" + nextEnd;
				groupsSentence.set(syntaxe, newFunction); 
				toRemove.add(groupsSentence.get(syntaxe + 1));
			}
			
			else if (isCoi && nextGprep) {
				System.out.println("Complément Objet Indirecte " + begin + "-" + nextEnd);
				String newFunction = "GCOI+" + begin + "+" + nextEnd;
				groupsSentence.set(syntaxe, newFunction); 
				toRemove.add(groupsSentence.get(syntaxe + 1));
			}
			
			else if (isGv && nextGprep) {
				System.out.println("Complément Objet Indirecte " + begin + "-" + nextEnd);
				String newFunction = "GCOI+" + begin + "+" + nextEnd;
				groupsSentence.set(syntaxe, newFunction); 
				toRemove.add(groupsSentence.get(syntaxe + 1));
			}
			
			else if (currentIsGprep && nextCoi) {
				System.out.println("Groupe Sujet Prépositionel + Complement indirecte " + begin + "-" + nextEnd);
				String newFunction = "GPREP_GCOI+" + begin + "+" + nextEnd;
				groupsSentence.set(syntaxe, newFunction); 
				toRemove.add(groupsSentence.get(syntaxe + 1));
			}


			
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}

		
	
	
	private void schemaGroupSujetVerbCod(ArrayList<String> groupsSentence) {
		

		
		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {


			String[] last         = recuperateLastGroupsSentence(syntaxe, groupsSentence);
			String[] current      = recuperateCurrentGroupsSentence(syntaxe, groupsSentence);
			String[] next         = recuperateNextGroupsSentence(syntaxe, groupsSentence);

			String   lastFunction = last[0];
			String   lastBegin 	  = last[1];
			String   lastEnd   	  = last[2];
			
			String   function     = current[0];
			String   begin 	      = current[1];
			String   end   	      = current[2];
			
			String   nextFunction = next[0];
			String   nextBegin 	  = next[1];
			String   nextEnd   	  = next[2];
			
			
			boolean currentIsSjGv  = function.equalsIgnoreCase("GS_GV");
			boolean nextCod        = nextFunction.equalsIgnoreCase("GCOD");
			boolean nextCod2        = nextFunction.equalsIgnoreCase("GCOD_GPREP");

			
			if (currentIsSjGv && nextCod2) {
				System.out.println("Groupe Sujet + Groupe Verbal + Complement directe " + begin + "-" + nextEnd);
				String newFunction = "GCOD+" + begin + "+" + nextEnd;
				groupsSentence.set(syntaxe, newFunction); 
				toRemove.add(groupsSentence.get(syntaxe + 1));
			}
			else if (currentIsSjGv && nextCod) {
				System.out.println("Groupe Sujet + Groupe Verbal + Complement directe " + begin + "-" + nextEnd);
				String newFunction = "GCOD+" + begin + "+" + nextEnd;
				groupsSentence.set(syntaxe, newFunction); 
				toRemove.add(groupsSentence.get(syntaxe + 1));
			}

			
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	
	
	
	
	
	
	
	private void schemaSentenceGroupNominal(ArrayList<String> groupsSentence) {

		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {


			String[] last         = recuperateLastGroupsSentence(syntaxe, groupsSentence);
			String[] current      = recuperateCurrentGroupsSentence(syntaxe, groupsSentence);
			String[] next         = recuperateNextGroupsSentence(syntaxe, groupsSentence);

			String   lastFunction = last[0];
			String   lastBegin 	  = last[1];
			String   lastEnd   	  = last[2];
			
			String   function     = current[0];
			String   begin 	      = current[1];
			String   end   	      = current[2];
			
			String   nextFunction = next[0];
			String   nextBegin 	  = next[1];
			String   nextEnd   	  = next[2];
			
			
			boolean lastIsntGv     = lastFunction.equalsIgnoreCase("GVerbal");

			boolean isGroupNominal = function.equalsIgnoreCase("GNominal");
			
			boolean nextIsntPrep   = nextFunction.equalsIgnoreCase("GPrep");
			boolean nextIsntGv     = nextFunction.equalsIgnoreCase("GVerbal");

			boolean conditionNoneLast = !lastIsntGv;
			boolean conditionNoneNext = !nextIsntPrep && !nextIsntGv;

			
			if (conditionNoneLast && conditionNoneNext && isGroupNominal) {System.out.println("Groupe Nominal" + begin + "-" + end);
												 						   String newFunction = "GN+" + begin + "+" + end;
												 						   groupsSentence.set(syntaxe, newFunction);
												 						  }
			
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	
	
	
	
	private void schemaVerbPrepVerb(ArrayList<String> groupsSentence) {

		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {

			String[] last    = recuperateLastGroupsSentence(syntaxe, groupsSentence);
			String[] current = recuperateCurrentGroupsSentence(syntaxe, groupsSentence);
			String[] next    = recuperateNextGroupsSentence(syntaxe, groupsSentence);
			String[] nextX2  = recuperateNextX2GroupsSentence(syntaxe, groupsSentence);
			
			String   lastFunction     = last[0];
			
			String   function     = current[0];
			String   begin 	      = current[1];
			String   end   	      = current[2];
			
			String   nextFunction = next[0];
			String   nextBegin 	  = next[1];
			String   nextEnd   	  = next[2];
			
			String   nextX2Function = nextX2[0];
			String   nextX2Begin 	= nextX2[1];
			String   nextX2End   	= nextX2[2];
			


			boolean isGroupVerbalPrep  = function.equalsIgnoreCase("GCOI_gv_p");
			boolean nextIsVerbal       = nextFunction.contains("GV");

			boolean currentIsGprepVrb  = function.equalsIgnoreCase("Gprep_gv");
			boolean nextIsGn           = nextFunction.contains("GN");



			if (isGroupVerbalPrep && nextIsVerbal) {
				makeASchem("COI ", "GPREP_GCOI+", begin, nextEnd, syntaxe, false, true, groupsSentence, toRemove);
			}

			else if (currentIsGprepVrb && nextIsGn) {
				makeASchem("GPREP COD ", "Gprep_gv_Cod+", begin, nextEnd, syntaxe, false, true, groupsSentence, toRemove);
			}
			
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	
	
	
	private void schemaGroupCod(ArrayList<String> groupsSentence) {


		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {

			String[] last    = recuperateLastGroupsSentence(syntaxe, groupsSentence);
			String[] current = recuperateCurrentGroupsSentence(syntaxe, groupsSentence);
			String[] next    = recuperateNextGroupsSentence(syntaxe, groupsSentence);
			String[] nextX2  = recuperateNextX2GroupsSentence(syntaxe, groupsSentence);
			
			String   lastFunction     = last[0];
			
			String   function     = current[0];
			String   begin 	      = current[1];
			String   end   	      = current[2];
			
			String   nextFunction = next[0];
			String   nextBegin 	  = next[1];
			String   nextEnd   	  = next[2];
			
			String   nextX2Function = nextX2[0];
			String   nextX2Begin 	= nextX2[1];
			String   nextX2End   	= nextX2[2];
			


			boolean isCOD      = function.equalsIgnoreCase("GCOD");
			boolean isGV       = function.equalsIgnoreCase("GV") && syntaxe == 0;
			
			boolean nextIsCod  = nextFunction.contains("GCOD");
			boolean nextIsAdv  = nextFunction.contains("GAdv");
			
			boolean nextX2Gn   = nextX2Function.contains("GN");

			if (isGV && nextIsAdv && nextX2Gn) {
				System.out.println("Groupe Verbal + Groupe Adverbial + Groupe Nominal" + begin + "-" + nextX2End);
				String newFunction = "GCOD+" + begin + "+" + nextX2End;
				groupsSentence.set(syntaxe, newFunction);
				toRemove.add(groupsSentence.get(syntaxe + 1));
				toRemove.add(groupsSentence.get(syntaxe + 2));
			}

			else if (isCOD && nextIsCod) {
				makeASchem("Groupe Prépositionel COD ", "GCOD+", begin, nextEnd, syntaxe, false, true, groupsSentence, toRemove);
			}

			
			
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	
	

	private void schemaGprepAndPropo(ArrayList<String> groupsSentence) {


		
		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {

			String[] last    = recuperateLastGroupsSentence(syntaxe, groupsSentence);
			String[] current = recuperateCurrentGroupsSentence(syntaxe, groupsSentence);
			String[] next    = recuperateNextGroupsSentence(syntaxe, groupsSentence);
			String[] nextX2  = recuperateNextX2GroupsSentence(syntaxe, groupsSentence);
			
			String   lastFunction     = last[0];
			
			String   function     = current[0];
			String   begin 	      = current[1];
			String   end   	      = current[2];
			
			String   nextFunction = next[0];
			String   nextBegin 	  = next[1];
			String   nextEnd   	  = next[2];
			
			String   nextX2Function = nextX2[0];
			String   nextX2Begin 	= nextX2[1];
			String   nextX2End   	= nextX2[2];
			


			boolean isGroupGprep = function.equalsIgnoreCase("GPrep");
			boolean groupsOne    = begin == end;
			
			boolean nextIsCod    = nextFunction.contains("GCOD");
			boolean nextIsCoi    = nextFunction.contains("GCOI");



			if (isGroupGprep && nextIsCod && groupsOne) {
				makeASchem("Groupe Prépositionel COD ", "GPREP_GCOD+", begin, nextEnd, syntaxe, false, true, groupsSentence, toRemove);
			}

			
			
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	
	
	
	private void schemaSentenceCodPrep(ArrayList<String> groupsSentence) {

		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 1; syntaxe < groupsSentence.size(); syntaxe++) {

			String[] last    = recuperateLastGroupsSentence(syntaxe, groupsSentence);
			String[] current = recuperateCurrentGroupsSentence(syntaxe, groupsSentence);
			String[] next    = recuperateNextGroupsSentence(syntaxe, groupsSentence);
			String[] nextX2  = recuperateNextX2GroupsSentence(syntaxe, groupsSentence);
			
			String   lastFunction     = last[0];
			
			String   function     = current[0];
			String   begin 	      = current[1];
			String   end   	      = current[2];
			
			String   nextFunction = next[0];
			String   nextBegin 	  = next[1];
			String   nextEnd   	  = next[2];
			
			String   nextX2Function = nextX2[0];
			String   nextX2Begin 	= nextX2[1];
			String   nextX2End   	= nextX2[2];
			


			boolean isGroupNominal = function.equalsIgnoreCase("GCOD");

			
			boolean nextIsntPrep   = nextFunction.equalsIgnoreCase("GPrep");




			if (isGroupNominal && nextIsntPrep) {
				makeASchem("COD + Prep ", "GCOD_GPREP+", begin, nextEnd, syntaxe, false, true, groupsSentence, toRemove);
			}

			
			
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	
	
	
	
	
	
	
	
	
	
	private void schemaSentenceGroupVerbal(ArrayList<String> groupsSentence) {

		
		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {


			String[] last    = recuperateLastGroupsSentence(syntaxe, groupsSentence);
			String[] current = recuperateCurrentGroupsSentence(syntaxe, groupsSentence);
			String[] next    = recuperateNextGroupsSentence(syntaxe, groupsSentence);

			String   lastFunction = last[0];
			String   lastBegin 	  = last[1];
			String   lastEnd   	  = last[2];
			
			String   function     = current[0];
			String   begin 	      = current[1];
			String   end   	      = current[2];
			
			String   nextFunction = next[0];
			String   nextBegin 	  = next[1];
			String   nextEnd   	  = next[2];
			

			boolean lastIsntGn    = lastFunction.equalsIgnoreCase("GNominal");
			boolean lastIsntRel   = lastFunction.equalsIgnoreCase("GRel");

			boolean isGroupVerbal = function.equalsIgnoreCase("GVerbal");

			boolean nextIsntPrep  = nextFunction.equalsIgnoreCase("GPrep");
			boolean nextIsntGn    = nextFunction.equalsIgnoreCase("GNominal");



			if (isGroupVerbal) {
				System.out.println("Groupe Verbal " + begin + "-" + end);
				String newFunction = "GV+" + begin + "+" + end;
				groupsSentence.set(syntaxe, newFunction);}

		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}

	}
	
	
	
	
	
	

	private static void makeASchem(String display, String schema,
								   String lastBegin, String nextEnd, int syntaxe,
								   boolean toRemoveLast, boolean toRemoveNext,
								   ArrayList<String> groupsSentence, 
								   ArrayList<String> toRemove) {


		

		String newFunction = schema + lastBegin + "+" + nextEnd;
	    groupsSentence.set(syntaxe, newFunction);

		if (toRemoveLast) { toRemove.add(groupsSentence.get(syntaxe - 1));  }
		if (toRemoveNext) { toRemove.add(groupsSentence.get(syntaxe + 1));  }
		
		System.out.println(display + " " + lastBegin + "-" + nextEnd);
	}
	

	
	
	
	
	
	
	private static String[] recuperateLastX2GroupsSentence(int syntaxe, ArrayList<String> groupsSentence) {
		
		//[GNominal+0+2, GVerbal+3+3, GPrep+4+5, 6]
		boolean isNotFirstWord = syntaxe > 1;

		String   lastX2Function   = "";
		String   lastX2Begin 	  = "";
		String   lastX2End   	  = "";
		if (isNotFirstWord) {
			String[] lastX2  	 = groupsSentence.get(syntaxe - 2).split("[+]");
			boolean  isNotEmptyLast = lastX2.length > 2;
			if (isNotEmptyLast) {
				lastX2Function  = lastX2[0];
				lastX2Begin 	= lastX2[1];
				lastX2End   	= lastX2[2];
			}
		}

		String[] lastReturn = {lastX2Function, lastX2Begin, lastX2End};
		return lastReturn;
	}
	
	
	
	private static String[] recuperateLastGroupsSentence(int syntaxe, ArrayList<String> groupsSentence) {
		
		//[GNominal+0+2, GVerbal+3+3, GPrep+4+5, 6]
		boolean isNotFirstWord = syntaxe > 0;

		String   lastFunction   = "";
		String   lastBegin 	    = "";
		String   lastEnd   	    = "";
		if (isNotFirstWord) {
			String[] last  		    = groupsSentence.get(syntaxe - 1).split("[+]");
			boolean  isNotEmptyLast = last.length > 1;
			if (isNotEmptyLast) {
				lastFunction  = last[0];
				lastBegin 	  = last[1];
				lastEnd   	  = last[2];
			}
		}
		//{GNominal, 0, 2}
		String[] lastReturn = {lastFunction, lastBegin, lastEnd};
		return lastReturn;
	}
	
	
	
	
	
	private static String[] recuperateCurrentGroupsSentence(int syntaxe, ArrayList<String> groupsSentence) {
	
		////[GNominal+0+2, GVerbal+3+3, GPrep+4+5, 6]
		String[] current    = groupsSentence.get(syntaxe).split("[+]");
		String   function   = "";
		String   begin 	    = "";
		String   end   	    = "";
		boolean  currentIsNotEmpty = current.length > 1;
		if (currentIsNotEmpty) {
			function = current[0];
			begin 	 = current[1];
			end   	 = current[2];
		}
		//{GNominal, 0, 2}
		String[] currentReturn = {function, begin, end};
		return   currentReturn;
	}
	
	
	
	

	
	private static String[] recuperateNextGroupsSentence(int syntaxe, ArrayList<String> groupsSentence) {
		

		boolean isLessOneLength = syntaxe < groupsSentence.size() - 1;
		
		if (isLessOneLength) {

			String[] next         	= groupsSentence.get(syntaxe + 1).split("[+]");
			String   nextFunction 	= "";
			String   nextBegin 	 	= "";
			String   nextEnd   	  	= "";
			boolean  nextIsNotEmpty = next.length > 1;
			if (nextIsNotEmpty) {
				nextFunction 	= next[0];
				nextBegin 	 	= next[1];
				nextEnd   	  	= next[2];
			}

			String[] nextReturn = {nextFunction, nextBegin, nextEnd};
			return nextReturn;
		}
		String[] nextReturn = {"", "", ""};
		return nextReturn;
	}
	
	
	
	
	
	private static String[] recuperateNextX2GroupsSentence(int syntaxe, ArrayList<String> groupsSentence) {
		
		////[GNominal+0+2, GVerbal+3+3, GPrep+4+5, 6]

		boolean isLessOneLength = syntaxe < groupsSentence.size() - 2;
		
		if (isLessOneLength) {

			String[] next         	= groupsSentence.get(syntaxe + 2).split("[+]");
			String   nextFunction 	= "";
			String   nextBegin 	 	= "";
			String   nextEnd   	  	= "";
			boolean  nextIsNotEmpty = next.length > 1;
			if (nextIsNotEmpty) {
				nextFunction 	= next[0];
				nextBegin 	 	= next[1];
				nextEnd   	  	= next[2];
			}
			//{GNominal, 0, 2}
			String[] nextReturn = {nextFunction, nextBegin, nextEnd};
			return nextReturn;
		}
		String[] nextReturn = {"", "", ""};
		return nextReturn;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private boolean Conditions(int nbList, int syntaxe, String method, String search, String[] list) {

		boolean condition = false;


		boolean contains = method == "contains";
		boolean equals   = method == "equal";
		boolean listEq   = method == "listEqual";
		
		boolean notEndListLess1 = syntaxe < this.currentSyntaxe.size() - nbList;

		if (notEndListLess1) {
			ArrayList<String> next = this.currentSyntaxe.get(syntaxe + nbList);

			if (contains) { condition = listContains(next, search); }
			if (equals)   { condition = listEqualsElement(next, search); }
			if (listEq)   { condition = listEquals(next, list); }

		}
		return condition;
	}

	
	private String getAdverb(int nbList, int syntaxe) {
		boolean notEndListLess1 = syntaxe < this.currentSyntaxe.size() - nbList;

		String adverbe = "";
		
		if (notEndListLess1) {
			adverbe = this.currentText.get(syntaxe + nbList);
		}
		return adverbe;
	}
	
	
	
	
	
	private void inGroupPrepositionelGv(ArrayList<String> groupsSentence, String functionGp, int beginGp, int endGp) {


		boolean isGroupPrepositionelVerbal = functionGp.equalsIgnoreCase("GPrepVerbal");
		
		if (isGroupPrepositionelVerbal) {

			for (int syntaxe = beginGp; syntaxe <= endGp; syntaxe++) {

				ArrayList<String> current = this.currentSyntaxe.get(syntaxe);
				
				boolean currentIsPrepo    = listEqualsElement(current, "Préposition");

				boolean nextIsPronom      = Conditions(1, syntaxe, "contains", "ronom", null);
				boolean nextIsVerb        = Conditions(1, syntaxe, "contains", "verbe#", null);

				boolean nextX2IsVerb      = Conditions(2, syntaxe, "contains", "verbe#", null);

				boolean nextX3IsAdv       = Conditions(3, syntaxe, "equal",   "Adverbe", null);
				

				groupAdverbial serchAdvb0  = new groupAdverbial(this.currentText.get(syntaxe));
				String 		   advFind0    = serchAdvb0.searchAdvbInDefineGroupe();
				
				String  nextX1Adverbe = getAdverb( 1, syntaxe);
				groupAdverbial serchAdvb1  = new groupAdverbial(nextX1Adverbe);
				String 		   advFind1    = serchAdvb1.searchAdvbInDefineGroupe();
				
				String  nextX2Adverbe = getAdverb( 2, syntaxe);
				groupAdverbial serchAdvb2  = new groupAdverbial(nextX2Adverbe);
				String 		   advFind2    = serchAdvb2.searchAdvbInDefineGroupe();
				
				String  nextX3Adverbe = getAdverb( 3, syntaxe);
				groupAdverbial serchAdvb3  = new groupAdverbial(nextX3Adverbe);
				String 		   advFind3    = serchAdvb3.searchAdvbInDefineGroupe();
				
				String  nextX4Adverbe = getAdverb( 4, syntaxe);
				groupAdverbial serchAdvb4  = new groupAdverbial(nextX4Adverbe);
				String 		   advFind4    = serchAdvb4.searchAdvbInDefineGroupe();
				
				
				String arrow = ""; String arrow1 = "(<- Prep)"; String arrow2 = "(-> Prep)";
				boolean isFirst = syntaxe == 0;
				if (currentIsPrepo && isFirst) { arrow = arrow2; } else { arrow = arrow1; }
				
				
				
				if (currentIsPrepo && nextIsPronom && nextX2IsVerb) {
					displayGroup(2, syntaxe, syntaxe + 1, syntaxe + 2, -1, -1, -1,  
							     arrow, "(Prn)", "(-> vrb)", "None", "None", "None");
				}
				else if (currentIsPrepo && nextIsVerb && nextX2IsVerb && nextX3IsAdv) {
					displayGroup(4, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, -1, -1,  
							    arrow, "(vrb)", "(-> vrb)", "(Adv " + advFind3 + ")", "None", "None");
				}
				
				else if (currentIsPrepo && nextIsPronom) {
					displayGroup(2, syntaxe, syntaxe + 1, -1, -1, -1, -1,  
						    arrow, "(Prnm)", "None", "None", "None", "None");
				}
				
				else if (currentIsPrepo && nextIsVerb) {
					displayGroup(2, syntaxe, syntaxe + 1, -1, -1, -1, -1,  
							    arrow, "(vrb)", "None", "None", "None", "None");
				}
			}
		}
	}
	
	
	
	private void inGroupVerbal(ArrayList<String> groupsSentence, String functionGp, int beginGp, int endGp) {
		
		
		boolean isGroupVerbal = functionGp.equalsIgnoreCase("GVerbal");
			
		if (isGroupVerbal) {

			for (int index = beginGp; index <= endGp; index++) {
	
				ArrayList<String> current = this.currentSyntaxe.get(index);

				//Conditions from current word syntaxe.
				boolean currentIsPrnm  = listContains(current, "ronom");
				boolean currentIsVerb  = listContains(current, "verbe#");
				boolean currentIsAdv   = listContains(current, "Adverbe");
				boolean currentIsNeg   = listContains(current, "Particule");
				boolean currentRel     = listContains(current, "ronom relatif");

				
				//Conditions from word (+1) syntaxe.
				boolean nextIsVerb      = Conditions(1, index, "contains", "verbe#", null);
				boolean nextIsParticule = Conditions(1, index, "equal", "Particule", null);
				boolean nextIsAdverb    = Conditions(1, index, "equal", "Adverbe", null);
				boolean nextIsPronom    = Conditions(1, index, "contains", "ronom", null);
				boolean nextIsPrnmRel   = Conditions(1, index, "contains", "ronom relatif", null);
				
				
				//Conditions from word (+2) syntaxe.
				boolean nextX2IsAdverb  = Conditions(2, index, "equal", "Adverbe", null);
				boolean nextX2IsVerb    = Conditions(2, index, "contains", "verbe#", null);
				boolean nextX2IsPrnm    = Conditions(2, index, "contains", "ronom", null);
				boolean nextX2Particule = Conditions(2, index, "equal", "Particule", null);
				boolean nextX2IsPrnmRel = Conditions(2, index, "contains", "ronom relatif", null);
	

				//Conditions from word (+3) syntaxe.
				boolean nextX3IsPrnm  = Conditions(3, index, "contains", "ronom", null);
				boolean nextX3IsAdv   = Conditions(3, index, "equals", "Adverbe", null);
				boolean nextX3IsVrb   = Conditions(3, index, "contains", "verbe#", null);
				boolean nextX3IsAdj   = Conditions(3, index, "equals", "Adjectif", null);

				
				//Conditions from word (+5) syntaxe.
				boolean nextX4IsAdv   = Conditions(4, index, "equal", "Adverbe", null);
				boolean nextX4IsPrnm  = Conditions(4, index, "contains", "ronom", null);
				
				
				//Conditions from word (+6) syntaxe.
				boolean nextX5IsPrnm  = Conditions(5, index, "contains", "ronom", null);

				
				
				//Recuperate adverbe functions from quantity, matiere...
				String[] function = recuperateAdverbFunction(index);
				String   advFind0 = function[0]; String advFind1 = function[1]; String advFind2 = function[2];
				String   advFind3 = function[3]; String advFind4 = function[4];

	

				if (currentIsPrnm && nextIsParticule && nextX2IsPrnm && nextX3IsVrb && nextX4IsPrnm) {
					 displayGroup(5, index, index + 1, index + 2, index + 3, index + 4, -1, 
							 "(Prn)", "(Neg)", "(Prn)", "(Vrb)", "(Prnm)", "None");
				}
				
				else if (currentIsPrnm && nextIsVerb && nextX2IsVerb && nextX3IsVrb) {
					 displayGroup(4, index, index + 1, index + 2, index + 3, -1, -1, 
							 "(Prn)", "(<- Vrb)", "(Vrb)", "(Vrb)", "None", "None"); 
				}
	

				else if (currentIsNeg && nextIsVerb && nextX2IsVerb && nextX3IsVrb) {
					 displayGroup(4, index, index + 1, index + 2, index + 3, -1, -1, 
							 "(Neg)", "(Vrb)", "(Vrb)", "(Vrb)", "None", "None");
				}
				else if (currentIsPrnm && nextIsParticule && nextX2IsPrnm && nextX3IsVrb) {
					displayGroup(4, index, index + 1, index + 2, index + 3, -1, -1, 
							"(Prn)", "(Neg)", "(Prn)", "(Vrb)", "None", "None");
				}

				else if (currentIsPrnm && nextIsPronom && nextX2IsVerb && nextX3IsVrb) {
					displayGroup(4, index, index + 1, index + 2, index + 3, -1, -1, 
							"(Prn)", "(Prn)", "(Vrb)", "(Vrb)", "None", "None");
				}
				
				else if (currentIsPrnm && nextIsPronom && nextX2IsVerb) {
					 displayGroup(3, index, index + 1, index + 2, -1, -1, -1, 
							 "(Prn)", "(Prn)", "(Vrb)", "None", "None", "None"); 
				}
				
				else if (currentIsPrnm && nextIsVerb && nextX2IsVerb) {
					 displayGroup(3, index, index + 1, index + 2, -1, -1, -1, 
							 "(Prn)", "(Vrb)", "(Vrb)", "None", "None", "None"); 
				}
				
				else if (currentIsPrnm && nextIsParticule && nextX2IsVerb) { 
						 displayGroup(3, index, index + 1, index + 2, -1, -1, -1, 
								 "(Prn)", "(Neg)", "(-> Vrb)", "None", "None", "None"); 
				}
				
				else if (currentIsVerb && nextIsVerb && nextX2IsVerb) {
					 displayGroup(3, index, index + 1, index + 2, -1, -1, -1, 
							 "(Vrb)", "(Vrb)", "(Vrb)", "None", "None", "None");
				}

				else if (currentIsNeg && nextIsVerb) {
					displayGroup(2, index, index + 1, -1, -1, -1, -1, 
							"(Neg)", "(Vrb)", "None", "None", "None", "None"); 
				}
				
				else if (currentIsVerb && nextIsVerb) {
					displayGroup(2, index, index + 1, -1, -1, -1, -1, 
							"(Vrb)", "(Vrb)", "None", "None", "None", "None"); 
				}
				
				else if (currentIsPrnm && nextIsVerb){
					displayGroup(2, index, index + 1, -1, -1, -1, -1, 
							"(Prn)", "(Vrb)", "None", "None", "None", "None"); 
				}
				
				else if (currentIsPrnm) {
					displayGroup(1, index, -1, -1, -1, -1, -1, 
							"(Prnm)", "None", "None", "None", "None", "None"); 
				}
				
				else if (currentIsVerb) {
					displayGroup(1, index, -1, -1, -1, -1, -1, 
							"(Vrb)", "None", "None", "None", "None", "None"); 
				}
				
			}
		}
	}
	
	
	private String[] recuperateAdverbFunction(int index) {

		String  nextAdverbe     = getAdverb(1, index);
		String  nextX2Adverbe   = getAdverb(2, index);
		String  nextX3Adverbe   = getAdverb(3, index);
		String  nextX4Adverbe   = getAdverb(4, index);
		
		groupAdverbial serchAdvb0  = new groupAdverbial(this.currentText.get(index));
		String 		   advFind0    = serchAdvb0.searchAdvbInDefineGroupe();

		groupAdverbial serchAdvb1  = new groupAdverbial(nextAdverbe);
		String 		   advFind1    = serchAdvb1.searchAdvbInDefineGroupe();
		
		groupAdverbial serchAdvb2  = new groupAdverbial(nextX2Adverbe);
		String 		   advFind2    = serchAdvb2.searchAdvbInDefineGroupe();
		
		groupAdverbial serchAdvb3  = new groupAdverbial(nextX3Adverbe);
		String 		   advFind3    = serchAdvb3.searchAdvbInDefineGroupe();
		
		groupAdverbial serchAdvb4  = new groupAdverbial(nextX4Adverbe);
		String 		   advFind4    = serchAdvb4.searchAdvbInDefineGroupe();

		String[] functions = {advFind0, advFind1, advFind2, advFind3, advFind4};
		return   functions;
	}
	
	
	private int isTirateInSentence() {

		int indexTirate = -1;
		for (int index=0; index < this.original.size(); index++) {

			String  word        = this.original.get(index);
			
			boolean isTirate    = word.contains("-");
			
			if (isTirate) { indexTirate = index; }		
		}
		return indexTirate;
	}
	

	
	
	private void inGroupNominal(ArrayList<String> groupsSentence, 
								String functionGp, int beginGp, int endGp) {

		boolean isGroupNominal = functionGp.equalsIgnoreCase("GNominal");
		
		if (isGroupNominal) {
				
			for (int syntaxe = beginGp; syntaxe <= endGp; syntaxe++) {


				ArrayList<String> current = this.currentSyntaxe.get(syntaxe);

				boolean currentIsDet  = listEquals(current, DETERMINANTS);
				boolean currentIsAdj  = listEqualsElement(current, "Adjectif");
				boolean currentIsNc   = listEqualsElement(current, "Nom commun");
				boolean currentIsNp   = listEqualsElement(current, "Nom propre");
				boolean currentIsInd  = listEqualsElement(current, "Pronom indéfini");
				
				boolean nextIsNm      = Conditions(1, syntaxe, "equal",    "Nom commun", null);
				boolean nextIsAdj     = Conditions(1, syntaxe, "contains", "djectif", null);
				boolean nextisAdj2    = Conditions(1, syntaxe, "contains", "djectif ", null);
				boolean nextIsNp      = Conditions(1, syntaxe, "equal",    "Nom propre", null);
				boolean nextIsdDet    = Conditions(1, syntaxe, "listEqual", "", DETERMINANTS);
				
				
				boolean nextX2IsRel   = Conditions(2, syntaxe, "contains", "ronom relatif", null);
				boolean nextX2IsAdj   = Conditions(2, syntaxe, "contains", "djectif", null);
				boolean nextX2IsAdj2  = Conditions(2, syntaxe, "contains", "djectif ", null);
				boolean nextX2IsNm    = Conditions(2, syntaxe, "equal",    "Nom commun", null);
				boolean nextX2IsCnjnc = Conditions(2, syntaxe, "equal",    "Conjonction de coordination", null);
				boolean nextX2IsPrn   = Conditions(2, syntaxe, "listEqual", "", DETERMINANTS);
				
				boolean nextX3IsArt   = Conditions(3, syntaxe, "listEqual", "", DETERMINANTS);
				boolean nextX3IsNp    = Conditions(3, syntaxe, "equal", "Nom propre", null);
				boolean nextX3IsNc    = Conditions(3, syntaxe, "equal", "Nom commun", null);
				boolean nextX3IsAdj   = Conditions(3, syntaxe, "equal", "Adjectif", null);
				
				boolean nextX4IsNp    = Conditions(4, syntaxe, "equal", "Nom propre", null);
				boolean nextX4IsNc    = Conditions(4, syntaxe, "equal", "Nom commun", null);
				
				boolean nextX5IsNm    = Conditions(5, syntaxe, "equal", "Nom commun", null);
				


				

				
				if (currentIsDet && nextIsNp && nextX2IsCnjnc && nextX3IsArt && nextX4IsNp && nextX5IsNm) {
					displayGroup(6, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, syntaxe + 4, syntaxe + 5,
							    "(Art)", "(-> Np)", "(cnjn)", "(art)", "(np)", "(np)");}
				
				
				
				else if (currentIsDet && nextIsNm && nextX2IsAdj && nextX3IsArt && nextX4IsNc) {
					displayGroup(5, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, syntaxe + 4, -1,
						       "(Art)", "(-> Nc)", "(Adj)", "(Art)", "(Nc)", "None");}
				
				else if (currentIsDet && nextIsAdj && nextX2IsNm && nextX3IsArt && nextX4IsNc) {
					displayGroup(5, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, syntaxe + 4, -1,
						       "(Art)", "(Adj)", "(-> Nc)", "(Art)", "(Nc)", "None");}
				
				
				else if (currentIsDet && nextIsNm && nextX2IsPrn && nextX3IsNp) {
					displayGroup(4, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, -1, -1,
						       "(Art)", "(-> Nm)", "(Prn)", "(Np)", "None", "None");}

				else if (currentIsDet && nextIsNm && nextX2IsAdj && nextX3IsNc) {
					displayGroup(4, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, -1, -1,
						       "(Art)", "(-> Nc)", "(Adj)", "(nc)", "None", "None");}
				
					
				
				else if (currentIsDet && nextIsNm && nextX2IsAdj && !nextX2IsAdj2)  {
					displayGroup(3, syntaxe, syntaxe + 1, syntaxe + 2, -1, -1, -1,
							    "(Art)", "(-> Nm)", "(<- Adj)", "None", "None", "None");}

				else if (currentIsDet && nextIsAdj && nextX2IsNm && !nextisAdj2) {
					displayGroup(3, syntaxe, syntaxe + 1, syntaxe + 2, -1, -1, -1,
		   						 "(Art ->)", "(Adj ->)", "(Nc)", "None", "None", "None");}

				else if (currentIsDet && nextIsdDet && nextX2IsNm) {
					displayGroup(3, syntaxe, syntaxe + 1, syntaxe + 2, -1, -1, -1,
							    "(Art)", "(Art)", "(-> Nm)", "None", "None", "None");}


				else if (currentIsDet && nextIsdDet) {
					displayGroup(2, syntaxe, syntaxe + 1, -1, -1, -1, -1,
	   						 "(Art)", "(Art)", "None", "None", "None", "None");}
				
				else if (currentIsDet && nextIsNm)  {
					displayGroup(2, syntaxe, syntaxe + 1, -1, -1, -1, -1,
		   						 "(Art)", "(-> Nm)", "None", "None", "None", "None");}
				
				else if (currentIsDet && nextIsNp) {
					displayGroup(2, syntaxe, syntaxe + 1, -1, -1, -1, -1,
						    "(det)", "(-> Np)", "None", "None", "None", "None");
				}
				
				else if (currentIsNp) {	displayGroup(1, syntaxe, -1, -1, -1, -1, -1,
					    "(Np)", "None", "None", "None", "None", "None");}
				
				else if (currentIsNc && nextIsAdj) {
					displayGroup(1, syntaxe, syntaxe + 1, -1, -1, -1, -1,
						    "(Nm <-)", "(Adjectif)", "None", "None", "None", "None");}
				
				else if (currentIsInd) {
					displayGroup(1, syntaxe, -1, -1, -1, -1, -1,
						    "(Prnm indéfini)", "None", "None", "None", "None", "None");
				}
				
				else if (currentIsNc) {
					displayGroup(1, syntaxe, -1, -1, -1, -1, -1,
						    "(Nm)", "None", "None", "None", "None", "None");}
				
				else if (currentIsAdj) {
					displayGroup(1, syntaxe, -1, -1, -1, -1, -1,
						         "(Adj)", "None", "None", "None", "None", "None");}
				
			}
		}
	}


	
	
	private void inGroupPrepositionelGn(ArrayList<String> groupsSentence, String functionGp, int beginGp, int endGp) {


		boolean isGroupPrepositionel = functionGp.equalsIgnoreCase("GPrep");
		
		if (isGroupPrepositionel) {
			
			for (int syntaxe = beginGp; syntaxe <= endGp; syntaxe++) {

				ArrayList<String> current = this.currentSyntaxe.get(syntaxe);
				
				boolean isFirstWord    = beginGp == 0;

				boolean isNotFirstWord = syntaxe > 0;
				boolean isGnBefore     = false;
				if (isNotFirstWord) {
					ArrayList<String> last   = this.currentSyntaxe.get(syntaxe - 1);
					String[]	      partGn = {"Nom commun", "Adjectif", "Forme d'adjectif", "nom commun"};
					isGnBefore = listEquals(last, partGn);
				}

				
				boolean currentIsPrepo = listEqualsElement(current, "Préposition");
				
				

				boolean nextIsNc          = Conditions(1, syntaxe, "equal", "Nom commun", null);
				boolean nextIsNP          = Conditions(1, syntaxe, "equal", "Nom propre", null);
				boolean nextIsArt         = Conditions(1, syntaxe, "listEqual", "", DETERMINANTS);
				boolean nextIsVInfi       = Conditions(1, syntaxe, "equal", "verbe#", null);
				boolean nextIsAdj         = Conditions(1, syntaxe, "equal", "Adjectif", null);

				
				boolean nextX2IsNc        = Conditions(2, syntaxe, "equal", "Nom commun", null);
				boolean nextX2IsNp        = Conditions(2, syntaxe, "equal", "Nom propre", null);
				boolean nextX2IsAdj       = Conditions(2, syntaxe, "contains", "djectif", null);
				boolean nextX2IsArt    	  = Conditions(2, syntaxe, "listEqual", "", DETERMINANTS);
				boolean nextX2IsCnjnc  	  = Conditions(2, syntaxe, "equal", "Conjonction de coordination", null);

				
				boolean nextX3IsAdj       = Conditions(3, syntaxe, "contains", "djectif", null);
				boolean nextX3IsArt       = Conditions(3, syntaxe, "listEqual", "", DETERMINANTS);
				boolean nextX3IsNc        = Conditions(3, syntaxe, "equal", "Nom commun", null);
				boolean nextX3IsNp        = Conditions(3, syntaxe, "equal", "Nom propre", null);
				boolean nextX3IsPrep      = Conditions(3, syntaxe, "equal", "Préposition", null);
				
				
				boolean nextX4IsNc        = Conditions(4, syntaxe, "equal", "Nom commun", null);
				boolean nextX4IsDet       = Conditions(4, syntaxe, "listEqual", "", DETERMINANTS);
				boolean nextX4IsArt       = Conditions(3, syntaxe, "listEqual", "", DETERMINANTS);
				boolean nextX4IsNp        = Conditions(4, syntaxe, "equal", "Nom propre", null);
				
				boolean nextX5IsNc        = Conditions(5, syntaxe, "equal", "Nom commun", null);

			
				String arrow = "";
				
				
				if (isGnBefore) { arrow = "(<- Prep)"; } else { arrow = "(-> Prep)"; }
				

				if (currentIsPrepo && nextIsArt && nextX2IsNc && nextX3IsNc && nextX4IsArt && nextX5IsNc) {
					displayGroup(6, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, syntaxe + 4, syntaxe + 5,
							arrow, "(Art)", "(Nm)", "(<- Adj)", "(Art)", "(Nm)");
				}

				else if (currentIsPrepo && nextIsArt && nextX2IsAdj && nextX3IsAdj && nextX4IsArt && nextX5IsNc) {
					displayGroup(6, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, syntaxe + 4, syntaxe + 5,
							arrow, "(Art)", "(Adj)", "(<- Nm)", "(Art)", "(Nm)");
				}

				else if (currentIsPrepo && nextIsArt && nextX2IsAdj && nextX3IsNc && nextX4IsDet && nextX5IsNc) {
					displayGroup(6, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, syntaxe + 4, syntaxe + 5,
							arrow, "(Art)", "(Adj)", "(-> Nm)", "(<- Art)", "(Nm)");
				}
				

				else if (currentIsPrepo && nextIsNc && nextX2IsCnjnc && nextX3IsPrep && nextX4IsNc) {
					displayGroup(5, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, syntaxe + 4, -1,
							     arrow, "(Nc)", "(<- Cnj)", "(<- Prep)", "(Nc)", "None");
				}
				
				else if (currentIsPrepo && nextIsArt && nextX2IsNc && nextX3IsArt && nextX4IsNc) {
					displayGroup(5, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, syntaxe + 4, -1,
							arrow, "(Art)", "(<- Nc)", "(<- Art)", "(Nc)", "None");
				}
				

				else if (currentIsPrepo && nextIsArt && nextX2IsNc && nextX3IsArt && nextX4IsNp) {
					displayGroup(5, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, syntaxe + 4, -1,
							arrow, "(Art)", "(<- Nc)", "(<- Art)", "(Np)", "None");
				}

				else if (currentIsPrepo && nextIsArt && nextX2IsAdj && nextX3IsNc) {
					displayGroup(4, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, -1, -1,
							     arrow, "(Art)", "(Adj)", "(-> Nc)", "None", "None");
				}
				
				else if (currentIsPrepo && nextIsArt && nextX2IsNc && nextX3IsAdj) {
					displayGroup(4, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, -1, -1,
							     arrow, "(Art)", "(Nc <-)", "(Adj)", "(Nc)", "None");
				}
				
				
				else if (currentIsPrepo && nextIsArt && nextX2IsNc && nextX3IsAdj) {
					displayGroup(4, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, -1, -1,
							arrow, "(Art)", "(<- Nm)", "(<- Adj)", "None", "None");
				}
				else if (currentIsPrepo && nextIsNc && nextX2IsArt && nextX3IsNc) {
					displayGroup(4, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, -1, -1,
							arrow, "(Nc)", "(Art)", "(Nc)", "None", "None");
				}

				else if (currentIsPrepo && nextIsArt && nextX2IsNc && nextX3IsAdj) {
					displayGroup(4, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, -1, -1,
							arrow, "(Art)", "(Nc)", "(Adj)", "None", "None");
				}
				
				else if (isFirstWord && currentIsPrepo && nextIsArt && nextX2IsNc) {
					displayGroup(3, syntaxe, syntaxe + 1, syntaxe + 2, -1, -1, -1,
							arrow, "(Art)", "(Nc)", "None", "None", "None");
				}

				else if (currentIsPrepo && nextIsArt && nextX2IsNp && nextX3IsNp) {
					displayGroup(3, syntaxe, syntaxe + 1, syntaxe + 2, syntaxe + 3, -1, -1,
							    arrow, "(Art) ", "(Np)", "(Np)", "None", "None");
				}
				
				else if (currentIsPrepo && nextIsArt && nextX2IsNc) {
					displayGroup(3, syntaxe, syntaxe + 1, syntaxe + 2, -1, -1, -1,
							arrow, "(Art)", "(Nc)", "None", "None", "None");
				}
				
				else if (currentIsPrepo && nextIsNc && nextX2IsAdj) {
					displayGroup(3, syntaxe, syntaxe + 1, syntaxe + 2, -1, -1, -1,
							arrow, "(Nc)", "(<- Adj)", "None", "None", "None");
				}
				else if (currentIsPrepo && nextIsVInfi) {
					displayGroup(2, syntaxe, syntaxe + 1, -1, -1, -1, -1,
							arrow, "(Vrb inf)", "None", "None", "None", "None");
				}
				
				else if (currentIsPrepo && nextIsArt) {
					displayGroup(2, syntaxe, syntaxe + 1, -1, -1, -1, -1,
							arrow, "(Adj)", "None", "None", "None", "None");
				}
				
				else if (currentIsPrepo && nextIsNc) {
					displayGroup(2, syntaxe, syntaxe + 1, -1, -1, -1, -1,
							arrow, "(Nm)", "None", "None", "None", "None");
				}

				else if (currentIsPrepo && nextIsNP) {
					displayGroup(2, syntaxe, syntaxe + 1, -1, -1, -1, -1,
							arrow, "(Np)", "None", "None", "None", "None");
				}

				else if (currentIsPrepo) {
					displayGroup(1, syntaxe, -1, -1, -1, -1, -1,
							arrow, "None", "None", "None", "None", "None");
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	private void inGroupAdverbe(ArrayList<String> groupsSentence, 
			  					String functionGp, int beginGp, int endGp) {
		
		
		boolean isGroupAdverbe = functionGp.equalsIgnoreCase("GAdverb");
		
		if (isGroupAdverbe) {
			
			for (int syntaxe = beginGp; syntaxe <= endGp; syntaxe++) {

				ArrayList<String> current = this.currentSyntaxe.get(syntaxe);

				String adverbe = this.currentText.get(syntaxe);

				boolean currentIsAdverbe  = listEqualsElement(current, "Adverbe");
				
				groupAdverbial serchAdvb  = new groupAdverbial(adverbe);
				String 		   advFind 	  = serchAdvb.searchAdvbInDefineGroupe();

				
				String  advFindNext = "";
				boolean nextIsNotLessOne = syntaxe < this.currentSyntaxe.size() - 1;
				boolean nextIsAdverb   = false;
				if (nextIsNotLessOne) {
					ArrayList<String> next  	  = this.currentSyntaxe.get(syntaxe + 1);
					nextIsAdverb  				  = listEqualsElement(next, "Adverbe");
					String nextAdverbe 			  = this.currentText.get(syntaxe + 1);
					groupAdverbial serchAdvbNext  = new groupAdverbial(nextAdverbe);
					advFindNext 	  			  = serchAdvbNext.searchAdvbInDefineGroupe();
				}
				
				
				if (currentIsAdverbe && nextIsAdverb) {
					displayGroup(2, syntaxe, syntaxe + 1, -1, -1, -1, -1,
								 "(Adv " + advFind + ")", "(Adv " + advFindNext + ")", "None", "None", "None", "None");
				}
				
				else if (currentIsAdverbe) {
					displayGroup(1, syntaxe, -1, -1, -1, -1, -1,
								"(Adv " + advFind + ")", "None", "None", "None", "None", "None");
				}
			}
		}
	}
	
		
	
	
	private void inGroupRelative(ArrayList<String> groupsSentence, 
								 String functionGp, int beginGp, int endGp) {

		boolean isGroupRelative = functionGp.equalsIgnoreCase("GRel");

		if (isGroupRelative) {

			for (int syntaxe = beginGp; syntaxe <= endGp; syntaxe++) {

				boolean  lastIsNom    = false;
				boolean  notFirstWord = syntaxe - 1 > 0;
				if (notFirstWord) {
					ArrayList<String> lastSyntaxe = this.currentSyntaxe.get(syntaxe - 1);
					lastIsNom  	 		   		  = listEqualsElement(lastSyntaxe, "Nom commun");
				}

				ArrayList<String> current = this.currentSyntaxe.get(syntaxe);
				boolean currentIsPrnmRel  = listEqualsElement(current, "Pronom relatif");


				String relDepend = "";
				if (lastIsNom) { relDepend += "(<- Psr)"; }
				else           { relDepend += "(Psr)"; }


				if (currentIsPrnmRel) {displayGroup(1, syntaxe, -1, -1, -1, -1, -1, 
						relDepend, "None", "None", "None", "None", "None");}

			}
		}
	}
	
	

	
	private void inGroupConjonction(ArrayList<String> groupsSentence, 
									String functionGp, int beginGp, int endGp) {

		boolean isGroupRelative = functionGp.equalsIgnoreCase("Gcnjonc");

		if (isGroupRelative) {

			for (int syntaxe = beginGp; syntaxe <= endGp; syntaxe++) {
	
				
				String arrow1 = "(Cnjnc ->)"; String arrow2 = "(<- Cnjnc ->)"; String arrow = "";
				
				boolean isFirst = syntaxe == 0;
				if (isFirst) { arrow = arrow1; } else { arrow = arrow2; }
				
				
				ArrayList<String> current     = this.currentSyntaxe.get(syntaxe);
				boolean currentIsConjonction  = listEqualsElement(current, "Conjonction de coordination") ||
												listEqualsElement(current, "Conjonction");

				if (currentIsConjonction) {displayGroup(1, syntaxe,- 1, -1, -1, -1, -1, 
						                   arrow, "None", "None", "None", "None", "None");}

			}
		}
	}
	
	
	private void displayGroup(int number, int nb1, int nb2, int nb3, int nb4, int nb5, int nb6, 
			 				  String syntaxe1, String syntaxe2, String syntaxe3, String syntaxe4, String syntaxe5, String syntaxe6) {

		String   display = "";
		int[]    numbers = {nb1, nb2, nb3, nb4, nb5, nb6};
		String[] sytaxes = {syntaxe1, syntaxe2, syntaxe3, syntaxe4, syntaxe5, syntaxe6};

		for (int index=0; index <= number; index++) {

			boolean isEndIndex = (index == number);

			boolean syntaxeIsNone = index < sytaxes.length && sytaxes[index] == null;
			boolean numberIsNone  = index < sytaxes.length && numbers[index] == -1;
			boolean conditionNone = index < sytaxes.length && !syntaxeIsNone && !numberIsNone;
			
			if (!isEndIndex && conditionNone) { display += (sytaxes[index] + this.currentText.get(numbers[index]) + " "); }
			if ( isEndIndex && conditionNone) { display += (sytaxes[index] + this.currentText.get(numbers[index]));       }
		}

		boolean canPut = regulationOfEntriesInWORKLIST(WORKLIST, nb1, display);

		if (canPut)      { WORKLIST.put(nb1, display); }


	}


	private boolean regulationOfEntriesInWORKLIST(Map<Integer, String> WORKLIST, int syntaxe, String display) {

		boolean canAddToWorklist = true;


		int 	 beginCurrent = syntaxe;
		String[] valueCurrent = display.split("[(]");
		int      endCurrent   = beginCurrent + valueCurrent.length;


		for(Entry<Integer, String> partSentence: WORKLIST.entrySet()) {

			int      key   = partSentence.getKey();
			String[] value = partSentence.getValue().split("[(]");
			int      end   = key + value.length;

			boolean inRangeOfCurrent =  beginCurrent >= key && endCurrent <= end;


			if (inRangeOfCurrent) { return false; }

		}
		return canAddToWorklist;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private void regroupingGroupPropoSubordonée(ArrayList<String> groupsSentence) {
		
		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size() - 1; syntaxe++) {

			String[] dataCurrent  = recuperateRegroupingCurrentInfo(groupsSentence, syntaxe);
			String   function     = dataCurrent[0];
			String   begin 	      = dataCurrent[1];
			String   end   	      = dataCurrent[2];

			String[] dataNext     = recuperateRegroupingNextInfo(groupsSentence, syntaxe);
			String   nextFunction = dataNext[0];
			String   nextBegin 	  = dataNext[1];
			String   nextEnd   	  = dataNext[2];
			
			String[] dataNextX2     = recuperateRegroupingNextX2Info(groupsSentence, syntaxe);
			String   nextX2Function = dataNextX2[0];
			String   nextX2Begin 	= dataNextX2[1];
			String   nextX2End   	= dataNextX2[2];
			
			String[] indexs1 = {begin, nextX2End};
			String[] indexs2 = {begin, end};
			
			boolean currentIsGv = function.equalsIgnoreCase("GVerbal");
			boolean nextIsRel   = nextFunction.equalsIgnoreCase("GRel");
			boolean nextX2IsGv  = nextX2Function.equalsIgnoreCase("GVerbal");
			
			
			boolean currentIsREL = function.equalsIgnoreCase("GRel");
			boolean notEmpty1    = verifyIndex(indexs1);
			boolean notEmpty2    = verifyIndex(indexs2);
			
			if (currentIsGv && nextIsRel && nextX2IsGv && notEmpty1) {
				String newFunction = function + "+" + begin + "+" + nextX2End;
				groupsSentence.set(syntaxe, newFunction);
				toRemove.add(groupsSentence.get(syntaxe + 1));
				toRemove.add(groupsSentence.get(syntaxe + 2));
			}

			else if (currentIsREL && notEmpty2) {
					String newFunction = function + "+" + begin + "+" + end;
					groupsSentence.set(syntaxe, newFunction);}
			

		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	
	
	
	
	
	
	private void regroupingGroupByVerbWithVerb(ArrayList<String> groupsSentence) {

		ArrayList<String> toRemove = new ArrayList<String>();
		

		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {

			String[] dataCurrent     = recuperateRegroupingCurrentInfo(groupsSentence, syntaxe);
			String   function        = dataCurrent[0];
			String   begin 	         = dataCurrent[1];
			String   end   	         = dataCurrent[2];

			String[] dataNext        = recuperateRegroupingNextInfo(groupsSentence, syntaxe);
			String   nextFunction    = dataNext[0];
			String   nextBegin 	     = dataNext[1];
			String   nextEnd   	     = dataNext[2];
			
			String[] indexs          = {begin, nextEnd};

			boolean currentIsGverbal = function.equalsIgnoreCase("GVerbal");
			boolean nextIsGVerbal    = nextFunction.equalsIgnoreCase("GVerbal");
			boolean notEmpty         = verifyIndex(indexs);

			if (currentIsGverbal && nextIsGVerbal && notEmpty) {
				boolean isPrnm = searchPronom(nextBegin, nextEnd, currentSyntaxe);
				if (!isPrnm) {
					String newFunction = function + "+" + begin + "+" + nextEnd;
					groupsSentence.set(syntaxe, newFunction);
					toRemove.add(groupsSentence.get(syntaxe + 1));}
				}
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}

	
		


	private void regroupingGprepVerbAdv(ArrayList<String> groupsSentence) {
		
		//Regrouping Group prépositionel verbal with group adverbe.
		
		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size() - 1; syntaxe++) {


			String[] dataCurrent  = recuperateRegroupingCurrentInfo(groupsSentence, syntaxe);
			String   function     = dataCurrent[0];
			String   begin 	      = dataCurrent[1];
			String   end   	      = dataCurrent[2];

			String[] dataNext     = recuperateRegroupingNextInfo(groupsSentence, syntaxe);
			String   nextFunction = dataNext[0];
			String   nextBegin 	  = dataNext[1];
			String   nextEnd   	  = dataNext[2];

			boolean currentIsPrepositionVerb = function.equalsIgnoreCase("GPrepVerbal");
			boolean nextIsAdv                = nextFunction.equalsIgnoreCase("GAdverb");

			if (currentIsPrepositionVerb && nextIsAdv) {
				String newFunction = function + "+" + begin + "+" + nextEnd;
				groupsSentence.set(syntaxe, newFunction);
				toRemove.add(groupsSentence.get(syntaxe + 1));
			}
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}

	
	
	
	private void regroupingGroupPrepoAdvGnomi(ArrayList<String> groupsSentence) {
		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size() - 2; syntaxe++) {


			String[] dataCurrent    = recuperateRegroupingCurrentInfo(groupsSentence, syntaxe);
			String   function       = dataCurrent[0];
			String   begin 	        = dataCurrent[1];
			String   end   	        = dataCurrent[2];

			String[] dataNext       = recuperateRegroupingNextInfo(groupsSentence, syntaxe);
			String   nextFunction   = dataNext[0];
			String   nextBegin 	    = dataNext[1];
			String   nextEnd   	    = dataNext[2];
			
			String[] dataNextX2     = recuperateRegroupingNextX2Info(groupsSentence, syntaxe);
			String   nextX2Function = dataNextX2[0];
			String   nextX2Begin 	= dataNextX2[1];
			String   nextX2End   	= dataNextX2[2];
			
			String[] indexs         = {begin, nextX2End};
			
			boolean currentIsPreposition = function.equalsIgnoreCase("GPrep");
			boolean nextIsAdv            = nextFunction.equalsIgnoreCase("GAdverb");
			boolean nextX2IsGnoMinal     = nextX2Function.equalsIgnoreCase("GNominal");
			
			boolean notEmpty             = verifyIndex(indexs);
			
			if (currentIsPreposition && nextIsAdv && nextX2IsGnoMinal && notEmpty) {
				String newFunction = function + "+" + begin + "+" + nextX2End;
				groupsSentence.set(syntaxe, newFunction);
				toRemove.add(groupsSentence.get(syntaxe + 1));
				toRemove.add(groupsSentence.get(syntaxe + 2));
			}

		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}


	
	private void regroupingGroupPrepoVerbeInfinitif(ArrayList<String> groupsSentence) {
		
		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size() - 1; syntaxe++) {


			String[] dataCurrent    = recuperateRegroupingCurrentInfo(groupsSentence, syntaxe);
			String   function       = dataCurrent[0];
			String   begin 	        = dataCurrent[1];
			String   end   	        = dataCurrent[2];

			String[] dataNext       = recuperateRegroupingNextInfo(groupsSentence, syntaxe);
			String   nextFunction   = dataNext[0];
			String   nextBegin 	    = dataNext[1];
			String   nextEnd   	    = dataNext[2];
			
			String[] indexs         = {begin, nextEnd};

			
			boolean isNextBegin     = nextBegin != "";
			boolean nextIsInfinitif = false;
			
			boolean notEmpty        = verifyIndex(indexs);
			
			if (isNextBegin) {
				String   nextSyntaxe     = this.currentSyntaxe.get(Integer.parseInt(nextBegin)).get(0);
				nextIsInfinitif          = nextSyntaxe.equalsIgnoreCase("verbe#");
			}


			boolean currentIsPreposition = function.equalsIgnoreCase("GPrep");
			boolean currentIsOne         = begin.equalsIgnoreCase(end);
			boolean nextIsVerbe          = nextFunction.equalsIgnoreCase("GVerbal");
			boolean nextIsOne            = nextBegin.equalsIgnoreCase(nextEnd);


			

			if (currentIsPreposition && !currentIsOne && nextIsOne && nextIsVerbe && nextIsInfinitif && notEmpty) {
				String newFunction = function + "Verbal+" + begin + "+" + nextEnd;
				groupsSentence.set(syntaxe, newFunction);
				toRemove.add(groupsSentence.get(syntaxe + 1));
			}
			
			else if (currentIsPreposition && currentIsOne && nextIsVerbe && nextIsInfinitif && notEmpty) {
				String newFunction = function + "Verbal+" + begin + "+" + nextEnd;
				groupsSentence.set(syntaxe, newFunction);
				toRemove.add(groupsSentence.get(syntaxe + 1));
			}

		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	
	

	
	
	
	private boolean regroupingGroupByPrepositionPreposition(ArrayList<String> groupsSentence) {

		
		ArrayList<String> toRemove = new ArrayList<String>();

		for (int syntaxe = 0; syntaxe < groupsSentence.size() - 1; syntaxe++) {

			String[] dataCurrent  = recuperateRegroupingCurrentInfo(groupsSentence, syntaxe);
			String   function     = dataCurrent[0];
			String   begin 	      = dataCurrent[1];
			String   end   	      = dataCurrent[2];

			String[] dataNext     = recuperateRegroupingNextInfo(groupsSentence, syntaxe);
			String   nextFunction = dataNext[0];
			String   nextBegin 	  = dataNext[1];
			String   nextEnd   	  = dataNext[2];

			String[] indexs       = {begin, nextEnd};
			
			boolean currentIsPreposition = function.equalsIgnoreCase("GPrep");
			boolean nextIsPreposition    = nextFunction.equalsIgnoreCase("GPrep");
			boolean notEmpty             = verifyIndex(indexs);
			
			if (currentIsPreposition && nextIsPreposition && notEmpty) {
				//String newFunction = function + "+" + begin + "+" + nextEnd;
				//groupsSentence.set(syntaxe, newFunction);
				//toRemove.add(groupsSentence.get(syntaxe + 1));
			}
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
		boolean oContinuer = continu(toRemove);
		return  oContinuer;
	}
	
	
	private boolean regroupingGroupByPrepositionGn(ArrayList<String> groupsSentence) {
		
		ArrayList<String> toRemove = new ArrayList<String>();
		
		for (int syntaxe = 0; syntaxe < groupsSentence.size() - 1; syntaxe++) {

			String[] dataCurrent  = recuperateRegroupingCurrentInfo(groupsSentence, syntaxe);
			String   function     = dataCurrent[0];
			String   begin 	      = dataCurrent[1];
			String   end   	      = dataCurrent[2];

			String[] dataNext     = recuperateRegroupingNextInfo(groupsSentence, syntaxe);
			String   nextFunction = dataNext[0];
			String   nextBegin 	  = dataNext[1];
			String   nextEnd   	  = dataNext[2];
			
			String[] indexs       = {begin, nextEnd};

			boolean currentIsPreposition = function.equalsIgnoreCase("GPrep");
			boolean nextIsGroupNominal   = nextFunction.equalsIgnoreCase("GNominal");
			boolean notEmpty             = verifyIndex(indexs);
			
			if (currentIsPreposition && nextIsGroupNominal && notEmpty) {

				String newFunction = function + "+" + begin + "+" + nextEnd;
				groupsSentence.set(syntaxe, newFunction);
				toRemove.add(groupsSentence.get(syntaxe + 1));
			}
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
		boolean oContinuer = continu(toRemove);
		return  oContinuer;
	}
	
	
	
	
	private boolean verifyIndex(String[] indexs) {
		
		boolean isIndex = true;
		for (String element: indexs) {
			if (isIndex) {
				boolean isEmpty = element.equalsIgnoreCase("");
				if (isEmpty) { isIndex = false; }
			}
		}
		
		return isIndex;
	}



	


	
	
	private boolean regroupingGroupGnCnjnGn(ArrayList<String> groupsSentence) {
		
		ArrayList<String> toRemove = new ArrayList<String>();
		
		for (int syntaxe = 0; syntaxe < groupsSentence.size(); syntaxe++) {

			String[] dataCurrent    = recuperateRegroupingCurrentInfo(groupsSentence, syntaxe);
			String   function       = dataCurrent[0];
			String   begin 	        = dataCurrent[1];
			String   end   	        = dataCurrent[2];

			String[] dataNext       = recuperateRegroupingNextInfo(groupsSentence, syntaxe);
			String   nextFunction   = dataNext[0];
			String   nextBegin 	    = dataNext[1];
			String   nextEnd   	    = dataNext[2];
			
			String[] dataNextX2     = recuperateRegroupingNextX2Info(groupsSentence, syntaxe);
			String   nextX2Function = dataNextX2[0];
			String   nextX2Begin 	= dataNextX2[1];
			String   nextX2End   	= dataNextX2[2];

			String[] indexs         = {begin, end, nextX2End};
			
			boolean currentIsGn = function.equalsIgnoreCase("GNominal");
			boolean nextIsCnjnc = nextFunction.equalsIgnoreCase("Gcnjonc");
			boolean nextIsGn    = nextX2Function.equalsIgnoreCase("GNominal");
			
			boolean notEmpty             = verifyIndex(indexs);
			
			if (currentIsGn && nextIsCnjnc && nextIsGn && notEmpty) {

				String newFunction = function + "+" + begin + "+" + nextX2End;
				groupsSentence.set(syntaxe, newFunction);
				toRemove.add(groupsSentence.get(syntaxe + 1));
				toRemove.add(groupsSentence.get(syntaxe + 2));
			}
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
		boolean oContinuer = continu(toRemove);
		return  oContinuer;
	}
	
	
	
	private boolean regroupingGroupByVerbAdverb(ArrayList<String> groupsSentence) {

		ArrayList<String> toRemove = new ArrayList<String>();
		
		for (int syntaxe = 0; syntaxe < groupsSentence.size() - 1; syntaxe++) {



			String[] dataCurrent    = recuperateRegroupingCurrentInfo(groupsSentence, syntaxe);
			String   function       = dataCurrent[0];
			String   begin 	        = dataCurrent[1];
			String   end   	        = dataCurrent[2];

			String[] dataNext       = recuperateRegroupingNextInfo(groupsSentence, syntaxe);
			String   nextFunction   = dataNext[0];
			String   nextBegin 	    = dataNext[1];
			String   nextEnd   	    = dataNext[2];
			
			String[] indexs         = {begin, nextEnd};

			boolean currentIsVerb   = function.equalsIgnoreCase("GVerbal");
			boolean nextIsAdverb    = nextFunction.equalsIgnoreCase("GAdverb");

			boolean currentIsAdverb = function.equalsIgnoreCase("GAdverb");
			boolean nextIsVerb      = nextFunction.equalsIgnoreCase("GVerbal");

			boolean notEmpty        = verifyIndex(indexs);
			
			if (currentIsVerb && nextIsAdverb && notEmpty) {
				String newFunction = function + "+" + begin + "+" + nextEnd;
				groupsSentence.set(syntaxe, newFunction);
				toRemove.add(groupsSentence.get(syntaxe + 1));
			}
			
			else if (currentIsAdverb && nextIsVerb && notEmpty) {
				String newFunction = nextFunction + "+" + begin + "+" + nextEnd;
				groupsSentence.set(syntaxe, newFunction);
				toRemove.add(groupsSentence.get(syntaxe + 1));
			}
			
			
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
		
		boolean oContinuer = continu(toRemove);
		return  oContinuer;
	}



	private void regroupingGroupPrepoCnjnPrepo(ArrayList<String> groupsSentence) {

		ArrayList<String> toRemove = new ArrayList<String>();
		System.out.println(groupsSentence);
		for (int syntaxe = 0; syntaxe < groupsSentence.size() - 1; syntaxe++) {

			String[] dataCurrent    = recuperateRegroupingCurrentInfo(groupsSentence, syntaxe);
			String   function       = dataCurrent[0];
			String   begin 	        = dataCurrent[1];
			String   end   	        = dataCurrent[2];

			String[] dataNext       = recuperateRegroupingNextInfo(groupsSentence, syntaxe);
			String   nextFunction   = dataNext[0];
			String   nextBegin 	    = dataNext[1];
			String   nextEnd   	    = dataNext[2];
			
		
			String[] dataNextX2     = recuperateRegroupingNextX2Info(groupsSentence, syntaxe);
			String   nextX2Function = dataNextX2[0];
			String   nextX2Begin 	= dataNextX2[1];
			String   nextX2End   	= dataNextX2[2];
			
			String[] dataNextX3     = recuperateRegroupingNextX3Info(groupsSentence, syntaxe);
			String   nextX3Function = dataNextX3[0];
			String   nextX3Begin 	= dataNextX3[1];
			String   nextX3End   	= dataNextX3[2];

			String[] indexs1        = {begin, nextX2End};
			String[] indexs2        = {begin, nextEnd};
			
			
			boolean nextIsConjonction    = nextFunction.equalsIgnoreCase("Gcnjonc");
			boolean currentIsEqualNextX2 = function.equalsIgnoreCase(nextX2Function);
			
			boolean nextX2Gnominal  = nextX2Function.equalsIgnoreCase("GNominal");
			boolean nextX3Verb      = nextX3Function.equalsIgnoreCase("GVERBAL");

			boolean notEmpty1       = verifyIndex(indexs1);
			boolean notEmpty2       = verifyIndex(indexs2);

			if (nextIsConjonction && currentIsEqualNextX2 && !(nextX2Gnominal && nextX3Verb) && notEmpty1) {

				//String newFunction = function + "+" + begin + "+" + nextX2End;
				//groupsSentence.set(syntaxe, newFunction);
				//toRemove.add(groupsSentence.get(syntaxe + 1));
				//toRemove.add(groupsSentence.get(syntaxe + 2));
			}
			
			if (nextIsConjonction && currentIsEqualNextX2 && (nextX2Gnominal && nextX3Verb) && notEmpty2) {
				//String newFunction = function + "+" + begin + "+" + nextEnd;
				//groupsSentence.set(syntaxe, newFunction);
				//toRemove.add(groupsSentence.get(syntaxe + 1));
			}
			
		}
		for (String elementToRemove: toRemove) {groupsSentence.remove(elementToRemove);}
	}
	
	
	


	private boolean continu(ArrayList<String> toRemove) {
		
		boolean out = false;
		
		boolean lengthToRemove = toRemove.size() > 1;
		if (lengthToRemove) { out = true; }
		
		return out;
	}

	
	private String[] recuperateRegroupingCurrentInfo(ArrayList<String> groupsSentence, int syntaxe) {

		String   function = "";
		String   begin 	  = "";
		String   end   	  = "";
		String[] current  = groupsSentence.get(syntaxe).split("[+]");
		boolean  currentIsNotEmpty = current.length > 1;
		if (currentIsNotEmpty) {
			function  = current[0];
			begin 	  = current[1];
			end   	  = current[2];
		}
			
		String[] data = {function, begin, end};
		return data;
	}
	
	private String[] recuperateRegroupingNextInfo(ArrayList<String> groupsSentence, int syntaxe) {
		
		
		String   nextFunction = "";
		String   nextbegin 	  = "";
		String   nextEnd   	  = "";

		boolean  isLessOneLength = syntaxe < groupsSentence.size() - 1;

		if (isLessOneLength) {
			String[] next  = groupsSentence.get(syntaxe + 1).split("[+]");
			boolean  nextNotEmpty   = next.length > 1;
			if (nextNotEmpty) {
				nextFunction  = next[0];
				nextbegin 	  = next[1];
				nextEnd   	  = next[2];
			}
		}
		
		String[] data = {nextFunction, nextbegin, nextEnd};
		return data;
	}
	
	
	private String[] recuperateRegroupingNextX2Info(ArrayList<String> groupsSentence, int syntaxe) {
		
	
		String   nextX2Function   = "";
		String   nextX2Begin 	  = "";
		String   nextX2End   	  = "";
		
		boolean isLessLengthX2 = syntaxe < groupsSentence.size() - 2;
		if (isLessLengthX2) {
			String[] nextX2           = groupsSentence.get(syntaxe + 2).split("[+]");
			boolean  nextX2IsNotEmpty = nextX2.length > 1;
			if (nextX2IsNotEmpty) {
				nextX2Function = nextX2[0];
				nextX2Begin    = nextX2[1];
				nextX2End      = nextX2[2];
			}
		}
		String[] data = {nextX2Function, nextX2Begin, nextX2End};
		return data;
	}
	
	private String[] recuperateRegroupingNextX3Info(ArrayList<String> groupsSentence, int syntaxe) {
		
		
		String   nextX3Function   = "";
		String   nextX3Begin 	  = "";
		String   nextX3End   	  = "";
		
		boolean isLessLengthX3 = syntaxe < groupsSentence.size() - 3;
		if (isLessLengthX3) {
			String[] nextX3           = groupsSentence.get(syntaxe + 3).split("[+]");
			boolean  nextX3IsNotEmpty = nextX3.length > 1;
			if (nextX3IsNotEmpty) {
				nextX3Function = nextX3[0];
				nextX3Begin    = nextX3[1];
				nextX3End      = nextX3[2];
			}
		}
		String[] data = {nextX3Function, nextX3Begin, nextX3End};
		return data;
	}
	
	
	
	
	

	private String[] treatElement(String currentGroup, ArrayList<String> groupsSentence, int group) {

		int lengthGroupString = currentGroup.length();
		String groupTreat     = groupsSentence.get(group).substring(1, lengthGroupString - 1);

		String[] groupCompose = groupTreat.split("[+]");
		String   begin 		  = groupCompose[1];
		String   end 	      = groupCompose[2];
		String   element 	  = groupCompose[0];
		
		String[] informations = {begin, end, element};
		
		return informations;
		
	}

	private void recuperateGroup(ArrayList<String> groups) {

		ArrayList<String> groupListGn = new ArrayList<String>();
		recuperateIndexDataOne(this.groupNominalFunctionBeginIndex, "groupNominal");
		placeGroup(this.groupNominalFunctionBeginIndex, "GNominal", true, false, groupListGn, groups);

		System.out.println(this.groupsVerbalsFunctionBeginIndex);
		System.out.println(this.pronomToGNBeginIndex);
		
		ArrayList<String> groupListGv = new ArrayList<String>();
		recuperateIndexDataOne(this.groupsVerbalsFunctionBeginIndex, "groupVerbal");
		placeGroup(this.groupsVerbalsFunctionBeginIndex, "GVerbal", true, false, groupListGv, groups);

		ArrayList<String> groupListAdverb = new ArrayList<String>();
		recuperateIndexDataTwo(this.adverbeFunction, "GroupAdverbial");
		placeGroup(this.adverbeFunction, "GAdverb", false, true, groupListAdverb, groups);

		ArrayList<String> groupListRel = new ArrayList<String>();
		recuperateIndexDataTwo(this.propositionRelativeFunction, "gropuPropoRelative");
		placeGroup(this.propositionRelativeFunction, "GRel", false, true, groupListRel, groups);

		ArrayList<String> groupListPrep = new ArrayList<String>();
		recuperateIndexDataTwo(this.prepositionFunction, "groupePrepo");
		placeGroup(this.prepositionFunction, "GPrep", false, true, groupListPrep, groups);

		ArrayList<String> groupListCnjnc = new ArrayList<String>();
		recuperateIndexDataTwo(this.conjonctionLocalisation, "Conjonction");
		placeGroup(this.conjonctionLocalisation, "Gcnjonc", false, true, groupListCnjnc, groups);

		for (int index=0; index < 5; index++) {System.out.println("");}
		
	}

	private void placeGroup(Map<Integer, String> dicoIndex, String group, boolean mode1, boolean mode2, 
			 			 	ArrayList<String> groupListDisplay, ArrayList<String> groupsSyntaxe) {

		int lengthSentence = this.currentText.size();
		for (int index=0; index < lengthSentence; index++) {groupListDisplay.add(Integer.toString(index));}

		for(Entry<Integer, String> groups: dicoIndex.entrySet()) {
			
			ArrayList<String> container = new ArrayList<String>();

			int		indexBegening  = groups.getKey();
			int	 	length   	   = 0;

			if (mode1) {length = recupDateOne(groups, container);}
			if (mode2) {length = recupDataTwo(groups, container);}

			int 	indexEnd = indexBegening + length;
			boolean inRange  = indexEnd < currentSyntaxe.size() && indexBegening > -1;
			if (inRange) {
				String display = group + "+" +Integer.toString(indexBegening)+ "+" +Integer.toString(indexEnd);
				for (int index=indexBegening; index <= indexEnd; index++) {groupListDisplay.set(index, display);}
				for (int index=indexBegening; index <= indexEnd; index++) {groupsSyntaxe.set(index, display);}
			}
		}

		String lastElement = "";
		Iterator<String> i = groupListDisplay.iterator();

		while (i.hasNext()) {
			String str = (String) i.next();

			boolean elementIsTirate = str.equalsIgnoreCase("_");
			boolean isEgalLast      = str.equalsIgnoreCase(lastElement);
			if (!elementIsTirate) {lastElement = str;}
			if (isEgalLast)		  {i.remove();}
		}
		System.out.println(group);
		System.out.println(groupListDisplay + "\n\n");
	}
	
	private void recuperateIndexDataTwo(Map<Integer, String> dicoIndex, String reference) {
		
		for(Entry<Integer, String> groups: dicoIndex.entrySet()) {

			ArrayList<String> container = new ArrayList<String>();

			int      indexGroup     = groups.getKey();

			recupDataTwo(groups, container);
			verifyConcordanceWithText(container, indexGroup, 0, reference);
		}
	}


	public void recuperateIndexDataOne(Map<Integer, String> dicoIndex, String reference) {
		

		for(Entry<Integer, String> groups: dicoIndex.entrySet()) {

			ArrayList<String> container = new ArrayList<String>();

			int      indexGroup = groups.getKey();
			int      length     = recupDateOne(groups, container);

			verifyConcordanceWithText(container, indexGroup, length, reference);
		}
	}
	


	private int recupDateOne(Entry<Integer, String> groups, ArrayList<String> container) {

		int      indexGroup = groups.getKey();
		String   value      = groups.getValue();
		String[] text       = value.split("/");
		int      length     = -1;

		for (String elementText: text) {if (elementText.length() > 1) {length += 1; container.add(elementText.split("=")[1]);}}
		
		return length;
	}
	

	
	private int recupDataTwo(Entry<Integer, String> groups, ArrayList<String> container) {

		int      indexGroup     = groups.getKey();
		String   value          = groups.getValue();
		String   text           = this.currentText.get(indexGroup);

		container.add(text);

		return 0;
	}
	
	


	private void verifyConcordanceWithText(ArrayList<String> container, int indexGroup, int length, String reference) {


		
		int    indexEnd = indexGroup + length;

		boolean lengthInf           = indexGroup < currentText.size() && indexEnd < currentText.size() && indexGroup > -1;
		boolean containerIsNotEmpty = container.size() > 0;
		
		if (containerIsNotEmpty && lengthInf) {

			String firstWordContainer = container.get(0);
			String lastWordContainer  = container.get(length);

			String verifTextFirst 	  = this.currentText.get(indexGroup);
			String verifTextLast  	  = this.currentText.get(indexEnd);
	
			boolean firstEquals 	  = firstWordContainer.equalsIgnoreCase(verifTextFirst);
			boolean lastEquals  	  = lastWordContainer.equalsIgnoreCase(verifTextLast);
	
			if ( firstEquals &&  lastEquals) {}
			if (!firstEquals || !lastEquals) {
				
				
				System.out.println("ERRREUR D'INDEXXXXXXXX define Groups : " + reference);
				System.out.println("index debut : " + indexGroup + " " + "index de fin : " + indexEnd + "\n");
				System.out.println("la liste: " + container);
				System.out.println("mot debut group : ======> " + firstWordContainer + " " + "mot de fin group : =====> " + lastWordContainer);
				System.out.println("mot debut text : ======> "  + verifTextFirst + " "     + "mot de fin : ======> "      + verifTextLast);
				System.out.println("");
			}
		}
		else {System.out.println(indexGroup);}
	}
	
	
	
	
	
	
	
	
	
	
	
	private void writtingText(String writting1, String writting2) throws IOException {

		File file = new File(SAVEPATH + "analyseSentence.txt");
		if(!file.exists()) { file.createNewFile(); }


		String sentence = ""; for (String element: currentText) { sentence += (element + " "); }
		
		FileWriter     writer = new FileWriter(file, true);
		
		writer.write("\n");
		writer.write("\n");
		writer.write("\n");
		writer.write(sentence);
		writer.write("\n");
		writer.write(writting1);
		writer.write("\n");
		writer.write(writting2);
		writer.close();
	}

	
	
	
	
	
	
}





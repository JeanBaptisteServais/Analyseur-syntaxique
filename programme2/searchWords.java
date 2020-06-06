package fr.jbaw.programme2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.jbaw.programme2.actionTreatment.actionTreatment;
import fr.jbaw.programme2.analysingWho.analysingWho;
import fr.jbaw.programme2.complementAction.complementAction;
import fr.jbaw.programme2.complementTreatement.complementTreatement;
import fr.jbaw.programme2.discoursTreatment.discoursTreatment;
import fr.jbaw.programme2.groupNominalTreatment.isGroupeNominal;
import fr.jbaw.programme2.haveIncrementedByGprepPrep.haveIncrementedByGprepPrep;
import fr.jbaw.programme2.identifiateDeterminant.identifiateDeterminant;
import fr.jbaw.programme2.identifiatePronomPersonnel.identifiatePronomPersonnel;
import fr.jbaw.programme2.identifiateSubject.identifiateSubject;
import fr.jbaw.programme2.identifiateVerbOfGroupNominal.verbOfGn;
import fr.jbaw.programme2.identificationOfAction.identificationOfAction;
import fr.jbaw.programme2.identificationOfSubjectWithoutVerb.identificationOfSubjectOfVerb;
import fr.jbaw.programme2.identificationPronomReflechis.pronomReflechis;
import fr.jbaw.programme2.incrementAction.incrementAction;
import fr.jbaw.programme2.incrementSaveGnFromGroups.incrementSaveGnFromGroups;
import fr.jbaw.programme2.incrementSubject.incrementSubject;
import fr.jbaw.programme2.isGroupRelative.isGroupRelative;
import fr.jbaw.programme2.localisationByPrepositionAdverbe.localisationByPrepoAdverbe;
import fr.jbaw.programme2.makePrecisionOnSubject.makePrecisionOnSubject;
import fr.jbaw.programme2.prepositionTreatment.prepositionTreatment;
import fr.jbaw.programme2.priorityOfSubject.prioritySubject;
import fr.jbaw.programme2.searchWho.searchWho;



public class searchWords extends searchWordUtils{

	

	private String[] PRONOM = {"je", "tu", "il", "nous", "vous", "ils", "elle", "on", "elles",
							   "Pronom personnel", "me", "te", "se", "lui",
							   "moi", "toi", "soi", "leur", "eux", "contraction=que", "contraction=se",
							   "contraction=me", "contraction=te"};
	
	private String[] DETERMINANTS = {"Article indéfini", "Article défini", "Article défini numéral",
									 "Pronom indéfini", "Forme de pronom indéfini", 
									 "pronom défini",
									 "Adjectif indéfini", "Adjectif possessif", "Adjectif démonstratif",
									 "Forme d'Adjectif possessif", "Forme d'Adjectif démonstratif",
								     "déterminants interrogatifs", 
									 "Forme d'article défini", "Forme d'article indéfini",
									 "Forme d’Adjectif possessif", "Forme d’Adjectif démonstratif",
									 "Forme d’article défini", "Forme d’article indéfini", "Adjectif interrogatif",
									 "Adjectif exclamatif", "Forme d’adjectif", "Article défini",
									 "Forme d’article défini", "Adjectif numéral"};
	
	

	private Map<Integer, String>		 groupNominalFunctionBeginIndex;  // Groups nominals indexs in sentence.
	private Map<Integer, String>		 groupsVerbalsFunctionBeginIndex; // Groups verbals indexs in sentece.
	private Map<Integer, String>		 pronomToGNBeginIndex;			  // other Information groups verbals.
	private Map<Integer, String>		 adverbeFunction;				  // Group adverbials indexs.
	private Map<Integer, String>		 propositionRelativeFunction;	  // Proposition relative indexs.
	private Map<Integer, String> 		 prepositionFunction;		      // Group prepositioel indexs.
	private Map<Integer, String> 		 conjonctionLocalisation;		  // Group adverbials indexs.
	private ArrayList<String>            currentText;              		  // Current sentence text.
	private ArrayList<ArrayList<String>> currentSyntaxe;				  // Current syntaxe of text.
	private ConjonctionDeCoordination    conjonction;					  // Conjonction indexs.
	private ArrayList<String>            original;						  //
	private ArrayList<String> 			 GROUPS;						  //
	private Map<String, String>		 	 GROUPEINFORMATIONS;			  //

	private Map<String,  ArrayList<String>> qui;						  // Subject of proposition (verbs) from sentence.
	private Map<Integer, ArrayList<String>> quand;						  //
	private Map<String,  ArrayList<String>> quoi;						  // Verbs + complement of sentence (propositions).
	private Map<String, ArrayList<String>> discours;					  // Save discours (dialogue).
	private Map<String, ArrayList<String>> details;						  //
	private Map<String,  ArrayList<ArrayList<String>>> ou;				  //
	private int 						 numberSentence;				  // Number of the sentence of the text.


	private ArrayList<ArrayList<ArrayList<String>>> saveSyntaxe;
	private ArrayList<ArrayList<String>> 		    saveSchema;
	private ArrayList<ArrayList<String>> 			saveText;


	private ArrayList<ArrayList<String>> saveCod;
	private ArrayList<ArrayList<String>> saveVerb;
	private ArrayList<ArrayList<String>> saveGn;
	private ArrayList<ArrayList<String>> inSearchingGnPronom;
	private ArrayList<ArrayList<String>> allGns;
	private ArrayList<ArrayList<String>> utilsInformation;
	private ArrayList<ArrayList<String>> saveGardeCmplt;
	private ArrayList<ArrayList<String>> prepAdv;



	public searchWords(Map<Integer, String>        				 groupNominalFunctionBeginIndex, 
					   Map<Integer, String>        				 groupsVerbalsFunctionBeginIndex, 
					   Map<Integer, String>       				 pronomToGNBeginIndex, 
					   Map<Integer, String>        				 adverbeFunction,
					   Map<Integer, String>        				 propositionRelativeFunction,
					   Map<Integer, String>         			 prepositionFunction, 
					   Map<Integer, String>         			 conjonctionLocalisation, 
					   ArrayList<ArrayList<String>> 			 currentSyntaxe, 
					   ArrayList<String>            			 currentText,
					   ArrayList<String>           			     original,		//aaaaaaaaaaaaaaaaaaaah quel horreur !
					   ConjonctionDeCoordination    			 conjonction,			
					   Map<String, ArrayList<String>> 			 quoi, 
					   Map<String, ArrayList<ArrayList<String>>> ou,
					   Map<Integer, ArrayList<String>> 			 quand, 
					   Map<String, ArrayList<String>>  			 qui,
					   int                         				 numberSentence,
					   Map<String, String> 		 				 GROUPEINFORMATIONS, 
					   ArrayList<String> 						 GROUPS, 
					   ArrayList<ArrayList<ArrayList<String>>>   saveSyntaxe, 
					   ArrayList<ArrayList<String>> 			 saveSchema, 
					   ArrayList<ArrayList<String>> 			 saveText, 
					   Map<String, ArrayList<String>>			 discours, 
					   Map<String, ArrayList<String>>			 details, 
					   ArrayList<ArrayList<String>>				 saveCod, 
					   ArrayList<ArrayList<String>>				 saveVerb, 
					   ArrayList<ArrayList<String>>				 saveGn, 
					   ArrayList<ArrayList<String>>				 inSearchingGnPronom,
					   ArrayList<ArrayList<String>>				 allGns, 
					   ArrayList<ArrayList<String>>				 utilsInformation,
					   ArrayList<ArrayList<String>>				 saveGardeCmplt,
					   ArrayList<ArrayList<String>>				 prepAdv) {
		
		
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
		this.conjonction                     = conjonction;
		this.qui 							 = qui;
		this.quoi 							 = quoi;
		this.ou 							 = ou;
		this.quand 							 = quand;
		this.discours                        = discours;
		this.details                         = details;
		this.saveCod                         = saveCod;
		this.saveVerb                        = saveVerb;
		this.saveGn                          = saveGn;
		this.inSearchingGnPronom             = inSearchingGnPronom;
		this.allGns                          = allGns;
		this.utilsInformation                = utilsInformation;
		this.saveGardeCmplt                  = saveGardeCmplt;
		this.prepAdv                         = prepAdv;
		this.numberSentence                  = numberSentence;
		this.GROUPEINFORMATIONS              = GROUPEINFORMATIONS;
		this.GROUPS                     	 = GROUPS;
		this.saveSyntaxe                     = saveSyntaxe;
		this.saveSchema                      = saveSchema;
		this.saveText                        = saveText;


		System.out.println("--------------------------------------------------------------------\n\n\n");

		//--------------------------------------------------------------
		//Sort groups syntaxics in the sentence.
		ArrayList<String> groupsSyntaxeSorted = new ArrayList<String>();
		sortDictionnary(groupsSyntaxeSorted, GROUPEINFORMATIONS);
	
		//--------------------------------------------------------------
		saveTheCurrentData(saveSyntaxe, saveSchema, saveText, groupsSyntaxeSorted, currentSyntaxe, currentText);

		//--------------------------------------------------------------
		treatContractionCurrentText(currentText);
		
		//--------------------------------------------------------------
		//Cut sentence in proposition constituate by verbe.
		ArrayList<ArrayList<String>> partOfSentence = new ArrayList<ArrayList<String>>();
		cutSentenceInGroupVerbal(groupsSyntaxeSorted, partOfSentence);
		System.out.println(partOfSentence);

		
		//---------------------------------------------------------------
		//Avoid error put an empty Gn.
		boolean partSempty = partOfSentence.get(0).size() == 0;
		if (partSempty) { partOfSentence.get(0).add("0+0+GNOMINAL"); }
		
		
		//---------------------------------------------------------------
		//Treatement of the discours
		ArrayList<String> containerDiscours = new ArrayList<String>();
		discoursTreatment discoursTreatment = new discoursTreatment(discours, numberSentence, currentText);
		discoursTreatment.isGuillemet(containerDiscours); 
		

		//We have cut sentence by proposition around the verb.
		//We need to save them for recuperate the subject in saveSentence.
		ArrayList<ArrayList<String>> saveSentence = new ArrayList<ArrayList<String>>();
		
		
		int     inSentence                 = 0;			//Number of the proposition
		boolean conjonctionPreposition     = false;		//for example: je mange en chantant et. je mange en chantant. 
		boolean haveIncrementedByGprepPrep = false;     // 


		for (ArrayList<String> groupsSyntaxe:  partOfSentence) {
			
			displaying();
			
			//treating data from last relevant of group nominal for example.
			//we recuperate indexs of groups and their signification.
			//the goal is to localise and associate all subject to verbe.
			
																				   // ojd, le chat joue aux dés en bois
			ArrayList<String> containerDataGroupVerbal  = new ArrayList<String>(); // joue
			ArrayList<String> containerDataGroupCmplt   = new ArrayList<String>(); // aux dé
			ArrayList<String> containerDataGroupSujet   = new ArrayList<String>(); // le chat
			ArrayList<String> containerDataGroupNominal = new ArrayList<String>(); // bois
			ArrayList<String> containerDataAdverbe      = new ArrayList<String>(); // aujourd'hui
			ArrayList<String> containerPrepo            = new ArrayList<String>(); // en boi

			//group 1                   group2
			//[0+1+gverbal, 2+5+cmplt] [6+10+gverbal]
			for(int index=0; index < groupsSyntaxe.size(); index++) {
	
				
				//recuperate and treat information about the schema from the syntaxe.
				String   groups        = groupsSyntaxe.get(index);
				String[] key      	   = groups.split("[+]");

				int      begening 	   = Integer.parseInt(key[0]);
				int      end      	   = Integer.parseInt(key[1]);
				String   groupFunction = key[2];

				//catch the schema
				boolean isGroupSujet     = groupFunction.equalsIgnoreCase("GSUJET");
				boolean isGroupVerbal    = groupFunction.equalsIgnoreCase("GVERBAL");
				boolean isComplement     = groupFunction.equalsIgnoreCase("GCMPLT");
				boolean isGroupNominal   = groupFunction.equalsIgnoreCase("GNOMINAL");
				boolean isGroupPreposi   = groupFunction.equalsIgnoreCase("GPREP");
				boolean isGroupPrepSUB   = groupFunction.equalsIgnoreCase("GPREPOFSUJET");
				
				//recuperate data from group analysis (group nominal for example).
				//for localise information of time, localiation and subject.
				//for example index 2 in sentence's pronom il. index 3 is verbe conjuguate to il
				//group verbal is to index 0 to 3.
				if      (isGroupSujet) 	   { isGroupSujet(begening, end, containerDataGroupSujet); }
				else if (isGroupVerbal)    { isGroupeVerbal(begening, end, containerDataGroupVerbal);   }
				else if (isComplement) 	   { isComplement(begening, end, containerDataGroupCmplt, groupsSyntaxe, partOfSentence); 							    }
				else if (isGroupNominal)   { GroupeNominal(begening, end, containerDataGroupNominal); }
				else if (isGroupPrepSUB)   { isGroupePreposi2(begening, end, containerDataGroupSujet);  }
				else if (isGroupPreposi)   { isGroupePreposi(begening, end, containerDataGroupNominal, 
															 groupsSyntaxe, index, containerPrepo, inSentence);     }

				
				//permet to recuperate subject of relativ group as: [gverbal cmplt rel] [gverbal] where cmplt is subject.
				GroupRelative(begening, end, containerDataGroupCmplt, containerDataGroupSujet,
						GROUPS, groupsSyntaxeSorted, groupsSyntaxe, partOfSentence);

				if (!haveIncrementedByGprepPrep) {
					haveIncrementedByGprepPrep = isGroupPrepPrep(begening, end, containerDataGroupSujet, partOfSentence);
				}

				//jean mange et le vent, souffle. <-- le vent qui souffle.
				if (!conjonctionPreposition && isGroupPreposi) {
					conjonctionPreposition = conditionAddingPreposition(groupsSyntaxe, index, GROUPS);
				}
				
			}

			isGroupAdverbe(containerDataAdverbe);

			System.out.println("");

			//from last treatment we put quoi= qui=. for catch informations. remove empty data.
			treatementOfContainers(containerDataGroupVerbal);
			treatementOfContainers(containerDataGroupCmplt);
			treatementOfContainers(containerDataGroupSujet);
			treatementOfContainers(containerDataGroupNominal);
			treatementOfContainers(containerDataAdverbe);

			//we dont want data from dicours. removing all contenair who's contains information of disours.
			censureByDiscours(containerDiscours, containerDataGroupVerbal, containerDataGroupCmplt, containerDataGroupSujet,
					containerDataGroupNominal, containerDataAdverbe, containerPrepo);

			
			System.out.println("container sujet :"    + containerDataGroupSujet);
			System.out.println("container verbal :"   + containerDataGroupVerbal);
			System.out.println("container cmplt :"    + containerDataGroupCmplt);
			System.out.println("container nominal :"  + containerDataGroupNominal);
			System.out.println("container adverbe :"  + containerDataAdverbe);
			System.out.println("container prepo : "   + containerPrepo);
			System.out.println("container discours :" + containerDiscours);

			System.out.println("");
			System.out.println("");



			//arraylist for increment qui quoi ou quand.
			ArrayList<String>  dataSubject 	    = new ArrayList<String>(); //subject (nom commun)
			ArrayList<String>  dataPreciSubject = new ArrayList<String>(); //precision on subject (adjectif/possession / genre)
			ArrayList<String>  dataOtherSubject = new ArrayList<String>(); //other subject (prnm reflechi)
			ArrayList<String>  dataAction       = new ArrayList<String>(); //verb
			ArrayList<String>  dataActionCmplt  = new ArrayList<String>(); //cmplt from cmplt schema
			ArrayList<String>  dataActionGn     = new ArrayList<String>(); //cmplt from gn    schema
			ArrayList<String>  dataTime         = new ArrayList<String>(); //time verbe.
			ArrayList<String>  dataCmplt        = new ArrayList<String>(); //
			Map<int[], String> cmpltDico 		= new HashMap<>();         // indexs of cmplt, gn and gprep for localie prepoition and adverb.


			//From subject or verbal group identifiate subject of the verbe.

			//Container subject and container verbal are for subject. DataSubject is the recepteur of subject. 
			//SaveVerb sauvegarde all verbe of the current and past sentences.
			//Part of sentence's proposition. inSentence number of the proposition in sentence.
			//groupsSyntaxeSorted schema syntaxic of the sentence.
			boolean incrementedFromSameSentence = 
			identifiateSubject(containerDataGroupSujet, containerDataGroupVerbal, dataSubject, saveVerb, partOfSentence,
							  inSentence, groupsSyntaxeSorted);

			
			//Identification of the genre complement of personn.
			//Search them from container verbal treat in the past. Search other designation of subject.
			//as: je lui parle.
			identifiateCmpltPerson(containerDataGroupVerbal, dataOtherSubject);
			
			
			//associate reflechi to origin.
			boolean[] incrementation     = identifiateCmpltRefl(containerDataGroupVerbal, dataSubject, dataAction);
			boolean   saveGnIncremented1 = incrementation[0];
			boolean   saveGnIncremented2 = incrementation[1];
			
			
			
			//recuperate genre of peronal pronom for make rapproach with subject.
			identifiatePronomsPersonnel(containerDataGroupVerbal, dataSubject, dataCmplt, dataAction);
		

			//recuperate genre of determinant. for have precision on subject and make rapproach
			//with pronom.
			identifiateTypeOfDeterminant(containerDataGroupSujet, dataPreciSubject, "detPreci");
			
			
			//
			identifiateTypeOfPronom(containerDataGroupVerbal, dataPreciSubject);
			

			//recuperate genre of subject, declare possession and precision from adjective.
			//it can give information on relation beetween two subjects and make a rapproachement of an unknow subject. 
			identifiateInformationsOnSubject(containerDataGroupCmplt, containerDataGroupSujet, containerDataGroupVerbal, 
											 dataPreciSubject, containerDataGroupNominal);

			
			//recuperate information about verbal group like pronom, verbe, reflexive pronom... 
			identifiateAction(containerDataGroupVerbal, dataAction, dataSubject, saveSentence, partOfSentence);



			//
			identifiateCmpltAction(dataActionCmplt, dataAction, partOfSentence, inSentence, cmpltDico);
			if (dataActionCmplt.size() == 0) { saveGardeCmplt.add(new ArrayList<String>()); }
			else 							 { saveGardeCmplt.add(dataActionCmplt); }

			
			
			//
			identifiateNonePronomOrSubject(containerDataGroupVerbal);
			

			//recuperate pronom as le la le un une en and identifiate main objet
			identifiateCmpltCod(containerDataGroupVerbal, dataActionCmplt);
			

			
			//Have verbe but no subject. search from last or next nominal group.
			boolean[] identificationSub = 
			identifiateSubjectToVerb(dataSubject, dataAction, containerDataGroupVerbal,partOfSentence, 
								     inSentence, saveGardeCmplt, prepAdv, qui, numberSentence);

			//for recuperate subject of unknow verbe we need to save information of nominal group.
			//from identifiatesub we increment directly. conditions are for that.
			boolean   verifySubject                   = identificationSub[0];
			boolean   haveRecupPronomFromLastSentence = identificationSub[1]; //Recuperate last adv/prep
			boolean   canNotBeTheMainSubject          = identificationSub[2];
			boolean   needPriorityLastGnPartOfGnx2    = identificationSub[3];

			//we got a verbe without pronom and subject. try to earch in futur an eventual pronom who can matching.
			if (needPriorityLastGnPartOfGnx2) { utilsInformation.get(utilsInformation.size() - 1).add("priority=" + dataSubject.get(0));}
			else 							  { utilsInformation.add(new ArrayList<String>()); }


			//
			identifiateSubjectOrDetailsOfSentence(verifySubject, dataSubject, saveGn); 


			//Save group verbal for eventual group nominal who depend of it.
			incrementSaveVerb();
			

			//save group nominal for evantual group verbal without subject.
			boolean notInDiscours = discours.get("true") == null && discours.get(Integer.toString(numberSentence)) == null;
			boolean canIncrmentGn = !saveGnIncremented1 && !saveGnIncremented2 && !incrementedFromSameSentence && 
									notInDiscours && !verifySubject && !needPriorityLastGnPartOfGnx2;

			
			if (canIncrmentGn) { incrementSaveGnFromGroupGnOrSubject(dataSubject); }
			
	
			//
			identifiateTime(containerDataGroupVerbal, containerDataAdverbe, dataTime);


			//Have group nominal without action.
			boolean[] gnIsPropoOfLastSentence = identifiateVerbOfGroupNominal(dataSubject, dataAction, 
												containerDataGroupNominal, saveGn, saveVerb, dataActionCmplt, saveGardeCmplt);

			//
			boolean needIncrementSentence = gnIsPropoOfLastSentence[0];
			boolean otherSubjectIsAfter   = identifiateOtherSubject(dataSubject, dataOtherSubject, dataPreciSubject);


			//
			makePrecisionOnSubjectIfNoPrecision(dataSubject, dataPreciSubject);


			prepAdv.add(new ArrayList<String>());			
			boolean isDataActionCmplt = dataActionCmplt.size() > 0 && !(dataActionCmplt.get(0).equals(""));
			boolean isDataAction      = dataAction.size() > 0      && !(dataAction.get(0).equals(""));
			boolean isGn              = containerDataGroupNominal.size() > 0 && !containerDataGroupNominal.get(0).equals("");
			
			//define function of prepoition and adverb. case have data action cmplt.
			if (isDataActionCmplt) {
				localisationByPrepoAdverbe localisation = 
				new localisationByPrepoAdverbe(currentText, currentSyntaxe, GROUPS, dataSubject, dataAction, dataActionCmplt,
											   dataActionGn, partOfSentence, inSentence, cmpltDico, prepAdv, qui);
			}

			//define function of prepoition and adverb. case data action.
			else if (isDataAction) {
				localisationByPrepoAdverbe localisation = 
				new localisationByPrepoAdverbe(currentText, currentSyntaxe, GROUPS, dataSubject, dataAction, dataAction, dataActionGn, 
											   partOfSentence, inSentence, cmpltDico, prepAdv, qui);
			}

			//define function of prepoition and adverb. case have no action but nominal group.
			else if (!isDataAction && !isDataActionCmplt && isGn) {
				
				//none treat remove the quoi= of quoi=group nominal.
				String removeQuoi = containerDataGroupNominal.get(0).split("=")[1];
				containerDataGroupNominal.set(0,  removeQuoi);

				localisationByPrepoAdverbe localisation = 
				new localisationByPrepoAdverbe(currentText, currentSyntaxe, GROUPS, dataSubject, containerDataGroupNominal,
				containerDataGroupNominal, containerDataGroupNominal, partOfSentence, inSentence, cmpltDico, prepAdv, qui);
				System.out.println(containerDataGroupNominal);
			}
			
			
			//
			removeFirstSpace(dataSubject);
			
	
			//
			System.out.println("\n\n\nsujet: " 	   + dataSubject);
			System.out.println("sujet précision: " + dataPreciSubject);
			System.out.println("action: " 		   + dataAction);
			System.out.println("cmplt action: "    + dataActionCmplt);
			System.out.println("cmplt action2: "   + dataActionGn);
			System.out.println("dans le temps: "   + dataTime);
			System.out.println("to find other "    + dataOtherSubject);
			System.out.println("where "            + dataOtherSubject);
			System.out.println("said "             + containerDiscours);

			
			//
			boolean utilSuppOne = utilsInformation.size() > 1;
			if (utilSuppOne) { prioritySubject.changeSubjectCauseLastGnIsGnOfLastX2Gn(utilsInformation, dataSubject); }


			//search information on current subject.
			analyseWho(dataSubject, dataPreciSubject, verifySubject, partOfSentence);


			//subject can reference to another in our sauvegard list. try to matching them.
			searchingIfItReferenceToSubjectFound(dataPreciSubject, dataSubject, 
			otherSubjectIsAfter, saveSentence, saveText, numberSentence); //elle <- reine
			
			//
			identifiateCmpltRefl(containerDataGroupVerbal, dataSubject, dataAction);
			
			//
			incrementQuiSubject(dataSubject, dataPreciSubject);      //sujet
			
			
			//
			searchingOtherPersonInQuoi(dataOtherSubject, dataSubject, dataAction, saveSentence, saveText); //il lui en donne


			//try to match possessif syntaxe with possesser.
			establishPossession(dataPreciSubject, dataSubject);
			

			//
			incrementActionSentence(dataSubject, dataAction, dataActionCmplt,
					dataActionGn, partOfSentence, inSentence, needIncrementSentence);
			
			
			//
			if (haveIncrementedByGprepPrep)  { saveGn.add(dataSubject);    }
			if (conjonctionPreposition)      { saveGn.add(containerPrepo); }
			if (incrementedFromSameSentence) { saveGn.add(dataSubject);    }
			
			
			prioritySubject prioritySubject = 
			new prioritySubject(saveGn, saveVerb, partOfSentence, currentText, canNotBeTheMainSubject, 
			dataSubject, dataAction, currentSyntaxe, inSentence);
			
	
			//
			System.out.println("\n\n\n\n\n");
			System.out.println("cmplt save " + saveGardeCmplt);
			System.out.println("prep ana save " + prepAdv);
			System.out.println(saveCod);
			System.out.println(saveVerb);
			System.out.println(saveGn);
			System.out.println(inSearchingGnPronom);
			System.out.println(utilsInformation);
			System.out.println("\n\n");
			System.out.println("discours:");
			System.out.println(discours);
			System.out.println("\nqui:");
			for(Entry<String, ArrayList<String>> groups: qui.entrySet()) {System.out.println(groups);}
			System.out.println("\nquoi:");
			for(Entry<String, ArrayList<String>> groups: quoi.entrySet()) {System.out.println(groups);}
			System.out.println("\n\n");
			

			//
			ArrayList<String> container = new ArrayList<String>();
			container.add(dataSubject.get(0));
			container.add(dataAction.get(0));
			saveSentence.add(container);

			
			System.out.println(currentText);
			System.out.println("\n\n\nSentence : " + saveSentence);

			//if (numberSentence == 14 && inSentence == 0) {System.out.println(5/0);}
			//if (inSentence == 1) {System.out.println(5/0);}
			for (int index=0; index < 5; index++) { System.out.println(""); }

			inSentence++;
		}
	}



	
	












	private void censureByDiscours(ArrayList<String>  containerDiscours, ArrayList<String> containerDataGroupVerbal,
									ArrayList<String> containerDataGroupCmplt, ArrayList<String> containerDataGroupSujet,
									ArrayList<String> containerDataGroupNominal, ArrayList<String> containerDataAdverbe, 
									ArrayList<String> containerPrepo) {

		//From all container of data: remove if element are contains in the discours.

		for (String elementDiscours: containerDiscours) {

			deletingFromDiscour(containerDataGroupVerbal, elementDiscours);
			deletingFromDiscour(containerDataGroupCmplt,  elementDiscours);
			deletingFromDiscour(containerDataGroupSujet,  elementDiscours);
			deletingFromDiscour(containerDataGroupNominal,elementDiscours);
			deletingFromDiscour(containerDataAdverbe,     elementDiscours);
			deletingFromDiscour(containerPrepo,           elementDiscours);
		}
		

		//two discours are following, on ouvre une phrase et on en ferme une autre.
		//Donc on compare les elements de la derniere phrase avec un guillement.
		boolean twoDiscoursAreFollowing = discours.get(Integer.toString(numberSentence)) != null;

		if (twoDiscoursAreFollowing) {
			ArrayList<String> containerLastDiscours = discours.get(Integer.toString(numberSentence));

			for (String elementDiscours: containerLastDiscours) {
				deletingFromDiscour(containerDataGroupVerbal, elementDiscours);
				deletingFromDiscour(containerDataGroupCmplt,  elementDiscours);
				deletingFromDiscour(containerDataGroupSujet,  elementDiscours);
				deletingFromDiscour(containerDataGroupNominal,elementDiscours);
				deletingFromDiscour(containerDataAdverbe,     elementDiscours);
				deletingFromDiscour(containerPrepo,           elementDiscours);
			}
		}
	}




	private boolean isGroupPrepPrep(int begening, int end,
			ArrayList<String> containerDataGroupSujet, ArrayList<ArrayList<String>> partOfSentence) {

		//
		
		haveIncrementedByGprepPrep gprep = new 
				haveIncrementedByGprepPrep(partOfSentence, GROUPS, begening, end, currentSyntaxe, currentText, containerDataGroupSujet);

		//
		boolean haveIncrementedByGprepPrep = gprep.subjectIsAGprep();

		return  haveIncrementedByGprepPrep;
	}



	private void GroupRelative(int begening, int end, 
			ArrayList<String> containerDataGroupCmplt, ArrayList<String> containerDataGroupSujet, ArrayList<String> GROUPS, 
			ArrayList<String> groupsSyntaxeSorted, ArrayList<String> groupsSyntaxe, ArrayList<ArrayList<String>> partOfSentence) {

		//

		isGroupRelative relative = new isGroupRelative();


		for (int index=1; index < GROUPS.size() - 3; index++) {
			
			String[] data            = relative.recuperateSchemas(GROUPS, index);

			String   lastFunction    = data[0];
			String   currentFunction = data[1];
			String   nextFunction    = data[2];
			String   nextX2Function  = data[3];

			int      nextGroupBegin  = Integer.parseInt(data[4]);
			int      nextX2GroupEnd  = Integer.parseInt(data[5]);
			int      lastGroupBegin  = Integer.parseInt(data[6]);
			int      lastGroupEnd    = Integer.parseInt(data[7]);

			//
			boolean lastGn       = lastFunction.contains("GNominal");
			boolean currentRel   = currentFunction.contains("GRel");
			boolean nextGs       = nextFunction.contains("GNominal");
			boolean nextX2isVerb = nextX2Function.contains("GVerbal");
			boolean inRange      = nextGroupBegin <= begening && nextX2GroupEnd >= end;
			boolean condition1   = lastGn && currentRel && nextGs && nextX2isVerb && inRange;

			if (condition1) { isComplement(lastGroupBegin, lastGroupEnd, containerDataGroupCmplt, groupsSyntaxe, partOfSentence); }
		}
		
		//
		recuperateSchemaFromGroupSyntaxe(groupsSyntaxeSorted,begening, end, containerDataGroupSujet);
	}



	private void recuperateSchemaFromGroupSyntaxe(ArrayList<String> groupsSyntaxeSorted, 
												  int begening, int end, ArrayList<String> containerDataGroupSujet) {

		//
		
		isGroupRelative relative = new isGroupRelative();

		//
		
		for (int index=2; index < groupsSyntaxeSorted.size(); index++) {

			String[] data = relative.recuperateSchemaIndexFirst(groupsSyntaxeSorted, index);

			String   lastX2Function  = data[0];
			String   lastFunction    = data[1];
			String   currentFunction = data[2];

			int      currentBegin      = Integer.parseInt(data[3]);
			int      currentEnd        = Integer.parseInt(data[4]);
			int      lastX2GroupBegin  = Integer.parseInt(data[5]);
			int      lastX2GroupEnd    = Integer.parseInt(data[6]);

			//
			
			boolean lastX2Rel    = lastX2Function.contains("GPREP");
			boolean lastRel      = lastFunction.contains("GREL");
			boolean currentVerb  = currentFunction.contains("GVERBAL");
			boolean inRange      = currentBegin <= begening && currentEnd >= end;
			boolean condition2   = lastX2Rel && lastRel && currentVerb && inRange;

		
			if (condition2) { isGroupSujet(lastX2GroupBegin, lastX2GroupEnd, containerDataGroupSujet); }
		}
	}




	private void makePrecisionOnSubjectIfNoPrecision(ArrayList<String> dataSubject, ArrayList<String> dataPreciSubject) {
		
		//
		makePrecisionOnSubject precision = new makePrecisionOnSubject(dataSubject, dataPreciSubject);
	}



	private void identifiatePronomsPersonnel(ArrayList<String> containerDataGroupVerbal, ArrayList<String> dataSubject, 
											 ArrayList<String> dataCmplt, ArrayList<String> dataAction) {

		//
		
		identifiatePronomPersonnel prnmPersonnel =
				new identifiatePronomPersonnel(dataSubject, saveGn, containerDataGroupVerbal, currentSyntaxe, 
											   currentText, dataAction);
	}



	private void analyseWho(ArrayList<String> dataSubject, ArrayList<String> dataPreciSubject,
			boolean verifySubject, ArrayList<ArrayList<String>> partOfSentence) {

		//

		if (!verifySubject) {
			System.out.println("\n\nanalyseWho make genre to current subject.");
			analysingWho analysingWho = 
					new analysingWho(dataSubject, dataPreciSubject, verifySubject, partOfSentence, currentSyntaxe, currentText);
		}
	}



	private boolean[] identifiateVerbOfGroupNominal(ArrayList<String> dataSubject, ArrayList<String> dataAction, 
												    ArrayList<String> containerDataGroupNominal, 
												    ArrayList<ArrayList<String>> saveGn, ArrayList<ArrayList<String>> saveVerb, 
												    ArrayList<String> dataActionCmplt, ArrayList<ArrayList<String>> saveGardeCmplt) {
	
		//We have a nominal group without subject and without verb.
		//Not a new sentence (can't be a subject).
		//Recuperate last subject and last action (current group nominal = complement).

		boolean[] actionDone = {false};

		verbOfGn verbOfGn = new verbOfGn(dataSubject, dataAction, containerDataGroupNominal, saveVerb, saveGn, 
						                 saveSchema, currentText, dataActionCmplt, discours, actionDone, saveGardeCmplt);

		return actionDone;
	}



	private boolean[] identifiateSubjectToVerb(ArrayList<String> dataSubject, ArrayList<String> dataAction, 
											 ArrayList<String> containerDataGroupVerbal, 
											 ArrayList<ArrayList<String>> partOfSentence, int inSentence, 
											 ArrayList<ArrayList<String>> saveGardeCmplt,
											 ArrayList<ArrayList<String>> prepAdv, Map<String, ArrayList<String>> qui,
											 int numberSentence) {
	

		//There is action and not subject.

		boolean[] toReturn = {false, false, false, false};
		
		identificationOfSubjectOfVerb identificationOfSubjectOfVerb = 
				new identificationOfSubjectOfVerb(saveSchema, saveGn, dataAction, currentSyntaxe, currentText, 
					saveText, DETERMINANTS, containerDataGroupVerbal, inSearchingGnPronom, saveVerb, dataSubject,
					toReturn, partOfSentence, inSentence, quoi, saveGardeCmplt, prepAdv, qui, numberSentence);
	
		return toReturn;
	}


	
	private void incrementActionSentence(ArrayList<String> dataSubject, ArrayList<String> dataAction,
										 ArrayList<String> dataActionCmplt, ArrayList<String> dataActionGn,
										 ArrayList<ArrayList<String>> partOfSentence, 
										 int inSentence, boolean needIncrementSentence) {
	
		//
		
		incrementAction incrementAction = 
				new incrementAction(dataSubject, dataAction, dataActionCmplt, 
						dataActionGn, quoi, partOfSentence, inSentence, needIncrementSentence, currentText);
	}



	

	private void establishPossession(ArrayList<String> dataPreciSubject, ArrayList<String> dataSubject) {

		//Define by possession. 

		searchWho searchWho    = new searchWho();
		String    appartenance = searchWho.establishAppartenant(dataPreciSubject, dataSubject, qui, saveVerb, saveSyntaxe);

		boolean   haveFound    = !(appartenance.equalsIgnoreCase(""));

		//
		if (haveFound) {

			String subject           = dataSubject.get(0);
			String incrementSubject  = "à:" + appartenance;
			String incrementPosseder = "a:" + subject;

			System.out.println("establishPossession - Can appartient a: " + appartenance);

			//
			boolean haveASubject = qui.get(subject) != null;
			if   (haveASubject) { incrementQuiByPossession(qui, subject, incrementSubject); }
			else { System.out.println("APPARTENANCE CHERCHER A QUI CA APPARTIENT"); }

			
			//
			boolean haveAnAppartenance = qui.get(appartenance) != null;
			if   (haveAnAppartenance) { incrementQuiByPossession(qui, appartenance, incrementPosseder); }
			else { System.out.println("APPARTENANCE CHERCHER A QUI CA APPARTIENT"); }
		}
	}



	private void searchingIfItReferenceToSubjectFound(ArrayList<String> dataPreciSubject, ArrayList<String> dataSubject, 
													  boolean otherSubjectIsAfter, ArrayList<ArrayList<String>> saveSentence, 
													  ArrayList<ArrayList<String>> saveText, int numberSentence) {
		
		//
		
		String[] pronoms         = {"je", "tu", "il", "elle", "on", "nous", "vous", "ils", "elles"};
		
		boolean isPronom         = listEquals(dataSubject, pronoms);
		boolean haveFound        = false;
		String  foundAMatch      = "";
		

		//
		if (isPronom) {
			System.out.println("\n\nsearchingIfItReferenceToSubjectFound - Searching cmplt person");
			searchWho searchWho = new searchWho();
			foundAMatch = searchWho.establishListOfPersonFound(dataPreciSubject, dataSubject, qui, "", saveGn, saveSentence, saveText);
			haveFound   = foundAMatch != null;
		}

		//
		if (haveFound) {
			System.out.println("searchingIfItReferenceToSubjectFound - can corresponding with: " + foundAMatch + "\n\n\n");
			dataSubject.set(0, foundAMatch);
			saveGn.get(saveGn.size() - 1).set(0, foundAMatch);
		}

		//
		else if (!haveFound && isPronom) {

			ArrayList<String> data = new ArrayList<String>();

			String  canBe     = searchWho.searchWhoCanBeFromRelationSameSentence(saveSentence, dataSubject, qui);
			infoGenreMatch(canBe, qui, data);

			boolean haveGenreMatch = data.size() >= 0;
			boolean haveCanBe      = !canBe.equalsIgnoreCase("");
			boolean notSameSub     = !canBe.equalsIgnoreCase(dataSubject.get(0));

			//
			if (haveGenreMatch && haveCanBe && notSameSub) { 
				dataSubject.set(0, canBe);
				System.out.println("searchingIfItReferenceToSubjectFound - can be from relation : " + canBe);
			}

			//
			else {
				String subject = dataSubject.get(0).toLowerCase();
				dataSubject.set(0, numberSentence + "-" + subject);
			}
		}
	}






	private void incrementQuiSubject(ArrayList<String> dataSubject, ArrayList<String> dataPreciSubject) {

		//

		boolean SNotEmpty  = dataSubject.get(0) != ""; 

		if (SNotEmpty) {

			String  subject    = dataSubject.get(0);
			boolean keyExists  = qui.get(subject) != null;

			if (!keyExists) { qui.put(subject, new ArrayList<String>()); }

			
			//
			removeFromList(dataPreciSubject, "");
			removeFromList(dataPreciSubject, ".");

			
			//
			for (String   element: dataPreciSubject) {
				 String[] elementSplit = element.split("=");
	
				 boolean  isNotEmpty   = elementSplit.length > 1;
	
				 if (isNotEmpty) {
					String   word         = elementSplit[0];
					String   function     = elementSplit[1];
					
					boolean isNotAlready  = qui.get(subject).contains(function);		
					if (!isNotAlready) { qui.get(subject).add(function); }
					saveGn.add(dataSubject);
				 }
	
				 if (!isNotEmpty) { 
					 boolean isNotAlready  = qui.get(subject).contains(element);		
					 if(!isNotAlready) { qui.get(subject).add(element); }
					 saveGn.add(dataSubject);
				 }
			}
		}
	}

	
	
	private void searchingOtherPersonInQuoi(ArrayList<String> dataOtherSubject, ArrayList<String> dataSubject, 
					ArrayList<String> dataAction, ArrayList<ArrayList<String>> saveSentence, ArrayList<ArrayList<String>> saveText) {
	
		//

		boolean isPersonToSearch = dataOtherSubject.size() > 0 && !dataOtherSubject.get(0).equals("");
		boolean haveFound        = false;
		boolean notEmptyOther    = !(dataOtherSubject.size() == 1 && dataOtherSubject.get(0).equalsIgnoreCase(""));
		String  foundOther       = "";

		//
		if (isPersonToSearch) {
			searchWho searchWho = new searchWho();
			foundOther = searchWho.establishListOfPersonFound(dataOtherSubject, dataSubject, qui, "prsn", saveGn, saveSentence, saveText);
			haveFound = foundOther != null;
		}

		//
		if (haveFound && notEmptyOther) {
			System.out.println("\n\nSearching other person");
			String increment = "(à=" + foundOther + ")";
			System.out.println("can references to: " + increment);
			dataAction.add(increment);
		}
	}
	
	
	

	

	private void isComplement(int begening, int end, ArrayList<String> containerData, 
			ArrayList<String> groupsSyntaxe, ArrayList<ArrayList<String>> partOfSentence) {

		//
		ArrayList<String> container = new ArrayList<String>();
		
		
		//
		complementTreatement complement = 
				new complementTreatement(begening, end, containerData, container, currentSyntaxe, 
										 currentText, DETERMINANTS, groupsSyntaxe, partOfSentence);

		//
		recuperateDataFromGroupConcern(begening, end, container);
		
		//
		complement.treatTheComplement();

	}


	
	private void isGroupeVerbal(int begening, int end, ArrayList<String> containerData) {

		//
		
		ArrayList<String> container = new ArrayList<String>();
		recuperateDataFromGroupConcern(begening, end, container);

		actionTreatment action = new actionTreatment(container, containerData, currentText);
	}	




	private void GroupeNominal(int begening, int end, ArrayList<String> containerData) {
		
		//
		
		ArrayList<String> container = new ArrayList<String>();
		recuperateDataFromGroupConcern(begening, end, container);
		isGroupeNominal isGroupeNominal = new isGroupeNominal(begening, end, containerData, container);
	}
	

	private void isGroupePreposi(int begening, int end, ArrayList<String> containerData, 
								 ArrayList<String> groupsSyntaxe, int index, ArrayList<String> containerPrepo, int inSentence) {
	
		//
		
		ArrayList<String> container = new ArrayList<String>();
		recuperateDataFromGroupConcern(begening, end, container);
		prepositionTreatment preposition = 
				new prepositionTreatment(container, containerData, containerPrepo, groupsSyntaxe, index, GROUPS, inSentence, currentText);

	}
	
	
	private void isGroupePreposi2(int begening, int end, ArrayList<String> containerData) {

		//
		
		ArrayList<String> container = new ArrayList<String>();
		recuperateDataFromGroupConcern(begening, end, container);
		prepositionTreatment preposition = new prepositionTreatment(begening, end, containerData, container);
	}
	
	
	
	
	private void isGroupSujet(int begening, int end, ArrayList<String> containerData) {

		//
		
		ArrayList<String> container = new ArrayList<String>();
		recuperateDataFromGroupConcern(begening, end, container);
		incrementSubject subject = new incrementSubject(container, containerData);
	}
		

	
	private void isGroupAdverbe(ArrayList<String> containerData) {
		
		//
		
		int index = 0;
		
		String[] adverbeGroup = {"adv=", "fnc="};
		
		for (ArrayList<String> current: this.currentSyntaxe) {
			
			String text       = this.currentText.get(index);
			boolean isAdverbe = listEqualsElement(current, "Adverbe");

			groupAdverbial serchAdvb  = new groupAdverbial(text);
			String 		   advFind    = serchAdvb.searchAdvbInDefineGroupe();

			if (isAdverbe) { adverbeGroup[0] += text; adverbeGroup[1] += advFind; }

			index++;
		}
		for (String element: adverbeGroup) { containerData.add(element); }
	}
	
	

	private void treatementOfContainers(ArrayList<String> containerData) {

		ArrayList<String> toRemove = new ArrayList<String>();

		for (String element: containerData) {

			String[] elementSplit  = element.split("=");
			boolean  isEmpty 	   = elementSplit.length == 1;
			boolean  containsEqual = element.contains("=");
			
			if      (isEmpty || !containsEqual)  { toRemove.add(element); }

			else if (!isEmpty) {
				int verif = -1;
				try  { verif = Integer.parseInt(elementSplit[1]); } catch(Exception e) {}
				boolean isNotLetterIsIncrementGv = verif != -1;
				if (isNotLetterIsIncrementGv) { toRemove.add(element); }
			}
		}
		for (String remove: toRemove) { containerData.remove(remove); }
	}
	


	
	
	
	
	

	private void identifiateTypeOfDeterminant(ArrayList<String> containerData, ArrayList<String> dataRecpteur, String search) {

		identifiateDeterminant identifiateDeterminant = 
				new identifiateDeterminant(containerData, search, dataRecpteur, numberSentence, currentText, currentSyntaxe);

	}

	
	
	private void identifiateTypeOfPronom(ArrayList<String> containerData, ArrayList<String> dataPreciSubject) {
	
		//System.out.println(containerData);
		
		String[] masc 	  = {"il"};
		String[] fem  	  = {"elle"};
		String[] mix  	  = {"lui", "on"};
		String[] plurMasc = {"ils"};
		String[] plurFem  = {"elles"};
		
		for (String   element: containerData) {
			 String[] elementSplit = element.split("=");
			 String   function     = elementSplit[0];
			 String   pronom       = elementSplit[1];

			 boolean isQui      = function.equalsIgnoreCase("qui");
			 boolean isMasc     = thisListEqualWord(masc, pronom);
			 boolean isFem      = thisListEqualWord(fem,  pronom);
			 boolean isMix      = thisListEqualWord(mix,  pronom);
			 boolean isPlurMasc = thisListEqualWord(plurMasc, pronom);
			 boolean isPlurFem  = thisListEqualWord(plurFem,  pronom);
			 
			 if      (isQui && isMasc)     { dataPreciSubject.add(pronom + "=masculin"); }
			 else if (isQui && isFem)      { dataPreciSubject.add(pronom + "=feminin"); }
			 else if (isQui && isMix)      { dataPreciSubject.add(pronom + "=general"); }
			 else if (isQui && isPlurMasc) { dataPreciSubject.add(pronom + "=plurMasc"); }
			 else if (isQui && isPlurFem)  { dataPreciSubject.add(pronom + "=plurFem"); }
		}
	}

	private void identifiateCmpltPerson(ArrayList<String> containerData, ArrayList<String> dataOtherSubject) {
	
		//System.out.println(containerData);

		String[] enonciateur    = {"moi"};
		String[] destinataire   = {"toi"};
		String[] designPlur     = {"eux"};
		
		String[] designMasc     = {"le",};
		String[] designFem      = {"la",};
		String[] desginGen  	= {"lui", "les", "leur", "soi"};

		boolean searchDesignMasc = false;
		boolean searchDesignFem  = false;
		boolean searchDesginGen  = false;

		for (String   element: containerData) {
			 String[] elementSplit = element.split("=");
			 String   function     = elementSplit[0];
			 String   pronom       = elementSplit[1];

			 boolean isQui        = function.equalsIgnoreCase("aqui");
			 boolean isdesignMasc = thisListEqualWord(designMasc, pronom);
			 boolean isdesignFem  = thisListEqualWord(designFem,  pronom);
			 boolean isdesginGen  = thisListEqualWord(desginGen,  pronom);

			 if      (isQui && isdesignMasc)     { dataOtherSubject.add(pronom + "=masculin"); }
			 else if (isQui && isdesignFem)      { dataOtherSubject.add(pronom + "=feminin");  }
			 else if (isQui && isdesginGen)      { dataOtherSubject.add(pronom + "=general");  }
		}
	}

	
	private boolean[] identifiateCmpltRefl(ArrayList<String> containerDataGroupVerbal, 
										 ArrayList<String> dataSubject, ArrayList<String> dataAction) {

		
		pronomReflechis pronomReflechis = new pronomReflechis(containerDataGroupVerbal,
															  dataSubject, dataAction, qui, saveGn);

		boolean[] incrementation = pronomReflechis.searchingPrnmReflechis();

		//Have we already incremented saveGn ?
		return incrementation;
	}
	


	private void identifiateCmpltCod(ArrayList<String> containerDataGroupVerbal, ArrayList<String> dataActionCmplt) {


		ArrayList<String> cmpltCod = new ArrayList<String>();
		boolean isntFirstSentence  = numberSentence > 0;

		if (isntFirstSentence) {

			String  NomCommun 		= searchAGNIfsearchingCmpltCod(numberSentence, saveSchema, saveSyntaxe, saveText, saveCod);
			boolean haveFoundTheCod = NomCommun != "";

			String cmplt = "";

			recuperateInformation(containerDataGroupVerbal, cmpltCod, "quoi");
			boolean isNotEmpty1 = cmpltCod.size() > 0;
	

			if (isNotEmpty1) { 
				cmplt = cmpltCod.get(0);

				if (haveFoundTheCod) { 
					cmpltCod.set(0, cmplt + "=" + NomCommun); 
					dataActionCmplt.add(NomCommun);
				}

				
				else if (!haveFoundTheCod) { 
					cmpltCod.set(0,     cmplt + "=quelqueChose?");
					dataActionCmplt.add(cmplt + "=quelqueChose?");
				}

				
				saveCod.add(cmpltCod);
			}
			else { saveCod.add(cmpltCod); }
		}
		else { saveCod.add(cmpltCod); }
	}

	
	

	

	private void identifiateTime(ArrayList<String> containerDataGroupVerbal, 
								 ArrayList<String> containerDataAdverbe, 
								 ArrayList<String> dataTime) {

		
		String[] times = {"", ""};

		for (String   verbe:   containerDataGroupVerbal) {
			 String[] elementSplit = verbe.split("=");
			 String   function     = elementSplit[0];
			 String   temps        = elementSplit[1];
			 
			 boolean isTime1     = function.equalsIgnoreCase("time1");
			 boolean isTime2     = function.equalsIgnoreCase("time2");

			 if      (isTime1) { times[0] = temps; }
			 else if (isTime2) { times[1] = temps; }
			 
		}
		System.out.println("\n\nidentifiateTime - " + Arrays.toString(times) + "\n\n");
	}
	
	
	
	private boolean identifiateSubject(ArrayList<String> containerDataGroupSujet, ArrayList<String> containerDataGroupVerbal, 
								       ArrayList<String> dataSubject, ArrayList<ArrayList<String>> saveVerb, 
								       ArrayList<ArrayList<String>> partOfSentence, 
								       int inSentence, ArrayList<String> groupsSyntaxeSorted) {
		
		
		
		identifiateSubject subject =
				new identifiateSubject(containerDataGroupSujet, containerDataGroupVerbal, 
						dataSubject, saveVerb, partOfSentence, inSentence, saveGn, currentText, numberSentence,
						currentSyntaxe, groupsSyntaxeSorted);
		
		boolean haveInc = subject.identification();
		return haveInc;
	}
	

	
	private void incrementSaveGnFromGroupGnOrSubject(ArrayList<String> dataSubject) {
	
		//Saving groups nominal principal in case empty group verbal is after.

		incrementSaveGnFromGroups incrementation = 
				new incrementSaveGnFromGroups(saveSchema, currentSyntaxe, DETERMINANTS, currentText, saveGn);

	}
	

	
	
	
	private void incrementSaveVerb() {
		
		ArrayList<String> sauvegarde    = saveSchema.get(saveSchema.size() - 1);
		ArrayList<String> containerVerb = new ArrayList<String>();
	
		for (String group: sauvegarde) {

			boolean containsGroup = group.contains("GVERBAL");

			if (containsGroup) {

				int[] indexs = {-1, -1};

				String[] schemaSplit = group.split("[+]");
				indexs[0] = Integer.parseInt(schemaSplit[0]);
				indexs[1] = Integer.parseInt(schemaSplit[1]) + 1;
				
				String incrementVerb = "";

				for (int index=indexs[0]; index < indexs[1]; index++) {
					
					ArrayList<String> current = currentSyntaxe.get(index);
					
					String   adverbe  = recuperateDataEqual(current, currentText, index, "Adverbe");
					String   verbe    = recuperateContains(current,  currentText, index, "verbe#");
					String   Cnjnc    = recuperateDataEqual(current, currentText, index, "Conjonction de coordination");
					String[] search   = {adverbe, verbe, Cnjnc};

					incrementVerb = incrementQuiSubject(search, incrementVerb);

				}
				boolean incrementNotEmpty = !incrementVerb.equalsIgnoreCase("");
				if (incrementNotEmpty) { incrementVerb = incrementVerb.substring(0, incrementVerb.length() - 1); }
				containerVerb.add(incrementVerb);
			}
		}
		saveVerb.add(containerVerb);
	}
	
	

	
	boolean identifiateOtherSubject(ArrayList<String> dataSubject, ArrayList<String> dataOtherSubject, 
									ArrayList<String> dataPreciSubject) {

		boolean otherSubjectIsAfter = false;
		
		ArrayList<String> onQui = new ArrayList<String>();

		recuperateInformation(dataPreciSubject,   onQui, "relationAvec");

		boolean isNotEmpty1 = onQui.size() > 0;

		if (isNotEmpty1) { 
			dataOtherSubject.add(onQui.get(0)); 
			saveGn.add(onQui);
			dataSubject.add(onQui.get(0));
			otherSubjectIsAfter = verifyPosition(dataSubject.get(0), onQui.get(0), currentText);
		}

		
		
		else { dataOtherSubject.add(""); }

		
		return otherSubjectIsAfter;
	}
	
	
	
	
	
	
	private void identifiateInformationsOnSubject(ArrayList<String> containerDataGroupCmplt, 
												  ArrayList<String> containerDataGroupSujet, 
												  ArrayList<String> containerDataVerbe,
												  ArrayList<String> dataPreciSubject, 
												  ArrayList<String> containerDataGroupNominal) {


		boolean negation = listContains(containerDataVerbe, "negation");


		ArrayList<String> onQui = new ArrayList<String>();

		recuperateInformation(containerDataGroupCmplt,   onQui, "qui");
		boolean isNotEmpty1 = onQui.size() > 0;
		if      (isNotEmpty1 && negation) { dataPreciSubject.add("pas " + onQui.get(0)); }
		else if (isNotEmpty1) { dataPreciSubject.add(onQui.get(0)); }

		

		recuperateInformation(containerDataGroupCmplt,   onQui, "Relationqui");
		boolean isNotEmpty4 = onQui.size() > 0;
		if      (isNotEmpty4 && negation) { dataPreciSubject.add(onQui.get(0)); }
		else if (isNotEmpty4) { dataPreciSubject.add("relationAvec:" + onQui.get(0)); }

		
		
		
		recuperateInformation(containerDataGroupSujet,   onQui, "precisionQui");
		boolean isNotEmpty2 = onQui.size() > 0;
		if (isNotEmpty2) { dataPreciSubject.add(onQui.get(0)); }
		
		
		recuperateInformation(containerDataGroupNominal,   onQui, "preciQuoi");
		boolean isNotEmpty3 = onQui.size() > 0;
		if (isNotEmpty3) { dataPreciSubject.add(onQui.get(0)); }
		

	}
	
	
	
	
	private void identifiateNonePronomOrSubject(ArrayList<String> containerData) {

		boolean isFoundADesign = listContains(containerData, "target=");

		if (isFoundADesign) {
		
			for (String   element: containerData) {

				 String[] elementSplit = element.split("=");
				 String   function     = elementSplit[0];
				 String   pronom       = elementSplit[1];
	
				 boolean isDesign     = function.equalsIgnoreCase("target");
				 boolean isMultiPossi = pronom.split("-").length > 1;
	
				 if (isMultiPossi && isDesign) { System.out.println("possiblités: " + pronom); }
			}
		}
	}

	


	private void identifiateAction(ArrayList<String> containerDataGroupVerbal, 
								   ArrayList<String> dataAction, ArrayList<String> dataSubject, 
								   ArrayList<ArrayList<String>> saveSentence, ArrayList<ArrayList<String>> partOfSentence) {

		
		identificationOfAction idenfiateAction = new identificationOfAction(containerDataGroupVerbal, dataSubject,
				saveSentence, partOfSentence, dataAction, inSearchingGnPronom);
	
		
		idenfiateAction.identifiateAction();
		

	}
	

	
	
	private void identifiateCmpltAction(ArrayList<String> dataActionCmplt, ArrayList<String> dataAction,
			ArrayList<ArrayList<String>> partOfSentence, int inSentence, Map<int[], String> cmpltDico) {
		

		boolean haveAction = !dataAction.get(0).equalsIgnoreCase("");

		complementAction complement =
				new complementAction(dataActionCmplt, dataAction, partOfSentence, inSentence, currentText, cmpltDico, haveAction);


	}
	
	
	
	
	
	


	
	
	private void recuperateDataFromGroupConcern(int begening, int end, List<String> container) {

		Map<String, Map<Integer, String>> thisLists = new HashMap<String, Map<Integer, String>>();
		thisLists.put("nominal",     groupNominalFunctionBeginIndex);
		thisLists.put("verb1",       groupsVerbalsFunctionBeginIndex);
		thisLists.put("verb2",       pronomToGNBeginIndex);
		thisLists.put("adverbe",     adverbeFunction);
		thisLists.put("propo",       propositionRelativeFunction);
		thisLists.put("prepo",       prepositionFunction);
		thisLists.put("conjonction", conjonctionLocalisation);
		

		for(Entry<String, Map<Integer, String>> groups: thisLists.entrySet()) {

			Map<Integer, String> valueGroup = groups.getValue();
			
			for(Entry<Integer, String> group: valueGroup.entrySet()) {
				
				int    key   = group.getKey();
				String value = group.getValue();

				boolean isInRange = key >= begening && key <= end;

				if (isInRange) { 
					String[] elementSplit = value.split("/");
					for (String splited: elementSplit) { 
						boolean isNotEmpty = splited.length() > 1;
						if (isNotEmpty) { container.add(splited); }
					}
				}
			}
		}
	}
	
	

	
	private void displaying() {
		ArrayList<String> displayText = new ArrayList<String>(this.currentText);
		for (int index=0; index < this.currentText.size(); index++) {displayText.set(index, index+this.currentText.get(index));}
		System.out.println(displayText + "\n");
	}
	
}
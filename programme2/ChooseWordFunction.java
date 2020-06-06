package fr.jbaw.programme2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Iterator;





public class ChooseWordFunction extends searchingInSentence{


	String[] pronomPersonnel = {"je", "tu", "il", "nous", "vous", "ils", "elle", "on", "elles",
								"Pronom personnel", "me", "te", "se", "lui",
								"moi", "toi", "soi", "leur", "eux", "contraction=que", "contraction=se",
								"contraction=me", "contraction=te"};

	String[] pronomPersonnelSyntaxe = {"Nom commun", "verbe", "pronom", "adjectif"};
	
	String[] determinantCommun = {"Article indéfini", "Article défini", "Article défini numéral",
								  "Pronom indéfini", "Forme de pronom indéfini", 
								  "pronom défini",
								  "Adjectif indéfini", "Adjectif possessif", "Adjectif démonstratif",
								  "Forme d'Adjectif possessif", "Forme d'Adjectif démonstratif",
								  "déterminants interrogatifs", 
								  "Forme d'article défini", "Forme d'article indéfini",
								  "Forme d’Adjectif possessif", "Forme d’Adjectif démonstratif",
								  "Forme d’article défini", "Forme d’article indéfini", "Adjectif interrogatif",
								  "Adjectif exclamatif", "Adjectif", "Forme d’adjectif", "Préposition", "Article défini",
								  "Forme d’article défini", "Forme d’article défini"};

	
	String[] determinantCommunWithotuAdj = {"Article indéfini", "Article défini", "Article défini numéral",
											  "Pronom indéfini", "Forme de pronom indéfini", 
											  "pronom défini",
											  "Adjectif indéfini", "Adjectif possessif", "Adjectif démonstratif",
											  "Forme d'Adjectif possessif", "Forme d'Adjectif démonstratif",
											  "déterminants interrogatifs", 
											  "Forme d'article défini", "Forme d'article indéfini",
											  "Forme d’Adjectif possessif", "Forme d’Adjectif démonstratif",
											  "Forme d’article défini", "Forme d’article indéfini", "Adjectif interrogatif",
											  "Adjectif exclamatif", "Forme d’adjectif", "Préposition", "Article défini",
											  "Forme d’article défini"};
	
	
	String[] determinantCommunWithotuAdjWithoutPrep = {"Article indéfini", "Article défini", "Article défini numéral",
													  "Pronom indéfini", "Forme de pronom indéfini", 
													  "pronom défini",
													  "Adjectif indéfini", "Adjectif possessif", "Adjectif démonstratif",
													  "Forme d'Adjectif possessif", "Forme d'Adjectif démonstratif",
													  "déterminants interrogatifs", 
													  "Forme d'article défini", "Forme d'article indéfini",
													  "Forme d’Adjectif possessif", "Forme d’Adjectif démonstratif",
													  "Forme d’article défini", "Forme d’article indéfini", "Adjectif interrogatif",
													  "Adjectif exclamatif", "Forme d’adjectif", "Article défini",
													  "Forme d’article défini"};
	
	String[] Ponctuation = {"guillement", "deux points", "parenthese ouvrante", 
							"parenthese fermante", "bec ouvrant", "bec fermant", "point", "point virgule", 
							"slash", "virgule", "point exclamation", 
							"point interrogation", "guillement ouvrant", "guillement fermant", "tirer"};
	

	String[] prnmInd = {"rien", "aucun", "aucune", "aucunes", "nul" ,"nule", "nules",
						"contraction=le un", "contraction=le une", "contraction=le autre", "ni contraction=le une",
						"pas une", "pas un", "plus contraction=de une", "plus contraction=de un",
						"plusieurs", "tout", "on", 
						"quelque un", "quelque une", 
						"quelque chose",
						"autrui", "certain", "certaine", "certaines", "autre chose", "chacun", "chacune", "tout un chacun",
						"contraction=ne importe qui", "contraction=ne importe quoi",
						"contraction=ne importe lequel", "contraction=ne importe laquelle", "contraction=ne importe lesquels", 
						"contraction=ne importe lesquelles", "quelque un", "quelques uns", "Quiconque", "certains", 
						"les autres", "contraction=le autre"};
	
	
	
	String[] artDEF  = {"du"};
	
	
	String[] prnmDem = {"ça", "cela", "contraction=ce", "ce", "celle", "celle là", "celle ci", "ceux là", "celle-ci", "ceux-là"};

	String[] possessifPrnm = {"mon", "nos", "vos" ,"leur", "leurs", "mes", "tes", "ses"};
	
	String[] beforeAdjectif = {"Nom commun", "Article indéfini", "Article défini", "Article défini numéral",
							   "Pronom indéfini", "Forme de pronom indéfini", "pronom défini", "Article partitif",
							   "Adjectif indéfini", "Adjectif possessif", "Adjectif démonstratif",
							   "Forme d'Adjectif possessif", "Forme d'Adjectif démonstratif",
							   "déterminants interrogatifs", "Forme d'article défini", "Forme d'article indéfini",
							   "Forme d’Adjectif possessif", "Forme d’Adjectif démonstratif",
							   "Forme d’article défini", "Forme d’article indéfini", "Adjectif interrogatif",
							   "Adjectif exclamatif", "Adjectif", "Forme de nom commun",
							   "Conjonction de coordination", "Adverbe"};
	
	String[] determinantPropre = {"Article indéfini", "Article défini", "Article défini numéral",
								  "Pronom indéfini", "Forme de pronom indéfini", "pronom défini",
								  "Adjectif indéfini", "Adjectif possessif", "Adjectif démonstratif",
								  "Forme d'Adjectif possessif", "Forme d'Adjectif démonstratif",
								  "déterminants interrogatifs", "Forme d'article défini",
								  "Forme d'article indéfini", "préposition", "Forme d’Adjectif possessif",
								  "Forme d’Adjectif démonstratif", "Forme d’article défini", "Forme d’article indéfini"};
	
	String[] articles = {"Article indéfini", "Article défini", "Article défini numéral",
						 "Forme d'article défini", "Forme d'article indéfini",
						 "Forme d’article défini", "Forme d’article indéfini",
						 "Article partitif"};
	
	
	String[] beforeVerb = {"Pronom", "Nom commun", "verbe#", "Conjonction de coordination", "Adverbe",
						  "Préposition", "Article partitif", "Adjectif", "Pronom indéfini", "Nom propre", 
						  "locution", "Pronom personnel", "Particule", "Forme de nom commun", "Forme d'adjectif", 
						  "Forme d’adjectif indéfini", "Forme de pronom indéfini", "Forme de nom commun", "Forme d’adjectif"};

	String[] articleDefini   = {"le", "la", "les"};
	String[] artcileIndefini = {"un", "une", "des"};
	
	String[] ponctuation  = {"virgule", "point", "point interrogation"};
	String[] nomCommun    = {"Nom commun", "nom propre"};
	String[] adjectif     = {"adjectif"};
	String[] subordonee   = {"pronom relatif", "Adverbe relatif", "Forme de pronom relatif"};
	String[] preposition  = {"preposition"};
	String[] verbe        = {"verbe"};
	
	String[] conjonction  = {"Conjonction de coordination", "mais", "ou", "et", "donc", "or", "ni", "car"};
	String[] groupeVerbal = {"verbe"};
	String[] expansian    = {"préposition", "pronom relatif", "adjectif"};

	String[] locution     = {"cd", "ver de terre", "vers de terre", "réseaux sociaux", "réseau social", "tous deux", "tous les deux", "chemin de fer", "Tous deux", "fer à repasser", "chez-toi", "chez toi", "chez-moi",
						   "chez moi", "chez elle", "chez-elle", "chez-nous", "chez nous", "après-midi", "après - midi", "pendant que", "Pendant que"};

	String[] voyelle      = {"a", "e", "i", "o", "u", "y"};

	String[] COMPOSEDNC   = {"après-midi", "aujourd'hui", "après-demain", "après midi"};
	
	String[] ADVERBLIST   = {"en arrière", "tandis que", "la plus", "le plus", "en fait", "en effet", "plus", "comme cela", "comme ça", "au point que", "beaucoup", "beaucoup trop", "un peu", "Puis", "puis", "jusque au", "Jusque au", "peu", "ne pas", "ne plus", "côte à côte", "une à une", "un à un", "ajourde hui", "ce jour", "même un jour", "ce jour même", "toujours plus", "tout droit", "Peu à peu", "en quelque sorte", "En premier", "En premier lieu", "à la fois", "peu à peu", "peu a peu", "de plus en plus",  "non plus", "en d'autres termes", "d'abord","adagio","ailleurs","ainsi","aisément","à l'excès","aléatoirement","alentour","alias","allègrement","allegretto","allegro","allusivement","alors","alphabétiquement","alternativement","ambitieusement","amèrement","amicalement","amoroso","amoureusement","analogiquement","anarchiquement","anciennement","andante","andantino","angéliquement","annuellement","anonymement","anormalement","antérieurement","anxieusement","à peine","à peu près","apparemment","approximativement","après","après","demain","arbitrairement","ardemment","arrière","artificiellement","artisanalement","artistiquement","assez","assez bien","assidûment","assurément","astucieusement","atrocement","attentivement","aucunement","audacieusement","au dedans","au dehors","au delà","au dessous","au dessus","au devant","aujourd'hui","auparavant","auprès","aussi","aussitôt","autant","automatiquement","autoritairement","autour","autrefois","autrement","avant","avantageusement","avant","hier","aveuglément","avidement","Bâillement","banalement","banco","bas","bassement","béatement","bénévolement","bestialement","bêtement","bien","assez bien","bientôt", "bigrement","bilatéralement","bis","bizarrement","bon","bougrement","bourgeoisement","bravement","bref","brièvement","brillamment","brusquement","brutalement","bruyamment","Çà","calmement","candidement","capricieusement","carrêment","catégoriquement","cavalièrement","céans","cependant","cérémonieusement","certainement","certes","chaleureusement","charitablement","charnellement","chastement","chaudement","chèrement","chichement","chimiquement","chrétiennement","chronologiquement","ci","cinquièmement","civilement","clairement","clandestinement","classiquement","cliniquement","collectivement","combien","comme","comment","commercialement","commodément","communément","comparativement","complètement","concrètement","concurremment","confidentiellement","confortablement","confusément","conjointement","conjugalement","consciemment","consciencieusement","conséquemment","considérablement","constamment","constitutionnellement","continuellement","continûment","contre","convenablement","convulsivement","copieusement","coquettement","cordialement","correctement","corrélativement","courageusement","couramment","courtoisement","craintivement","crânement","crescendo","criminellement","cruellement","curieusement","cyniquement","D'abord","dangereusement","davantage","debout","deçà","décemment","décidément","dédaigneusement","dedans","défavorablement","défectueusement","définitivement","dehors","déjà","délibérément","délicatement","délicieusement","demain","démesurément","demi","démocratiquement","depuis","déraisonnablement","derechef","dernièrement","derrière","désagréablement","désespérément","désormais","dessous","dessus","deuxièmement","devant","dévotement","diablement","diaboliquement","diamétralement","différemment","difficilement","diffusément","dignement","diplomatiquement","directement","discrètement","distinctement","distraitement","diversement","divinement","dixièmement","doctement","dogmatiquement","donc","dorénavant","doublement","doucement","doucereusement","doucettement","douillettement","dramatiquement","drôlement","dubitativement","dûment","durablement","durement","dynamiquement","Économiquement","effectivement","efficacement","effrontément","effroyablement","également","électriquement","élégamment","elliptiquement","élogieusement","éminemment","emphatiquement","empiriquement","encore","énergiquement","enfin","énormément","ensuite","entièrement","environ","éperdument","épisodiquement","épouvantablement","équitablement","essentiellement","est-ce-que","esthétiquement","éternellement","étonnamment","étourdiment","étourdissement","étrangement","étroitement","étymologiquement","évasivement","éventuellement","évidemment","exactement","exagérément","à l'excès","excellemment", "exceptionnellement","excessivement","exclusivement","exemplairement","exhaustivement","expérimentalement","explicitement","exprès","expressément","extérieurement","extraordinairement","extrêmement","Fabuleusement","fâcheusement","facilement","facultativement","faiblement","fameusement","familièrement","fanatiquement","fantastiquement","farouchement","fatalement","faussement","fautivement","favorablement","fébrilement","fermement","férocement","fictivement","fidèlement","fièrement","fiévreusement","finalement","financièrement","finement","fiscalement","follement","foncièrement", "là", "Là", "bas", "fondamentalement","forcément","formellement","formidablement","fort","fortement","fortissimo","fortuitement","fougueusement","fraîchement","franchement","franco","fraternellement","frauduleusement","frénétiquement","fréquemment","frileusement","frivolement","froidement","fructueusement","fugitivement","furieusement","furtivement","futilement","Gaiement","gaillardement","galamment","gauloisement","généralement","généreusement","génétiquement","gentiment","globalement","glorieusement","gloutonnement", "goulûment","gracieusement","graduellement","grandement","graphiquement","grassement","gratis","gratuitement","grossièrement","guère","Habilement","habituellement","haineusement","hardiment","hargneusement","harmonieusement","hâtivement","hebdomadairement","héréditairement","hermétiquement","héroïquement","heureusement","hideusement","hier","hiérarchiquement","historiquement","honnêtement","horizontalement","horriblement","hostilement","huitièmement","humainement","humblement","hypocritement","Ici","idem","ignoblement","illégalement","illégitimement","illico","illogiquement","illusoirement","immanquablement","immédiatement","immensément","immodérément","immuablement","imparfaitement","impassiblement","impatiemment","impeccablement","impérativement","imperceptiblement","impérialement","impérieusement","impersonnellement","imperturbablement","impétueusement","impitoyablement","implacablement","impoliment","improprement","impromptu","imprudemment","impudemment","impulsivement","impunément","incessamment","incidemment","inclusivement","incomparablement","incognito","incomplètement","inconditionnellement","inconfortablement","incongrûment","inconsciemment","inconsidérément","incontestablement","incorrectement","incorrigiblement","incroyablement","indécemment","indéfiniment","indéniablement","indépendamment","indifféremment","indignement","indirectement","indiscrètement","indiscutablement","indissolublement","indistinctement","individuellement","indolemment","indubitablement","indûment","industriellement","inefficacement","inégalement","inélégamment","inéluctablement","inépuisablement","inévitablement","inexactement","inexorablement","inexplicablement","inextricablement","infailliblement","infatigablement","infidèlement","infiniment","inflexiblement","infra","infructueusement","ingénieusement","ingénument","inhumainement","iniquement","initialement","injustement","innocemment","inopinément","inopportunément","insensiblement","inséparablement","insidieusement","insolemment","instamment","instantanément","instinctivement","insuffisamment","intarissablement","intégralement","intellectuellement","intelligemment","intelligiblement","intensément","intensivement","intentionnellement","intérieurement","interminablement","intimement","intransitivement","intrépidement","intrinsèquement","intuitivement","inutilement","invariablement","inversement","invinciblement","involontairement","invraisemblablement","ironiquement","irréductiblement","irréfutablement","irrégulièrement","irrémédiablement","irréparablement","irréprochablement","irrésistiblement","irrespectueusement","irrévérencieusement","irréversiblement","irrévocablement","item","Jadis","jalousement","jamais","joliment","journellement","judiciairement","judicieusement","juridiquement","justement","Là bas","là bas","laborieusement","laconiquement","là haut","laidement","lamentablement","langoureusement","largement","lascivement","latéralement","légitimement","lentement","lestement","libéralement","librement","linéairement","lisiblement","littérairement","littéralement","localement","logiquement","loin","longtemps","longuement","lors","lourdement","lucidement","lugubrement","lumineusement","luxueusement","Machinalement","magiquement","magistralement","magnanimement","magnifiquement","maigrement","maintenant","majestueusement","majoritairement","pas mal","maladivement","maladroitement","malaisément","malencontreusement","malheureusement","malhonnêtement","malicieusement","malignement", "malproprement","manifestement","manuellement","marginalement","matériellement","maternellement","mathématiquement","méchamment","médicalement","médiocrement","mélancoliquement","mélodieusement","même","mensuellement","mentalement","merveilleusement","mesquinement","métaphoriquement","méthodiquement","méticuleusement","mielleusement","mieux","militairement","minutieusement","miraculeusement","misérablement","moderato","modestement","moindrement","moins","mollement","mondialement","monstrueusement","moralement","mordicus","morphologiquement","mortellement","moyennement","mûrement","musicalement","mutuellement","mystérieusement","mystiquement","Naïvement","naguère","naturellement","néanmoins","nécessairement","négativement","négligemment","neuvièmement","niaisement","noblement","nominalement","nominativement","nommément","non","nonchalamment","normalement","notablement","notamment","notoirement","notoirement","nouvellement","nuitamment","nullement","numériquement","Objectivement","obligatoirement","obligeamment","obliquement","obscurément","obséquieusement","obstinément","oc","occasionnellement","officiellement","officieusement","oïl","oisivement","onzièmement","opiniâtrement","opportunément","oralement","ordinairement","ores","organiquement","orgueilleusement","originairement","originellement","ostensiblement","où","oui","outrageusement","outre","ouvertement","Pacifiquement","partant", "à peine","paradoxalement","parallèlement","parcimonieusement","pareillement","paresseusement","parfaitement","parfois","partiellement","particulièrement","partiellement","partout","pas","pas mal","passablement","passagèrement","passionnellement","passionnément","passivement","paternellement","pathétiquement","patiemment","pauvrement","pécuniairement","péjorativement","péniblement","pensivement","péremptoirement","perfidement","périodiquement","pernicieusement","perpendiculairement","perpétuellement","personnellement","pertinemment","pesamment","petitement","un peu","peureusement","peut être","philosophiquement","phonétiquement","piètrement","pieusement","pis","piteusement","pitoyablement","placidement","plaintivement","plaisamment","platement","pleinement","plutôt","poliment","politiquement","pompeusement","ponctuellement","populairement","posément","positivement","postérieurement","potentiellement","pourtant","poussivement","pratiquement","préalablement","précairement","précautionneusement","précédemment","précieusement","précipitamment","précisément","précocement","prématurément","premièrement","près","présentement","presque","prestement","prestissimo","presto","prétendument","prétentieusement","préventivement","primitivement","primo","princièrement","principalement","prioritairement","probablement","prochainement","prodigieusement","professionnellement","profondément","progressivement","promptement","pronominalement","proportionnellement","proprement","prou","proverbialement","providentiellement","provisoirement","prudemment","psychologiquement","pudiquement","puérilement","puis","puissamment","purement","Qualitativement","quand ?","quantitativement","quarto","quasi","quasiment","quatorzièmement","quatrièmement","quelque","quelquefois","quelque part","quinzièmement","quotidiennement","Radicalement","raisonnablement","rapidement","rarement","rationnellement","récemment","réciproquement","recta","réellement","réglementairement","régulièrement","religieusement","remarquablement","résolument","respectivement","respectueusement","rétroactivement","rétrospectivement","ridiculement","rigidement","rigoureusement","rituellement","rondement","royalement","rudement","Sacrément","sagement","sainement","salement","sardoniquement","sauvagement","savamment","schématiquement","sciemment","scientifiquement","secondairement","secondement","secrètement","secundo","seizièmement","sélectivement","sempiternellement","sensément","sensiblement","sentencieusement","sentimentalement","septièmement","sereinement","sérieusement","servilement","seulement","sévèrement","sexuellement","si","silencieusement","simplement","simultanément","sincèrement","singulièrement","sitôt","sixièmement","sobrement","socialement","soi disant","soigneusement" ,"solennellement","solidement","solitairement","sommairement","somptueusement","sordidement","sottement","soudain","soudainement","souplement","sourdement","sournoisement","souvent","souverainement","soigneusement","spécialement","spécieusement","spécifiquement","spirituellement","splendidement","spontanément","sporadiquement","sportivement","statiquement","stérilement","stoïquement","strictement","studieusement","stupidement","suavement","subitement","subjectivement","subrepticement","substantivement","subtilement","successivement","succinctement","suffisamment","superbement","superficiellement","supérieurement","superstitieusement","supra","suprêmement","surabondamment","sûrement","surtout","sus","symboliquement","symétriquement","synchroniquement","systématiquement","Tacitement","tant","tantôt","tard","tardivement","techniquement","tellement","témérairement","temporairement","tendancieusement","tendrement","terriblement","tertio","textuellement","théâtralement","théoriquement","tièdement","timidement","tortueusement","tôt","totalement","toujours","tout à fait","toutefois","traditionnellement","tragiquement","traîtreusement","tranquillement","transitivement","transversalement","treizièmement","très","trimestriellement","triomphalement","triplement","tristement","trivialement","troisièmement","trompeusement","trop","trop peu" ,"tout à fait","tumultueusement","typiquement","Ultérieurement","unanimement","uniènement","uniformément","unilatéralement","uniquement","universellement","un peu de","usuellement","utilement", "un peu","Vachement","vaguement","vainement","valablement","valeureusement","vaniteusement","verbalement","véridiquement","véritablement","à verse","verticalement","vertigineusement","vertueusement","vicieusement","victorieusement","vigoureusement","vilainement","vilement","volontairement","volontiers","voluptueusement","voracement","vraiment"};
	
	String[] PREPOSITION  = {"proche de", "contraction=de ici", "de ici", "voilà", "voici", "sur", "aujourde hui", "parce que", "À cause de", "À condition de", "À côté de",
							"Afin de", "Au cours de", "Au moyen de","Au nom de","D’avec","Dans le but de","De manière à",
							"De peur de","En dehors de","En dépit de","En face de","En raison de","En vertu de","En vue de",
							"Étant donné","Faute de","Grâce à","Jusqu’à","Loin de","Lors de","Par rapport à","Par suite de",
							"Par-delà","Quant à","Sauf à","Sous prétexte de","À","Après","Avant","Avec","Chez","Contre","Dans",
							"De","Derrière","Devant","Excepté","Malgré","Moyennant","Outre","Par","Parmi",
							"Passé","Pendant","Pour","Sans","Sauf","Selon","Sous","Suivant","Sur","Vers","Vu",
							"pour", "en raison de", "à cause de", "sous prétexte de", "grâce à", "de peur de",
							"à", "dans", "depuis", "durant", "pendant", "jusque à", "avant" ,"après", "pour", "afin",
							"dans", "à", "sous", "sur", "devant", "derrière", "devant", "face à", "contre",
							"chez", "jusque à",
							"pour", "afin", "envers", "dans le but de", "afin de", "pour", "en raison de", 
							"à cause de", "sous prétexte de", "grâce à", "de peur de",
							"pour", "en destination de", "vers", "à", "pour", "afin", "envers", "dans le but de", "afin de", "contre", "malgré",
							"pour", "en destination de", "vers", "à", "avec", "sans", "au moyen de", "grâce à",
							"pour", "en destination de", "vers", "à",
							"contre", "malgré", 
							"tout contraction=de abord", "À cause de","À condition de",
							"À côté de","Afin de","Au cours de","Au moyen de","Au nom de",
							"D’avec","Dans le but de","De manière à","De peur de","En dehors de",
		    				"En dépit de","En face de","En raison de","En vertu de","En vue de","Étant donnée","Faute de","Grâce à",
		    				"Jusqu’à","Loin de","Lors de","Par rapport à","Par suite de","Par-delà","Quant à","Sauf à","Sous prétexte de",
		    				"Il était une fois"};

	
	String[] prnmPersonnel = {"me", "te", "nous", "vous", "lui", "leur", "à moi", "à toi", "à nous", "à vous", "à lui", "à elle",
								"à eux", "à elles", "y", "de moi", "de toi", "de nous", "de vous", "de lui", "de elle", "contraction=de elle",
								"contraction=de eux", "de eux", "d'eux", "d'elle", "contraction=de elles", "me", "te", "se", "moi", "toi", "lui", "leur",
								"nous", "vous"};
	
	String[] interjectionList = {"ah", "oh", "hélas", "zut", "holà"};
	
	
	private ArrayList<ArrayList<ArrayList<String>>> saveSyntaxe;
	private ArrayList<ArrayList<String>> saveSchema;

	private String SAVEPATH;
	private int getSentence;

	public ChooseWordFunction() {

		this.verbe 					= verbe;
		this.voyelle 				= voyelle;
		this.adjectif 				= adjectif;
		this.expansian 				= expansian;
		this.nomCommun 				= nomCommun;
		this.subordonee				= subordonee;
		this.beforeVerb  			= beforeVerb;
		this.preposition 		    = preposition;
		this.ponctuation		 	= Ponctuation;
		this.conjonction 			= conjonction;
		this.groupeVerbal 			= groupeVerbal;
		this.beforeAdjectif			= beforeAdjectif;
		this.pronomPersonnel 		= pronomPersonnel;
		this.determinantCommun 		= determinantCommun;
		this.determinantPropre 		= determinantPropre;
		this.pronomPersonnelSyntaxe = pronomPersonnelSyntaxe;
		
	}


	public void ChooseWord(List<ArrayList<ArrayList<String>>> sentenceSyntaxe,
						  List<ArrayList<String>> sentenceText,
						  ArrayList<ArrayList<ArrayList<String>>> currentSyntaxeTreat,
						  ArrayList<ArrayList<String>> currentTextTreat, int getSentence, 
						  ArrayList<ArrayList<ArrayList<String>>> saveSyntaxe, 
						  ArrayList<ArrayList<String>> saveSchema, String SAVEPATH) throws IOException {

		
		this.saveSyntaxe = saveSyntaxe;
		this.saveSchema  = saveSchema;
		this.SAVEPATH    = SAVEPATH;
		this.getSentence = getSentence;
		
		ArrayList<ArrayList<ArrayList<String>>> first = firstTreatment(sentenceText, sentenceSyntaxe, getSentence);

		ArrayList<ArrayList<String>>   currentSyntaxe = first.get(0);
		ArrayList<String> 			   currentText    = first.get(1).get(0);
		int indexTirate       = Integer.parseInt(first.get(2).get(0).get(0));
		boolean thereIsTirate = Boolean.parseBoolean(first.get(3).get(0).get(0));

		treatSyntaxe(sentenceSyntaxe, currentSyntaxe, currentText, thereIsTirate, indexTirate, getSentence, sentenceText);

		forDisplay(currentSyntaxe, currentText);

		ArrayList<ArrayList<ArrayList<String>>> containerOut = new ArrayList<ArrayList<ArrayList<String>>>();
		returnTextAndSyntaxe(containerOut, currentText, currentSyntaxe, 0, false);

		currentSyntaxeTreat.add(currentSyntaxe);
		currentTextTreat.add(currentText);

	}

	
	
	private void forDisplay(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) throws IOException {

		for(int forDisplay=0;forDisplay < 8; forDisplay++) {System.out.println("");}

		String display = "";
		for(int nb=0; nb < currentSyntaxe.size(); nb++) {
			String word = currentText.get(nb);
			if (currentSyntaxe.get(nb).size() > 1) {display += "_ ";}
			else {display += word + " ";}
		}

		System.out.println(currentText);
		System.err.println(display);
		System.out.println(currentSyntaxe);
		

		
		for(int forDisplay=0;forDisplay < 7; forDisplay++) {System.out.println("");}
		
		for(int nb=0; nb < currentSyntaxe.size(); nb++) {
			String ok = "";
			String word = currentText.get(nb);
			if (currentSyntaxe.get(nb).size() > 1) {ok = "_";}
			else {ok = word;}
			System.out.println(currentSyntaxe.get(nb) + " " + ok);
		}
		
		
		writtingSyntaxe(currentText, currentSyntaxe);

		for(int forDisplay=0;forDisplay < 8; forDisplay++) {System.out.println("");}
	}
	
	private void writtingSyntaxe(ArrayList<String> currentText, ArrayList<ArrayList<String>> currentSyntaxe) throws IOException {

		
		File file = new File(this.SAVEPATH + "analyseSyntaxique.txt");
		if(!file.exists()) { file.createNewFile(); }
		
		String sentence = "Phrase numéro " + getSentence + ": "; 
		for (String element: currentText) { sentence += (element + " "); }

		try {
			FileWriter     writer = new FileWriter(file, true);
			writer.write(sentence);
			writer.write("\n");
			writer.write("\n");
			

			for(int nb=0; nb < currentSyntaxe.size(); nb++) {
				String ok = "";
				String word = currentText.get(nb);
				if (currentSyntaxe.get(nb).size() > 1) {ok = "_";}
				else {ok = word;}
				writer.write(currentSyntaxe.get(nb) + " " + ok);
				writer.write("\n");
			}
			writer.write("\n");
			writer.write("\n");
			writer.write("\n");
			writer.close();
		}catch (Exception e) {}
	}
	
	public void writtingLogo(String SAVEPATH) throws IOException {
		
		File file = new File(SAVEPATH + "analyseSyntaxique.txt");

		if(!file.exists()) { file.createNewFile(); }
		

		FileWriter     writer = new FileWriter(file, true);

		writer.write("\n");
		writer.write("\n");
		writer.write("\n");
		writer.write("											Jb & co Assureur Auto/Moto");
		writer.close();

	}
	
	
	
	
	private ArrayList<ArrayList<ArrayList<String>>> firstTreatment(List<ArrayList<String>> sentenceText,
																   List<ArrayList<ArrayList<String>>> sentenceSyntaxe, int getSentence) {

		System.out.println("\n\n\n" + sentenceText.get(getSentence));
		System.out.println("phrase de départ " + sentenceSyntaxe.get(getSentence));
		
		
		for (int index = 0; index < 5; index++) {System.out.println("");}


		ArrayList<String>  currentText = sentenceText.get(getSentence);
		ArrayList<String>  saveText = new ArrayList<String>(currentText);
		
		ArrayList<ArrayList<String>> currentSyntaxe = sentenceSyntaxe.get(getSentence);
		ArrayList<ArrayList<String>> saveSyntaxe = new ArrayList<ArrayList<String>>(currentSyntaxe);


		removeNOWord(currentSyntaxe, currentText);

		
		List<Integer> indexToremove = new ArrayList<Integer>();
		indexNoneSearch(currentSyntaxe, indexToremove, currentText);
		deleteNoneSearch(indexToremove, currentSyntaxe);



		AdverbPrepoSearch defini = new AdverbPrepoSearch(artDEF, "Article défini");
		searchingIfThereAreAdverb(defini, currentText, currentSyntaxe);

		AdverbPrepoSearch prnmPoss = new AdverbPrepoSearch(possessifPrnm, "Adjectif possessif");
		searchingIfThereAreAdverb(prnmPoss, currentText, currentSyntaxe);

		AdverbPrepoSearch prnmDemons = new AdverbPrepoSearch(prnmDem, "Adjectif démonstratif");
		searchingIfThereAreAdverb(prnmDemons, currentText, currentSyntaxe);

		AdverbPrepoSearch interjec = new AdverbPrepoSearch(interjectionList, "Interjection");
		searchingIfThereAreAdverb(interjec, currentText, currentSyntaxe);

		AdverbPrepoSearch indefini = new AdverbPrepoSearch(prnmInd, "Pronom indéfini");
		searchingIfThereAreAdverb(indefini, currentText, currentSyntaxe);

		AdverbPrepoSearch locutionSearch = new AdverbPrepoSearch(locution, "Nom commun");
		searchingIfThereAreAdverb(locutionSearch, currentText, currentSyntaxe);

		AdverbPrepoSearch adverSearch = new AdverbPrepoSearch(ADVERBLIST, "Adverbe");
		searchingIfThereAreAdverb(adverSearch, currentText, currentSyntaxe);

		AdverbPrepoSearch preproSearch = new AdverbPrepoSearch(PREPOSITION, "Préposition");
		searchingIfThereAreAdverb(preproSearch, currentText, currentSyntaxe);


		System.out.println(currentText);
		System.out.println(currentSyntaxe);

		
		isParticule(currentSyntaxe, currentText);
		isVerbIfParticule(currentSyntaxe, currentText);

		boolean thereIsTirate = false;
		int indexTirate = caseTirate(currentSyntaxe, currentText);
		if (indexTirate > 0) {thereIsTirate=true;}

		ArrayList<ArrayList<ArrayList<String>>> containerOut = new ArrayList<ArrayList<ArrayList<String>>>();
		returnTextAndSyntaxe(containerOut, currentText, currentSyntaxe, indexTirate, thereIsTirate);

		return containerOut;
	}

	

	
	
	private void searchingIfThereAreAdverb(AdverbPrepoSearch adverSearch, ArrayList<String> currentText,
			   							  ArrayList<ArrayList<String>> currentSyntaxe) {

		adverSearch.matchElementList(currentText, currentSyntaxe);
		System.out.println("Adverbe/Prepo list");
	}


	
	
	

	private void returnTextAndSyntaxe(ArrayList<ArrayList<ArrayList<String>>> containerOut,
									  ArrayList<String> currentText, ArrayList<ArrayList<String>> currentSyntaxe, int indexTirate,
									  boolean thereIsTirate) {

		ArrayList<ArrayList<String>> containerText = new ArrayList<ArrayList<String>>();
		containerText.add(currentText);
		
		ArrayList<ArrayList<String>> containerTirateIndex = new ArrayList<ArrayList<String>>();
		ArrayList<String> containerIndex = new ArrayList<String>();
		containerIndex.add(Integer.toString(indexTirate));
		containerTirateIndex.add(containerIndex);

		ArrayList<ArrayList<String>> containerTirateBoolean = new ArrayList<ArrayList<String>>();
		ArrayList<String> containerBoolean = new ArrayList<String>();
		containerBoolean.add(Boolean.toString(thereIsTirate));
		containerTirateBoolean.add(containerBoolean);
		
		containerOut.add(currentSyntaxe);
		containerOut.add(containerText);
		containerOut.add(containerTirateIndex);
		containerOut.add(containerTirateBoolean);
	}





	private void treatSyntaxe(List<ArrayList<ArrayList<String>>> sentenceSyntaxe, 
							  ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText,
							  boolean thereIsTirate, int indexTirate, int getSentence, List<ArrayList<String>> originalText) {

		for(int forDisplay=0;forDisplay < 2; forDisplay++) {System.out.println("");}
		for (int index=0; index < currentText.size(); index++) {
			try{System.out.println(currentSyntaxe.get(index) + " " + currentText.get(index) + " " + index);} catch (Exception e) {}
		}
		for(int forDisplay=0;forDisplay < 2; forDisplay++) {System.out.println("");}
		
		
		
		//All function are in "searchingInSentence".

	
		treatmentOfDictionnary(currentText, currentSyntaxe);

		for (int index=0; index <= 3; index++) {

			if      (index >= 2) { treatmentSyntaxe32(currentSyntaxe, currentText, originalText, getSentence); }
			else if (index == 0) { treatmentSyntaxe33(currentSyntaxe, currentText); }

			firstTreatmentSyntaxe(currentSyntaxe,  currentText, originalText.get(getSentence));
			secondTreatmentSyntaxe(currentSyntaxe, currentText, originalText.get(getSentence));
			thirdTreatmentSyntaxe(currentSyntaxe,  currentText);
			treatmentSyntaxe4(currentSyntaxe,  currentText);
			treatmentSyntaxe5(currentSyntaxe,  currentText);
			treatmentSyntaxe6(currentSyntaxe,  currentText);
			treatmentSyntaxe7(currentSyntaxe,  currentText);
			treatmentSyntaxe8(currentSyntaxe,  currentText);
			treatmentSyntaxe9(currentSyntaxe,  currentText, thereIsTirate);
			treatmentSyntaxe10(currentSyntaxe, currentText);
			treatmentSyntaxe11(currentSyntaxe, currentText);
			treatmentSyntaxe12(currentSyntaxe, currentText, thereIsTirate);
			treatmentSyntaxe13(currentSyntaxe, currentText, indexTirate);
			treatmentSyntaxe14(currentSyntaxe, currentText);
			treatmentSyntaxe15(currentSyntaxe, currentText, getSentence, sentenceSyntaxe, thereIsTirate, indexTirate, originalText);
			treatmentSyntaxe16(currentSyntaxe, currentText);
			treatmentSyntaxe17(currentSyntaxe, currentText);
			treatmentSyntaxe18(currentSyntaxe, currentText);
			treatmentSyntaxe19(currentSyntaxe, currentText);
			treatmentSyntaxe20(currentSyntaxe, currentText);
			treatmentSyntaxe21(currentSyntaxe, currentText);
			treatmentSyntaxe22(currentSyntaxe, currentText);
			treatmentSyntaxe23(currentSyntaxe, currentText);
			treatmentSyntaxe24(currentSyntaxe, currentText);
			treatmentSyntaxe25(currentSyntaxe, currentText);
			treatmentSyntaxe26(currentSyntaxe, currentText);

		}
		
		treatmentSyntaxe27(currentSyntaxe, currentText);
		treatmentSyntaxe28(currentSyntaxe, currentText);
		treatmentSyntaxe29(currentSyntaxe, currentText);
		treatmentSyntaxe30(currentSyntaxe, currentText);
		treatmentSyntaxe31(currentSyntaxe, currentText);
		treatmentSyntaxe34(currentSyntaxe, currentText);
	}

	
	
	
	










	private void treatmentSyntaxe34(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

	}



	private void treatmentSyntaxe33(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		removeIFEmpty(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "removeIFEmpty");
		
	}


	private void treatmentSyntaxe32(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText,
									List<ArrayList<String>> originalText, int getSentence) {
		haveNoFoundAfterArt(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "haveNoFound");

		cannotBeVerbFromDefinatePronomNonePrnm(currentSyntaxe, currentText, originalText, getSentence);
		isAProblemHere(currentSyntaxe, "cannotBeVerbFromDefinatePronomNonePrnm");
		
		isNomOrConjonction(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isNomOrConjonction");
		
		pronomRelOrConjonction(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "pronomRelOrConjonction");
		
	}


	private void treatmentSyntaxe31(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		AdjectifOrNomCommunNcTheTwo(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "AdjectifOrNomCommunNcTheTwo");
		
		twoNcFollowing(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "twoNcFollowing");
		
		isAdjPrnIndéfiniLastPrepLastX2Nc(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isAdjPrnIndéfiniLastPrepLastX2Nc");
		
		adjDemonstratifBeforeVerbIsPrnm(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "adjDemonstratifBeforeVerbIsPrnm");
		
		oneWordTwoWord(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "oneWordTwoWord");
		
		choicePPOrAdj(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "oneWordTwoWord");
		
		removeInterjectionIfAdj(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "removeInterjectionIfAdj");
		
	}


	private void treatmentSyntaxe30(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		isNcOrAdjIfTirate(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "notTwoVerbWithoutDifferentTime");
		
		isNcOrAdjPossessif(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isNcOrAdjPossessif");
		
		ncAdjCantBeNc(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "ncAdjCantBeNc");
		
		isNcOrAdvLastAdjNumeral(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isNcOrAdvLastAdjNumeral");
		
		isAdjOrVerbBeforeAdv(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isNcOrAdvLastAdjNumeral");
		
		isNomCommunEndGn(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isNomCommunEndGn");
		
		finalChoiceArtPrepOrParti(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "finalChoiceArtPrepOrParti");
		
		conjoncVerbPrep(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "conjoncVerbPrep");
		
		nmChossePrepNm(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "nmChossePrepNm");
		
		choiceBeetWeenAdjAndNc(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "choiceBeetWeenAdjAndNc");
		
	}


	private void treatmentSyntaxe29(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		isNomCommunIfAdjOrNcLastWord(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isNomCommunIfAdjOrNcLastWord");
		
		beetweenTwoAdverbCanBeCOnjonction(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "beetweenTwoAdverbCanBeCOnjonction");
		
		adjectifOrArticleDefini(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "adjectifOrArticleDefini");
		
		adjectifOrVerbNextIsVerbeInf(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "adjectifOrVerbNextIsVerbeInf");
		
		beforeInfinitif(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "beforeInfinitif");
		
		isArtBeforeNomPropre(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isArtBeforeNomPropre");
		
		PrnmPersoOrArtDefNextGn(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "PrnmPersoOrArtDefNextGn");

		caseQueAndNeg(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "caseQueAndNeg");

		notTwoVerbWithoutDifferentTime(currentSyntaxe, true);
		isAProblemHere(currentSyntaxe, "notTwoVerbWithoutDifferentTime");
		
	}


	private void treatmentSyntaxe28(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		lastElementIsAdjOrNc(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "lastElementIsAdjOrNc");
		
		conjonRelOrAdv(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "conjonRelOrAdv");
		
		isPonctuationList(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "isPonctuationList");
		
		isntConjonctionWithNcFromLastAndNext(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isntConjonctionWithNcFromLastAndNext");
		
		isVerbOrAdjIfPrepoAfter(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isVerbOrAdjIfPrepoAfter");
		
		isCompositeVerbAdvMidle(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isCompositeVerbAdvMidle");
		
		isNcIfArtBefore(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isNcIfArtBefore");
		
		
	}


	private void treatmentSyntaxe27(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		cannotBeAnAdverbeCausePrepoAdverb(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "cannotBeAnAdverbeCausePrepoAdverb");
		
		removeTwoFollowingPrepAdverbe(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "cannotBeAnAdverbeCausePrepoAdverb");
		
		makeItPrnmPersonel(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "makeItPrnmPersonel");
		
		adverbeOrPrepo(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "adverbeOrPrepo");
		
		isAdjNumOrIndefini(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "isAdjNumOrIndefini");
		
		ncOrPrnm(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "ncOrPrnm");
		
		isArticleIfNextIsGn(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isArticleIfNextIsGn");
		
		
	}


	private void treatmentSyntaxe26(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		isArtAfterPrepo(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isArtAfterPrepo");
		
		isNomCommunOrVerbBeforePronm(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isNomCommunOrVerbBeforePronm");
		
		
		isArticleBeforeNumeral(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isArticleBeforeNumeral");
		
		donrRaiseVerbBeetweenTwoAdjNc(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "donrRaiseVerbBeetweenTwoAdjNc");
		
		ncOrVerbBeforeRel(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "ncOrVerbBeforeRel");
		
		isNoneButFinishBymMent(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "isNoneButFinishBymMent");
		
		
	}


	private void treatmentSyntaxe25(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		isArtOrNcBeforeAdjAfterIsNc(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isArtOrNcBeforeAdjAfterIsNc");
		
		deleteDéfinitionsCorespondantàVotreRecherche(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "deleteDéfinitionsCorespondantàVotreRecherche");
		
		adjectifOrNomCommunNextAdj(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "adjectifOrNomCommunNextAdj");
		
		isPrepositionIfNextIsAdv(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isPrepositionIfNextIsAdv");
		
		isNomCommunIfNextPrepoNc(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isNomCommunIfNextPrepoNc");
		
		PrnmPersoOrArtDefNextGn(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "PrnmPersoOrArtDefNextGn");
		
		isNotPrepositionIfLastIsPronomPersonnel(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "adjectifOrArticleDefini");
		
		isNcOrAdjIfNoNc(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isNcOrAdjIfNoNc");
		
	}


	private void treatmentSyntaxe24(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		cannotBeBeforeNc(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "cannotBeBeforeNc");
		
		isVerbBeforeAdvAndAfterParticule(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "cannotBeBeforeNc");
		
		cannotBeArtAfterArt(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "cannotBeArtAfterArt");
		
		cannotBeVerbIfAllLastPropoAreDet(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "cannotBeVerbIfAllLastPropoAreDet");
		
		prepOrArtOnlyNc(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "prepOrArtOnlyNc");
		
		adjNuméralInInteger(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "adjNuméralInInteger");
		
		PrnmIndéfiniOrAdj(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "PrnmIndéfiniOrAdj");
		
		raiseInformationOnFromDico(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "raiseInformationOnFromDico");
		
	}


	private void treatmentSyntaxe23(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		isPronomPersonnel(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "isPronomPersonnel");
		
		caseTirateVerbePronom(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "caseTirateVerbePronom");
		
		isArticleOrPrnmPersoAfterNc(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isArticleOrPrnmPersoAfterNc");
		
		itsVerb(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "itsVerb");
		
		isVerb(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isVerb");

		isVerbAfterPrnm(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isVerbAfterPrnm");
		
		
		cannotBeNcBeforeArt(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "cannotBeNcBeforeArt");
		
		notAdvEndSearch(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "notAdvEndSearch");
		
		
	}


	private void treatmentSyntaxe22(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		AdjOrNm(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "AdjOrNm");
		
		nomCommunV2FromLastSentence(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "nomCommunV2FromLastSentence");
		
		lastIsVerbNextIsVerbRemoveNextIfNotPP(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "cannotBeVerbFromPronom");

		nmOrAdj(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "nmOrAdj");
		
		isUnCase(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "isUnCase");

		adverbeOrNc(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "adverbeOrNc");

		
		isConjonctionOrPronom(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "isConjonctionOrPronom");
		
		
		
	}


	private void treatmentSyntaxe21(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		isNotConjonctionOrIfPrepoBefore(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "isNotConjonctionOrIfPrepoBefore");
		
		
		caseArtPartitifAndIndefini(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "caseArtPartitifAndIndefini");
		
		maisAndCarIsConjonction(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "maisIsConjonction");
		
		isArticleIndefiniRelatif(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isArticleIndefiniRelatif");
		
		choiceArtIndefini(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "choiceArtIndefini");
		
		
		caseToutes(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "caseToutes");
		
		isVerbOrNcLastIsNc(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isVerbOrNcLastIsNc");
		
	}


	private void treatmentSyntaxe20(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		cantBeAdj(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "cantBeAdj");
		
		unRemoveAdjectif(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "unRemoveAdjectif");
		
		isPrepositionIfNomCOmmunLast(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isPrepositionIfNomCOmmunLast");
		
		isLastWordCantBeAdjIfBeforeArt(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isLastWordCantBeAdjIfBeforeArt");


		isNotAdjIfNcBeforeNcAfter(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isNotAdjIfNcBeforeNcAfter");
		
		syntaxeIsCommun(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "syntaxeIsCommun");
		
		cannotBeAdjBeforeArt(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "cannotBeAdjBeforeArt");

		
	}


	private void treatmentSyntaxe19(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		replaceFormeByNomCommun(currentSyntaxe, "Forme de nom commun", "Nom commun", "glawy");
		isAProblemHere(currentSyntaxe, "replaceFormeByNomCommun");
		
		replaceFormeByNomCommun(currentSyntaxe, "Forme d’adjectif", "Adjectif", "Forme d’adjectif ");
		isAProblemHere(currentSyntaxe, "replaceFormeByNomCommun");


		
		removeTwoSameSyntaxe(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "removeTwoSameSyntaxe");
		
		isPrepositionOrArticleCaseDe(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "isPrepositionOrArticleCaseDe");
		
		cannotBeAdjWithoutNomCommun(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isPrepositionOrArticleCaseDe");

		lengthTwoBeforeArtIsNc(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "lengthTwoBeforeArtIsNc");
		
		adjectifOrAdverbeNotAdverbeCauseBegening(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "adjectifOrAdverbeNotAdverbeCauseBegening");
		
		enPronomIfAfterPronomAndBeforeVerb(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "enPronomIfAfterPronomAndBeforeVerb");
		
	}


	private void treatmentSyntaxe18(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		cannotBeAverbAfterArt(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "cannotBeAverbAfterArt");
		
		pronomOrAdjectifIndefini(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "pronomOrAdjectifIndefini");

		
		isntAnadjectifAfterAverb(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "isntAnadjectifAfterAverb");
		

		isAdverbe(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isAdverbe");
		
		adjectivIfNextCnjncThenAdj(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "adjectivIfNextCnjncThenAdj");
		

		canBeVerbBeforeVerbAfterPrepo(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "canBeVerbBeforeVerbAfterPrepo");
		
	}


	private void treatmentSyntaxe17(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		formeAdjOrNomCommun(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "formeAdjOrNomCommun");
		
		notTwoVerbWithoutDifferentTime(currentSyntaxe, false);
		isAProblemHere(currentSyntaxe, "notTwoVerbWithoutDifferentTime");
		
		contractionCase(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "contractionCase");
		
		deIsArticleDefiniBehindAnAdjectiv(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "deIsArticleDefiniBehindAnAdjectiv");
	
		deIsPreposition(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "deIsPreposition");
		
		letterIsPronomPersonnel(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "letterIsPronomPersonnel");
		
		cantBeANomCommunAfterAdjectifAndBeforePreposition(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "cantBeANomCommunAfterAdjectifAndBeforePreposition");
		
		
	}


	private void treatmentSyntaxe16(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		caseMais(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "caseMais");
		
		comboArticleAdjNomCommun(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "comboArticleAdjNomCommun");
		
		isPronomOrArt(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isPronomOrArt");
		
		artLaLeLApo(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "artLaLeLApo");
		
		verbOrNomCommun(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "verbOrNomCommun");
		
		participePastDelete(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "participePastDelete");

		deleteNomCommunIfCurrentNomCommun(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "deleteNomCommunIfCurrentNomCommun");
		
	}


	private void treatmentSyntaxe15(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText, 
								   int getSentence, List<ArrayList<ArrayList<String>>> sentenceSyntaxe, 
								   boolean thereIsTirate, int indexTirate, List<ArrayList<String>> originalText) {
	
		cantBeVerbDApostroph(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "cantBeVerbDApostroph");
		
		isOrNotInterjection(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isOrNotInterjection");
		
		isVerbeIfSContraction(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isVerbeIfSContraction");

		cannotBeAdjectifAfterPrepo(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "cannotBeAdjectifAfterPrepo");
		
		if (getSentence > 0){
			isConjonctionOrPronomCANBEfromLastSentence(sentenceSyntaxe, currentSyntaxe,
					currentText, getSentence, thereIsTirate, indexTirate, originalText);
		}
		
	}


	private void treatmentSyntaxe14(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		verbOrNomCommun(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "verbOrNomCommun");
		
		isVerbeOrNomCommunPronomAfter(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "isVerbeOrNomCommunPronomAfter");
		
		differenciatePronomAdjdemonstratif(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "differenciatePronomAdjdemonstratif");
			
		pronomRelatifOrIndefini(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "pronomRelatifOrIndefini");
		
		isDeterminant(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isDeterminant");
		
		deleteNomCommunIfCurrentNomCommun(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "deleteNomCommunIfCurrentNomCommun");
		
		partitifOrPrepoIsTheSameForNow(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "partitifOrPrepoIsTheSameForNow");

		
	}


	private void treatmentSyntaxe13(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText, int indexTirate) {
		pronomVerbCaseTirate(currentSyntaxe, indexTirate);
		isAProblemHere(currentSyntaxe, "pronomVerbCaseTirate");
		
		cantBeVerbIfParticipePassé(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "cantBeVerbIfParticipePassé");
		
		cannotBeAnAdjectif(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "cannotBeAnAdjectif");
		
		caseDe(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "caseDe");
		
		pronomPersonnelOrArticle(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "pronomPersonnelOrArticle");
		
	}


	private void treatmentSyntaxe12(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText, boolean thereIsTirate) {
		ConjonctionPronomAdverbe(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "ConjonctionPronomAdverbe");
		
		isNotNomCommun(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isNotNomCommun");
	
		verbeTiratePronom(currentSyntaxe, thereIsTirate);
		isAProblemHere(currentSyntaxe, "verbeTiratePronom");

		adjectifCaseTirateIfVerbeOrAdjectif(currentSyntaxe, thereIsTirate);
		isAProblemHere(currentSyntaxe, "adjectifCaseTirateIfVerbeOrAdjectif");

		conditionlettre(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "conditionlettre3");
			
		isInterogation(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isInterogation");
		
		queCase(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "queCase");
		
	}


	private void treatmentSyntaxe11(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		cantBeNomCommunVerbPrepoNomCommun(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "cantBeNomCommunVerbPrepoNomCommun");
		
		cannotAfterDeterminant(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "cannotAfterDeterminant");

		caseOu(currentText, currentSyntaxe);
		isAProblemHere(currentSyntaxe, "caseOu");
	
		isPrepositionOrPronomOrAdverbe(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isPrepositionOrPronomOrAdverbe");

		isVerbeOrNotAfterPreposition(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isVerbeOrNotAfterPreposition");
	
		artLaLeLApo(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "artLaLeLApo");
	
		
	}


	private void treatmentSyntaxe10(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		isAdjectifIsNextIsArt(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isAdjectifIsNextIsArt");
		
		deleteNomCommunIfIsDeterminant(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "deleteNomCommunIfIsDeterminant");
		
		isAdjectifOrVerb(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isAdjectifOrVerb");
		
		isInterogation(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isInterogation");

		deleteNomCommunIfCurrentNomCommun(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "deleteNomCommunIfCurrentNomCommun");
		
		cannotBeVerbBeforeAdjectif(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "cannotBeVerbBeforeAdjectif");
			
		cannotBeAnArticle(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "cannotBeAnArticle");
		
	}


	private void treatmentSyntaxe9(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText, boolean thereIsTirate) {
		beforeVerb(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "beforeVerb");
		
		afterVerb(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "afterVerb");

		cannotBeFromLastVerbe(currentSyntaxe, thereIsTirate);
		isAProblemHere(currentSyntaxe, "cannotBeFromLastVerbe");

		
		
		isArticlePartitif(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isArticlePartitif");
		
		isArticleDefiniOrIndefini(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "isArticleDefiniOrIndefini");
		
	}


	private void treatmentSyntaxe8(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		isAdjectifOrNomCommunNextIsAdjectif(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isAdjectifOrNomCommunNextIsAdjectif");

		isPreposition(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isPreposition");

		
		isPrepositionV2(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "isPrepositionV2");
		
		modifieVerbeToVerb(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "adjectifOrArticleDefini");
		
		isNotVerbIfNotGerondifOrInfinitif(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isNotVerbIfNotGerondifOrInfinitif");

		
		conditionlettre(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "conditionlettre2");
		
	}


	private void treatmentSyntaxe7(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		defineNomPropreFromFirstLetter(currentText, currentSyntaxe);
		isAProblemHere(currentSyntaxe, "defineNomPropreFromFirstLetter");
		
		raiseNomCommunIfPronom(currentText, currentSyntaxe);
		isAProblemHere(currentSyntaxe, "raiseNomCommunIfPronom");

		isArticleOrPronom(currentText, currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isArticleOrPronom");

		deleteNomCommunIfIsDeterminant(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "deleteNomCommunIfIsDeterminant");
		
		conditionlettre(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "conditionlettre1");
		
		verbOrNomCommun(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "verbOrNomCommun");
	
		
	}


	private void treatmentSyntaxe6(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		ConjonctionPronomAdverbe(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "ConjonctionPronomAdverbe");
		
		afterVerb(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "afterVerb");
		
		foundVerbTwoTime(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "foundVerbTwoTime");

		isAdverbelà(currentText, currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isAdverbelà");

		isVerbeOrAdjectifInterrogation(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isVerbeOrAdjectifInterrogation");
	
		
	}


	private void treatmentSyntaxe5(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		delEmptySyntaxe(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "delEmptySyntaxe");

		isNotNomCommun(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isNotNomCommun");

		isNotPronom(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "isNotPronom");
		
		caseDeIsNotNomCommun(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "caseDeIsNotNomCommun");

		beforeVerb(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "beforeVerb");
		
	}


	private void treatmentSyntaxe4(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		nomCommunOrVerbNextIsAdj(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "nomCommunOrVerbNextIsAdj");

		isDeterminant(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "isDeterminant");

		caseLes(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "caseLes");

		emptyAjustIt(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "emptyAjustIt");
		
	}
	
	private void thirdTreatmentSyntaxe(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		//Next word is Nom commun. Choice adjectif.
		nextOnlyNcitsAdj(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "nextOnlyNcitsAdj");
		
		//Word is "ne". Choice particle.
		isWordNeIsParticule(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "isWordNeIsParticule");
		
		//After a particule choice verbe.
		afterParticuleCanBeVerb(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "afterParticuleCanBeVerb");
		
		//Last word of sentence propose Interjection raise it.
		cannotBeInterjectionLastWord(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "cannotBeInterjectionLastWord");
		
		//Replace forme d'adjectif by adjectif.
		replaceFormAdjByAdj(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "replaceFormAdjByAdj");
	}


	private void treatmentOfDictionnary(ArrayList<String> currentText, ArrayList<ArrayList<String>> currentSyntaxe) {

		//Dico put Verbe if word's infinitif.
		modifieVerbeToVerb(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "DICO TREATMENT - adjectifOrArticleDefini");
		
		//Remove empty word ("").
		removeEmptyElement(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "DICO TREATMENT - removeEmptyElement");
		
		//If word isn't correct dictionnary put typo no found.
		removeVarianteTypoFromScrap(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "DICO TREATMENT - removeVarianteTypoFromScrap");
		
		//No found word dico suggests word's with "correspondant a votre recherche". 
		deleteDéfinitionsCorespondantàVotreRecherche(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "DICO TREATMENT - deleteDéfinitionsCorespondantàVotreRecherche");
		
		//Dico suggest word in web with sentence: "sur web".
		deleteSurWeb(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "DICO TREATMENT - deleteSurWeb");

		//Delete tirate.
		ifTirateElement(currentText, currentSyntaxe);
		isAProblemHere(currentSyntaxe, "DICO TREATMENT - ifTirateElement");

	}
	


	private void firstTreatmentSyntaxe(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText,
			ArrayList<String> originalSentence) {

		//
		removeInCaseWordWithSyntaxe(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "FIRST TREATMENT - removeInCaseWordWithSyntaxe");
		
		removeEmptyElement(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "FIRST TREATMENT - removeEmptyElement");
		
		isAdjectifNumeralByNumber(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "FIRST TREATMENT - isAdjectifNumeralByNumber");
		
		conditionQuelque(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "FIRST TREATMENT - conditionQuelque");
		
		removeAndKeepPonctuation(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "FIRST TREATMENT - removeAndKeepPonctuation");
		
		saCase(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "FIRST TREATMENT - saCase");

	}
	


	private void secondTreatmentSyntaxe(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText,
			ArrayList<String> originalSentence) {


		equalsVerbe(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "SECOND TREATMENT - equalsVerbe"); 
		
		pronmRelOrInterro(currentSyntaxe, currentText);
		isAProblemHere(currentSyntaxe, "SECOND TREATMENT - pronmRelOrInterro"); 
		
		isAdjNumeralOrNomCommun(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "SECOND TREATMENT - isAdjNumeralOrNomCommun"); 
		
		isNomCommunAfterNumeral(currentSyntaxe, originalSentence);
		isAProblemHere(currentSyntaxe, "SECOND TREATMENT - isNomCommunAfterNumeral"); 
		
		nomCommunOrForm(currentSyntaxe);
		isAProblemHere(currentSyntaxe, "SECOND TREATMENT - nomCommunOrForm"); 
	}


	
	



	private void AdjectifOrNomCommunNcTheTwo(ArrayList<ArrayList<String>> currentSyntaxe) {

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			ArrayList<String> current = currentSyntaxe.get(syntaxe);

			boolean currentIsTwo = current.size() == 2;
			boolean currentContainsAdj = listEqualsElement(current, "Adjectif");
			boolean currentContainsNc  = listEqualsElement(current, "Nom commun");

			
			if (currentIsTwo && currentContainsAdj && currentContainsNc) {
				removeFromList(current, "Adjectif");
				System.out.println("AdjectifOrNomCommunNcTheTwo " + syntaxe);
			}
		}
	}

	
	
	private void isArticleOrPrnmPersoAfterNc(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Choose article if last's nom commun and current contains pronom or definate article.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);

			//Last's one and is Nom commun
			boolean lastOne    = last.size() == 1;
			boolean lastIsNc   = listEqualsElement(last, "Nom commun");

			//Current's two and contains pronom personnel and article personnel.
			boolean currentTwo = current.size() == 2;
			boolean containsPrnm = listEqualsElement(current, "Pronom personnel");
			boolean containsArt  = listContains(current, "défini");

			//Dont remove article.
			if (lastOne && lastIsNc && currentTwo && containsPrnm && containsArt) {
				dontRemoveFromListContains(current, "défini");
				System.out.println("isArticleOrPrnmPersoAfterNc " + syntaxe);
			}
		}
	}


	private void choicePPOrAdj(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//Choice Participe past or adjectif (A PP can be use as adjective).
		//Choice PP if we got an accent.
		//Only PP or Adjectif's presents.

		//it's a last condition in generally it treat that.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			String  word     = currentText.get(syntaxe);
			String  endWord  = "";
			
			//Recuperate 2 last letters.
			boolean wordIsLength = word.length() >= 2;
			if (wordIsLength) { endWord   = word.substring(word.length() - 2, word.length() - 1); }
			
			//Verify last letters contains an accent.
			boolean lastWordIsEaccent = endWord.contains("é");

			//Current contains verbe PP or adjectiv.
			boolean currentIsNotOne   = current.size() > 1;
			boolean containsVrb       = listContains(current, "verbe#");
			boolean containsAdj       = listEqualsElement(current, "Adjectif");
			
			//is adjectif and verbe and accent remove adjective.
			if (currentIsNotOne && containsVrb && containsAdj && lastWordIsEaccent) {
				dontRemoveFromListContains(current, "verbe#");
				System.out.println("choicePPOrAdj " + syntaxe);
			}
			
			//is adjectif and verbe and no accent remove verbe.
			else if (currentIsNotOne && containsVrb && containsAdj && !lastWordIsEaccent) {
				dontRemoveFromListElement(current, "Adjectif");
				System.out.println("choicePPOrAdj " + syntaxe);
			}
		}
	}


	private void adjDemonstratifBeforeVerbIsPrnm(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Make an adjectif demonstrative to Pronom if it before a verbe.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			//Next's verbe and current contains demonstratif.
			boolean nextOne  = next.size() == 1;
			boolean nextVerb = listContains(next, "verbe#"); 
			boolean currentContainsDem = listContains(current, "émonstratif");
			
			//Make adjectif to pronom.
			if (nextOne && nextVerb && currentContainsDem) {
				current.clear();
				current.add("Pronom démonstratif");
				System.out.println("adjDemonstratifBeforeVerbIsPrnm " + syntaxe);
			}
		}
	}


	private void isAdjPrnIndéfiniLastPrepLastX2Nc(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//From last 2 elements (Nom commun and preposition), choice adjectif indéfini.

		for (int syntaxe = 2; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> lastX2  = currentSyntaxe.get(syntaxe - 2);
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			
			//(current - 2) contains Nom commun and is one.
			boolean lastX2Nc         = listEqualsElement(lastX2, "Nom commun") && lastX2.size() == 1;
			
			//(current - 1) contains Préposition and is one.
			boolean lastPrep         = listEqualsElement(last,   "Préposition") && last.size() == 1;
			
			//current contains that:
			boolean currentContains1 = listEqualsElement(current, "Forme d’adjectif indéfini");
			boolean currentContains2 = listEqualsElement(current, "Nom commun");
			boolean currentContains3 = listEqualsElement(current, "Adjectif indéfini");
			boolean currentContains4 = listEqualsElement(current, "Forme de pronom indéfini");
			boolean currentContains5 = listEqualsElement(current, "Forme d’adverbe");
			boolean currentContains6 = listEqualsElement(current, "Pronom indéfini");
			
			boolean contanening = currentContains1 && currentContains2 && currentContains3 &&
					              currentContains4 && currentContains5 && currentContains6;
			
			//if lastx2 is a name, last is preposition and current contains that. Choice adjective indéfini. 
			if (lastX2Nc && lastPrep && contanening) {
				dontRemoveFromListElement(current, "Adjectif indéfini");
				System.out.println("isAdjPrnIndéfiniLastPrepLastX2Nc " + syntaxe);
			}
		}
	}



	
	
	private void removeInterjectionIfAdj(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Last choice beetween interjection or adjective (a last condition's treat interjection).
		//Choice adjective.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			//Current's two and contains interjection and adjective.
			boolean currentTwo    = current.size() == 2;
			boolean containsInter = listEqualsElement(current, "Interjection");
			boolean containsAdj   = listEqualsElement(current, "Adjectif");
			
			//Remove interjection.
			if (currentTwo && containsInter && containsAdj) {
				dontRemoveFromListElement(current, "Adjectif");
				System.out.println("removeInterjectionIfAdj " + syntaxe);
			}
		}
	}
	
	
	
	private void choiceBeetWeenAdjAndNc(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Make a choice beetween adjective and nom commun. Remove adjective.
		//A next condition associate two nom commun following.
		//l'esclave fossile.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			//Current's two and contains nom commun or adjective.
			boolean currentIsTwo = current.size() == 2;
			boolean containsNc   = listEqualsElement(current, "Nom commun");
			boolean containsAdj  = listEqualsElement(current, "Adjectif");
			
			//Next's one and nom commun.
			boolean nextOne      = next.size() == 1;
			boolean nextIsNc     = listEqualsElement(next, "Nom commun");

			//Remove adjective.
			if (currentIsTwo && containsNc && containsAdj && nextOne && nextIsNc) {
				current.clear(); current.add("Nom commun");
				System.out.println("choiceBeetWeenAdjAndNc " + syntaxe);
			}
		}
	}
	

	private void isNoneButFinishBymMent(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//It's case none but word finish by ment. It's an adverbe.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			String            word    = currentText.get(syntaxe);
			String            endWord = "";
			
			//Try to recup last 5 last letters.
			try { endWord = word.substring(word.length() - 5, word.length()); }catch (Exception e) {}

			//Current's one is none found syntaxe and finish by ement.
			boolean isOne      = current.size() == 1;
			boolean isNone     = listEqualsElement(current, "None");
			boolean endIsEment = endWord.equalsIgnoreCase("ement");
			
			//Pass None to adverbe.
			if (isOne && isNone && endIsEment) {
				current.clear();
				current.add("Adverbe");
				System.out.println("isNoneButFinishBymMent " + syntaxe);
			}
		}
	}


	private void oneWordTwoWord(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//There is a compose word none treat. Make them one.
		
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		
		//First word, second word, function word. 
		String[] oneWordTwoWord = {"parce", "contraction=que", "Pronom relatif", 
				};

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			//Current and next word.
			String text1 = currentText.get(syntaxe);
			String text2 = currentText.get(syntaxe + 1);

			//Run the oneWordTwoWord array, three by three.
			for (int index=0; index < oneWordTwoWord.length / 3; index+=3) {
				
				String word1 = oneWordTwoWord[index];	  //First    word
				String word2 = oneWordTwoWord[index + 1]; //second   word
				String func  = oneWordTwoWord[index + 2]; //function word
				
				boolean oneMatching = text1.equalsIgnoreCase(word1);
				boolean twoMatching = text2.equalsIgnoreCase(word2);
	
				//Remove next word and next syntaxe. Make them one in text.
				if (oneMatching && twoMatching) {
					current.set(syntaxe, func);
					currentText.set(syntaxe, text1 + " " + text2);
					toRemove.add(syntaxe + 1);
					System.out.println("oneWordTwoWord " + syntaxe);
				}
					
			}
		}
		
		//Remove from syntaxe and text.
		int add = 0;
		for (int index: toRemove) {
			currentSyntaxe.remove(index + add);
			currentText.remove(index + add);
			add -= 1;
		}
	}
	
	
	
	private void twoNcFollowing(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//Two same syntaxe are following. Make them one from syntaxe and text.
		//For example two nom commun are following:
		//[Article], [Nom commun], [Nom commun] [Verbe]
		//le, esclave, fossile, mange
		//[Article], [Nom commun], [Verbe] -> le, esclave fossile, mange
		
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);

			String currentWord = currentText.get(syntaxe);
			String nextWord    = currentText.get(syntaxe + 1);
			
			boolean currentOne = current.size() == 1;
			boolean nextOne    = next.size() == 1;
			
			boolean currentNc     = listEqualsElement(current, "Nom commun");
			boolean nextNc        = listEqualsElement(next,    "Nom commun");
		
			boolean currentArtInd = listEqualsElement(current, "Article indéfini");
			boolean nextArtInd    = listEqualsElement(next,    "Article indéfini");

			if (currentOne && nextOne && ((currentNc && nextNc) || (currentArtInd && nextArtInd))) {
				toRemove.add(syntaxe + 1);
				currentText.set(syntaxe, currentWord + " " + nextWord);
			}
		}
		int add = 0;
		for (int index: toRemove) {
			currentSyntaxe.remove(index + add);
			currentText.remove(index + add);
			add -= 1;
		}
	}


	private void nmChossePrepNm(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Current is not one. Choice Nom commun from the next 2 word's.
		//If next's préposition and (next + 2)'s Nom commun.
		//le rouge de vigne.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 2; syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			ArrayList<String> nextX2  = currentSyntaxe.get(syntaxe + 2);
			
			boolean currentNotOne     = current.size() > 1;
			boolean currentHaveNc     = listEqualsElement(current, "Nom commun");

			boolean nextIsPrep        = listEqualsElement(next,   "Préposition");
			boolean nextX2Nm          = listEqualsElement(nextX2, "Nom commun");
			
			if (currentNotOne && currentHaveNc && nextIsPrep && nextX2Nm) {
				dontRemoveFromListElement(current, "Nom commun");
				System.out.println("nmChossePrepNm " + syntaxe);
			}
		}
	}


	private void isVerb(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Sometimes verb dictionnary and word dictionnary are in competition in case infinitif.
		//One put Verbe and we put verbe#. Remove Verbe.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean isNotOne      = current.size() > 1;
			boolean containsVerbe = listEqualsElement(current, "verbe#");
			
			if (isNotOne && containsVerbe) {
				current.clear();
				current.add("verbe#");
				System.out.println("isVerb " + syntaxe);
			}
		}
	}
	
	private void conjoncVerbPrep(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Dont remove verbe if last's conjonction and next nom commun.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			boolean lastOne 	  = last.size() == 1;
			boolean currentNotOne = current.size() > 1;
			boolean nextOne 	  = next.size() == 1;
			
			boolean lastIsConjonc = listEqualsElement(last, "Conjonction de coordination");
			boolean currentCntNc  = listEqualsElement(current, "Nom commun");
			boolean currentCntVrb = listContains(current, "verbe#");
			
			boolean nextIsPrep    = listEqualsElement(next, "Préposition");
			
			if (lastOne && currentNotOne && nextOne && lastIsConjonc && currentCntNc && currentCntVrb && nextIsPrep) {
				dontRemoveFromListContains(current, "verbe#");
				System.out.println("conjoncVerbPrep " + syntaxe);
			}
		}
	}


	private void finalChoiceArtPrepOrParti(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Choice article in case we got article, préposition and partitif.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean currentContains1 = listEqualsElement(current, "Forme d’article indéfini");
			boolean currentContains2 = listEqualsElement(current, "Préposition");
			boolean currentContains3 = listEqualsElement(current, "Article partitif");
			
			
			if (currentContains1 && currentContains2) {
				dontRemoveFromListElement(current, "Forme d’article indéfini");
				System.out.println("finalChoiceArtPrepOrParti " + syntaxe);
			}
		}
	}
	
	private void nmOrAdj(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Choice adjective in case next's syntaxe's nom commun.
		//But from others function choice to keep nm.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			boolean nextOne    = next.size() == 1;
			boolean nextIsNc   = listEqualsElement(next, "Nom commun");
			
			boolean nextConditions = nextOne && nextIsNc;
			
			boolean lastOne = last.size() > 1;
			boolean lastArt = thisListEqualList(last, determinantCommunWithotuAdj);
			
			boolean currentTwo = current.size() == 2;
			boolean containsNm  = listEqualsElement(current, "Nom commun");
			boolean containsAdj = listEqualsElement(current, "Adjectif");

			if (currentTwo && containsNm && containsAdj && nextConditions && lastArt) {
				removeFromList(current, "Adjectif");
				System.out.println("nmOrAdj " + syntaxe);
			}
		}
	}

	private void isNomCommunEndGn(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Choice to keep nm from the last 3 word. We are in a nominal group.
		//Because from the last 3 words got a verbe.
		
		for (int syntaxe = 3; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> lastX3  = currentSyntaxe.get(syntaxe - 3);
			ArrayList<String> lastX2  = currentSyntaxe.get(syntaxe - 2);
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean lastX3Verb = listContains(lastX3, "verbe#");
			boolean lastX2Verb = listContains(lastX2, "verbe#");
			boolean nastX1Verb = listContains(last,   "verbe#");
			
			boolean lastCnj  = listEqualsElement(last, "Conjonction de coordination");
			boolean lastAdv  = listEqualsElement(last, "Adverbe");
			
			boolean liaisonLast = lastCnj || lastAdv;
			
			
			boolean currentContainsVerb = listContains(current, "verbe#");
			boolean containsNc = listEqualsElement(current, "Nom commun");

			boolean isTwo = current.size() == 2;
			
			
			if((lastX3Verb || lastX2Verb || nastX1Verb) && currentContainsVerb && containsNc && isTwo && !liaisonLast) {
				removeFromListContains(current, "verbe#");
				System.out.println("isNomCommunEndGn1 " + syntaxe);
			}
			else if ((lastX3Verb || lastX2Verb || nastX1Verb) && currentContainsVerb && containsNc && isTwo && liaisonLast) {
				removeFromListContains(current, "Nom commun");
				System.out.println("isNomCommunEndGn2 " + syntaxe);
			}
		}
	}


	private void pronomRelOrConjonction(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//QUE can be conjonction and pronom relative. Choice to keep conjonction
		//because last conditions treat relatif pronom.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			

			boolean containsRel   = listEqualsElement(current, "Pronom relatif");
			boolean containConjnc = listEqualsElement(current, "Conjonction");
			
			if (containsRel && containConjnc) {
				removeFromList(current, "Conjonction");
				System.out.println("pronomRelOrConjonction " + syntaxe);
			}
		}
	}


	private void ncOrVerbBeforeRel(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Next word is a relative pronom. Current word contains verbe or Nom commun.
		//Choice nomm commun.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			boolean currentNotOne = current.size() > 1;
			boolean nextIsRel     = listEqualsElement(next, "Pronom relatif");
			
			boolean currentVerb   = listContains(current, "verbe#");
			boolean currentNc     = listEqualsElement(current, "Nom commun");
			
			if (currentNotOne && nextIsRel && currentVerb && currentNc) {
				dontRemoveFromListElement(current, "Nom commun");
				System.out.println("ncOrVerbBeforeRel " + syntaxe);
			}
		}
	}
	
	
	private void isAdjOrVerbBeforeAdv(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//We need to make a choice beetween verbe and adjective.
		//From last's (nom commun) and next (adverbe) choice to keep verbe.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);

			boolean lastOne      = last.size() == 1;
			boolean lastNc       = listEqualsElement(last, "Nom commun");
		
			boolean currentTwo   = current.size() == 2;
			boolean containsVerb = listContains(current, "verbe#");
			boolean containsAdj  = listEqualsElement(current, "Adjectif");
			
			boolean nextOne      = next.size() == 1;
			boolean nextAdv      = listEqualsElement(next, "Adverbe");
			
			if (lastOne && lastNc && currentTwo && containsVerb && containsAdj && nextOne && nextAdv) {
				dontRemoveFromListContains(current, "verbe#");
				System.out.println("isAdjOrVerbBeforeAdv " + syntaxe);
			}
		}
	}


	private void donrRaiseVerbBeetweenTwoAdjNc(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Current word can be adjective, nom commun or verbe.
		//This word is beetween two word who's can be two. These word contains
		//Nom commun xor adjective. Don't remove verbe.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			boolean lastIsTwo   = last.size() == 2;
			boolean lastIsNcAdj = listEqualsElement(last, "Nom commun") &&
								  listEqualsElement(last, "Adjectif");
			
			boolean currentContainsAdjNcVrb = (listEqualsElement(current, "Nom commun")   ||
					  						  listEqualsElement(current, "Adjectif")) &&
					  						  listContains(current, "verbe#");
			
			boolean nextIsTwo   = next.size() == 2;
			boolean nextIsNcAdj = listEqualsElement(next, "Nom commun") &&
					  			  listEqualsElement(next, "Adjectif");
			
			
			if (lastIsTwo && lastIsNcAdj && currentContainsAdjNcVrb && nextIsTwo && nextIsNcAdj) {
				dontRemoveFromListContains(current, "verbe#");
				System.out.println("donrRaiseVerbBeetweenTwoAdjNc " + syntaxe);
			}
		}
	}


	private void isNcOrAdvLastAdjNumeral(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Last's adjective numéral. Current can contains adverbe and nom commuon.
		//Remove adverbe.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
		
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean lastIsOne    = last.size() == 1;
			boolean lastIsAdjNum = listEqualsElement(last, "Adjectif numéral");
			
			boolean currentTwo   = current.size() == 1;
			boolean containsAdv  = listEqualsElement(current, "Adverbe");
		
			if (lastIsOne && lastIsAdjNum && currentTwo && containsAdv) {
				current.clear(); current.add("Nom commun");
				System.out.println("isNcOrAdvLastAdjNumeral " + syntaxe);
			}
		}
	}
	
	private void ncAdjCantBeNc(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Lastx2's nom commun and last adjective. Current contains nom commun and verbe.
		//Keep verbe.
		
		for (int syntaxe = 2; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> lastX2  = currentSyntaxe.get(syntaxe - 2);
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean lastX2One  = lastX2.size() == 1;
			boolean lastIsOne  = last.size() == 1;
			boolean currentTwo = current.size() == 2;
			
			boolean lastX2IsNc = listEqualsElement(lastX2, "Nom commun");
			boolean lastIsAdj  = listEqualsElement(last, "Adjectif");
			
			boolean containsVrb = listContains(current, "verbe#");
			boolean containsNc  = listEqualsElement(current, "Nom commun");
			
			if (lastX2One && lastIsOne && lastX2IsNc && lastIsAdj && containsVrb && containsNc && currentTwo) {
				dontRemoveFromListContains(current, "verbe#");
				System.out.println("ncAdjCantBeNc " + syntaxe);
			}
		}
	}
	

	private void isNcOrAdjPossessif(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Current's nom commun or adjective possessive.
		//last's article and next préposition. Keep nom commun.

		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			boolean lastIsOne = last.size() == 1;
			boolean nextIsOne = next.size() == 1;
	
			//Last's article
			boolean lastContainsArt       = listContains(last, "rticle");
			
			//Current possessive or nom commun.
			boolean currentContainsAdjPos = listEqualsElement(current, "Adjectif possessif");
			boolean currentContainsNc     = listEqualsElement(current, "Nom commun");
			
			//Next préposition.
			boolean nextContainsPrep      = listEqualsElement(next, "Préposition");
			
			if (lastIsOne && nextIsOne && lastContainsArt && currentContainsAdjPos && currentContainsNc && nextContainsPrep) {
				dontRemoveFromListElement(current, "Nom commun");
				System.out.println("isNcOrAdjPossessif " + syntaxe);
			}
		}
	}
	
	private void isNcOrAdjIfTirate(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//Current contains adjective or nom commun.
		//Dont remove nom commun in case word contains a tirate.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			String word = currentText.get(syntaxe);
			
			boolean currentContainsAdj = listEqualsElement(current, "Adjectif");
			boolean currentContainsNc  = listEqualsElement(current, "Nom commun");
			
			boolean wordContanisTirate = word.contains("-");
			
			if (currentContainsAdj && currentContainsNc && wordContanisTirate) {
				dontRemoveFromListElement(current, "Nom commun");
				System.out.println("isNcOrAdjIfTirate " + syntaxe);
			}
		}
	}
	
	
	private void isArticleBeforeNumeral(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Adjectif numeral can't be preced by pronom personnel.

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			//Current contains pronom and article.
			boolean currentIsTwo        = current.size() == 2;
			boolean containsPronomPerso = listEqualsElement(current, "Pronom personnel");
			boolean containsArt         = listContains(current, "rticle");

			//Next's adjective numéral.
			boolean nextIsOne 	 = next.size() == 1;
			boolean nextIsAdjNum = listEqualsElement(next, "Adjectif numéral");
			
			//Remove pronom.
			if(currentIsTwo && containsPronomPerso && containsArt && nextIsOne && nextIsAdjNum) {
				removeFromList(current, "Pronom personnel");
				System.out.println("isArticleBeforeNumeral " + syntaxe);
			}
		}
	}
	
	private void caseQueAndNeg(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		//Make "que" adverbe or remove it.
		//It said (from a website) "que" adverbe is alwais use with particule. So from next 5 words
		//search "ne".

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {

			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			String word = currentText.get(syntaxe);

			//Word's que or qu'
			boolean isWordQue         = word.equalsIgnoreCase("que") || word.equalsIgnoreCase("contraction=que");
			
			//Contains adverbe.
			boolean currentNotOne     = current.size() > 1;
			boolean containsAdverbe   = listEqualsElement(current, "Adverbe");
			
			//From next 5 word search particle (ne).
			boolean thereIsNeInRange5 = false; 
			boolean inRange = syntaxe + 5 < currentText.size();
			List<String> part = new ArrayList<String>();
			
			if      (inRange) { part = currentText.subList(syntaxe, syntaxe + 5); }
			else if (inRange) { part = currentText.subList(syntaxe, currentText.size() - 1); }


			for (String element: part) {
				boolean isNe   = element.equalsIgnoreCase("ne");
				if (isNe)   { thereIsNeInRange5 = true; break; } 
			}


			if (isWordQue && currentNotOne && containsAdverbe && thereIsNeInRange5) {
				dontRemoveFromListElement(current, "Adverbe");
				System.out.println("caseQueAndNeg1 " + syntaxe);
			}
			else if (isWordQue && currentNotOne && containsAdverbe && !thereIsNeInRange5) {
				removeFromList(current, "Adverbe");
				System.out.println("caseQueAndNeg2 " + syntaxe);
			}
		}
	}
	

	private void isNomCommunOrVerbBeforePronm(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Choice verbe beetween verbe and nom commun; before a pronom personnel.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			boolean nextOne    = next.size() == 1;
			boolean nextIsPrnm = listEqualsElement(next, "Pronom personnel");
			
			boolean currentTwo    = current.size() > 1;
			boolean containsVerb  = listContains(current, "verbe#");
			boolean containsNc    = listEqualsElement(current, "Nom commun");
			
			if (nextOne && nextIsPrnm && currentTwo && containsVerb && containsNc) {
				removeFromListContains(current, "verbe#");
				System.out.println("isNomCommunOrVerbBeforePronm " + syntaxe);
			}
		}
	}




	private void isArtAfterPrepo(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Choice article beetween preposition and article. Because last's préposition.
		//From last function we make preposition composite.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean lastIsOne    = last.size() == 1;
			boolean lastIsPrep   = listEqualsElement(last, "Préposition");
			
			boolean currentIsTwo = current.size() == 2;
			boolean containsPrnm = listEqualsElement(current, "Pronom personnel");
			boolean containsArt  = listContains(current, "rticle ");

			if (lastIsOne && lastIsPrep && currentIsTwo && containsPrnm && containsArt) {
				dontRemoveFromListContains(current, "rticle");
				System.out.println("isArtAfterPrepo " + syntaxe);
			}
		}
	}
	
	
	private void isNcOrAdjIfNoNc(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Keep nom commun if last'nt nom commun and next'nt adjectif or nom commun.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			//Last one and verbe. Or last one and no nom commun.
			boolean lastIsOne          = last.size() == 1;
			boolean lastIsVrb          = listContains(last, "verbe#");
			boolean noLastNc           = listEqualsElement(last, "Nom commun");
			
			//Current contains adjective and nom commun.
			boolean currentIsTwo       = current.size() == 2;
			boolean currentContainsAdj = listEqualsElement(current, "Adjectif");
			boolean currentContainsNc  = listEqualsElement(current, "Nom commun");
			
			//Next's nom commun xor adjective.
			boolean nextContainsNc     = listEqualsElement(next, "Nom commun");
			boolean nextContainsAdj    = listEqualsElement(next, "Adjectif");
			
			
			boolean lastCondition      = lastIsOne && lastIsVrb || noLastNc;

			
			if (!lastCondition && currentIsTwo && currentContainsAdj && currentContainsNc && !nextContainsNc && !nextContainsAdj) {
				dontRemoveFromListElement(current, "Nom commun");
				System.out.println("isNcOrAdjIfNoNc " + syntaxe);
			}
		}
	}
	

	private void isArtBeforeNomPropre(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Current's two possibilies: pronom personnel or article. Next's nom propre.
		//Choice article.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			boolean nextIsOne    = next.size() == 1;
			boolean currentIsTwo = current.size() == 2;
			
			boolean nextIsNp                 = listEqualsElement(next, "Nom propre");
			boolean currentContainsPrnmPerso = listEqualsElement(current, "Pronom personnel");
			boolean currentContainsArt       = listContains(current, "rticle ");
			
			if (nextIsOne && currentIsTwo && nextIsNp && currentContainsPrnmPerso && currentContainsArt) {
				dontRemoveFromListContains(current, "rticle ");
				System.out.println("isArtBeforeNomPropre " + syntaxe);
			}
		}
	}
	
	
	private void beforeInfinitif(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Before infinitif choice to keep: préposition, verbe, pronom and adverbe.
		//Remove others.
		
		String[] beforeInfinitif = {"Préposition", "verbe#", "Pronom personnel", "Adverbe"};
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);

			boolean currentNotOne = current.size() > 1;
			boolean nextOne    = next.size() == 1;
			
			boolean nextInfi   = listEqualsElement(next, "verbe#");
			boolean currentCnt = thisListContains(current, beforeInfinitif);
			
			if (currentNotOne && nextOne && nextInfi && currentCnt) {
				dontRemoveFromThisList(current, beforeInfinitif);
				System.out.println("beforeInfinitif " + syntaxe);
			}
		}
	}
	
	private void adjectifOrVerbNextIsVerbeInf(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Remove adjective in case we got verbe and adjective from next word who's verbe.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			boolean currentIsTwo = current.size() == 2;
			boolean containsVerb = listContains(current, "verbe#");
			boolean containsAdj  = listEqualsElement(current, "Adjectif");
			
			boolean nextIsOne    = next.size() == 1;
			boolean nextIsInfi   = listEqualsElement(next, "verbe#"); 
			
			if (currentIsTwo && containsVerb && containsAdj && nextIsOne && nextIsInfi) {
				removeFromList(current, "Adjectif");
				System.out.println("adjectifOrVerbNextIsVerbeInf " + syntaxe);
			}
		}
	}
	
	

	private void isNotPrepositionIfLastIsPronomPersonnel(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Last's pronom personnel. Remove préposition.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean lastIsOne       = last.size() == 1;
			boolean lastIsPrnmPerso = listEqualsElement(current, "Pronom personnel");
			
			boolean currentIsTwo    = current.size() == 2;
			boolean currentIsPrnm   = listEqualsElement(current, "Pronom personnel");
			boolean currentIsPrep   = listEqualsElement(current, "Préposition");
			
			if (lastIsOne && lastIsPrnmPerso && currentIsTwo && currentIsPrnm && currentIsPrep) {
				removeFromList(current, "Préposition");
				System.out.println("isNotPrepositionIfLastIsPronomPersonnel " + syntaxe);
			}
		}
	}
	
	
	private void isNomOrConjonction(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Remove conjonction if we got nom commun.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean isTwo               = current.size() == 2;
			boolean containsConjonction = listEqualsElement(current, "Conjonction");
			boolean containsNc          = listEqualsElement(current, "Nom commun");
			
			if (isTwo && containsConjonction && containsNc) {
				removeFromList(current, "Conjonction");
				System.out.println("isNomOrConjonction " + syntaxe);
			}
		}
	}
	
	private void modifieVerbeToVerb(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Dico word and verbe are in compétition, One can catch a verbe and the other no.
		//Modify Verbe to verbe# (verbe# marks infinitif).
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {

			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean isOne  		 = current.size() == 1;
			boolean isVerb 		 = listEqualsElement(current, "Verbe");
			boolean containsVerb = listContains(current, "verbe#");
			
			
			if (isVerb && isOne) { 
				current.set(0, "verbe#");
				System.out.println("modifieVerbeToVerb1 " + syntaxe);
			}
			else if (isVerb && !isOne && containsVerb) {
				removeFromList(current, "Verbe");
				System.out.println("modifieVerbeToVerb2 " + syntaxe);
			}
		}
	}

	private void adjectifOrArticleDefini(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//We have adjectif and article défini. Because scrap take other words proprosed. 
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			ArrayList<String> current     = currentSyntaxe.get(syntaxe);
			
			boolean currentTwo   = current.size() == 2;
			boolean containsAdj  = listEqualsElement(current, "Adjectif");
			boolean containsArt  = listContains(current, "rticle défini");
			
			if (currentTwo && containsAdj && containsArt) {
				dontRemoveFromListContains(current, "rticle défini");
				System.out.println("adjectifOrArticleDefini " + syntaxe);
			}
		}
	}

	private void PrnmPersoOrArtDefNextGn(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//We have article defini or pronom personnel. Next is Adjectif or Nc. remove pronom.

		String[] gn = {"Adjectif", "Nom commun"};
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			ArrayList<String> current     = currentSyntaxe.get(syntaxe);
			ArrayList<String> next        = currentSyntaxe.get(syntaxe + 1);
			
			boolean currentIsTwo  = current.size() == 2;
			boolean containsArt   = listContains(current, "rticle défini");
			boolean containsPrnm  = listEqualsElement(current, "Pronom personnel");
			
			
			boolean nextIsGn     = thisListEqualList(next, gn);
			boolean nextIsVrb    = listContains(next, "verbe#");
			
			boolean nextIsOne    = next.size() == 1;

			
			if (currentIsTwo && containsArt && containsPrnm && nextIsGn && nextIsOne && !nextIsVrb) {
				dontRemoveFromListContains(current, "rticle défini");
				System.out.println("PrnmPersoOrArtDefNextGn article " + syntaxe);
			}
			else if(currentIsTwo && containsArt && containsPrnm && !nextIsGn && nextIsOne && nextIsVrb) {
				dontRemoveFromListContains(current, "Pronom personnel");
				System.out.println("PrnmPersoOrArtDefNextGn verbe " + syntaxe);
			}
		}
	}
	
	private void isNomCommunIfNextPrepoNc(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//We have Adjectif or Nom commun. Next's préposition and nextX2's Nom commun.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 2; syntaxe++) {
			ArrayList<String> current     = currentSyntaxe.get(syntaxe);
			ArrayList<String> next        = currentSyntaxe.get(syntaxe + 1);
			ArrayList<String> nextX2      = currentSyntaxe.get(syntaxe + 2);

			boolean currentIsTwo = current.size() == 2;
			boolean containsAdj  = listEqualsElement(current, "Adjectif");
			boolean containsNc   = listEqualsElement(current, "Nom commun");
			
			boolean nextIsOne    = next.size() == 1;
			boolean nextIsPrep   = listEqualsElement(next, "Préposition");
			
			boolean nextX2One    = nextX2.size() == 1;
			boolean nextX2IsNc   = listEqualsElement(nextX2, "Nom commun");
			
			if (currentIsTwo && containsAdj && containsNc && nextIsOne && nextIsPrep && nextX2One && nextX2IsNc) {
				dontRemoveFromListElement(current, "Nom commun");
				System.out.println("isNomCommunIfNextPrepoNc " + syntaxe);
			}
		}
	}
	
	
	private void isPrepositionIfNextIsAdv(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Current's preposition or pronom personnel. Next's adverbe. Raise pronom.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			ArrayList<String> current     = currentSyntaxe.get(syntaxe);
			ArrayList<String> next        = currentSyntaxe.get(syntaxe + 1);
			
			boolean currentIsTwo = current.size() == 2;
			boolean currentContainsPrepo = listEqualsElement(current, "Préposition");
			boolean currentContainsPrnm  = listEqualsElement(current, "Pronom personnel");

			boolean nextIsOne = next.size() == 1;
			boolean nextIsAdv = listEqualsElement(next, "Adverbe");
			
			if (currentIsTwo && currentContainsPrepo && currentContainsPrnm && nextIsOne && nextIsAdv) {
				dontRemoveFromListElement(current, "Préposition");
				System.out.println("isPrepositionIfNextIsAdv " + syntaxe);
			}
		}
	}
	
	private void adjectifOrNomCommunNextAdj(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Next is Nom commun or adjectif. Current is Nom commun and adjectif.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> current     = currentSyntaxe.get(syntaxe);
			ArrayList<String> next        = currentSyntaxe.get(syntaxe + 1);
			
			boolean currentIsTwo = current.size() == 2;
			boolean containsNc   = listEqualsElement(current, "Nom commun");
			boolean containsAdj  = listEqualsElement(current, "Adjectif");
			
			boolean nextIsOne    = next.size() == 1;
			
			boolean nextIsAdj    = listEqualsElement(next, "Adjectif");	
			boolean nextIsNc     = listEqualsElement(next, "Nom commun");
			
			if(currentIsTwo && containsNc && containsAdj && nextIsOne && nextIsAdj) {
				dontRemoveFromListElement(current, "Nom commun");
				System.out.println("adjectifOrNomCommunNextAdj1 " + syntaxe);
			}
			
			else if (currentIsTwo && containsNc && containsAdj && nextIsOne && nextIsNc) {
				dontRemoveFromListElement(current, "Adjectif");
				System.out.println("adjectifOrNomCommunNextAdj2 " + syntaxe);
			}
		}
		
	}


	private void beetweenTwoAdverbCanBeCOnjonction(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Choice conjonction or pronom relative. If next or last are adverbe choice to keep conjonction.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last        = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current     = currentSyntaxe.get(syntaxe);
			ArrayList<String> next        = currentSyntaxe.get(syntaxe + 1);
			
			boolean lastIsOne     = last.size() == 1;
			boolean lastIsAdverbe = listEqualsElement(last, "Adverbe");
			
			boolean currentIsTwo  			   = current.size() == 2;
			boolean currentContainsConjonction = listEqualsElement(current, "Conjonction");
			boolean currentContainsPrnmRel     = listEqualsElement(current, "Pronom relatif");
			
			boolean nextIsOne = next.size() == 1;
			boolean nextIsAdv = listEqualsElement(next, "Adverbe");
			
			if (lastIsOne && lastIsAdverbe && currentIsTwo && currentContainsConjonction &&
				currentContainsPrnmRel && nextIsOne && nextIsAdv) {
				dontRemoveFromListElement(current, "Conjonction");
				System.out.println("beetweenTwoAdverbCanBeCOnjonction " + syntaxe);
			}	
		}
	}
	
	

	private void isNomCommunIfAdjOrNcLastWord(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Is a last word in a sentence. Choice to keep nom commun.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			
			ArrayList<String> current     = currentSyntaxe.get(syntaxe);
			boolean isNotInRange = syntaxe < currentSyntaxe.size() - 1;
			
			ArrayList<String> next = new ArrayList<String>();
			if (isNotInRange) { next = currentSyntaxe.get(syntaxe + 1); }
			
			boolean nextIsPonctuation = listEqualsElement(next, "Ponctuation");
			boolean isLastWord        = syntaxe == currentSyntaxe.size() - 1;
			
			boolean containsAdj       = listEqualsElement(current, "Adjectif");
			boolean containsNc        = listEqualsElement(current, "Nom commun");
			
			if ((nextIsPonctuation || isLastWord) && containsAdj && containsNc) {
				dontRemoveFromListElement(current, "Nom commun");
				System.out.println("isNomCommunIfAdjOrNcLastWord " + syntaxe);
			}
		}
	}


	private void isNcIfArtBefore(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Choice nom commun from verbe and nom commun if last's one of that:
		//Adjectif possessif, rticle défini, rticle indéfin.
		
		String[] cannotBefore = {"Adjectif possessif", "rticle défini", "rticle indéfini"};
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last     = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current  = currentSyntaxe.get(syntaxe);
			
			boolean currentIsOne = current.size() == 1;
			boolean lastIsOne    = last.size() == 1;;
			
			boolean currenIsVerbe    = listContains(current, "verbe#");
			boolean lastIsCantBefore = thisListContains(last, cannotBefore);
			
			if (currentIsOne && lastIsOne && currenIsVerbe && lastIsCantBefore) {
				current.clear(); current.add("Nom commun");
				System.out.println("isNcIfArtBefore " + syntaxe);
			}
		}
	}
	
	
	private void isCompositeVerbAdvMidle(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Choice to keep verbe beetween nom commun and verb if last and lastx2 are verbe and adverb.
		//j'ai totalement loupé.
		
		for (int syntaxe = 2; syntaxe < currentSyntaxe.size(); syntaxe++) {

			ArrayList<String> last     = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> lastX2   = currentSyntaxe.get(syntaxe - 2);
			ArrayList<String> current  = currentSyntaxe.get(syntaxe);

			boolean currentIsTwo       = current.size() == 2;
			boolean currentContainsVrb = listContains(current, "verbe#");
			boolean currentContainsNc  = listEqualsElement(current, "Nom commun");
			
			boolean lastIsAdverbe      = listEqualsElement(last, "Adverbe");
			boolean lastX2IsVerb       = listContains(lastX2, "verbe#");
			
			
			if (currentIsTwo && currentContainsVrb && currentContainsNc && lastIsAdverbe && lastX2IsVerb) {
				dontRemoveFromListContains(current, "verbe#");
				System.out.println("isCompositeVerbAdvMidle " + syntaxe);
			}
		}
	}
	
	private void isVerbOrAdjIfPrepoAfter(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Choice verbe (beetween verbe and adjectiv) if it"s the first word and next's préposition.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> current  = currentSyntaxe.get(syntaxe);
			ArrayList<String> next     = currentSyntaxe.get(syntaxe + 1);
			
			boolean firstWord   = syntaxe == 0;
			boolean isSizeTwo   = current.size() == 2;
			
			boolean containsVrb = listContains(current, "verbe#");
			boolean containsAdj = listEqualsElement(current, "Adjectif");
			
			boolean nextIsPrepo = listEqualsElement(next, "Préposition");
			boolean nextIsOne   = next.size() == 1;
			
			if (firstWord && isSizeTwo && containsVrb && containsAdj && nextIsPrepo && nextIsOne) {
				dontRemoveFromListContains(current, "verbe#");
				System.out.println("isVerbOrAdjIfPrepoAfter " + syntaxe);
			}
		}
	}
	
	
	private void isntConjonctionWithNcFromLastAndNext(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Isn't conjonction and it's nom commun
		//if last's pronom indefini and next preposition.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last     = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current  = currentSyntaxe.get(syntaxe);
			ArrayList<String> next     = currentSyntaxe.get(syntaxe + 1);
			
			boolean currentOne = current.size() > 1;
			boolean lastIsOne  = last.size() == 1;
			boolean nextIsOne  = next.size() == 1;
			
			boolean lastIsPrnm         = listEqualsElement(last, "Pronom indéfini");
			boolean nextIsPréposition  = listEqualsElement(next, "Préposition");
			
			boolean currentContainsNc  = listEqualsElement(current, "Nom commun");
			boolean currentContainsCnj = listEqualsElement(current, "Conjonction");
			
			if (currentOne && lastIsOne && nextIsOne && lastIsPrnm && nextIsPréposition && currentContainsNc && currentContainsCnj) {
				dontRemoveFromListElement(current, "Nom commun");
				System.out.println("isntConjonctionWithNcFromLastAndNext " + syntaxe);
			}
			
		}
		
	}
	
	

	private void isPonctuationList(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//Put ponctuation in case one of those symbole's:
		
		String[] ponct = {"«", "'", "\"", "»", ".", ":", "!", "-", "—", "?", "?"};
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			ArrayList<String> current  = currentSyntaxe.get(syntaxe);
			String            word     = currentText.get(syntaxe);
			
			boolean containsPonct = thisListContainsWord(ponct, word);
			if (containsPonct) {
				current.clear(); current.add("ponctuation");
				System.out.println("isPonctuationList " + syntaxe);
			}
		}
	}
	
	
	
	private void conjonRelOrAdv(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//Keep conjonction if it's a nex sentence and a first word.
		
		String[] que = {"Conjonction", "Pronom relatif", "Adverbe"};
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			ArrayList<String> current  = currentSyntaxe.get(syntaxe);
			String            word     = currentText.get(syntaxe);
			
			String 		   firstLetter = Character.toString(word.charAt(0));

			boolean firstWord     = syntaxe == 0;
			boolean contrainening1 = listEqualsElement(current, "Conjonction");
			boolean contrainening2 = listEqualsElement(current, "Pronom relatif");
			boolean contrainening3 = listEqualsElement(current, "Adverbe");

			boolean isSize        = current.size() > 1;
			
			if (contrainening1 && contrainening2 && contrainening3 && firstWord && isSize) {
				dontRemoveFromListContains(current, "Conjonction");
				System.out.println("conjonRelOrAdv " + syntaxe);
			}
		}
	}
	
	private void lastElementIsAdjOrNc(ArrayList<ArrayList<String>> currentSyntaxe) {
		


		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last     = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current  = currentSyntaxe.get(syntaxe);

			boolean currentIsTwo  = current.size() == 2;
			boolean containsAd    = listEqualsElement(current, "Adjectif");
			boolean containsNc    = listEqualsElement(current, "Nom commun");
			
			boolean lastWord        = syntaxe == currentSyntaxe.size() - 1;
			
			boolean lastContainsNc   = listEqualsElement(last, "Nom commun");
			boolean lastContainsAdj  = listEqualsElement(last, "Adjectif");
			
			if (currentIsTwo && containsAd && containsNc && lastWord && !lastContainsNc) {
				dontRemoveFromListElement(current, "Nom commun");
				System.out.println("lastElementIsAdjOrNc " + syntaxe);
			}
			else if (currentIsTwo && containsAd && containsNc && lastWord && lastContainsNc && lastContainsAdj) {
				dontRemoveFromListElement(last,    "Nom commun");
				dontRemoveFromListElement(current, "Adjectif");
				System.out.println("lastElementIsAdjOrNc " + syntaxe);
			}
		}
	}
	
	
	private void isArticleIfNextIsGn(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		String[] gn = {"djectif", "om commun"};
		
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> current  = currentSyntaxe.get(syntaxe);
			ArrayList<String> next     = currentSyntaxe.get(syntaxe + 1);
			
			boolean currentIsThree = current.size() == 3;
			
			boolean containening   = listEqualsElement(current, "Article indéfini") &&
									 listEqualsElement(current, "Adjectif numéral") &&
									 listEqualsElement(current, "Pronom indéfini");
			
			
			boolean nextContainsGn = thisListContains(next, gn);
			boolean nextArt        = thisListEqualList(next, this.determinantCommun);
			
			boolean nextGn = nextContainsGn || nextArt;
			
			
			if (currentIsThree && containening && nextGn) {
				dontRemoveFromListElement(current, "Article indéfini");
				System.out.println("isArticleIfNextIsGn " + syntaxe);
			}
		}
	}
	
	
	private void ncOrPrnm(ArrayList<ArrayList<String>> currentSyntaxe) {
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last     = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current  = currentSyntaxe.get(syntaxe);
			
			boolean lastIsOne	  = last.size() == 1;
			boolean lastIsPrnmRel = listEqualsElement(last, "Pronom relatif");
			
			boolean currentIsTwo 		  = current.size() == 2;
			boolean currentContainsNc     = listEqualsElement(current, "Nom commun");
			boolean currentContainPrnmInd = listEqualsElement(current, "Pronom indéfini");
			
			if (lastIsOne && lastIsPrnmRel && currentIsTwo && currentContainsNc && currentContainPrnmInd) {
				dontRemoveFromListElement(current, "Pronom indéfini");
				System.out.println("ncOrPrnm " + syntaxe);
			}
		}
	}



	private void isPrepositionV2(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		
		String[] isNot = {"des"};
		
		for (int syntaxe = 2; syntaxe < currentSyntaxe.size(); syntaxe++) {

			ArrayList<String> lastX2   = currentSyntaxe.get(syntaxe - 2);
			ArrayList<String> last     = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current  = currentSyntaxe.get(syntaxe);
			
			String word = currentText.get(syntaxe);
			
			boolean isNotOne     = current.size() > 1;
			boolean containsPrep = listEqualsElement(current, "Préposition");
			boolean containsVrb  = listContains(current, "verbe#");
			  
			boolean lastOrLastX2IsNc = listEqualsElement(last,   "Nom commun") ||
									   listEqualsElement(lastX2, "Nom commun");
			
			boolean lastIsntPrnm     = thisListEqualList(last, pronomPersonnel);
			boolean butNot           = thisListEqualWord(isNot, word); // des find dès.
			
			if (isNotOne && containsPrep && lastOrLastX2IsNc && !lastIsntPrnm && !containsVrb && !butNot) {
				dontRemoveFromListElement(current, "Préposition");
				System.out.println("isPrepositionV2 " + syntaxe);
			}
		}
	}



	private void isNotVerbIfNotGerondifOrInfinitif(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//After a Préposition we can have a verbe. But this verbe need to be as gérondif or infinitif.
		//Else it's a pronom.

		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last     = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current  = currentSyntaxe.get(syntaxe);

			boolean containsVerb = listContains(current, "verbe#");
			String  verb 		 = listContainsRecuperate(current, "verbe#");
			
			boolean isInfinitif      = verb.equalsIgnoreCase("verbe#");
			boolean containsGerondif = verb.contains("érondif");
			boolean containsPP = verb.contains("articipe présent");
			


			boolean notOne            = current.size() > 1;

			boolean lastIsOne         = last.size() == 1;
			boolean lastIsPreposition = listEqualsElement(last, "Préposition");
			
			
			
			if (containsVerb && !(isInfinitif || containsGerondif || containsPP) && notOne && lastIsOne && lastIsPreposition) {
				removeFromListContains(current, "verbe#");
				System.out.println("isNotVerbIfNotGerondifOrInfinitif " + syntaxe);
			}
		}
	}


	private void isPrnmRelBuConjonc(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//We put pronom relatif but, if the prnm is beetewen two Gn it can be a conjonction.
		
		for (int syntaxe = 2; syntaxe < currentSyntaxe.size() - 2; syntaxe++) {
			
			ArrayList<String> lastX2  = currentSyntaxe.get(syntaxe - 2);
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			ArrayList<String> nextX2  = currentSyntaxe.get(syntaxe + 2);
			
			
			boolean isOne 			  = current.size() == 1;
			boolean isPronomRel 	  = listEqualsElement(current, "Pronom relatif");

			boolean lastX2tOrLastIsNc = listEqualsElement(lastX2, "Nom commun") || listEqualsElement(last, "Nom commun");
			boolean nextOrNext2IsNc   = listEqualsElement(next,   "Nom commun") || listEqualsElement(nextX2, "Nom commun");
			
			if (isOne && isPronomRel && lastX2tOrLastIsNc && nextOrNext2IsNc) {
				current.clear(); current.add("Conjonction");
				System.out.println("isPrnmRelBuConjonc " + syntaxe);
			}
			
		}
	}



	
	
	
	
	
	
	
	
	
	private void isArtOrNcBeforeAdjAfterIsNc(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 2; syntaxe++) {
			
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			ArrayList<String> nextX2  = currentSyntaxe.get(syntaxe + 2);
			
			
			boolean currentSizeTwo = current.size() == 2;
			boolean containsNc     = listEqualsElement(current, "Nom commun");
			boolean containsArt    = thisListEqualList(current, determinantCommunWithotuAdj);
			
			boolean nextOne        = next.size() == 1;
			boolean nextX2OnE      = nextX2.size() == 1;
			
			boolean nextAdj        = listEqualsElement(next, "Adjectif");
			boolean nextX2Nc       = listEqualsElement(nextX2, "Nom commun");
			
			if (currentSizeTwo && containsNc && containsArt && nextOne && nextX2OnE && nextAdj && nextX2Nc) {
				dontRemoveFromThisList(current, determinantCommunWithotuAdj);
				System.out.println("isArtOrNcBeforeAdjAfterIsNc " + syntaxe);
			}
		}
	}
	
	
	
	private void isAdjNumOrIndefini(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		//Remove adjectif numeral if not int.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			String word = currentText.get(syntaxe);
			
			
			int nb = -1; try { nb = Integer.parseInt(word); } catch (Exception e) {}

			
			boolean isNb = nb != -1;
			boolean currentIsTwo   = current.size() == 2;
			boolean containsAdjNum = listEqualsElement(current, "Adjectif numéral");
			boolean containArtInde = listEqualsElement(current, "Article indéfini") ||
									 listEqualsElement(current, "Forme d'article indéfini");
								
			if (!isNb && currentIsTwo && containsAdjNum && containArtInde) {
				dontRemoveFromListContains(current, "ticle indéfini");
				System.out.println("isAdjNumOrIndefini " + syntaxe);
			}
			else if (isNb) {
				dontRemoveFromListContains(current, "Adjectif numéral");
				System.out.println("isAdjNumOrIndefini " + syntaxe);
			}
		}
	}
	
	
	
	
	private void nextOnlyNcitsAdj(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Next syntaxe's only nom commun. Current contains Nc and adjectif.
		//Remove Nc.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			boolean currentContainsNc  = listEqualsElement(current, "Nom commun");
			boolean currentContainsAdj = listEqualsElement(current, "Adjectif");
			boolean currentIsTwo       = current.size() > 1;

			boolean isContainsArt = thisListEqualList(current, this.determinantCommunWithotuAdj);
			
			boolean conditionCurrent   = currentContainsNc && currentContainsAdj && currentIsTwo && !isContainsArt;
			
			boolean nextSize1 = next.size() == 1;
			boolean nextSize2 = next.size() == 2;
			

			boolean nextIsNc    = listEqualsElement(next, "Nom commun");
			boolean nextIsForme = listEqualsElement(next, "Forme de nom commun");
			
			if (conditionCurrent && ((nextSize1 && nextIsNc) || 
									 (nextSize1 && nextIsForme) ||
									 (nextSize2 && nextIsNc && nextIsForme))) {
				
				dontRemoveFromListElement(current, "Adjectif");
				System.out.println("nextOnlyNcitsAdj " + syntaxe);
			}
		}
	}
	
	
	private void adverbeOrPrepo(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {

			ArrayList<String> current    = currentSyntaxe.get(syntaxe);
			
			boolean currentContainsPrepo = listEqualsElement(current, "Préposition");
			boolean currentContainsAdv   = listEqualsElement(current, "Adverbe");
			boolean currentIsTwo         = current.size() == 2;
			
			if (currentContainsPrepo && currentContainsAdv && currentIsTwo) {
				dontRemoveFromListElement(current, "Adverbe");
				System.out.println("adverbeOrPrepo " + syntaxe);
			}
		}
	}

	
	

	private void removeEmptyElement(ArrayList<ArrayList<String>> currentSyntaxe) {
		//Remove space character.

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);

			removeFromList(current, "");
			removeFromList(current, " ");
			
		}		
	}
	
	
	private void emptyAjustIt(ArrayList<ArrayList<String>> currentSyntaxe) {

		//If we have an empty syntaxe, and last is an article,
		//put adjectif, adverb and nom commun.

		boolean firstWordEmpty = currentSyntaxe.get(0).size() == 0;
		if     (firstWordEmpty) { System.out.println(5/0); }


		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {

			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean currentIsOne      = current.size() == 1 && current.get(0).equals("") ;
			boolean lastIsOne         = last.size() == 1;
			boolean lastIsArt         = thisListEqualList(last, this.determinantCommun);
			

			if (currentIsOne && lastIsOne && lastIsArt) {
				String[] afterArt = {"Adjectif", "Adverbe", "Nom commun"};
				for (String element: afterArt) { current.add(element); };
				System.out.println("emptyAjustIt was empty put afterARt " + syntaxe);
			}
		}
	}

	

	private void raiseInformationOnFromDico(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		//The scrap dictionnary permet to acces to a rubric via "sur web", raise it.

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			String 			  word 	  = currentText.get(syntaxe).toLowerCase();

			//Verify it's sur web cause we search only "sur" with contains.
			String rewordWord = Character.toString(word.charAt(0)).toUpperCase() + word.substring(1, word.length());

			boolean containsSur  = listContains(current, "sur");
			boolean containsWord = listContains(current, rewordWord);

			if (containsSur && containsWord) {
				removeFromListContains(current, rewordWord);
				System.out.println("raiseInformationOnFromDico " + syntaxe);
			}
		}
	}


	private void afterParticuleCanBeVerb(ArrayList<ArrayList<String>> currentSyntaxe) {

		//We can find a verb after particule and not nom commun.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);

			boolean currentNotOne = current.size() > 1;
			boolean lastOne       = last.size() == 1;
			boolean lastParticule = listEqualsElement(last, "Particule");

			boolean currentContainsVerb = listContains(current, "verbe#");
			boolean containsNc          = listEqualsElement(current, "Nom commun");


			if (currentNotOne && lastOne && lastParticule && currentContainsVerb && containsNc) {
				removeFromListContains(current, "Nom commun");
				System.out.println("afterParticuleCanBeVerb " + syntaxe);
			}
		}
	}


	private void makeItPrnmPersonel(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//Make pronom personnel from the text. They are define in a genreal list.
		
		ArrayList<Integer> remove = new ArrayList<Integer>();

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			String 			  word    = currentText.get(syntaxe);
			
			boolean nextGn   = listEqualsElement(next, "Nom commun") ||
							   listEqualsElement(next, "Adjectif");
			
			boolean matching = thisListEqualWord(prnmPersonnel, word);
			boolean isNtPrnm = listEqualsElement(current, "Pronom personnel");
			boolean size     = current.size() == 1;

			if (matching && isNtPrnm && !size && !nextGn) {
				current.clear(); current.add("Pronom personnel");
			}

			else if (matching && size && !nextGn) {
				current.clear(); current.add("Pronom personnel");
				System.out.println("makeItPrnmPersonel " + syntaxe);
			}
		}
	}


	private void ifTirateElement(ArrayList<String> currentText, ArrayList<ArrayList<String>> currentSyntaxe) {
		
		
		
		for (int text = 0; text < currentText.size(); text++) {

			String word = currentText.get(text);
			boolean isTirate = word.equalsIgnoreCase("—");
			
			if (isTirate) {
				currentText.remove("—");
				System.out.println("ifTirateElement " + text);
			}
		}

		String[] ponct = {"«", "'", "\"", "»", ".", ":", "!", "-", "—"};
		boolean isPonctEnd = thisListEqualList(currentSyntaxe.get(currentSyntaxe.size() - 1), ponctuation);
		boolean textInfSynta = currentText.size() + 1 == currentSyntaxe.size();
		boolean textPonctEnd = thisListEqualWord(ponct, currentText.get(currentText.size() - 1));
		
		if (textInfSynta && isPonctEnd && !textPonctEnd) {
			currentSyntaxe.remove(currentSyntaxe.size() - 1);
			System.out.println("ifTirateElement delete last ponctuation");
		}
	}


	
	private void PrnmIndéfiniOrAdj(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);

			boolean notOne = current.size() == 2;
			boolean containsPrnmInd = listEqualsElement(current, "Forme de pronom indéfini");
			boolean containsAdjInd  = listEqualsElement(current, "Forme d’adjectif indéfini");
			
			boolean conditionCurrent = notOne && containsPrnmInd && containsAdjInd;
			
			boolean nextIsNc   = listEqualsElement(next, "Nom commun");
			boolean nextIsAdj  = listEqualsElement(next, "Adjectif");
			boolean nextIsArt  = thisListEqualList(next, this.determinantCommun);
			
			boolean NcCase     = nextIsNc || nextIsAdj || nextIsArt;
			
			boolean nextIsAdv  = listEqualsElement(next, "Adverbe");
			boolean nextIsVerb = listContains(next, "verbe#");
			
			boolean verbCase   = nextIsAdv || nextIsVerb;
			
			if (conditionCurrent && NcCase) {
				dontRemoveFromListElement(current, "Forme de pronom indéfini");
				System.out.println("PrnmIndéfiniOrAdj 1 " + syntaxe );
			}
			else if (conditionCurrent && verbCase) {
				dontRemoveFromListElement(current, "Forme de pronom indéfini");
				System.out.println("PrnmIndéfiniOrAdj 2 " + syntaxe );
			}
		}
	}

	private void adjNuméralInInteger(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		//Choice to put adjective numéral if word can be parseInt.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			String word = currentText.get(syntaxe);

			int toInt = -1;
			
			try { toInt = Integer.parseInt(word); } catch (Exception e) {}
			
			boolean isInteger = toInt != -1;
			
			if (isInteger) { 
				current.clear();
				current.add("Adjectif numéral"); 
				System.out.println("adjNuméralInInteger " + syntaxe);
			}
			
		}
		
		
	}

	
	
	private void cannotBeVerbFromDefinatePronomNonePrnm(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText,
			List<ArrayList<String>> originalText, int getSentence) {

		
		
		ArrayList<String> lastSentenceText = new ArrayList<String>();
		
		if (getSentence > 0) { lastSentenceText = originalText.get(getSentence - 1); }
		String  firstletter   = Character.toString(currentText.get(0).charAt(0));
		boolean isNewSentence = firstletter.equals(firstletter.toUpperCase());

		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {

			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);


			String  lastWord = verifyAccentLastWord(currentText, syntaxe);
			boolean lastWordIsEaccent = (lastWord.equalsIgnoreCase(""));


			if (current.size() > 1) { removeFromList(current, "Verbe"); }

			boolean lastIsVerb    = listContains(last, "verbe#") && last.size() == 1;
			
			boolean currentNotOne = current.size() > 1;
			boolean containsVerb  = listContains(current, "verbe#");
			boolean lastWordIsE   = lastWord.equalsIgnoreCase("e"); // j'entre, il entre.


			String  verbe    = listContainsRecuperate(current, "verbe#");
			boolean canSplit = verbe.split("#").length > 1;
			String  prnm     = ""; if (canSplit) { prnm = verbe.split("#")[1].split(" ")[0]; }

			boolean isntThirdPers = prnm.contains("il ");
			boolean isThePrnm     = listEqualsElement(currentText, prnm);

			if (!isNewSentence && !isThePrnm) {
				System.out.println(prnm);
				isThePrnm = listEqualsElement(lastSentenceText, prnm);
			}

			
			if (currentNotOne && containsVerb && !isntThirdPers && !isThePrnm && !lastWordIsEaccent && !lastWordIsE && !lastIsVerb) {
				removeFromListContains(current, "verbe#");
				System.out.println("cannotBeVerbFromDefinatePronomNonePrnm " + syntaxe);
			}
		}		
	}

	private String verifyAccentLastWord(ArrayList<String> currentText, int syntaxe) {
	
		//Verify from last 2 letter contains accent.
		
		String[] accent = {"é", "è"};
		
		String   word         = currentText.get(syntaxe);
		String   lastWord     = "";
		boolean  wordIsLength = word.length() >= 2;
		
		
		if (wordIsLength) {

			lastWord = word.substring(word.length() - 1, word.length());
			String  lastWordx2   = word.substring(word.length() - 2, word.length() - 1);

			boolean lastWordIsE1 = thisListContainsWord(accent, lastWord);
			boolean lastWordIsE2 = thisListContainsWord(accent, lastWordx2);
			
			boolean lastWordIsEaccent  = lastWordIsE1 || lastWordIsE2;
			if (lastWordIsEaccent) { return lastWord; }

		}
		return lastWord;
	}
	
	
	
	
	
	
	
	
	
	
	private void prepOrArtOnlyNc(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Choice preposition from all article's if next's nom commun.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			boolean nextOne       = next.size() == 1;
			boolean currentNotOne = current.size() > 1;

			boolean nextNc        = listEqualsElement(next,    "Nom commun");
			boolean containsPrep  = listEqualsElement(current, "Préposition");
			boolean containsArt   = thisListEqualList(current, this.determinantCommun);
			
			if (nextOne && currentNotOne && nextNc && containsPrep && containsArt) {
				dontRemoveFromListElement(current, "Préposition");
				System.out.println("prepOrArtOnlyNc " + syntaxe);
			}
		}
	}
	
	
	private void cannotBeVerbIfAllLastPropoAreDet(ArrayList<ArrayList<String>> currentSyntaxe) {
		

		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean currentIsNotOne = current.size() > 1;
			boolean isNcOrADJ    = listEqualsElement(last, "Nom commun") || listEqualsElement(last, "Adjectif");
			boolean containsAdv  = listEqualsElement(current, "Adverbe");
			boolean containsVrb  = listContains(current, "verbe#");
			boolean lastIsntPrep = listEqualsElement(last, "Préposition") || listContains(last, "démonstratif");
			
			int matching = 0;
			for (String element: last) {
				boolean isDet  = thisListEqualWord(this.determinantCommun, element);
				boolean isNc   = element.equalsIgnoreCase("Nom commun") || element.equalsIgnoreCase("Adjectif");
				boolean isVerb = element.contains("verbe#");
				if (!isVerb && (isDet || isNc)) { matching += 1; }
			}
			
			boolean allLastAreDet      = matching == last.size() && matching > 0;

			
			if (currentIsNotOne && allLastAreDet && !isNcOrADJ && !lastIsntPrep) {
				removeFromListContains(current, "verbe#"); 
				System.out.println("cannotBeVerbIfAllLastPropoAreDet1 " + syntaxe);
			}
			else if (currentIsNotOne && isNcOrADJ && containsVrb && containsAdv) {
				removeFromListContains(current, "Adverbe"); 
				System.out.println("cannotBeVerbIfAllLastPropoAreDet2 " + syntaxe);
			}
		}
	}


	private void notTwoVerbWithoutDifferentTime(ArrayList<ArrayList<String>> currentSyntaxe, boolean endLoop) {
		

		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {

			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			

			boolean currentNotOne     = current.size() > 1;
			boolean lastOne           = last.size() == 1;

			boolean areVerbLast       = listContains(current, "verbe#") && 
										listContains(last,    "verbe#");	
			//[, verbe#j'ai#Indicatif présent#, Aï Nom commun] ai 2
			 //[verbe#reconnu#Participe passé masculin singulier#, Aucun mot trouvé] reconnu 3

			String[] timesOne = {"passé composé", "imparfait", "Participe passé", 
								 "Indicatif futur simple", "Participe présent", "présent", "passé simple"};
			
			String[] timesTwo = {"présent", "Indicatif passé simple", "Participe passé"};
			


			
			
			if (currentNotOne && lastOne && areVerbLast) {
				try {
					String lastV = listContainsRecuperate(last,    "verbe#").split("[#]")[2];
					String currV = listContainsRecuperate(current, "verbe#").split("[#]")[2];
		
					boolean times1 = thisListContainsWordTwoCase(timesOne, lastV);
					boolean times2 = thisListContainsWordTwoCase(timesTwo, currV);
					boolean coresponding = times1 && times2;
					
					if (!coresponding) {
						removeFromListContains(current, "verbe#");
						System.out.println("notTwoVerbWithoutDifferentTime11 " + syntaxe);
					}
					
					
				}catch (Exception e) {}
			}
			
			
			try {
				ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
				boolean nextOne           = next.size() == 1;
				boolean areVerbNext       = listContains(current, "verbe#") && 
										    listContains(next,    "verbe#");
			



				if (currentNotOne && nextOne && areVerbNext) {
	
					String nextV = listContainsRecuperate(next,    "verbe#").split("[#]")[2]; 
					String currV = listContainsRecuperate(current, "verbe#").split("[#]")[2]; 
	
					boolean[] conditions = {verbe(currV, nextV, "présent", "imparfait"), 
										    verbe(currV, nextV, "présent", "Participe passé"),
										    verbe(currV, nextV, "présent", "Passé composé"),
										    verbe(currV, nextV, "Imparfait", "Participe passé"),
										    verbe(currV, nextV, "présent", "Plus que parfait"),
										    verbe(currV, nextV, "Passé simple", "Passé antérieur"),
										    verbe(currV, nextV, "Futur", "Futur antérieur"),};
					
					
					
					boolean coresponding = false;
					for (boolean condi: conditions) { if (condi == true) { coresponding = true; break; } }
		
					
					if (!coresponding) {
						removeFromListContains(current, "verbe#");
						System.out.println("notTwoVerbWithoutDifferentTime21 AFTER " + syntaxe);
					}
	
					else if (coresponding && endLoop) {
						dontRemoveFromListContains(current, "verbe#");
						System.out.println("notTwoVerbWithoutDifferentTime22 AFTER " + syntaxe);
					}
						
				}
			}catch (Exception e) {}
		}
	}

	
	private boolean verbe(String one, String two, String prsnt1, String prsnt2) {
		
		//Return true if one time corresponding with the second.
		
		boolean caseOne = one.contains(prsnt1);
		boolean caseTwo = two.contains(prsnt2);
		
		if (caseOne && caseTwo) { return true; } 
		
		return false;
	}
	
	
	

	private void haveNoFoundAfterArt(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		String noFound  = "définition correspondante. Veuillez vérifier l'orthographe ...";


		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean currentIsOne    = current.size() == 1;
			boolean lastIsOne       = last.size() == 1;
			boolean lastIsArt       = thisListEqualList(last, this.determinantCommun);

			boolean currenContains  = listContains(current, noFound);


			if (currentIsOne && lastIsOne && currenContains && lastIsArt) {
				String[] afterArt = {"Adjectif", "Adverbe", "Nom commun"};
				for (String element: afterArt) { current.add(element); };
				current.remove(0);
				System.out.println("haveNoFoundAfterArt " + syntaxe);
			}
		}
	}




	

	
	

	private void cannotBeArtAfterArt(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		String[] noArt = {"Forme de pronom indéfini", "Pronom indéfini"};
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			String word = currentText.get(syntaxe);
			
			boolean currentNotOne = current.size() > 1;
			boolean lastIsOne     = last.size() == 1;
			
			boolean conditionLength = currentNotOne && lastIsOne;
			
			boolean lastIsArt      = thisListEqualList(last, this.determinantCommunWithotuAdj);
			boolean currentCntsArt = thisListEqualList(current, this.determinantCommunWithotuAdj);
			boolean isntPrepo      = listEqualsElement(last, "Préposition");
			boolean isntAdj        = listEqualsElement(last, "Adjectif")  || listEqualsElement(last, "Forme d'adjectif");
			boolean isDes          = word.equalsIgnoreCase("dés");

			boolean exceptSomeArt  = thisListEqualList(last, noArt);
			
			
			boolean raisinAll      = false;
			
			if (conditionLength && lastIsArt && currentCntsArt && !isntPrepo && !isntAdj) {
				
				ArrayList<String> save = new ArrayList<String>(current);
				removeFromListFromList(save, this.determinantCommunWithotuAdj);
				raisinAll = save.size() == 0;
				
			}
			
			
			if (conditionLength && lastIsArt && currentCntsArt && !isntPrepo && !isntAdj && !raisinAll && !exceptSomeArt) {
				removeFromListFromList(current, this.determinantCommunWithotuAdj);
				System.out.println("cannotBeArtAfterArt1 " + syntaxe);
			}
			if (isDes) { 
				current.clear();
				current.add("Nom commun"); 
				System.out.println("cannotBeArtAfterArt2 " + syntaxe);
			}
		}
	}


	private void removeInCaseWordWithSyntaxe(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		ArrayList<String> toRemove = new ArrayList<String>();
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);
			String wordText = currentText.get(syntaxe).toLowerCase();
			
			//[Ca Préposition, Ça Pronom démonstratif, Çà Adverbe, Ça Nom commun]

			for (int index=0; index < current.size(); index++) {
				
				String   synt      = current.get(index);
				String[] syntSplit = synt.split(" ");

				
				int    numberComposed = 0;
				String increment      = "";
				for (String composed: syntSplit) {
					String firstletter = "";
					
					boolean isComposed = composed.length() > 0;
					if (isComposed) { firstletter = Character.toString(composed.charAt(0)); }
					
					boolean isMaj  = firstletter.equals(firstletter.toUpperCase());
					if (isMaj) { numberComposed += 1; }
					
					boolean canIncrement = numberComposed > 1;
					if (canIncrement) { increment += (composed + " "); }

				}

				boolean thereAreMoreOneMaj = numberComposed > 1;
				if (thereAreMoreOneMaj) { 
					increment = increment.substring(0, increment.length() - 1);
					boolean isContening = current.contains(increment);
					if      (!isContening) { current.set(index, increment); }
					else if (isContening)  { current.remove(index); }
					System.out.println("removeInCaseWordWithSyntaxe " + syntaxe);
				}
				
			}
		}
	}







	private void removeTwoFollowingPrepAdverbe(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		ArrayList<Integer> toRemove = new ArrayList<Integer>();

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);
			ArrayList<String> next      = currentSyntaxe.get(syntaxe + 1);
			
			
			String currentWord = currentText.get(syntaxe);
			String nextWord    = currentText.get(syntaxe + 1);
			
			String incrementWord = currentWord + " " + nextWord;

			boolean currentIsOne = current.size() == 1;
			boolean nextIsOne    = next.size() == 1;
			
			boolean condition    = currentIsOne && nextIsOne;
			
			boolean currentIsPrep = listEqualsElement(current, "Préposition");
			boolean nextIsPrep    = listEqualsElement(next, "Préposition");
			
			boolean conditionPrep = currentIsPrep && nextIsPrep;
			
			boolean currentIsAdv  = listEqualsElement(current, "Adverbe");
			boolean nextIsAdv     = listEqualsElement(next, "Adverbe");
			
			boolean conditionAdv  = currentIsAdv && nextIsAdv;
			
			
			if 		(condition && conditionPrep)  { 
				currentText.set(syntaxe, incrementWord);
				toRemove.add(syntaxe + 1);
				System.out.println("removeTwoFollowingPrepAdverbe " + (syntaxe+1));
			}

			else if (condition && conditionAdv)   { 
				currentText.set(syntaxe, incrementWord);
				toRemove.add(syntaxe + 1);
				System.out.println("removeTwoFollowingPrepAdverbe " + (syntaxe + 1));
			}
		}
		for (int element: toRemove) { 
			currentSyntaxe.remove(element);
			currentText.remove(element);
		}
	}





	private void removeNOWord(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//Remove none definition word found.
		
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {

			ArrayList<String> current   = currentSyntaxe.get(syntaxe);
			boolean currentIsOne = current.size() == 1;
			boolean contains     =  listContains(current, "Le mot exact n'a pas été trouvé");
			boolean contains2     = listContains(current, "choisissez la bonne orthographe.");
			boolean contains3     = listContains(current, "Veuillez vérifier l'orthographe ...");
			boolean contains4    = listEqualsElement(current, "donc aucune définition correspondante. Veuillez vérifier l'orthographe ...");
			


			 
			if (currentIsOne && (contains  || contains4)) { toRemove.add(syntaxe); }
			else if (contains2) { removeFromListContains(current, "choisissez la bonne orthographe."); }
			else if (contains3) { removeFromListContains(current, "Veuillez vérifier l'orthographe ..."); }
		}
		

			
		int IndextoRemove = 0;
		boolean haveRemove = false;
		for (int index: toRemove) {
			currentSyntaxe.remove(index - IndextoRemove); 
			IndextoRemove += 1;
		}
		
		
		
	}

	



	
	
	private void removeIFEmpty(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		//Remove and put nom propre (complete second function) if none case.
		
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);
			String word = currentText.get(syntaxe);

			String firstletter = Character.toString(word.charAt(0));
			boolean isMaj  = firstletter.equals(firstletter.toUpperCase());
			
			boolean isEmpty = current.size() == 0;

			if (isEmpty && !isMaj) { toRemove.add(syntaxe); System.out.println("removeIFEmpty " + syntaxe);}
			else if (isEmpty && isMaj) { current.add("Nom propre"); }
		}
		

		int decrement = 0;
		for (int index: toRemove) {
			currentSyntaxe.remove(index + decrement);
			currentText.remove(index + decrement);
			decrement -= 1;
		}

	}
	
	
	
	
	
	
	
	
	private void isVerbBeforeAdvAndAfterParticule(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//After a particule and before an adverbe it's a verb.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			
			ArrayList<String> last   = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);
			ArrayList<String> next   = currentSyntaxe.get(syntaxe + 1);
			
			
			
			boolean lastIsOne = last.size() == 1;
			boolean nextIsOne = next.size() == 1;
			boolean currentNotOne = current.size() > 1;
			
			boolean conditions = lastIsOne && nextIsOne && currentNotOne;
			
			boolean lastContainsParticule = listEqualsElement(last, "Particule");
			boolean currentContainsVerb   = listContains(current, "verbe#");
			boolean nextContainsAdv       = listEqualsElement(next, "Adverbe");
			
			
			if (conditions && lastContainsParticule && currentContainsVerb && nextContainsAdv) {
				dontRemoveFromListContains(current, "verbe#");
				System.out.println("isVerbBeforeAdvAndAfterParticule " + syntaxe);
			}
		}
	}
	
	
	
	
	

	private void pronmRelOrInterro(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//Choice interogativ pronom or relivo from first word if last character's "?".
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);
			
			boolean currentNotOne    = current.size() > 1;
			boolean currentIsIntero  = listEqualsElement(current, "Pronom interrogatif");
			boolean isFirstWord      = syntaxe == 0;
			
			boolean lastIsInterro    = currentText.get(currentText.size() - 1).contentEquals("?");
			
			if (currentNotOne && currentIsIntero && (!isFirstWord || lastIsInterro)) {
				removeFromList(current, "Pronom interrogatif");
				System.out.println("pronmRelOrInterro remove interro " + syntaxe);
			}
			else if (currentNotOne && currentIsIntero && isFirstWord && lastIsInterro) {
				dontRemoveFromListElement(current, "Pronom interrogatif");
				System.out.println("pronmRelOrInterro no remove interro " + syntaxe);
			}
		}
	}




	private void notAdvEndSearch(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Cannot be adverbe because we make a treatement's adverbe before.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);

			boolean notOne      = current.size() > 1;
			boolean containsAdv = listContains(current, " Adverbe");
			

			if (notOne && containsAdv) {
				removeFromListContains(current, "Adverbe");
				System.out.println("notAdvEndSearch " + syntaxe);
			}
		}
	}



	private void cannotBeNcBeforeArt(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//Cannot be a nom commun if next word's -article- (not preposition) and is one of this words.

		String[] artCanBe = {"des", "du", "au", "contraction=de une", "de", "contraction=de un", "mon", "ma", "ce"};
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);
			ArrayList<String> next      = currentSyntaxe.get(syntaxe + 1);
			
			String nextWord = currentText.get(syntaxe + 1);
			
			boolean currentNotOne = current.size() > 1;
			boolean containsNc    = listEqualsElement(current, "Nom commun");
			
			boolean nextIsOne     = next.size() == 1;
			
			boolean isNotPrepo    = listEqualsElement(next, "Préposition");
			boolean isArt         = thisListEqualList(next, this.determinantCommun);
			boolean canBe         = thisListContainsWord(artCanBe, nextWord);

			boolean nextIsAdj     = listEqualsElement(next, "Adjectif") || listEqualsElement(next, "Forme d'adjectif");
			boolean nextIsVerb    = listContains(next, "verbe#");
			
			boolean nextCondition = !isNotPrepo && isArt && !canBe && !nextIsAdj && !nextIsVerb;
			

			
			
			if (currentNotOne && containsNc && nextCondition && nextIsOne) {
				removeFromList(current, "Nom commun");
				System.out.println("cannotBeNcBeforeArt " + syntaxe);
			}
		}
	}


	private void isVerbAfterPrnm(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Choice to put verbe after a personal pronom or personel relative.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last          = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> currentList   = currentSyntaxe.get(syntaxe);
			ArrayList<String> next   		= currentSyntaxe.get(syntaxe + 1);
			
			boolean isNotOne      = currentList.size() > 1;
			boolean thereIsVerbe  = listContains(currentList, "verbe#");
			boolean thereIsNm     = listEqualsElement(currentList, "Nom commun");
			
			boolean lastIsOne     = last.size() == 1;
			boolean lastIsPronm1  = listEqualsElement(last, "Pronom personnel");
			boolean lastIsPrnm2   = listEqualsElement(last, "Pronom relatif");
			
			boolean nextIsVrb     = listContains(currentList, "verbe#");
			
			boolean condition1    = isNotOne && lastIsOne && thereIsVerbe && thereIsNm;
			boolean condition2    = lastIsPronm1 || lastIsPrnm2;
			
			if (condition1 && condition2) {
				dontRemoveFromListContains(currentList, "verbe#");
				System.out.println("isVerbAfterPrnm " + syntaxe);
			}
			else if (lastIsPronm1 && lastIsOne && nextIsVrb) {
				dontRemoveFromListContains(currentList, "verbe#");
				System.out.println("isVerbAfterPrnm " + syntaxe);
			}
		}
	}


	private void isAdjectifNumeralByNumber(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		//Put adjective numéral if current word can be parseInt.

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> currentList   = currentSyntaxe.get(syntaxe);
			String word = currentText.get(syntaxe);

			int nb = -1;
			
			try {
				int wordNumber = Integer.parseInt(word);
				nb = wordNumber;
			} catch (Exception e) {}
			
			boolean isNumber = nb > -1;

			if (isNumber) { 
				currentList.clear(); currentList.add("Adjectif numéral");
				System.out.println("isAdjectifNumeralByNumber " + syntaxe);
			}
		}
	}


	private void itsVerb(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Dont remove verbe if next's verbe.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList   = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList      = currentSyntaxe.get(syntaxe + 1);
			
			boolean lengthNotOne = currentList.size() > 1;
			boolean containsVerb = listEqualsElement(currentList, "verbe#");
			boolean nextIsVerb   = listEqualsElement(currentList, "verbe#");
			boolean nextIsOne    = nextList.size() == 1;
			boolean isVerbByDef  = listEqualsElement(currentList, "Verbe");
					
			if (lengthNotOne && containsVerb && nextIsVerb && nextIsOne) {
				dontRemoveFromListElement(currentList, "verbe#");
				System.out.println("itsVerb " + syntaxe);
			}
		}
	}


	private void caseTirateVerbePronom(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//Avait-elle considerate as one word.
		//Add to text and syntaxe pronom personnel.
		String[] prnom = {"je", "tu", "il", "nous", "vous", "ils", "elles", "elle", "on"};
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> currentList   = currentSyntaxe.get(syntaxe);
			String            word          = currentText.get(syntaxe);
			
			boolean wordContainsTirate = word.contains("-");
			boolean notEmpty           = word.split("-").length > 1;
			if (wordContainsTirate && notEmpty) {
				String[] wordSplit = word.split("-");
				
				boolean isPrnm = thisListEqualWord(prnom, wordSplit[1]);
				if (isPrnm) {
					currentText.set(syntaxe,     wordSplit[0]);
					currentText.add(syntaxe + 1, wordSplit[1]);
					
					currentSyntaxe.add(syntaxe + 1, new ArrayList<String>());
					currentSyntaxe.get(syntaxe + 1).add("Pronom personnel");
					System.out.println("caseTirateVerbePronom " + syntaxe + 1);
				}
			}
		}
	}


	private void isAProblemHere(ArrayList<ArrayList<String>> currentSyntaxe, String string) {
		
		//In case one word syntaxe is empty display it. Framboise is the best index world, my cat.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {

			ArrayList<String> currentList   = currentSyntaxe.get(syntaxe);

			boolean currenListIsntEmpty = currentList.size() == 0;
			
			if (currenListIsntEmpty) {System.out.println("ERREUR: " + string);}
		}
		//System.out.println(currentSyntaxe.get(10) + " " + string);
		//framboise
	}



	
	private void cannotBeBeforeNc(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Remove pronom if next word's nom commun.
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last      = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);
			
			boolean currentIsOne  = current.size() == 1;
			boolean currentIsNc   = listEqualsElement(current, "Nom commun");
			boolean beforeIsPrnm  = listContains(last, "Pronom");
			boolean lastIsNotOne  = last.size() > 1;
					
					
			if (currentIsOne && currentIsNc && beforeIsPrnm && lastIsNotOne) {
				removeFromListContains(last, "Pronom");
				System.out.println("cannotBeBeforeNc " + syntaxe);
			}
		}
	}
	
	
	
	
	

	
	
	
	
	private void isPronomPersonnel(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//Put pronom personnel in one of this case. (Can be nom commun).
		
		String[] prnom = {"je", "tu", "il", "nous", "vous", "ils", "elles", "elle", "on"};
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);
			String word = currentText.get(syntaxe);
			
			boolean notOne = current.size() > 1;
			boolean isPrnm = thisListEqualWord(prnom, word);
			
			if (notOne && isPrnm) {
				current.clear(); current.add("Pronom personnel");
				System.out.println("isPronomPersonnel " + syntaxe);
			}
		}
	}
	


	private void isConjonctionOrPronom(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last      = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);
			ArrayList<String> next      = currentSyntaxe.get(syntaxe + 1);
			
			String  word = currentText.get(syntaxe);
			boolean isQue = word.equalsIgnoreCase("que") || word.equalsIgnoreCase("contraction=que");
			
			boolean isNotOne  = current.size() > 1;
			boolean nextIsOne = next.size() == 1;
			boolean lastIsOne = last.size() == 1;

			boolean conditionsLength = isNotOne && nextIsOne && lastIsOne;
			
			
			boolean isConjonc    = listEqualsElement(current, "Conjonction");
			boolean isPronomRel  = listEqualsElement(current, "Pronom relatif");
			
			boolean conditionCurrent = isConjonc && isPronomRel;
			

			
			boolean lastIsName   = listEqualsElement(last, "Nom commun");
			boolean lastIsVerbe  = listContains(last, "verbe#") || listEqualsElement(last, "Adverbe");
			
			boolean nextIsPronom = listEqualsElement(next, "Pronom personnel");
			boolean nextIsDet    = thisListEqualList(next, determinantCommun);
			
			
			if (isQue && conditionsLength && conditionCurrent && (lastIsName || nextIsPronom)) {
				dontRemoveFromListElement(current, "Pronom relatif");
				System.out.println("isConjonctionOrPronom keep prnm rel" + syntaxe);
			}
			

			if (isQue && conditionsLength && conditionCurrent && nextIsDet) {
				current.clear(); current.add("Conjonction coordination");
				System.out.println("isConjonctionOrPronom keep conjonction" + syntaxe);
			}
		}
	}
	
	
	
	private void cannotBeAnAdverbeCausePrepoAdverb(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean notLengthOne       = current.size() > 1;
			boolean lastIsOne          = next.size() == 1;
			boolean lastIsAdverbe      = listEqualsElement(next, "Adverbe");
			boolean currentContainsAdv = listEqualsElement(current, "Adverbe");
			
			if (notLengthOne && lastIsOne && lastIsAdverbe && currentContainsAdv) {
				removeFromList(current, "Adverbe");
				System.out.println("cannotBeAnAdverbeCausePrepoAdverb " + syntaxe);
			}
		}
		
		
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean notLengthOne       = current.size() > 1;
			boolean lastIsOne          = last.size() == 1;
			boolean lastIsAdverbe      = listEqualsElement(last, "Adverbe");
			boolean currentContainsAdv = listEqualsElement(current, "Adverbe");
			
			if (notLengthOne && lastIsOne && lastIsAdverbe && currentContainsAdv) {
				removeFromList(current, "Adverbe");
				System.out.println("cannotBeAnAdverbeCausePrepoAdverb " + syntaxe);
			}
		}
	}
	
	
	
	private void adverbeOrNc(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Choice to keep nom commun beetween nom commun and adverb. Becase last treatment.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean isNotOne    = current.size() > 1;
			boolean containsAdv = listEqualsElement(current, "Adverbe");
			boolean cotnainsNc  = listEqualsElement(current, "Nom commun");
			
			if (isNotOne && containsAdv && cotnainsNc) {
				removeFromList(current, "Adverbe");
				System.out.println("adverbeOrNc " + syntaxe);
			}
		}
		
		
	}
	
	
	private void isUnCase(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		//remove adjectif from case word's Un.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);
			String word = currentText.get(syntaxe);

			boolean equalAdj      = listEqualsElement(current, "Adjectif");
			boolean equalNc       = listEqualsElement(current, "Nom commun");
			boolean currentNotOne = current.size() > 1;
			boolean wordIsUn      = word.equalsIgnoreCase("un") ||word.equalsIgnoreCase("une");


			if (currentNotOne && wordIsUn && (equalAdj || equalNc)) {
				removeFromList(current, "Adjectif");
				removeFromList(current, "Nom commun");
				System.out.println("isUnCase " + syntaxe);
			}
		}
	}
	
	
	
	private void equalsVerbe(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Dictionnary put Verbe as rire gave (Nom commun, Verbe)
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last      = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);
			
			boolean EqualsVerbe      = listEqualsElement(current, "Verbe");
			boolean isNotOne         = current.size() > 1;
			boolean verbDicoFoundToo = listContains(current, "verbe#");
			
			boolean thereIsNc        = listEqualsElement(current, "Nom commun");
			boolean lastIsOne        = last.size() == 1;
			boolean isInfinitif      = listEqualsElement(current, "verbe#");
			
			boolean lastIsArt        = thisListEqualList(last, this.determinantCommun);
			
			if (EqualsVerbe && isNotOne && verbDicoFoundToo && thereIsNc && lastIsOne && isInfinitif && lastIsArt) {
				dontRemoveFromListContains(current, "Nom commun");
				System.out.println("equalsVerbe Nc" + syntaxe);
			}
			
			else if (EqualsVerbe && isNotOne && verbDicoFoundToo && thereIsNc && lastIsOne && !isInfinitif && !lastIsArt) {
				dontRemoveFromListContains(current, "verbe#");
				System.out.println("equalsVerbe Verbe" + syntaxe);
			}
		}
	}
	
	
	private void lastIsVerbNextIsVerbRemoveNextIfNotPP(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//Verify from text
		
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> last      = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);
			ArrayList<String> next      = currentSyntaxe.get(syntaxe + 1);
			
			
			String word = currentText.get(syntaxe);
			
			boolean lastIsVerb      = listContains(last,    "verbe#");
			boolean currentIsPronom = listContains(current, "Pronom personnel");
			boolean nextIsVerb      = listContains(next,    "verbe#");
			boolean nextIsPP        = listContains(next,    "Participe");
			
			boolean currentNotOne   = current.size() > 1;
			boolean nextNotOne      = next.size() > 1;
			boolean lastOne         = last.size() == 1;
			
			boolean conditionLength = currentNotOne && nextNotOne && lastOne;
			
			
			if (lastIsVerb && currentIsPronom && nextIsVerb && !nextIsPP && conditionLength) {
				removeFromList(current, "Pronom personnel");
				removeFromListContains(next, "verbe#");
				System.out.println("lastIsVerbNextIsVerbRemoveNextIfNotPP " + (syntaxe + 1) + "& " + syntaxe);
			}
		}
	}
	
	
	
	
	private void nomCommunV2FromLastSentence(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		

		
		String[] ponct = {"«", "'", "\"", "»", ".", ":", "!", "-", "—", "?", "?"};
		
		String[] ponct2 = {"point", "point exclamation", "point interrogation", "point virgule", "guillement ouvrant", "guillement fermant",
						   "tiret", "Ponctuation"};
		
		boolean notEmpty = saveSyntaxe.size() > 0;
		if (notEmpty) {

			ArrayList<ArrayList<String>> last = saveSyntaxe.get(saveSyntaxe.size() - 1);
			int     lastIndex      = last.size() - 1;
			String  lastElement    = last.get(lastIndex).get(0);
			boolean FirstWord      = thisListEqualWord(ponct2, lastElement);

			for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
				
				ArrayList<String> current   = currentSyntaxe.get(syntaxe);

		
				boolean notFirstWord = syntaxe > 0;
				String lastword = ""; if (notFirstWord) { lastword = currentText.get(syntaxe - 1); }
				
				String word        = currentText.get(syntaxe);
				
				boolean notEmptyWord = !word.equalsIgnoreCase("");
				if (notEmptyWord) {
					String firstLetter = Character.toString(word.charAt(0));
	
					boolean isMaj         = firstLetter.equals((firstLetter.toUpperCase()));
					boolean lastIsntPonct = thisListEqualWord(ponct, lastword);
					boolean isntPonct     = thisListEqualWord(ponct, word);

	
					if (isMaj && !FirstWord && !lastIsntPonct && !isntPonct) {
						current.clear();
						current.add("Nom propre");
						System.out.println("nomCommunV2FromLastSentence " + syntaxe);
					}
					else if (isntPonct) {
						current.clear();
						current.add("Ponctuation");
						System.out.println("nomCommunV2FromLastSentence2 " + syntaxe);
					}
				}
			}
		}

	}
	

	private void AdjOrNm(ArrayList<ArrayList<String>> currentSyntaxe) {

		for (int syntaxe = 2; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> lasxt2   = currentSyntaxe.get(syntaxe - 2);
			ArrayList<String> last     = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current  = currentSyntaxe.get(syntaxe);
			ArrayList<String> next     = currentSyntaxe.get(syntaxe + 1);
			
			boolean lastX2IsOne = lasxt2.size() == 1;
			boolean lastIsOne   = last.size()   == 1;
			
			boolean lastX2IsNc = listEqualsElement(lasxt2, "Nom commun");
			boolean lastIsNc   = listEqualsElement(last,   "Nom commun");
			boolean lastCnjc   = listEqualsElement(last,   "Conjonction de coordination");
			
			boolean conditionLast = lastX2IsOne && lastIsOne && ( (lastX2IsNc && !lastCnjc) || lastIsNc);
			
			boolean isNotOne    = current.size() > 1;
			boolean isTwo       = current.size() == 2;
			boolean containsNm  = listEqualsElement(current, "Nom commun");
			boolean containsAdj = listEqualsElement(current, "Adjectif");
			
			
			
			boolean nextIsTwo             = next.size() == 2 || next.size() == 1;
			boolean nextNotOnlyNc         = listEqualsElement(next, "Nom commun") || 
											listEqualsElement(next, "Forme de nom commun");
			
			boolean nextNc                = nextIsTwo && nextNotOnlyNc;
			


			if (isNotOne && containsNm && containsAdj && conditionLast) {
				dontRemoveFromListElement(current, "Adjectif");
				System.out.println("AdjOrNm2 " + syntaxe);
			}
			else if (isNotOne && containsNm && containsAdj && conditionLast && nextNc) {
				dontRemoveFromListElement(current, "Nom commun");
				System.out.println("AdjOrNm3 " + syntaxe);
			}
		}
	}
	
	
	
	private void removeVarianteTypoFromScrap(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Remove word form dictionnary. Case not a good orthograph.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);

			boolean currentContainsError = listContains(current, "Variante par contrainte typographique");

			if (currentContainsError) {
				removeFromListContains(current, "Variante par contrainte typographique");
				System.out.println("removeVarianteTypoFromScrap " + syntaxe);
			}
		}
	}

	
	
	
	
	
	
	
	
	private void isVerbOrNcLastIsNc(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Choice to remove nom commun after a nom commun.
		
		System.out.println(currentSyntaxe);
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current   = currentSyntaxe.get(syntaxe);
			ArrayList<String> last      = currentSyntaxe.get(syntaxe - 1);
			
			boolean currentIsNotOne     = current.size() > 1;
			boolean lastIsOne           = last.size() == 1;
			
			boolean lastIsNc            = listEqualsElement(last, "Nom commun");
			boolean currentContainsNc   = listEqualsElement(current, "Nom commun");
			boolean currentContainsVerb = listContains(current, "verbe#");
			
			if (currentIsNotOne && lastIsOne && lastIsNc && currentContainsNc && currentContainsVerb) {
				removeFromList(current, "Nom commun");
				System.out.println("isVerbOrNcLastIsNc " + syntaxe);
			}	
		}
	}
	
	
	private void caseToutes(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		//Case toute and toutes. Choice to put adverv or pronom indefini.
		
		String[] toutList = {"toutes", "toute"};
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList   = currentSyntaxe.get(syntaxe);
			ArrayList<String> next          = currentSyntaxe.get(syntaxe + 1);

			String  word  = currentText.get(syntaxe);
			
			boolean isTwo = currentList.size() > 1; 
			
			boolean isWordTout 		 = word.equalsIgnoreCase("tout");
			boolean isWordToutAccord = thisListEqualWord(toutList, word);
			
			boolean nextIsAdj   = listEqualsElement(next, "Adjectif");
			boolean nextIsOne   = next.size() == 1;
			
			boolean currentContainsAdv = listEqualsElement(currentList, "Adverbe");
			boolean currentContainRonm = listContains(currentList, "ronom indéfini");
			
			
			if (isWordTout && nextIsAdj && nextIsOne && isTwo && currentContainsAdv) {
				dontRemoveFromListElement(currentList, "Adverbe");
				System.out.println("caseToutes (adverbe) " + syntaxe);
			}
			
			if ((isWordTout || isWordToutAccord) && nextIsOne && !nextIsAdj && isTwo && currentContainRonm) {
				dontRemoveFromListContains(currentList, "ronom indéfini");
				System.out.println("caseToutes (Pronom indéfini) " + syntaxe);
			}
		}
	}
	
	

	private void choiceArtIndefini(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Choice to keep article indéfini from: numéral and pronom.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> currentList   = currentSyntaxe.get(syntaxe);
			
			boolean sizeIsThree    =  currentList.size() == 3;
			boolean containsArtInd = listEqualsElement(currentList, "Forme d’article indéfini");
			boolean contanisAdjNum = listEqualsElement(currentList, "Forme d’adjectif numéral");
			boolean ContainPronom  = listEqualsElement(currentList, "Forme de pronom indéfini");
			
			if (sizeIsThree && containsArtInd && contanisAdjNum && ContainPronom) {
				dontRemoveFromListElement(currentList, "Forme d’article indéfini");
				System.out.println("choiceArtIndefini " + syntaxe);
			}
		}
	}
	

	private void isArticleIndefiniRelatif(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//IF pronom relative beetween relativo and indéfini.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> currentList   = currentSyntaxe.get(syntaxe);
			
			boolean currentIsTwo           = currentList.size() == 2;
			boolean currentContainsPrnmRel = listEqualsElement(currentList, "Pronom relatif");
			boolean currentContainsPrnInd  = listEqualsElement(currentList, "Pronom indéfini");
			
			
			if (currentIsTwo && currentContainsPrnmRel && currentContainsPrnInd) {
				currentList.clear();
				currentList.add("Pronom relatif");
				System.out.println("isArticleIndefiniRelatif " + syntaxe);
			}
		}
	}
	
	
	
	
	
	private void isNomCommunAfterNumeral(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> originalSentence) {

		//
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last          = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> currentList   = currentSyntaxe.get(syntaxe);
		
			boolean fromOriginParenthese    = originalSentence.get(syntaxe).equalsIgnoreCase(")") || 
											  originalSentence.get(syntaxe).equalsIgnoreCase("-");
			

			boolean lastContainsNumeral = listEqualsElement(last, "Adjectif numéral");
			boolean lastIsOne           = last.size() == 1;
			
			boolean currentIsNotOne     = currentList.size() > 1;
			
			if (currentIsNotOne) { removeFromList(currentList, "Verbe"); }
			currentIsNotOne     = currentList.size() > 1;

			boolean currentContainsVerb = listContains(currentList, "verbe#");

			if (lastContainsNumeral && lastIsOne && currentIsNotOne && currentContainsVerb && !fromOriginParenthese) {
				removeFromListContains(currentList, "verbe#");
				System.out.println("isNomCommunAfterNumeral " + syntaxe);
			}
		}
	}
	
	
	private void isAdjNumeralOrNomCommun(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> currentList   = currentSyntaxe.get(syntaxe);
			
			boolean sizeIsTwo   = currentList.size() == 2;
			boolean containsNc  = listEqualsElement(currentList, "Nom commun");
			boolean containsNum = listEqualsElement(currentList, "Adjectif numéral");
			
			if (sizeIsTwo && containsNc && containsNum) {
				dontRemoveFromListElement(currentList, "Adjectif numéral");
				System.out.println("isAdjNumeralOrNomCommun " + syntaxe);
			}
		}
	}
	
	
	
	
	
	private void saCase(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			String word 			  = currentText.get(syntaxe);
			
			boolean wordIsSa = word.equalsIgnoreCase("sa");
			if (wordIsSa) {
				current.clear();
				current.add("Adjectif possessif");
				System.out.println("saCase " + syntaxe);
			}
		}
	}
	
	
	private void maisAndCarIsConjonction(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			String word = currentText.get(syntaxe);
			
			boolean wordIsMais = word.equalsIgnoreCase("mais");
			boolean wordIsCar  = word.equalsIgnoreCase("car");
			
			if (wordIsMais || wordIsCar) {
				current.clear();
				current.add("Conjonction de coordination");
				System.out.println("maisAndCarIsConjonction " + syntaxe);
			}
		}
	}
	
	
	private void isAdjectifIsNextIsArt(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			boolean lastContainsNm     = listEqualsElement(last, "Nom commun");
			boolean lastIsOne          = last.size() == 1;
					
			boolean currentIsTwo       = current.size() == 2;
			boolean currentContainsNm  = listEqualsElement(current, "Nom commun");
			boolean currentContainsAdj = listEqualsElement(current, "Adjectif");
			
			boolean nextIsOne       = next.size() == 1;
			boolean nextContainsArt = thisListEqualList(next, this.determinantCommun);
			
			if (currentIsTwo && currentContainsNm && currentContainsAdj && nextIsOne && nextContainsArt && lastContainsNm && lastIsOne) {
				removeFromList(current, "Nom commun");
				System.out.println("isAdjectifIsNextIsArt " + syntaxe);
			}
		}
	}
	
	
	
	private void isWordNeIsParticule(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			String word = currentText.get(syntaxe);
			
			boolean wordIsNe = word.equalsIgnoreCase("ne");
			
			if (wordIsNe) {
				current.clear();
				current.add("Particule");
				System.out.println("isWordNeIsParticule " + syntaxe);
			}
		}
	}


	
	private void caseArtPartitifAndIndefini(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean currentIsLengthTwo = current.size() == 2;
			boolean containsArtNum     = listEqualsElement(current, "Adjectif numéral");
			boolean containsArtInd     = listEqualsElement(current, "Article indéfini");

			
			if (currentIsLengthTwo && containsArtNum && containsArtInd) {
				removeFromList(current, "Adjectif numéral");
				System.out.println("caseArtPartitifAndIndefini " + syntaxe);
			}
		}
	}
	
	
	
	private void isNotConjonctionOrIfPrepoBefore(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			String word = currentText.get(syntaxe);
			
			boolean wordIsOr    = word.equalsIgnoreCase("or");
			boolean lastIsPrepo = listEqualsElement(last, "Préposition");
			boolean lastIsArt   = listEquals(last, this.determinantCommun);
			
			if (wordIsOr && (lastIsPrepo || lastIsArt)) {
				current.clear();
				current.add("Nom commun");
				System.out.println("isNotConjonctionOrIfPrepoBefore " + syntaxe);
			}
		}
	}
	
	

	
	
	
	private void cannotBeAdjBeforeArt(ArrayList<ArrayList<String>> currentSyntaxe) {
		

		for (int syntaxe = 2; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
		
			
			ArrayList<String> lastx2 = currentSyntaxe.get(syntaxe - 2);
			ArrayList<String> last = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);

			
			boolean lastX2isAdj = listEqualsElement(lastx2, "Adjectif") || listEqualsElement(lastx2, "Forme d'adjectif");
			boolean lastIsCnjnc = listEqualsElement(last, "Conjonction de coordination");
			
			boolean lastX2isAdjIsOne = lastx2.size() == 1;
			boolean lastIsCnjncisOne = last.size() == 1;
			
			boolean conditionLast = lastX2isAdj && lastIsCnjnc && lastX2isAdjIsOne && lastIsCnjncisOne;
			
			boolean currentIsNotOne = current.size() > 1;
			boolean nextIsOne 	    = next.size() == 1;
			boolean nextIsArt 		= listEquals(next, this.determinantCommun);
		    boolean nextContainsInd = listContains(next, "indéfini");
		    
			boolean nextIsPrep      = listEqualsElement(next, "Préposition");
			boolean containsAdj     = listEqualsElement(next, "Adjectif");
			
			boolean currentContainsAdj    = listEqualsElement(current, "Adjectif");
			
			if (currentIsNotOne && conditionLast && nextIsPrep && nextIsOne && containsAdj && currentContainsAdj) {
				dontRemoveFromListElement(current, "Adjectif");
				System.out.println("cannotBeAdjBeforeArt " + syntaxe);
			}
			
			if (currentIsNotOne && nextIsOne && (nextIsArt && !nextContainsInd) && !conditionLast && !nextIsPrep) {
				removeFromList(current, "Adjectif");
				System.out.println("cannotBeAdjBeforeArt " + syntaxe);
			}
			
		}

	}
	
	
	private void syntaxeIsCommun(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> currentList   = currentSyntaxe.get(syntaxe);
			boolean currentIsCommunWIthoutName = listEqualsElement(currentList, "commun");
			
			if (currentIsCommunWIthoutName) {
				removeFromList(currentList, "commun");
				currentList.add("Nom commun");
				System.out.println("syntaxeIsCommun " + syntaxe);
			}
		}
	}
	
	
	private void isNotAdjIfNcBeforeNcAfter(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);

			boolean lastIsOne = last.size() == 1;
			boolean nextIsOne = next.size() == 1;
			boolean currentISNotOne = current.size() > 1;
			boolean conditionLength = lastIsOne && nextIsOne && currentISNotOne;
			
			boolean lastIsNc  = listEqualsElement(last, "Nom commun");
			boolean lastIsAdj = listEqualsElement(last, "Adjectif");
			boolean lastIsNp  = listEqualsElement(last, "nom propre");
			
			boolean nextIsNc  = listEqualsElement(next, "Nom commun");
			boolean nextIsAdj = listEqualsElement(next, "Adjectif");
			boolean nextIsNp  = listEqualsElement(next, "nom propre");
			
			boolean conditionNextLast = (lastIsNc || lastIsAdj || lastIsNp) && (nextIsNc || nextIsAdj || nextIsNp);

			boolean currentContainsAdj = listEqualsElement(current, "Adjectif");
			boolean currentContainsArt = listEqualsElement(current, "Forme d’article défini");
			
			if (conditionLength && conditionNextLast && currentContainsAdj && currentContainsArt) {
				removeFromList(current, "Adjectif");
				System.out.println("isNotAdjIfNcBeforeNcAfter (remove adj)" + syntaxe);
			}	
		}
	}
	
	
	
	
	
	private void isLastWordCantBeAdjIfBeforeArt(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);


			boolean currentIsNotOne       = current.size() > 1;
			boolean lastIsOne 			  = last.size()    == 1;

			boolean lastIsArt             = thisListEqualList(last, this.determinantCommun);
			
			boolean nextContainsNomCOmmun = listEqualsElement(current, "Nom commun");
			boolean nextContainsAdj       = listEqualsElement(current, "Adjectif");

			boolean lastWord              = syntaxe == currentSyntaxe.size() - 1;

			if (currentIsNotOne && lastIsOne && lastIsArt && nextContainsNomCOmmun && nextContainsAdj && lastWord) {
				dontRemoveFromListContains(current, "Nom commun");
				System.out.println("isLastWordCantBeAdjIfBeforeArt " + syntaxe);
			}
		}
	}
	
	
	
	private void isPrepositionIfNomCOmmunLast(ArrayList<ArrayList<String>> currentSyntaxe) {

		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);

			boolean currentIsNotOne       = current.size() > 1;
			boolean lastIsOne 			  = last.size()    == 1;
			boolean nextIsOnlyTwo         = next.size()    == 2;
			
			boolean conditionLength       = currentIsNotOne && lastIsOne && nextIsOnlyTwo;
			
			boolean lastIsNomCommun       = listEqualsElement(last, "Nom commun");
			boolean lastIsAdj             = listEqualsElement(last, "Adjectif");
			boolean nextContainsNomCOmmun = listEqualsElement(next, "Nom commun");
			boolean nextContainsAdj       = listEqualsElement(next, "Adjectif");

			boolean currentContainsArt    = listEqualsElement(current, "Forme d’article indéfini");
			boolean currentContainsPrep   = listEqualsElement(current, "Préposition");

			boolean currentConditions     = currentContainsArt && currentContainsPrep;
						
			if (conditionLength && lastIsNomCommun && lastIsAdj && nextContainsNomCOmmun && nextContainsAdj && currentContainsArt) {
				dontRemoveFromListContains(current, "Préposition");
				System.out.println("isPrepositionIfNomCOmmunLast (prépo) " + syntaxe);
			}
			
			if (conditionLength && !lastIsNomCommun && !lastIsAdj && nextContainsNomCOmmun && nextContainsAdj && currentContainsArt) {
				dontRemoveFromListContains(current, "Forme d’article indéfini");
				System.out.println("isPrepositionIfNomCOmmunLast (art) " + syntaxe);
			}
			
			if (lastIsOne && currentIsNotOne && (lastIsNomCommun || lastIsAdj) && (nextContainsNomCOmmun || nextContainsAdj) && currentConditions) {
				dontRemoveFromListContains(current, "Préposition");
				System.out.println("isPrepositionIfNomCOmmunLast (prépo) " + syntaxe);
			}
			
			
		}
	}
	
	private void unRemoveAdjectif(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		//Un case. Remove adjective and nom commun.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			String word = currentText.get(syntaxe);
			
			boolean currentIsUn        = word.equalsIgnoreCase("un");
			boolean isOne = current.size() == 1;
			
			boolean containsAdh = listEqualsElement(current, "Adjectif");
			boolean containsNc  = listEqualsElement(current, "Nom commun");
			
			if (currentIsUn && !isOne && containsAdh && containsNc) {
				removeFromList(current, "Adjectif");
				removeFromList(current, "Nom commun");
				System.out.println("unRemoveAdjectif " + syntaxe);
			}
		}
	}
	
	
	
	private void cantBeAdj(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		//Is adjectif or verbe from next or last.
		//from next (articles, adjectif possessif or conjonction...)

		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			String            word    = currentText.get(syntaxe);

			boolean isRelativX2 = false;
			boolean isNcX2      = false;
			try {
				ArrayList<String> lastX2    = currentSyntaxe.get(syntaxe - 2);

				boolean 		  lastX2One = lastX2.size() == 1;
				boolean 		  isRel     = listEqualsElement(lastX2, "Pronom relatif");
	
				if (lastX2One && isRel) {isRelativX2 = true;}
				
				isNcX2      = listEqualsElement(lastX2, "Nom commun");
		
			}catch (Exception e) {}

		
			boolean currentIsTwo       = current.size() > 1;
			boolean currentContainsAdj = listEqualsElement(current, "Adjectif");
			boolean currentContainsVrb = listContains(current, "verbe#");

			boolean lastNotNc      = listEqualsElement(last, "Nom commun");
			boolean wordIsNotEmpty = !word.equalsIgnoreCase("");

			//Condition lastx2 is Nc and last's verbe.
			boolean lastIsVerb = listContains(last, "verbe#");
			boolean lastX2IsNc = isNcX2;
			boolean  conditionNcVerb = lastIsVerb && lastX2IsNc;
			
			
			String[] canBeAfterVerb   = {"Adjectif possessif", "Conjonction de coordination"};
			boolean  nextIsAdjArt     = thisListContains(next, canBeAfterVerb);
			boolean  nextIsArt        = thisListEqualList(next, determinantCommunWithotuAdjWithoutPrep);
			boolean  conditionCanNext = nextIsAdjArt || nextIsArt;
			

			boolean  isAvoir1 = false;
			boolean  isAvoir2 = false;
			String   verbe    = "";
			try {
				verbe    = listContainsRecuperate(last, "verbe#").split("[#]")[1].split(" ")[1];
				isAvoir1 = verbe.equalsIgnoreCase("a");
				isAvoir2 = verbe.substring(0, 1).equalsIgnoreCase("av");
			} catch (Exception e) {}


			
			boolean conditioNAvoir = isAvoir2 || isAvoir1;
			
			
			
			if (wordIsNotEmpty) { //can catch the last word's ponctuation.

				
				if (currentIsTwo && currentContainsAdj && currentContainsVrb && !lastNotNc && !isRelativX2 && !conditionNcVerb) {
					removeFromList(current, "Adjectif");
					System.out.println("cantBeAdj1   " + syntaxe);
				}
				
				else if (currentIsTwo && currentContainsAdj && currentContainsVrb
						&& !lastNotNc && !isRelativX2 && conditionNcVerb && !conditionCanNext && !conditioNAvoir) {
					dontRemoveFromListElement(current, "Adjectif");
					System.out.println("cantBeAdj is adjectif   " + syntaxe);
				}
				
				else if (conditioNAvoir && currentIsTwo && currentContainsAdj && currentContainsVrb) {
					removeFromList(current, "Adjectif");
					System.out.println("cantBeAdj3   " + syntaxe);
				}
				
			}
		}
	}
	
	
	private void enPronomIfAfterPronomAndBeforeVerb(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 2; syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next 	  = currentSyntaxe.get(syntaxe + 1);
			ArrayList<String> nextX2  = currentSyntaxe.get(syntaxe + 2);
			
			String word = currentText.get(syntaxe + 1);
			
			boolean currentIsPronom = listEqualsElement(current, "Pronom personnel");
			boolean nextIsEn        = word.equalsIgnoreCase("en");
			boolean nextX2IsVerb    = listContains(nextX2, "verbe#");
			
			boolean currentIsOne    = current.size() == 1;
			boolean nextX2IsOne     = nextX2.size()  == 1;
			
			boolean conditionLength = currentIsOne && nextX2IsOne;
			
			if (currentIsPronom && nextIsEn && nextX2IsVerb && conditionLength) {
				next.clear();
				next.add("Pronom personnel");
				System.out.println("enPronomIfAfterPronomAndBeforeVerb " + (syntaxe + 1));
			}
		}
	}
	
	
	
	
	private void adjectifOrAdverbeNotAdverbeCauseBegening(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean containsAdverbe  = listEqualsElement(current, "Adjectif");
			boolean containsAdjectif = listEqualsElement(current, "Adverbe");
			boolean isOnlyTwo        = current.size() == 2;
			
			if (containsAdverbe && containsAdjectif && isOnlyTwo) {
				dontRemoveFromListContains(current, "Adjectif");
				System.out.println("adjectifOrAdverbeNotAdverbeCauseBegening " + syntaxe);
			}
		}
	}

	

	private void lengthTwoBeforeArtIsNc(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> last 	  = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			

			boolean groupIsLengthTwo = currentSyntaxe.size() == 2;
			boolean lastIsOne 	     = last.size() == 1;
			boolean currentIsNotOne  = current.size() > 1;
			boolean lastIsArt        = listEquals(last, this.determinantCommun);
			
			boolean currentIsAdj     = listEqualsElement(current, "Adjectif");
			boolean currentIsNc      = listEqualsElement(current, "Nom commun");
			boolean currentIsAdvberb = listEqualsElement(current, "Adverbe");
			
			boolean conditionCurrent = currentIsAdj && currentIsNc && currentIsAdvberb;
			if (groupIsLengthTwo && lastIsOne && currentIsNotOne && lastIsArt && conditionCurrent) {
				dontRemoveFromListElement(current, "Nom commun");
				System.out.println("lengthTwoBeforeArtIsNc " + syntaxe);
			}
		}
	}
	

	private void cannotBeAdjWithoutNomCommun(ArrayList<ArrayList<String>> currentSyntaxe) {

		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> last = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next = currentSyntaxe.get(syntaxe + 1);
			
			boolean nextIsNomCommun   = listEqualsElement(next, "Nom commun");
			boolean currentIsNotOne   = current.size() > 1;
			boolean lastIsPreposition = listEqualsElement(last, "Préposition");
			boolean isAdjectif        = listEqualsElement(current, "Adjectif");
			boolean isNomCommun       = listEqualsElement(current, "Nom commun");
			boolean lastIsVerb        = listContains(last, "verbe#");
			
			if (!nextIsNomCommun && currentIsNotOne && lastIsPreposition && isAdjectif && isNomCommun && !lastIsVerb) {
				removeFromList(current, "Adjectif");
				System.out.println("cannotBeAdjWithoutNomCommun " + syntaxe);
			}
		}
	}
	
	
	
	
	private void cannotBeInterjectionLastWord(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Delete interjection last word.
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			boolean isLastWord     = (syntaxe == currentSyntaxe.size() - 1);
			boolean isNotLengthOne = current.size() > 1;
			boolean isInterjection = listEqualsElement(current, "Interjection");

			boolean isNotOnePropo  = syntaxe < currentSyntaxe.size() - 1 &&
					(currentSyntaxe.size() == 2 && listEqualsElement(currentSyntaxe.get(syntaxe + 1), "Ponctuation")) ||
					currentSyntaxe.size() == 1;

			if (isLastWord && isNotLengthOne && isInterjection && !isNotOnePropo) {
				removeFromList(current, "Interjection");
				System.out.println("cannotBeInterjectionLastWord " + syntaxe);
			}
		}
	}
	
	
	
	private void isPrepositionOrArticleCaseDe(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		for (int syntaxe = 2; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> lastx2 = currentSyntaxe.get(syntaxe - 2);
			ArrayList<String> last   = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			ArrayList<String> next    = currentSyntaxe.get(syntaxe + 1);
			
			String            word    = currentText.get(syntaxe);
			
			boolean isNotLengthOne   = current.size() > 1;
			boolean curentWordIsDe   = word.equalsIgnoreCase("de");
			
			boolean currentWordIsCnt = word.equalsIgnoreCase("contraction=de");
			boolean isPresentArt     = listContains(current, "article");
			boolean isPresentPrep     = listContains(current, "Préposition");
			
			
			boolean nextIsOne        = next.size() == 1;
			
			boolean nextIsNm         = listEqualsElement(next,   "Nom commun");
			
			boolean lastx2IsNm       = listEqualsElement(lastx2, "Nom commun");
			boolean lastIsCnj        = listEqualsElement(last,    "Conjonction de coordination");
			
			boolean lastIsNm         = listEqualsElement(last,    "Nom commun");
			
			
			
			
			if (isNotLengthOne && curentWordIsDe && !isPresentArt && nextIsOne) {
				current.clear();
				current.add("Forme d’article indéfini");
				System.out.println("isPrepositionOrArticleCaseDe " + syntaxe);
			}

			if (isNotLengthOne && curentWordIsDe && !isPresentArt && nextIsOne) {
				dontRemoveFromListContains(current, "article");
				System.out.println("isPrepositionOrArticleCaseDe (de article) " + syntaxe);
			}
			
			
			if (isNotLengthOne && currentWordIsCnt && nextIsOne && !lastIsNm && lastIsCnj && lastx2IsNm && isPresentPrep) {
				dontRemoveFromListContains(current, "Préposition");
				System.out.println("isPrepositionOrArticleCaseDe (argent et de rubis) " + syntaxe);
			}
			
			
			if (isNotLengthOne && currentWordIsCnt && nextIsOne && lastIsNm && isPresentPrep) {
				dontRemoveFromListElement(current, "Préposition");
				System.out.println("isPrepositionOrArticleCaseDe (de prepo ou partitif) " + syntaxe);
			}
		}
	}
	
	
	
	private void removeTwoSameSyntaxe(ArrayList<ArrayList<String>> currentSyntaxe) {

		ArrayList<String> toRemove = new ArrayList<String>();
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			for (int index1=0; index1 < current.size(); index1++) {
				
				String element1 = current.get(index1);
				
				for (int index2=0; index2 < current.size(); index2++) {
					
					String element2 = current.get(index2);
					
					boolean isSameElement  = element1.equalsIgnoreCase(element2);
					boolean isNotSameIndex = index1 != index2; 

					if (isSameElement && isNotSameIndex) { 
						current.remove(element1); 
						System.out.println("removeTwoSameSyntaxe " + syntaxe);
					}
				}
			}
		}
	}
	
	


	
	private void replaceFormeByNomCommun(ArrayList<ArrayList<String>> currentSyntaxe, String find, String replace, String not) {

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> current = currentSyntaxe.get(syntaxe);
			
			for (int index=0; index < current.size(); index++) {

				String element = current.get(index);
				boolean isFormeNomCommun = element.contains(find);
				boolean isNot            = element.contains(not);
				if (isFormeNomCommun && !isNot) {
					current.set(index, replace);
					System.out.println("replaceFormeByNomCommun " + syntaxe);
				}
			}
		}
	}
	
	
	private void canBeVerbBeforeVerbAfterPrepo(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> lastList     = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList     = currentSyntaxe.get(syntaxe + 1);

			boolean lastIsOne 		 = lastList.size() == 1;
			boolean currentIsNotOne  = currentList.size() > 1;
			boolean nextIsOne        = nextList.size() == 1;
			
			boolean conditionLength  = lastIsOne && currentIsNotOne && nextIsOne;
			
			boolean lastContainsVerb    = listContains(lastList, "verbe#");
			
			boolean currentContainsVerb = listContains(currentList, "verbe#");
			boolean currentContainsAdj  = listEqualsElement(currentList, "Adjectif");
			boolean currentContainsNm   = listEqualsElement(currentList, "Nom commun");
			
			boolean conditionCurrent    = currentContainsVerb && currentContainsAdj && currentContainsNm;
			
			
			boolean nextContainsPrepo  = listEqualsElement(nextList, "Préposition");

			
			if (conditionLength && conditionCurrent && nextContainsPrepo) {
				dontRemoveFromListContains(currentList, "verbe#");
				System.out.println("canBeVerbBeforeVerbAfterPrepo " + syntaxe);
			}
		}
	}
	
	private void isAdverbe(ArrayList<ArrayList<String>> currentSyntaxe) {

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList     = currentSyntaxe.get(syntaxe + 1);

			boolean currentIsOne          = currentList.size() == 1;
			boolean lastIsNotOne 		  = nextList.size() > 1;
			boolean currentContainsVerb   = listContains(currentList, "verbe#");
			boolean nextContainsAdverb    = listEqualsElement(nextList, "Adverbe");
			
			boolean nextIsNotArt          = listEquals(nextList, this.determinantCommun);
			
			if (currentIsOne && lastIsNotOne && currentContainsVerb && nextContainsAdverb && !nextIsNotArt) {
				dontRemoveFromListElement(nextList, "Adverbe");
				System.out.println("isAdverbe " + (syntaxe + 1));
			}
		}
	}
	
	private void adjectivIfNextCnjncThenAdj(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 2; syntaxe++) {
			
			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList     = currentSyntaxe.get(syntaxe + 1);
			ArrayList<String> nextX2List     = currentSyntaxe.get(syntaxe + 2);
			
			boolean currentIsNotOne  = currentList.size() > 1;
			boolean containsAdjectif = listEqualsElement(currentList, "Adjectif");
			
			boolean nextIsConjonc    = listEqualsElement(nextList, "Conjonction de coordination");
			boolean nextX2IsAdj      = listEqualsElement(nextX2List, "Adjectif");
			
			boolean nextIsOne        = nextList.size()  == 1;
			boolean nextX2IsOne      = nextX2List.size() == 1;
			

			if (currentIsNotOne && containsAdjectif && nextIsConjonc && nextX2IsAdj && nextIsOne && nextX2IsOne) {
				dontRemoveFromListElement(currentList, "Adjectif");
				System.out.println("adjectivIfNextCnjncThenAdj " + syntaxe);
			}
		}
	}
	
	
	private void isntAnadjectifAfterAverb(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList     = currentSyntaxe.get(syntaxe + 1);
			
			boolean currentIsVerbPresent = listContains(currentList, "Indicatif présent");
			boolean nextIsPP    		 = listContains(nextList, "Participe passé");

			boolean currentIsOne   		 = currentList.size() == 1;
			boolean nextIsNotOne 		 = nextList.size() > 1;

			boolean nextIsAdjective 	 = listEqualsElement(nextList, "Adjectif");
	
			if (currentIsVerbPresent && nextIsPP && currentIsOne && nextIsNotOne && nextIsAdjective) {
				dontRemoveFromListContains(nextList, "verbe#");
				System.out.println("isntAnadjectifAfterAverb " + (syntaxe + 1));
			}
		}
	}
	
	
	private void cannotBeAverbAfterArt(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList     = currentSyntaxe.get(syntaxe + 1);
			
			String word     = currentText.get(syntaxe);

			boolean currentIsOne = currentList.size() == 1;
			boolean nextIsNotOne = nextList.size() > 1;
			boolean nextIsVerbe  = listContains(nextList, "verbe#");
			boolean nextIsNm     = listEqualsElement(nextList, "Nom commun");
			boolean currentIsArt = listEquals(currentList, determinantCommunWithotuAdj);
			
			boolean exceptDemonstratif = listContains(currentList, "djectif démonstratif");
			
			boolean isNotPrepo   = listEqualsElement(currentList, "Préposition");
			
			boolean lengthCondi  = currentIsOne && nextIsNotOne;
			boolean condiSynt    = nextIsVerbe && nextIsNm && currentIsArt;
			
			boolean isContraction = word.contains("contraction=");
			
			if (lengthCondi && condiSynt && !isNotPrepo && !exceptDemonstratif) {
				removeFromListContains(nextList, "verbe#");
				System.out.println("cannotBeAverbAfterArt1 " + (syntaxe + 1));
			}
			else if (isContraction && exceptDemonstratif && nextIsNotOne && nextIsVerbe) {
				dontRemoveFromListContains(nextList, "verbe#");
				System.out.println("cannotBeAverbAfterArt2 " + (syntaxe + 1));
			}
		}
	}
	

	
	
	private void cantBeANomCommunAfterAdjectifAndBeforePreposition(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> lastList    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);

			boolean lastIsOne 		   = lastList.size() == 1;
			
			boolean lastIsAdjectif     = listEqualsElement(lastList, "Adjectif");
			boolean currentIsVerb      = listContains(currentList, "verbe#");
			boolean currentIsAdjectif  = listEqualsElement(currentList, "Adjectif");
			boolean currentIsNomCommun = listEqualsElement(currentList, "Nom commun");

			if (lastIsOne && lastIsAdjectif && currentIsVerb && currentIsAdjectif && currentIsNomCommun) {
				dontRemoveFromListContains(currentList, "verbe#");
				System.out.println("cantBeANomCommunAfterAdjectifAndBeforePreposition " + syntaxe);
			}
		}
	}
	

	private void letterIsPronomPersonnel(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList    = currentSyntaxe.get(syntaxe + 1);
			
			String word = currentText.get(syntaxe);

			boolean isLetter 			  = listEqualsElement(currentList, "Lettre");
			boolean letterIsT 			  = word.equalsIgnoreCase("t");
			boolean nextIsPronomPersonnel = listEqualsElement(nextList, "Pronom personnel");
			boolean nextIsOne             = nextList.size() == 1;
			
			
			if (isLetter && letterIsT && nextIsPronomPersonnel && nextIsOne) {
				currentList.clear();
				currentList.add("Pronom personnel");
				currentText.set(syntaxe, "te");
				System.out.println("letterIsPronomPersonnel " + syntaxe);
			}
		}
	}

	private void deIsPreposition(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> lastList    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList    = currentSyntaxe.get(syntaxe + 1);
			
			String word = currentText.get(syntaxe);

			boolean currentIsNotOne = currentList.size() > 1;
			boolean nextIsOne       = nextList.size() == 1;

			boolean currentIsArtInd      = listEqualsElement(currentList, "Forme d’article indéfini");
			boolean currentIsPreposition = listEqualsElement(currentList, "Préposition");
			boolean wordIsDe 			 = word.equalsIgnoreCase("de");

			boolean nextIsNomPropre      = listEqualsElement(nextList, "Nom propre");
			boolean lastIsAdverbe        = listEqualsElement(lastList, "Adverbe");
			boolean lastIsInfinitif      = listEqualsElement(lastList, "verbe#");
			boolean lastIsAdjectif       = listEqualsElement(lastList, "Adjectif");
			
			
			boolean conditions1 = currentIsNotOne &&  nextIsOne && currentIsArtInd && currentIsPreposition && wordIsDe;
			boolean conditions2 = nextIsNomPropre || lastIsAdverbe || lastIsInfinitif || lastIsAdjectif;

			if (conditions1 && conditions2) {
				dontRemoveFromListContains(currentList, "Préposition");
				System.out.println("deIsPreposition " + syntaxe);
			}
			
			else if(conditions1) {
				dontRemoveFromListContains(currentList, "Préposition");
				System.err.println("deIsPreposition (last condition, cant be adjectiv == de) " + syntaxe);
			}
		}
	}
	
	
	private void deIsArticleDefiniBehindAnAdjectiv(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
	
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList    = currentSyntaxe.get(syntaxe + 1);
			
			String word = currentText.get(syntaxe);

			boolean currentIsNotOne = currentList.size() > 1;
			boolean nextIsOne       = nextList.size() == 1;

			boolean nextIsAdjectif  = listContains(nextList, "djectif");

			
			boolean currentIsArtInd = listEqualsElement(currentList, "Forme d’article indéfini");
			boolean currentIsPreposition = listEqualsElement(currentList, "Préposition");

			boolean wordIsDe = word.equalsIgnoreCase("de");
			
			
			if (currentIsNotOne &&  nextIsOne && nextIsAdjectif && currentIsArtInd && currentIsPreposition && wordIsDe) {
				dontRemoveFromListContains(currentList, "Forme d’article indéfini");
				System.out.println("deIsArticleDefiniBehindAnAdjectiv " + syntaxe);
			}
		}
	}
	
	
	

	

	private void formeAdjOrNomCommun(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> lastList = currentSyntaxe.get(syntaxe);
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList = currentSyntaxe.get(syntaxe);
			
			boolean containsNomCommun = listEqualsElement(currentList, "Nom commun");
			boolean containsFormAject = listEqualsElement(currentList, "Forme d’adjectif");

			boolean lastIsOne         = lastList.size() == 1;
			boolean nextIsOne         = nextList.size() == 1;
			
			if (containsNomCommun && containsFormAject && lastIsOne && nextIsOne) {
				removeFromList(currentList, "Forme d’adjectif");
				System.out.println("formeAdjOrNomCommun " + syntaxe);
			}
		}
	}
	




	
	private void artLaLeLApo(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {


		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			String word = currentText.get(syntaxe);
			
			boolean wordIsLa   = word.equalsIgnoreCase("la");
			boolean wordIsLe   = word.equalsIgnoreCase("le");
			boolean wordIsLApo = word.equalsIgnoreCase("contraction=le");
			boolean isNotOne   = currentList.size() == 1;
			boolean firstWord  = syntaxe == 0;
			
			boolean currentNotOne = currentList.size() > 1;
			
			if ((wordIsLa || wordIsLe || wordIsLApo) && currentNotOne) {
				removeFromList(currentList, "Nom commun");
				System.out.println("artLaLeLApo (remove nm) " + syntaxe);
			}
			
			if (wordIsLa && currentNotOne) {
				removeFromList(currentList, "Adverbe");
				System.out.println("artLaLeLApo (remove adverbe) " + syntaxe);
			}
			
			
			if ((wordIsLa || wordIsLe) && firstWord && !isNotOne) {
				removeFromList(currentList, "Pronom personnel");
				System.out.println("artLaLeLApo (remove prnm personel) " + syntaxe);
			}
		}
	}
	
	
	private void isPronomOrArt(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastList    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> nextList    = currentSyntaxe.get(syntaxe + 1);

			boolean lastContainsPronomPerso  = listEqualsElement(lastList, "Pronom personnel");
			boolean nextIsVerb               = listContains(nextList, "verbe#");
			boolean currentContainsArtDef    = listContains(currentList, "rticle défini");
			boolean currentContainsPrnmPerso = listEqualsElement(currentList, "Pronom personnel");
			 
			boolean nextContainsPrnmPerso    = listEqualsElement(nextList, "Pronom personnel");

			boolean notOne  = currentList.size() == 1;
			
			if (!lastContainsPronomPerso && currentContainsArtDef && currentContainsPrnmPerso && !nextContainsPrnmPerso && notOne) {
				removeFromList(currentList, "Pronom personnel");
				System.out.println("isPronomOrArt (pronm perso)1 " + syntaxe);
			}

			else if (lastContainsPronomPerso && nextIsVerb && currentContainsArtDef && currentContainsPrnmPerso) {
				removeFromListContains(currentList, "rticle défini");
				System.out.println("isPronomOrArt (art def)2 " + syntaxe);
			}
			
			else if ((lastContainsPronomPerso || nextContainsPrnmPerso) && currentContainsArtDef && currentContainsPrnmPerso) {
				dontRemoveFromListElement(currentList, "Pronom personnel");
				System.out.println("isPronomOrArt (pronm perso)3 " + syntaxe);
			}
			
		}
	}
	

	
	
	private void isVerbeIfSContraction(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList    = currentSyntaxe.get(syntaxe + 1);
			
			boolean containsContraction = listContains(currentList, "contraction=se");
			boolean nextIsVerbe         = listContains(nextList, "verbe#");

			if (containsContraction && nextIsVerbe) {
				dontRemoveFromListContains(nextList, "verbe#");
				System.out.println("isVerbeIfSContraction " + (syntaxe + 1));
			}
			
		}
	}
	
	
	

	private void comboArticleAdjNomCommun(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 2; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList    = currentSyntaxe.get(syntaxe + 1);
			ArrayList<String> nextListX2  = currentSyntaxe.get(syntaxe + 2);
			
			boolean currentIsNotOne = currentList.size() > 1;
			boolean nextisOne       = nextList.size()   == 1;
			boolean nextX2IsOne     = nextListX2.size() == 1;
			
			boolean nextIsAdjectif       = listEqualsElement(nextList, "Adjectif");
			boolean nextIsFormeAdjectif  = listEqualsElement(nextList, "Forme d'adjectif");
			boolean nextIsNomCommun      = listEqualsElement(nextListX2, "Nom commun");

			boolean currentContainsArtInd  = listEqualsElement(currentList, "Article indéfini");
			boolean currentContainsNum     = listEqualsElement(currentList, "Adjectif numéral");
			boolean currentContainsPrnmInd = listEqualsElement(currentList, "Pronom indéfini");

			boolean conditionSize         = currentIsNotOne && nextisOne && nextX2IsOne;
			boolean conditionNextContains = ((nextIsAdjectif || nextIsFormeAdjectif) && nextIsNomCommun) || nextIsNomCommun;
			boolean currentContains       = currentContainsArtInd && currentContainsNum && currentContainsPrnmInd;
			
			if (conditionSize && conditionNextContains && currentContains) {
				String[] elements = {"Article indéfini", "Adjectif numéral"};
				dontRemoveFromThisList(currentList, elements);
				System.out.println("comboArticleAdjNomCommun " + syntaxe);
			}
		}
	}


	private void caseMais(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);

			String word = currentText.get(syntaxe);

			boolean isMais = word.equalsIgnoreCase("mais");
			boolean isNotFirstWord = syntaxe > 0;
			boolean thereIsBeforeName = false;

			if (isNotFirstWord) {
				ArrayList<String> lastList    = currentSyntaxe.get(syntaxe - 1);
				thereIsBeforeName = listEquals(lastList, this.determinantCommun);
			}

			if (isMais && !thereIsBeforeName) {
				currentList.clear(); currentList.add("Conjonction de coordination");
				System.out.println("caseMais " + syntaxe);
			}
		}
	}

	
	private void nomCommunOrVerbNextIsAdj(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 2; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String>  lastx2      = currentSyntaxe.get(syntaxe - 2);
			ArrayList<String>  last        = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String>  currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String>  nextList    = currentSyntaxe.get(syntaxe + 1);

			boolean isX2IsNc    = listEqualsElement(lastx2, "Nom commun");
			boolean lastIsPrepo = listEqualsElement(last, "Préposition");
			boolean conditionLength = isX2IsNc && lastIsPrepo;
			
			boolean lastx2IsOne = lastx2.size() == 1;
			boolean lastIsOne   = last.size() == 1;
	
			boolean conditionPrepo = isX2IsNc && lastIsPrepo;
			
			
			boolean nextIsOne           = nextList.size() == 1;
			
			boolean currentContainsNM   = listEqualsElement(currentList, "Nom commun");
			boolean currentContainsVerb = listContains(currentList, "verbe#");
			
			boolean nextIsAdjective     = listEqualsElement(nextList, "Adjectif");
			boolean nextIsAdverbe       = listEqualsElement(nextList, "Adverbe");

			if (nextIsOne && nextIsAdjective && currentContainsNM && currentContainsVerb) {
				dontRemoveFromListContains(currentList, "Nom commun");
				System.out.println("nomCommunOrVerbNextIsAdj " + syntaxe);
			}

			else if (nextIsOne && nextIsAdverbe && currentContainsNM && currentContainsVerb && conditionLength && !conditionPrepo) {
				dontRemoveFromListContains(currentList, "verbe#");
				System.out.println("nomCommunOrVerbNextIsAdj " + syntaxe);
			}
			
			else if (nextIsOne && nextIsAdverbe && currentContainsNM && currentContainsVerb && conditionLength && conditionPrepo) {
				dontRemoveFromListContains(currentList, "Nom commun");
				System.out.println("nomCommunOrVerbNextIsAdj " + syntaxe);
			}
		}
	}


	private void replaceFormAdjByAdj(ArrayList<ArrayList<String>> currentSyntaxe) {


		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String>  currentList = currentSyntaxe.get(syntaxe);

			boolean currentIsOne   = currentList.size() == 1;
			boolean containsFrmAdj = currentList.get(0).equalsIgnoreCase("Forme d’adjectif");

			if (currentIsOne && containsFrmAdj) {
				currentList.clear();
				currentList.add("Adjectif");
				System.out.println("replaceFormAdjByAdj " + syntaxe);
			}
		}
	}
	

	private void deleteSurWeb(ArrayList<ArrayList<String>> currentSyntaxe) {
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String>  currentList = currentSyntaxe.get(syntaxe);
			removeFromListContains(currentList, "sur le web");
		}
	}
	
	
	private void deleteDéfinitionsCorespondantàVotreRecherche(ArrayList<ArrayList<String>> currentSyntaxe) {

		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size(); syntaxe++) {
			ArrayList<String>  currentList = currentSyntaxe.get(syntaxe);
			removeFromListContains(currentList, "Définitions corespondante à votre recherche");
			removeFromListContains(currentList, "Aucun mot trouvé");
			removeFromListContains(currentList, "contraction=");
			
		}
	}
	
	

	private void isConjonctionOrPronomCANBEfromLastSentence(List<ArrayList<ArrayList<String>>> sentenceSyntaxe,
															ArrayList<ArrayList<String>>	   currentSyntaxe, 
															ArrayList<String> Text, int getSentence, boolean thereIsTirate,
															int indexTirate, List<ArrayList<String>> originalText) {

		ArrayList<ArrayList<String>> lastSyntaxe = sentenceSyntaxe.get(getSentence - 1);

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String>  currentList      = currentSyntaxe.get(syntaxe);

			String firstLetter = Character.toString(Text.get(0).charAt(0));
			boolean isANewSentence     = firstLetter.equals(firstLetter.toUpperCase());

			boolean currentIsNotOne    = currentList.size() > 1;
			boolean currentIsPronomRel = listEqualsElement(currentList, "Pronom relatif");
			boolean currentIsConjonc   = listEqualsElement(currentList, "Conjonction");

			boolean lastIsNomCommun    = whileNotIndexEquals  (currentSyntaxe, "Nom commun", 0, syntaxe);
			boolean lastIsVerb         = whileNotIndexContains(currentSyntaxe, "verbe#",     0, syntaxe);

			boolean casePronomOrConjonction = currentIsNotOne && currentIsPronomRel && currentIsConjonc;
			boolean searchBefore            = !isANewSentence && !lastIsNomCommun && !lastIsVerb;
			boolean searchCurrent  			= lastIsNomCommun || lastIsVerb;

		    if		(casePronomOrConjonction && searchCurrent) {System.err.println("recherche pronom ou conjonction CUURENT a faire");}
		    else if (casePronomOrConjonction && searchBefore)  {

		    	Integer[] indexs = searchBeforeConjPron(sentenceSyntaxe, lastSyntaxe, Text, casePronomOrConjonction, getSentence, originalText);

				int NM = indexs[0];
				int NP = indexs[1];
				int Vb = indexs[2];

				boolean isConjonction = (Vb > -1 && Vb < NM && Vb < NP) || (Vb > -1 && NM == -1 && NP == -1);
				boolean isPronom      = ((NM > -1 && NM < Vb ) || (NM > -1 && Vb == -1)) || ((NP > -1 && NP < Vb) ||(NP > -1 && Vb == -1));

				if (isConjonction) {dontRemoveFromListElement(currentList, "Conjonction");}
				else if (isPronom) {dontRemoveFromListElement(currentList, "Pronom relatif");}
				System.out.println("isConjonctionOrPronomCANBEfromLastSentence: " + syntaxe);
		    }
		}
	}

	private Integer[] searchBeforeConjPron(List<ArrayList<ArrayList<String>>> sentenceSyntaxe, 
									  ArrayList<ArrayList<String>> lastSyntaxe, 
									  ArrayList<String> text, boolean caseProCon, 
									  int getSentence, List<ArrayList<String>> sentenceText) {

		getSentence = getSentence - 1;

		ArrayList<ArrayList<ArrayList<String>>> first = firstTreatment(sentenceText, sentenceSyntaxe, getSentence);
		ArrayList<ArrayList<String>> currentSyntaxe   = first.get(0);
		ArrayList<String> 			 currentText      = first.get(1).get(0);
		int 	indexTirate   = Integer.parseInt(first.get(2).get(0).get(0));
		boolean thereIsTirate = Boolean.parseBoolean(first.get(3).get(0).get(0));

		treatSyntaxe(sentenceSyntaxe, currentSyntaxe, currentText, thereIsTirate, indexTirate, getSentence, sentenceText);

		int NM = whileNotIndexEqualsRecupIndex(lastSyntaxe, "Nom commun", 0, lastSyntaxe.size());
		int NP = whileNotIndexEqualsRecupIndex(lastSyntaxe, "Nom propre", 0, lastSyntaxe.size());
		int Vb = whileNotIndexContainsRecupIndex(lastSyntaxe, "verbe#",   0, lastSyntaxe.size());

		Integer[] recuperateIndex = {NM, NP, Vb};

		return recuperateIndex;
	}



	
	private void removeAndKeepPonctuation(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String>  currentList      = currentSyntaxe.get(syntaxe);
			ArrayList<Integer> indexPonctuation = new ArrayList<Integer>();

			removeTableauAndRecupIndex(currentList, this.Ponctuation, "equals", indexPonctuation, syntaxe);
			for(int ponctIndexed: indexPonctuation) {currentText.remove(currentText.get(ponctIndexed));}
			for(int ponctIndexed: indexPonctuation) {currentSyntaxe.remove(currentList);}

			boolean removedPonctu = indexPonctuation.size() > 0;
			if(removedPonctu) {System.out.println("removeAndKeepPonctuation: " + indexPonctuation);}
		}
	}
	
	
	
	private void cannotBeAdjectifAfterPrepo(ArrayList<ArrayList<String>> currentSyntaxe) {

		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastList     = currentSyntaxe.get(syntaxe - 1);

			boolean currentIsNotOne 		= currentList.size() > 1;
			boolean lastIsOne       		= lastList.size() == 1;
			boolean lastIsPrepos    		= listEqualsElement(lastList, "Préposition");
			boolean currentContainsAdjectif = listEqualsElement(currentList, "Adjectif");

			if (currentIsNotOne && lastIsOne && lastIsPrepos && currentContainsAdjectif) {
				removeFromList(currentList, "Adjectif");
				System.out.println("cannotBeAdjectifAfterPrepo " + syntaxe);
			}
		}
	}
	

	
	private void caseDeIsNotNomCommun(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			String            word         = currentText.get(syntaxe);
			
			boolean wordIsDe                 = word.equalsIgnoreCase("contraction=de");
			boolean currentContainsNomCommun = listEqualsElement(currentList, "Nom commun");
			boolean currentIsNotOne          = currentList.size() > 1;

			if (wordIsDe && currentContainsNomCommun && currentIsNotOne) {
				removeFromList(currentList, "Nom commun");
				System.out.println("caseDeIsNotNomCommun " + syntaxe);
			}
		}
	}
	
	
	private void isOrNotInterjection(ArrayList<ArrayList<String>> currentSyntaxe) {
		

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastSyntaxe = currentSyntaxe.get(currentSyntaxe.size() - 1);

			boolean isNotFirstWord   = syntaxe > 0;
			boolean isFirstWord  	 = syntaxe == 0;
			boolean isExclamativeEnd = lastSyntaxe.get(0).equalsIgnoreCase("Point exclamation");
			boolean isInterjection   = listEqualsElement(currentList, "Interjection");
			boolean isNotOne         = currentList.size() > 1;
			
			
			if (isNotFirstWord && !isExclamativeEnd && isInterjection && isNotOne) {
				removeFromList(currentList, "Interjection");
				System.out.println("isOrNotInterjection (del interjection) " + syntaxe);
			}

			
			if (isFirstWord && isExclamativeEnd && isInterjection) {
				dontRemoveFromListElement(currentList, "Interjection");
				System.out.println("isOrNotInterjection (keep interjection) " + syntaxe);
			}

		}
		
	}
	
	
	
	private void cantBeVerbDApostroph(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {


			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastList     = currentSyntaxe.get(syntaxe - 1);

			String lastWord            = currentText.get(syntaxe - 1);

			boolean currentIsNotOne    = currentList.size() > 1;
			boolean containsVerb       = listContains(currentList, "verbe#");
			boolean lastOsDApostroph   = lastWord.equalsIgnoreCase("contraction=de");
			boolean verbIsNotInfinitif = listContainsRecuperate(currentList, "verbe#").split("#").length == 1;

			if (currentIsNotOne && containsVerb && lastOsDApostroph && !verbIsNotInfinitif) {
				removeFromListContains(currentList, "verbe#");
				removeFromList(currentList, "Verbe");
				System.out.println("cantBeVerbDApostroph " + syntaxe);
			}
		}
	}


	private void partitifOrPrepoIsTheSameForNow(ArrayList<ArrayList<String>> currentSyntaxe) {

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> next         = currentSyntaxe.get(syntaxe + 1);
			
			boolean currentIsNotOne    = currentList.size() > 1;
			boolean currentIsPrepo     = listEqualsElement(currentList, "Préposition");
			boolean currentIsArtPartit = listEqualsElement(currentList, "Article partitif");
			boolean nextIsOne          = next.size() == 1;
			
			if (currentIsNotOne && currentIsPrepo && currentIsArtPartit && nextIsOne) {
				removeFromList(currentList, "Article partitif");
				System.out.println("partitifOrPrepoIsTheSameForNow (partitif remove): " + syntaxe);
			}
		}
	}
	

	private void pronomRelatifOrIndefini(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList     = currentSyntaxe.get(syntaxe + 1);


			String[] pronom  = {"je", "il", "j"};

			
			boolean currentIsNotOne    = currentList.size() > 1;
			boolean currentIsPronomInd = listEqualsElement(currentList, "Pronom indéfini");
			boolean currentIsPronomRel = listEqualsElement(currentList, "Pronom relatif");
			boolean nextIsOne          = nextList.size() == 1;

			String[]  verbTreat   = nextList.get(0).split("#");
			String    pronomInter = "";
			if (verbTreat.length > 1) {

				String formApo = "";
				boolean isApostrophe = verbTreat[1].contains("'") || verbTreat[1].contains("’");;
				if (verbTreat[1].contains("'")) {formApo = "'";} 
				if (verbTreat[1].contains("’")) {formApo = "’";}

				if      (isApostrophe) {pronomInter  = verbTreat[1].split(formApo)[0];}
				else if (!isApostrophe){pronomInter  = verbTreat[1].split(" ")[0];}
			}

			boolean nextIsVerb         = listContains(nextList, "verbe#");
			boolean nextIsDefineByVerb = thisListEqualWord(pronom, pronomInter);

			if (currentIsNotOne && currentIsPronomInd && currentIsPronomRel && nextIsOne && nextIsVerb && nextIsDefineByVerb) {
				dontRemoveFromListElement(currentList, "Pronom relatif");
				System.out.println("pronomRelatifOrIndefini " + syntaxe);
			}
		}
	}
	

	
	private void differenciatePronomAdjdemonstratif(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList     = currentSyntaxe.get(syntaxe + 1);
	
			boolean currentIsNotOne = currentList.size() > 1;
			boolean isPronomDemon   = listEqualsElement(currentList, "Pronom démonstratif");
			boolean isAdjectifDemon = listEqualsElement(currentList, "Adjectif démonstratif");
			
			boolean nextIsNomCommun = listEqualsElement(nextList, "Nom commun");
			
			if (currentIsNotOne && isPronomDemon && isAdjectifDemon && !nextIsNomCommun) {
				removeFromList(currentList, "Adjectif démonstratif");
				System.out.println("differenciatePronomAdjdemonstratif " + syntaxe);
			}	
		}
	}
	
	
	
	
	private void isVerbeOrNomCommunPronomAfter(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList     = currentSyntaxe.get(syntaxe + 1);

			String[] pronomPerso = {"je", "tu", "il", "elle", "on", "ils"};
			
			boolean currentIsNotOne    = currentList.size() > 1;
			boolean nextIsOne          = nextList.size() == 1;

			boolean nextIsPronomPerso  = listEquals(nextList, pronomPerso);
			boolean currentIsNomCommun = listEqualsElement(currentList, "Nom commun");
			boolean currentIsVerbe     = listContains(currentList, "verbe#");

			if (currentIsNotOne && currentIsNomCommun && currentIsVerbe && nextIsOne && nextIsPronomPerso) {
				dontRemoveFromListContains(currentList, "verbe#");
				System.out.println("isVerbeOrNomCommunPronomAfter " + syntaxe);
			}
		}
	}
	
	
	private void pronomPersonnelOrArticle(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> lastList     = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList     = currentSyntaxe.get(syntaxe + 1);
			
			
			boolean currentIsNotOne  = currentList.size() > 1;
			boolean lastIsOne        = lastList.size() == 1;
			boolean nextIsOne        = nextList.size() == 1;
			
			boolean lastIsPronom     = listEqualsElement(lastList, "Pronom personnel");
			boolean lastIsVerbe      = listContains(lastList, "verbe#");
			boolean nextIsVerbe      = listContains(nextList, "verbe#");
			boolean nextIsNom        = listEqualsElement(nextList, "Nom commun");
			
			
			
			boolean currentIsPronom  = listEqualsElement(currentList, "Pronom personnel");
			boolean currentIsArtInd  = listContains(currentList, "rticle indéfini");

			boolean currentIsArtDef  = listContains(currentList, "rticle défini");

			boolean currentContaintic = listContains(currentList, "rticle");
			
			if (currentIsNotOne && lastIsOne && (lastIsPronom || lastIsVerbe) && nextIsVerbe && currentIsPronom && currentIsArtInd) {
				dontRemoveFromListElement(currentList, "Pronom personnel");
				System.out.println("pronomPersonnelOrArticle (keep pronom perso) " + syntaxe);
			}
	
			else if (currentIsNotOne && lastIsOne && currentIsPronom && currentIsArtInd && nextIsNom && nextIsOne && currentContaintic) {
				dontRemoveFromListContains(currentList, "rticle");
				System.out.println("pronomPersonnelOrArticle (keep article1) " + syntaxe);
			}
			
			else if (currentIsNotOne && lastIsOne && currentIsPronom && currentIsArtDef && nextIsNom && nextIsOne && currentContaintic) {
				dontRemoveFromListContains(currentList, "rticle");
				System.out.println("pronomPersonnelOrArticle (keep article2) " + syntaxe);
			}
		}
	}
	
	
	
	
	private void caseDe(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 2; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {


			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastList     = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> lastLastList = currentSyntaxe.get(syntaxe - 2);

			boolean currentIsNotOne = currentList.size() > 1;
			boolean lastIsOne       = lastList.size() == 1;
			boolean lastLastIsOne   = lastLastList.size() == 1;
			
			boolean lastLastIsArt         = listContains(lastLastList, "rticle");
			boolean lastLastIsdef         = listContains(lastLastList, "défini");
			boolean lastIsNomCommun       = listEqualsElement(lastList, "Nom commun");
			boolean currentContainsPrepo  = listEqualsElement(currentList, "Préposition");
			
			boolean currentContainsVrb    = listContains(currentList, "verbe#");
			
			if (currentIsNotOne && lastIsOne && lastLastIsOne && lastLastIsArt &&
				lastLastIsdef && lastIsNomCommun && currentContainsPrepo && !currentContainsVrb) {
				dontRemoveFromListElement(currentList, "Préposition");
				System.out.println("caseDe préposition " + syntaxe);
			}
			else if (currentIsNotOne && lastIsOne && lastLastIsOne && lastLastIsArt &&
					lastLastIsdef && lastIsNomCommun && currentContainsPrepo && currentContainsVrb) {
				dontRemoveFromListContains(currentList, "verbe#");
				System.out.println("caseDe verbe " + syntaxe);
			}
				
		}
	}
	
	
	private void cannotBeAnAdjectif(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastList    = currentSyntaxe.get(syntaxe - 1);

			boolean currentIsNotOne    = currentList.size() > 1;
			boolean containsAdjectif   = listEqualsElement(currentList, "Adjectif") || listEqualsElement(currentList, "Forme d'adjectif");
			boolean lastContainsBefore = thisListEqualList(lastList, this.beforeAdjectif);
			
			boolean containsVerb       = listContains(lastList, "verbe#");

			if (currentIsNotOne && containsAdjectif && !lastContainsBefore && !containsVerb) {
				removeFromList(currentList, "Adjectif");
				removeFromList(currentList, "Forme d'adjectif");
				System.out.println("cannotBeAnAdjectif (match before): " + syntaxe);
			}
			

			ArrayList<String> nextList    = currentSyntaxe.get(syntaxe + 1);

			boolean lastIsOne          = lastList.size() == 1;
			boolean nextIsNomcommun    = listEqualsElement(nextList, "Nom commun");
			boolean nextIsOne          = nextList.size() == 1;
			boolean isNotLastWord      = syntaxe <  currentSyntaxe.size() - 2;
			boolean lastIsNomCommun    = listContains(lastList, "Nom commun");
			
			if (currentIsNotOne && nextIsOne && containsAdjectif && 
					!nextIsNomcommun && isNotLastWord && lastIsOne && !lastIsNomCommun && !containsVerb) {

				removeFromList(currentList, "Adjectif");
				removeFromList(currentList, "Forme d'adjectif");
				System.out.println("cannotBeAnAdjectif (match after): " + syntaxe);
			}
		}
	}
	
	
	private void cantBeVerbIfParticipePassé(ArrayList<ArrayList<String>> currentSyntaxe) {
		

		for (int syntaxe = 2; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastList    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> lastListX2  = currentSyntaxe.get(syntaxe - 2);
			
			boolean currentIsNotOne = currentList.size() > 1;
			boolean currentIsVerb   = listContains(currentList, "verbe#");

			boolean currentIsParticipePassé = listContains(currentList,     "#Participe passé");
			boolean lastIsntVerbe           = listContains(lastList,        "verbe#")     ||
											  listContains(lastList,        "Adverbe")    ||
											  listContains(lastListX2,      "verbe#")     ||
											  listEqualsElement(lastList,   "Nom commun") ||
											  listEqualsElement(lastListX2, "Nom commun");

			if (currentIsNotOne && currentIsVerb && currentIsParticipePassé && !lastIsntVerbe) {
				removeFromListContains(currentList, "verbe#");
				removeFromList(currentList, "Verbe");
				System.out.println("cantBeVerbIfParticipePassé (verbe remove): " + syntaxe);
			}
		}
	}
	
	private void participePastDelete(ArrayList<ArrayList<String>> currentSyntaxe) {
		

		
		for (int syntaxe = 2; syntaxe < currentSyntaxe.size(); syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastList    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> lastListX2  = currentSyntaxe.get(syntaxe - 2);
			
			String[] verb = listContainsRecuperate(currentList, "verbe#").split("[#]");
			boolean isntInfinitif  = verb.length > 1;
			boolean isParticipPast = false;
			boolean currentIsNotOn = currentList.size() > 1;
			
			boolean isNoNcBefore    = listEqualsElement(lastList, "Nom commun");
			boolean isNoNCbBeforeX2 = listContains(lastListX2, "Nom commun");
			
			boolean isNoverbBefore  = listContains(lastList, "verbe#");
			
			boolean isNoAdv         = listContains(lastList, "Adverbe");
			boolean isNoCnj         = listContains(lastList, "Conjonction de coordination");
			boolean isNoVerbX2      = listContains(lastListX2, "verbe#");
			
	
			boolean conditionVerb   = !(isNoverbBefore || ((isNoAdv || isNoCnj) && isNoVerbX2) ||
									   (isNoNcBefore || isNoNCbBeforeX2) || isNoAdv);

			if (isntInfinitif) {isParticipPast =  verb[2].contains("Participe passé");}

			if (currentIsNotOn && isntInfinitif && isParticipPast && conditionVerb) {
				removeFromListContains(currentList, "verbe#");
				removeFromList(currentList, "Verbe");
				System.out.println("participePastDelete1 " + syntaxe);
			}
			else if (currentIsNotOn && isntInfinitif && isParticipPast && !conditionVerb) {
				removeFromList(currentList, "Nom commun");
				System.out.println("participePastDelete2 " + syntaxe);
			}
		}
	}
	
	private void pronomVerbCaseTirate(ArrayList<ArrayList<String>> currentSyntaxe, int indexTirate) {

		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList    = currentSyntaxe.get(syntaxe + 1);


			boolean isLengthNotOne        = currentList.size() > 1;
			boolean nextIsNotOne          = nextList.size() > 1;

			boolean currentContainsVerb   = listContains(currentList,   "verbe#");
			boolean NextcontainsPronomDem = listContains(nextList, "Pronom");;

			boolean tirateWasBeetween     = indexTirate == syntaxe + 1;

			if (isLengthNotOne && nextIsNotOne && currentContainsVerb && NextcontainsPronomDem && tirateWasBeetween) {
				dontRemoveFromListContains(currentList, "verbe#");
				dontRemoveFromListContains(nextList, "Pronom");
				System.out.println("pronomVerbCaseTirate: " + syntaxe + " & " + (syntaxe + 1));
			}
			
			
			
			
		}
	}
	
	
	private void adjectifCaseTirateIfVerbeOrAdjectif(ArrayList<ArrayList<String>> currentSyntaxe, boolean containsTirate) {
		
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 2; syntaxe++) {

			ArrayList<String> currentList   = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList      = currentSyntaxe.get(syntaxe + 1);
			ArrayList<String> nextNextList  = currentSyntaxe.get(syntaxe + 2);

			
			boolean currentIsNotOne  = currentList.size() > 1;
			boolean containsVerbe    = listContains(currentList, "verbe#");
			boolean containsAdjectif = listEqualsElement(currentList, "Adjectif");
			boolean nextIsVerbe      = listContains(nextList, "verbe#");
			boolean nextNextIsPronom = listEqualsElement(nextNextList, "Pronom personnel");
			boolean nextIsOne        = nextList.size() == 1;
			boolean nextNextIsOne    = nextNextList.size() == 1;
			
			if (currentIsNotOne && containsVerbe && containsAdjectif && nextIsVerbe && nextNextIsPronom && 
			    nextIsOne && nextNextIsOne && containsTirate) {
				dontRemoveFromListElement(currentList, "Adjectif");
				System.out.println("adjectifCaseTirateIfVerbeOrAdjectif (keep adj) " + syntaxe);
			}
			
		}
		
	}
	
	
	private void verbeTiratePronom(ArrayList<ArrayList<String>> currentSyntaxe, boolean containsTirate) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList    = currentSyntaxe.get(syntaxe + 1);
			
			boolean currentIsNotOne      = currentList.size() > 1; 
			boolean currentContainsVerbe = listContains(currentList, "verbe#");
			boolean nextIsPronom         = listEqualsElement(nextList, "Pronom personnel");
			boolean nextIsOne            = nextList.size() == 1;
			
			
			
			if (currentIsNotOne && currentContainsVerbe && nextIsPronom && nextIsOne && containsTirate) {
				dontRemoveFromListContains(currentList, "verbe#");
				System.out.println("verbeTiratePronom: " + syntaxe);
			}
		}
		
		
	}
	
	
	private void queCase(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastList    = currentSyntaxe.get(syntaxe - 1);
			
			String word = currentText.get(syntaxe);
			
			boolean isQue          = word.equalsIgnoreCase("que");
			
			boolean lastIsOne      = lastList.size() == 1;
			boolean isLengthNotOne = currentList.size() > 1;

			boolean lastIsNom      = listEqualsElement(lastList, "Nom commun");
			boolean lastIsPronom   = listContains(lastList, "Adjectif");
			
			boolean lastIsVerbe    = listContains(lastList, "verbe#");
			
			boolean containsPronom = listContains(currentList, "Pronom");
			boolean containsCnjnc = listContains(currentList, "Conjonction");
			
			
			if (isQue && isLengthNotOne && lastIsOne && (lastIsNom || lastIsPronom) && containsPronom) {
				dontRemoveFromListContains(currentList, "Pronom");
				System.out.println("queCase: " + syntaxe);
			}
			
			else if (isQue && isLengthNotOne && lastIsOne && lastIsVerbe && containsCnjnc) {
				dontRemoveFromListElement(currentList, "Conjonction");
				System.out.println("queCase: " + syntaxe);
			}
			
			
		}
	}

	private void pronomOrAdjectifIndefini(ArrayList<ArrayList<String>> currentSyntaxe) {



		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> lastList    = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList    = currentSyntaxe.get(syntaxe + 1);

			boolean lastIsNomCommun  = listEqualsElement(lastList, "Nom commun");
			boolean lastIsOne        = lastList.size() == 1;
			
			boolean lengthisNotOne   = currentList.size() > 1;
			boolean nextIsOne        = nextList.size() == 1;

			boolean containsAdjectif = listContains(currentList,   "djectif");
			boolean nextIsNomCommun  = listEqualsElement(nextList, "Nom commun");

			boolean containsPronom   = listContains(currentList, "ronom");
			boolean nextIsVerbe      = listContains(nextList,    "verbe#");
			boolean containsRticl    = listContains(currentList,  "djectif#");
		
			
			boolean containsArt      = thisListContains(currentList, this.determinantCommun);


			
			if (lastIsNomCommun && lastIsOne && containsAdjectif && containsPronom && lengthisNotOne) {
				dontRemoveFromListContains(currentList, "djectif");
				System.out.println("pronomOrAdjectifIndefini (djectif or pronom)1: " + syntaxe);
			}

			else if (lengthisNotOne && containsPronom && nextIsOne && nextIsNomCommun && !lastIsNomCommun && containsRticl) {

				dontRemoveFromListContains(currentList, "rticle");
				System.out.println("pronomOrAdjectifIndefini (article)2: " + syntaxe);
			}

			else if (lengthisNotOne && containsAdjectif && nextIsOne && nextIsNomCommun && lastIsNomCommun && containsArt) {
				removeFromList(currentList, "Adjectif");
				removeFromList(currentList, "Forme d'adjectif");
				System.out.println("pronomOrAdjectifIndefini (suppr djectif mais pas pronom)3: " + syntaxe);
			}
			
			
			else if (lengthisNotOne && containsPronom && nextIsOne && nextIsVerbe) {
				dontRemoveFromListContains(currentList, "ronom");
				System.out.println("pronomOrAdjectifIndefini (pronom)4: " + syntaxe);
			}
				
		}
	}
	


	
	

	private void isVerbeOrNotAfterPreposition(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastList    = currentSyntaxe.get(syntaxe - 1);

			boolean lastIsOne            = lastList.size() == 1;
			boolean lastIsPreposition    = lastList.get(0).equalsIgnoreCase("Préposition");
			boolean currentIsNotOne      = currentList.size() > 1;
			boolean currentContainsVerbe = listContains(currentList, "verbe#");

			String[] currentIsInfinitif  = listContainsRecuperate(currentList, "verbe#").split("#");
			boolean isInfinitif          = currentIsInfinitif.length == 1;
			boolean gerondifPP           = listContains(currentList, "érondif") || listContains(currentList, "ticipe présent");
			
			
			if (lastIsOne && lastIsPreposition && currentIsNotOne && currentContainsVerbe && !isInfinitif && !gerondifPP) {
				removeFromListContains(currentList, "verbe#");
				removeFromList(currentList, "Verbe");
				System.out.println("isVerbeOrNotAfterPreposition (remove verb)" + syntaxe);
			}
		}
	}
	
	
	
	private void isVerbIfParticule(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		for (int syntaxe = 1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {


			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastList    = currentSyntaxe.get(syntaxe - 1);


			String[] partInterese      = listContainsRecuperate(currentList, "verbe#").split("[# ]");
			boolean  pronomVerbe       = partInterese.length > 1;

			boolean isPronomCondi  = false;
			boolean isVoyelleCondi = false;

			boolean isLengthNotOne  = currentList.size() > 1;
			boolean lastIsOne       = lastList.size() == 1;
			boolean lastIsParticule = listEqualsElement(lastList, "Particule");

			boolean containsVerb    = listContains(currentList, "verbe#");
			
			if (pronomVerbe && isLengthNotOne && lastIsOne && lastIsParticule) {

				String pronom  = partInterese[1];
				String voyelle = Character.toString(partInterese[2].charAt(0));

				isPronomCondi  = thisListEqualWord(this.pronomPersonnel, pronom);
				isVoyelleCondi = thisListEqualWord(this.voyelle, voyelle);

				if (isPronomCondi && isVoyelleCondi && containsVerb) {
					dontRemoveFromListContains(currentList, "verbe#");
					System.out.println("isVerbIfParticule: " + syntaxe);
				}
			}
		}
	}
	
	
	private void isParticule(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			String word = currentText.get(syntaxe);
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);

			boolean isParticule       = listContains(currentList, "Particule");
			boolean isNegation        = listContains(currentList, "contraction=ne");

			if (isParticule && isNegation) {dontRemoveFromListElement(currentList, "Particule");}
			if (isParticule && isNegation) {System.out.println("particule: " + syntaxe);}
		}
	}
	
	
	
	private void isPrepositionOrPronomOrAdverbe(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList    = currentSyntaxe.get(syntaxe + 1);

			boolean nextIsAdjectif    = listEqualsElement(nextList, "Adjectif");
			boolean nexIsNomCommun    = listEqualsElement(nextList, "Nom commun");
			boolean nextIsNomPropre   = listEqualsElement(nextList, "Nom propre");
			
			boolean nextIsVerb        = listContains(nextList, "verbe#");

			boolean currentNotOne     = currentList.size() == 1;
			boolean nextIsOne         = nextList.size()    == 1;

			
			boolean currentContainsPreposition = listContains(currentList, "Préposition");
			boolean currentContainsPronom      = listContains(currentList, "Pronom");

			if ((currentNotOne && nextIsOne && currentContainsPreposition && currentContainsPronom) 
				&& (nextIsAdjectif || nexIsNomCommun || nextIsNomPropre)) {
				removeFromListContains(currentList, "Pronom");
				System.out.println("isPrepositionOrPronomOrAdverbe(pronom): " + syntaxe);
			}

			else if((currentNotOne && nextIsOne && currentContainsPreposition && currentContainsPronom) && nextIsVerb) {
				removeFromListContains(currentList, "Préposition");
				System.out.println("isPrepositionOrPronomOrAdverbe(prépo): " + syntaxe);
			}
		}
	}
	
	
	private void caseOu(ArrayList<String> currentText, ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			String word = currentText.get(syntaxe);
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			
			boolean isOuAccent        = word.equalsIgnoreCase("où");
			boolean isOuWithoutAccent = word.equalsIgnoreCase("ou");
			boolean isNotLengthOne    = currentList.size() > 1;
			
			boolean containsCnj       = listEqualsElement(currentList, "Conjonction de coordination");
			boolean containsAdv       = listEqualsElement(currentList, "Adverbe relatif");
			
			
			if     (isOuWithoutAccent && isNotLengthOne && containsCnj) {
				dontRemoveFromListElement(currentList, "Conjonction de coordination");
			}
			else if(isOuAccent && isNotLengthOne && containsAdv)        {
				dontRemoveFromListElement(currentList, "Adverbe relatif");
			}
			
			if     (isOuWithoutAccent && isNotLengthOne) {System.out.println("caseOu " + syntaxe);}
			else if(isOuAccent && isNotLengthOne)        {System.out.println("caseOu " + syntaxe);}
		}
	}

	private void isArticleOrPronom(ArrayList<String> currentText, ArrayList<ArrayList<String>> currentSyntaxe) {


		for (int syntaxe = 0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList    = currentSyntaxe.get(syntaxe + 1);

			String word                   = currentText.get(syntaxe);

			boolean wordUne          = word.equalsIgnoreCase("une");

			boolean nextIsNomCommun  = listEqualsElement(nextList, "Nom commun");
			boolean nextIsVerb       = listContains(nextList, "verbe#");

			if      (wordUne && nextIsNomCommun) { currentList.clear(); currentList.add("article indéfini"); }
			else if (wordUne && nextIsVerb)      { currentList.clear(); currentList.add("Forme de pronom indéfini"); }

			if      (wordUne && nextIsNomCommun) {System.out.println("isArticleOrPronom: " + syntaxe);}
			else if (wordUne && nextIsVerb)      {System.out.println("isArticleOrPronom: " + syntaxe);}

		}
	}
	
	


	
	

	
	private void cannotAfterDeterminant(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe=2; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> lastX2List   = currentSyntaxe.get(syntaxe - 2);
			ArrayList<String> lastList     = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> next         = currentSyntaxe.get(syntaxe + 1);
			
			boolean lastIsOne          = lastList.size() == 1;
			boolean lastContainsDet    = thisListContains(lastList, this.determinantCommun);
			boolean isntAdjNum         = listEqualsElement(lastList, "Adjectif numéral");
			
			boolean currentIsNotOne    = currentList.size() > 1;
			boolean currentIsAdjectif  = listEqualsElement(currentList, "Adjectif");
			
			boolean lastIsNc  = listEqualsElement(lastList, "Nom commun");
			boolean lastIsAdj = listEqualsElement(lastList, "Adjectif");

			boolean lastIsCnj = listEqualsElement(lastList, "Conjonction de coordination");
			boolean lastX2IsAdj = listEqualsElement(lastX2List, "Adjectif");
			boolean lastX2IsNc = listEqualsElement(lastX2List, "Nom commun");
			

			
			boolean conditionLast = !lastIsCnj && !lastX2IsAdj && !lastX2IsNc;
			
			
			boolean currentIsPreposition  = listEqualsElement(currentList, "Adverbe");
			boolean currentIsAdverbe      = listEqualsElement(currentList, "Préposition");
	
			boolean nextIsTwo             = next.size() == 2;
			boolean nextNotOnlyNc         = listEqualsElement(next, "Nom commun") || 
											listEqualsElement(next, "Forme de nom commun");
			
			boolean nextNc                = nextIsTwo && nextNotOnlyNc;
			
			
			
			if(lastIsOne && lastContainsDet && currentIsNotOne && currentIsAdjectif && !nextNc) {
				removeFromList(currentList, "Adjectif");
				System.out.println("isAdjectifOrNomCommunLastIsDeterminant (adj): " + syntaxe);
			}
			

			else if(lastIsOne && lastContainsDet && currentIsNotOne && currentIsPreposition) {
				removeFromList(currentList, "Adverbe");
				System.out.println("isAdjectifOrNomCommunLastIsDeterminant (adverbe): " + syntaxe);
			}
			

			
			else if(lastIsOne && lastContainsDet && currentIsNotOne && currentIsAdverbe && !lastIsNc && !lastIsAdj && conditionLast && !isntAdjNum) {
				removeFromList(currentList, "Préposition");
				System.out.println("isAdjectifOrNomCommunLastIsDeterminant (prépo): " + syntaxe);
			}
		}
	}
	
	
	
	
	private void nomCommunOrForm(ArrayList<ArrayList<String>> currentSyntaxe) {
		

		
		for (int syntaxe=0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList   = currentSyntaxe.get(syntaxe);
			
			boolean lengthIsNotOne          = currentList.size() > 1;
			boolean isPresentNomCommun      = listEqualsElement(currentList, "Nom commun");
			boolean isPresenteFormNomCommun = listEqualsElement(currentList, "Forme de nom commun");
			
			if (lengthIsNotOne && isPresentNomCommun && isPresenteFormNomCommun) {
				removeFromList(currentList, "Nom commun");
				System.out.println("nomCommunOrForm: " + syntaxe);
			}
		}
	}
	

	
	private void cantBeNomCommunVerbPrepoNomCommun(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe=2; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			

			ArrayList<String> lastLastList = currentSyntaxe.get(syntaxe - 2);
			boolean lastLastIsOne          = lastLastList.size() == 1;
			boolean lastLastIsVerb         = lastLastList.get(0).contains("verbe#");
			
			ArrayList<String> lastList     = currentSyntaxe.get(syntaxe - 1);
			boolean lastIsOne              = lastList.size() == 1;
			boolean lastIsPreposition      = lastList.get(0).equalsIgnoreCase("Préposition");
			
			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			boolean currentIsNotLengthOne  = currentList.size() > 1;
			boolean currentContainsNomCommun = listEqualsElement(currentList, "Nom commun");
 
			
			if (lastLastIsOne && lastLastIsVerb && lastIsOne && lastIsPreposition && currentIsNotLengthOne && currentContainsNomCommun) {
				removeFromList(currentList, "Nom commun");
				System.out.println("cantBeNomCommunVerbPrepoNomCommun: " + (syntaxe));
			}
		}
	}
	
	
	

	

	
	private void cannotBeAnArticle(ArrayList<ArrayList<String>> currentSyntaxe) {

		for (int syntaxe=0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			ArrayList<String> nexTtList    = currentSyntaxe.get(syntaxe + 1);
			
			boolean currentContainsArt  = thisListEqualList(currentList, this.articles);
			boolean currentIsNotOne     = currentList.size() > 1;

			boolean nexTListContainsArt = thisListEqualList(nexTtList  , this.articles);
			boolean nexTIsOne           = nexTtList.size() == 1;

			
			if (currentContainsArt && nexTListContainsArt && currentIsNotOne && nexTIsOne) {
				removeFromListFromList(currentList, this.articles);
				System.out.println("cannotBeAnArticle: " + syntaxe);
			}
		}
	}

	

	private void contractionCase(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe=0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList  = currentSyntaxe.get(syntaxe);
			
			boolean lengthIsTwo         = currentList.size() == 2;
			boolean contractionAndOther = listContains(currentList, "contraction=");
			
			if (lengthIsTwo && contractionAndOther) {
				removeFromListContains(currentList, "contraction=");
				System.out.println("contractionCase " + syntaxe);
			}
		}
	}
	
	
	
	private void isAdjectifOrVerb(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe=0; syntaxe < currentSyntaxe.size() - 3; syntaxe++) {
			
			ArrayList<String> currentList      = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList	       = currentSyntaxe.get(syntaxe + 1);
			ArrayList<String> nextNextList     = currentSyntaxe.get(syntaxe + 2);
			ArrayList<String> nextNextNextList = currentSyntaxe.get(syntaxe + 3);
			
			boolean isLengthOne        = currentList.size() == 1;
			boolean isAdjectif         = listEqualsElement(currentList,  "Adjectif");
			boolean nextIsConjoncti    = listEqualsElement(nextList,     "Conjonction de coordination");
			boolean nextNextIsAfjectif = listEqualsElement(nextNextList, "Adjectif");
			boolean nextNextIsAVerb    = listContains(nextNextList, "verbe#");
			boolean NexX3tIsAdverb     = listEqualsElement(nextNextNextList, "Adverbe");

			if (isLengthOne && isAdjectif && nextIsConjoncti && nextNextIsAfjectif && nextNextIsAVerb && !NexX3tIsAdverb) {
				dontRemoveFromListElement(nextNextList, "Adjectif");
				System.out.println("isAdjectifOrVerb (remove adjectif): " + (syntaxe+2));
			}
			else if (isLengthOne && isAdjectif && nextIsConjoncti && nextNextIsAfjectif && nextNextIsAVerb && NexX3tIsAdverb) {
				dontRemoveFromListContains(nextNextList, "verbe#");
				System.out.println("isAdjectifOrVerb (keep verb): " + (syntaxe + 2));
			}
		}
	}
	
	
	
	private void conditionQuelque(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> Text) {
		


		for (int syntaxe=1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
		
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastList	  = currentSyntaxe.get(syntaxe - 1);
			ArrayList<String> nextList	  = currentSyntaxe.get(syntaxe + 1);

			String word = Text.get(syntaxe);

			boolean currentIsNotOne = currentList.size() > 1;

			boolean isQuelques  = word.equalsIgnoreCase("quelques");
			boolean isQuelque   = word.equalsIgnoreCase("quelque");

			boolean isNumeral   = listEqualsElement(lastList,    "Adjectif numéral");

			boolean beforeIsDet = thisListEqualList(lastList, this.determinantCommun);
			boolean afterIsName = listEqualsElement(nextList, "Nom commun");
			


			if      (isQuelques) {currentList.clear(); currentList.add("Adjectif indéfini");
								  System.out.println("conditionQuelque (1): " + syntaxe);}

			else if (isQuelque && isNumeral) {currentList.clear(); currentList.add("Adverbe");
					                          System.out.println("conditionQuelque (2): " + syntaxe);}

			else if ((currentIsNotOne && isQuelque && beforeIsDet && afterIsName) ||
					 (currentIsNotOne && isQuelque && beforeIsDet) ||
					 (currentIsNotOne && isQuelque && afterIsName)) {
					currentList.clear(); currentList.add("Adjectif indéfini");
					 System.out.println("conditionQuelque (3): " + syntaxe);}

		}
	}
	
	
	
	private void beforeVerb(ArrayList<ArrayList<String>> currentSyntaxe) {

		for (int syntaxe=1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastList    = currentSyntaxe.get(syntaxe - 1);
			
			boolean isVerb       = listContains(currentList, "verbe#");
			boolean currentIsOne = currentList.size() == 1;
			
			boolean lastIsNotOne  = lastList.size() > 1;
			boolean isBeforeVerb  = thisListContains(currentList, this.beforeVerb);
			boolean isPonctuation = thisListContains(currentList, this.Ponctuation);
			
			ArrayList<String> saveList = new ArrayList<String>(lastList);
			
			if (currentIsOne && isVerb && lastIsNotOne && (isBeforeVerb || isPonctuation)) {
				dontRemoveFromThisList(saveList, this.beforeVerb);
			}
			
			
			boolean saveListEmpty = saveList.size() == 0;
			
			if (currentIsOne && isVerb && lastIsNotOne && (isBeforeVerb || isPonctuation) && !saveListEmpty) {
				dontRemoveFromThisList(lastList, this.beforeVerb);
				System.out.println("beforeVerb: " + (syntaxe - 1));
			}

		}
	}
	
	
	private void indexNoneSearch(ArrayList<ArrayList<String>> currentSyntaxe, List<Integer> indexToremove, ArrayList<String> currentText) {


		for (int syntaxe=0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			
			ArrayList<String> currentList   = currentSyntaxe.get(syntaxe);

			

			boolean isOneLength = currentList.size() == 1;
			boolean isNone = listEqualsElement(currentList, "None");
			
			
			boolean sameLength = currentSyntaxe.size() - 1 == currentText.size() || currentSyntaxe.size() == currentText.size() || syntaxe == 0;

			if (sameLength) {
				

				
				String word		   = currentText.get(syntaxe);
				String firstletter = Character.toString(word.charAt(0));

				boolean isMaj  = firstletter.equals(firstletter.toUpperCase());
				int nb = -1;
				try { nb = Integer.parseInt(word); } catch (Exception e) {}
				boolean isNb = nb != -1;
				
				boolean nextContains = false;
				try {
					ArrayList<String> next   = currentSyntaxe.get(syntaxe + 1);
					nextContains = listContains(next, currentText.get(syntaxe).toLowerCase()); 
				}
				catch(Exception e) {}

				if      (isOneLength && isNone &&  isMaj && !isNb && !nextContains) { 
					currentList.clear(); currentList.add("Nom propre"); 
				}
				
				else if (isOneLength && isNone && !isMaj && isNb && !sameLength) {
					indexToremove.add(syntaxe);
					System.out.println("deleteNoneSearch: " + syntaxe);
				}
				
				else if (isOneLength && isNone && isMaj && isNb && !sameLength) {
					indexToremove.add(syntaxe); 
					System.out.println("deleteNoneSearch: " + syntaxe);
				}

				else if (isOneLength && isNone && !sameLength) {
					indexToremove.add(syntaxe);
					System.out.println("deleteNoneSearch: " + syntaxe);
				}
			}
			
			else if (!sameLength && syntaxe != 0) {
				if (isOneLength && isNone) {indexToremove.add(syntaxe); System.out.println("deleteNoneSearch: " + syntaxe); }
			}
		}
	}
	
	
	private void deleteNoneSearch(List<Integer> indexToremove, ArrayList<ArrayList<String>> currentSyntaxe) {

		int index = 0;

		for (int loop=0; loop < indexToremove.size(); loop++) {

			int SyntaxetoRemove = indexToremove.get(loop);

			boolean indexIsNot0 = index > 0;
			boolean indexIs0    = index == 0;

			if      (indexIs0)    {index++;}
			else if (indexIsNot0) {SyntaxetoRemove = SyntaxetoRemove - index; index++;}

			currentSyntaxe.remove(SyntaxetoRemove);
		}
		indexToremove.clear();
	}



	
	
	
	
	
	
	
	
	
	private void conditionlettre(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> Text) {
		

		for (int syntaxe=0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			String word = Text.get(syntaxe);

			boolean isLengthOne = currentList.size() == 1;
			boolean isLettre    = listEqualsElement(currentList, "Lettre");

			if      (!isLengthOne) {removeFromList(currentList, "Lettre");}

			boolean isa          	  = word.equalsIgnoreCase("a");
			boolean isy          	  = word.equalsIgnoreCase("y");
			boolean isà         	  = word.equalsIgnoreCase("à");
			boolean isNotAdverbe 	  = !currentList.get(0).equalsIgnoreCase("Adverbe");
			boolean isNotAlreadyPrepo = !currentList.get(0).equalsIgnoreCase("Préposition");
			boolean lengthIsNotOne    = currentList.size() > 1;
			boolean containsVerbe     = listContains(currentList, "verbe#");
			
			
			if     (isà && isNotAdverbe && isNotAlreadyPrepo) { currentList.clear(); currentList.add("Préposition");}
			else if(isy) {currentList.clear(); currentList.add("Pronom personnel");}
			else if(isa && isLettre && !isà && !containsVerbe) { currentList.clear(); currentList.add("verbe#"); }
			else if (isa && isLettre && !isà && containsVerbe) { dontRemoveFromListContains(currentList, "verbe#"); }

			if     (isà && isNotAdverbe) {System.out.println("conditionlettre(à): " + syntaxe);}
			else if(isy) {System.out.println("conditionlettre(y): " + syntaxe);}
			else if(isa && isLettre && !isà) {System.out.println("conditionlettre(verb): " + syntaxe);}
		
		
		}
	}


	private void isArticlePartitif(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		for (int syntaxe=0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);

			String[] articlesUndefine     = {"Forme d’article indéfini", "Forme d'article indéfini"};
			String[] articlesDefine       = {"Forme d’article défini"  , "Forme d'article défini"};
			String[] articlePartitif      = {"Article partitif"}; 

			boolean containsArticleUndefine = listEquals(currentList, articlesUndefine);
			boolean containsArticleDefine   = listEquals(currentList, articlesDefine);
			boolean containsPartitif        = listEquals(currentList, articlePartitif);
			boolean isLengthTwo             = currentList.size() == 2;
			//pronom relatif verbe apres
			

			if(containsArticleUndefine && containsPartitif && isLengthTwo) {
				removeFromListFromList(currentList, articlesUndefine);
				//removeFromListFromList(currentList, articlePartitif);
				System.out.println("isArticlePartitif (ici définir partitif): " + syntaxe);
			}
			else if(containsArticleDefine && containsPartitif && isLengthTwo) {
				//removeFromListFromList(currentList, articlesDefine);
				removeFromListFromList(currentList, articlePartitif);
				System.out.println("isArticlePartitif (ici définir partitif): " + syntaxe);
			}
		}
	}
	
	
	
	private void isArticleDefiniOrIndefini(ArrayList<ArrayList<String>> currentSyntaxe,  ArrayList<String> currentText) {
		
		for (int syntaxe=0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			String            word        = currentText.get(syntaxe);

			String[] articleUndefini      = {"Forme d’article indéfini", "Forme d'article indéfini"};
			String[] articleDefine        = {"Forme d’article défini"  , "Forme d'article défini"}; 

			boolean containsArtDefUndefine = listEquals(currentList, articleUndefini);
			boolean containsArtDefinine    = listEquals(currentList, articleDefine);
			
			if (containsArtDefUndefine && containsArtDefinine) {

				boolean undefine = thisListEqualWord(this.artcileIndefini, word);
				boolean define   = thisListEqualWord(this.articleDefini,   word);

				if      (undefine) {dontRemoveFromList(currentList, articleUndefini);}
				else if (define)   {dontRemoveFromList(currentList, articleDefine);}

				if (undefine || define) { System.out.println("isArticleDefiniOrIndefini: " + syntaxe);}
			}
		}
	}


	
	
	private void isPreposition(ArrayList<ArrayList<String>> currentSyntaxe) {


		int index = 0;//1
		for (ArrayList<String> syntaxe: currentSyntaxe) {

			if (index < currentSyntaxe.size() - 1) {

				boolean thereIsPreposition = false;
				boolean thereIsDeterminant = false;
				boolean currentLengthOne   = syntaxe.size() == 1;

				boolean nexTisLengthOne   = currentSyntaxe.get(index + 1).size() == 1;
				boolean nexTisNomCommun   = currentSyntaxe.get(index + 1).get(0).equalsIgnoreCase("nom commun");
				
				boolean containsArt       = thisListEqualList(syntaxe, this.determinantCommun);
				boolean isOne             = syntaxe.size() == 1;
				
				ArrayList<String> next = currentSyntaxe.get(index + 1);
				
				boolean containsVerb = listContains(next, "verbe#");
				String  verb 		 = listContainsRecuperate(next, "verbe#");

				boolean isInfinitif      = verb.equalsIgnoreCase("verbe#");
				boolean containsGerondif = verb.contains("érondif");
				boolean containsPP = verb.contains("articipe présent");
				
				
				boolean nextVerb = containsVerb && (isInfinitif || containsGerondif || containsPP);
				
				
				for (String syn: syntaxe) {
					if(syn.equalsIgnoreCase("préposition")){thereIsPreposition = true;}

					for (String det: this.determinantCommun) {
						if (det.equalsIgnoreCase(syn)) {thereIsDeterminant = true;}
					}
				}

				if(nexTisLengthOne && nexTisNomCommun && !currentLengthOne && containsArt) {
					dontRemoveFromThisList(syntaxe, this.determinantCommun);
					System.out.println("isPreposition (no remove det):" + index);
				}

				if (nexTisLengthOne && thereIsPreposition && thereIsDeterminant && !isOne && nextVerb) {
					syntaxe.clear();
					syntaxe.add("Préposition");
					System.out.println("isPreposition (no remove prépo): " + index);
				}
				index++;
			}
		}

		


		//2
		isInterogation(currentSyntaxe);

		for (int index1 = 1; index1 < currentSyntaxe.size(); index1++) {

			ArrayList<String> currentList = currentSyntaxe.get(index1);
			ArrayList<String> lastList    = currentSyntaxe.get(index1 - 1);
	
			boolean isPreposition   	 = listEqualsElement(currentList, "Préposition");
			boolean lengthIsMoreOne 	 = currentList.size() > 1;
			boolean syntaxeInterroBefore = listContains(lastList, "interrogatif");
			boolean lastListIsOne        = lastList.size() == 1;


			if (isPreposition && lengthIsMoreOne && syntaxeInterroBefore && lastListIsOne) {
				currentList.clear();
				currentList.add("préposition");
				System.out.println("isPreposition: " + index1);
			}
		}
		
	}
	
	

	private void cannotBeFromLastVerbe(ArrayList<ArrayList<String>> currentSyntaxe, boolean isTiratePresent) {
		
		//Remove verbe if last word isn't a before verb from global list.
		
		for (int syntaxe=1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastList    = currentSyntaxe.get(syntaxe - 1);
			
			boolean isVerb = listContains(currentList, "verbe#");
			boolean notOne = currentList.size() > 1;

			boolean isBeforeVerbeContains = thisListContains(lastList, this.beforeVerb);

			boolean lastIsOne = lastList.size() == 1;
			
			if((isVerb && notOne && !isTiratePresent) && !isBeforeVerbeContains && lastIsOne) {
				removeFromListContains(currentList, "verbe#");
				removeFromList(currentList, "Verbe");
				System.out.println("cannotBeFromLastVerbe: " + syntaxe);
			}
		}

	}

	
	
	private void cannotBeVerbBeforeAdjectif(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Remove verbe if next's adjective. For example: la voiture rouge. voiture's verbe "je voiture". Remove verbe
		//Because next's adjective.
		
		for (int syntaxe=1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> lastList    = currentSyntaxe.get(syntaxe - 1);
			
			boolean currentIsOne      = currentList.size() == 1;
			boolean currentIsAdjectif = listEqualsElement(currentList, "Adjectif");
			boolean lastContainsVerb  = listContains(lastList, "verbe#");
			boolean lastIsNotOne      = lastList.size() > 1;
			
			if (lastContainsVerb && lastIsNotOne && currentIsOne && currentIsAdjectif) {

				removeFromListContains(lastList, "verbe#");
				System.out.println("cannotBeVerbBeforeAdjectif: " + (syntaxe - 1));
			}
		}
	}
	

	
	private void deleteNomCommunIfCurrentNomCommun(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Delete nom commun from next if current is nom commun.
		
		cannotBeVerbBeforeAdjectif(currentSyntaxe);
		for (int syntaxe=1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList    = currentSyntaxe.get(syntaxe + 1);
			ArrayList<String> lasTList    = currentSyntaxe.get(syntaxe - 1);

			String[] nomCommun            = {"Nom commun", "Forme de nom commun"};

			boolean currentIsNomCommun = listEquals(currentList, nomCommun);
			boolean currentIsOne       = currentList.size() == 1;
			boolean currentIsNotOne    = currentList.size() > 1;
			
			boolean nextIsNomCommun    = listEquals(nextList, nomCommun);
			boolean nextIsNotOne       = nextList.size() > 1;
			boolean nextIsOne          = nextList.size() == 1;
			boolean nextContainsNc     = listContains(nextList, " Nom commun");
			
			boolean lastIsNomCommun    = listEquals(lasTList, nomCommun);
			boolean lastIsNotOne       = lasTList.size() > 1;
			
			if (currentIsNomCommun && currentIsOne && nextIsNomCommun && nextIsNotOne) {
				removeFromList(nextList, "Nom commun");
				System.out.println("deleteNomCommunIfCurrentNomCommun(after): " + (syntaxe + 1));
			}

			else if (currentIsNomCommun && currentIsOne && nextIsNotOne && nextContainsNc) {
				removeFromListContains(nextList, " Nom commun");
				System.out.println("deleteNomCommunIfCurrentNomCommun(next): " + (syntaxe + 1));
			}
		}
	}

	
	
	
	

	private void isDeterminant(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Don't remove determinant if next word is nom commun.
		
		for (int syntaxe=0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList 	  = currentSyntaxe.get(syntaxe + 1);

			String[] elementDoNotRemove = {"Forme d’adjectif indéfini"};
			String[] adjIndefini        = {"Forme d’adjectif indéfini"};
			String[] NomCommun          = {"Nom commun"};
			String[] formeNomCommun     = {"Forme de nom commun"};

			boolean isDeterminant     = listEquals(currentList, adjIndefini);

			boolean isNomCommun       = listEquals(nextList, NomCommun);
			boolean isFormNomCommun   = listEquals(nextList, formeNomCommun);
			
			if     (isDeterminant && isNomCommun)     {
				dontRemoveFromList(currentList, elementDoNotRemove); System.out.println("isDeterminant: " + syntaxe);}
			else if(isDeterminant && isFormNomCommun) {
				dontRemoveFromList(currentList, elementDoNotRemove); System.out.println("isDeterminant: " + syntaxe);}
			

		}
	}
	
	
	
	private int caseTirate(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		//Index localisation of a tirate.
		
		ArrayList<Integer> indexTiret = new ArrayList<Integer>();

		for (int syntaxe=0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {

			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);

			boolean isTirate = listEqualsElement(currentList, "tiret");

			if (isTirate) {indexTiret.add(syntaxe);}

		}

		int index = 0;
		int indexTirate = 0;

		for (int tirate: indexTiret) {
			if(index > 0) {tirate = tirate - 1;}

			currentSyntaxe.remove(currentSyntaxe.get(tirate));
			currentText.remove(currentText.get(tirate));
			System.out.println("caseTirate: " + tirate);

			indexTirate = tirate;
			index++;
		}

		return indexTirate;
	}
	

	private void isNotNomCommun(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Remove nom commun: if next isn't verb, prep, adjective. Current doesn't contains adjectif and isn't 2.
		//Remove adjective: next verb and current contains nom commun and adjective.
		//Remove verb ifnext preposition and nextx2 nom commun and isn't a gerondif or an infinitif.
		
		for (int syntaxe=0; syntaxe < currentSyntaxe.size() - 2; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> next        = currentSyntaxe.get(syntaxe + 1);
			ArrayList<String> nextX2      = currentSyntaxe.get(syntaxe + 2);

			boolean  isFisrtWord         = syntaxe == 0;
			String[] NomCommun           = {"Nom commun"};
			boolean  isNomCommun         = listEquals(currentList, NomCommun);
			boolean  isNotLengthOne      = currentList.size() > 1;
			boolean  currentsVerb        = listContains(currentList, "verbe#");
			
			boolean  nextIsVerb          = listContains(next, "verbe#");
			boolean  nextIsPrep          = listEqualsElement(next, "Préposition");
			boolean  nextIsAdj           = listEqualsElement(next, "Adjectif");
			
			boolean  nextX2IsNc          = listEqualsElement(nextX2, "Nom commun");
			
			boolean  isTwo               = currentList.size() == 2;
			boolean  containAdj          = listEqualsElement(currentList, "Adjectif");

			String  verb = listContainsRecuperate(currentList, "verbe#");
			boolean containsGerondif = verb.contains("érondif");
			boolean containsPP       = verb.contains("articipe présent");
			boolean time             = containsGerondif || containsPP;
			
			
			if (isFisrtWord && isNomCommun && isNotLengthOne && (!nextIsVerb && !nextIsPrep && !nextIsAdj) && !(isTwo && containAdj)) {
				removeFromList(currentList, "Nom commun");
				System.out.println("isNotNomCommun (premier mot): " + syntaxe);
			}

			else if (isFisrtWord && isNomCommun && isNotLengthOne && !nextIsVerb && (isTwo && containAdj)) {
				removeFromList(currentList, "Adjectif");
				System.out.println("isNotNomCommun (premier mot)2: " + syntaxe);
			}
			
			else if (isFisrtWord && isNomCommun && currentsVerb && nextIsPrep && nextX2IsNc && isNotLengthOne && !time) {
				removeFromListContains(currentList, "verbe#");
				System.out.println("isNotNomCommun1 " + syntaxe);
			}
			
			else if (isFisrtWord && isNomCommun && currentsVerb && nextIsPrep && nextX2IsNc && isNotLengthOne && time) {
				removeFromList(currentList, "Nom commun");
				System.out.println("isNotNomCommun (premier mot2): " + syntaxe);
			}
			

			else if (!isFisrtWord) {

				ArrayList<String> LastList    = currentSyntaxe.get(syntaxe - 1);
				String[] NomCommuns           = {"Nom commun", "Forme de nom commun"};

				boolean lastIsVerb            = listContains(LastList,     "verbe#");
				boolean lastIsConjonction     = listEqualsElement(LastList,"Conjonction");
				boolean lastIsOne             = LastList.size() == 1;


				
				if (lastIsVerb && lastIsOne && isNotLengthOne)             {
					removeFromListFromList(currentList, NomCommuns);
					System.out.println("isNotNomCommun(verbe + nm) : " + syntaxe);
				}
				else if (lastIsConjonction && lastIsOne && isNotLengthOne) {
					removeFromListFromList(currentList, NomCommuns);
					System.out.println("isNotNomCommun (cnjnction + nm)" + syntaxe);
				}


				
			}
		}
	}

	
	private void isNotPronom(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		//Choice to keep pronom personnel or not. From la/le case.
		//If first word and next's verbe keep pronom personneL;
		
		for (int syntaxe=0; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> next        = currentSyntaxe.get(syntaxe + 1);

			String word = currentText.get(syntaxe);
			
			boolean isFisrtWord = syntaxe == 0;
			boolean isPronom = TableauEquals(this.pronomPersonnel, currentText.get(syntaxe));
			
			boolean isLe         = word.equalsIgnoreCase("le");
			boolean isLa         = word.equalsIgnoreCase("la");
			
			boolean nextIsVerbe  = listContains(next, "verbe#");
			boolean nextIsOne    = next.size() == 1;
			boolean currentIsOne = currentList.size() == 1;
			
			if (isFisrtWord && !isPronom && !nextIsVerbe && !currentIsOne) {
				removeFromList(currentList, "Pronom personnel");
				System.out.println("isNotPronom: " + syntaxe);
			}	
			
			else if (isFisrtWord && nextIsVerbe && (isLe || isLa) && nextIsOne) {
				     currentList.clear(); currentList.add("Pronom personnel");
                     System.out.println("isNotPronom: prnm" + syntaxe);
            }	
			
		}
	}




	private void afterVerb(ArrayList<ArrayList<String>> currentSyntaxe) {

		//After a verbe dont remove nom commun.

		for (int syntaxe=1; syntaxe < currentSyntaxe.size() - 1; syntaxe++) {
			
			ArrayList<String> currentList = currentSyntaxe.get(syntaxe);
			ArrayList<String> nextList    = currentSyntaxe.get(syntaxe + 1);

			boolean onlyOneSyntaxe    = currentList.size() == 1;
			boolean nextLengthMoreOne = nextList.size() > 1;
			boolean isVerb            = listContains(currentList, "verbe#");

			boolean containsNm        = listEqualsElement(nextList, "Nom commun");
			
			if (onlyOneSyntaxe && nextLengthMoreOne && isVerb && containsNm) {
				removeFromList(nextList, "Nom commun");
				System.out.println("afterVerb: remove nc " + (syntaxe + 1));}

		}
	}
	
	

	private void ConjonctionPronomAdverbe(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Choice pronom relative, conjonction or adverbe.
		//pronom relative: last nom
		//Conjonction: last verb and next'nt article.
		//Adverbe: last verbe and next article.
		
		for (int index=0; index < currentSyntaxe.size() - 1; index++) {
			
			ArrayList<String> syntaxes = currentSyntaxe.get(index);
			ArrayList<String> next     = currentSyntaxe.get(index + 1);
			
			String[] conjonction   = {"Conjonction"};
			String[] pronomRelatif = {"Pronom relatif"};
			String[] adverbe       = {"Adverbe"};
			
			
			boolean containsConjonction = listEquals(syntaxes, conjonction);
			boolean containsPronom      = listEqualsElement(syntaxes, "Pronom relatif");
			boolean containsAdverb      = listEquals(syntaxes, adverbe);

			String  lastElement 		= currentSyntaxe.get(currentSyntaxe.size() - 1).get(0);
			boolean sentenceIsInterro   = lastElement.equalsIgnoreCase("point interrogation");
			boolean sentenceIsExcla     = lastElement.equalsIgnoreCase("point exclamation");
			boolean indexIsZero         = index == 0; 

			if ((!sentenceIsInterro && !sentenceIsExcla && !indexIsZero) && 
				((containsConjonction && containsPronom && containsAdverb)   ||
			     (containsConjonction && (containsPronom || containsAdverb)) ||
			     (containsPronom && (containsConjonction || containsAdverb)) ||
			     (containsAdverb && (containsConjonction || containsPronom)))) {

				ArrayList<String> Lastsyntaxes = currentSyntaxe.get(index - 1);
				
				boolean lastListIsOne = Lastsyntaxes.size() == 1;
				boolean lastIsNom     = Lastsyntaxes.get(0).equalsIgnoreCase("nom commun");
				boolean lastIsVerb    = Lastsyntaxes.get(0).contains("verbe#");

				boolean nextIsArt     = thisListEqualList(next, determinantCommun);
				

				
				if     (lastListIsOne && lastIsNom && containsPronom) {dontRemoveFromList(syntaxes, pronomRelatif); }
				else if(lastListIsOne && lastIsVerb && !nextIsArt && containsConjonction)    {dontRemoveFromList(syntaxes, conjonction);}
				else if(lastListIsOne && lastIsVerb && nextIsArt && containsPronom)     {dontRemoveFromList(syntaxes, pronomRelatif);}

				if     (lastListIsOne && lastIsNom && containsPronom) {System.out.println("ConjonctionPronomAdverbe a " + syntaxes); }
				else if(lastListIsOne && lastIsVerb && !nextIsArt && containsConjonction)    {
					System.out.println("ConjonctionPronomAdverbe b " + syntaxes);}
				else if(lastListIsOne && lastIsVerb && nextIsArt && containsPronom)     {
					System.out.println("ConjonctionPronomAdverbe c " + syntaxes);}
				
				

			}

			else if (containsConjonction && containsPronom && containsAdverb && (sentenceIsInterro || sentenceIsExcla) && indexIsZero) {
				syntaxes.clear();
				syntaxes.add("adverbe");
				System.out.println("ConjonctionPronomAdverbe 2: " + index);
			}
			
			
		}
	}
	

	private void isAdverbelà(ArrayList<String> text, ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Case unique là adverbe.

		for (int index=0; index < text.size() - 1; index++) {
			
			String word = text.get(index);
			ArrayList<String> current = currentSyntaxe.get(index);

			boolean lengthIsOne = current.size() == 1;
			boolean isAdverbeLa = word.equalsIgnoreCase("là");
			
			if (!lengthIsOne && isAdverbeLa) {current.clear();current.add("Adverbe");}
			if (!lengthIsOne && isAdverbeLa) {System.out.println("isAdverbelà: "  + index);}
		}
	}
	
	
	
	
	

	private void isInterogation(ArrayList<ArrayList<String>> currentSyntaxe) {

		
		//Verify last charachter is interrogative.
		String  lastElementisInterro = currentSyntaxe.get(currentSyntaxe.size() - 1).get(0);
		boolean isInterrogation      = lastElementisInterro.equalsIgnoreCase("point interrogation");

		int index=0;
		for (ArrayList<String> List: currentSyntaxe) {

			int     display   = List.size();
			String  synt 	  = "";
			boolean isInterro = false;
			boolean isLength  = List.size() > 1;
			ArrayList<String> container = new ArrayList<String>();

			for(String syntaxe: List) {

				boolean containInterro = syntaxe.contains("interrogatif");

				if(containInterro) {synt = syntaxe; isInterro = true;}
				if(!isInterrogation && !containInterro) {container.add(syntaxe);}
			}

			boolean containerLength = container.size() > 0;

			if (isInterrogation && isInterro && isLength) {
				List.clear(); List.add(synt); 
				//System.out.println("isInterogation: " + index);
			}

			else if (!isInterrogation && isLength && containerLength) {
				List.clear();
				for (String element: container) {List.add(element);}
				//System.out.println("isInterogation: " + index);
			}

			index++;
		}
	}


	
	private void isAdjectifOrNomCommunNextIsAdjectif(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Keep nom commun if next adjective.
		
		for (int index=0;index < currentSyntaxe.size() - 1; index++) {

			ArrayList<String> currentList  = currentSyntaxe.get(index);
			ArrayList<String> oneListAfter = currentSyntaxe.get(index + 1);
			
			boolean currentIsLengthOne = currentList.size() == 1;
			boolean isNomCommun        = currentList.get(0).equalsIgnoreCase("nom commun");
			boolean NextSyntaxeMoreOne = oneListAfter.size() > 1;

			boolean isNomCommunNext    = listEqualsElement(oneListAfter, "nom commun");
			boolean isAdjectifNext     = listEqualsElement(oneListAfter, "Adjectif");


			if (currentIsLengthOne && isNomCommun && NextSyntaxeMoreOne && isAdjectifNext && isNomCommunNext) {
				removeFromList(oneListAfter, "Nom commun");
				System.out.println("isAdjectifOrNomCommunNextIsAdjectif: " + index);
			}
		}
	}
	

	
	
	private void deleteNomCommunIfIsDeterminant(ArrayList<ArrayList<String>> currentSyntaxe) {

		//Delete nom commun if word's detmerminant from global list. For example "un".

		for (int index=1; index < currentSyntaxe.size(); index ++) {

			boolean isNomCommun = false;
			boolean isNomPropre = false;

			boolean lengthOne   = currentSyntaxe.size() == 1;
			
			if(currentSyntaxe.get(index).get(0).equalsIgnoreCase("nom commun")) {isNomCommun = true;}
			if(currentSyntaxe.get(index).get(0).equalsIgnoreCase("nom propre")) {isNomPropre = true;}

			if ((isNomCommun || isNomPropre) && lengthOne) {

				String[] determiantList = null;
				if      (isNomCommun) {determiantList = this.determinantCommun;}
				else if (isNomPropre) {determiantList = this.determinantPropre;}

				boolean determinantIsPresent = false;
				ArrayList<String> container = new ArrayList<String>();
				for (String det: determiantList) {

					for (String syn: currentSyntaxe.get(index - 1)) {

						if (syn.equalsIgnoreCase(det))					      {determinantIsPresent = true;}
						if (syn.equalsIgnoreCase("nom commun")) 		      {}
						else if (syn.equalsIgnoreCase("forme de nom commun")) {}
						else if (syn.equalsIgnoreCase("pronom personnel"))    {}
						else if (!container.contains(syn))                    {container.add(syn);}
					}
				}
				currentSyntaxe.get(index - 1).clear();
				for(String a: container) {currentSyntaxe.get(index - 1).add(a);}
			}
		}	
	}



	private void isVerbeOrAdjectifInterrogation(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//Choice adjectif in if word contains adjective or pronom if the last charactere is "?".
	
		for (int list=1; list < currentSyntaxe.size(); list ++) {
			
			ArrayList<String> CurrentSentenceList = currentSyntaxe.get(list);
			ArrayList<String> lastSentenceList 	  = currentSyntaxe.get(list - 1);
			
			boolean isPronom     = thisListEqualList(CurrentSentenceList, this.pronomPersonnel);
			boolean lastIsOne    = lastSentenceList.size() == 1;
			
			boolean containsAdj  = listEqualsElement(CurrentSentenceList, "Adjectif");
			boolean lengthIsTwo  = CurrentSentenceList.size() == 2;

			String  lastSyntaxe 	  = currentSyntaxe.get(currentSyntaxe.size() - 1).get(0);
			boolean isInterroSentence = lastSyntaxe.equalsIgnoreCase("point interrogation");

			if(isPronom && lastIsOne && containsAdj && isInterroSentence && lengthIsTwo){
				dontRemoveFromListElement(CurrentSentenceList, "Adjectif");
				System.out.println("isVerbeOrAdjectifInterrogation: " + list);
			}
		}
	}
	
	
	
	private void verbOrNomCommun(ArrayList<ArrayList<String>> currentSyntaxe){

		//Choice to put Nom commun if last's determinant and not preposition or demonstrativ article.
		//Else choice verb.
		
		isPreposition(currentSyntaxe);

		for (int list=2; list < currentSyntaxe.size(); list ++) {

			ArrayList<String> currentList = currentSyntaxe.get(list);
			ArrayList<String> lastList    = currentSyntaxe.get(list - 1);
			ArrayList<String> lastListX2  = currentSyntaxe.get(list - 2);

			boolean isVerbPresent 	   = false;
			boolean isNomCommunPresent = false;

			boolean lastIsDeterminant  = false;
			boolean lastIsPreposition  = false;
			boolean lastIsLengthOne    = lastList.size() == 1;
			
			boolean lastx2IsNc         = listEqualsElement(lastListX2, "Nom commun");
			boolean isntlastPrepo      = listEqualsElement(lastList, "Préposition");
			boolean isntlastDem        = listContains(lastList, "démonstratif");
			
			for(String syntaxe: currentList) {
				if     (syntaxe.contains("verbe#"))				{isVerbPresent      = true;}
				else if(syntaxe.equalsIgnoreCase("Nom commun")) {isNomCommunPresent = true;}
			}


			if (isVerbPresent && isNomCommunPresent) {
				for (String element: lastList) {
					for (String determinant: this.determinantCommunWithotuAdj) {

						if (element.equalsIgnoreCase(determinant)) {lastIsDeterminant = true;}}
					if (element.equalsIgnoreCase("préposition"))   {lastIsPreposition = true;}}
			}


			if  (lastIsDeterminant && lastIsLengthOne && !isntlastPrepo && !isntlastDem) {
				removeFromListContains(currentList, "verbe#");
				removeFromList(currentList, "Verbe");
				System.out.println("verbOrNomCommun1 " + list);
			}

			else if(lastIsPreposition && lastIsLengthOne && !lastx2IsNc && !lastIsDeterminant) {
				removeFromList(currentList, "Nom commun");
				System.out.println("verbOrNomCommun2 " + list);
			}
		}
	}

	
	
	

	private void raiseNomCommunIfPronom(ArrayList<String> text, ArrayList<ArrayList<String>> syntaxe) {
		
		//From dico dico can give us pronom as Nc, rename it.
		
		String[] pronom = {"je", "tu", "il", "elle", "on", "nous", "vous", "ils", "elles"};
		
		for(int indexWord=0; indexWord < text.size(); indexWord++) {

			boolean sameLength = indexWord <= syntaxe.size() -1;
			
			if (sameLength) {
				ArrayList<String> currentList = syntaxe.get(indexWord);
				String word = text.get(indexWord);
				boolean isPrnm = thisListEqualWord(pronom, word);
	
				if (isPrnm) { currentList.clear(); currentList.add("Pronom personnel"); }
			}
		}
	}


	public void caseLes(ArrayList<ArrayList<String>> currentSyntaxe, ArrayList<String> currentText) {

		//From dico "les" give preposition, raise it.
		
		for (int index=0; index < currentText.size() - 1; index++) {
			
			String word = currentText.get(index);
			boolean isLes = word.equalsIgnoreCase("les");

			if (isLes) {removeFromList(currentSyntaxe.get(index), "Préposition");}
		}
	}



	public void foundVerbTwoTime(ArrayList<ArrayList<String>> currentSyntaxe) {
		
		//We scrapping two differents dictionnary (word and verbe).
		//the word dictionnary put Verb. Raise it for place verbe#.
		

		int index = 0;
		for (ArrayList<String> List: currentSyntaxe) {

			boolean listContainsTwoElement = List.size() == 2;

			if (listContainsTwoElement) {
				boolean firstIsVerb    = List.get(0).equalsIgnoreCase("verbe");
				boolean secondIsVerb   = List.get(1).contains("verbe#");

				if (listContainsTwoElement && firstIsVerb && secondIsVerb) {
					List.remove(List.get(0));
					System.out.println("foundVerbTwoTime: " + index);
				}
			}
			index++;
		}
	}
	


	private void defineNomPropreFromFirstLetter(ArrayList<String> text, ArrayList<ArrayList<String>> syntaxe) {

		//Define nom propre because dictionnary doesn't found nom propre and put none from the first letter.
		
		ArrayList<Boolean> ponct = new ArrayList<Boolean>();
		
		for (int word=0; word < syntaxe.size(); word++) {
			
			ArrayList<String> currentList = syntaxe.get(word);
			
			boolean isPonct = thisListEqualList(currentList, Ponctuation);
			if (isPonct) {ponct.add(true);}
			else {ponct.add(false);}
		}
		
		
		for (int word=0; word < syntaxe.size(); word++) {

			String firstLetter     = Character.toString(text.get(word).charAt(0));

			boolean isMajuscule    = firstLetter.equals(firstLetter.toUpperCase());
			boolean isNotFirstWord = word > 0;
			boolean noPonctuation  = text.get(word).length() > 1;
			boolean ContainSpace   = text.get(word).contains(" ");
					
			boolean lastArePonct   = areElementBefore(ponct, word);
		
			if (isMajuscule && isNotFirstWord && noPonctuation &&!ContainSpace && !lastArePonct) {
				syntaxe.get(word).clear(); 
				syntaxe.get(word).add("Nom propre");
				System.out.println("defineNomPropreFromFirstLetter : " + word);
			}
		}
	}


	private void delEmptySyntaxe(ArrayList<ArrayList<String>> syntaxe) {

		//Remove an empty syntaxe after a none raise.
		
		System.out.println(syntaxe);
		
		for (int sentence=0; sentence < syntaxe.size(); sentence++) {
			for (int word=0; word < syntaxe.get(sentence).size(); word++) {
				if (syntaxe.get(sentence).get(word).equalsIgnoreCase("")) {
					syntaxe.get(sentence).remove(syntaxe.get(sentence).get(word));
				}
			}
		}
	}
	

	
	
	

	
	
	
}
# Analyseur-syntaxique

Analysing syntaxe, schema sentence and inter subject in a text Fr

https://short-edition.com/fr/categorie/tres-tres-court

<h1> Scrapping </h1>

 - https://www.le-dictionnaire.com/ (word)
 
 - https://leconjugueur.lefigaro.fr/ (verbe)
 
 
<h1> Analyse syntaxique</h1>
 
 analyse et attribuation des fonctions syntaxiques aux mots
 
![aa](https://user-images.githubusercontent.com/54853371/83953960-267a9900-a845-11ea-8c8f-2f0da2541677.png)

![aa](https://user-images.githubusercontent.com/54853371/83954054-f7b0f280-a845-11ea-8c29-9e5a339b805e.png)

#décommentez cela, commentez le reste.

 
<h1> Analyse de schema phrase + détection du sujet</h1>
 
 analyse des différents groupes de schéma de phrase
 
 ![aa](https://user-images.githubusercontent.com/54853371/83953983-575ace00-a845-11ea-9f1e-965291ea2321.png)
 
<h1> Rattachement inter sujet</h1>
 
détection et rattachement des sujets entre les phrases

L'homme a mis des brindille sur sa tante, l'homme a froit, il se souffle dans les mains.

![sentence1](https://user-images.githubusercontent.com/54853371/83954299-4f505d80-a848-11ea-8dd5-de251d92ee6f.png)
![ana1](https://user-images.githubusercontent.com/54853371/83954301-524b4e00-a848-11ea-9170-04c71160833b.png)

![sentence2](https://user-images.githubusercontent.com/54853371/83954300-50818a80-a848-11ea-8e36-c611675e1909.png)
![ana2](https://user-images.githubusercontent.com/54853371/83954302-55ded500-a848-11ea-82ba-bb65184e7b1b.png)

![sentence3](https://user-images.githubusercontent.com/54853371/83954342-950d2600-a848-11ea-9436-9e392aec8dfd.png)


<h1>utilisation:</h1>

- télécharger éclipse

- télécharger librarie de scrappage java

- placer texte dans texte et le nommer 0.txt

- placer ses paths

- lancer programme 1 (sortie syntaxe + phrase)

- lancer programme 2 (sortie 1 analyse syntaxique)

<h1> ne peut pas:  </h1>

 dans certains cas

 - différencier proprement un adjectif d'un participe passé. Choix du participe passé.
 - faire correctement la différence entre un nom commun et un adjectif (qui peut etre un nom commun).
 - faire dans tous les cas la différence entre un verbe et un nom commun.

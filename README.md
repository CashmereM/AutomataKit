![Logotype_Nantes-U_noir-72dpi.png](.attachments.1559958319/Logotype_Nantes-U_noir-72dpi.png)

# 

R4.12 - Automates et langages

AHAMADA Ibrahim

Ressource R4.12 - Automates et langages : développement d'une application gérant une liste d'automates et analyse une expression entrée par l'utilisateur via un analyseur paramétré par un automate choisi parmi la liste d'automates.

Date : 16/02/2024

Lieu : France, Nantes, IUT de Nantes Joffre

### Sommaire

### 1. [Introduction](#introduction)
### 2. [Présentation de l'application](#pr%C3%A9sentation-de-lapplication)
### 3. [Manuel utilisateur](#manuel-utilisateur)
### 4. [Projet](#projet)
### 5. [Accepteur algorithme](#accepteur-algorithme)
### 6. [Réutilisation comme API](#r%C3%A9utilisation-comme-api)
### 7. [Limitations du projet](#limitations-du-projet)
### 8. [Conclusion](#conclusion)
### 9. [Remerciements](#remerciements)

## Introduction

L'application développée en **Kotlin** permet d'implémenter un analyseur paramétré par un automate. Elle offre la possibilité de sélectionner un automate parmi une liste pré-définie, puis d'analyser une expression entrée par l'utilisateur. L'analyse est effectuée à l'aide d'un algorithme qui vérifie si l'expression respecte les règles et les caractères définis par l'automate choisi.

## Présentation de l'application

L'application est à exécuter dans un terminal avec la commande suivante :

```
java -jar Automate.jar
```

### Manuel utilisateur

    ![Capture d’écran du 2024-02-16 15-55-39.png](.attachments.1559958319/Capture%20d%E2%80%99%C3%A9cran%20du%202024-02-16%2015-55-39.png)

Après execution du .jar, une interface graphique va s'ouvrir. Vous devriez choisir un automate parmi ceux dans la liste.

![image.png](.attachments.1559958319/image.png)

Après votre choix, vous serez rediriger vers une seconde interface, où vous pourrez tester l'analyseur paramétré par l'automate choisi.

![Capture d’écran du 2024-02-16 15-56-05.png](.attachments.1559958319/Capture%20d%E2%80%99%C3%A9cran%20du%202024-02-16%2015-56-05.png)

![Capture d’écran du 2024-02-16 15-56-43.png](.attachments.1559958319/Capture%20d%E2%80%99%C3%A9cran%20du%202024-02-16%2015-56-43.png)

Entrer une expression puis valider la. Si l'expression est correcte (dépend de l'automate) alors :

Resultat : OK s'affichera sinon Resultat : KO .

![Capture d’écran du 2024-02-16 15-57-05.png](.attachments.1559958319/Capture%20d%E2%80%99%C3%A9cran%20du%202024-02-16%2015-57-05.png)

Une fenêtre pop-up s'affichera pour vous prévenir de votre erreur et vous donner un descriptif du format attendu. Dans le cas où l'expression est valide, seul Resultat : OK s'affichera.

![Capture d’écran du 2024-02-16 15-58-31.png](.attachments.1559958319/Capture%20d%E2%80%99%C3%A9cran%20du%202024-02-16%2015-58-31.png)

## Projet

### Structure du projet

.
└── main
├── kotlin
│   └── app
│       ├── App.kt
│       ├── automates
│       │   ├── date.kt
│       │   ├── heure.kt
│       │   └── smiley.kt
│       ├── controller
│       │   ├── BoutonRetourChoixAutomateController.kt
│       │   ├── SelectionnerAutomateController.kt
│       │   └── VerifierChaineController.kt
│       ├── Main.kt
│       ├── model
│       │   ├── Automate.kt
│       │   ├── GestionAutomate.kt
│       │   └── State.kt
│       └── view
│           ├── AutomateChoiceView.kt
│           ├── AutomateView.kt
│           └── UserInterface.kt
└── resources
├── app
│   └── hello-view.fxml
├── css
│   └── choiceview.css
└── META-INF
└── MANIFEST.MF

### Classes

#### Automate

##### Attributs

```
alphabet : MutableSet<String> -> Représente l'alphabet de l'automate

ensembleEtats : MutableSet<State> -> Contient l'ensemble des états de l'automate

etatInitial : State -> Représente l'etat initial de l'automate

etatFinaux : MutableSet<State> -> Contient l'ensemble des etats finaux de l'automate

 relationTransition : MutableMap<String, State> -> Représente DELTA la relation de transition de l'automate

descriptionAutomate : String -> Une description du format correcte de l'expression que reconnait l'automate
```

##### Methodes

```
addTranstion -> permet de rajouter une transion d'un etat à un autre (etatEntre, caractere, etatSorti)

removeTransition -> permet de supprimer une transition entre un état et un autre état ( etat, caractere )

accepteur -> retourne un boolean True/False selon si l'expression entrée est validé par l'automate ou pas
```

#### State

Représente les états de l'automate

##### Attributs

```
name : String -> nom de l'état, vide si rien
outputTransition : MutableMap<String, State> -> contient les états de sortis qu'on peut accéder depuis l'instance en lui-même, et le caractère lié à cette transition
```

##### Methodes

```
addTranstion -> permet de rajouter une transion d'un etat à un autre (, caractere, etatSorti)

removeTransition -> permet de supprimer une transition entre un vers un état ( caractere )

accepteur -> retourne un boolean True/False selon si l'expression entrée est validé par l'automate ou pas
```

#### GestionAutomate

Représente le gestionnaire des automates de l'application

##### Attributs

```
currentAutomate : Automate -> 
listFonctionsDeCreationAutomate : MutableList<() -> 
```

##### Methodes

```
listeNomAutomate -> Retourne la liste des noms de chaque automate contenu dans listFonctionsDeCreationAutomate

changeCurrentAutomate -> Change l'automate courant
```

### Automates implémentés

Trois automates ont été implémentés :

- nom : Heure , format : HH:MM:SS , exemple : 23:43:01 , limite : 00:00:00 à 23:59:59

  ![heure.png](.attachments.1559958319/heure.png)
- nom : Date, format : JJ/MM/AAAA , exemple : 24/11/0010 , limite : (seulement année, mois et jour reprenne les normes du calendrier) 0001 à 9999 , exceptions : les années bisexitiles ne sont pas pris en compte donc le mois de février s'arrête au 28

  ![date.png](.attachments.1559958319/date.png)
- nom : Smiley, expressions acceptés -> :)    :-)    :(    ;-)    :=)   ]\-)

  ![20240215_094005.jpg](.attachments.1559958319/20240215_094005.jpg)

## Accepteur algorithme

![image (2).png](.attachments.1559958319/image%20%282%29.png)

L'algo commence l'analyse d'expression depuis l'etat initial, verifie caractère si l'etat courant contient une route pour le caractere, si c'est le cas, l'etat courant passe à l'etat de sorti de la route du caractere en question est repete le processus jusqu'à atteindre l'etat final ou un puit.

Exception :

- si il atteind un etat final mais que la chaine de caractère n'a pas été parcouru entierement alors, il retourne false
- si il atteind un puit, alors il retourne false

Scénario nominal : il parcours entiérement la chaine et atteind un etat final à la fin de la boucle, retourne true

## Réutilisation comme API

Si on devait utiliser l'application comme API, seul le modèle (les entités) pourront être utilisés.

Les entités ont été implémentés de sorte à n'avoir des dépendances que entre eux. L'entité GestionAutomate pourrait être omis dans le cadre de la réalisation de l'API car celle avait pour but de pouvoir utiliser une architecture MVC pour la conception de l'application.

## Limitations du projet

L'application se limite à une interface rudimentaire, la création des automates se fait avec une fonction pour chaque type d'automate, instancie l'automate et la retourne. Le mieux qu'on aurait voulu ici, c'est qu'à partir d'un Json ou CSV on puisse pouvoir générer différents automates. Pour moins de 10 automates, la methode de création des automates via des fonctions pourrait suffire, mais aller au-delà de ce nombre et cette méthode perd grandement en efficacité. Donc le stockage des automates serait meilleur avec l'un deux des formats.

Les automates ne peuvent être constitués que d'alphabet de caractère de la norme UTF-8

## Conclusion

En conclusion, nous avons implémenté des automates sous forme d'une application Kotlin avec une interface JavaFx. L'application permet de lister et d'appliquer l'algorithme de l'analyseur sur une expression rentrée.

On s'orientera maintenant vers une amélioration de l'algorithme de l'analyseur et on se demandera, pourrions-nous améliorer l'algorithme pour pouvoir prendre en compte le

## Remerciements

Merci Nils Moreau--Thomas pour le graphe de l'automate date

Merci à Jeremie Christian Attiogbe pour le cours sur les Automates et langages et l'algorithme de l'accepteur (analyseur) .

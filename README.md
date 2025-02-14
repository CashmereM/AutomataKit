![Logotype_Nantes-U_noir-72dpi.png](.attachments.1559958319/Logotype_Nantes-U_noir-72dpi.png)

# 

R4.12 - Automates et langages

AHAMADA Ibrahim

Ressource R4.12 - Automates et langages : développement d'une application gérant une liste d'automates et analyse une expression entrée par l'utilisateur via un analyseur paramétré par un automate choisi parmi la liste d'automates.

Date : 16/02/2024

Lieu : France, Nantes, IUT de Nantes Joffre

### Manuel utilisateur

Cette application en Kotlin permet de gérer une liste d'automates et d'analyser des expressions en fonction des règles définies par un automate choisi. Elle prend en charge les automates finis déterministes (DFA) pour vérifier la validité syntaxique d'expressions.  

## Fonctionnalités  
- **Gestion des automates** : Sélection d'un automate parmi une liste prédéfinie.  
- **Analyse d'expressions** : Vérification de la validité syntaxique d'une expression selon l'automate choisi.  
- **Exportation** : Exportation des automates au format `.dot` pour une visualisation avec Graphviz.  
- **Description des automates** : Affichage des détails d'un automate.  

## Prérequis  

- **Java** : Version récente (JDK 17 ou supérieur recommandé).


## Installation  
1. **Compiler le projet et générer le fichier JAR**  
```bash
./gradlew clean build
```
Le fichier `.jar` généré se trouvera dans le répertoire courant.  

2. **Exécuter l'application**  
```bash
java -jar nom-du-fichier.jar
```

## Utilisation  
Après avoir lancé le programme, une interface en ligne de commande similaire à un shell Linux apparaîtra. Vous pouvez utiliser les commandes suivantes :  

- **analyze** : Permet de tester la validité d'une expression.  
- **describe** : Affiche une description détaillée d'un automate.  
- **export** : Exporte un automate au format `.dot`, compatible avec Graphviz.  
- **help** : Liste les commandes disponibles.  
- **clear** : Nettoie le terminal.  
- **exit** : Quitte l'application.  

## Auteurs  
- **AHAMADA Ibrahim**  
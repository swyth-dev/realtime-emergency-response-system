# 📊 Tests de performance avec Apache JMeter

Ce dépôt contient un fichier de test **JMeter** (`.jmx`) destiné à effectuer des tests de performance sur une application ou une API. Ce guide vous explique comment installer JMeter et exécuter le scénario de test fourni.

## 🔧 Prérequis

- Java JDK 8 ou supérieur installé sur votre machine
- Système d’exploitation compatible : Windows, macOS, Linux

## 🧪 Installation de JMeter

1. Télécharger Apache JMeter depuis le site officiel :  
   👉 [https://jmeter.apache.org/download_jmeter.cgi](https://jmeter.apache.org/download_jmeter.cgi)

2. Extraire l'archive téléchargée dans un dossier de votre choix.

3. Accédez au dossier `bin` à l'intérieur du dossier extrait.

4. Lancez JMeter :
   - Sous **Windows** : double-cliquez sur `jmeter.bat`
   - Sous **macOS/Linux** : exécutez `./jmeter` dans un terminal

> 💡 Assurez-vous que la variable d'environnement `JAVA_HOME` est bien définie.

## 📂 Utilisation du fichier JMeter

1. Ouvrez JMeter (interface graphique).

2. Allez dans `Fichier` > `Ouvrir` et sélectionnez le fichier `.jmx` situé dans ce dépôt (dans le dossier `tests` ou un autre dossier selon votre structure).

3. Adaptez les paramètres si nécessaire (URL de l'API, nombre d'utilisateurs, etc.).

4. Cliquez sur le bouton **Démarrer** (flèche verte en haut) pour lancer le test.

## 📝 Résultats

- Vous pouvez ajouter des éléments tels que des `Rapports`, `Table de résultats`, ou des `Graphiques` pour visualiser les performances.
- Pour exporter les résultats :
  - Cliquez-droit sur un élément de rapport > `Enregistrer sous`

## 🚀 Exécution en ligne de commande (optionnel)

Vous pouvez aussi exécuter le test sans l'interface graphique :

```bash
jmeter -n -t chemin/vers/le/fichier.jmx -l résultats.jtl

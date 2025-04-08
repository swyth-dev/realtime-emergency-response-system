# Système de Réponse d'Urgence en Temps Réel - MedHead Enterprise (Proof of Concept)

> Ce dépôt GitHub contient une preuve de concept basée sur une architecture orientée événements et microservices, dans le cadre de la mise en place d’un système de réponse d’urgence en temps réel pour le consortium MedHead.  
>
> Un consortium composé de quatre entreprises leaders s’est formé pour mutualiser leurs efforts, données, applications et feuilles de route, afin de développer une plateforme de nouvelle génération, centrée sur le patient. Celle-ci vise à améliorer les soins de base tout en étant réactive, opérationnelle en temps réel et capable de prendre des décisions critiques en situation d’urgence, en tenant compte de toutes les données disponibles.

## Diagramme d'architecture



> Note : Dans le cadre des développements, une seule base de données est démarrée, avec des schémas différents initalisé par chaque microservice.

## 📁 Structure du projet

Le dépôt est organisé de la manière suivante :

```bash
├── .github/workflows/                # Fichiers de configuration CI/CD (GitHub Actions)
├── backend/                          # Code source backend (services, domaine, API, etc.)
├── doc/                              # Documentation du projet
├── frontend/realtime-emergency-app/ # Application frontend (React, Angular, etc.)
├── jmeter/                           # Scénarios de test de performance JMeter
├── .gitignore                        # Fichiers à ignorer par Git
├── LICENSE                           # Licence du projet
├── README.md                         # Fichier de documentation principal
├── compose.yml                       # Configuration Docker Compose pour les microservices
```

## ⚙️ Configuration de votre environnement local

### Prérequis

Afin de démarrer vos développements, vous devez avoir :
- **Java 21**
- **Maven** 
- **Node 20** et **Angular CLI**
- **Docker Desktop** et **Docker compose** d'intallé
- Un **IDE** (Intellij, VsCode) : nous recommandons IntelliJ.
- Optionnel :
   - Apache JMeter. Reportez vous à [la documentation en lien](./jmeter/README.md).
   - Un client d'API ([Bruno](https://www.usebruno.com/), [Postman](https://www.postman.com/), [Insomnia](https://insomnia.rest/download)). Les collections Bruno et Postman sont disponibles [ici](./docs).

Clonez ce projet sur votre machine.

### 📦 Démarrer les composants liés aux microservices

Pour stocker les données de manière persistante et permettre à l’application de démarrer correctement, ainsi qu'assurer la communication des évènements, vous devez démarrer certains composants :
- **PostgreSQL** : Système de gestion de base de donnée relationelle. Pour cette PoC, une seule DB est démarrée, et chaque microservice utilise son propre schéma. En production, chaque micro service utilise sa propre instance de DB
- **Kafka** : Plateforme de messagerie distribuée qui permet aux services de communiquer en temps réel via des événements.
- **Kafka UI** : Interface web pour superviser les brokers Kafka, et notamment les événements liés à la réservation de lit.

1. [**Installez Docker Desktop**](https://docs.docker.com/desktop/) sur votre machine.
2. (**Optionnel**) Effacez les volumes locaux de persistance :
   - `rm -rf db_data:/var/lib/postgresql/data` pour les données relationelles.
   - `rm -rf kafka_data:/var/lib/kafka/data` pour les données d'évènements.
4. Exécutez la commande `docker compose up` depuis la racine du projet pour démarrer les composants :

> 💡 Les microservices ayant besoin d'une base de données relationelle ne démarreront pas si la base de bonnée n'est pas préalablement démarrée.

### Démarrer les microservices

Pour démarrer les microservices de manière pratique : 
1. Ouvrez le dossier `backend` dans votre IDE IntelliJ
2. Depuis la fenêtre **Services**, lancez 1 à 1 vos microservices en respectant l'odre de démarrage :
   1. Discovery Service
   2. Hospital & Emergency Service (peu importe le sens)
   3. Gateway Service

> Note : De cette manière, les services vont s'enregistrer aurpès du Discovery Server et l'API Gateway saura router correctement les requêtes entrantes

### Démarrer l'application frontend Anuglar

Pour démarrer l'application Angular, placez vous dans le dossier d'application, installez les packages et lancez l'application : 

```bash
cd ./frontend/realtime-emergency-app/
npm i
ng serve 
```

## 🧱 Gestion des migrations de données avec Liquibase
Nous utilisons [**Liquibase**](https://www.liquibase.com/) pour versionner les changements de structure, gérer les migrations de données, et peupler la base avec des données de test.

> 💡 Nous utilisons le plugin [IntelliJ JPA Buddy](https://plugins.jetbrains.com/plugin/15075-jpa-buddy) pour générer les changelogs à partir des entités JPA, évitant ainsi les scripts SQL manuels.
> Toutes les modifications proviennent directement des entités de chaque domaine.

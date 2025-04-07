# Système de Réponse d'Urgence en Temps Réel - MedHead Enterprise (Proof of Concept)

> Ce dépôt GitHub contient une preuve de concept basée sur une architecture orientée événements et microservices, dans le cadre de la mise en place d’un système de réponse d’urgence en temps réel pour le consortium MedHead.  
>
> Un consortium composé de quatre entreprises leaders s’est formé pour mutualiser leurs efforts, données, applications et feuilles de route, afin de développer une plateforme de nouvelle génération, centrée sur le patient. Celle-ci vise à améliorer les soins de base tout en étant réactive, opérationnelle en temps réel et capable de prendre des décisions critiques en situation d’urgence, en tenant compte de toutes les données disponibles.

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

Avant toute chose, clonez ce projet sur votre machine.

### 📦 Démarrer les composants liés aux microservices

Pour stocker les données de manière persistante et permettre à l’application de démarrer correctement, ainsi qu'assurer la communication des évènements, vous devez démarrer certains composants :
- PostgreSQl : Système de gestion de base de donnée relationelle. Pour cette PoC, une seule DB est démarrée, et chaque microservice utilise son propre schéma. En production, chaque micro service utilise sa propre instance de DB
- Kafka : Plateforme de messagerie distribuée qui permet aux services de communiquer en temps réel via des événements.
- Kafka UI : Interface web pour superviser les brokers Kafka, et notamment les événements liés à la réservation de lit.

1. [**Installez Docker Desktop**](https://docs.docker.com/desktop/) sur votre machine.

2. Exécutez la commande `docker compose up` depuis la racine du projet pour démarrer les composants :

## 🧱 Gestion des migrations de données avec Liquibase
Nous utilisons Liquibase pour versionner les changements de structure, gérer les migrations de données, et peupler la base avec des données de test.

> 💡 Nous utilisons le plugin [IntelliJ JPA Buddy](https://plugins.jetbrains.com/plugin/15075-jpa-buddy) pour générer les changelogs à partir des entités JPA, évitant ainsi les scripts SQL manuels.
> Toutes les modifications proviennent directement du modèle de domaine.

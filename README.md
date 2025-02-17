# MedHead Enterprise Real-Time Emergency Response System Proof of Concept
> This Github repository own a Proof of Concept about Event-Driven & Microservices Architecture, as part of the implementation of a real-time emergency response system for the MedHead Consortium. 
>
> A consortium of four leading companies has come together to consolidate the efforts, data, applications and roadmaps of each to develop a next-generation, patient-centric platform capable of improving the basic care offered, while being responsive, operational in real time and capable of making decisions in emergency situations, taking all data into account.

## Setup your local environment

First and foremost, clone this project. 

### Run a Docker PostgreSQL container

Setup a postgres container to have a database and store persisted data. Otherwise, application won't start.

1. [**Install Docker desktop**](https://docs.docker.com/desktop/) on your machine.

2. Copy and paste this command in your terminal to run a functional container and match this project local config : 

```shell
docker run --rm -d --name medhead-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -p 5432:5432 postgres:latest
```

### Run manually Liquibase Database migration & seeding (For now)

To versioning data changes, handle data migrations and test data seeding, we use the broadly adopted Liquibase tool.

We use [JPA Buddy IntelliJ Plugin](https://plugins.jetbrains.com/plugin/15075-jpa-buddy) to [create changelog from JPA Entities](https://www.jetbrains.com/help/idea/jpa-buddy-database-versioning.html#ddl-by-entities) and avoid manual scripts. **All change comes from our domain model**.

At this time, you need to do some actions :

- In IntelliJ IDEA, setup the datasource for the project. you can copy [app properties for URl, user/password](https://github.com/swyth-dev/realtime-emergency-response-system/blob/d723e64aa94af62ffbe5eed8cecb03ac7e4d66e6/hospital-service/src/main/resources/application.yaml)  
- Create a new postgres schema `hospital-service`
- Now you can run Liquibase Update. it will check db changelog, create tables and columns, and seed database from [data CSV files](https://github.com/swyth-dev/realtime-emergency-response-system/blob/52221e1ba7a20c39e5829a550d8d92c67f38f217/hospital-service/src/main/resources/db/changelog/data).

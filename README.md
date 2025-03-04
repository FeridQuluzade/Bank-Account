# bank-account
Java Microservices: CQRS &amp; Event Sourcing with Kafka

                        ---------------Functionality of code down below---------------

Handle commands and raise events.

Use the mediator pattern to implement command and query dispatchers.

Create and change the state of an aggregate with event messages.

Implement an event store / write database in MongoDB.

Create a read database in MySQL.

Apply event versioning.

Implement optimistic concurrency control.

Produce events to Apache Kafka.

Consume events from Apache Kafka to populate and alter the read database.

Replay the event store and recreate the state of the aggregate.

Separate read and write concerns.

Structure your code using Domain-Driven-Design best practices.

Replay the event store to recreate the entire read database.

Replay the event store to recreate the entire read database into a different database type - PostgreSQL.


                        ---------------All Docker commands and dependencies---------------

#1. Java Development Kit (JDK)

OpenJDK:
https://openjdk.java.net/install/  

Oracle JDK:
https://www.oracle.com/java/technologies/javase-downloads.html

Once installed, check Java version:
> java -version

#2. Maven

Download from:
https://maven.apache.org/download.cgi

Once installed, check Maven version:
> mvn -v OR mvn --version

#3. IDE or Code Editor

IntelliJ IDEA:
https://www.jetbrains.com/idea/download/

NetBeans IDE:
https://netbeans.apache.org/download/index.html

Eclipse:
https://www.eclipse.org/downloads/

VS Code:
https://code.visualstudio.com/download

#4. Postman

Download from:
https://www.postman.com/downloads/

#5. Docker

Download for Mac or Windows:
https://www.docker.com/products/docker-desktop

Download for Linux Ubuntu:
https://docs.docker.com/engine/install/ubuntu/

Download for Linux Debian:
https://docs.docker.com/engine/install/debian/

Download for Linux CentOS:
https://docs.docker.com/engine/install/centos/

Download for Linux Fedora:
https://docs.docker.com/engine/install/fedora/

Once installed, check Docker version:
> docker --version

#6. Create Docker Network - techbankNet 

docker network create --attachable -d bridge techbankNet

#7. Install or init docker compose 

https://docs.docker.com/compose/install

#8. Apache Kafka

Create docker-compose.yml file with contents:

version: "3.4"

services:
  zookeeper:
    image: bitnami/zookeeper
    restart: always
    ports:
      - "2181:2181"
    volumes:
      - "zookeeper_data:/bitnami"
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
  kafka:
    image: bitnami/kafka
    ports:
      - "9092:9092"
    restart: always
    volumes:
      - "kafka_data:/bitnami"
    environment:
      - KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
      - KAFKA_LISTENERS=PLAINTEXT://:9092
      - KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092
    depends_on:
      - zookeeper

volumes:
  zookeeper_data:
    driver: local
  kafka_data:
    driver: local
    

The run by executing the following command:

> docker-compose up -d

#9. MongoDB

Run in Docker:
docker run -it -d --name mongo-container \
-p 27017:27017 --network techbankNet \
--restart always \
-v mongodb_data_container:/data/db \
mongo:latest

Download Client Tools – Robo 3T:
https://robomongo.org/download

#9. MySQL

Run in Docker:
docker run -it -d --name mysql-container \
-p 3306:3306 --network techbankNet \
-e MYSQL_ROOT_PASSWORD=techbankRootPsw \
--restart always \
-v mysql_data_container:/var/lib/mysql  \
mysql:latest

Client tools in Docker – Adminer:
docker run -it -d --name adminer \
-p 8080:8080 --network techbankNet \
 -e ADMINER_DEFAULT_SERVER=mysql-container \
--restart always adminer:latest

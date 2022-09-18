# DPMP - boost data stream performance
## Table of contents
* [Overview](#overview)
* [Architecture Overview](#architecture-overview)
* [Technologies](#technologies)
* [Setup](#setup)

## Overview
DPMP is a master thesis work consisting in a sensitive analysis for performance optimization. Here you can find the architecture of the project:

## Architecture Overview
<img src="https://user-images.githubusercontent.com/45661520/190897235-ff8b18ea-318b-4065-a7f7-f6c6d909b7bf.png">


	
## Technologies
Project is created with:
* JDK: 17.0.1
* Spring-Framework: 2.6.2
* Maven: 3.6.3
* Docker Engine: 20.10.8
* Python: 3.8
* Docker Compose: 1.29.2
* Docker Desktop: 4.1.1
* MongoDB: 5.0.10
* Mysql: 8.0 
* Prometheus: 
	
## Setup
To run this project, you need to install Docker and setup these containers: 
* MongoDB
* Mysql
* Prometheus

### Mysql

```
$ docker run --name mysqldb -e MYSQL_ROOT_PASSWORD="YOUR-ROOT-PSW" -e MYSQL_DATABASE="DB-NAME" -e MYSQL_USER="USER-NAME" MYSQL_PASSWORD="USER-PSW" -p 3307:3307 -it mysql:8.0
$ docker exec -it mysqldb  /bin/bash
$ mysql -u root -p
$ grant all on DB-NAME.* to 'USER-NAME'@'%'; -- Gives all privileges to the new user on the newly created database
```

### MongoDb
```
$ docker run --name mymongo -d mongo   
```

### Prometheus
```
$ docker run \
$    -p 9090:9090 \
$    -v /path/to/prometheus.yml:/etc/prometheus/prometheus.yml \
$    prom/prometheus
```

**_NOTE:_**  You can find an example of **prometheus.yml** config file in the root folder of the repository

### DPMP spring project

Edit Docker compose file, adding in mysql config block this lines:
```
 mysqldb:
    container_name: mysqldb
    image: mysql:8.0
    ports:
      - 3307:3306
    environment:
      - MYSQL_ROOT_PASSWORD=<YOUR-ROOT-PSW>
      - MYSQL_DATABASE=<DB-NAME>
      - MYSQL_USER=<USER-NAME>
      - MYSQL_PASSWORD=<USER-PSW>
```
Let's open a terminal and change your directory in the root project folder. Inside DPMP project you have already a Dockerfile configured. You need to only execute this line:

```
$ docker build -t DPMP .
```
Now you have builded DPMP image and you are ready to execute it with docker compose. Let's execute this line in root project folder:

```
$ docker compose up
```

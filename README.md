# kafka-spring-sample

## build

```shell
# $ docker compose down --remove-orphans && rm -rf .docker
$ docker-compose up -d

$ docker-compose ps

# build all modules
$ ./gradlew clean build
# BUILD SUCCESSFUL in 9s

$ ls -l order-notification-consumer/build/libs/order-notification-consumer-0.0.1-SNAPSHOT.jar
-rw-r--r--  1 kiyotakeshi  staff  25780150 Dec 14 11:57 order-notification-consumer/build/libs/order-notification-consumer-0.0.1-SNAPSHOT.jar

$ ls -l order-producer/build/libs/order-producer-0.0.1-SNAPSHOT.jar 
-rw-r--r--  1 kiyotakeshi  staff  35163646 Dec 14 11:57 order-producer/build/libs/order-producer-0.0.1-SNAPSHOT.jar

$ ls -l order-rdb-consumer/build/libs/order-rdb-consumer-0.0.1-SNAPSHOT.jar 
-rw-r--r--  1 kiyotakeshi  staff  47071095 Dec 14 11:57 order-rdb-consumer/build/libs/order-rdb-consumer-0.0.1-SNAPSHOT.jar
```

## run

![confirm the operation](./kafka-spring-sample.gif)

```shell
# first, you should run producer in order to create topic
$ java -jar order-producer/build/libs/order-producer-0.0.1-SNAPSHOT.jar

$ java -jar order-notification-consumer/build/libs/order-notification-consumer-0.0.1-SNAPSHOT.jar

$ java -jar order-rdb-consumer/build/libs/order-rdb-consumer-0.0.1-SNAPSHOT.jar 
```

you can use [postman collection](./postman)

## check broker from host

```shell
# wherever you want
$ cd /tmp/ && wget https://dlcdn.apache.org/kafka/3.0.0/kafka-3.0.0-src.tgz

$ tar zxvf kafka-3.0.0-src.tgz

$ cd kafka-3.0.0-src

$ ./gradlew jar -PscalaVersion=2.13.6

$ ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

$ ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic order-events

$ ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic order-events
```

## confirm email [http://localhost:8025](http://localhost:8025)

# kafka-spring-sample

## build

```shell
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

## check broker from host

```shell
$ cd /tmp/ && wget https://dlcdn.apache.org/kafka/3.0.0/kafka-3.0.0-src.tgz

$ tar zxvf kafka-3.0.0-src.tgz

$ ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

$ ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic order-events

$ ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic order-events
```


- access ([http://localhost:8025](http://localhost:8025)) and clicked mail content link

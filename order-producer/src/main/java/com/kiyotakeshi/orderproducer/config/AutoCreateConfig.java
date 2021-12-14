package com.kiyotakeshi.orderproducer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AutoCreateConfig {

    // if you create manual
    // $ ./bin/kafka-topics.sh --create --topic order-events --bootstrap-server localhost:9092 --replication-factor 3 --partitions 3
    @Bean
    public NewTopic orderEvents(){
        return TopicBuilder.name("order-events")
                .partitions(3)
                .replicas(3)
                .build();
    }
    // @see README.md
    // $ ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --list | grep order
    // $ ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic order-events
}

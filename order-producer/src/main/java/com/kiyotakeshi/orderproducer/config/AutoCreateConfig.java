package com.kiyotakeshi.orderproducer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AutoCreateConfig {

    @Bean
    public NewTopic libraryEvents(){
        return TopicBuilder.name("order-events")
                .partitions(3)
                .replicas(3)
                .build();
    }
    // @see README.md
    // $ ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --list | grep library
    // $ ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic order-events
}

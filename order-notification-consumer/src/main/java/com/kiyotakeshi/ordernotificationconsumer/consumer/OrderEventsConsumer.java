package com.kiyotakeshi.ordernotificationconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kiyotakeshi.ordernotificationconsumer.service.OrderEventsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class OrderEventsConsumer {

    private final OrderEventsService orderEventsService;

    // use ConcurrentKafkaListenerContainerFactory default
    // @see
    // org/springframework/boot/autoconfigure/kafka/KafkaAutoConfiguration.java -> KafkaAnnotationDrivenConfiguration
    // org/springframework/boot/autoconfigure/kafka/KafkaAnnotationDrivenConfiguration.java#kafkaListenerContainerFactory
    @KafkaListener(topics = {"order-events"})
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord: {} ", consumerRecord);
        orderEventsService.processOrderEvent(consumerRecord);
    }
}

package com.kiyotakeshi.orderproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiyotakeshi.orderproducer.domain.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class OrderEventProducer {

    private final String TOPIC = "order-events";

    private final KafkaTemplate<Integer, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public OrderEventProducer(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public ListenableFuture<SendResult<Integer, String>> sendOrderEvent(OrderEvent orderEvent) throws JsonProcessingException {
        Integer key = orderEvent.getOrderEventId();
        String value = objectMapper.writeValueAsString(orderEvent);
        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(TOPIC, key, value);

        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
        return listenableFuture;
    }

    private void handleFailure(Integer key, String value, Throwable ex) {
        log.error("Error sending the message for the key: {} and the valud is {}. And the exception is {}", key, value, ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Message send successfully for the key : {} and the value is {}, partition is {}", key, value, result.getRecordMetadata().partition());
    }
}

package com.kiyotakeshi.orderrdbconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiyotakeshi.orderrdbconsumer.domain.OrderEvent;
import com.kiyotakeshi.orderrdbconsumer.repository.OrderEventsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class OrderEventsService {

    private final ObjectMapper objectMapper;

    private final OrderEventsRepository orderEventsRepository;

    public void processOrderEvent(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        var orderEvent = objectMapper.readValue(consumerRecord.value(), OrderEvent.class);
        log.info("orderEvent: {}", orderEvent);

        switch (orderEvent.getOrderEventType()) {
            case NEW:
                // save
                save(orderEvent);
                break;
            case UPDATE:
                // update
                validate(orderEvent);
                save(orderEvent);
                break;
            default:
                log.info("Invalid Order Event Type");
        }
    }

    private void validate(OrderEvent orderEvent) {
        if (orderEvent.getOrderEventId() == null) {
            throw new IllegalArgumentException("Order Event Id is missing");
        }

        Optional<OrderEvent> orderEventOptional = orderEventsRepository.findById(orderEvent.getOrderEventId());
        if (!orderEventOptional.isPresent()) {
            throw new IllegalArgumentException("Not a valid Order Event");
        }
        log.info("Validation is successful for the Order Event: {}", orderEventOptional.get());
    }

    private void save(OrderEvent orderEvent) {
        orderEvent.getOrder().setOrderEvent(orderEvent);
        orderEventsRepository.save(orderEvent);
        log.info("Successfully Persisted the Order Event {}", orderEvent);
    }
}

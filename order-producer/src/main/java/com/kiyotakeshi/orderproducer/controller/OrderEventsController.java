package com.kiyotakeshi.orderproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kiyotakeshi.orderproducer.domain.OrderEvent;
import com.kiyotakeshi.orderproducer.domain.OrderEventType;
import com.kiyotakeshi.orderproducer.producer.OrderEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order-events")
@Slf4j
public class OrderEventsController {

    private OrderEventProducer orderEventProducer;

    public OrderEventsController(OrderEventProducer orderEventProducer) {
        this.orderEventProducer = orderEventProducer;
    }

    @PostMapping
    public ResponseEntity<OrderEvent> postOrderEvent(@RequestBody @Valid OrderEvent orderEvent)
            throws JsonProcessingException {

        log.info("invoke kafka producer");
        orderEvent.setOrderEventType(OrderEventType.NEW);
        orderEventProducer.sendOrderEvent(orderEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderEvent);
    }

    @PutMapping
    public ResponseEntity<?> putOrderEvent(@RequestBody @Valid OrderEvent orderEvent)
            throws JsonProcessingException {

        if (orderEvent.getOrderEventId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass the OrderEventId");
        }

        orderEvent.setOrderEventType(OrderEventType.UPDATE);
        // if you want to send a message to same partition, make sure you pass the same key
        orderEventProducer.sendOrderEvent(orderEvent);
        return ResponseEntity.status(HttpStatus.OK).body(orderEvent);
    }
}

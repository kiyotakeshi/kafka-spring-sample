package com.kiyotakeshi.ordernotificationconsumer.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderEvent {

    private Integer orderEventId;

    private OrderEventType orderEventType;

    private Order order;
}

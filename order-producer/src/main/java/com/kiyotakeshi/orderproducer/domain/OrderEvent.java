package com.kiyotakeshi.orderproducer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderEvent {
    private Integer orderEventId;
    private OrderEventType orderEventType;

    @NotNull
    @Valid
    private Order order;
}

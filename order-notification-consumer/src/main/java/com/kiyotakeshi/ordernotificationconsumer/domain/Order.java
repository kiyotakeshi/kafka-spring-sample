package com.kiyotakeshi.ordernotificationconsumer.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {

    private Integer id;
    private String name;
    private Integer price;

    @ToString.Exclude
    private OrderEvent orderEvent;
}

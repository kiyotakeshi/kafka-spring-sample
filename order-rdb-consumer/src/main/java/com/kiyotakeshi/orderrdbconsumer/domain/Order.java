package com.kiyotakeshi.orderrdbconsumer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private Integer id;
    private String name;
    private Integer price;

    @OneToOne
    @JoinColumn(name = "orderEventId")
    private OrderEvent orderEvent;
}

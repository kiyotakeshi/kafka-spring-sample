package com.kiyotakeshi.orderrdbconsumer.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "order_events")
public class OrderEvent {

    @Id
    @GeneratedValue
    private Integer orderEventId;

    @Enumerated(EnumType.STRING)
    private OrderEventType orderEventType;

    @OneToOne(mappedBy = "orderEvent", cascade = {CascadeType.ALL})
    @ToString.Exclude
    private Order order;
}

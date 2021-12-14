package com.kiyotakeshi.orderrdbconsumer.repository;

import com.kiyotakeshi.orderrdbconsumer.domain.OrderEvent;
import org.springframework.data.repository.CrudRepository;

public interface OrderEventsRepository extends CrudRepository<OrderEvent, Integer> {
}

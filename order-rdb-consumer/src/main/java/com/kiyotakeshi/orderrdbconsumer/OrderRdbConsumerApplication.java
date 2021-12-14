package com.kiyotakeshi.orderrdbconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class OrderRdbConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderRdbConsumerApplication.class, args);
	}

}

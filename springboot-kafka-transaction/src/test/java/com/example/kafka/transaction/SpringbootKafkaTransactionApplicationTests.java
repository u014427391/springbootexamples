package com.example.kafka.transaction;

import com.example.kafka.transaction.model.Order;
import com.example.kafka.transaction.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SpringbootKafkaTransactionApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
        Order order = new Order("error", "iPhone", 1); // 触发异常

        assertThrows(RuntimeException.class, () -> orderService.createOrder(order));


    }

}

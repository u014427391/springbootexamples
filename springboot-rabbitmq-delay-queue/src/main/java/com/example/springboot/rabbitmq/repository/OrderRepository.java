package com.example.springboot.rabbitmq.repository;

import com.example.springboot.rabbitmq.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    /**
     * 根据订单ID查询订单
     * @param orderId 订单ID
     * @return 订单对象
     */
    Optional<Order> findByOrderId(String orderId);
}
package com.example.springboot.rabbitmq.service;

import com.example.springboot.rabbitmq.dto.OrderDTO;
import com.example.springboot.rabbitmq.entity.Order;
import com.example.springboot.rabbitmq.enums.OrderStatus;

import java.util.Optional;

public interface OrderService {

    /**
     * 创建订单
     * @param orderDto 订单信息
     * @return 创建的订单
     */
    Order createOrder(OrderDTO orderDto);

    /**
     * 根据订单ID查询订单
     * @param orderId 订单ID
     * @return 订单信息
     */
    Optional<Order> getOrderById(String orderId);

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param status  新状态
     * @return 更新后的订单
     */
    Order updateOrderStatus(String orderId, OrderStatus status);

    /**
     * 支付订单
     * @param orderId 订单ID
     * @return 支付结果
     */
    boolean payOrder(String orderId);

    /**
     * 关闭超时未支付订单
     * @param orderId 订单ID
     */
    void closeOrder(String orderId);

    /**
     * 发送订单到延迟队列
     * @param orderId 订单ID
     * @param delayTime 延迟时间(毫秒)
     */
    void sendOrderToDelayQueue(String orderId, long delayTime);
}
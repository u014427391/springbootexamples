package com.example.springboot.rabbitmq.service.impl;


import com.example.springboot.rabbitmq.config.RabbitMQConfig;
import com.example.springboot.rabbitmq.dto.OrderDTO;
import com.example.springboot.rabbitmq.entity.Order;
import com.example.springboot.rabbitmq.enums.OrderStatus;
import com.example.springboot.rabbitmq.repository.OrderRepository;
import com.example.springboot.rabbitmq.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDto) {
        // 幂等性校验
        if (redisTemplate.hasKey("ORDER_CREATE_" + orderDto.getRequestId())) {
            throw new IllegalArgumentException("重复请求");
        }
        redisTemplate.opsForValue().set("ORDER_CREATE_" + orderDto.getRequestId(), "1", 5, TimeUnit.MINUTES);


        // 设置订单初始状态为未支付
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.UNPAID.getCode());
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        order.setUserId(orderDto.getUserId());
        order.setAmount(orderDto.getAmount());

        // 保存订单到数据库
        Order savedOrder = orderRepository.save(order);
        log.info("订单创建成功，订单ID：{}", savedOrder.getOrderId());

        // 发送订单到延迟队列，设置延迟30分钟
        long delayTime = 1 * 60 * 1000; // 30分钟
        sendOrderToDelayQueue(savedOrder.getOrderId(), delayTime);

        return savedOrder;
    }

    @Override
    public Optional<Order> getOrderById(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(String orderId, OrderStatus status) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("订单不存在，ID：" + orderId));

        order.setStatus(status.getCode());
        order.setUpdateTime(LocalDateTime.now());

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public boolean payOrder(String orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("订单不存在，ID：" + orderId));

        // 检查订单状态
        if (order.getStatus() != OrderStatus.UNPAID.getCode()) {
            log.warn("订单状态异常，无法支付，订单ID：{}，当前状态：{}",
                    orderId, OrderStatus.fromCode(order.getStatus()));
            return false;
        }

        // 更新订单状态为已支付
        order.setStatus(OrderStatus.PAID.getCode());
        order.setUpdateTime(LocalDateTime.now());
        orderRepository.save(order);

        log.info("订单支付成功，订单ID：{}", orderId);
        return true;
    }

    @Override
    @Transactional
    public void closeOrder(String orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("订单不存在，ID：" + orderId));

        // 检查订单状态
        if (order.getStatus() != OrderStatus.UNPAID.getCode()) {
            log.info("订单无需关闭，订单ID：{}，当前状态：{}",
                    orderId, OrderStatus.fromCode(order.getStatus()));
            return;
        }

        // 更新订单状态为已关闭
        order.setStatus(OrderStatus.CLOSED.getCode());
        order.setUpdateTime(LocalDateTime.now());
        orderRepository.save(order);

        log.info("订单已关闭，订单ID：{}", orderId);
    }

    @Override
    public void sendOrderToDelayQueue(String orderId, long delayTime) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.ORDER_ROUTING_KEY,
                orderId,
                message -> {
                    message.getMessageProperties().setExpiration(String.valueOf(delayTime));
                    return message;
                }
        );

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                // 消息发送失败，执行数据库回滚或补偿逻辑
                orderRepository.deleteById(orderId);
                log.error("消息发送失败，原因：{}", cause);
            }
        });

        log.info("订单已发送到延迟队列，订单ID：{}，延迟时间：{}毫秒", orderId, delayTime);
    }
}

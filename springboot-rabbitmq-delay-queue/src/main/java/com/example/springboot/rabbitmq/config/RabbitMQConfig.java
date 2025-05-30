package com.example.springboot.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String ORDER_PROCESS_QUEUE = "order.process.queue";
    public static final String ORDER_ROUTING_KEY = "order.routing.key";

    public static final String ORDER_DLX_EXCHANGE = "order.dlx.exchange";
    public static final String ORDER_DELAY_QUEUE = "order.delay.queue";
    public static final String ORDER_DLX_ROUTING_KEY = "order.dlx.routing.key";

    // 设置订单交换机类
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE, true, false);
    }

    // 配置处理队列，设置TTL和DLX交换机
    @Bean
    public Queue orderProcessQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", ORDER_DLX_EXCHANGE); // 死信交换机
        args.put("x-message-ttl", 60000); // 设置消息过期时间（毫秒）
        return new Queue(ORDER_PROCESS_QUEUE, true, false, false, args);
    }

    // 配置延迟队列
    @Bean
    public Queue orderDelayQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", ORDER_DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", ORDER_DLX_ROUTING_KEY);
        args.put("x-max-priority", 10); // 设置队列优先级
        args.put("x-message-ttl", 60000); // 设置消息过期时间（毫秒）
        return new Queue(ORDER_DELAY_QUEUE, true, false, false, args);
    }

    // 绑定延迟队列到订单交换机
    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(orderDelayQueue()).to(orderExchange()).with(ORDER_ROUTING_KEY);
    }

    // 配置DLX交换机
    @Bean
    public DirectExchange orderDlxExchange() {
        return new DirectExchange(ORDER_DLX_EXCHANGE, true, false);
    }

    // 绑定处理队列到DLX交换机
    @Bean
    public Binding processQueueBinding() {
        return BindingBuilder.bind(orderProcessQueue()).to(orderDlxExchange()).with(ORDER_DLX_ROUTING_KEY);
    }

}
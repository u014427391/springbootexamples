package com.example.springboot.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

import static org.springframework.amqp.core.BindingBuilder.bind;
import static org.springframework.amqp.core.QueueBuilder.durable;

@Configuration
public class RabbitMQConfig {

    // 订单相关交换机、路由键和队列常量
    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String ORDER_ROUTING_KEY = "order.routing.key";
    public static final String ORDER_PROCESS_QUEUE = "order.process.queue";

    // DLX交换机、路由键和队列常量
    public static final String ORDER_DLX_EXCHANGE = "order.dlx.exchange";
    public static final String ORDER_DLX_ROUTING_KEY = "order.dlx.routing.key";
    public static final String ORDER_DELAY_QUEUE = "order.delay.queue";

    // 错误处理相关交换机、路由键和队列常量
    public static final String ERROR_EXCHANGE = "error.exchange";
    public static final String ERROR_QUEUE = "error.queue";
    public static final String ERROR_ROUTING_KEY = "error.routing.key";

    /**
     * 声明订单交换机，将错误消息路由到错误交换机
     */
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE, true, false,
                Collections.singletonMap("alternate-exchange", ERROR_EXCHANGE));
    }

    /**
     * 声明DLX交换机
     */
    @Bean
    public DirectExchange orderDlxExchange() {
        return new DirectExchange(ORDER_DLX_EXCHANGE, true, false);
    }

    /**
     * 声明错误交换机，使用扇形交换机将消息广播到所有绑定的队列
     */
    @Bean
    public FanoutExchange errorExchange() {
        return new FanoutExchange(ERROR_EXCHANGE, true, false);
    }

    /**
     * 声明处理队列
     */
    @Bean
    public Queue orderProcessQueue() {
        return new Queue(ORDER_PROCESS_QUEUE, true);
    }

    /**
     * 声明延迟队列，并绑定到DLX交换机
     */
    @Bean
    public Queue orderDelayQueue() {
        return durable(ORDER_DELAY_QUEUE)
                .withArgument("x-dead-letter-exchange", ORDER_DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", ORDER_DLX_ROUTING_KEY)
                .withArgument("x-max-priority", 10)
                .build();
    }

    /**
     * 声明错误队列
     */
    @Bean
    public Queue errorQueue() {
        return new Queue(ERROR_QUEUE, true);
    }

    /**
     * 绑定延迟队列到订单交换机
     */
    @Bean
    public Binding orderDelayBinding() {
        return bind(orderDelayQueue()).to(orderExchange()).with(ORDER_ROUTING_KEY);
    }

    /**
     * 绑定处理队列到DLX交换机
     */
    @Bean
    public Binding orderProcessBinding() {
        return bind(orderProcessQueue()).to(orderDlxExchange()).with(ORDER_DLX_ROUTING_KEY);
    }

    /**
     * 绑定错误队列到错误交换机
     */
    @Bean
    public Binding errorBinding() {
        return bind(errorQueue()).to(errorExchange());
    }
}
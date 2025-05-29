package com.example.springboot.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 订单交换机
    public static final String ORDER_EXCHANGE = "order.exchange";
    // 订单路由键
    public static final String ORDER_ROUTING_KEY = "order.routing.key";
    // 处理队列
    public static final String ORDER_PROCESS_QUEUE = "order.process.queue";

    // dlx交换机
    public static final String ORDER_DLX_EXCHANGE = "order.dlx.exchange";
    // 路由键
    public static final String ORDER_DLX_ROUTING_KEY = "order.dlx.routing.key";
    // 延时队列
    public static final String ORDER_DELAY_QUEUE = "order.delay.queue";

    // 声明订单交换机
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE, true, false);
    }

    // 声明Dlx交换机
    @Bean
    DirectExchange orderDlxExchange() {
        return new DirectExchange(ORDER_DLX_EXCHANGE, true, false);
    }

    // 声明处理队列
    @Bean
    public Queue orderProcessQueue() {
        return new Queue(ORDER_PROCESS_QUEUE, true);
    }

    // 声明延迟队列并绑定到Dlx交换机
    @Bean
    public Queue orderDelayQueue() {
        return QueueBuilder.durable(ORDER_DELAY_QUEUE)
                .withArgument("x-dead-letter-exchange", ORDER_DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", ORDER_DLX_ROUTING_KEY)
                .build();
    }

    // 绑定延迟队列到订单交换机
    @Bean
    public Binding orderDelayBinding() {
        return BindingBuilder.bind(orderDelayQueue())
                .to(orderExchange())
                .with(ORDER_ROUTING_KEY);
    }

    // 绑定处理队列到Dlx交换机
    @Bean
    public Binding orderProcessBinding() {
        return BindingBuilder.bind(orderProcessQueue())
                .to(orderDlxExchange())
                .with(ORDER_DLX_ROUTING_KEY);
    }


}

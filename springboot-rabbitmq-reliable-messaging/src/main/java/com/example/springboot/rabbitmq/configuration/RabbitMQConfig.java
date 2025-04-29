package com.example.springboot.rabbitmq.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConfig {

    public static final String QUEUE_NAME  = "TestDirectQueue";
    public static final String EXCHANGE_NAME  = "TestDirectExchange";
    public static final String ROUTING_KEY  = "TestDirectRouting";


    @Bean
    public Queue testDirectQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange testDirectExchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(testDirectQueue()).to(testDirectExchange()).with(ROUTING_KEY);
    }

}

package com.example.springboot.rabbitmq.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "flow.control.queue";
    public static final String EXCHANGE_NAME = "flow.control.exchange";
    public static final String ROUTING_KEY = "flow.control.key";

    // 配置队列
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .maxLength(1000)
                .build();
    }

    // 配置交换机
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // 绑定队列和交换机
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    // 配置消息转换器
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 配置RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        
        // 设置mandatory标志，确保消息在无法路由时返回
        rabbitTemplate.setMandatory(true);
        
        // 设置发布确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送成功: {}",  correlationData);
            } else {
                log.warn("消息发送失败: {}",  cause);
            }
        });
        
        // 设置返回回调
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息被退回: {}", new String(message.getBody()));
            log.info("回复码: ", replyCode);
            log.info("回复文本: ", replyText);
            log.info("交换机: ", exchange);
            log.info("路由键: ", routingKey);
        });
        
        return rabbitTemplate;
    }

    // 配置监听器容器工厂
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setConcurrentConsumers(3); // 设置并发消费者数量
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(50); // 设置 QoS
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 手动确认模式
        return factory;
    }
}
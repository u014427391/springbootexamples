package com.example.springboot.rabbitmq.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * RabbitTemplate 配置类
 */
@Component
@Slf4j
public class RabbitTemplateConfig {

    /**
     * 创建 RabbitTemplate Bean
     * @param connectionFactory 连接工厂
     * @return RabbitTemplate 实例
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true); // 设置为 true，以便未路由的消息可以返回

        // 设置 Confirm 回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送到交换机成功，消息ID：{}", correlationData.getId());
            } else {
                log.info("消息发送到交换机失败，消息ID：{}，原因：{}", correlationData.getId(), cause);
            }
        });

        // 设置 Return 回调
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息发送失败，交换机：{}，路由键：{}，原因：{}", exchange, routingKey, replyText);
        });


        return rabbitTemplate;
    }
}
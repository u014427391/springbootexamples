package com.example.springboot.rabbitmq.service;

import com.example.springboot.rabbitmq.configuration.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MessageReceiver {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("接收到消息：{}" , message);
        try {
            // 模拟消息处理逻辑
            Thread.sleep(1000);
            // 手动确认消息
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // 消息处理失败，拒绝消息并重新入队
            channel.basicNack(deliveryTag, false, true);
        }
    }
}

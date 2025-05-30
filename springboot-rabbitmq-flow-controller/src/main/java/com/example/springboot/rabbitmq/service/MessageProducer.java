package com.example.springboot.rabbitmq.service;

import com.example.springboot.rabbitmq.configuration.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class MessageProducer {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final AtomicInteger messageCount = new AtomicInteger(0);

    // 最大发送消息数量，用于模拟流量控制
    private static final int MAX_MESSAGES = 1000;

    // 流量控制标志
    private volatile boolean flowControlEnabled = false;

    public void sendMessage(String message) {
        if (flowControlEnabled) {
            log.warn("流量控制已启用，暂停发送消息");
            return;
        }

        if (messageCount.get() >= MAX_MESSAGES) {
            log.warn("达到最大消息数量，触发流量控制");
            enableFlowControl(5000);
            return;
        }

        // 生成唯一的消息ID
        String correlationId = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(correlationId);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                message,
                correlationData
        );

        // 增加消息计数
        messageCount.incrementAndGet();
        log.info("发送消息:{}, 消息ID：{} ", message, correlationId);

    }

    /**
     * 启用流量控制
     * @param durationMillis 流量控制持续时间（毫秒）
     */
    public void enableFlowControl(long durationMillis) {
        flowControlEnabled = true;
        new Thread(() -> {
            try {
                Thread.sleep(durationMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            flowControlEnabled = false;
            messageCount.set(0);
            log.info("流量控制已禁用");
        }).start();
    }

}

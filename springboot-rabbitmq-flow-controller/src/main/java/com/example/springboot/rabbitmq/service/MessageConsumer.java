package com.example.springboot.rabbitmq.service;


import com.example.springboot.rabbitmq.configuration.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

@Service
@Slf4j
public class MessageConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    @Retryable(value = {IOException.class}, maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2))
    public void receiveMessage(Message message, Channel channel) throws IOException {
        try {
            if (channel == null || !channel.isOpen()) {
                log.warn("Channel is closed or null, unable to process message");
                return;
            }
            // 动态设置预取计数
            channel.basicQos(calculatePrefetchCount());

            String content = new String(message.getBody());
            log.info("接收到消息:{} ", content);

            // 模拟消息处理时间
            Thread.sleep(100);

            // 发送消息确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

            log.info("消息处理完成");
        } catch (Exception e) {
            log.error("处理消息时发生错误: {}", e.getMessage(), e);
            if (channel != null && channel.isOpen()) {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // 失败后重新入队
            }
        }
    }


    // 根据系统负载动态计算预取计数
    private int calculatePrefetchCount() {
        double cpuLoad = getSystemCpuLoad();
        int basePrefetch = 10;
        return (int) Math.max(1, basePrefetch * (1 - cpuLoad));
    }

    // 获取当前系统 CPU 负载
    private double getSystemCpuLoad() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osBean.getSystemLoadAverage() / osBean.getAvailableProcessors();
    }

}
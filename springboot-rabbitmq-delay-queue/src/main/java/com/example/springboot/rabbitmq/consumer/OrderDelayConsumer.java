package com.example.springboot.rabbitmq.consumer;

import com.example.springboot.rabbitmq.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class OrderDelayConsumer {
    private static final String X_DEATH_HEADER = "x-death"; // RabbitMQ Dlx头部

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = "order.process.queue", containerFactory = "manualAckContainerFactory")
    public void processOrderDelay(String orderId, Channel channel, Message message) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("收到延迟消息，订单ID：{}，deliveryTag：{}", orderId, deliveryTag);

        try {
            // 处理订单关闭逻辑
            orderService.closeOrder(orderId);

            // 手动确认消息
            channel.basicAck(deliveryTag, false);
            log.info("订单关闭成功，订单ID：{}，已确认消息", orderId);
        } catch (Exception e) {
            try {
                // 获取重试次数（通过 x-death 头部）
                int retryCount = getRetryCountFromXDeathHeader(message);

                if (retryCount >= 3) { // 最大重试次数设为3次
                    log.error("订单关闭失败，超过最大重试次数（{}次），订单ID：{}，原因：{}", retryCount, orderId, e.getMessage());
                    channel.basicReject(deliveryTag, false); // 拒绝消息，发送到Dlx队列
                } else {
                    log.warn("订单关闭失败，重试次数：{}，订单ID：{}，原因：{}", retryCount, orderId, e.getMessage());
                    channel.basicNack(deliveryTag, false, true); // 重新入队
                }
            } catch (IOException ioException) {
                log.error("消息确认失败，订单ID：{}", orderId, ioException);
            }
        }
    }

    /**
     * 从 x-death 头部获取重试次数
     * @param message RabbitMQ 消息
     * @return 重试次数（首次消费为0，每次重试+1）
     */
    private int getRetryCountFromXDeathHeader(Message message) {
        List<Map<String, Object>> xDeathHeaders = (List<Map<String, Object>>) message.getMessageProperties().getHeaders().get(X_DEATH_HEADER);
        if (xDeathHeaders == null) {
            return 0; // 首次消费，无重试记录
        }
        // x-death 列表中的每个元素代表一次重试记录，size 即为重试次数
        return xDeathHeaders.size();
    }
}
package com.example.springboot.rabbitmq.consumer;

import com.example.springboot.rabbitmq.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDelayConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderDelayConsumer.class);

    @Autowired
    private OrderService orderService;


    @RabbitListener(queues = "order.process.queue")
    public void processOrderDelay(String orderId) {
        logger.info("收到延迟消息，订单ID：{}", orderId);
        try {
            // 处理延迟订单关闭逻辑
            orderService.closeOrder(orderId);
            logger.info("订单关闭成功，订单ID：{}", orderId);
        } catch (Exception e) {
            logger.error("处理订单关闭失败，订单ID：{}", orderId, e);
            // 可以添加重试机制或告警逻辑
        }

    }

}

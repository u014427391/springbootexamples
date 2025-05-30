package com.example.springboot.rabbitmq.service;


import com.example.springboot.rabbitmq.configuration.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        try {
            // 模拟消息处理时间
            Thread.sleep(100);

            System.out.println("接收到消息: " + message);

            // 这里可以添加业务处理逻辑
            processMessage(message);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("消息处理被中断: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("处理消息时发生错误: " + e.getMessage());
            // 可以根据业务需求添加重试或错误处理逻辑
        }
    }

    private void processMessage(String message) {
        // 实际业务处理逻辑
        System.out.println("处理消息: " + message);
        // 例如：更新数据库、调用其他服务等
    }
}
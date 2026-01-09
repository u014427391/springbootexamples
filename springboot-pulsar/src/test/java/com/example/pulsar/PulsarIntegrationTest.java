package com.example.pulsar;

import com.example.pulsar.model.Message;
import com.example.pulsar.service.PulsarProducerService;
import org.apache.pulsar.client.api.PulsarClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class PulsarIntegrationTest {

    @Autowired
    private PulsarProducerService producerService;

    @Test
    void testSendMessage() throws InterruptedException, PulsarClientException {
        CountDownLatch latch = new CountDownLatch(1);
        
        // 创建测试消息
        Message message = new Message();
        message.setContent("Test message from integration test");
        message.setSender("Integration Test");

        // 发送消息
        producerService.send(message);

        // 等待消费者接收消息（设置超时时间）
        boolean received = latch.await(10, TimeUnit.SECONDS);
        assertTrue(received, "Message should be received by consumer");
    }

    @Test
    void testSendMultipleMessages() throws InterruptedException, PulsarClientException {
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            Message message = new Message();
            message.setContent("Test message " + i);
            message.setSender("Integration Test");

            producerService.send(message);
        }

        boolean received = latch.await(15, TimeUnit.SECONDS);
        assertTrue(received, "All messages should be received by consumer");
    }

    @Test
    void contextLoads() {
        // 测试 Spring 上下文是否正常加载
    }
}

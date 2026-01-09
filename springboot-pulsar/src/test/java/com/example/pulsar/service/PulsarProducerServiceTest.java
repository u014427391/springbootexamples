package com.example.pulsar.service;

import com.example.pulsar.model.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.pulsar.core.PulsarTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PulsarProducerServiceTest {

    @Mock
    private PulsarTemplate<Message> pulsarTemplate;

    @InjectMocks
    private PulsarProducerService producerService;

    @Test
    void testSendMessage() throws PulsarClientException {
        // 准备测试数据
        Message message = new Message();
        message.setContent("Test message");
        message.setSender("Test Sender");

        // 模拟 pulsarTemplate.send 方法
        doNothing().when(pulsarTemplate).send(eq("example-topic"), any(Message.class));

        // 执行测试
        producerService.send(message);

        // 验证是否调用了 send 方法
        verify(pulsarTemplate).send(eq("example-topic"), any(Message.class));
    }
}

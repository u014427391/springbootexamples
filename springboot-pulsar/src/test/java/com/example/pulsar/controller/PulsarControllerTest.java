package com.example.pulsar.controller;

import com.example.pulsar.model.Message;
import com.example.pulsar.service.PulsarProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PulsarController.class)
class PulsarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PulsarProducerService producerService;

    @Test
    void testSendMessage() throws Exception {
        // 准备测试数据
        Message message = new Message();
        message.setContent("Test message");
        message.setSender("Test Sender");

        // 模拟 producerService.sendMessage 方法
        doNothing().when(producerService).send(any(Message.class));

        // 执行测试
        mockMvc.perform(post("/api/pulsar/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isOk())
                .andExpect(content().string("Message sent to Pulsar successfully!"));

        // 验证是否调用了 sendMessage 方法
        verify(producerService).send(any(Message.class));
    }
}

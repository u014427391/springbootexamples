package com.example.chatbot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.ai.openai.api-key=test-key"
})
class ChatbotApplicationTests {

    @Test
    void contextLoads() {
        // 验证应用程序上下文是否成功加载
    }

}

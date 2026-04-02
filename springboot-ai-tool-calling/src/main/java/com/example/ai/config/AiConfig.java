package com.example.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring AI 配置类
 */
@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                        你是一个专业、简洁、有用的 AI 助手。
                        请根据工具返回的结果，用自然语言直接回答用户问题。
                        不要返回 JSON、代码块或格式符号，不要说“使用xx函数”，只给最终答案。
                        """)
                .build();
    }

}

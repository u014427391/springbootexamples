package com.example.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("你是一个AI聊天助手")
                .build();
    }

    /**
     * 配置本地内存版的ChatMemory Bean
     */
    @Bean
    public ChatMemory chatMemory() {
        // InMemoryChatMemory是ChatMemory的默认实现，基于本地内存存储对话上下文
        return new InMemoryChatMemory();
    }

}

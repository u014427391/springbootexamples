package com.example.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepositoryDialect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ChatConfig {


    @Bean
    public ChatClient chatClient(ChatClient.Builder builder , @Qualifier("jdbcChatMemory") ChatMemory jdbcChatMemory) {
        return builder.defaultSystem("你是一个AI聊天助手")
                .defaultAdvisors(new SimpleLoggerAdvisor(),
                        // 配置 chat memory advisor
                        MessageChatMemoryAdvisor.builder(jdbcChatMemory).build())
                .build();
    }

    /**
     * 配置 JDBC 聊天记忆存储库
     */
    @Bean
    public ChatMemoryRepository chatMemoryRepository(JdbcTemplate jdbcTemplate) {
        return JdbcChatMemoryRepository.builder()
                .jdbcTemplate(jdbcTemplate)
                .dialect(JdbcChatMemoryRepositoryDialect.from(jdbcTemplate.getDataSource()))
                .build();
    }

    /**
     * 内存存储
     * 数据仅存在于 JVM 内存中，服务重启即丢失
     */
    @Bean
    public ChatMemory inMemoryChatMemory() {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(20)
                .build();
    }

    /**
     * 配置数据库版的ChatMemory
     * @return org.springframework.ai.chat.memory.ChatMemory
     */
    @Bean
    public ChatMemory jdbcChatMemory(ChatMemoryRepository chatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository) // 注入JDBC实现
                .maxMessages(10) // 最大历史消息数
                .build();
    }


//    @Bean
//    public MessageWindowChatMemory messageWindowChatMemoryWithJdbc() {
//        return MessageWindowChatMemory.builder()
//                .chatMemoryRepository(jdbcChatMemoryRepository)
//                .maxMessages(100)
//                .build();
//    }

}

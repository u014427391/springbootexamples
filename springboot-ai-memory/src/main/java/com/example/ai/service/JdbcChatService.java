package com.example.ai.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("jdbcChatService")
public class JdbcChatService extends AbstractAiChatService{

    @Autowired
    @Qualifier("jdbcChatMemory")
    private ChatMemory jdbcChatMemory;

    public JdbcChatService(ChatClient chatClient) {
        super(chatClient);
    }
    @Override
    protected ChatMemory getChatMemory() {
        return jdbcChatMemory;
    }


}

package com.example.ai.service;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service("redisChatService")
public class RedisChatService extends AbstractAiChatService {

    @Resource(name = "redisChatMemory")
    private ChatMemory redisChatMemory;

    public RedisChatService(ChatClient chatClient) {
        super(chatClient);
    }

    @Override
    protected ChatMemory getChatMemory() {
        return redisChatMemory;
    }
}
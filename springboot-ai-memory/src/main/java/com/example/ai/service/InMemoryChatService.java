package com.example.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("inMemoryChatService")
public class InMemoryChatService extends AbstractAiChatService {

    @Autowired
    @Qualifier("inMemoryChatMemory")
    private ChatMemory inMemoryChatMemory;

    public InMemoryChatService(ChatClient chatClient) {
        super(chatClient);
    }

    @Override
    protected ChatMemory getChatMemory() {
        return inMemoryChatMemory;
    }
}
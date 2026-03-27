package com.example.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.List;

public abstract class AbstractAiChatService {

    protected final ChatClient chatClient;

    public AbstractAiChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public final String chat(String conversationId, String message) {
        ChatMemory chatMemory = getChatMemory();
        List<Message> history = chatMemory.get(conversationId);

        String reply = chatClient.prompt()
                .messages(history)
                .user(message)
                .call()
                .content();

        chatMemory.add(conversationId, new UserMessage(message));
        chatMemory.add(conversationId, new AssistantMessage(reply));

        return reply;
    }

    protected abstract ChatMemory getChatMemory();

    public void clear(String conversationId) {
        getChatMemory().clear(conversationId);
    }

}

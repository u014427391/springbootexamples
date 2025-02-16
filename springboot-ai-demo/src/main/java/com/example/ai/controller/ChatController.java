package com.example.ai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.defaultSystem("你是一个AI智能应用").build();
    }

    @GetMapping(value = "/chat/{message}")
    public String chat(@PathVariable("message") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
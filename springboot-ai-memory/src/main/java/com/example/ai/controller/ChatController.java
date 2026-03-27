package com.example.ai.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class ChatController {

    @Autowired
    private  ChatClient chatClient;

    @Autowired
    @Qualifier("jdbcChatMemory")
    private ChatMemory chatMemory;


    @GetMapping("/chat/memory/{message}")
    public String chatWithMemory1(@PathVariable("message") String message) {
        return chatClient.prompt()
                .user(message)
                // 传递conversationId,保持模型的历史记录连贯
                .advisors(advisorSpec -> advisorSpec.param("conversationId", "bnA9f525-l7ae-5c66-ae21-vh53547c96cf"))
                .call()
                .content();
    }

}
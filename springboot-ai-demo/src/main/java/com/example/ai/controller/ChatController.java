package com.example.ai.controller;

import com.example.ai.advisor.SimpleLoggerAdvisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class ChatController {

    @Autowired
    private  ChatClient chatClient;

//    public ChatController(ChatClient.Builder builder) {
//        this.chatClient = builder.defaultSystem("你是一个AI智能应用").build();
//    }

    @Autowired
    private SimpleLoggerAdvisor loggerAdvisor;

    @Autowired
    private ChatMemory chatMemory;

    @GetMapping("/chat/memory/{message}")
    public String chatWithMemory(@PathVariable("message") String message) {
        return chatClient.prompt()
                .advisors(new MessageChatMemoryAdvisor(chatMemory))
                .user(message)
                .call()
                .content();
    }


    @GetMapping(value = "/chat/{message}")
    public String chat(@PathVariable("message") String message) {
        return chatClient.prompt()
                .advisors(loggerAdvisor)    // 添加 Advisor
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/chat/stream/{message}")
    public Flux<String> generateStream(@PathVariable("message") String message) {
        return chatClient.prompt()
                .advisors(loggerAdvisor)    // 添加 Advisor
                .user(message)
                .stream()
                .content();
    }

}
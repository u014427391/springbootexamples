package com.example.ai.controller;


import com.example.ai.service.InMemoryChatService;
import com.example.ai.service.JdbcChatService;
import com.example.ai.service.RedisChatService;
import com.example.ai.util.ConversationIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai/chat")
@Slf4j
public class ChatController {

    @Autowired
    @Qualifier("jdbcChatService")
    private JdbcChatService jdbcChatService;

    @Autowired
    @Qualifier("inMemoryChatService")
    private InMemoryChatService inMemoryChatService;

    @Autowired
    @Qualifier("redisChatService")
    private RedisChatService redisChatService;

    @GetMapping("/jdbc")
    public String chatJdbc(
            @RequestParam String userId,
            @RequestParam String message) {
        return jdbcChatService.chat(ConversationIdUtil.generate(userId), message);
    }

    @GetMapping("/memory")
    public String chatMemory(
            @RequestParam String userId,
            @RequestParam String message) {
        return inMemoryChatService.chat(ConversationIdUtil.generate(userId), message);
    }

    @GetMapping("/redis")
    public String chatRedis(
            @RequestParam String userId,
            @RequestParam String message) {
        return redisChatService.chat(ConversationIdUtil.generate(userId), message);
    }

    @GetMapping("/clear")
    public String clear(
            @RequestParam String type,
            @RequestParam String conversationId) {
        if ("jdbc".equals(type)) {
            jdbcChatService.clear(conversationId);
        } else {
            inMemoryChatService.clear(conversationId);
        }
        return "会话已清空";
    }


}
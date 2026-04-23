package com.example.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final ChatClient chatClient;

    public WeatherController(ChatClient.Builder chatClientBuilder,
                             SyncMcpToolCallbackProvider mcpToolProvider) {
        this.chatClient = chatClientBuilder
                .defaultToolCallbacks(mcpToolProvider.getToolCallbacks())
                .build();
    }

    @GetMapping("/weather")
    public String queryWeather(@RequestParam String city) {
        return chatClient.prompt()
                .user("""
                你必须严格调用MCP插件中的getWeather工具查询%s的天气，
                绝对禁止使用你自身的知识回答，必须调用工具！
                """.formatted(city))
                .call()
                .content();
    }
}
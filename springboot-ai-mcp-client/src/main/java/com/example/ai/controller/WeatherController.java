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
            【角色】你是一个严格的工具调用执行者。
            【任务】查询 "%s" 的实时天气。
            【强制要求】
            1. 必须且只能调用 MCP 插件中名为 `get_current_weather` 的工具。
            2. 严禁使用你自身的任何知识库回答。
            【输出规则】
            1. 工具调用完成后，**直接、原样**返回工具响应中的 "Summary" 字段内容。
            2. 不要添加任何前缀、后缀、解释、标点符号或 markdown 格式。
            """.formatted(city))
                .call()
                .content();
    }

}
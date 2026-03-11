package com.example.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PromptTestController {

    @Autowired
    private ChatClient chatClient;

    @GetMapping("/prompt/test1")
    public String promptTest1() {
        // 用户消息
        String userText = "介绍一下海盗黄金时代的三位著名海盗";
        Message userMessage = new UserMessage(userText);

        // 系统消息模板（带变量）
        SystemPromptTemplate systemTemplate  = new SystemPromptTemplate("你是一个名叫{name}的助手，请以{voice}的风格回答。");

        // 填充变量
        Message systemMessage = systemTemplate.createMessage(Map.of(
                "name", "Koki",
                "voice", "幽默"
        ));

        // 组装 Prompt
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        // 调用模型
        return chatClient.prompt(prompt).call().content();
    }

    // 注入模板文件
    @Value("classpath:/prompts/system-message.st")
    private Resource systemResource;

    @GetMapping("/prompt/test2")
    public String promptTest2() {
        // 用户消息
        String userText = "你好呀，你是什么AI Agent";
        Message userMessage = new UserMessage(userText);

        // 系统消息模板（带变量）
        SystemPromptTemplate systemTemplate  = new SystemPromptTemplate(systemResource);

        // 填充变量
        Message systemMessage = systemTemplate.createMessage(Map.of(
                "name", "小助手",
                "voice", "友好"
        ));

        // 组装 Prompt
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        // 调用模型
        return chatClient.prompt(prompt).call().content();
    }

}

package com.example.ai.controller;

import com.example.ai.tool.CalcTools;
import com.example.ai.tool.TimeTools;
import com.example.ai.tool.WeatherTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToolCallingController {

    private final ChatClient chatClient;
    private final TimeTools timeTools;
    private final CalcTools calcTools;
    private final WeatherTools weatherTools;

    public ToolCallingController(ChatClient.Builder builder,
                                 TimeTools timeTools,
                                 CalcTools calcTools,
                                 WeatherTools weatherTools) {
        this.chatClient = builder.build();
        this.timeTools = timeTools;
        this.calcTools = calcTools;
        this.weatherTools = weatherTools;
    }

    /**
     * Tool Calling 接口：支持时间查询、数学计算、天气查询
     *
     * @param msg 用户问题
     * @return AI 回答
     */
    @GetMapping("/ai")
    public String callTool(@RequestParam String msg) {
        // 注册所有工具，AI 根据问题自动选择合适的工具调用
        return chatClient.prompt(msg)
                .tools(timeTools, calcTools, weatherTools)
                .call()
                .content();
    }
}
package com.example.ai.config;

import com.example.ai.tool.WeatherMcpTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpServerConfig {

    @Bean
    public ToolCallbackProvider weatherTools(WeatherMcpTool weatherMcpTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherMcpTool)
                .build();
    }
}
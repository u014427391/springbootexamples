package com.example.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.execution.ToolExecutionExceptionProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.format.annotation.DurationFormat;

import java.sql.SQLException;
import java.util.function.Function;

/**
 * Spring AI 配置类
 */
@Configuration
public class AiConfig {
    public static final String WEATHER_TOOL = "currentWeather";

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                        你是一个专业、简洁、有用的 AI 助手。
                        请根据工具返回的结果，用自然语言直接回答用户问题。
                        不要返回 JSON、代码块或格式符号，不要说“使用xx函数”，只给最终答案。
                        """)
                .build();
    }

    @Bean
    public ToolExecutionExceptionProcessor exceptionProcessor() {
        return e -> {
            if (e.getCause() instanceof SQLException) {
                return "数据库错误: " + e.getMessage();
            }
            return "工具调用失败: " + e.getMessage();
        };
    }

    @Bean(WEATHER_TOOL)
    @Description("获取指定地点的天气")
    Function<WeatherRequest, WeatherResponse> currentWeather() {
        return request -> new WeatherResponse(30.0,
                request.unit());
    }

}

record WeatherRequest(String location, DurationFormat.Unit unit)
{}
record WeatherResponse(double temp, DurationFormat.Unit unit) {}
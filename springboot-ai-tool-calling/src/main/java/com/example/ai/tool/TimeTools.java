package com.example.ai.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimeTools {

    @Tool(description = "获取用户所在时区的当前日期和时间，用于回答时间相关问题")
    public String getCurrentTime() {
        return LocalDateTime.now()
                .atZone(java.time.ZoneId.systemDefault())
                .toString();
    }
}
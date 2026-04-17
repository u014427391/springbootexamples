package com.example.ai.model;

import java.util.List;

/**
 * 聚合数据天气响应数据模型
 */
public record WeatherResponse(
        String city,                    // 城市名称
        RealtimeWeather realtime,       // 实时天气
        List<FutureWeather> future      // 未来天气预报
) {
    /**
     * 实时天气数据
     */
    public record RealtimeWeather(
            String temperature,    // 温度（℃）
            String humidity,       // 湿度（%）
            String info,           // 天气描述（如"晴"、"多云"）
            String wid,            // 天气图标代码
            String direct,         // 风向
            String power,          // 风力
            String aqi             // 空气质量指数
    ) {}
    
    /**
     * 未来天气预报数据
     */
    public record FutureWeather(
            String date,           // 日期（如"2023-10-01"）
            String temperature,    // 温度范围（如"15/25℃"）
            String weather,        // 天气描述
            String direct          // 风向
    ) {}
    
    /**
     * 生成格式化的天气摘要信息
     */
    public String toSummary() {
        if (realtime == null) {
            return "暂无天气数据";
        }
        return String.format(
                "%s: %s°C, %s, 湿度 %s%%, %s %s, 空气质量指数 %s",
                city, realtime.temperature, realtime.info,
                realtime.humidity, realtime.direct, realtime.power, realtime.aqi
        );
    }
}
package com.example.ai.service;

import com.example.ai.model.WeatherResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    private static final String BASE_URL = "http://apis.juhe.cn/simpleWeather/query";
    // 请替换为你在聚合数据申请的 API Key
    private static final String API_KEY = "4efc59bb794a071dcabf21fc0583dffd";
    
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public WeatherService(RestClient.Builder restClientBuilder, ObjectMapper objectMapper) {
        this.restClient = restClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    /**
     * 获取完整天气信息（实时 + 未来预报）
     */
    public WeatherResponse getWeather(String city) {
        try {
            // 对城市名称进行 URL 编码（聚合数据 API 要求 UTF-8 URLEncode）
            String encodedCity = UriUtils.encode(city, StandardCharsets.UTF_8);
            
            String response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(BASE_URL)
                            .queryParam("city", encodedCity)
                            .queryParam("key", API_KEY)
                            .build())
                    .retrieve()
                    .body(String.class);

            JsonNode root = objectMapper.readTree(response);
            
            // 检查错误码
            int errorCode = root.path("error_code").asInt();
            if (errorCode != 0) {
                String reason = root.path("reason").asText();
                throw new RuntimeException("聚合数据 API 调用失败: " + reason);
            }
            
            JsonNode result = root.path("result");
            
            // 解析实时天气
            JsonNode realtimeNode = result.path("realtime");
            WeatherResponse.RealtimeWeather realtime = new WeatherResponse.RealtimeWeather(
                    realtimeNode.path("temperature").asText(),
                    realtimeNode.path("humidity").asText(),
                    realtimeNode.path("info").asText(),
                    realtimeNode.path("wid").asText(),
                    realtimeNode.path("direct").asText(),
                    realtimeNode.path("power").asText(),
                    realtimeNode.path("aqi").asText()
            );
            
            // 解析未来天气预报
            List<WeatherResponse.FutureWeather> futureList = new ArrayList<>();
            JsonNode futureArray = result.path("future");
            if (futureArray.isArray()) {
                for (JsonNode futureNode : futureArray) {
                    WeatherResponse.FutureWeather future = new WeatherResponse.FutureWeather(
                            futureNode.path("date").asText(),
                            futureNode.path("temperature").asText(),
                            futureNode.path("weather").asText(),
                            futureNode.path("direct").asText()
                    );
                    futureList.add(future);
                }
            }
            
            return new WeatherResponse(
                    result.path("city").asText(),
                    realtime,
                    futureList
            );
            
        } catch (Exception e) {
            throw new RuntimeException("获取天气信息失败: " + e.getMessage(), e);
        }
    }
}
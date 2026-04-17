package com.example.ai.service;

import com.example.ai.model.WeatherResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    private static final String BASE_URL = "http://apis.juhe.cn/simpleWeather/query?key=%s&city=%s";

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    private static final String API_KEY = "your-api-key";


    public WeatherService(RestClient.Builder restClientBuilder, ObjectMapper objectMapper) {
        this.restClient = restClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    /**
     * 获取完整天气信息（实时 + 未来预报）
     */
    public WeatherResponse getWeather(String city) {
        try {

            String url = String.format(BASE_URL, API_KEY, city);

            String response = restClient.get()
                    .uri(url)
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
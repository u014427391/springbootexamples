package com.example.ai.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

/**
 * 天气查询工具，通过聚合数据天气 API 获取实时天气信息
 */
@Component
@Slf4j
public class WeatherTools {

    private final WebClient webClient;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public WeatherTools(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }


    /**
     * 免费天气接口，无需 API Key，国内直接访问，支持全国城市
     */
    @Tool(description = "查询中国城市的实时天气，返回温度、天气状况、风向风速")
    public String getWeather(
            @ToolParam(description = "中文城市名称，例如：北京、上海、长沙、深圳") String city) {

        try {
            String apiKey = "your-api-key";

            String url = String.format("http://apis.juhe.cn/simpleWeather/query?key=%s&city=%s", apiKey, city);

            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            log.error("查询天气服务异常：", e);
            return "天气查询服务异常，请稍后再试";
        }
    }

}
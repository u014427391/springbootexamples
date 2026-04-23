package com.example.ai.tool;

import com.example.ai.model.WeatherResponse;
import com.example.ai.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class WeatherMcpTool {

    private final WeatherService weatherService;

    public WeatherMcpTool(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * 获取实时天气
     */
    @Tool(
            name = "get_current_weather",
            description = """
                    获取指定城市的实时天气信息，包括：
                    - 当前温度、体感温度
                    - 湿度百分比
                    - 风速
                    - 天气描述（如晴、多云、雨等）
                    
                    适用场景：当用户询问"现在某地天气怎么样"、"某地热不热"等问题时调用。
                    """
    )
    public String getCurrentWeather(
            @ToolParam(description = "城市名称，支持中文或英文，例如：Beijing、北京、London、New York")
            String city
    ) {
        log.info("[MCP Tool] 收到调用请求，参数city={}", city);
        WeatherResponse weather = weatherService.getWeather(city);
        log.info("[MCP Tool] 返回结果: {}", weather.toSummary());
        return weather.toSummary();
    }


}
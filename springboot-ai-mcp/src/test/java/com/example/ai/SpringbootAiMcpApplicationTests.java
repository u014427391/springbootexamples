package com.example.ai;

import com.example.ai.model.WeatherResponse;
import com.example.ai.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootAiMcpApplicationTests {

    @Autowired
    private WeatherService weatherService;
    @Test
    void contextLoads() {
        WeatherResponse resp = weatherService.getWeather("长沙");
        System.out.println("resp:"+ resp.toSummary());
    }

}

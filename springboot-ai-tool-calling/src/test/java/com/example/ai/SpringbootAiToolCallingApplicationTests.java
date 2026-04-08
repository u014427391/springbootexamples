package com.example.ai;

import com.example.ai.config.AiConfig;
import com.example.ai.tool.CalcTools;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.function.Function;

record WeatherRequest(String city, String unit) {}
record WeatherResponse(double temperature, String unit) {}
interface WeatherService extends Function<WeatherRequest,
        WeatherResponse> {
    @Override
    WeatherResponse apply(WeatherRequest request);
}


@SpringBootTest
class SpringbootAiToolCallingApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private ChatModel chatModel;

    @Test
    void testToolCalling() {
        // 编程式定义MethodToolCallback
        Method method = ReflectionUtils.findMethod(CalcTools.class,
                "add", int.class, int.class);
        ToolDefinition toolDefinition = ToolDefinition.builder()
                .name("add")
                .description("计算两数之和")
                .inputSchema("""
            {
                "type": "object",
                "properties": {
                    "a": { "type": "integer", "description": "第一个加数" },
                    "b": { "type": "integer", "description": "第二个加数" }
                },
                "required": ["a", "b"]
            }
            """)
                .build();

        ToolCallback toolCallback = MethodToolCallback.builder()
                .toolDefinition(toolDefinition)
                .toolMethod(method)
                .toolObject(new CalcTools())
                .build();

        ChatClient chatClient =
                ChatClient.builder(chatModel).build();
        String content = chatClient.prompt("计算出100和1000两个数相加的结果")
                        .toolCallbacks(toolCallback)
                        .call()
                        .content();
        System.out.println(content);
    }



    @Test
    public void testToolCallingFunction() {
        // 创建工具
        ToolCallback toolCallback =
                FunctionToolCallback.builder("currentWeather",new WeatherService() {
                            @Override
                            public WeatherResponse apply(WeatherRequest request)
                            {
                                return new WeatherResponse(25.5,request.unit());
                            }
                        }).description("获取指定地点的天气")
                        .inputType(WeatherRequest.class)
                        .build();

        String content = ChatClient.create(chatModel)
                .prompt("今天长沙的天气怎么样？")
                .toolCallbacks(toolCallback) // 新版用 tools() 不是 toolCallbacks()
                .call()
                .content();

        System.out.println(content);
    }

    @Test
    void testToolCallingFunctionConfig() throws Exception{
        String content =  ChatClient.create(chatModel)
                .prompt("今天长沙的天气怎么样?")
                .toolNames(AiConfig.WEATHER_TOOL)
                .call()
                .content();
        System.out.println(content);
    }


}

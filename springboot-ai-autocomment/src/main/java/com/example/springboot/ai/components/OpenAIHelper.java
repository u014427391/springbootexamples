package com.example.springboot.ai.components;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@Slf4j
public class OpenAIHelper {
    @Value("${openai.api-url}")
    private String apiURL;

    @Value("${openai.model-name}")
    private String modelName;

    @Value("${openai.api-key}")
    private String apiKey;

    private final Gson gson = new Gson();

    public OpenAIHelper() {
    }

    public OpenAIHelper(String apiKey) {
        this.apiKey = apiKey;
    }

    // 生成评论
    public String generateComment(String title) throws IOException {
        if (apiKey == null || apiKey.isEmpty() || title == null || title.isEmpty()) {
            log.error("API密钥或标题为空");
            return "";
        }

        RequestBody requestBody = new RequestBody();
        requestBody.model = modelName;
        requestBody.temperature = 0.7;
        requestBody.max_tokens = 50;

        Message message = new Message();
        message.role = "user";
        message.content = "生成一条积极的评论，主题是: " + title +
                "。评论需要自然、互动性强，不超过50个字。";
        requestBody.messages = new Message[]{message};

        URL url = new URL(apiURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = gson.toJson(requestBody).getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                ResponseBody responseBody = gson.fromJson(response.toString(), ResponseBody.class);
                if (responseBody != null &&
                        responseBody.choices != null &&
                        responseBody.choices.length > 0 &&
                        responseBody.choices[0].message != null) {
                    return responseBody.choices[0].message.content;
                }
            }
        } else {
            log.error("请求失败，状态码: {}", responseCode);
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                log.error("错误信息: {}", response);
            }
        }

        return "";
    }

    // 内部类：请求体（DeepSeek版）
    private static class RequestBody {
        String model;
        double temperature;
        int max_tokens;
        Message[] messages;

        @SerializedName("top_p")
        double topP = 0.9;

        @SerializedName("frequency_penalty")
        double frequencyPenalty = 0.0;

        @SerializedName("presence_penalty")
        double presencePenalty = 0.0;
    }

    // 内部类：消息（与OpenAI相同）
    private static class Message {
        String role;
        String content;
    }

    // 内部类：响应体（DeepSeek版）
    private static class ResponseBody {
        Choice[] choices;

        String id;
        String object;
        long created;
        Usage usage;
    }

    // 内部类：选择项（与OpenAI相同）
    private static class Choice {
        Message message;
        int index;
        String finish_reason;
    }

    // 内部类：使用统计（DeepSeek版）
    private static class Usage {
        @SerializedName("prompt_tokens")
        int promptTokens;

        @SerializedName("completion_tokens")
        int completionTokens;

        @SerializedName("total_tokens")
        int totalTokens;
    }
}
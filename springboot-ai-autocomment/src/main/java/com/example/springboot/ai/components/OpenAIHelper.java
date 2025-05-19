package com.example.springboot.ai.components;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenAIHelper {
    private static final String API_URL = "https://api.siliconflow.cn/v1/chat/completions"; // DeepSeek API地址
    private static final String MODEL = "deepseek-ai/DeepSeek-R1-Distill-Qwen-7B"; // DeepSeek模型名称
    private final String apiKey;
    private final Gson gson = new Gson();

    public OpenAIHelper(String apiKey) {
        this.apiKey = apiKey;
    }
    
    // 生成评论
    public String generateComment(String title) throws IOException {
        // 如果没有提供API密钥或标题为空，返回空字符串
        if (apiKey == null || apiKey.isEmpty() || title == null || title.isEmpty()) {
            return "";
        }
        
        // 构建请求体
        RequestBody requestBody = new RequestBody();
        requestBody.model = MODEL;
        requestBody.temperature = 0.7; // 控制生成文本的随机性，0.0表示确定性最高，2.0表示随机性最高
        requestBody.max_tokens = 50; // 最大生成token数
        
        Message message = new Message();
        message.role = "user";
        message.content = "生成一条积极的评论，主题是: " + title +
                          "。评论需要自然、互动性强，不超过50个字。";
        requestBody.messages = new Message[]{message};
        
        // 发送请求
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey); // DeepSeek使用相同的授权头格式
        connection.setDoOutput(true);
        
        // 写入请求体
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = gson.toJson(requestBody).getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        
        // 读取响应
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                
                // 解析响应
                ResponseBody responseBody = gson.fromJson(response.toString(), ResponseBody.class);
                if (responseBody != null && 
                    responseBody.choices != null && 
                    responseBody.choices.length > 0 &&
                    responseBody.choices[0].message != null) {
                    return responseBody.choices[0].message.content;
                }
            }
        } else {
            // 读取错误响应
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.err.println("DeepSeek API请求失败，状态码: " + responseCode + ", 错误信息: " + response);
            }
        }
        
        // 请求失败时返回空字符串
        return "";
    }
    
    // 内部类：请求体（DeepSeek版）
    private static class RequestBody {
        String model;
        double temperature;
        int max_tokens;
        Message[] messages;
        
        // DeepSeek特定参数（如果需要）
        @SerializedName("top_p")
        double topP = 0.9; // 核采样概率
        
        @SerializedName("frequency_penalty")
        double frequencyPenalty = 0.0; // 频率惩罚，降低重复度
        
        @SerializedName("presence_penalty")
        double presencePenalty = 0.0; // 存在惩罚，鼓励生成新话题
    }
    
    // 内部类：消息（与OpenAI相同）
    private static class Message {
        String role;
        String content;
    }
    
    // 内部类：响应体（DeepSeek版）
    private static class ResponseBody {
        Choice[] choices;
        
        // DeepSeek可能返回的额外字段
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
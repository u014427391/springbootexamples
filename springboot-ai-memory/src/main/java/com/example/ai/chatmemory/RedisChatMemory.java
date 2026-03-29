package com.example.ai.chatmemory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class RedisChatMemory implements ChatMemory {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String KEY_PREFIX = "ai:chat:memory:";
    private static final long EXPIRE_DAYS = 7;

    // 最大消息数（窗口限制）
    private static final int MAX_MESSAGES = 20;

    public RedisChatMemory(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Message> get(String conversationId) {
        String key = KEY_PREFIX + conversationId;
        List<String> jsonList = redisTemplate.opsForList().range(key, 0, -1);

        if (jsonList == null || jsonList.isEmpty()) {
            return List.of();
        }

        return jsonList.stream()
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, Message.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Redis 消息反序列化失败", e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public void add(String conversationId, Message message) {
        try {
            String key = KEY_PREFIX + conversationId;
            String json = objectMapper.writeValueAsString(message);

            redisTemplate.opsForList().rightPush(key, json);
            redisTemplate.expire(key, EXPIRE_DAYS, TimeUnit.DAYS);

            // 关键：自动限制最多20条（模拟 MessageWindow）
            trimToMaxSize(key);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Redis 消息序列化失败", e);
        }
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        for (Message msg : messages) {
            add(conversationId, msg);
        }
    }

    @Override
    public void clear(String conversationId) {
        redisTemplate.delete(KEY_PREFIX + conversationId);
    }

    // ====================== 窗口限制逻辑 ======================
    private void trimToMaxSize(String key) {
        Long size = redisTemplate.opsForList().size(key);
        if (size != null && size > MAX_MESSAGES) {
            // 只保留最近 MAX_MESSAGES 条
            redisTemplate.opsForList().trim(key, size - MAX_MESSAGES, -1);
        }
    }

}
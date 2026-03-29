package com.example.ai.chatmemory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class RedisChatMemory implements ChatMemory {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String KEY_PREFIX = "ai:chat:memory:";
    private static final long EXPIRE_DAYS = 7;
    private static final int MAX_MESSAGES = 20;

    public RedisChatMemory(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // ===================== 读取消息 =====================
    @Override
    public List<Message> get(String conversationId) {
        String key = KEY_PREFIX + conversationId;
        List<String> jsonList = redisTemplate.opsForList().range(key, 0, -1);

        if (jsonList == null || jsonList.isEmpty()) {
            return List.of();
        }

        return jsonList.stream()
                .map(this::deserializeMessage)
                .collect(Collectors.toList());
    }

    // ===================== 新增单条消息 =====================
    @Override
    public void add(String conversationId, Message message) {
        try {
            String key = KEY_PREFIX + conversationId;
            String json = objectMapper.writeValueAsString(message);

            redisTemplate.opsForList().rightPush(key, json);
            redisTemplate.expire(key, EXPIRE_DAYS, TimeUnit.DAYS);
            trimToMaxSize(key);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("消息序列化失败", e);
        }
    }

    // ===================== 批量消息 =====================
    @Override
    public void add(String conversationId, List<Message> messages) {
        messages.forEach(msg -> add(conversationId, msg));
    }

    // ===================== 清空 =====================
    @Override
    public void clear(String conversationId) {
        redisTemplate.delete(KEY_PREFIX + conversationId);
    }

    // ===================== 【核心：手动反序列化】 =====================
    private Message deserializeMessage(String json) {
        try {
            // 先读成 Map，手动判断类型
            Map<String, Object> map = objectMapper.readValue(json, Map.class);
            String type = (String) map.get("messageType");
            String content = (String) map.get("text");

            return switch (MessageType.valueOf(type)) {
                case USER -> new UserMessage(content);
                case ASSISTANT -> new AssistantMessage(content);
                case SYSTEM -> new SystemMessage(content);
                default -> throw new IllegalArgumentException("不支持的消息类型");
            };
        } catch (Exception e) {
            throw new RuntimeException("消息反序列化失败", e);
        }
    }

    // ===================== 限制最大条数 =====================
    private void trimToMaxSize(String key) {
        Long size = redisTemplate.opsForList().size(key);
        if (size != null && size > MAX_MESSAGES) {
            redisTemplate.opsForList().trim(key, size - MAX_MESSAGES, -1);
        }
    }
}
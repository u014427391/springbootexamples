package com.example.chatbot.service;

import com.example.chatbot.exception.ChatException;
import com.example.chatbot.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatService.class);
    private final ChatClient chatClient;
    private final String systemPrompt;
    private final int maxHistorySize;
    private final List<Message> chatHistory = new ArrayList<>();
    private final ReentrantLock historyLock = new ReentrantLock();


    public ChatService(ChatClient.Builder builder,
                       @Value("${spring.ai.openai.system-prompt:你是一个友好、乐于助人的AI助手。请提供简洁、准确的回答。}") String systemPrompt,
                       @Value("${spring.ai.chat.history.max-size:50}") int maxHistorySize) {
        this.chatClient = builder.defaultSystem(systemPrompt).build();
        this.systemPrompt = systemPrompt;
        this.maxHistorySize = maxHistorySize;
        initializeChatHistory();
    }


    /**
     * 初始化聊天历史，添加系统消息
     */
    private void initializeChatHistory() {
        historyLock.lock();
        try {
            chatHistory.clear();
            chatHistory.add(new SystemMessage(systemPrompt));
            log.info("聊天历史已初始化，系统提示: {}", systemPrompt);
        } finally {
            historyLock.unlock();
        }
    }

    /**
     * 处理聊天请求
     * @param userInput 用户输入内容
     * @return AI响应内容
     */
    public String chat(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            throw new ChatException("输入内容不能为空");
        }

        try {
            String processedInput = userInput.trim();
            log.info("收到用户输入: {}", processedInput);

            // 添加用户消息到历史记录
            UserMessage userMessage = new UserMessage(processedInput);
            addToHistory(userMessage);

            // 检查历史记录大小，超过限制则清理
            trimHistoryIfNeeded();

            // 创建提示并获取响应（直接使用框架Message列表）
            Prompt prompt = new Prompt(new ArrayList<>(getFrameworkChatHistory()));
            log.info("发送请求到AI模型，历史消息数量: {}", prompt.getInstructions().size());

            String responseContent = chatClient.prompt(prompt)
                    .call()
                    .chatResponse()
                    .getResult()
                    .getOutput()
                    .getContent();

            // 处理空响应
            if (responseContent == null || responseContent.trim().isEmpty()) {
                responseContent = "抱歉，未能生成有效响应，请尝试重新提问。";
                log.warn("AI返回空响应，使用默认回复");
            }

            // 添加助手响应到历史记录
            addToHistory(new AssistantMessage(responseContent));
            log.info("AI响应处理完成，响应长度: {}", responseContent.length());

            return responseContent;
        } catch (Exception e) {
            log.error("处理聊天请求失败", e);
            throw new ChatException("处理聊天请求失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取前端需要的聊天历史（不含系统消息）
     * @return 聊天历史列表
     */
    public List<ChatMessage> getChatHistory() {
        historyLock.lock();
        try {
            return chatHistory.stream()
                    .skip(1) // 跳过系统消息
                    .map(message -> {
                        String role = message instanceof UserMessage ? "user" : "assistant";
                        return new ChatMessage(role, message.getContent());
                    })
                    .collect(Collectors.toList());
        } finally {
            historyLock.unlock();
        }
    }

    /**
     * 获取框架需要的完整聊天历史（包含系统消息）
     * @return 框架消息列表
     */
    private List<Message> getFrameworkChatHistory() {
        historyLock.lock();
        try {
            return new ArrayList<>(chatHistory);
        } finally {
            historyLock.unlock();
        }
    }

    /**
     * 清除聊天历史
     */
    public void clearHistory() {
        initializeChatHistory();
        log.info("聊天历史已清除");
    }

    /**
     * 添加消息到历史记录
     */
    private void addToHistory(Message message) {
        historyLock.lock();
        try {
            chatHistory.add(message);
            log.debug("添加消息到历史，角色: {}, 内容长度: {}",
                    message.getClass().getSimpleName(),
                    message.getContent().length());
        } finally {
            historyLock.unlock();
        }
    }

    /**
     * 如果历史记录超过最大限制，清理部分历史
     */
    private void trimHistoryIfNeeded() {
        historyLock.lock();
        try {
            // 保留系统消息，只清理用户和助手的消息
            if (chatHistory.size() > maxHistorySize) {
                int messagesToRemove = chatHistory.size() - maxHistorySize;
                for (int i = 0; i < messagesToRemove; i++) {
                    chatHistory.remove(1); // 从索引1开始删除（保留系统消息）
                }
                log.info("聊天历史已修剪，移除了 {} 条消息，当前大小: {}",
                        messagesToRemove, chatHistory.size());
            }
        } finally {
            historyLock.unlock();
        }
    }
}
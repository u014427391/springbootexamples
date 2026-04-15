package com.example.rag.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RAGService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Autowired
    public RAGService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
    }

    public String ask(String question) {
        //  从 Milvus 检索与问题最相关的 5 个文档片段
        SearchRequest searchRequest = SearchRequest.builder()
                .query(question)  // 查询文本
                .topK(5)          // 指定返回的最相似文档数量
                .build();
        List<Document> documents = vectorStore.similaritySearch(searchRequest);
        List<String> context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.toList());

        // 构建 Prompt
        PromptTemplate promptTemplate = new PromptTemplate("""
                请根据以下背景信息回答用户的问题。如果背景信息不包含答案，请说明"依据现有资料无法回答"。
                
                ### 背景信息:
                {context}
                
                ### 用户问题:
                {question}
                
                ### 回答:
                """);

        // 调用 LLM 生成答案
        return chatClient.prompt(promptTemplate.create(Map.of("context", context, "question", question)))
                .call()
                .content();
    }

}

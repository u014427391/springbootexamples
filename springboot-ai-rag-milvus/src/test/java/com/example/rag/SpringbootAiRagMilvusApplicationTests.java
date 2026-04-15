package com.example.rag;

import com.example.rag.service.RAGService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class SpringbootAiRagMilvusApplicationTests {
    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private RAGService ragService;

    @Test
    void testRAGFlow() throws IOException {


        // 执行 RAG 提问
        String question = "什么是 RAG？它的主要作用是什么？";
        String answer = ragService.ask(question);
        System.out.println("用户问题: " + question);
        System.out.println("模型回答: " + answer);


    }
    @Test
    void contextLoads() {
    }

}

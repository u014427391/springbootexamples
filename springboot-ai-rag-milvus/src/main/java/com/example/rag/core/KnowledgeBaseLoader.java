package com.example.rag.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class KnowledgeBaseLoader {

    @Autowired
    private VectorStore vectorStore;

    @EventListener(ApplicationReadyEvent.class)
    public void loadDocumentsOnStartup() {
        try {
            // 读取 classpath 下的测试文档
            ClassPathResource resource = new ClassPathResource("data/test-knowledge.txt");
            TikaDocumentReader reader = new TikaDocumentReader(resource);

            // 分割文档
            TokenTextSplitter splitter = new TokenTextSplitter();
            List<Document> documents = splitter.apply(reader.read());

            // 存入 Milvus
            vectorStore.add(documents);
            log.info("成功加载 {} 个文档片段到知识库。", documents.size());
        } catch (Exception e) {
            log.error("加载文档失败", e);
        }
    }
}

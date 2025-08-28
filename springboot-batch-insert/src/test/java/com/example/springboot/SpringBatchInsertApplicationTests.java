package com.example.springboot;

import com.example.springboot.service.BatchInsertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
class SpringBatchInsertApplicationTests {

    @Autowired
    private BatchInsertService batchInsertService;

    @Test
    void testBatchInsert() throws Exception {
        int testCount = 1000;
        CompletableFuture<Void> future = batchInsertService.processBatchInsert(testCount);
        future.get();
    }

    @Test
    void contextLoads() {
    }
}
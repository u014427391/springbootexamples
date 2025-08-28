package com.example.springboot.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class BatchInsertService {

    private Logger log = LoggerFactory.getLogger(BatchInsertService.class);

    @Autowired
    @Qualifier("batchInsertExecutor")
    private Executor batchInsertExecutor;

    @Autowired
    private UserRepository userRepository;

    private static final Integer BATCH_SIZE = 1000;

    public CompletableFuture<Void> processBatchInsert(Integer totalCount) {
        if (totalCount <= 0) {
            throw new IllegalArgumentException("插入数量必须大于0");
        }

        // 记录总任务开始时间
        Instant totalStart = Instant.now();
        log.info("批量插入开始，总数量：{} ", totalCount);

        // 1. 生成测试数据并记录耗时
        Instant dataGenStart = Instant.now();
        List<User> testData = generateTestData(totalCount);
        long dataGenCost = java.time.Duration.between(dataGenStart, Instant.now()).toMillis();
        log.info("测试数据生成完成，耗时：{}ms", dataGenCost);

        // 2. 分割数据并记录耗时
        Instant splitStart = Instant.now();
        List<List<User>> partitionedUserList = CollUtil.split(testData, BATCH_SIZE);
        long splitCost = java.time.Duration.between(splitStart, Instant.now()).toMillis();
        log.info("数据分割完成，分成 {} 批，每批 {} 条，耗时：{}ms",
                partitionedUserList.size(), BATCH_SIZE, splitCost);

        // 3. 批量插入并记录每批耗时
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (List<User> batch : partitionedUserList) {
            final int batchSize = batch.size();
            // 记录当前批次开始时间
            Instant batchStart = Instant.now();
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    userRepository.saveAll(batch);
                    // 计算当前批次耗时
                    long batchCost = java.time.Duration.between(batchStart, Instant.now()).toMillis();
                    log.info("批次插入完成，批次大小：{}，耗时：{}ms", batchSize, batchCost);
                } catch (Exception e) {
                    log.error("批次插入失败，批次大小：{}，错误：{}", batchSize, e.getMessage(), e);
                    throw e;
                }
            }, batchInsertExecutor);
            futures.add(future);
        }

        // 4. 等待所有批次完成并记录总耗时
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .whenComplete((v, e) -> {
                    long totalCost = java.time.Duration.between(totalStart, Instant.now()).toMillis();
                    if (e != null) {
                        log.error("批量插入失败，总耗时：{}ms ", totalCost, e);
                    } else {
                        log.info("批量插入完成，总数量：{}，总耗时：{}ms，平均每条耗时：{}ms",
                                totalCount, totalCost, totalCount > 0 ? (totalCost / totalCount) : 0);
                    }
                });
    }

    private List<User> generateTestData(Integer count) {
        List<User> users = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setId(IdUtil.getSnowflake().nextId());
            user.setUsername("user" + i);
            user.setPassword(IdUtil.fastSimpleUUID());
            user.setEmail("user" + i + "@example.com");
            user.setCreateTime(LocalDateTimeUtil.now());
            user.setModifyTime(LocalDateTimeUtil.now());
            users.add(user);
        }
        return users;
    }
}
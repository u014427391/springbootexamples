package com.example.redission.service;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BloomFilterService {

    private static final Logger logger = LoggerFactory.getLogger(BloomFilterService.class);

    @Value("${bloom.filter.expected-insertions:1000000}")
    private long expectedInsertions;

    @Value("${bloom.filter.false-probability:0.03}")
    private double falseProbability;

    private static final String BLOOM_FILTER_NAME = "bloomFilter";

    private final RedissonClient redissonClient;
    private RBloomFilter<String> bloomFilter;

    public BloomFilterService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public synchronized void initBloomFilter() {
        if (bloomFilter == null) {
            bloomFilter = redissonClient.getBloomFilter(BLOOM_FILTER_NAME);

            // 检查布隆过滤器是否已经初始化
            if (!bloomFilter.isExists()) {
                bloomFilter.tryInit(expectedInsertions, falseProbability);
                logger.info("Bloom filter initialized with expected insertions: {} and false probability: {}",
                        expectedInsertions, falseProbability);
            } else {
                logger.info("Bloom filter already exists, using existing instance");
            }
        }
    }

    public void add(String key) {
        if (key == null) {
            logger.warn("Attempt to add null key to Bloom filter");
            return;
        }

        bloomFilter.add(key);
        logger.debug("Added key: {} to Bloom filter", key);
    }

    public boolean contains(String key) {
        if (key == null) {
            logger.warn("Attempt to check null key in Bloom filter");
            return false;
        }

        boolean result = bloomFilter.contains(key);
        logger.debug("Checked key: {} in Bloom filter, result: {}", key, result);
        return result;
    }

    /**
     * 重新初始化布隆过滤器（仅在需要清空时调用）
     */
    public synchronized void reinitialize() {
        bloomFilter.delete();
        bloomFilter.tryInit(expectedInsertions, falseProbability);
        logger.info("Bloom filter reinitialized");
    }

    /**
     * 获取布隆过滤器的错误概率
     */
    public double getFalseProbability() {
        return falseProbability;
    }

    /**
     * 获取布隆过滤器的预期插入数量
     */
    public long getExpectedInsertions() {
        return expectedInsertions;
    }
}
package com.example.redission.runner;

import com.example.redission.service.BloomFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BloomFilterInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BloomFilterInitializer.class);

    private final BloomFilterService bloomFilterService;

    @Autowired
    public BloomFilterInitializer(BloomFilterService bloomFilterService) {
        this.bloomFilterService = bloomFilterService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 在应用启动时初始化布隆过滤器
        bloomFilterService.initBloomFilter();
        logger.info("Bloom filter initialized during application startup");

    }
}
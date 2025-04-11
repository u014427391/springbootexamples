package com.example.redission.runner;

import com.example.redission.dto.OrderDetailDTO;
import com.example.redission.service.BloomFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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

        // 模拟订单数据并写入布隆过滤器
        List<OrderDetailDTO> orderData = generateSampleOrderData();
        initializeBloomFilterWithOrderData(orderData);
    }

    private void initializeBloomFilterWithOrderData(List<OrderDetailDTO> orderData) {
        // 将订单数据写入布隆过滤器
        for (OrderDetailDTO order : orderData) {
            // 将订单号写入布隆过滤器
            bloomFilterService.add(order.getOrderNumber());

            // 将订单中的商品 SKU ID 写入布隆过滤器
            for (OrderDetailDTO.OrderItemDTO item : order.getItems()) {
                bloomFilterService.add(item.getSkuId());
            }
        }

        logger.info("Bloom filter initialized with {} orders and their items", orderData.size());
    }

    private List<OrderDetailDTO> generateSampleOrderData() {
        // 使用 Arrays.asList 替代 List.of（JDK 8 兼容）
        return Arrays.asList(
                new OrderDetailDTO(
                        "1",
                        "ORD-001",
                        new BigDecimal("100.00"),
                        "COMPLETED",
                        "PAID",
                        "2024-01-01 12:00:00",
                        Arrays.asList(
                                new OrderDetailDTO.OrderItemDTO(
                                        "SKU-001",
                                        "Product 1",
                                        "image1.jpg",
                                        2,
                                        new BigDecimal("50.00"),
                                        new BigDecimal("100.00")
                                )
                        )
                ),
                new OrderDetailDTO(
                        "2",
                        "ORD-002",
                        new BigDecimal("200.00"),
                        "COMPLETED",
                        "PAID",
                        "2024-01-02 12:00:00",
                        Arrays.asList(
                                new OrderDetailDTO.OrderItemDTO(
                                        "SKU-002",
                                        "Product 2",
                                        "image2.jpg",
                                        1,
                                        new BigDecimal("200.00"),
                                        new BigDecimal("200.00")
                                )
                        )
                )
        );
    }
}
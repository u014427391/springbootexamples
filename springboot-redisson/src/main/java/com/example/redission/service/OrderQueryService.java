package com.example.redission.service;

import com.example.redission.dto.OrderDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class OrderQueryService {

    private static final Logger logger = LoggerFactory.getLogger(OrderQueryService.class);

    private final BloomFilterService bloomFilterService;

    @Autowired
    public OrderQueryService(BloomFilterService bloomFilterService) {
        this.bloomFilterService = bloomFilterService;
    }

    /**
     * 查询订单信息
     *
     * @param orderNumber 订单号
     * @return 订单信息，如果不存在则返回 null
     */
    public OrderDetailDTO getOrderDetails(String orderNumber) {
        // 先通过布隆过滤器判断订单号是否存在
        if (!bloomFilterService.contains(orderNumber)) {
            logger.info("Order {} does not exist in Bloom filter", orderNumber);
            return null;
        }

        // 模拟订单数据
        OrderDetailDTO order = generateSampleOrderData(orderNumber);
        if (order != null) {
            logger.info("Order {} found in simulated data", orderNumber);
            return order;
        } else {
            logger.info("Order {} exists in Bloom filter but not found in simulated data", orderNumber);
            return null;
        }
    }

    /**
     * 模拟生成订单数据
     *
     * @param orderNumber 订单号
     * @return 模拟的订单数据
     */
    private OrderDetailDTO generateSampleOrderData(String orderNumber) {
        // 模拟的订单数据
        if ("ORD-001".equals(orderNumber)) {
            return new OrderDetailDTO(
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
            );
        } else if ("ORD-002".equals(orderNumber)) {
            return new OrderDetailDTO(
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
            );
        }
        return null;
    }
}
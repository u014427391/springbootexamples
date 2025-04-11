package com.example.redission.controller;

import com.example.redission.dto.OrderDetailDTO;
import com.example.redission.service.OrderQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderQueryController {

    private static final Logger logger = LoggerFactory.getLogger(OrderQueryController.class);

    private final OrderQueryService orderQueryService;

    @Autowired
    public OrderQueryController(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    /**
     * 查询订单信息
     *
     * @param orderNumber 订单号
     * @return 订单信息，如果不存在则返回 null
     */
    @GetMapping("/{orderNumber}")
    public OrderDetailDTO getOrderDetails(@PathVariable String orderNumber) {
        OrderDetailDTO order = orderQueryService.getOrderDetails(orderNumber);
        logger.info("Query order {}: result = {}", orderNumber, order);
        return order;
    }
}
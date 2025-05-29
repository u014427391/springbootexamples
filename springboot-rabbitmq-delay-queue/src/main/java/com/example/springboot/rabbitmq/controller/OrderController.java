package com.example.springboot.rabbitmq.controller;

import com.example.springboot.rabbitmq.dto.OrderDTO;
import com.example.springboot.rabbitmq.entity.Order;
import com.example.springboot.rabbitmq.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * @param orderDTO
     * @return 创建结果
     */
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            Order order = new Order();
            order.setOrderId(UUID.randomUUID().toString());
            order.setUserId(orderDTO.getUserId());
            order.setAmount(orderDTO.getAmount());

            Order savedOrder = orderService.createOrder(order);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("创建订单失败：" + e.getMessage());
        }
    }

    /**
     * 支付订单
     * @param orderId 订单ID
     * @return 支付结果
     */
    @PostMapping("/{orderId}/pay")
    public ResponseEntity<?> payOrder(@PathVariable String orderId) {
        try {
            boolean result = orderService.payOrder(orderId);
            if (result) {
                return ResponseEntity.ok("订单支付成功");
            } else {
                return ResponseEntity.badRequest().body("订单支付失败");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("支付处理失败：" + e.getMessage());
        }
    }

    /**
     * 查询订单
     * @param orderId 订单ID
     * @return 订单信息
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable String orderId) {
        try {
            return orderService.getOrderById(orderId)
                   .map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("查询订单失败：" + e.getMessage());
        }
    }
}
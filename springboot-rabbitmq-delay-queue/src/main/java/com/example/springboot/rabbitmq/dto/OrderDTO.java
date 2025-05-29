package com.example.springboot.rabbitmq.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDTO {

    String userId;

    private BigDecimal amount;

    private String requestId;
}

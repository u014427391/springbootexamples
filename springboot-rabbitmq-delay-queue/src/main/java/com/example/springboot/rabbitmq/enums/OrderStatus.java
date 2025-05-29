package com.example.springboot.rabbitmq.enums;

public enum OrderStatus {
    UNPAID(0, "未支付"),
    PAID(1, "已支付"),
    CLOSED(2, "已关闭");

    private Integer code;
    private String description;

    OrderStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    // 通过状态码获取枚举实例的静态方法
    public static OrderStatus fromCode(Integer code) {
        if (code == null) {
            throw new IllegalArgumentException("订单状态码不能为空");
        }
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的订单状态码: " + code);
    }
}
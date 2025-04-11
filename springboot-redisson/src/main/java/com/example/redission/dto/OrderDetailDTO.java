package com.example.redission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {

    private String orderId; // 订单ID
    private String orderNumber; // 订单号
    private BigDecimal totalAmount; // 总金额
    private String orderStatus; // 订单状态
    private String paymentStatus; // 支付状态
    private String createTime; // 创建时间
    private List<OrderItemDTO> items; // 订单商品项

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderItemDTO {
        private String skuId; // 商品SKU ID
        private String skuName; // 商品名称
        private String skuImage; // 商品图片
        private int quantity; // 商品数量
        private BigDecimal price; // 商品单价
        private BigDecimal subtotal; // 商品小计
    }

}

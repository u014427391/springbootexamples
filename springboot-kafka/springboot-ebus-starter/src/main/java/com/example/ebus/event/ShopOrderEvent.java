package com.example.ebus.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopOrderEvent {

    private String orderCode;
    private String productName;
    private float price;
    private String productDesc;
    private Integer isOk;

}

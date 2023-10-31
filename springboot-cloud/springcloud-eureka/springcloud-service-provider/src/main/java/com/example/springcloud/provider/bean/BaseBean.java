package com.example.springcloud.provider.bean;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseBean {

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createMan;

    private String updateMan;

}

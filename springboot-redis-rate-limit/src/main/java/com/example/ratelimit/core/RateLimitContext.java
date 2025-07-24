package com.example.ratelimit.core;

import com.example.ratelimit.core.RateLimitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateLimitContext {

    private String compositeKey; // 主键
    private RateLimitType type;  // 算法枚举
    private int limit;           // 阈值
    private int window;          // 窗口秒 or 速率
    private int capacity;        // 桶容量
    private long nowMillis;      // 当前时间戳

}
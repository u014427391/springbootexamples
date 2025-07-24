package com.example.ratelimit.controller;

import com.example.ratelimit.core.RateLimitType;
import com.example.ratelimit.core.RateLimiter;
import com.example.ratelimit.keygenerator.DefaultKeyGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    /* ================== 1 固定窗口 ================== */
    /**
     * 每 IP 每秒最多 5 次
     * curl http://localhost:8080/demo/fixed
     */
    @RateLimiter(type = RateLimitType.FIXED_WINDOW, limit = 5, window = 1, keyGen = DefaultKeyGenerator.class)

    /* ================== 2 滑动窗口 ================== */
    /**
     * 每用户每 5 秒内最多 10 次（精度 1 秒）
     * curl http://localhost:8080/demo/sliding
     */
    //@RateLimiter(type = RateLimitType.SLIDING_WINDOW, limit = 10, window = 5, keyGen = DefaultKeyGenerator.class)

    /* ================== 3 令牌桶 ================== */
    /**
     * 秒杀下单接口：每秒产生 20 个令牌，桶容量 50，允许突发
     * curl http://localhost:8080/demo/token
     */
//    @RateLimiter(type = RateLimitType.TOKEN_BUCKET,
//            limit = 20,          // 每秒速率
//            capacity = 50,       // 桶容量
//            keyGen = DefaultKeyGenerator.class)

    /* ================== 4 漏桶 ================== */
    /**
     * 日志上报接口：匀速流出 100 req/s，桶容量 200
     * curl http://localhost:8080/demo/leaky
     */
//    @RateLimiter(type = RateLimitType.LEAKY_BUCKET,
//            limit = 100,         // 每毫秒流出量 0.1
//            capacity = 200,
//            keyGen = DefaultKeyGenerator.class)
    @PostMapping
    public String order() {
        return "success";
    }

}

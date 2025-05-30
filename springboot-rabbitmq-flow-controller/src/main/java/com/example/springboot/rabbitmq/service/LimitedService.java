package com.example.springboot.rabbitmq.service;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LimitedService {


    private final RateLimiter rateLimiter = RateLimiter.create(5);


    public void limitedMethod() {
        // 尝试获取一个令牌，若获取不到则等待
        if (rateLimiter.tryAcquire()) {
            // 执行具体业务逻辑
            log.info("请求被处理");
        } else {
            log.info("请求被限流");
        }
    }

}

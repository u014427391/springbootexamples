package com.example.ratelimit.controller;

import com.example.ratelimit.core.RateLimitType;
import com.example.ratelimit.core.RateLimiter;
import com.example.ratelimit.keygenerator.DefaultKeyGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @RateLimiter(type = RateLimitType.TOKEN_BUCKET, limit = 10, capacity = 20, keyGen = DefaultKeyGenerator.class)
    @PostMapping
    public String order() {
        return "success";
    }

}

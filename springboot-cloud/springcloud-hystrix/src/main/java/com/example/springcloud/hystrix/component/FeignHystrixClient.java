package com.example.springcloud.hystrix.component;

import com.example.springcloud.hystrix.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "eureka-service-provider", fallback = FeignHystrixClientFallback.class)
//@FeignClient(name = "eureka-service-provider", fallbackFactory = FeignHystrixClientFallbackFactory.class)
public interface FeignHystrixClient {
    @RequestMapping(value = "/api/users/{username}",method = RequestMethod.GET)
    User findGithubUser(@PathVariable("username") String username);
}
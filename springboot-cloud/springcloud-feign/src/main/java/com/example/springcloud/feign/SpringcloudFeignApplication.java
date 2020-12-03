package com.example.springcloud.feign;

import com.example.springcloud.feign.bean.User;
import com.example.springcloud.feign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@RestController
public class SpringcloudFeignApplication {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudFeignApplication.class, args);
    }

    @GetMapping("/findUser/{username}")
    public User index(@PathVariable("username")String username){
        return userService.findGithubUser(username);
    }
}
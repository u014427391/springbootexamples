package com.example.springcloud.consumer;

import com.example.springcloud.consumer.bean.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class SpringcloudServiceConsumerApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudServiceConsumerApplication.class, args);
    }

    @GetMapping("/findUser/{username}")
    public User index(@PathVariable("username")String username){
        return restTemplate().getForObject("http://EUREKA-SERVICE-PROVIDER/api/users/"+username,User.class);
    }
}


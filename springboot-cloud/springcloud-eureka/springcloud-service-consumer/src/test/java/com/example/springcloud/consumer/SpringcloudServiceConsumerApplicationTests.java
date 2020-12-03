package com.example.springcloud.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest
class SpringcloudServiceConsumerApplicationTests {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Test
    void contextLoads() {
      String s= restTemplate().getForObject("http://EUREKA-SERVICE-PROVIDER/api/users",String.class);
       System.out.println(s);
    }

}

package com.example.springcloud.ribbon;

import com.example.springcloud.ribbon.bean.User;
import com.example.springcloud.ribbon.configuration.IgnoreComponentScan;
import com.example.springcloud.ribbon.configuration.RibbonClientConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "eureka-service-provider",configuration = RibbonClientConfiguration.class)
@ComponentScan(excludeFilters={@ComponentScan.Filter(type= FilterType.ANNOTATION,value= IgnoreComponentScan.class)})
@RestController
@Slf4j
public class SpringcloudRibbonApplication {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudRibbonApplication.class, args);
    }

    @GetMapping(value = {"/test"})
    public String test(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("EUREKA-SERVICE-PROVIDER");
        URI uri = URI.create(String.format("http://%s:%s", serviceInstance.getHost() , serviceInstance.getPort()));
        System.out.println(uri.toString());
        return uri.toString();
    }

    @GetMapping("/findUser/{username}")
    public User index(@PathVariable("username")String username){
        return restTemplate.getForObject("http://EUREKA-SERVICE-PROVIDER/api/users/"+username,User.class);
    }

}

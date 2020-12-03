package com.example.springcloud.provider;

import com.example.springcloud.provider.bean.User;
import com.example.springcloud.provider.service.UserService;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class SpringcloudServiceProviderApplication {

    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudServiceProviderApplication.class, args);
    }

    @GetMapping({"/api/users/{username}"})
    @ResponseBody
    public User findUser(@PathVariable("username")String username) throws InterruptedException{
        //User user=  userService.findUser(username);
        User user = new User("nicky","http://smilenicky.blog.csdn.net");
        return user;
    }

    @GetMapping({"/api/findUser"})
    @ResponseBody
    public User findUserByName(@RequestParam(value = "username",required = false)String username) throws InterruptedException{
        User user = new User("nicky","http://smilenicky.blog.csdn.net");
        return user;
    }
}

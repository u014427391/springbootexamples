package com.example.springcloud.zuul;

import com.example.springcloud.zuul.web.filter.ZuulApiGatewayErrFilter;
import com.example.springcloud.zuul.web.filter.ZuulApiGatewayPostFilter;
import com.example.springcloud.zuul.web.filter.ZuulApiGatewayPreFilter;
import com.example.springcloud.zuul.web.filter.ZuulApiGatewayRouteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class SpringcloudZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudZuulApplication.class, args);
    }

    @Bean
    public ZuulApiGatewayPreFilter zuulApiGatewayPreFilter(){
        return new ZuulApiGatewayPreFilter();
    }
    @Bean
    public ZuulApiGatewayPostFilter zuulApiGatewayPostFilter(){
        return new ZuulApiGatewayPostFilter();
    }
    @Bean
    public ZuulApiGatewayRouteFilter zuulApiGatewayRouteFilter(){
        return new ZuulApiGatewayRouteFilter();
    }
    @Bean
    public ZuulApiGatewayErrFilter zuulApiGatewayErrFilter(){
        return new ZuulApiGatewayErrFilter();
    }
}

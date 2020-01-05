package com.example.springboot.dubbo;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <pre>
 * Springboot启动类
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020年01月05日  修改内容:
 * </pre>
 */
@EnableDubbo(scanBasePackages="com.example.springboot.dubbo.service.impl")
@SpringBootApplication
public class OrderServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceProviderApplication.class, args);
    }
}
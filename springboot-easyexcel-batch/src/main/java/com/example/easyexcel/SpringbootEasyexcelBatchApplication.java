package com.example.easyexcel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringbootEasyexcelBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootEasyexcelBatchApplication.class, args);
    }

}

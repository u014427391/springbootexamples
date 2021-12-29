package com.example.ali.canal;

import com.example.ali.canal.client.AliCanalClient;
import com.example.ali.canal.client.SimpleCanalClientExample;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AliCanalClient.class)
public class SpringbootCanalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCanalApplication.class, args);
    }

}

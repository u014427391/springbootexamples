package com.example.oauth2.implicit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class ImplicitApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImplicitApplication.class, args);
    }

}

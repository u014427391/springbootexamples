package com.example.oauth2.password;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class PasswordApplication {

    public static void main(String[] args) {
        SpringApplication.run(PasswordApplication.class, args);
    }

}

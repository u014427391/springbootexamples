package com.example.springboot.jwt;

import com.example.springboot.jwt.configuration.JWTProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootJwtApplicationTests {

    @Autowired
    JWTProperties jwtProperties;

    @Test
    void contextLoads() {
        System.out.println(jwtProperties.toString());
    }

}

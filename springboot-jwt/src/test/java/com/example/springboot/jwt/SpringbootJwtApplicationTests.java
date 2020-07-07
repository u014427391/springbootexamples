package com.example.springboot.jwt;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.springboot.jwt.configuration.JWTProperties;
import com.example.springboot.jwt.core.userdetails.JWTUserDetails;
import com.example.springboot.jwt.util.JWTTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import javax.sql.DataSource;

@SpringBootTest
class SpringbootJwtApplicationTests {

    @Autowired
    JWTProperties jwtProperties;
    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() {
        System.out.println(jwtProperties.toString());
    }

    @Test
    void testJwtToken() {
        UserDetails userDetails = new JWTUserDetails();
        ((JWTUserDetails) userDetails).setUserId(1L);
        ((JWTUserDetails) userDetails).setUsername("123123");
        ((JWTUserDetails) userDetails).setIsEnabled(true);
        ((JWTUserDetails) userDetails).setIsAccountNonExpired(false);
        System.out.println(jwtTokenUtil.generateToken(userDetails));
    }

    @Test
    void testDataSource(){
        System.out.println(dataSource);
    }

}

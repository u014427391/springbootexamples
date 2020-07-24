package com.example.springboot.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SpringbootSecurityApplicationTests {

    @Test
    void contextLoads() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //加密"123" $2a$10$A508vRns6gDSnhy324uygei5rQx6VN5OB5cpWqvKjiPtWYaMz6SD2
        String encode = bCryptPasswordEncoder.encode("123");
        System.out.println(encode);
    }

}

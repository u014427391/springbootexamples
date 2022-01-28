package com.example.mongodb;

import com.example.mongodb.model.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
class SpringbootMongodbApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
        UserModel user = UserModel.builder()
                .id(1L)
                .name("admin")
                .email("123456@qq.com")
                .build();
        mongoTemplate.insert(user);

    }

}

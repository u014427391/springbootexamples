package com.example.mongodb;

import com.example.mongodb.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
class SpringbootMongodbApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {}

    @Test
    void insert() {
        User user = User.builder()
                .id(10L)
                .name("admin")
                .age(18)
                .email("123456@qq.com")
                .build();
        mongoTemplate.insert(user , "user");
    }

    @Test
    void update() {
        User user = mongoTemplate.findOne(
                Query.query(Criteria.where("_id").is(1L)), User.class);
        user.setName("testUser1");
        mongoTemplate.save(user , "user");
    }

    @Test
    void updateFirst() {
        // 多条相同的数据，只会更新第一条
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("name").is("User1")),
                Update.update("name" , "James"), User.class);
    }

    @Test
    void updateMulti() {
        // 多条相同的数据，都会根据条件更新
        mongoTemplate.updateMulti(
                Query.query(Criteria.where("name").is("User1")),
                Update.update("name" , "James"), User.class);
    }

    @Test
    void findAndModify() {
        // 和updateFirst类似，不过会返回更新的数据
        User user = mongoTemplate.findAndModify(
                Query.query(Criteria.where("name").is("User1")) ,
                Update.update("name" , "James"), User.class);
        System.out.println(user);
    }

    @Test
    void upsert() {
        // 查得到数据更新，查不到会更新一条
        mongoTemplate.upsert(
                Query.query(Criteria.where("name").is("User10")) ,
                Update.update("name" , "James"), User.class);

    }

    @Test
    void remove() {
        User user = User.builder()
                .id(10L)
                .name("test")
                .email("test@qq.com")
                .build();
        mongoTemplate.remove(user);
    }

    @Test
    void count(){
        long count = mongoTemplate.count(
                Query.query(Criteria.where("name").is("User1")) ,
                User.class);
        System.out.println(count);
    }

}

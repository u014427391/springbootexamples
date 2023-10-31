package com.example.mongodb;


import com.example.mongodb.model.User;
import com.example.mongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ApplicationRunner implements CommandLineRunner {

    @Autowired
    @Qualifier(value = "userRepository")
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        this.init();
    }

    private void init() {
        userRepository.deleteAll();
        List<User> users = Stream.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L ,9L).map(i -> {
            User user = new User();
            user.setId(i);
            user.setName("User" + i);
            user.setAge(18);
            user.setEmail("test" + i + "@qq.com");
            return user;
        }).collect(Collectors.toList());
        userRepository.saveAll(users);
    }

}

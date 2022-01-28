package com.example.mongodb;


import com.example.mongodb.model.UserModel;
import com.example.mongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

        UserModel user1 = UserModel.builder()
                .id(1L)
                .age(18)
                .name("Jone")
                .email("test1@qq.com")
                .build();
        UserModel user2 = UserModel.builder()
                .id(2L)
                .age(18)
                .name("Jack")
                .email("test2@qq.com")
                .build();

        userRepository.insert(user1);
        userRepository.insert(user2);
    }

}

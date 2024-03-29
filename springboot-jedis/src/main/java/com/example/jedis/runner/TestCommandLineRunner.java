package com.example.jedis.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
@Slf4j
public class TestCommandLineRunner implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        log.info("TestCommandLineRunner");
    }

}

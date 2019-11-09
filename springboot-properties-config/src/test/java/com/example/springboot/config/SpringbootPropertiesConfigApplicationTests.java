package com.example.springboot.config;

import com.example.springboot.config.bean.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootPropertiesConfigApplicationTests {

	@Autowired
	User user;

	@Test
	public void testConfigurationProperties(){
		System.out.println(user);
	}

	@Test
	void contextLoads() {
	}

}

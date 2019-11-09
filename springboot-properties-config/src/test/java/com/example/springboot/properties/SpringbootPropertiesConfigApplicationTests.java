package com.example.springboot.config;

import com.example.springboot.config.bean.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringbootPropertiesConfigApplicationTests {

	@Autowired
	User user;

	@Autowired
	ApplicationContext ioc;

	@Test
	public void testConfigurationProperties(){
		System.out.println(user);
	}

	@Test
	void contextLoads() {
		boolean flag =  ioc.containsBean("testBean")
	}

}

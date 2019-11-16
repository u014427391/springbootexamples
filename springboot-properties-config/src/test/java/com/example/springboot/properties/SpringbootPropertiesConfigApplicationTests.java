package com.example.springboot.properties;

import com.example.springboot.properties.bean.User;
import com.example.springboot.properties.config.AppConfig;
import com.example.springboot.properties.test.X;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
		boolean flag =  ioc.containsBean("testService1");
		System.out.println(flag);

		AnnotationConfigApplicationContext ioc  = new AnnotationConfigApplicationContext();
		ioc.register(AppConfig.class);
		ioc.refresh();
		System.out.println(ioc.getBean(X.class));

	}

}

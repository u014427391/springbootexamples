package com.example.springboot.properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ImportResource(locations = {"classpath:beans.xml"})
public class SpringbootPropertiesConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootPropertiesConfigApplication.class, args);
	}

}

package com.example.springboot.hateoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class SpringbootHateoasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootHateoasApplication.class, args);
	}

}

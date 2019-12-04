package com.example.springboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

@SpringBootApplication
//@ComponentScan
//@EnableWebMvc
public class SpringbootWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWebApplication.class, args);
	}

	@Bean
	public ViewResolver testViewResolver(){
		return new TestViewResolver();
	}

	protected static class TestViewResolver implements ViewResolver{
		@Override
		public View resolveViewName(String viewName, Locale locale) throws Exception {
			return null;
		}
	}

}

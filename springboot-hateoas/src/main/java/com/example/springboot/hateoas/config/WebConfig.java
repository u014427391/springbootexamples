package com.example.springboot.hateoas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@EnableHypermediaSupport(type=EnableHypermediaSupport.HypermediaType.HAL)
public class WebConfig extends WebMvcConfigurerAdapter {

}

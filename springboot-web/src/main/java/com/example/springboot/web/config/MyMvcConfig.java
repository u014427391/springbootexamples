package com.example.springboot.web.config;

import com.example.springboot.web.component.MessageLocalResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年11月23日  修改内容:
 * </pre>
 */
@Configuration
@ComponentScan(basePackages = { "com.example.springboot.web" })
@Order(0)
//@EnableWebMvc
public class MyMvcConfig implements WebMvcConfigurer{

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
       //registry.addViewController("/web").setViewName("success");
        registry.addViewController("/").setViewName("login");
        //registry.addViewController("/login.html").setViewName("login");
    }


    @Bean
    public LocaleResolver messageLocalResolver(){
        MessageLocalResolver messageLocalResolver = new MessageLocalResolver();
        return messageLocalResolver;
    }

//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() {
//        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
//        lci.setParamName("lang");
//        return lci;
//    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor());
//    }


}

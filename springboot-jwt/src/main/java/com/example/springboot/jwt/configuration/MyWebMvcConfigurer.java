package com.example.springboot.jwt.configuration;

import com.example.springboot.jwt.core.message.MessagesLocalResolver;
import com.example.springboot.jwt.web.filter.JWTAuthenticationTokenFilter;
import com.example.springboot.jwt.web.handler.SecurityHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Arrays;

/**
 * <pre>
 *  MyWebMvcConfigurer
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/07 13:52  修改内容:
 * </pre>
 */
@Configuration
@EnableConfigurationProperties({ WebMvcProperties.class})
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private JWTProperties jwtProperties;
    @Autowired
    WebMvcProperties webMvcProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityHandlerInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JWTAuthenticationTokenFilter(jwtProperties);
    }

    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(jwtAuthenticationTokenFilter());
        return registrationBean;
    }


    /**
     * 自定义LocalResolver
     * @Author nicky.ma
     * @Date 2019/11/24 13:45
     * @return org.springframework.web.servlet.LocaleResolver
     */
    @Bean
    public LocaleResolver localeResolver(){
        MessagesLocalResolver localResolver = new MessagesLocalResolver();
        localResolver.setDefaultLocale(webMvcProperties.getLocale());
        return localResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

}

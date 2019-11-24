package com.example.springboot.i18n.config;

import com.example.springboot.i18n.component.MessagesLocalResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * <pre>
 *
 * </pre>
 * <p>
 * <pre>
 * @author nicky.ma
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019/11/24 11:15  修改内容:
 * </pre>
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer{

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }

//    @Bean
//    public LocaleResolver sessionLocaleResolver() {
//        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
//        // set default locale
//        sessionLocaleResolver.setDefaultLocale(Locale.US);
//        return sessionLocaleResolver;
//    }
//
//    @Bean
//    public LocaleResolver cookieLocaleResolver() {
//        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
//        cookieLocaleResolver.setCookieName("Language");
//        cookieLocaleResolver.setCookieMaxAge(1000);
//        return cookieLocaleResolver;
//    }
//
//    @Bean
//    public LocaleResolver customLocaleResolver(){
//        MessagesLocalResolver messagesLocalResolver = new MessagesLocalResolver();
//        return messagesLocalResolver;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LocaleChangeInterceptor()).addPathPatterns("/**");
//    }



}

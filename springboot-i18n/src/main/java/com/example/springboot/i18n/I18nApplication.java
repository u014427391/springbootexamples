package com.example.springboot.i18n;

import com.example.springboot.i18n.component.MessagesLocalResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@SpringBootApplication
public class I18nApplication {

	public static void main(String[] args) {
		SpringApplication.run(I18nApplication.class, args);
	}

//	@Bean
//    public LocaleResolver sessionLocaleResolver() {
//        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
//        // set default locale
//        sessionLocaleResolver.setDefaultLocale(Locale.US);
//        return sessionLocaleResolver;
//    }


	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver slr = new CookieLocaleResolver();
		slr.setCookieMaxAge(1000);
		slr.setCookieName("Language");
		return slr;
	}

//	@Bean
//	public LocaleResolver customLocaleResolver(){
//		MessagesLocalResolver messagesLocalResolver = new MessagesLocalResolver();
//		return messagesLocalResolver;
//	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			//拦截器
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(new LocaleChangeInterceptor()).addPathPatterns("/**");
			}
		};
	}

}

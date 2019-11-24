package com.example.springboot.i18n.config;

import com.example.springboot.i18n.component.CustomLocalResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * <pre>
 *  I18nConfig配置类
 * </pre>
 * <p>
 * <pre>
 * @author nicky.ma
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019/11/24 11:15  修改内容:
 * </pre>
 */
@Configuration
@EnableConfigurationProperties({ WebMvcProperties.class})
public class I18nConfig implements WebMvcConfigurer{

    @Autowired
    WebMvcProperties webMvcProperties;

    /**
     * 定义视图解析器，拦截/请求，都转跳到login页面
     * @Author nicky.ma
     * @Date 2019/11/24 13:51
     * @Param [registry]
     * @return void
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }

    /**
     * 定义SessionLocaleResolver
     * @Author nicky.ma
     * @Date 2019/11/24 13:52
     * @return org.springframework.web.servlet.LocaleResolver
     */
//    @Bean
//    public LocaleResolver localeResolver() {
//        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
//        // set default locale
//        sessionLocaleResolver.setDefaultLocale(Locale.US);
//        return sessionLocaleResolver;
//    }

    /**
     * 定义CookieLocaleResolver
     * @Author nicky.ma
     * @Date 2019/11/24 13:51
     * @return org.springframework.web.servlet.LocaleResolver
     */
//    @Bean
//    public LocaleResolver localeResolver() {
//        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
//        cookieLocaleResolver.setCookieName("Language");
//        cookieLocaleResolver.setCookieMaxAge(1000);
//        return cookieLocaleResolver;
//    }

    /**
     * 自定义LocalResolver
     * @Author nicky.ma
     * @Date 2019/11/24 13:45
     * @return org.springframework.web.servlet.LocaleResolver
     */
    @Bean
    public LocaleResolver localeResolver(){
        CustomLocalResolver localResolver = new CustomLocalResolver();
        localResolver.setDefaultLocale(webMvcProperties.getLocale());
        return localResolver;
    }

    /**
     * 定义localeChangeInterceptor
     * @Author nicky.ma
     * @Date 2019/11/24 13:45
     * @return org.springframework.web.servlet.i18n.LocaleChangeInterceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        //默认的请求参数为locale，eg: login?locale=zh_CN
        localeChangeInterceptor.setParamName(LocaleChangeInterceptor.DEFAULT_PARAM_NAME);
        return localeChangeInterceptor;
    }

    /**
     * 注册拦截器
     * @Author nicky.ma
     * @Date 2019/11/24 13:47
     * @Param [registry]
     * @return void
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/**");
    }



}

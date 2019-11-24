package com.example.springboot.i18n.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

}

package com.example.springboot.properties.config;

import com.example.springboot.properties.service.TestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年11月09日  修改内容:
 * </pre>
 */
@Configuration
public class PerpertiesConfig {

    @Bean
    public TestService testService1(){
        return new TestService();
    }
}

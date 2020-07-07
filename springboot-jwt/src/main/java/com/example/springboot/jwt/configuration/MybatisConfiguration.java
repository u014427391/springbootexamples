package com.example.springboot.jwt.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <pre>
 * Mybatis配置类
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年12月15日  修改内容:
 * </pre>
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.example.springboot.jwt.mapper"})
public class MybatisConfiguration {

	//配置支持驼峰命名和大小写自动转换
    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer(){
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}

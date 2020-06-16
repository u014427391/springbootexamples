package com.example.springboot.oauth2.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <pre>
 *  Mybatis配置
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/06/15 15:33  修改内容:
 * </pre>
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.example.springboot.oauth2.mapper"})
public class MybatisConfig {

    /**
     * 配置支持驼峰命名和大小写自动转换
     * @Author mazq
     * @Date 2020/06/15 15:34
     * @Param []
     */
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

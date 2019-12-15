package com.example.springboot.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <pre>
 *
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
@ComponentScan
@MapperScan(basePackages = {"com.example.springboot.mybatis.mapper"})
public class MybatisConfig {

//    @Autowired
//    private DataSource dataSource;

    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer(){
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }

//    @Primary
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory() throws Exception{
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(dataSource);
//        factoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
//        factoryBean.setConfigLocation(new ClassPathResource("/mybatis/mybatis-config.xml"));
//        factoryBean.setMapperLocations(new ClassPathResource("/mybatis/mapper/*.xml"));
//        return factoryBean.getObject();
//    }
//
//    @Primary
//    @Bean
//    public DataSourceTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dataSource);
//    }
}

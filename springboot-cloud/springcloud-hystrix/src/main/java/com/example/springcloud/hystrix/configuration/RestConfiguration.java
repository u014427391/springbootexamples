package com.example.springcloud.hystrix.configuration;

import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <pre>
 *  RestConfiguration
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/31 09:43  修改内容:
 * </pre>
 */
@Configuration
public class RestConfiguration {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

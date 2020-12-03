package com.example.springcloud.ribbon.configuration;

import com.example.springcloud.ribbon.component.MyRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.cloud.netflix.ribbon.ZonePreferenceServerListFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 *   Ribbon Clients configuration
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/29 14:22  修改内容:
 * </pre>
 */
@Configuration(proxyBeanMethods = false)
@IgnoreComponentScan
public class RibbonClientConfiguration {

//    @Autowired
//    IClientConfig config;

    @Bean
    public IRule roundRobinRule() {
        return new MyRule();
    }

    @Bean
    public ZonePreferenceServerListFilter serverListFilter() {
        ZonePreferenceServerListFilter filter = new ZonePreferenceServerListFilter();
        filter.setZone("myTestZone");
        return filter;
    }

    @Bean
    public IPing ribbonPing() {
        return new PingUrl();
    }

}

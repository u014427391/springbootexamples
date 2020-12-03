package com.example.springcloud.ribbon.configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ConfigurationBasedServerList;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * <pre>
 *      Customizing the Default Ribbon Clients
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/29 14:26  修改内容:
 * </pre>
 */
//@RibbonClient(name = "defaultRibbonClient",configuration = RibbonClientConfiguration.class)
//@RibbonClient(name = "eureka-service-provider",configuration = RibbonClientConfiguration.class)
public class DefaultRibbonClients {

    public static class BazServiceList extends ConfigurationBasedServerList {
        public BazServiceList(IClientConfig config) {
            super.initWithNiwsConfig(config);
        }
    }

}

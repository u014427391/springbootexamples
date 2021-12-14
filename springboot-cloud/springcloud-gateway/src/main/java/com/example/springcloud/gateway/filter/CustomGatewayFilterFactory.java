package com.example.springcloud.gateway.filter;

import com.example.springcloud.gateway.util.EncryptUtils;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Date;

/**
 * <pre>
 *      自定义网关过滤器
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/12/14 09:59  修改内容:
 * </pre>
 */
public class CustomGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomGatewayFilterFactory.Config>
implements Ordered {

    CustomGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String passToken = exchange.getRequest().getHeaders().getFirst("passToken");
            long now = new Date().getTime();
            String timestamp = Long.toString((long)Math.floor(now/1000));
            String signature = "";
            try {
                signature = EncryptUtils.toSHA256(timestamp + passToken + timestamp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("passid", config.getPaasid())
                    .header("serviceId", config.getServiceId())
                    .header("signature", signature)
                    .build();
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Data
    public static class Config {
        private String paasid;
        private String serviceId;
    }
}

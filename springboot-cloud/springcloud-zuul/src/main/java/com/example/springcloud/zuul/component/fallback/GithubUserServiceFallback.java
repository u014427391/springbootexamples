package com.example.springcloud.zuul.component.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.exception.HystrixTimeoutException;

/**
 * <pre>
 *      Hystrix 后备类(fallback)
 * </pre>
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/08/06 11:30  修改内容:
 * </pre>
 */
@Component
public class GithubUserServiceFallback implements FallbackProvider {
    private static final String DEFAULT_MESSAGE = "information is not available.";

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if (cause instanceof HystrixTimeoutException) {
            return new GatewayClientResponse(HttpStatus.GATEWAY_TIMEOUT, DEFAULT_MESSAGE);
        } else {
            return new GatewayClientResponse(HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_MESSAGE);
        }
    }
}

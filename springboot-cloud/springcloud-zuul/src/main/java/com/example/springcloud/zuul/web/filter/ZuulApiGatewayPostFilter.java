package com.example.springcloud.zuul.web.filter;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.RequestContent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * <pre>
 *      API Gateway后置过滤器
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/08/06 10:05  修改内容:
 * </pre>
 */
@Slf4j
//@Component
public class ZuulApiGatewayPostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        try (final InputStream responseDataStream = context.getResponseDataStream()) {
            if(responseDataStream == null) {
                log.warn("RESPONSE BODY: {}", "");
                return null;
            }
            String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
            log.info("RESPONSE BODY: {}", responseData);
            context.setResponseBody(responseData);
        }
        catch (Exception e) {
            throw new ZuulException(e, INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
        return null;
    }
}

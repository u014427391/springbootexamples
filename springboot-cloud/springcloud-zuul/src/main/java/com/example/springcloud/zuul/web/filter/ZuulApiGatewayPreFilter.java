package com.example.springcloud.zuul.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORWARD_TO_KEY;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVICE_ID_KEY;

/**
 * <pre>
 *  API网关预过滤器
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/08/05 18:08  修改内容:
 * </pre>
 */
@Slf4j
//@Component
public class ZuulApiGatewayPreFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return PRE_TYPE;
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
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String accessToken = request.getParameter("token");
        if (StringUtils.isEmpty(accessToken)) {
            // zuul过滤该请求，不进行路由
            ctx.setSendZuulResponse(false);
            // 设置返回的错误码
            ctx.setResponseStatusCode(403);
            ctx.setResponseBody("AccessToken is Invalid ");
            return null;
        }
        log.info("accessToken: {}",accessToken);
        // 否则业务继续执行
        return null;
    }
}

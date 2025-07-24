package com.example.ratelimit.keygenerator;

import com.example.ratelimit.core.RateLimitContext;
import com.example.ratelimit.keygenerator.KeyGenerator;
import com.example.ratelimit.util.IpUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class DefaultKeyGenerator implements KeyGenerator {

    @Override
    public String generate() {
        HttpServletRequest req = ((ServletRequestAttributes)
            RequestContextHolder.currentRequestAttributes()).getRequest();
        return "rate:" + req.getRequestURI() + ":" + IpUtil.getIpAddr(req);
    }
}
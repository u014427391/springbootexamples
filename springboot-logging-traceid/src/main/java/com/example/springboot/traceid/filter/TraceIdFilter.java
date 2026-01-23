package com.example.springboot.traceid.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * TraceId过滤器，为每个请求生成唯一的traceId
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class TraceIdFilter extends OncePerRequestFilter {

    public static final String TRACE_ID = "traceId";
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        // 尝试从请求头中获取traceId，如果没有则生成一个新的
        String traceId = request.getHeader("X-Trace-Id");
        if (traceId == null || traceId.isEmpty()) {
            traceId = generateTraceId();
        }
        
        // 将traceId设置到MDC中
        MDC.put(TRACE_ID, traceId);
        
        try {
            // 将traceId添加到响应头中，方便追踪
            response.addHeader("X-Trace-Id", traceId);
            
            // 打印请求开始日志（包含traceId）
            log.info("Request started - Method: {}, URI: {}, RemoteAddr: {}", 
                    request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
            
            // 继续过滤器链
            filterChain.doFilter(request, response);
            
            // 打印请求完成日志（包含traceId）
            log.info("Request completed - Method: {}, URI: {}, Status: {}", 
                    request.getMethod(), request.getRequestURI(), response.getStatus());
            
        } finally {
            // 请求完成后清理MDC中的traceId
            MDC.clear();
        }
    }
    
    private String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
}
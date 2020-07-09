package com.example.springboot.jwt.web.handler;

import com.example.springboot.jwt.core.jwt.annotation.JWTPermitAll;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 *  SecurityHandlerInterceptor
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/07 13:47  修改内容:
 * </pre>
 */
public class SecurityHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            JWTPermitAll jwtPermitAll = handlerMethod.getMethodAnnotation(JWTPermitAll.class);
            if (jwtPermitAll != null) {
                return true;
            }
        }
        return super.preHandle(request,response,handler);
    }
}

package com.example.springboot.async.exception;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * <pre>
 *  Copy @ https://www.baeldung.com/spring-async
 * </pre>
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/20 11:08  修改内容:
 * </pre>
 */
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler  {

    @Override
    public void handleUncaughtException(
            Throwable throwable, Method method, Object... obj) {
        System.out.println("Exception message - " + throwable.getMessage());
        System.out.println("Method name - " + method.getName());
        for (Object param : obj) {
            System.out.println("Parameter value - " + param);
        }
    }
}

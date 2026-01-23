package com.example.springboot.traceid.utils;

import org.slf4j.MDC;

/**
 * MDC工具类，用于操作MDC中的traceId
 */
public class MdcUtil {

    public static final String TRACE_ID = "traceId";

    /**
     * 获取当前请求的traceId
     * @return traceId，如果不存在则返回null
     */
    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    /**
     * 设置traceId到MDC
     * @param traceId traceId
     */
    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    /**
     * 清理MDC中的traceId
     */
    public static void clearTraceId() {
        MDC.remove(TRACE_ID);
    }

    /**
     * 获取当前traceId，如果不存在则返回默认值
     * @param defaultValue 默认值
     * @return traceId或默认值
     */
    public static String getTraceIdOrDefault(String defaultValue) {
        String traceId = getTraceId();
        return traceId != null ? traceId : defaultValue;
    }
}
package com.example.resttemplate.configuration;


import cn.hutool.core.bean.BeanUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RestTemplateWrapper extends RestTemplate {

    private RestTemplate restTemplate;

    public RestTemplateWrapper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Nullable
    public <T> T getForObject(String url, Class<T> responseType, Object bean) throws RestClientException {
        Map<String, Object> paramMap = null;
        try {
            paramMap = BeanUtil.beanToMap(bean);
        } catch (Exception e) {
            logger.error("参数处理异常:{}" , e);
        }
        return restTemplate.getForObject(url , responseType, paramMap);
    }

}

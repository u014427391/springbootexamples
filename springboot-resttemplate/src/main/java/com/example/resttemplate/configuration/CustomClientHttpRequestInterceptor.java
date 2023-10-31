package com.example.resttemplate.configuration;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Slf4j
public class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {


    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        trackRequest(httpRequest , bytes);
        ClientHttpResponse httpResponse = clientHttpRequestExecution.execute(httpRequest , bytes);
        trackResponse(httpResponse);
        return httpResponse;
    }

    private void trackRequest(HttpRequest httpRequest, byte[] bytes) {
        log.info("=======================request start=================================================");
        log.info("Headers        : {}", httpRequest.getHeaders());
        log.info("Request URI    : {}", httpRequest.getURI());
        log.info("Request Method : {}", httpRequest.getMethod());
        log.info("Request Body   : {}" , Convert.toStr(bytes));
        log.info("=======================request end=================================================");
    }

    private void trackResponse(ClientHttpResponse httpResponse) throws IOException {
        log.info("=======================response start=================================================");
        log.info("Status code  : {}", httpResponse.getStatusCode());
        log.info("Status text  : {}", httpResponse.getStatusText());
        log.info("Headers      : {}", httpResponse.getHeaders());
        log.info("=======================response end=================================================");

    }

}

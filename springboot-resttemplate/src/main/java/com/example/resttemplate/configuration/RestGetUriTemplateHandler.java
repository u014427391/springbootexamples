package com.example.resttemplate.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;
import java.util.Map;

@Slf4j
public class RestGetUriTemplateHandler implements UriTemplateHandler {

    private UriTemplateHandler uriTemplateHandler = new DefaultUriBuilderFactory();


    @Override
    public URI expand(String uriTemplate, Map<String, ?> uriVariables) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(uriTemplate);
        for (Map.Entry<String, ?> entry : uriVariables.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }
        String uriString = builder.toUriString();
        log.info("uriString:[{}]" , uriString);
        return uriTemplateHandler.expand(uriString , uriVariables);
    }

    @Override
    public URI expand(String uriTemplate, Object... uriVariables) {
        return uriTemplateHandler.expand(uriTemplate, uriVariables);
    }
}

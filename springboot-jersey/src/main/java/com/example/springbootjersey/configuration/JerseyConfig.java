package com.example.springbootjersey.configuration;

import com.example.springbootjersey.endpoint.FileServerEndpoint;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/server")
public class JerseyConfig extends ResourceConfig{

    public JerseyConfig() {
        register(FileServerEndpoint.class);
        register(MultiPartFeature.class);
    }
}

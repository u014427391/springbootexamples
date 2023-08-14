package com.example.jbossuploadfile.configuration;

import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Component
@ApplicationPath("/rest/")
public class JAXRSApplication extends Application {
}

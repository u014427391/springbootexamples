package com.example.jedis.listener;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;

@Slf4j
public class TestSpringApplicationRunListener implements SpringApplicationRunListener {

    private final SpringApplication application;
    private final String[] args;

    public TestSpringApplicationRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }


    @Override
    public void starting() {
        log.info("starting...");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        log.info("environmentPrepared...");

    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("contextPrepared...");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("contextLoaded...");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        log.info("started...");
    }

    @SneakyThrows
    @Override
    public void running(ConfigurableApplicationContext context) {
        log.info("running...");
        ConfigurableEnvironment environment = context.getEnvironment();
        String port = environment.getProperty("server.port");
        String contextPath = environment.getProperty("server.servlet.context-path");
        String docPath = port + "" + contextPath + "/doc.html";
        String externalAPI = InetAddress.getLocalHost().getHostAddress();

        log.info("\n Swagger API: "
                        + "Local-API: \t\thttp://127.0.0.1:{}\n\t"
                        + "External-API: \thttp://{}:{}\n\t",
                docPath, externalAPI, docPath);
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info("failed...");
    }
}

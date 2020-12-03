package com.example.spirngcloud.client;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringcloudEurekaClientApplicationTests {

    @Autowired
    private EurekaClient discoveryClient;

    @Test
    void serviceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("EUREKA-CLIENT1", false);
        System.out.println(instance.getHomePageUrl());
    }

    @Test
    void contextLoads() {
    }

}

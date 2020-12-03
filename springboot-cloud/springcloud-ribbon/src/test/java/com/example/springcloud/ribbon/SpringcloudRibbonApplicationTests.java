package com.example.springcloud.ribbon;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
import com.netflix.loadbalancer.reactive.ServerOperation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import rx.Observable;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SpringcloudRibbonApplicationTests {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Test
    void contextLoads() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("EUREKA-SERVICE-PROVIDER");
        URI uri = URI.create(String.format("http://%s:%s", serviceInstance.getHost() , serviceInstance.getPort()));
        System.out.println(uri.toString());
    }

    @Test
    void testLoadBalancer(){
        // 服务列表
        List<Server> serverList = Arrays.asList(new Server("localhost", 8083), new Server("localhost", 8084));
        // 构建负载实例
        BaseLoadBalancer loadBalancer = LoadBalancerBuilder.newBuilder().buildFixedServerListLoadBalancer(serverList);
        loadBalancer.setRule(new RandomRule());
        for (int i = 0; i < 5; i++) {
            String result = LoadBalancerCommand.<String>builder().withLoadBalancer(loadBalancer).build()
                    .submit(new ServerOperation<String>() {
                        public Observable<String> call(Server server) {
                            try {
                                String address = "http://" + server.getHost() + ":" + server.getPort()+"/EUREKA-SERVICE-PROVIDER/api/users/mojombo";
                                System.out.println("调用地址：" + address);
                                return Observable.just("");
                            } catch (Exception e) {
                                return Observable.error(e);
                            }
                        }
                    }).toBlocking().first();
            System.out.println("result：" + result);
        }
    }

}

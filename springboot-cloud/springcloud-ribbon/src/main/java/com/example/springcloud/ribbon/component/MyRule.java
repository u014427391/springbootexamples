package com.example.springcloud.ribbon.component;
 
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class MyRule extends AbstractLoadBalancerRule
{
    // 总共被调用的次数，目前要求每台被调用5次
    private int total = 0;
    // 当前提供服务的机器号
    private int index = 0;

    public Server choose(ILoadBalancer lb, Object key)
    {
        if (lb == null) {
            return null;
        }
        Server server = null;

        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }
            // 获取可用的服务列表
            List<Server> upList = lb.getReachableServers();
            // 获取所有服务列表
            List<Server> allList = lb.getAllServers();

            int serverCount = allList.size();
            if (serverCount == 0) {
               // 没有获取到服务
                return null;
            }

            //int index = chooseRandomInt(serverCount);
            //server = upList.get(index);
            if(total < 5)
            {
                server = upList.get(index);
                total++;
            }else {
                total = 0;
                index++;
                if(index >= upList.size())
                {
                    index = 0;
                }
            }

            if (server == null) {
                // 释放线程
                Thread.yield();
                continue;
            }

            if (server.isAlive()) {
                return (server);
            }

            server = null;
            Thread.yield();
        }

        return server;
    }

    protected int chooseRandomInt(int serverCount) {
        return ThreadLocalRandom.current().nextInt(serverCount);
    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }
}

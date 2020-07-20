package com.example.springboot.async.service;

import com.example.springboot.async.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * <pre>
 *  GitHubLookupService
 * copy @https://spring.io/guides/gs/async-method/
 * </pre>
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/20 10:18  修改内容:
 * </pre>
 */
@Service
public class GitHubLookupService {

    private static final Logger LOG = LoggerFactory.getLogger(GitHubLookupService.class);

    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    //@Async("threadPoolTaskExecutor")
    public Future<User> findUser(String user) throws InterruptedException {
        LOG.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url, User.class);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }

}

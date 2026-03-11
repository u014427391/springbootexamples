package com.example.ai.advisor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class SimpleLoggerAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {


    @Override
    public AdvisedResponse aroundCall(AdvisedRequest req, CallAroundAdvisorChain chain) {
        log.info("🚀 请求前: {}", req.userText());
        AdvisedResponse res = chain.nextAroundCall(req);  // 继续执行链
        log.info("✅ 响应后: {}", res.response());
        return res;
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest req, StreamAroundAdvisorChain chain) {
        log.info("🌊 流式请求前: {}", req.userText());

        return chain.nextAroundStream(req)
                .doOnNext(res -> log.info("📝 流式响应: {}", res));
    }

    @Override
    public String getName() {
        return "simpleLoggerAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

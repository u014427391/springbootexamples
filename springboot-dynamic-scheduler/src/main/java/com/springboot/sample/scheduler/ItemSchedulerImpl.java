package com.springboot.sample.scheduler;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
@Component
@Slf4j
public class ItemSchedulerImpl extends AbstractScheduler{

    @Value("${schedule.cron}")
    private String defaultCron;

    private String cronString;

    @Override
    protected String getCronString() {
        return cronString;
    }

    @Override
    protected void doBusiness() {
        log.info("执行业务...");
    }

    @Override
    protected String getDefaultCron() {
        return defaultCron;
    }

    @Override
    protected Lock getLock() {
        return new ReentrantLock();
    }
}

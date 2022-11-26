package com.springboot.sample.scheduler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.locks.Lock;

@Slf4j
@Component
@Data
public abstract class AbstractScheduler implements SchedulerTaskJob{

    @Resource(name = "taskScheduler")
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private Lock rLock;

    @Override
    public void executeTask() {
        String cron = getCronString();
        Runnable task = () -> {
            try {
                getLock().lock();
                // 执行业务
                doBusiness();
            } finally {
                rLock.unlock();
            }
        };
        Trigger trigger = new Trigger()   {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                CronTrigger trigger;
                try {
                    trigger = new CronTrigger(cron);
                    return trigger.nextExecutionTime(triggerContext);
                } catch (Exception e) {
                    log.error("cron表达式异常，已经启用默认配置");
                    // 配置cron表达式异常，执行默认的表达式
                    trigger = new CronTrigger(getDefaultCron());
                    return trigger.nextExecutionTime(triggerContext);
                }
            }
        };
        threadPoolTaskScheduler.schedule(task , trigger);
    }

    protected abstract String getCronString();

    protected abstract void doBusiness();

    protected abstract String getDefaultCron();

    protected abstract Lock getLock();

}

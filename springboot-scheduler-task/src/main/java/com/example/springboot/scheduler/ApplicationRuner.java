package com.example.springboot.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/22 13:36  修改内容:
 * </pre>
 */
@Component
@Slf4j
public class ApplicationRuner implements CommandLineRunner {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private CronTrigger cronTrigger;
    @Autowired
    @Qualifier("periodicTrigger")
    private PeriodicTrigger periodicTrigger;

    @Override
    public void run(String... args) throws Exception {
//        taskScheduler.schedule(new TaskThread(Thread.currentThread().getName()), new Date());
//        taskScheduler.scheduleWithFixedDelay(new TaskThread(Thread.currentThread().getName()), 1000);
//        taskScheduler.scheduleWithFixedDelay(new TaskThread(Thread.currentThread().getName()), new Date(), 1000);
//        taskScheduler.scheduleAtFixedRate(new TaskThread("Thread.currentThread().getName()), new Date(), 2000);
//        taskScheduler.scheduleAtFixedRate(new TaskThread(Thread.currentThread().getName()), 2000);
          taskScheduler.schedule(new TaskThread(Thread.currentThread().getName()), cronTrigger);
//        taskScheduler.schedule(new TaskThread(Thread.currentThread().getName()), periodicTrigger);
    }

    class TaskThread implements Runnable {
        String name;
        public TaskThread(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            log.info("task is run on thread :[{}],current time:[{}]",name,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
    }
}

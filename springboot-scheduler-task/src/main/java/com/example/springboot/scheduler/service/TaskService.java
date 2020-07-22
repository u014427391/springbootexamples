package com.example.springboot.scheduler.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <pre>
 *   TaskService
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/21 16:39  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class TaskService {

    @Scheduled(fixedDelay = 10000)
    public void testFixedDelay() {
        log.info("fixedDelay test,thread name:[{}],execute time:[{}]",Thread.currentThread().getName(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }


    @Scheduled(cron = "0 0/1 * * * ? ")
    public void testCron(){
        log.info("cron test,thread name:[{}],execute time:[{}]",Thread.currentThread().getName(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Scheduled(initialDelay = 5000, fixedRate = 10000)
    public void testFixedRate() {
        log.info("fixedRate test,thread name:[{}],execute time:[{}]",Thread.currentThread().getName(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}

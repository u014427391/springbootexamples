package com.example.springboot.controller;

import com.example.springboot.service.BatchInsertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BatchInsertController {

    private Logger log = LoggerFactory.getLogger(BatchInsertController.class);


    @Autowired
    private BatchInsertService batchInsertService;

    /**
     * 提供API接口，用于触发批量插入
     */
    @PostMapping("/batch-insert")
    public ResponseEntity<String> triggerBatchInsert(@RequestParam(defaultValue = "10000") int count) {
        if (count <= 0) {
            return ResponseEntity.badRequest().body("插入数量必须大于0");
        }

        try {
            batchInsertService.processBatchInsert(count);
            return ResponseEntity.accepted().body("批量插入任务已启动，将异步执行!");
        } catch (Exception e) {
            log.error("Failed to start batch insert", e);
            return ResponseEntity.internalServerError().body("启动批量插入失败");
        }
    }
}
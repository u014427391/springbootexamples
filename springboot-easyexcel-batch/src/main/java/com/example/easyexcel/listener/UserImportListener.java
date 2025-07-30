package com.example.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.easyexcel.model.User;
import com.example.easyexcel.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 用户数据导入监听器（独立类实现）
 */
@Slf4j
public class UserImportListener extends AnalysisEventListener<User> {

    // 批量保存阈值（可根据内存调整）
    private static final int BATCH_SIZE = 5000;
    
    // 临时存储批次数据
    private final List<User> batchList = new ArrayList<>(BATCH_SIZE);
    
    // 导入结果统计
    private final AtomicLong successCount = new AtomicLong(0);
    private final AtomicLong failCount = new AtomicLong(0);
    
    // 业务服务（通过构造器注入）
    private final UserService userService;

    public UserImportListener(UserService userService) {
        this.userService = userService;
    }

    /**
     * 每读取一行数据触发
     */
    @Override
    public void invoke(User user, AnalysisContext context) {
        // 数据验证
        if (validateUser(user)) {
            batchList.add(user);
            successCount.incrementAndGet();
            
            // 达到批次大小则保存
            if (batchList.size() >= BATCH_SIZE) {
                saveBatchData();
                // 清空列表释放内存
                batchList.clear();
            }
        } else {
            failCount.incrementAndGet();
            log.warn("数据验证失败: {}", user);
        }
    }

    /**
     * 所有数据读取完成后触发
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 处理剩余数据
        if (!batchList.isEmpty()) {
            saveBatchData();
            batchList.clear();
        }
        log.info("当前Sheet导入结束，成功: {}, 失败: {}", successCount.get(), failCount.get());
    }

    /**
     * 批量保存数据
     */
    private void saveBatchData() {
        try {
            // 调用业务层批量保存（带事务）
            userService.batchSaveUsers(batchList);
            log.debug("批量保存成功，数量: {}", batchList.size());
        } catch (Exception e) {
            log.error("批量保存失败，数量: {}", batchList.size(), e);
            // 失败处理：可记录失败数据到文件或数据库
            handleSaveFailure(batchList);
        }
    }

    /**
     * 数据验证逻辑
     */
    private boolean validateUser(User user) {
        // 基础字段验证（根据实际业务调整）
        if (user == null) return false;
        if (user.getId() == null) return false;
        if (user.getName() == null || user.getName().trim().isEmpty()) return false;
        return true;
    }

    /**
     * 处理保存失败的数据
     */
    private void handleSaveFailure(List<User> failedData) {
        // 实现失败数据的处理逻辑（例如写入失败日志表）
        // userService.saveFailedData(failedData);
    }

    // Getter方法用于统计结果
    public long getSuccessCount() {
        return successCount.get();
    }

    public long getFailCount() {
        return failCount.get();
    }
}

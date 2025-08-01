package com.example.easyexcel.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.easyexcel.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class ExcelExportService {

    private final ThreadPoolTaskExecutor excelExecutor;
    private final UserService userService;

    // 每个Sheet的数据量
    private static final int DATA_PER_SHEET = 100000;

    // 单次查询最小数据量（根据内存情况调整）
    private static final int MIN_BATCH_SIZE = 5000;
    // 单次查询最大数据量（防止OOM）
    private static final int MAX_BATCH_SIZE = 20000;

    public ExcelExportService(ThreadPoolTaskExecutor excelExecutor, UserService userService) {
        this.excelExecutor = excelExecutor;
        this.userService = userService;
    }

    /**
     * 流式导出百万级用户数据（无内存累积）
     */
    public void exportMillionUsers(HttpServletResponse response, long totalCount) throws IOException {
        // 设置响应头（支持流式输出）
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("百万用户数据", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        // 计算总Sheet数
        int sheetCount = (int) (totalCount / DATA_PER_SHEET + (totalCount % DATA_PER_SHEET > 0 ? 1 : 0));
        log.info("总Sheet数：{}，总数据量：{}", sheetCount, totalCount);

        // 直接使用响应输出流，避免中间字节缓存
        try (OutputStream os = response.getOutputStream()) {
            ExcelWriter excelWriter = EasyExcel.write(os, User.class).build();

            // 逐个处理Sheet，处理完立即释放
            for (int sheetNo = 0; sheetNo < sheetCount; sheetNo++) {
                long sheetStart = sheetNo * (long) DATA_PER_SHEET;
                long sheetEnd = Math.min((sheetNo + 1) * (long) DATA_PER_SHEET, totalCount);
                String sheetName = "用户数据" + (sheetNo + 1);

                log.info("开始处理Sheet[{}]：{} - {}条", sheetName, sheetStart, sheetEnd);

                // 创建Sheet并流式写入
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetNo, sheetName).build();
                streamWriteSheet(excelWriter, writeSheet, sheetStart, sheetEnd);

                log.info("Sheet[{}]处理完成，内存已释放", sheetName);
            }

            excelWriter.finish();
            log.info("所有数据导出完成");
        } catch (Exception e) {
            log.error("导出失败", e);
            throw new IOException("Excel导出失败", e);
        }
    }

    /**
     * 流式写入单个Sheet，数据不驻留内存
     */
    private void streamWriteSheet(ExcelWriter excelWriter, WriteSheet writeSheet, long start, long end) {
        long remaining = end - start;
        long currentPos = start;

        // 循环获取小批量数据，写入后立即释放
        while (remaining > 0) {
            // 动态调整批次大小（剩余少的时候用实际值，避免内存浪费）
            int batchSize = (int) Math.min(Math.min(remaining, MAX_BATCH_SIZE),
                    Math.max(remaining / 10, MIN_BATCH_SIZE));

            // 获取当前批次数据
            List<User> batchData = userService.findUsersByRange(currentPos, currentPos + batchSize);

            if (batchData == null || batchData.isEmpty()) {
                log.warn("数据中断，提前结束。当前位置：{}", currentPos);
                break;
            }

            // 写入数据（写入后EasyExcel会处理数据，无需保留）
            excelWriter.write(batchData, writeSheet);

            // 强制释放当前批次内存（关键步骤）
            releaseBatchMemory(batchData);

            // 更新进度
            remaining -= batchData.size();
            currentPos += batchData.size();
            log.debug("已写入 {} 条，剩余 {} 条", currentPos - start, remaining);
        }
    }

    /**
     * 彻底释放批次数据内存
     */
    private void releaseBatchMemory(List<User> batchData) {
        // 1. 清除列表元素引用
        batchData.clear();
        // 2. 如果是ArrayList，可手动置空内部数组（反射优化，可选）
        clearArrayListInternalArray(batchData);
        // 3. 解除外部引用
        batchData = null;
        // 4. 触发Minor GC（仅建议，不保证立即执行）
        System.gc();
    }

    /**
     * 反射清空ArrayList内部数组（进一步优化内存释放）
     */
    private void clearArrayListInternalArray(List<User> list) {
        if (list instanceof java.util.ArrayList) {
            try {
                java.lang.reflect.Field elementDataField = java.util.ArrayList.class.getDeclaredField("elementData");
                elementDataField.setAccessible(true);
                Object[] elementData = (Object[]) elementDataField.get(list);
                // 置空内部数组元素，彻底释放引用
                for (int i = 0; i < elementData.length; i++) {
                    elementData[i] = null;
                }
            } catch (Exception e) {
                log.debug("反射清空ArrayList内部数组失败（不影响主流程）", e);
            }
        }
    }
}

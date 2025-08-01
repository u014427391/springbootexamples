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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ExcelExportService {

    private final ThreadPoolTaskExecutor excelExecutor;
    private final UserService userService;

    // 每个Sheet的数据量
    private static final int DATA_PER_SHEET = 100000;

    // 每次查询的数据量
    private static final int QUERY_BATCH_SIZE = 10000;

    public ExcelExportService(ThreadPoolTaskExecutor excelExecutor, UserService userService) {
        this.excelExecutor = excelExecutor;
        this.userService = userService;
    }

    /**
     * 导出百万级用户数据（优化内存版本）
     */
    public void exportMillionUsers(HttpServletResponse response, long totalCount) throws IOException {
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("百万用户数据", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // 计算总Sheet数
        int sheetCount = (int) (totalCount / DATA_PER_SHEET + (totalCount % DATA_PER_SHEET > 0 ? 1 : 0));
        log.info("需要生成的Sheet总数：{}", sheetCount);

        try (OutputStream os = response.getOutputStream()) {
            // 创建ExcelWriter，直接写入响应输出流
            ExcelWriter excelWriter = EasyExcel.write(os, User.class).build();

            // 用于保证Sheet写入顺序的前一个Future
            CompletableFuture<Void> previousFuture = CompletableFuture.completedFuture(null);

            for (int sheetNo = 0; sheetNo < sheetCount; sheetNo++) {
                final int currentSheetNo = sheetNo;
                long start = currentSheetNo * (long) DATA_PER_SHEET;
                long end = Math.min((currentSheetNo + 1) * (long) DATA_PER_SHEET, totalCount);

                // 每个Sheet的处理依赖于前一个Sheet完成，保证顺序
                previousFuture = previousFuture.thenRunAsync(() -> {
                    try {
                        log.info("开始处理Sheet {} 的数据（{} - {}）", currentSheetNo, start, end);
                        writeSheetData(excelWriter, currentSheetNo, start, end);
                        log.info("完成处理Sheet {} 的数据", currentSheetNo);
                    } catch (Exception e) {
                        log.error("处理Sheet {} 数据失败", currentSheetNo, e);
                        throw new RuntimeException("处理Sheet " + currentSheetNo + " 数据失败", e);
                    }
                }, excelExecutor);
            }

            // 等待所有Sheet处理完成
            previousFuture.join();

            // 完成写入
            excelWriter.finish();
            log.info("所有Sheet写入完成");

        } catch (Exception e) {
            log.error("Excel导出失败", e);
            throw e;
        }
    }

    /**
     * 写入单个Sheet的数据
     */
    private void writeSheetData(ExcelWriter excelWriter, int sheetNo, long start, long end) {
        String sheetName = "用户数据" + (sheetNo + 1);
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetNo, sheetName).build();

        long totalToQuery = end - start;
        int totalWritten = 0;

        // 分批查询并写入，每批查询后立即写入，不缓存大量数据
        for (long i = 0; i < totalToQuery; i += QUERY_BATCH_SIZE) {
            long currentStart = start + i;
            long currentEnd = Math.min(start + i + QUERY_BATCH_SIZE, end);

            // 调用UserService查询数据
            List<User> batchData = userService.findUsersByRange(currentStart, currentEnd);

            if (batchData == null || batchData.isEmpty()) {
                log.info("{} - {} 范围没有数据", currentStart, currentEnd);
                break; // 没有更多数据，提前退出
            }

            // 直接写入这一批数据
            excelWriter.write(batchData, writeSheet);
            totalWritten += batchData.size();

            log.info("Sheet {} 已写入 {} - {} 范围的数据，累计 {} 条",
                    sheetName, currentStart, currentEnd, totalWritten);

            // 清除引用，帮助GC
            batchData = new ArrayList<>();
        }

        log.info("Sheet {} 写入完成，共 {} 条数据", sheetName, totalWritten);
    }
}

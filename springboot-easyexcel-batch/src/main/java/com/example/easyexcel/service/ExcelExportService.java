package com.example.easyexcel.service;

import cn.hutool.core.convert.Convert;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.easyexcel.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ExcelExportService {

    private final ThreadPoolTaskExecutor excelExecutor;
    private final UserService userService;

    // 每个Sheet的数据量
    private static final int DATA_PER_SHEET = 100000;

    // 每次写入的数据量
    private static final int WRITE_BATCH_SIZE = 10000;

    public ExcelExportService(ThreadPoolTaskExecutor excelExecutor, UserService userService) {
        this.excelExecutor = excelExecutor;
        this.userService = userService;
    }

    public void exportMillionUsers(HttpServletResponse response, long totalCount) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("百万用户数据", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 需要多少个Sheet
        int sheetCount = Convert.toInt(totalCount / DATA_PER_SHEET + (totalCount % DATA_PER_SHEET > 0 ? 1 : 0));

        // 创建ExcelWriter
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), User.class)
                .autoCloseStream(false)
                .build();

        try {
            CompletableFuture[] futures = new CompletableFuture[sheetCount];
            for (int sheetNo = 0; sheetNo < sheetCount; sheetNo++) {
                final int currentSheet = sheetNo;
                // 计算当前Sheet的数据范围
                long start = currentSheet * (long) DATA_PER_SHEET;
                long end  = Math.min((currentSheet + 1) * (long)DATA_PER_SHEET, totalCount);

                CompletableFuture.runAsync(() -> {
                    try {
                        writeSheetData(excelWriter, currentSheet, start, end);
                    } catch (Exception e) {
                        log.error("写入Sheet {} 数据失败", currentSheet, e);
                        throw new RuntimeException(e);
                    }
                }, excelExecutor);
            }

            CompletableFuture.allOf(futures).join();
            log.info("所有Sheet数据写入完成，共 {} 个Sheet", sheetCount);
        } finally {
            // 关闭ExcelWriter
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

    }

    private void writeSheetData(ExcelWriter excelWriter, int sheetNo, long start, long end) {
        log.info("开始处理Sheet {}，数据范围：{} - {}", sheetNo, start, end);

        WriteSheet writeSheet = EasyExcel.writerSheet(sheetNo, "用户数据" + sheetNo + 1)
                .build();

        for(long i = start; i < end; i += WRITE_BATCH_SIZE) {
            long batchEnd = Math.min(i + WRITE_BATCH_SIZE, end);
            List<User> users = userService.findUsersByRange(i, batchEnd);

            excelWriter.write(users, writeSheet);
            log.info("Sheet {} 已写入 {} 条数据", sheetNo, batchEnd - start);
        }
        log.info("Sheet {} 处理完成，共 {} 条数据", sheetNo, end - start);
    }

}

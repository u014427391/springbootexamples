package com.example.easyexcel.service;

import com.alibaba.excel.EasyExcel;

import com.alibaba.excel.ExcelWriter;

import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.easyexcel.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class ExcelExportService {

    private final ThreadPoolTaskExecutor excelExecutor;
    private final UserService userService;  // 注入UserService

    // 每个Sheet的数据量
    private static final int DATA_PER_SHEET = 100000;

    // 每次查询的数据量
    private static final int QUERY_BATCH_SIZE = 10000;

    // 构造函数注入UserService
    public ExcelExportService(ThreadPoolTaskExecutor excelExecutor, UserService userService) {
        this.excelExecutor = excelExecutor;
        this.userService = userService;
    }

    /**
     * 导出百万级用户数据（从UserService读取）
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

        // 内存中缓存所有Sheet的数据（键：sheetNo，值：数据列表）
        ConcurrentMap<Integer, List<User>> sheetDataMap = new ConcurrentHashMap<>(sheetCount);

        try {
            // 第一步：多线程并行从UserService查询所有数据并缓存
            CompletableFuture[] queryFutures = new CompletableFuture[sheetCount];
            for (int sheetNo = 0; sheetNo < sheetCount; sheetNo++) {
                final int currentSheetNo = sheetNo;
                long start = currentSheetNo * (long) DATA_PER_SHEET;
                long end = Math.min((currentSheetNo + 1) * (long) DATA_PER_SHEET, totalCount);

                queryFutures[currentSheetNo] = CompletableFuture.runAsync(() -> {
                    try {
                        log.info("开始查询Sheet {} 的数据（{} - {}）", currentSheetNo, start, end);
                        List<User> sheetData = queryUserDataByRange(start, end);
                        sheetDataMap.put(currentSheetNo, sheetData);
                        log.info("完成查询Sheet {} 的数据，共 {} 条", currentSheetNo, sheetData.size());
                    } catch (Exception e) {
                        log.error("查询Sheet {} 数据失败", currentSheetNo, e);
                        throw new RuntimeException("查询Sheet " + currentSheetNo + " 数据失败", e);
                    }
                }, excelExecutor);
            }

            // 等待所有数据查询完成
            CompletableFuture.allOf(queryFutures).join();
            log.info("所有Sheet数据查询完成，开始生成Excel");

            // 第二步：将内存中的数据写入Excel并输出到响应流
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                ExcelWriter excelWriter = EasyExcel.write(bos, User.class).build();
                // 按顺序写入每个Sheet
                for (int sheetNo = 0; sheetNo < sheetCount; sheetNo++) {
                    String sheetName = "用户数据" + (sheetNo + 1);
                    List<User> sheetData = sheetDataMap.get(sheetNo);

                    if (sheetData == null || sheetData.isEmpty()) {
                        log.warn("Sheet {} 没有数据，跳过", sheetNo);
                        continue;
                    }

                    // 创建Sheet并写入数据
                    WriteSheet writeSheet = EasyExcel.writerSheet(sheetNo, sheetName).build();
                    excelWriter.write(sheetData, writeSheet);
                    log.info("Sheet {} 写入完成，共 {} 条数据", sheetName, sheetData.size());
                }

                // 所有Sheet写入完成后，将完整Excel写入响应流
                excelWriter.finish();
                byte[] excelBytes = bos.toByteArray();

                // 设置文件大小，让浏览器知道文件总大小
                response.setContentLength(excelBytes.length);

                // 写入响应流
                try (OutputStream os = response.getOutputStream()) {
                    os.write(excelBytes);
                    os.flush();
                }
                log.info("Excel文件已完整写入响应流，总大小：{} KB", excelBytes.length / 1024);
            }

        } catch (Exception e) {
            log.error("Excel导出失败", e);
            throw e;
        }
    }

    /**
     * 从UserService分批查询指定范围的用户数据
     */
    private List<User> queryUserDataByRange(long start, long end) {
        List<User> allData = new ArrayList<>();
        long totalToQuery = end - start;

        // 分批查询，避免一次查询过多数据导致内存问题
        for (long i = 0; i < totalToQuery; i += QUERY_BATCH_SIZE) {
            long currentStart = start + i;
            long currentEnd = Math.min(start + i + QUERY_BATCH_SIZE, end);

            // 调用UserService查询数据
            List<User> batchData = userService.findUsersByRange(currentStart, currentEnd);

            if (batchData != null && !batchData.isEmpty()) {
                allData.addAll(batchData);
                log.info("已查询 {} - {} 范围的数据，累计 {} 条",
                        currentStart, currentEnd, allData.size());
            } else {
                log.info("{} - {} 范围没有数据", currentStart, currentEnd);
                break; // 没有更多数据，提前退出
            }
        }

        return allData;
    }
}

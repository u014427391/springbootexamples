package com.example.easyexcel.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.easyexcel.listener.SheetCountListener;
import com.example.easyexcel.listener.UserImportListener;
import com.example.easyexcel.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 百万级Excel数据导入服务
 */
@Service
@Slf4j
public class ExcelImportService {

    private final ThreadPoolTaskExecutor excelExecutor;
    private final UserService userService;

    public ExcelImportService(ThreadPoolTaskExecutor excelExecutor, UserService userService) {
        this.excelExecutor = excelExecutor;
        this.userService = userService;

    }

    /**
     * 多线程导入百万级用户数据（每个Sheet一个线程）
     */
    public void importMillionUsers(MultipartFile file) throws ExecutionException, InterruptedException, IOException {
        // 1. 保存成临时文件，避免多线程共用 InputStream
        java.io.File tmpFile = java.io.File.createTempFile("excel_", ".xlsx");
        file.transferTo(tmpFile);          // Spring 提供的零拷贝
        tmpFile.deleteOnExit();            // JVM 退出时自动清理

        ExcelTypeEnum excelType = getExcelType(file.getOriginalFilename());

        // 2. 拿 sheet 数量
        int sheetCount;
        try (InputStream in = new java.io.FileInputStream(tmpFile)) {
            sheetCount = getSheetCount(in);
        }
        log.info("开始导入，总 Sheet 数: {}", sheetCount);

        // 3. 并发读，每个 Sheet 独立 FileInputStream
        AtomicLong totalSuccess = new AtomicLong(0);
        AtomicLong totalFail    = new AtomicLong(0);

        List<CompletableFuture<Void>> futures = new ArrayList<>(sheetCount);
        for (int sheetNo = 0; sheetNo < sheetCount; sheetNo++) {
            final int idx = sheetNo;
            futures.add(CompletableFuture.runAsync(() -> {
                try (InputStream in = new java.io.FileInputStream(tmpFile)) {
                    UserImportListener listener = new UserImportListener(userService);
                    EasyExcel.read(in, User.class, listener)
                            .excelType(excelType)
                            .sheet(idx)
                            .doRead();

                    totalSuccess.addAndGet(listener.getSuccessCount());
                    totalFail.addAndGet(listener.getFailCount());
                    log.info("Sheet {} 完成，成功: {}, 失败: {}", idx, listener.getSuccessCount(), listener.getFailCount());
                } catch (IOException e) {
                    throw new RuntimeException("Sheet " + idx + " 读取失败", e);
                }
            }, excelExecutor));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        log.info("全部导入完成，总成功: {}，总失败: {}", totalSuccess.get(), totalFail.get());
    }

    /**
     * 获取Excel中的Sheet数量
     */
    private int getSheetCount(InputStream inputStream) {
        SheetCountListener countListener = new SheetCountListener();
        EasyExcel.read(inputStream)
                .registerReadListener(countListener)
                .doReadAll();
        return countListener.getSheetCount();
    }

    /**
     * 获取Excel文件类型
     *
     */
    public ExcelTypeEnum getExcelType(String fileName) {
        if (fileName == null) return null;
        if (fileName.toLowerCase().endsWith(".xlsx")) {
            return ExcelTypeEnum.XLSX;
        } else if (fileName.toLowerCase().endsWith(".xls")) {
            return ExcelTypeEnum.XLS;
        }
        return null;
    }


}

package com.example.easyexcel.controller;

import com.example.easyexcel.service.ExcelExportService;
import com.example.easyexcel.service.ExcelImportService;
import com.example.easyexcel.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/api/excel")
@Api(tags = "Excel导入导出接口")
@Slf4j
public class ExcelController {

    private final ExcelExportService exportService;
    private final ExcelImportService importService;
    private final UserService userService;

    public ExcelController(ExcelExportService exportService, ExcelImportService importService, UserService userService) {
        this.exportService = exportService;
        this.importService = importService;
        this.userService = userService;
    }

    @GetMapping("/export")
    @ApiOperation("导出用户数据")
    public void exportUsers(HttpServletResponse response,
                            @RequestParam(defaultValue = "1000000") long totalCount) throws IOException {
        try {
            exportService.exportMillionUsers(response, totalCount);
            log.info("成功导出 {} 条用户数据", totalCount);
        } catch (Exception e) {
            log.error("导出用户数据失败", e);
            try {
                if (!response.isCommitted()) {
                    // 响应未提交，重置并返回错误信息
                    response.reset();
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    writer.write("{\"code\":500,\"message\":\"导出失败：" + e.getMessage() + "\"}");
                    writer.flush();
                } else {
                    // 响应已提交，无法重置，只能记录日志
                    log.warn("响应流已提交，无法返回错误信息");
                }
            } catch (IOException ex) {
                log.error("处理导出错误响应失败", ex);
            }
        }
    }


    @PostMapping("/generate-test-data")
    @ApiOperation("生成测试数据")
    public ResponseEntity<String> generateTestData(@RequestParam(defaultValue = "1000000") long count) {
        try {
            userService.generateTestData(count);
            return ResponseEntity.ok("成功生成 " + count + " 条测试数据");
        } catch (Exception e) {
            log.error("生成测试数据失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("生成测试数据失败：" + e.getMessage());
        }
    }

    @GetMapping("/count")
    @ApiOperation("获取用户总数")
    public ResponseEntity<Long> getUserCount() {
        return ResponseEntity.ok(userService.getUserCount());
    }

}

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

package com.example.easyexcel.controller;

import com.example.easyexcel.core.rest.ResultBean;
import com.example.easyexcel.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class SampleController {

    @Autowired
    private ImportService exportService;

    @PostMapping(value = "/import")
    public ResultBean<?> doImport(MultipartFile file) throws IOException {
        exportService.doImportExcel(file);
        return ResultBean.ok();
    }

}

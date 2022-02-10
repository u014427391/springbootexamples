package com.example.easyexcel.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.example.easyexcel.core.excel.UserEasyExcelListener;
import com.example.easyexcel.model.User;
import com.example.easyexcel.model.dto.UserExcelDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <pre>
 *      文件导入
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2022/02/10 10:28  修改内容:
 * </pre>
 */
@Service
public class ImportService {

    public void doImportExcel(MultipartFile file) throws IOException {
        UserEasyExcelListener easyExcelListener = new UserEasyExcelListener();
        ExcelReaderBuilder read = EasyExcel.read(
                file.getInputStream(),
                UserExcelDto.class,
                easyExcelListener);
        read.doReadAll();
    }
}

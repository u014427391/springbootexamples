package com.example.easyexcel.model.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.example.easyexcel.core.annotation.EasyExcelCollection;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserExcelDto {

    @ExcelIgnore
    private String batchId;

    @ExcelIgnore
    private Date date;

    @ExcelProperty(value = "序号")
    private String seq;

    @ExcelProperty(value = "用户名")
    private String name;

    @ExcelProperty(value = "密码")
    private String password;

    @EasyExcelCollection
    private List<AddressDto> address;

}

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

    @ExcelProperty(value = "序号",index = 0)
    private String seq;

    @ExcelProperty(value = "用户名" ,index = 1)
    private String name;

    @ExcelProperty(value = "密码", index = 2)
    private String password;

    @ExcelProperty(value = "描述",index = 3)
    private String addressName;

    @ExcelProperty(value = "邮政编码",index = 4)
    private String code;

    @EasyExcelCollection
    private List<AddressDto> address;

}

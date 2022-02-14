package com.example.easyexcel.model.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.example.easyexcel.core.annotation.EasyExcelCollection;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;


@Data
public class UserExcelDto {


    @ExcelProperty(value = "序号",index = 0)
    @NotBlank(message = "序号必须填！")
    private String seq;

    @ExcelProperty(value = "用户名" ,index = 1)
    @NotBlank(message = "用户名必须填！")
    private String name;

    @ExcelProperty(value = "密码", index = 2)
    @Length(min = 0 , max = 8 , message = "密码最多8位！")
    private String password;

    @ExcelProperty(value = "描述",index = 3)
    private String addressName;

    @ExcelProperty(value = "邮政编码",index = 4)
    private String code;



}

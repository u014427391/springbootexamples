package com.example.easyexcel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_user")
public class User {
    @Id
    @ExcelProperty(index = 0, value = "ID")
    private Long id;
    
    @ExcelProperty(index = 1, value = "姓名")
    private String name;
    
    @ExcelProperty(index = 2, value = "邮箱")
    private String email;
    
    @ExcelProperty(index = 3, value = "地址")
    private String address;
}

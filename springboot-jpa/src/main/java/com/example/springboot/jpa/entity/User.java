package com.example.springboot.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sys_user")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Data
public class User{

    @Id //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Integer userId;

    @Column(name = "username",length = 20) //这是和数据表对应的一个列
    private String username;

    @Column(name = "sex")
    private String sex;

    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private Date createTime;



}

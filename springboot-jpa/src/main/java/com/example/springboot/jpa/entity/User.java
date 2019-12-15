package com.example.springboot.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "sys_user")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class User{

    @Id //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Integer userId;

    @Column(name = "username",length = 20) //这是和数据表对应的一个列
    private String username;

    @Column
    private String sex;

    @Column
    private String password;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

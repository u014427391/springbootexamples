package com.example.springboot.oauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息的实体类
 * @author Nicky
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User  implements Serializable {
    /** 用户Id**/
    private int id;

    /** 用户名**/
    private String username;

    /** 用户密码**/
    private String password;

    /** 手机号**/
    private String phone;

    /** 邮件**/
    private String email;

    /** 最后一次时间**/
    private Date lastLoginTime;

}


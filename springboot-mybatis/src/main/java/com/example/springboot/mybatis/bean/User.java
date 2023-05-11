package com.example.springboot.mybatis.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年12月15日  修改内容:
 * </pre>
 */
@Data
public class User implements Serializable{

    private String userId;
    private String username;
    private Sex sex;
    private String password;

}

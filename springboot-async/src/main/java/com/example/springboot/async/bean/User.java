package com.example.springboot.async.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  用户信息实体类
 *  Copy @ https://spring.io/guides/gs/async-method/
 * </pre>
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/20 10:14  修改内容:
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class User implements Serializable {

    private String name;
    private String blog;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", blog='" + blog + '\'' +
                '}';
    }
}

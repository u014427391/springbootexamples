package com.example.springcloud.feign.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <pre>
 *  User
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/27 17:38  修改内容:
 * </pre>
 */
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class User extends BaseBean implements Serializable {

    private String name;
    private String blog;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", blog='" + blog + '\'' +
                ", createTime='" + super.getCreateTime() + '\'' +
                ", updateTime='" + super.getUpdateTime() + '\'' +
                ", createMan='" + super.getCreateMan() + '\'' +
                ", updateMan='" + super.getUpdateMan() + '\'' +
                '}';
    }
}
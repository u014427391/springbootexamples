package com.example.springboot.hateoas.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年10月26日  修改内容:
 * </pre>
 */
@Entity
@Table(name="sys_user")
public class SysUserInfo extends ResourceSupport implements Serializable{

    @Id
    @GeneratedValue
    private Long userId;
    @Column(unique=true,length=20,nullable=false)
    private String username;
    @Column(length=2,nullable=true)
    private String sex;
    @Column(length=10,nullable=true)
    private String password;

    public SysUserInfo(){

    }

    @JsonCreator
    public SysUserInfo(@JsonProperty("userId")Long userId,@JsonProperty("username")String username,
                       @JsonProperty("sex")String sex,@JsonProperty("password")String password){
        this.userId = userId;
        this.username = username;
        this.sex = sex;
        this.password = password;
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

    public Long getUserId() {

        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

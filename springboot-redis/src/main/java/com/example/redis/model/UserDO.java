package com.example.redis.model;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@TableName("user")
@EqualsAndHashCode(callSuper=true)
@Data
public class UserDO extends BaseDO implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID =  -2767372720145830784L;

    private String name;
    private Integer age;
    private String email;

}

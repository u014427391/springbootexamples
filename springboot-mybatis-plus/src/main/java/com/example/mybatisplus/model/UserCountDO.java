package com.example.mybatisplus.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@TableName("user")
@Data
public class UserCountDO extends UserDO implements Serializable {
    private static final long serialVersionUID = 3319012504813563043L;

    @TableField(value = "count(*)", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private Long count;

    @TableField(value = "date_format(create_time, '%Y年%m月%d日')", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private String chineseDateFormat;

}

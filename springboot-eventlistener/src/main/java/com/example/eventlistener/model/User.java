package com.example.eventlistener.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("user")
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {
    @TableId
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;

}

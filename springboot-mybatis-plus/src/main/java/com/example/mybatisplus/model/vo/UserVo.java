package com.example.mybatisplus.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserVo implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;

}

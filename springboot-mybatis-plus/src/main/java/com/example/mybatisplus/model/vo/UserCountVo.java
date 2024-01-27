package com.example.mybatisplus.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCountVo implements Serializable {

    private static final long serialVersionUID = 751593958762207003L;
    private String value;

    private Long num;

}

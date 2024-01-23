package com.example.springboot.jpa.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserSearchDto implements Serializable {

    private Date startTime;

    private Date endTime;

    private String userName;

}

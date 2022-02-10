package com.example.easyexcel.model;


import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Data
public class User implements Serializable {


    private String seq;

    private String name;

    private String password;

    private List<Address> address;

}

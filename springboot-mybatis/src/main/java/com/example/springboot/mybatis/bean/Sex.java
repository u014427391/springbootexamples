package com.example.springboot.mybatis.bean;


import com.example.springboot.mybatis.common.enumhandler.IEnum;

public enum  Sex implements IEnum {

    MAN("1","男"),
    WOMAN("2","女");

    private String code;
    private String name;

    Sex(String code, String name) {
        this.code = code;
        this.name = name;
    }


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}

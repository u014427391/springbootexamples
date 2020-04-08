package com.example.springboot.rabbitmq.model;

import lombok.*;

import java.io.Serializable;

/**
 * User信息类
 * @Author mazq
 * @Date 2020/04/08 15:12
 */
@Data
@AllArgsConstructor
@ToString
public class User implements Serializable{

    private String name;

    private String pwd;

//    @Override
//    public String toString() {
//        return "User{" +
//                "name='" + name + '\'' +
//                ", pwd='" + pwd + '\'' +
//                '}';
//    }
}
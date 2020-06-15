package com.example.springboot.oauth2.mapper;

import com.example.springboot.oauth2.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserDao {

    @Select("select * from user where username=#{username}")
    User findByUsername(@Param("username") String username);

}

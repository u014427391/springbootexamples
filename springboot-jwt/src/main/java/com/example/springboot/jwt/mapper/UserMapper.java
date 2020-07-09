package com.example.springboot.jwt.mapper;


import com.example.springboot.jwt.core.jwt.userdetails.JWTUserDetails;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *  UserMapper
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/05/15 17:34  修改内容:
 * </pre>
 */
@Repository("userMapper")
public interface UserMapper {

    @Select("select * from sys_user where username=#{username}")
    JWTUserDetails findByUsername(@Param("username") String username);

    @Select("select * from sys_user where username=#{username} and password=#{password}")
    JWTUserDetails findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}

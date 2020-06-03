package com.example.springboot.oauth2.mapper;


import com.example.springboot.oauth2.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/05/15 17:34  修改内容:
 * </pre>
 */
public interface UserMapper {

    @Select("select * from sys_user where username=#{username}")
    User findByUsername(@Param("username") String username);

    @Select("select * from sys_user where username=#{username} and password=#{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}

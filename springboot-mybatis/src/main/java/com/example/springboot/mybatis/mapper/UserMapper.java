package com.example.springboot.mybatis.mapper;

import com.example.springboot.mybatis.bean.User;
import com.example.springboot.mybatis.common.cache.MyBatisRedisCache;
import org.apache.ibatis.annotations.*;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年12月15日  修改内容:
 * </pre>
 */
@CacheNamespace(implementation = MyBatisRedisCache.class)
public interface UserMapper {
    @Select("select * from sys_user where userId=#{userId}")
    public User getUserById(Integer userId);

    @Delete("delete from sys_user where userId=#{userId}")
    public int deleteUserById(Integer userId);

    @Options(useGeneratedKeys = true,keyProperty = "userId")
    @Insert("insert into sys_user(username,sex,password) values(#{username},#{sex},#{password})")
    public int insertUser(User user);

    @Update("update sys_user set username=#{username} where userId=#{userId}")
    public int updateUser(User user);
}

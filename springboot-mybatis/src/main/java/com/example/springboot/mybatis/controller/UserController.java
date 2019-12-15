package com.example.springboot.mybatis.controller;

import com.example.springboot.mybatis.bean.User;
import com.example.springboot.mybatis.mapper.SysUserMapper;
import com.example.springboot.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    SysUserMapper userDao;

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable("userId") Integer userId){
        return userMapper.getUserById(userId);
    }

    @PostMapping("/user")
    public User insertDept(User user){
        userMapper.insertUser(user);
        return user;
    }

    /**
     * xml方式获取用户信息
     * @param id
     * @return
     */
    @GetMapping("/api/user/{id}")
    public User getUserById(@PathVariable("id") Integer id){
        return userDao.getApiUserById(id);
    }

}

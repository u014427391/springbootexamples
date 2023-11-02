package com.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.model.UserDO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@MybatisPlusTest
class MybatisPlusSampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void test() {
        List<UserDO> userList = userMapper.selectList(new QueryWrapper<UserDO>());
        userList.stream().forEach(System.out::println);
    }
}

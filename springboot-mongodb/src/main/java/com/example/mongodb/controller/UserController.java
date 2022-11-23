package com.example.mongodb.controller;


import cn.hutool.core.bean.BeanUtil;
import com.example.mongodb.common.page.PageBean;
import com.example.mongodb.common.page.PageDataBean;
import com.example.mongodb.common.rest.ResultBean;
import com.example.mongodb.model.User;
import com.example.mongodb.model.dto.UserDto;
import com.example.mongodb.model.vo.UserVo;
import com.example.mongodb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = {"/user"})
    public ResultBean<List<UserVo>> users() {
        List<User> poList = userService.list();
        List<UserVo> voList = BeanUtil.copyToList(poList , UserVo.class);
        return ResultBean.ok(voList);
    }

    @GetMapping(value = {"/user/{id}"})
    public ResultBean<UserVo> user(@PathVariable("id")Long id) {
        User user = userService.getOneById(id);
        UserVo userVo = BeanUtil.copyProperties(user , UserVo.class);
        return ResultBean.ok(userVo);
    }

    @PostMapping(value = "/user")
    public ResultBean<User> save(@RequestBody UserDto userDto) {
        User user = BeanUtil.copyProperties(userDto , User.class);
        return ResultBean.ok(userService.save(user));
    }

    @DeleteMapping(value = "/user/{id}")
    public ResultBean<Long> del(@PathVariable("id") Long id) {
        userService.removeById(id);
        return ResultBean.ok(id);
    }

    @PutMapping(value = "/user")
    public ResultBean<User> update(@RequestBody UserDto userDto) {
        User user = BeanUtil.copyProperties(userDto , User.class);
        return ResultBean.ok(userService.updateById(user));
    }

    @GetMapping(value = "/user/pageList")
    public ResultBean<PageDataBean<List<User>>> pageList(PageBean pageBean , User param) {
        PageDataBean<List<User>> pageDataBean = userService.pageList(pageBean, param);
        return ResultBean.ok(pageDataBean);
    }

}

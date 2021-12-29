package com.example.mybatisplus.controller;

import com.example.mybatisplus.common.ResultBean;
import com.example.mybatisplus.model.User;
import com.example.mybatisplus.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value = {"/user"})
    public ResultBean<List<User>> users() {
        return ResultBean.ok(userService.list());
    }

    @GetMapping(value = {"/user/{id}"})
    public ResultBean<User> user(@PathVariable("id")Integer id) {
        return ResultBean.ok(userService.getById(id));
    }

    @PostMapping(value = "/user")
    public ResultBean<User> save(@RequestBody User user) {
        boolean flag = userService.save(user);
        if (flag) return ResultBean.ok(user);
        return ResultBean.badRequest("新增失败");
    }

    @DeleteMapping(value = "/user/{id}")
    public ResultBean<Integer> del(@PathVariable("id") Integer id) {
        boolean flag = userService.removeById(id);
        if (flag) return ResultBean.ok(id);
        return ResultBean.badRequest("删除失败");
    }

    @PutMapping(value = "/user")
    public ResultBean<User> update(@RequestBody User user) {
        boolean flag = userService.updateById(user);
        if (flag) return ResultBean.ok(user);
        return ResultBean.badRequest("更新失败");
    }

}

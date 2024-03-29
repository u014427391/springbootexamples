package com.example.redis.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.redis.common.page.PageBean;
import com.example.redis.common.page.PageDataBean;
import com.example.redis.common.rest.ResultBean;
import com.example.redis.handler.UserHandler;
import com.example.redis.model.UserDO;
import com.example.redis.model.dto.UserDto;
import com.example.redis.model.vo.UserVo;
import com.example.redis.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private UserHandler userHandler;

    @PostMapping(value = "/user")
    public ResultBean<UserDO> save(@RequestBody UserDto userDto) {
        UserDO user = BeanUtil.copyProperties(userDto, UserDO.class);
        boolean flag = userService.save(user);
        if (flag) return ResultBean.ok(user);
        return ResultBean.badRequest("新增失败");
    }

    @PostMapping(value = "/pageUserInfo")
    public ResultBean<PageDataBean<UserVo>> pageUserInfo(@RequestBody PageBean pageBean) {
        return ResultBean.ok(userHandler.pageUserInfo(pageBean));
    }

}
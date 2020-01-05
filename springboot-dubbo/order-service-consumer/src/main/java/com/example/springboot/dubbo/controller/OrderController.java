package com.example.springboot.dubbo.controller;

import com.example.spring.dubbo.bean.UserAddress;
import com.example.spring.dubbo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;



@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @ResponseBody
    @RequestMapping("/initOrder")
    public List<UserAddress> initOrder(@RequestParam("uid")String userId) {
        return orderService.initOrder(userId);
    }

}

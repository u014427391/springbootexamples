package com.example.springboot.web.controller;

import com.example.springboot.starter.service.HelloService;
import com.example.springboot.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019/11/29 19:02  修改内容:
 * </pre>
 */
@Controller
public class HelloController {

    @Autowired
    HelloService helloService;

    @RequestMapping(value = {"/index"})
    public ModelAndView toIndex() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg","This is <b>great!</b>");
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = {"/testException"})
    @ResponseBody
    public String testException(String tFlag){
        if("1".equals(tFlag)) {
            throw new NotFoundException("404异常",404);
        }
        return "hello!";
    }

    @GetMapping(value = {"/sayHello/{name}"})
    @ResponseBody
    public String sayHello(@PathVariable("name")String name){
        return helloService.sayHello(name);
    }

    @GetMapping(value = {"/collect"})
    @ResponseBody
    public String collect(@RequestParam("u")String u , @RequestParam("p")String p) {
        return String.format("username:%s , password :%s" , u , p);
    }


}

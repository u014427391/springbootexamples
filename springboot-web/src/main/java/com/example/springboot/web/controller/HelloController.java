package com.example.springboot.web.controller;

import com.example.springboot.starter.service.HelloService;
import com.example.springboot.web.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
        return new HelloService().sayHello(name);
    }

}

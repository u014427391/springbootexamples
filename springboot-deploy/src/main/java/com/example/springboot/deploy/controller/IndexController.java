package com.example.springboot.deploy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 *  IndexController
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/23 10:59  修改内容:
 * </pre>
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/test"})
    public ModelAndView toTestPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("test");
        modelAndView.addObject("username","admin");
        return modelAndView;
    }
}

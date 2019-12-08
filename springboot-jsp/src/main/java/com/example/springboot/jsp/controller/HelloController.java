package com.example.springboot.jsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年12月08日  修改内容:
 * </pre>
 */
@Controller
public class HelloController {

    @RequestMapping(value = {"/success"})
    public String toSuccess(){
        return "success";
    }
}

package com.example.springboot.actuator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <pre>
 *  IndexController
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/23 17:53  修改内容:
 * </pre>
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/test"})
    @ResponseBody
    public String test(){
        return "success";
    }
}

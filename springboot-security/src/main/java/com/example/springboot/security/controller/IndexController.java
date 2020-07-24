package com.example.springboot.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <pre>
 *      IndexController
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/24 11:11  修改内容:
 * </pre>
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/index","/"})
    @ResponseBody
    public String index(){
        return "success";
    }
}

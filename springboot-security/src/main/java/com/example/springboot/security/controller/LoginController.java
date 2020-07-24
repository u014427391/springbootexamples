package com.example.springboot.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 *      LoginController
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/24 15:07  修改内容:
 * </pre>
 */
@Controller
public class LoginController {

    @GetMapping(value = {"/login"})
    public ModelAndView toLogin() {
        return new ModelAndView("login");
    }
}

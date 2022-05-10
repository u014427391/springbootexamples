package com.example.security.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping(value = {"/login"})
    public ModelAndView toLogin() {
        return new ModelAndView("login");
    }
}

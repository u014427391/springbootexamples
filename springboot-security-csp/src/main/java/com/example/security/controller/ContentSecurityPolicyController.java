package com.example.security.controller;


import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
@Slf4j
public class ContentSecurityPolicyController {

    @GetMapping(value = {"/login"})
    public ModelAndView toLogin() {
        return new ModelAndView("login");
    }

    @PostMapping(value = {"/report"})
    @ResponseBody
    public String report(HttpServletRequest request) throws IOException {
        String report = IoUtil.read(request.getInputStream(), StandardCharsets.UTF_8);
         if (log.isInfoEnabled()) {
            log.info("Report: {}", report);
        }
        return report;
    }

}

package com.example.springboot.logging.log4j.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 *
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019/11/18 11:50  修改内容:
 * </pre>
 */
@RestController
public class HelloController {

    Logger LOG = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = {"/hello"})
    public String hello(HttpServletRequest request){
        if (LOG.isInfoEnabled()) {
            LOG.info("url:{}",request.getRequestURI());
        }
        return "hello world!";
    }
}

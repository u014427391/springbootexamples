package com.example.springcloud.gateway.rest.controller;

import com.example.springcloud.gateway.bean.ResultBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *      HystrixRestController
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/09/15 11:46  修改内容:
 * </pre>
 */
@RestController
public class HystrixRestController {

    @GetMapping(value = {"/fallback"})
    public ResultBean fallback() {
        return ResultBean.badRequest("Hystrix fallback");
    }
}

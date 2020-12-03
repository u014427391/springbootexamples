package com.example.springcloud.config.rest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *      ConnfigClientController
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/08/12 17:37  修改内容:
 * </pre>
 */
@RestController
@RefreshScope
public class ConnfigClientController {

    @Value("${config.client.profile}")
    private String profile;

    @GetMapping(value = "/test")
    public String test() {
        return this.profile;
    }

}

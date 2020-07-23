package com.example.springboot.actuator.actuate.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *      ApplicationHealthIndicator
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/23 16:01  修改内容:
 * </pre>
 */
@Component
public class ApplicationHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        //自定义的检查方法
        return Health.up().withDetail("msg","调用成功").build();
        //return Health.down().withDetail("msg","服务异常").build();
    }
}


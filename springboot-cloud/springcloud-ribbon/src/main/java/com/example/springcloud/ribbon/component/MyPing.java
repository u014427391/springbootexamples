package com.example.springcloud.ribbon.component;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;

/**
 * <pre>
 *  MyPing
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/31 15:05  修改内容:
 * </pre>
 */
public class MyPing implements IPing {
    /**
     * Checks whether the given <code>Server</code> is "alive" i.e. should be
     * considered a candidate while loadbalancing
     *
     * @param server
     */
    @Override
    public boolean isAlive(Server server) {
        return true;
    }
}

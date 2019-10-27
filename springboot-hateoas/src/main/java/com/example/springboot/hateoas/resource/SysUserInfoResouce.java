package com.example.springboot.hateoas.resource;

import com.example.springboot.hateoas.core.model.AbstractModel;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年10月26日  修改内容:
 * </pre>
 */
public class SysUserInfoResouce extends Resource{

    public SysUserInfoResouce(AbstractModel model) {
        super(model);
        Long id = model.getId();
        add(linkTo(getClass()).slash(id).withRel("sysUsers"));
    }

}

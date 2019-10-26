package com.example.springboot.hateoas.core.resource;

import com.example.springboot.hateoas.bean.SysUserInfo;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;


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
public class CustomListResourceAssembler extends ResourceAssemblerSupport<CustomIdentifiable,SysUserInfo> {

    public CustomListResourceAssembler() {
        super(CustomListResourceAssembler.class, SysUserInfo.class);
    }

    @Override
    public SysUserInfo toResource(CustomIdentifiable list) {
        SysUserInfo sysUser = createResourceWithId(list.getId() , list);
        return sysUser;
    }

    @Override
    protected SysUserInfo instantiateResource(CustomIdentifiable entity) {
        return super.instantiateResource(entity);
    }
}

package com.example.springboot.hateoas.core;

import com.example.springboot.hateoas.model.SysUserInfoModel;
import com.example.springboot.hateoas.resource.SysUserInfoResouce;
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
public class CustomResourceAssembler extends ResourceAssemblerSupport<SysUserInfoModel,SysUserInfoResouce> {

    public CustomResourceAssembler(Class<?> sourceClass) {
        super(sourceClass,SysUserInfoResouce.class);
    }

    @Override
    public SysUserInfoResouce toResource(SysUserInfoModel entity) {
        SysUserInfoResouce sysUser = createResourceWithId(entity.getId() , entity);
        return sysUser;
    }

    @Override
    protected SysUserInfoResouce instantiateResource(SysUserInfoModel   entity) {
        //return super.instantiateResource(entity);
        return new SysUserInfoResouce(entity);
    }
}

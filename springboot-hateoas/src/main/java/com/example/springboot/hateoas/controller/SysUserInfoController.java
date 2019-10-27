package com.example.springboot.hateoas.controller;

import com.example.springboot.hateoas.core.CustomResourceAssembler;
import com.example.springboot.hateoas.exception.NotFoundException;
import com.example.springboot.hateoas.model.SysUserInfo;
import com.example.springboot.hateoas.model.SysUserInfoModel;
import com.example.springboot.hateoas.repository.SysUserInfoModelRepository;
import com.example.springboot.hateoas.repository.SysUserInfoRepository;
import com.example.springboot.hateoas.resource.SysUserInfoResouce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
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
@RestController
@ExposesResourceFor(SysUserInfoModel.class)
@RequestMapping("/api/user")
public class SysUserInfoController {

    Logger LOG = LoggerFactory.getLogger(SysUserInfoController.class);

    @Autowired
    SysUserInfoRepository sysUserRepository;
    @Autowired
    SysUserInfoModelRepository sysUserInfoModelRepository;

    @GetMapping("/findAll")
    public List<SysUserInfo> findAllSysUserInfo() {
        return  sysUserRepository.findAll();
    }


    @GetMapping("/findBySysUserId/{userId}")
    public SysUserInfo findBySysUserId(@PathVariable("userId") long userId) {
        if (LOG.isInfoEnabled()) {
            LOG.info("请求参数userId : {}" , userId);
        }
        Optional<SysUserInfo> sysUserInfo = Optional.ofNullable(sysUserRepository.findByUserId(userId));
        if (!sysUserInfo.isPresent()) {
            throw new NotFoundException("查询不到用户信息! userId:"+userId);
        }
        //Resource<SysUserInfo> resource = new Resource<SysUserInfo>(sysUserInfo.get());
        ControllerLinkBuilder linkBuilder = linkTo(methodOn(this.getClass()).findBySysUserId(userId));
        sysUserInfo.get().add(linkBuilder.withRel("findBySysUserId"));
        return sysUserInfo.get();
    }

    @GetMapping("/findBySysUserIdWithResource/{userId}")
    public Resource<SysUserInfoResouce> findBySysUserIdWithResource(@PathVariable("userId") long userId) {
        return new CustomResourceAssembler(this.getClass()).toResource(sysUserInfoModelRepository.findByUserId(userId));
    }


}

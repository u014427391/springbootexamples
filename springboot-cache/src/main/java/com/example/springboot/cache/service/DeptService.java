package com.example.springboot.cache.service;

import com.example.springboot.cache.bean.Department;
import com.example.springboot.cache.mapper.DepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/01 13:41  修改内容:
 * </pre>
 */
@Service
public class DeptService {

    Logger LOG = LoggerFactory.getLogger(DeptService.class);

    @Autowired
    DepartmentMapper departmentMapper;

    @Qualifier("redisCacheManager")
    @Autowired
    RedisCacheManager redisCacheManager;

//      @Cacheable(cacheNames = "dept",cacheManager = "redisCacheManager")
//    public Department getDeptById(Integer id){
//        System.out.println("查询部门"+id);
//        Department department = departmentMapper.getDeptById(id);
//        return department;
//    }

    // 使用缓存管理器得到缓存，进行api调用
    public Department getDeptById(Integer id){
        LOG.info("查询id为{}的员工信息",id);
        Department department = departmentMapper.getDeptById(id);

        //获取某个缓存
        Cache dept = redisCacheManager.getCache("dept");
        dept.put("dept:1",department);

        return department;
    }


}

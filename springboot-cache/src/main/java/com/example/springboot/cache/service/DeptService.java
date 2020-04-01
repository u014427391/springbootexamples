package com.example.springboot.cache.service;

import com.example.springboot.cache.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    DepartmentMapper departmentMapper;

    //@Qualifier(value = "deptCacheManager")
    //@Autowired
    //RedisCacheManager deptCacheManager;


}

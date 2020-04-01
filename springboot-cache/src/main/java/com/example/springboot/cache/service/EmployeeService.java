package com.example.springboot.cache.service;

import com.example.springboot.cache.bean.Employee;
import com.example.springboot.cache.mapper.EmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/01 13:50  修改内容:
 * </pre>
 */
@Service
public class EmployeeService {

    Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    EmployeeMapper employeeMapper;

    @Cacheable(value = {"emp"}, keyGenerator = "myKeyGenerator",condition = "#a0>=1",unless = "#a0==2")
    public Employee getEmp(Integer id) {
        Employee employee = this.employeeMapper.getEmpById(id);
        LOG.info("查询{}号员工",id);
        return employee;
    }


}

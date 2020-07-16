package com.example.springboot.elasticsearch.repository;

import com.example.springboot.elasticsearch.bean.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *  EmployeeRepository
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/16 17:24  修改内容:
 * </pre>
 */
@Repository
public interface EmployeeRepository extends ElasticsearchRepository<Employee,Long>{

}

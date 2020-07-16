package com.example.springboot.elasticsearch;

import com.example.springboot.elasticsearch.bean.Employee;
import com.example.springboot.elasticsearch.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class SpringbootElasticsearchApplicationTests {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void createIndex(){
        Employee employee = new Employee();
        employee.setId(2L);
        employee.setName("sys");
        employee.setPassword("123");
        employeeRepository.save(employee);
    }

    @Test
    void selectIndex(){
        Optional<Employee> employee = employeeRepository.findById(2L);
        System.out.println(employee.get().toString());
    }

}

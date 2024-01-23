package com.example.springboot.jpa.repository;

import com.example.springboot.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor {

}


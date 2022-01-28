package com.example.mongodb.repository;


import com.example.mongodb.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModel , Long> {

}

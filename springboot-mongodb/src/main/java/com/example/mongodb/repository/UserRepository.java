package com.example.mongodb.repository;


import com.example.mongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    @Query("{ 'email' : ?0 }")
    User findByEmail(String email);

    @Query("{'_id' : ?0 }")
    Optional<User> findById(Long id);

}

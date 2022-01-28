package com.example.mongodb.service.impl;


import com.example.mongodb.model.User;
import com.example.mongodb.repository.UserRepository;
import com.example.mongodb.service.IUserService;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(
        rollbackFor = Exception.class
)
public class UserServiceImpl implements IUserService {

    @Autowired
    @Qualifier(value = "userRepository")
    private UserRepository userRepository;

    @Override
    public List<User> list() {
        return userRepository.findAll();
    }

    @Override
    public User getOneById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return optional.orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.insert(user);
    }

    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateById(User user) {
       return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}

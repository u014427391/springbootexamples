package com.example.mongodb.service;


import com.example.mongodb.common.page.PageBean;
import com.example.mongodb.common.page.PageDataBean;
import com.example.mongodb.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {

    List<User> list();

    User getOneById(Long id);

    User save(User user);

    void removeById(Long id);

    User updateById(User user);

    User findByEmail(String email);

    PageDataBean<List<User>> pageList(PageBean pageBean , User queryParam);

    Page<User> mongoPageList(PageBean pageBean , User queryParam);

}

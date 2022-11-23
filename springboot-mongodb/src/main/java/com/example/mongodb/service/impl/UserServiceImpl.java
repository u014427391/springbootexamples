package com.example.mongodb.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.example.mongodb.common.page.PageBean;
import com.example.mongodb.common.page.PageDataBean;
import com.example.mongodb.model.User;
import com.example.mongodb.repository.UserRepository;
import com.example.mongodb.service.IUserService;
import com.github.pagehelper.Page;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    @Qualifier(value = "userRepository")
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

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
    @Transactional(rollbackFor = Exception.class)
    public User save(User user) {
        return userRepository.insert(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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

    @Override
    public PageDataBean<List<User>> pageList(PageBean pageBean, User queryParam) {
        pageBean.initPage();
        MongoCollection<Document> collection = mongoTemplate.getCollection("user");
        Page page = new Page();
        page.setTotal(collection.countDocuments());
        pageBean.setPages(page);

        Document params = new Document();
        if (StrUtil.isNotBlank(queryParam.getName())) {
            params.append("name", queryParam.getName());
        }
        Bson orderBy = new BasicDBObject("_id" , -1);
        FindIterable<Document> iterable = collection.find(params).sort(orderBy).skip((pageBean.getPageIndex() - 1) * pageBean.getPageRowNum()).limit(pageBean.getPageRowNum());
        MongoCursor<Document> cursor = iterable.iterator();
        List<Document> result = new ArrayList<>();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            result.add(doc);
        }

        return pageBean.loadData(result);
    }

}

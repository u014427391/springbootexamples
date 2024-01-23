package com.example.springboot.jpa.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.jpa.dto.UserSearchDto;
import com.example.springboot.jpa.entity.User;
import com.example.springboot.jpa.repository.UserRepository;
import com.example.springboot.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User getUser(Integer userId) {
        User user = userRepository.getOne(userId);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User insertUser(User user) {
        User save = userRepository.save(user);
        return save;
    }

    @Override
    public List<User> listUser(UserSearchDto searchDto) {
        Specification<User> specification = new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                Path<Object> createTime = root.get("createTime");
                Path<Object> username = root.get("username");

                if (ObjectUtil.isNotNull(searchDto.getStartTime())) {
                    LocalDateTime localDateTime = DateUtil.toLocalDateTime(searchDto.getStartTime());
                    LocalDateTime createDateStart = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(), 00, 00, 00);

                    Predicate pSTime = criteriaBuilder.greaterThanOrEqualTo(createTime.as(String.class), DateUtil.format(new DateTime(createDateStart), DatePattern.NORM_DATETIME_PATTERN));
                    predicate.getExpressions().add(pSTime);
                }

                if (ObjectUtil.isNotNull(searchDto.getEndTime())) {
                    LocalDateTime localDateTime = DateUtil.toLocalDateTime(searchDto.getEndTime());

                    LocalDateTime createDateEnd = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(), 23, 59, 59);
                    Predicate pETime = criteriaBuilder.lessThanOrEqualTo(createTime.as(String.class), DateUtil.format(new DateTime(createDateEnd), DatePattern.NORM_DATETIME_PATTERN));
                    predicate.getExpressions().add(pETime);
                }

                if (StrUtil.isNotBlank(searchDto.getUserName())) {
                    Predicate pUserName = criteriaBuilder.like(username.as(String.class), searchDto.getUserName());
                    predicate.getExpressions().add(pUserName);
                }

                return predicate;
            }
        };

        return Optional.ofNullable(userRepository.findAll(specification)).orElse(Collections.EMPTY_LIST);
    }
}

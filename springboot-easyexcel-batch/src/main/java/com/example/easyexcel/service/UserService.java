package com.example.easyexcel.service;

import com.example.easyexcel.model.User;
import com.example.easyexcel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    /**
     * 批量保存用户
     */
    @Transactional
    public void batchSaveUsers(List<User> users) {
        // 分批保存，防止一次性插入过多数据
        int batchSize = 500;
        for (int i = 0; i < users.size(); i += batchSize) {
            int end = Math.min(i + batchSize, users.size());
            userRepository.saveAll(users.subList(i, end));
        }
    }

    /**
     * 根据范围查询用户
     */
    public List<User> findUsersByRange(long startId, long endId) {
        // 实际项目中应该根据ID范围查询
        // 这里为了演示，使用生成数据的方式
        return LongStream.range(startId, endId)
                .mapToObj(i -> {
                    User user = new User();
                    user.setId(i);
                    user.setName("用户" + i);
                    user.setEmail("user" + i + "@example.com");
                    user.setAddress("地址" + i);
                    return user;
                })
                .collect(Collectors.toList());
    }

    /**
     * 生成测试数据
     */
    @Transactional
    public void generateTestData(long count) {
        log.info("开始生成 {} 条测试数据", count);
        
        int batchSize = 10000;
        for (long i = 0; i < count; i += batchSize) {
            long end = Math.min(i + batchSize, count);
            List<User> users = LongStream.range(i, end)
                    .mapToObj(id -> {
                        User user = new User();
                        user.setId(id);
                        user.setName("测试用户" + id);
                        user.setEmail("test_user" + id + "@example.com");
                        user.setAddress("测试地址" + id);
                        return user;
                    })
                    .collect(Collectors.toList());
            
            userRepository.saveAll(users);
            log.info("已生成 {} 条测试数据", end);
        }
        
        log.info("测试数据生成完成，共 {} 条", count);
    }

    /**
     * 获取用户总数
     */
    public long getUserCount() {
        return userRepository.count();
    }
}

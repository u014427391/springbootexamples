package com.example.redis.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.redis.common.page.PageBean;
import com.example.redis.common.page.PageDataBean;
import com.example.redis.model.vo.UserVo;
import com.github.pagehelper.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class UserHandler {

    private static final String REDIS_KEY = "testKeyRecord";

    @Resource
    private RedisTemplate redisTemplate;

    public PageDataBean<UserVo> pageUserInfo(PageBean pageBean) {
        Integer pageNum = Optional.ofNullable(pageBean.getPageNum()).orElse(1);
        Integer pageSize = Optional.ofNullable(pageBean.getPageSize()).orElse(10);
        pageBean.initPage();

        if (!redisTemplate.hasKey(REDIS_KEY)) {
            return pageBean.loadData(CollUtil.newArrayList());
        }
        int min = (pageNum -1) * pageSize;
        int max = min + pageSize - 1 ;
        Long size = redisTemplate.opsForZSet().size(REDIS_KEY);

        Set<String> recordSet = Optional.ofNullable(redisTemplate
                .opsForZSet()
                .reverseRange(REDIS_KEY, min, max))
                .orElse(CollUtil.newHashSet());
        List<UserVo> list = CollUtil.newArrayList();
        recordSet.stream().forEach(getValue -> {
            if (StrUtil.isNotBlank(getValue)) {
                UserVo recordVo = null;
                try {
                    recordVo = JSONUtil.toBean(getValue, UserVo.class);
                } catch (Exception e) {
                    // ignore exception
                }
                if (recordVo != null) {
                    list.add(recordVo);
                }
            }
        });

        Page page = new Page();
        page.setTotal(size);
        pageBean.setPages(page);

        return pageBean.loadData(list);
    }

}

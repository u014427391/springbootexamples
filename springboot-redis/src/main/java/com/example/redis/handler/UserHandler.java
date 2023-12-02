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

    @Resource
    private RedisTemplate redisTemplate;

    public PageDataBean<UserVo> pageUserInfo(PageBean pageBean) {
        Integer pageNum = Optional.ofNullable(pageBean.getPageNum()).orElse(1);
        Integer pageSize = Optional.ofNullable(pageBean.getPageSize()).orElse(10);
        pageBean.initPage();

        Set<String> recordSet = Optional.ofNullable(redisTemplate.opsForZSet()
                .reverseRange("testKeyRecord", (pageNum - 1) * pageSize + 1, pageSize)).orElse(CollUtil.newHashSet());
        List<UserVo> list = CollUtil.newArrayList();
        recordSet.stream().forEach(getValue->{
            if (StrUtil.isNotBlank(getValue)) {
                UserVo recordDto = null;
                try {
                    recordDto = JSONUtil.toBean(getValue, UserVo.class);
                } catch (Exception e) {
                    // ignore exception
                }
                if (recordDto != null) {
                    list.add(recordDto);
                }
            }
        });
        long totalCount = redisTemplate.opsForZSet().zCard("testKeyRecord");

        Page page = new Page();
        page.setTotal(totalCount);

        return pageBean.loadData(list);
    }

}

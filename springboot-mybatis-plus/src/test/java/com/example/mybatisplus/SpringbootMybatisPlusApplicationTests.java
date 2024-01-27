package com.example.mybatisplus;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.mybatisplus.mapper.UserCountMapper;
import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.model.UserCountDO;
import com.example.mybatisplus.model.UserDO;
import com.example.mybatisplus.model.vo.UserCountVo;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class SpringbootMybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCountMapper userCountMapper;

    @Test
    void contextLoads() {
        List<UserDO> userList = userMapper.selectList(new QueryWrapper<UserDO>());
        userList.stream().forEach(System.out::println);
    }

    @Test
    void testGroupBy() {
        LambdaQueryWrapper<UserCountDO> selectWrapper = Wrappers.lambdaQuery();
        selectWrapper.select(UserCountDO::getChineseDateFormat, UserCountDO::getCount);
        selectWrapper.groupBy(UserCountDO::getChineseDateFormat);

        List<Map<String, Object>> userMapList = Optional.ofNullable(userCountMapper.selectMaps(selectWrapper)).orElse(Collections.emptyList());

        List<UserCountVo> resultList = new ArrayList<>();
        for (Map<String, Object> e : userMapList) {
            String chineseDateFormat = Convert.toStr(e.get("chineseDateFormat"));
            Long count = Optional.ofNullable(Convert.toLong(e.get("count"))).orElse(0L);
            UserCountVo userCountVo = UserCountVo.builder()
                    .value(chineseDateFormat)
                    .num(count)
                    .build();
            resultList.add(userCountVo);
        }

        Date startTime = DateUtil.date(1705248000000L);
        Date endTime = DateUtil.date(1705852800000L);
        List<DateTime> dateList = Optional.ofNullable(DateUtil.rangeToList(startTime, endTime, DateField.DAY_OF_YEAR)).orElse(Collections.emptyList());
        for (DateTime dateTime : dateList) {
            String format = DateUtil.format(dateTime, "YYYY年MM月dd日");
            if (resultList.contains(format)) {
                continue;
            }
            UserCountVo messageCountDto = UserCountVo.builder()
                    .value(format)
                    .num(0L)
                    .build();
            resultList.add(messageCountDto);
        }

        List<UserCountVo> sortedResultList = resultList.stream().sorted(Comparator.comparing(UserCountVo::getValue, Comparator.nullsLast(String::compareTo))).collect(Collectors.toList());
        System.out.println(JSONUtil.toJsonPrettyStr(sortedResultList));
    }

}

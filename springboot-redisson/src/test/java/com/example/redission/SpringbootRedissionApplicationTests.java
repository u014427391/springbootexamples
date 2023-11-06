package com.example.redission;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;
import org.redisson.client.RedisClient;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

@SpringBootTest
class SpringbootRedissionApplicationTests {

    @Resource
    private RedissonClient redissonClient;

    @Test
    void contextLoads() throws ExecutionException, InterruptedException {
        RMap<Object, Object> redissonClientMap = redissonClient.getMap("recordMap");
        Map<String,Integer> map = new HashMap<>();
        map.put("pv" , 1);
        map.put("uv" , 1);
        redissonClientMap.putAll(map);
        System.out.println(redissonClientMap.addAndGet("pv", 2));


        RDeque<Object> recordList = redissonClient.getDeque("recordList");
        recordList.addFirst("1");
        recordList.addFirst("2");
        recordList.addFirst("3");
        IntStream.range(0,3).forEach(a->{
            System.out.println(recordList.poll());
        });

        // 抽奖程序
        RSet<String> recordSet = redissonClient.getSet("recordSet");
        List<String> members = Lists.newArrayList("alice", "tim","tom" , "风清扬", "jack" );
        recordSet.addAll(members);
        RFuture<Set<String>> threeSet = recordSet.removeRandomAsync(3
        );
        RFuture<Set<String>> twoSet = recordSet.removeRandomAsync(2
        );
        RFuture<Set<String>> oneSet = recordSet.removeRandomAsync(1
        );
        System.out.println("三等奖："+threeSet.get());
        System.out.println("二等奖："+twoSet.get());
        System.out.println("一等奖："+oneSet.get());

        // 共同关注的人
        RSet<Object> tom = redissonClient.getSet("tom");
        tom.addAll(Lists.newArrayList("令狐冲","james","风清扬"));
        RSet<Object> jack = redissonClient.getSet("jack");
        jack.addAll(Lists.newArrayList("令狐冲","tim","jack"));
        System.out.println("共同关注的人："+tom.readIntersectionAsync("jack").get());

        // tom可能认识的人
        System.out.println("tom可能认识的人："+jack.readDiffAsync("tom").get());

        // 排名榜
        RScoredSortedSet<String> school = redissonClient.getScoredSortedSet("school");
        school.add(60, "tom");
        school.add(60, "jack");
        school.add(60, "tim");
        school.addScore("tom", 20);
        school.addScore("jack", 10);
        school.addScore("tim", 30);
        RFuture<Collection<ScoredEntry<String>>> collectionRFuture = school.entryRangeReversedAsync(0, -1);
        Iterator<ScoredEntry<String>> iterator = collectionRFuture.get().iterator();
        System.out.println("成绩从高到低排序");
        while(iterator.hasNext()) {
            ScoredEntry<String> next = iterator.next();
            String value = next.getValue();
            System.out.println(value);
        }
        RFuture<Collection<ScoredEntry<String>>> collectionRFuture1 = school.entryRangeReversedAsync(0, 2);
        Iterator<ScoredEntry<String>> iterator1 = collectionRFuture1.get().iterator();
        System.out.println("成绩前三名");
        while (iterator1.hasNext()) {
            System.out.println(iterator1.next().getValue());
        }

    }

}

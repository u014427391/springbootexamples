package com.example.springboot.ai;

import com.example.springboot.ai.comment.DouyinAutoCommenter;
import com.example.springboot.ai.comment.ToutiaoAutoCommenter;
import com.example.springboot.ai.components.OpenAIHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringbootAiAutocommentApplicationTests {

    @Autowired
    private OpenAIHelper openAIHelper;

    @Autowired
    private ToutiaoAutoCommenter toutiaoAutoCommenter;

    @Autowired
    private DouyinAutoCommenter douyinAutoCommenter;

    @Test
    void contextLoads() {
    }

    @Test
    void testOpenAI() throws IOException {
        System.out.println(openAIHelper.generateComment("小米芯片"));
    }

    @Test
    void testToutiaoAutoComment() throws InterruptedException {
        toutiaoAutoCommenter.startAutoComment();
    }

    @Test
    void testDouyinAutoComment() throws InterruptedException {
        douyinAutoCommenter.startAutoComment();
    }

}

package com.example.springboot.ai;

import com.example.springboot.ai.components.OpenAIHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringbootAiAutocommentApplicationTests {

    @Autowired
    private OpenAIHelper openAIHelper;

    @Test
    void contextLoads() {
    }

    @Test
    void testOpenAI() throws IOException {
        System.out.println(openAIHelper.generateComment("小米芯片"));
    }


}

package com.example.ai;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootAiDemoApplicationTests {
	@Autowired
	private OpenAiChatModel chatModel;

	@Autowired(required = false)
	private ChatMemory chatMemory;


	@Test
	void testMemory() {
		// 1. 创建记忆容器
		ChatMemory memory = new InMemoryChatMemory();

		// 2. 构建客户端并添加记忆顾问
		ChatClient client = ChatClient.builder(chatModel)
				.defaultAdvisors(new MessageChatMemoryAdvisor(memory))
				.build();

		// 3. 第一轮对话
		String response1 = client.prompt()
				.user("我叫张三，今年28岁")
				.call()
				.content();
		System.out.println("AI: " + response1);
		// 输出：你好张三，很高兴认识你！

		// 4. 第二轮对话（测试记忆）
		String response2 = client.prompt()
				.user("我今年多大了？")
				.call()
				.content();
		System.out.println("AI: " + response2);
		// 输出：你今年28岁。
	}






	@Test
	void contextLoads() {
	}

}

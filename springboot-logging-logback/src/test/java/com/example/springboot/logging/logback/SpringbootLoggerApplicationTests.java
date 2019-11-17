package com.example.springboot.logging.logback;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootLoggerApplicationTests {

	Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Test
	void contextLoads() {
		LOG.trace("this is trace log infomation....");
		LOG.debug("this is debug log infomation....");
		LOG.info("this is info log infomation....");
		LOG.warn("this is warn log infomation....");
		LOG.error("this is error log infomation....");
	}

}

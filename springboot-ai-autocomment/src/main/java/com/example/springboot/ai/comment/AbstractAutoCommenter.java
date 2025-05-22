package com.example.springboot.ai.comment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public abstract class AbstractAutoCommenter {

    protected static final Logger log = LoggerFactory.getLogger(AbstractAutoCommenter.class);

    protected WebDriver driver;

    public abstract void startAutoComment();

    protected abstract ChromeOptions configureChromeOptions();

    protected abstract boolean isLoggedIn();

    protected abstract boolean login();

    protected abstract List<String> readUrls();

    protected abstract void comment(String url);

    protected void doComment(List<String> articleUrls) {
        log.info("共读取到 {} 个文章链接", articleUrls.size());
        for (int i = 0; i < articleUrls.size(); i++) {
            log.info("处理第 {} 个文章，共 {} 个", i + 1, articleUrls.size());
            comment(articleUrls.get(i));
        }
    }



}
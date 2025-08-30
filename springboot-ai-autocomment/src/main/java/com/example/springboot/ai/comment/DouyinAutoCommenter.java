package com.example.springboot.ai.comment;

import com.example.springboot.ai.components.OpenAIHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class DouyinAutoCommenter extends AbstractAutoCommenter {

    @Value("${chromedriver.path}")
    private String CHROME_DRIVER_PATH;

    @Value("${article.urls.path}") // 复用文章链接文件路径配置
    private String ARTICLE_PATH;

    @Value("${browser.userDataDir}")
    private String USER_DATA_DIR;

    @Value("${login.username}")
    private String loginUsername;

    @Value("${login.password}")
    private String loginPassword;

    @Autowired
    private OpenAIHelper openAIHelper;

    @Override
    public void startAutoComment() {
        WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        ChromeOptions options = configureChromeOptions();
        driver = new ChromeDriver(options);

        try {
            if (!isLoggedIn()) {
                if (!login()) {
                    log.error("登录失败，无法执行评论操作");
                    driver.quit();
                    return;
                }
            }

            List<String> douyinUrls = readUrls();
            if (douyinUrls.isEmpty()) {
                log.info("抖音链接文件为空，没有内容需要评论");
                return;
            }

            doComment(douyinUrls);
        } finally {
            driver.quit();
        }
    }

    @Override
    protected ChromeOptions configureChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--user-data-dir=" + USER_DATA_DIR);
        // 抖音特定User-Agent
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");
        options.addArguments("--window-size=1920,1080");
        return options;
    }

    @Override
    protected boolean isLoggedIn() {
        try {
            driver.get("https://www.douyin.com");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            // 检测登录状态（抖音未登录时会显示"登录"按钮）
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'登录')]")));
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    protected boolean login() {
        try {
            driver.get("https://www.douyin.com");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // 点击登录按钮
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'登录')]")));
            loginButton.click();

            // 切换到密码登录（抖音可能需要先点击"密码登录"选项）
            WebElement pwdLoginOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(text(),'密码登录')]")));
            pwdLoginOption.click();

            // 输入用户名（手机号）
            WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@placeholder='请输入手机号']")));
            usernameField.sendKeys(loginUsername);

            // 输入密码
            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@placeholder='请输入密码']")));
            passwordField.sendKeys(loginPassword);

            // 点击登录按钮
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'登录')]")));
            submitBtn.click();

            // 等待登录完成（可能需要验证码，这里简化处理）
            Thread.sleep(3000);
            driver.navigate().refresh();

            return isLoggedIn();
        } catch (Exception e) {
            log.error("抖音登录失败: {}", e.getMessage());
            return false;
        }
    }

    @Override
    protected List<String> readUrls() {
        List<String> urls = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARTICLE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty() && trimmed.contains("douyin.com")) {
                    urls.add(trimmed);
                }
            }
        } catch (IOException e) {
            log.error("读取抖音链接文件时出错", e);
        }
        return urls;
    }

    @Override
    protected void comment(String url) {
        driver.get(url);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // 等待页面加载完成
            Thread.sleep(2000);

            // 抖音视频页面需要先点击评论按钮打开评论区
            WebElement commentIcon = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class, 'comment-icon')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", commentIcon);

            // 获取视频标题（抖音标题在meta标签中）
            String title = (String) ((JavascriptExecutor) driver).executeScript(
                    "return document.querySelector('meta[property=\"og:title\"]').content");
            log.info("获取视频标题: {}", title);

            // 生成评论内容
            String comment = openAIHelper.generateComment(title);
            log.info("生成评论: {}", comment);

            // 输入评论内容
            WebElement commentBox = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//textarea[contains(@placeholder, '添加评论')]")));
            commentBox.sendKeys(comment);

            // 提交评论
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'发送')]")));
            submitBtn.click();

            // 等待评论提交完成
            Thread.sleep(2000);
        } catch (Exception e) {
            log.error("评论抖音内容时出错", e);
        }
    }
}
package com.example.springboot.ai.comment;

import com.example.springboot.ai.components.OpenAIHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ToutiaoAutoCommenter {

    private static final Logger log = LoggerFactory.getLogger(ToutiaoAutoCommenter.class);

    @Value("${chromedriver.path}")
    private String CHROME_DRIVER_PATH;

    @Value("${article.urls.path}")
    private String ARTICLE_PATH;

    @Value("${browser.userDataDir}")
    private String USER_DATA_DIR;

    @Value("${openai.api-key}")
    private String OPENAI_API_KEY;

    @Value("${login.username}")
    private String loginUsername;

    @Value("${login.password}")
    private String loginPassword;

    private static WebDriver driver;

    @Autowired
    private OpenAIHelper openAIHelper;

    public void startAutoComment() {

        WebDriverManager.chromedriver().setup(); // 自动下载并配置chromedriver
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage"); // 解决Docker环境下的内存问题
        options.addArguments("--no-sandbox"); // 解决Linux环境下的权限问题

        // 设置用户数据目录
        options.addArguments("--user-data-dir=" + USER_DATA_DIR);

        // 设置User-Agent，使用与当前浏览器匹配的版本
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.5112.81 Safari/537.36";
        options.addArguments("user-agent=" + userAgent);

        // 窗口大小
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);

        // 设置隐式等待时间
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        List<String> articleUrls = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARTICLE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    articleUrls.add(line.trim());
                }
            }
        } catch (IOException e) {
            log.error("读取文章链接文件时出错", e);
        }
        log.info("共读取到 {} 个文章链接", articleUrls.size());

        if (!checkLoginStatus()) {
            login(driver);
        }

        for (int i = 0; i < articleUrls.size(); i++) {
            log.info("处理第 {} 个文章，共 {} 个", i + 1, articleUrls.size());

            commentArticle(driver, articleUrls.get(i));

        }

        // 关闭浏览器
        driver.close();
    }

    public void commentArticle(WebDriver driver, String url) {
        driver.get(url);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds());
            WebElement commentBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("comment-textarea")));

            String articleTitle = driver.getTitle();
            log.info("获取文章标题:{}", articleTitle);

            // 假设 OpenAIHelper 是一个可以生成评论的类
            String comment = generateComment(articleTitle);
            log.info("评论内容: {}", comment);
            commentBox.sendKeys(comment);

            WebElement commentButton = driver.findElement(By.className("submit-btn"));
            commentButton.click();

            // 等待几秒钟以确保评论成功提交
            Thread.sleep(3000);

        } catch (Exception e) {
            log.error("评论文章时出错", e);
        }
    }

    private void login(WebDriver driver) {
        driver.get("http://www.toutiao.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds());

        try {

            // 等待页面完全加载
            wait.until(webDriver ->
                    ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

            // 等待登录按钮出现在 DOM 中
            WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".login-button")));
            //log.info("登录按钮的文本: {}", loginButton.getText());

            // 尝试点击登录按钮
            try {
                loginButton.click();
            } catch (Exception e) {
                // 如果点击失败，尝试使用 JavaScript 执行点击
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginButton);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
            }


            WebElement clickableAccountLoginOption = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("li[aria-label='账密登录']")));
            clickableAccountLoginOption.click();

            WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("web-login-normal-input__input")));
            usernameField.sendKeys(loginUsername);

            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("web-login-button-input__input")));
            passwordField.sendKeys(loginPassword);

            WebElement agreeCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.className("web-login-confirm-info__checkbox")));
            if (!agreeCheckbox.isSelected()) {
                agreeCheckbox.click();
            }

            WebElement loginSubmitButton = driver.findElement(By.className("web-login-button"));
            loginSubmitButton.click();

            // 登录成功后刷新页面
            driver.navigate().refresh();

        } catch (Exception e) {
            log.error("登录时出错:{}", e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private static boolean checkLoginStatus() {
        // 实现检查登录状态的逻辑
        // 这里可以检查特定的元素是否存在，或者访问一个只有登录用户才能访问的页面
        try {
            driver.get("http://www.toutiao.com");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3).getSeconds());
            WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".login-button")));
            return false; // 如果找到了登录按钮，则未登录
        } catch (Exception e) {
            return true; // 如果没有找到登录按钮，则已登录
        }
    }

    private String generateComment(String articleTitle) throws IOException {
        String comment = openAIHelper.generateComment(articleTitle);
        return comment;
    }
}
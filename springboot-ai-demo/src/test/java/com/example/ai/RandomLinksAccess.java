package com.example.ai;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * @author yanZhiHang
 * @date 2023/11/30 10:20
 * 刷多个url访问量
 */
public class RandomLinksAccess {
    public static void main(String[] args) {
        // 链接地址列表
        List<String> urls = new ArrayList<>();
        urls.add("https://blog.csdn.net");

        // 添加更多URL

        // 创建定时任务执行器
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(urls.size());

        // 在半小时内，每隔3到5分钟随机访问一次链接
        int halfHour = 24 * 60 * 60; // 一天对应的秒数
        int minInterval = 60; // 1分钟对应的秒数
        int maxInterval = 2 * 40; // 1.5分钟对应的秒数

        // 执行任务
        urls.forEach(url -> {
            int randomInterval = new Random().nextInt(maxInterval - minInterval + 1) + minInterval;
            executorService.scheduleAtFixedRate(() -> {
                // 获取当前时间
                LocalDateTime currentTime = LocalDateTime.now();
                // 格式化时间为字符串
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedTime = currentTime.format(formatter);
                try {
                    // 创建URL对象
                    URL obj = new URL(url);
                    // 打开连接
                    HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                    // 设置请求方法
                    connection.setRequestMethod("GET");
                    // 获取响应码
                    int responseCode = connection.getResponseCode();
                    System.out.println(formattedTime + " - code：" + responseCode + " - URL：" + url);
                    // 读取响应内容
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, 0, randomInterval, TimeUnit.SECONDS);
        });

        // 等待半小时后关闭执行器
        executorService.schedule(() -> executorService.shutdown(), halfHour, TimeUnit.SECONDS);
    }
}
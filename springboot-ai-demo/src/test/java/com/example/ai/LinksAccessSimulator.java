package com.example.ai;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 模拟真实用户访问URL（防IP封禁+效率优化）
 * 核心：人类行为模拟 + 可控并发 + 反爬特征隐藏
 */
public class LinksAccessSimulator {
    // ===================== 核心配置（可根据需求调整）=====================
    // 模拟真实用户数（并发数，建议2-3，过多易封IP）
    private static final int SIMULATED_USERS = 1;
    // 总运行时长（半小时，单位秒）
    private static final int TOTAL_RUN_SECONDS = 30 * 60;
    // 人类行为请求间隔（20-60秒，避免高频触发风控）
    private static final int MIN_INTERVAL_SEC = 20;
    private static final int MAX_INTERVAL_SEC = 60;
    // 目标URL列表
    private static final List<String> TARGET_URLS = Arrays.asList(
            "https://blog.csdn.net"
    );

    // ===================== 反爬核心：请求头池（模拟多设备/多场景）=====================
    // 多浏览器/设备User-Agent池（避免单一标识）
    private static final List<String> USER_AGENT_POOL = Arrays.asList(
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_2) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.1 Safari/605.1.15",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Edge/120.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Linux; Android 13; SM-G998B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"
    );
    // Referer池（模拟从搜索引擎/CSDN首页跳转，避免直接访问）
    private static final List<String> REFERER_POOL = Arrays.asList(
            "https://www.csdn.net/",
            "https://blog.csdn.net/",
            "https://www.baidu.com/s?wd=" + URLEncoder.encode("Java并发编程实战", StandardCharsets.UTF_8),
            "https://www.google.com/search?q=" + URLEncoder.encode("SpringBoot教程", StandardCharsets.UTF_8)
    );

    // ===================== 全局工具（线程安全）=====================
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final AtomicBoolean IS_RUNNING = new AtomicBoolean(true); // 运行标记

    // 初始化SSL（解决HTTPS握手问题）
    static {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 1. 创建线程池（模拟真实用户，核心数=模拟用户数，避免过度并发）
        ExecutorService executor = new ThreadPoolExecutor(
                SIMULATED_USERS,
                SIMULATED_USERS,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(5), // 任务队列，避免堆积
                r -> new Thread(r, "User-Sim-" + UUID.randomUUID().toString().substring(0, 8)),
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略：兜底执行，避免任务丢失
        );

        // 2. 启动每个模拟用户的任务
        for (int i = 0; i < SIMULATED_USERS; i++) {
            executor.submit(new UserBehaviorSimulator());
        }

        // 3. 定时关闭（总时长后优雅停机）
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            IS_RUNNING.set(false);
            executor.shutdown();
            scheduler.shutdown();
            System.out.printf("[%s] 任务执行完毕，已停止所有模拟用户%n", LocalDateTime.now().format(FORMATTER));
        }, TOTAL_RUN_SECONDS, TimeUnit.SECONDS);

        // 4. 等待线程池关闭（防止主线程提前退出）
        try {
            if (!executor.awaitTermination(TOTAL_RUN_SECONDS + 10, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // 强制关闭未完成任务
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    /**
     * 模拟单个用户的访问行为（核心：随机、非机械、接近人类）
     */
    static class UserBehaviorSimulator implements Runnable {
        // 每个用户维护独立Cookie（模拟会话，避免每次都是新访客）
        private String userCookie = generateRandomCookie();

        @Override
        public void run() {
            while (IS_RUNNING.get()) {
                try {
                    // 1. 随机选择URL（避免固定顺序访问）
                    String targetUrl = TARGET_URLS.get(RANDOM.nextInt(TARGET_URLS.size()));
                    // 2. 执行访问（核心逻辑）
                    accessUrl(targetUrl);
                    // 3. 人类行为间隔（正态分布，避免固定间隔）
                    int sleepTime = getHumanLikeInterval();
                    System.out.printf("[%s] 模拟用户[%s] 访问完成，下次访问间隔：%d秒%n",
                            LocalDateTime.now().format(FORMATTER),
                            Thread.currentThread().getName(),
                            sleepTime);
                    TimeUnit.SECONDS.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break; // 线程中断，退出循环
                } catch (Exception e) {
                    System.err.printf("[%s] 模拟用户[%s] 访问失败：%s%n",
                            LocalDateTime.now().format(FORMATTER),
                            Thread.currentThread().getName(),
                            e.getMessage());
                    // 失败后随机延迟重试（避免高频重试触发风控）
                    try {
                        TimeUnit.SECONDS.sleep(RANDOM.nextInt(10) + 5);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }

        /**
         * 执行URL访问（效率+反爬优化）
         */
        private void accessUrl(String url) throws IOException {
            HttpURLConnection conn = null;
            try {
                URL obj = new URL(url);
                conn = (HttpURLConnection) obj.openConnection();

                // ========== 反爬：模拟真实请求头 ==========
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", USER_AGENT_POOL.get(RANDOM.nextInt(USER_AGENT_POOL.size())));
                conn.setRequestProperty("Referer", REFERER_POOL.get(RANDOM.nextInt(REFERER_POOL.size())));
                conn.setRequestProperty("Cookie", userCookie); // 维护用户会话
                // 补充完整请求头，避免被识别为爬虫
                conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
                conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
                conn.setRequestProperty("Cache-Control", "max-age=0");
                conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
                conn.setRequestProperty("Connection", "keep-alive"); // 连接复用，提升效率

                // ========== 效率优化：超时配置 ==========
                conn.setConnectTimeout(5000);  // 连接超时5秒（避免阻塞）
                conn.setReadTimeout(8000);     // 读取超时8秒
                conn.setUseCaches(false);      // 禁用缓存，模拟真实刷新

                // ========== 执行访问（仅校验响应码，跳过响应体读取，提升效率） ==========
                int responseCode = conn.getResponseCode();
                System.out.printf("[%s] 模拟用户[%s] 访问URL：%s | 响应码：%d%n",
                        LocalDateTime.now().format(FORMATTER),
                        Thread.currentThread().getName(),
                        url,
                        responseCode);

                // 更新Cookie（模拟会话延续）
                String newCookie = conn.getHeaderField("Set-Cookie");
                if (newCookie != null && !newCookie.isEmpty()) {
                    userCookie = newCookie.split(";")[0]; // 简化Cookie处理，保留核心部分
                }
            } finally {
                if (conn != null) {
                    conn.disconnect(); // 关闭连接，避免资源泄漏
                }
            }
        }

        /**
         * 生成人类行为级随机间隔（正态分布，更接近真实浏览习惯）
         * 避免均匀随机，减少机械特征
         */
        private int getHumanLikeInterval() {
            double mean = (MIN_INTERVAL_SEC + MAX_INTERVAL_SEC) / 2.0; // 均值
            double stdDev = 10.0; // 标准差
            // 生成正态分布随机数，限制在[MIN, MAX]范围内
            double randomVal = RANDOM.nextGaussian() * stdDev + mean;
            return (int) Math.max(MIN_INTERVAL_SEC, Math.min(MAX_INTERVAL_SEC, randomVal));
        }

        /**
         * 生成随机Cookie（模拟用户会话）
         */
        private String generateRandomCookie() {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            return String.format("csdn_uid=%s; uuid=%s; Hm_lvt_%s=%d;",
                    uuid.substring(0, 16),
                    uuid,
                    uuid.substring(0, 8),
                    System.currentTimeMillis() / 1000);
        }
    }
}
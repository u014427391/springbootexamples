
# SpringBoot 2.0 与微服务实践系列教程

本专栏基于 **Spring Boot 2.0**，配套自己编写的代码示例和图解，内容涵盖从基础入门到微服务架构的实践。包括基础配置、Web开发、数据库操作、Redis、日志管理，以及企业级开发中的消息队列、MongoDB、Elasticsearch、OAuth2.0、JWT、Spring Cloud、Dubbo、Spring Cloud Alibaba 等内容，形成一个完整的知识体系，适合作为入门教程。

专栏代码已托管至 GitHub，可通过以下链接克隆到本地：[GitHub代码下载](https://github.com/u014427391/springbootexamples)。

---

## 开发环境

- **JDK**：1.8  
- **Spring Boot**：2.3.2.RELEASE  
- **Spring Cloud**：Hoxton.SR9  
- **Spring Cloud Alibaba**：2.2.2.RELEASE  
- **MyBatis Plus**：3.4.3.4  
- **Maven**：3.2+  
- **MySQL**：5.7.36  

## 开发工具

- **IntelliJ IDEA**  
- **smartGit**  
- **Navicat**  

---

## 快速开始

可以通过阿里云提供的 **Cloud Native App Initializer** 快速搭建 Spring Boot 项目：[阿里云 Spring Boot 脚手架](https://start.aliyun.com/bootstrap.html)。

---

## 学习入门前言

- [SpringBoot 系列之快速创建项目教程](https://blog.csdn.net/u014427391/article/details/102870300)

---

## 一、配置使用篇

### 1.1 配置文件用法

- [外部配置用法简介](https://blog.csdn.net/u014427391/article/details/102995991)  
- [配置文件加载位置](https://blog.csdn.net/u014427391/article/details/102994600)  
- [配置文件占位符使用](https://blog.csdn.net/u014427391/article/details/102985940)  

### 1.2 配置注解使用介绍

- [@PropertySource 用法简介](https://blog.csdn.net/u014427391/article/details/102931513)  
- [@Value 和 @ConfigurationProperties](https://blog.csdn.net/u014427391/article/details/102887045)  

### 1.3 YAML 配置使用

- [YAML 配置用法学习笔记](https://blog.csdn.net/u014427391/article/details/102877780)  
- [@PropertySource 读取 YAML 文件](https://blog.csdn.net/u014427391/article/details/103235131)  

### 1.4 配置 profiles 多环境

- [profiles 配置多环境用法介绍](https://blog.csdn.net/u014427391/article/details/102931424)  
- [profiles 配置多环境使用补充](https://blog.csdn.net/u014427391/article/details/89792248)  

---

## 二、数据访问篇

### 2.1 JDBC 数据访问

- [JDBC 数据访问](https://blog.csdn.net/u014427391/article/details/103538659)  

### 2.2 ORM 框架 MyBatis

- [集成 MyBatis 教程](https://blog.csdn.net/u014427391/article/details/103547514)  

### 2.3 Spring Data JPA

- [Spring Data JPA 集成教程](https://blog.csdn.net/u014427391/article/details/103551914)  
- [JPA 实现按年月日查询](https://smilenicky.blog.csdn.net/article/details/135852229)  

### 2.4 MyBatis Plus

- [集成 MyBatis Plus](https://blog.csdn.net/u014427391/article/details/121888057)  
- [MyBatis Plus 自动填充实现](https://smilenicky.blog.csdn.net/article/details/134184177)  
- [MyBatis Plus 实现分组查询](https://smilenicky.blog.csdn.net/article/details/135885378)  

### 2.5 连接池 Druid

- [集成 Druid 配置数据源监控](https://blog.csdn.net/u014427391/article/details/103547228)  
- [Druid 进行维度的统计和监控](https://blog.csdn.net/u014427391/article/details/70890506)  

---

## 三、日志处理篇

### 3.1 基础入门系列

- [日志框架介绍及其原理简介](https://blog.csdn.net/u014427391/article/details/103082396)  
- [日志框架使用教程](https://smilenicky.blog.csdn.net/article/details/103101517)  

### 3.2 Logback 入门

- [集成 Logback 实现日志打印](https://blog.csdn.net/u014427391/article/details/86848207)  

### 3.3 Log4j 入门

- [切换 Log4j 日志框架](https://blog.csdn.net/u014427391/article/details/103108102)  

---

## 四、Web 使用篇

### 4.1 模板引擎之 JSP

- [集成 JSP 模板引擎](https://blog.csdn.net/u014427391/article/details/103445785)  

### 4.2 模板引擎之 Thymeleaf

- [集成 Thymeleaf 用法手册](https://blog.csdn.net/u014427391/article/details/103241846)  

### 4.3 国际化多语言

- [i18n 国际化多语言支持教程](https://blog.csdn.net/u014427391/article/details/103226530)  

### 4.4 RESTful API 支持

- [HATEOAS 用法简介](https://blog.csdn.net/u014427391/article/details/102650252)  
- [基于 Jersey 实现 RESTful 风格文件上传 API](https://blog.csdn.net/u014427391/article/details/132254789)  
- [集成 Resteasy 实现 RESTful 接口](https://blog.csdn.net/u014427391/article/details/132260754)  

### 4.5 在线文档 Swagger

- [集成 Swagger2](https://blog.csdn.net/u014427391/article/details/90706219)  

### 4.6 Spring Validation 校验

- [表单参数校验整理](https://blog.csdn.net/u014427391/article/details/122298707)  
- [自定义枚举类的数据校验注解](https://blog.csdn.net/u014427391/article/details/121980957)  

### 4.7 RestTemplate

- [RestTemplate 使用示例](https://smilenicky.blog.csdn.net/article/details/108831497)  

### 4.8 EasyExcel 报表

- [集成 EasyExcel 导入合并行数据](https://blog.csdn.net/u014427391/article/details/123529499)  
- [对 Excel 报表的校验提示](https://blog.csdn.net/u014427391/article/details/123532264)  

### 4.9 Jackson 应用

- [自定义 Jackson 对象映射器](https://smilenicky.blog.csdn.net/article/details/134184022)  

### 4.10 WebSocket 应用

- [搭建 WebSocket 应用](https://smilenicky.blog.csdn.net/article/details/139777159)  

---

## 五、缓存处理篇

### 5.1 缓存入门

- [项目中缓存使用详细教程](https://blog.csdn.net/u014427391/article/details/105226170)  

### 5.2 集成 Redis 教程

- [集成 Redis 实现缓存处理](https://blog.csdn.net/u014427391/article/details/78799623)  
- [使用 Redis 做 MyBatis 二级缓存](https://blog.csdn.net/u014427391/article/details/122255831)  
- [使用 Redis ZSet 实现排序分页](https://smilenicky.blog.csdn.net/article/details/134772873)  
- [集成 Redisson 入门与实践](https://smilenicky.blog.csdn.net/article/details/134202872)  
- [使用 Redis 实现延时队列](https://smilenicky.blog.csdn.net/article/details/136164815)  
- [集成 Jedis 教程](https://smilenicky.blog.csdn.net/article/details/134580788)  
- [基于 Jedis 实现分布式锁](https://smilenicky.blog.csdn.net/article/details/134884618)  

### 5.3 集成 MongoDB 教程

- [Spring Data MongoDB 教程](https://blog.csdn.net/u014427391/article/details/122717644)  
- [MongoCollection 示例](https://blog.csdn.net/u014427391/article/details/122828065)  
- [MongoDB Aggregations](https://blog.csdn.net/u014427391/article/details/122828360)  
- [MongoTemplate 加 PageHelper 分页实现](https://smilenicky.blog.csdn.net/article/details/128012318)  
- [基于 MongoRepository 实现分页](https://smilenicky.blog.csdn.net/article/details/128216735)  

---

## 六、消息队列篇

### 6.1 消息队列之 RabbitMQ

- [RabbitMQ 使用实用教程](https://blog.csdn.net/u014427391/article/details/105414281)  

### 6.2 消息队列之 Kafka

- [集成 Kafka 实现事件发布](https://blog.csdn.net/u014427391/article/details/122091467)  

---

## 七、微服务之 Dubbo

- [集成 Dubbo 实现微服务教程](https://blog.csdn.net/u014427391/article/details/103848114)  
- [集成 Dubbo 的方式](https://blog.csdn.net/u014427391/article/details/103945442)  

---

## 八、微服务之 Spring Cloud

### 8.1 服务治理实现

- [使用 Netflix Eureka 进行服务治理](https://blog.csdn.net/u014427391/article/details/107567035)  

### 8.2 声明式服务调用

- [声明式服务调用 Netflix Feign](https://blog.csdn.net/u014427391/article/details/107632921)  

### 8.3 客户端负载均衡

- [客户端负载均衡 Netflix Ribbon](https://blog.csdn.net/u014427391/article/details/107662300)  

### 8.4 服务容错保护

- [服务容错保护 Netflix Hystrix](https://blog.csdn.net/u014427391/article/details/107819700)  

### 8.5 API 网关服务

- [API 网关服务 Zuul](https://blog.csdn.net/u014427391/article/details/107845774)  
- [API Gateway 开发手册](https://blog.csdn.net/u014427391/article/details/108584446)  

### 8.6 分布式配置中心

- [分布式配置中心极速入门与实践](https://blog.csdn.net/u014427391/article/details/108491861)  

---

## 九、微服务之 Spring Cloud Alibaba

### 9.1 Nacos 服务注册与发现

- [快速开始搭建 Nacos 环境](https://smilenicky.blog.csdn.net/article/details/111628299)  
- [Nacos 服务注册与发现](https://smilenicky.blog.csdn.net/article/details/111518470)  

### 9.2 Nacos 分布式配置中心

- [Nacos 分布式配置中心](https://smilenicky.blog.csdn.net/article/details/111519080)  

### 9.3 Nacos 集成分布式服务组件 Dubbo

- [分布式服务组件 Dubbo](https://smilenicky.blog.csdn.net/article/details/112826860)  

### 9.4 集成阿里 Canal 监听 MySQL

- [集成阿里 Canal 监听 MySQL Binlog](https://blog.csdn.net/u014427391/article/details/121989219)  
- [Canal 和 Kafka 实现异步实时更新](https://blog.csdn.net/u014427391/article/details/122211056)  

### 9.5 服务防护组件 Sentinel

- [服务防护组件 Sentinel](https://smilenicky.blog.csdn.net/article/details/113603534)  

---

## 十、单点登录篇

### 10.1 单点登录入门

- [单点登录集群安装教程](http://blog.csdn.net/u014427391/article/details/78653482)  

### 10.2 单点登录框架 CAS

- [CAS 原理简单介绍](https://blog.csdn.net/u014427391/article/details/82083995)  
- [使用 cas-overlay 搭建服务端（一）](https://blog.csdn.net/u014427391/article/details/105818468)  
- [CAS 5.3.1 支持 JDBC 认证登录（二）](https://blog.csdn.net/u014427391/article/details/105603895)  
- [CAS 5.3.1 自定义 JDBC 认证策略（三）](https://blog.csdn.net/u014427391/article/details/105820486)  
- [CAS 5.3.1 自定义 Shiro 认证策略（四）](https://blog.csdn.net/u014427391/article/details/105820586)  

### 10.3 单点登录方案 OAuth2.0

- [OAuth2.0 集成 JWT 实现单点登录](https://smilenicky.blog.csdn.net/article/details/106017401)  

### 10.4 前后端分类鉴权 JWT

- [前后端接口安全技术 JWT](https://smilenicky.blog.csdn.net/article/details/107099840)  

---

## 十一、OAuth2.0 篇

### 11.1 OAuth2.0 入门

- [基本概念和运作流程（一）](https://smilenicky.blog.csdn.net/article/details/106543396)  

### 11.2 OAuth2.0 四种模式

- [授权码模式实践教程（二）](https://smilenicky.blog.csdn.net/article/details/106551368)  
- [简化模式实践教程（三）](https://smilenicky.blog.csdn.net/article/details/106686607)  
- [密码模式实践教程（四）](https://smilenicky.blog.csdn.net/article/details/106687880)  
- [客户端模式实践教程（五）](https://smilenicky.blog.csdn.net/article/details/106689987)  

### 11.3 OAuth2.0 信息存储

- [信息数据库存储教程（六）](https://smilenicky.blog.csdn.net/article/details/106757121)  
- [信息 Redis 存储教程（七）](https://smilenicky.blog.csdn.net/article/details/106790398)  
- [JWT 令牌实践教程（八）](https://smilenicky.blog.csdn.net/article/details/106805642)  

---

## 十二、搜索引擎篇

### 12.1 搜索引擎之 Elasticsearch

- [Elasticsearch 极速入门与实践教程](https://smilenicky.blog.csdn.net/article/details/107365858)  

---

## 十三、Task 任务篇

### 13.1 异步任务使用

- [异步任务 @Async 使用教程](https://smilenicky.blog.csdn.net/article/details/107458630)  

### 13.2 定时任务使用

- [使用 Spring Task 实现定时任务](https://smilenicky.blog.csdn.net/article/details/107488534)  

### 13.3 邮件任务使用

- [发送邮件极速入门与实践](https://smilenicky.blog.csdn.net/article/details/107517585)  

---

## 十四、部署监控篇

### 14.1 热部署实践

- [devtools 热部署实现教程](https://smilenicky.blog.csdn.net/article/details/107531226)  
- [Spring Boot Actuator 系统监控与管理入门教程](https://smilenicky.blog.csdn.net/article/details/107535188)  

---

## 十五、安全授权篇

### 15.1 Spring Security 使用

- [极速入门与实践教程](https://smilenicky.blog.csdn.net/article/details/107559687)  

---

## 十六、AI 应用篇

- [Spring AI + DeeSeek 创建 AI 应用](https://smilenicky.blog.csdn.net/article/details/145666415)  

---

## 十七、源码学习篇

### 17.1 Spring Boot 自动配置原理

- [自动配置原理简介](https://blog.csdn.net/u014427391/article/details/102844681)  
- [异常处理自动配置](https://blog.csdn.net/u014427391/article/details/103334278)  
- [Locale 自动配置](https://blog.csdn.net/u014427391/article/details/103258401)  
- [Spring MVC 自动配置](https://blog.csdn.net/u014427391/article/details/103240199)  

### 17.2 Spring Boot 嵌入式 Servlet 容器

- [嵌入式 Servlet 容器](https://blog.csdn.net/u014427391/article/details/103425427)  

### 17.3 Spring Boot 注解系列

- [@PropertySource 注解实现](https://blog.csdn.net/u014427391/article/details/103258216)  

### 17.4 Spring Boot 自定义 Starter

- [自定义 starter 实践教程](https://blog.csdn.net/u014427391/article/details/103807042)  

---

## 拓展篇

### 函数式编程语言

- [集成 Scala 开发 API 接口](https://smilenicky.blog.csdn.net/article/details/124326849)  

---

## 技术博客公众号

![微信](https://i-blog.csdnimg.cn/blog_migrate/7b8ef397c8784853ca86a820e351c5d4.png)

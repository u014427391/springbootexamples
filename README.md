# springboot examples






对应[SpringBoot系列博客专栏](https://blog.csdn.net/u014427391/category_9195353.html)，例子代码下载，代码暂时托管于GitHub，在github上clone到本地既可，[github下载中链接](https://github.com/u014427391/springbootexamples)，本博客不定时更新
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200722230650596.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTQ0MjczOTE=,size_16,color_FFFFFF,t_70)


* Spring框架：作为JavaEE框架领域的一款重要的开源框架，在企业应用开发中有着很重要的作用，同时Spring框架及其子框架很多，所以知识量很广。

* Spring Boot：一款Spring系统的一款框架，是2014年推出的一款使Spring框架开发变得容易的框架。学过Spring框架的都知识，Spring框架难以避免地需要配置不少XMl，而使用Spring Boot框架的话，就可以使用注解开发，极大地简化基于Spring框架的开发。Spring Boot充分利用了JavaConfig的配置模式以及“约定优于配置”的理念，能够极大的简化基于Spring MVC的Web应用和REST服务开发。

本专栏基于Springboot2.0，配套自己写的代码例子，内容设计基本的配置用法，web，数据库，Redis，也涉及到企业级开发的消息队列，dubbo，搜索引擎等方面，并有源码的简单分析，适合作为入门教程

@[toc]
## 学习入门前言
[SpringBoot系列之快速创建项目教程](https://blog.csdn.net/u014427391/article/details/102870300)
## 一、配置使用篇

### 1.1 配置文件用法
* [SpringBoot系列之外部配置用法简介](https://blog.csdn.net/u014427391/article/details/102995991)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-config)
* [SpringBoot系列之配置文件加载位置](https://blog.csdn.net/u014427391/article/details/102994600)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-config)
* [SpringBoot系列之配置文件占位符使用](https://blog.csdn.net/u014427391/article/details/102985940)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-config)

### 1.2 配置注解使用介绍
* [SpringBoot系列之@PropertySource用法简介](https://blog.csdn.net/u014427391/article/details/102931513)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-config)
* [SpringBoot系列之@Value和@ConfigurationProperties](https://blog.csdn.net/u014427391/article/details/102887045)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-config)

### 1.3 YAML配置使用
* [SpringBoot系列之YAML配置用法学习笔记](https://blog.csdn.net/u014427391/article/details/102877780)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-config)
* [SpringBoot系列之@PropertySource读取yaml文件](https://blog.csdn.net/u014427391/article/details/103235131)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-config)

### 1.4 配置profis多环境
* [SpringBoot系列之profiles配置多环境](https://blog.csdn.net/u014427391/article/details/89792248)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-config)
* [SpringBoot系列之profiles配置多环境用法介绍](https://blog.csdn.net/u014427391/article/details/102931424)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-config)

## 二、数据访问篇
### 2.1 JDBC数据访问
* [SpringBoot系列之JDBC数据访问](https://blog.csdn.net/u014427391/article/details/103538659)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/sppringboot-jdbc)
### 2.2 ORM框架Mybatis
* [SpringBoot系列之集成Mybatis教程](https://blog.csdn.net/u014427391/article/details/103547514)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-mybatis)
### 2.3 Spring data JPA
* [SpringBoot系列之Spring Data Jpa集成教程](https://blog.csdn.net/u014427391/article/details/103551914)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-jpa)
### 2.4 连接池Druid
* [SpringBoot系列之集成Druid配置数据源监控](https://blog.csdn.net/u014427391/article/details/103547228)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/sppringboot-jdbc)
* [SpringBoot系列之Druid进行维度的统计和监控](https://blog.csdn.net/u014427391/article/details/70890506)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/sppringboot-jdbc)

## 三、日志处理篇
### 3.1 基础入门系列
* [SpringBoot系列之日志框架介绍及其原理简介](https://blog.csdn.net/u014427391/article/details/103082396)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-logging-logback)
* [SpringBoot系列之日志框架使用教程](https://smilenicky.blog.csdn.net/article/details/103101517)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-logging-logback)
### 3.2 logback入门
* [SpringBoot系列之集成logback实现日志打印](https://blog.csdn.net/u014427391/article/details/86848207)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-logging-logback)
### 3.3 log4j入门
* [SpringBoot系列之切换log4j日志框架](https://blog.csdn.net/u014427391/article/details/103108102)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-logging-log4j)

## 四、Web使用篇
### 4.1 模板引擎之jsp
* [SpringBoot系列之集成jsp模板引擎](https://blog.csdn.net/u014427391/article/details/103445785)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-jsp)
### 4.2 模板引擎之Thymeleaf
* [SpringBoot系列之集成Thymeleaf用法手册](https://blog.csdn.net/u014427391/article/details/103241846)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/thymeleaf/thymeleafexamples-gtvg)
### 4.3 国际化多语言
* [SpringBoot系列之i18n国际化多语言支持教程](https://blog.csdn.net/u014427391/article/details/103226530)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-web)
### 4.4 RESTFul API支持

* [SpringBoot系列之HATEOAS用法简介](https://blog.csdn.net/u014427391/article/details/102650252)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-hateoas)
### 4.5 在线文档Swagger
* [SpringBoot系列之集成Swagger2](https://blog.csdn.net/u014427391/article/details/90706219)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples)

## 五、缓存处理篇
### 5.1 缓存入门
* [SpringBoot系列之项目中缓存使用详细教程](https://blog.csdn.net/u014427391/article/details/105226170)     &nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-cache)
### 5.2 集成Redis教程
* [SpringBoot系列之集成Redis实现缓存处理](https://blog.csdn.net/u014427391/article/details/78799623)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/jeeplatform)

## 六、消息队列篇
### 6.1 消息队列之RabbitMQ
* [SpringBoot系列之RabbitMQ使用实用教程](https://blog.csdn.net/u014427391/article/details/105414281)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-rabbitmq)
## 七、微服务篇
### 7.1 微服务之Dubbo
* [SpringBoot系列之集成Dubbo实现微服务教程](https://blog.csdn.net/u014427391/article/details/103848114)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-dubbo)
* [SpringBoot系列之集成Dubbo的方式](https://blog.csdn.net/u014427391/article/details/103945442)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-dubbo)

### 7.2 微服务之SpringCloud
#### 7.2.1 服务治理实现
* [SpringCloud系列使用Netflix Eureka进行服务治理](https://blog.csdn.net/u014427391/article/details/107567035)
#### 7.2.2 声明式服务调用
* [SpringCloud系列之声明式服务调用Netflix Feign
](https://blog.csdn.net/u014427391/article/details/107632921)  
#### 7.2.3 客户端负载均衡
* [SpringCloud系列之客户端负载均衡Netflix Ribbon
](https://blog.csdn.net/u014427391/article/details/107662300)
#### 7.2.4 服务容错保护
* [SpringCloud系列之服务容错保护Netflix Hystrix
](https://blog.csdn.net/u014427391/article/details/107819700)
#### 7.2.5 API网关服务
* [SpringCloud系列之API网关(Gateway)服务Zuul
](https://blog.csdn.net/u014427391/article/details/107845774) 
* [SpringCloud系列之API Gateway开发手册
](https://blog.csdn.net/u014427391/article/details/108584446)
#### 7.2.6 分布式配置中心
* [SpringCloud系列之分布式配置中心极速入门与实践
](https://blog.csdn.net/u014427391/article/details/108491861) 

### 7.3 微服务之Spring Cloud Alibaba
* [Spring Cloud Alibaba系列之快速开始搭建Nacos环境](https://smilenicky.blog.csdn.net/article/details/111628299)
* [Spring Cloud Alibaba系列之Nacos服务注册与发现](https://smilenicky.blog.csdn.net/article/details/111518470)
* [Spring Cloud Alibaba系列之Nacos分布式配置中心](https://smilenicky.blog.csdn.net/article/details/111519080)

## 八、单点登录篇
### 8.1 单点登录入门
* [ 单点登录集群安装教程](http://blog.csdn.net/u014427391/article/details/78653482)
### 8.2 单点登录框架CAS
* [CAS单点登录系列之原理简单介绍](https://blog.csdn.net/u014427391/article/details/82083995)
* [CAS系列之使用cas overlay搭建服务端（一）](https://blog.csdn.net/u014427391/article/details/105818468)
* [CAS 5.3.1系列之支持JDBC认证登录（二）](https://blog.csdn.net/u014427391/article/details/105603895)
* [CAS 5.3.1系列之自定义JDBC认证策略（三）](https://blog.csdn.net/u014427391/article/details/105820486)
* [CAS 5.3.1系列之自定义Shiro认证策略（四）](https://blog.csdn.net/u014427391/article/details/105820586)
### 8.3 单点登录方案OAuth2.0
* [OAuth2.0系列之集成JWT实现单点登录](https://smilenicky.blog.csdn.net/article/details/106017401)
### 8.4 前后端分类鉴权JWT
* [SpringBoot系列之前后端接口安全技术JWT](https://smilenicky.blog.csdn.net/article/details/107099840)

## 九、OAuth2.0篇
### 9.1 OAuth2.0入门
* [OAuth2.0系列之基本概念和运作流程（一）](https://smilenicky.blog.csdn.net/article/details/106543396)
### 9.2 OAuth2.0四种模式
* [OAuth2.0系列之授权码模式实践教程（二）](https://smilenicky.blog.csdn.net/article/details/106551368)
* [OAuth2.0系列之简化模式实践教程（三）](https://smilenicky.blog.csdn.net/article/details/106686607)
* [OAuth2.0系列之密码模式实践教程（四）](https://smilenicky.blog.csdn.net/article/details/106687880)
* [OAuth2.0系列之客户端模式实践教程（五）](https://smilenicky.blog.csdn.net/article/details/106689987)
### 9.3 OAuth2.0信息存储
* [OAuth2.0系列之信息数据库存储教程（六）](https://smilenicky.blog.csdn.net/article/details/106757121)
* [OAuth2.0系列之信息Redis存储教程（七）](https://smilenicky.blog.csdn.net/article/details/106790398)
* [OAuth2.0系列之JWT令牌实践教程（八）](https://smilenicky.blog.csdn.net/article/details/106805642)

## 十、搜索引擎篇
### 10.1 搜索引擎之Elasticsearch
* [SpringBoot系列之Elasticsearch极速入门与实践教程](https://smilenicky.blog.csdn.net/article/details/107365858)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-elasticsearch)

## 十一、Task任务篇
### 11.1 异步任务使用
* [SpringBoot系列之异步任务@Async使用教程](https://smilenicky.blog.csdn.net/article/details/107458630)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-async)
### 11.2 定时任务使用
* [SpringBoot系列之使用Spring Task实现定时任务](https://smilenicky.blog.csdn.net/article/details/107488534)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-scheduler-task)


### 11.3 邮件任务使用
* [SpringBoot系列之发送邮件极速入门与实践](https://smilenicky.blog.csdn.net/article/details/107517585)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-email)


## 十二、部署监控篇
### 12.1 热部署实践
* [SpringBoot系统之devtools热部署实现教程](https://smilenicky.blog.csdn.net/article/details/107531226)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-deploy)

* [Spring Boot Actuator系统监控与管理入门教程](https://smilenicky.blog.csdn.net/article/details/107535188)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-actuator)

## 十三、安全授权篇
### 13.1 SpringSecurity使用
* [Spring Security系列之极速入门与实践教程](https://smilenicky.blog.csdn.net/article/details/107559687)&nbsp;&nbsp;&nbsp; &nbsp;>> [source download](https://github.com/u014427391/springbootexamples/tree/master/springboot-security)
## 十四、源码学习篇
### 14.1 SpringBoot自动配置原理
* [SpringBoot源码学习系列之自动配置原理简介](https://blog.csdn.net/u014427391/article/details/102844681)
* [SpringBoot源码学习系列之异常处理自动配置](https://blog.csdn.net/u014427391/article/details/103334278)
* [SpringBoot源码学习系列之Locale自动配置](https://blog.csdn.net/u014427391/article/details/103258401)
* [SpringBoot源码学习系列之SpringMVC自动配置](https://blog.csdn.net/u014427391/article/details/103240199)
### 14.2 SpringBoot嵌入式Servlet容器
* [SpringBoot源码学习系列之嵌入式Servlet容器](https://blog.csdn.net/u014427391/article/details/103425427)

### 14.3 SpringBoot注解系列
* [SpringBoot源码学习系列之@PropertySource注解实现](https://blog.csdn.net/u014427391/article/details/103258216)

### 14.4 SpringBoot自定义Starter
* [SpringBoot系列之自定义starter实践教程](https://blog.csdn.net/u014427391/article/details/103807042)


技术博客公众号
![微信](https://img-blog.csdnimg.cn/20200707134024524.png)
计算机编程QQ群
![qq](https://img-blog.csdnimg.cn/20200707134038159.png)


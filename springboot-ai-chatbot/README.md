# Spring AI 聊天助手

这是一个使用Spring AI和OpenAI API实现的聊天助手应用程序。该应用程序使用DeepSeek-R1-Distill-Llama-8B模型，通过SiliconFlow API提供服务。

## 功能特点

- 基于Spring AI框架实现的聊天功能
- 使用OpenAI兼容的API接口
- 响应式Web界面
- 聊天历史记录管理
- 支持代码高亮显示

## 技术栈

- Spring Boot 3.2.3
- Spring AI 1.0.0-M5
- Thymeleaf
- HTML/CSS/JavaScript

## 快速开始

### 前提条件

- JDK 17或更高版本
- Maven 3.6或更高版本
- OpenAI API密钥或兼容的API密钥

### 配置

在`application.yml`文件中配置你的API密钥：

```yaml
spring:
  ai:
    openai:
      api-key: your-api-key-here
      base-url: https://api.siliconflow.cn
      chat:
        options:
          model: deepseek-ai/DeepSeek-R1-Distill-Llama-8B
```

或者，你可以通过环境变量设置API密钥：

```bash
export OPENAI_API_KEY=your-api-key-here
```

### 构建和运行

```bash
mvn clean package
java -jar target/springboot-ai-chatbot-0.0.1-SNAPSHOT.jar
```

或者使用Maven Spring Boot插件：

```bash
mvn spring-boot:run
```

应用程序将在 http://localhost:8080 上运行。

## API端点

- `POST /api/chat` - 发送聊天消息并获取AI响应
- `GET /api/chat/history` - 获取聊天历史记录
- `POST /api/chat/clear` - 清除聊天历史记录

## 使用示例

### 通过Web界面

1. 打开浏览器访问 http://localhost:8080
2. 在输入框中输入你的问题
3. 点击"发送"按钮或按Enter键
4. 查看AI助手的回复

### 通过API

```bash
# 发送聊天消息
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message":"你好，请介绍一下自己"}'

# 获取聊天历史
curl -X GET http://localhost:8080/api/chat/history

# 清除聊天历史
curl -X POST http://localhost:8080/api/chat/clear
```

## 自定义

你可以通过修改以下文件来自定义聊天助手的行为：

- `ChatService.java` - 修改系统提示信息和聊天逻辑
- `index.html` - 自定义Web界面
- `application.yml` - 调整模型参数和API设置

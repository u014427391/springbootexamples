package com.example.springboot.email;

import com.example.springboot.email.bean.EmailDto;
import com.example.springboot.email.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@SpringBootTest
class SpringbootEmailApplicationTests {

    @Autowired
    EmailService emailService;
    @Resource
    TemplateEngine templateEngine;

    @Test
    void contextLoads() {}

    @Test
    void testSendTextMail(){
        EmailDto emailDto = new EmailDto();
        emailDto.setSendTo("your_email@qq.com");
        emailDto.setSubject("发送文本邮件");
        emailDto.setContent("测试发送文本邮件!");
        emailService.sendTextMail(emailDto);
    }

    @Test
    void testSendHtmlMail() throws MessagingException {
        EmailDto emailDto = new EmailDto();
        emailDto.setSendTo("your_email@qq.com");
        emailDto.setSubject("发送html邮件");
        String html = "<html><head><title>email</title></head><body><h1>测试发送html邮件</h1></body></html>";
        emailDto.setContent(html);
        emailService.sendHtmlMail(emailDto);
    }

    @Test
    void testSendAttachmentMail() throws MessagingException {
        String[] filePaths = new String[]{"C:\\Users\\Administrator\\Desktop\\awrrpt_2_22022_22023.html","C:\\Users\\Administrator\\Desktop\\awr.html"};
        EmailDto emailDto = new EmailDto();
        emailDto.setSendTo("your_email@qq.com");
        emailDto.setSubject("发送附件邮件");
        emailDto.setFilePaths(filePaths);
        String html = "<html><head><title>email</title></head><body><h1>测试发送附件邮件</h1></body></html>";
        emailDto.setContent(html);
        emailService.sendAttachmentMail(emailDto);
    }

    @Test
    void testSendInLineImgMail() throws MessagingException {
        String inlineImgPath = "C:\\Users\\Administrator\\Desktop\\007Tv3Vmly1ggxvrzrytij31hc0u047h.jpg";
        String srcId = "img01";
        EmailDto emailDto = new EmailDto();
        emailDto.setSendTo("your_email@qq.com");
        emailDto.setSubject("发送html内嵌图片邮件");
        emailDto.setInlineImgId(srcId);
        emailDto.setInlineImgPath(inlineImgPath);
        String html = "<html><head><title>email</title></head><body><h1>发送html内嵌图片邮件</h1>"+
                "<img src=\'cid:"+ srcId +"\'></img>" +
                "</body></html>";
        emailDto.setContent(html);
        emailService.sendInLineImgMail(emailDto);
    }

    @Test
    void testSendTemplateEmail() throws MessagingException {
        Context context = new Context();
        context.setVariable("username", "admin");
        context.setVariable("id", "123456789");
        EmailDto emailDto = new EmailDto();
        emailDto.setSendTo("2284087296@qq.com");
        emailDto.setSubject("发送模板html邮件");
        emailDto.setContent(templateEngine.process("test",context));
        emailService.sendHtmlMail(emailDto);
    }
}

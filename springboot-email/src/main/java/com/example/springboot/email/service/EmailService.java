package com.example.springboot.email.service;

import com.example.springboot.email.bean.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * <pre>
 *  Email发送业务类
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/22 16:15  修改内容:
 * </pre>
 */
@Service
public class EmailService {

    @Autowired
    MailProperties mailProperties;
    @Autowired
    JavaMailSender javaMailSender;

    /**
     *  发送文本邮件
     * @Author mazq
     * @Date 2020/07/22 16:31
     * @Param [email]
     * @return
     */
    public void sendTextMail(EmailDto email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email.getSendTo());
        mailMessage.setSubject(email.getSubject());
        mailMessage.setText(email.getContent());
        mailMessage.setFrom(mailProperties.getUsername());
        javaMailSender.send(mailMessage);
    }

    /**
     *  发送Html邮件
     * @Author mazq
     * @Date 2020/07/22 16:31
     * @Param [email]
     * @return
     */
    public void sendHtmlMail(EmailDto email) throws MessagingException {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);
        messageHelper.setTo(email.getSendTo());
        messageHelper.setSubject(email.getSubject());
        messageHelper.setText(email.getContent(),true);
        messageHelper.setFrom(mailProperties.getUsername());
        javaMailSender.send(mailMessage);
    }

    /**
     *  发送附件邮件
     * @Author mazq
     * @Date 2020/07/22 16:31
     * @Param [email]
     * @return
     */
    public void sendAttachmentMail(EmailDto email) throws MessagingException {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);
        messageHelper.setTo(email.getSendTo());
        messageHelper.setSubject(email.getSubject());
        messageHelper.setText(email.getContent(),true);
        messageHelper.setFrom(mailProperties.getUsername());
        for (String filePath : email.getFilePaths()) {
            FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
            messageHelper.addAttachment(fileSystemResource.getFilename() , fileSystemResource);
        }
        javaMailSender.send(mailMessage);
    }

    /**
     *  发送html内嵌图片邮件
     * @Author mazq
     * @Date 2020/07/22 16:31
     * @Param [email]
     * @return
     */
    public void sendInLineImgMail(EmailDto email) throws MessagingException {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);
        messageHelper.setTo(email.getSendTo());
        messageHelper.setSubject(email.getSubject());
        messageHelper.setText(email.getContent(),true);
        messageHelper.setFrom(mailProperties.getUsername());
        FileSystemResource fileSystemResource = new FileSystemResource(new File(email.getInlineImgPath()));
        messageHelper.addInline(email.getInlineImgId(), fileSystemResource);
        javaMailSender.send(mailMessage);
    }

}

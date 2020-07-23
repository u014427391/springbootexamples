package com.example.springboot.email.bean;

import lombok.Data;

/**
 * <pre>
 *      EmailDto
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/22 16:26  修改内容:
 * </pre>
 */
@Data
public class EmailDto {

    /**
     * 发送对象
     */
    private String sendTo;
    /**
     * 发送主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;
    /**
     * 发起对象
     */
    private String sendFrom;
    /**
     * 附件路径数组
     */
    private String[] filePaths;
    /**
     * 内嵌图片ID
     */
    private String inlineImgId;
    /**
     * 内嵌图片路径
     */
    private String inlineImgPath;
}

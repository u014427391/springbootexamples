//package com.example.springboot.i18n.component;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.support.ResourceBundleMessageSource;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.annotation.PostConstruct;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component("messageSource")
//public class MessageResourceExtension extends ResourceBundleMessageSource {
//    private final static Logger logger = LoggerFactory.getLogger(MessageResourceExtension.class);
//
//    /**
//     * 指定的国际化文件目录
//     */
//    @Value(value = "${spring.messages.baseFolder:i18n}")
//    private String baseFolder;
//
//    /**
//     * 父MessageSource指定的国际化文件
//     */
//    @Value(value = "${spring.messages.basename:messages}")
//    private String basename;
//
//    @PostConstruct
//    public void init() {
//        logger.info("init MessageResourceExtension....");
//        if (!StringUtils.isEmpty(baseFolder)) {
//            try {
//                this.setBasenames(getAllBaseNames(baseFolder));
//            } catch (IOException e) {
//                logger.error(e.getMessage());
//            }
//        }
//        //设置父MessageSource
//        ResourceBundleMessageSource parent = new ResourceBundleMessageSource();
//        parent.setBasename(basename);
//        this.setParentMessageSource(parent);
//    }
//
//    /**
//     * 获取文件夹下所有的国际化文件名
//     *
//     * @param folderName 文件名
//     * @return
//     * @throws IOException
//     */
//    private String[] getAllBaseNames(String folderName) throws IOException {
//        Resource resource = new ClassPathResource(folderName);
//        File file = resource.getFile();
//        List<String> baseNames = new ArrayList<>();
//        if (file.exists() && file.isDirectory()) {
//            this.getAllFile(baseNames, file, "");
//        } else {
//            logger.error("指定的baseFile不存在或者不是文件夹");
//        }
//        return baseNames.toArray(new String[baseNames.size()]);
//    }
//
//    /**
//     * 遍历所有文件
//     *
//     * @param basenames
//     * @param folder
//     * @param path
//     */
//    private void getAllFile(List<String> basenames, File folder, String path) {
//        if (folder.isDirectory()) {
//            for (File file : folder.listFiles()) {
//                this.getAllFile(basenames, file, path + folder.getName() + File.separator);
//            }
//        } else {
//            String i18Name = this.getI18FileName(path + folder.getName());
//            if (!basenames.contains(i18Name)) {
//                basenames.add(i18Name);
//            }
//
//        }
//    }
//
//    /**
//     * 把普通文件名转换成国际化文件名
//     *
//     * @param filename
//     * @return
//     */
//    private String getI18FileName(String filename) {
//        filename = filename.replace(".properties", "");
//        for (int i = 0; i < 2; i++) {
//            int index = filename.lastIndexOf("_");
//            if (index != -1) {
//                filename = filename.substring(0, index);
//            }
//        }
//        return filename;
//    }
//}
package com.example.springbootjersey.manager;

import cn.hutool.core.io.FileUtil;
import com.example.springbootjersey.entity.FileUploadResult;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class FileUploadHandler {



    public FileUploadResult filepload(InputStream inputStream , FormDataContentDisposition fileDisposition) throws IOException {
        String fileName = fileDisposition.getFileName();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        File file = FileUtil.writeFromStream(inputStream, new File("D:/server/" + fileName));
        long length = file.length();
        log.info("fileName : [{}] , fileTye : [{}], size:[{}]" , fileName , fileType , length);
        return FileUploadResult.builder()
                .fileName(fileName)
                .fileUrl(file.getPath())
                .fileSize(length)
                .fileType(fileType).build();
    }


}

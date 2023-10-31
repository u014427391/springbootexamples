package com.example.springbootjersey.endpoint;


import com.example.springbootjersey.common.ResultBean;
import com.example.springbootjersey.entity.FileUploadResult;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;

public interface IFileServerClient {

    ResultBean<FileUploadResult> uploadFile(InputStream inputStream , FormDataContentDisposition fileDisposition);

}

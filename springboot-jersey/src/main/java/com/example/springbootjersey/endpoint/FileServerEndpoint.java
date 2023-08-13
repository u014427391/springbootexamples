package com.example.springbootjersey.endpoint;

import com.example.springbootjersey.common.ResultBean;
import com.example.springbootjersey.entity.FileUploadResult;
import com.example.springbootjersey.manager.FileUploadHandler;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;


@Path("/api")
@Service
@Produces({MediaType.APPLICATION_JSON  , MediaType.APPLICATION_XML})
@Slf4j
public class FileServerEndpoint implements IFileServerClient {

    @Resource
    private FileUploadHandler fileUploadHandler;

    @POST
    @Path("/v1/uploadFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Override
    public ResultBean<FileUploadResult> uploadFile(@FormDataParam("file") InputStream inputStream,
                                                   @FormDataParam("file") FormDataContentDisposition fileDisposition) {
        try {
            FileUploadResult result = fileUploadHandler.filepload(inputStream ,fileDisposition);
            return ResultBean.ok(result);
        } catch (Exception e) {
            log.error("exception:{}" , e);
            return ResultBean.badRequest("error" , null);
        }
    }
}

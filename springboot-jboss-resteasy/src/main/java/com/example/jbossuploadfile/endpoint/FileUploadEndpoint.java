package com.example.jbossuploadfile.endpoint;


import cn.hutool.core.io.FileUtil;
import com.example.jbossuploadfile.entity.FileUploadResult;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@Path("/api")
@Component
public class FileUploadEndpoint {

    @Path("/v1/fileUpload")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public FileUploadResult filepload(MultipartFormDataInput formDataInput) throws IOException {
        String fileName = getFileName(formDataInput , "file");
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        InputStream inputStream = getInputStream(formDataInput , "file");
        File file = FileUtil.writeFromStream(inputStream, new File("D:/server/" + fileName));
        long length = file.length();
        log.info("fileName : [{}] , fileTye : [{}], size:[{}]" , fileName , fileType , length);
        return FileUploadResult.builder()
                .fileName(fileName)
                .fileUrl(file.getPath())
                .fileSize(length)
                .fileType(fileType).build();
    }

    private InputStream getInputStream(MultipartFormDataInput input, String s) {
        try {
            InputStream result;
            if (input.getParts().size() == 1) {
                InputPart filePart = input.getParts().iterator().next();
                result = filePart.getBody(InputStream.class, null);
            } else {
                result = input.getFormDataPart(s, ByteArrayInputStream.class, null);
            }
            if (result == null) {
                throw new IllegalArgumentException("Can't find a valid 'file' part in the multipart request");
            }
            return result;
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while reading multipart request", e);
        }
    }

    private String getFileName(MultipartFormDataInput input , String s) {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get(s);
        for (InputPart inputPart : inputParts) {
            MultivaluedMap<String, String> header = inputPart.getHeaders();
            String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
            for (String filename : contentDisposition) {
                if ((filename.trim().startsWith("filename"))) {
                    String[] name = filename.split("=");
                    String finalFileName = name[1].trim().replaceAll("\"", "");
                    return finalFileName;
                }
            }
        }
        return "unknown";
    }

}

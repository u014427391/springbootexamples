package com.example.resteasy.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResult implements Serializable {

    private String fileUrl;

    private Long fileSize;

    private String fileName;

    private String fileType;


}

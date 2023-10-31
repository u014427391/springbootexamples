package com.example.springboot.mybatis.common.enumhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = JacksonEnumDeserializer.class)
public interface IEnum {

    String getCode();
    String getName();
}

package com.example.springboot.mybatis.common.enumhandler;


import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;

public class JacksonEnumDeserializer extends JsonDeserializer<IEnum> implements ContextualDeserializer {

    private Class targetClass;

    @Override
    public IEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode jsonNode = codec.readTree(jsonParser);
        String code = jsonNode.asText();
        if (StrUtil.isNotBlank(code) && targetClass != null) {
            return (IEnum)EnumUtils.codeOf(targetClass , code);
        }
        return null;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        JavaType contextualType = deserializationContext.getContextualType();
        targetClass = contextualType.getRawClass();
        return this;
    }

}

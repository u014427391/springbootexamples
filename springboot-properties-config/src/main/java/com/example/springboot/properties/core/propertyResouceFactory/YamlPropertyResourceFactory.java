package com.example.springboot.properties.core.propertyResouceFactory;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;

/**
 * <pre>
 *  YAML配置文件读取工厂类
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019/11/13 15:44  修改内容:
 * </pre>
 */
public class YamlPropertyResourceFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(@Nullable String s, EncodedResource encodedResource) throws IOException {
        if (encodedResource == null) {
           return super.createPropertySource(s, encodedResource);
        }
        List<PropertySource<?>> sources = new YamlPropertySourceLoader().load(encodedResource.getResource().getFilename(),encodedResource.getResource());
        return sources.get(0);
    }
}

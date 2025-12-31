package com.example.pulsar.config;

import com.example.pulsar.model.Message;
import org.apache.pulsar.client.api.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.core.DefaultSchemaResolver;
import org.springframework.pulsar.core.SchemaResolver;

@Configuration
public class PulsarConfiguration {

    /**
     * 注册 Schema
     *
     * @Date 2025/12/30 17:52
     */
    @Bean
    public SchemaResolver.SchemaResolverCustomizer<DefaultSchemaResolver> schemaResolverCustomizer() {
        return (schemaResolver) -> {
            schemaResolver.addCustomSchemaMapping(Message.class, Schema.JSON(Message.class));
        };
    }

}

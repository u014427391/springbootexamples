package com.example.mongodb.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.mongodb.repository")
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    @Override
    public MongoClient mongoClient() {
        ConnectionString str = new ConnectionString("mongodb://127.0.0.1:27017/test");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(str)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoClientDbFactory(mongoClient(), "test");
    }

    @Override
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.example.mongodb");
    }




}

package com.example.mongodb.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.mongodb.repository")
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    private static final String DB_NAME = "test";
    private static final String IP = "127.0.0.1";
    private static final String PORT = "27017";

    @Override
    public MongoClient mongoClient() {
        ConnectionString str = new ConnectionString("mongodb://"+ IP + ":"+ PORT + "/" + getDatabaseName());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(str)
                .build();
        return MongoClients.create(mongoClientSettings);
    }


    @Override
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoClient() , getDatabaseName());
    }

    @Override
    protected String getDatabaseName() {
        return DB_NAME;
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.example.mongodb");
    }




}

package com.example.mongodb;


import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@SpringBootTest
public class MongodbCollectionTests {

    private static final String DATABASE = "test";
    private static final String COLLECTION = "user";
    private static final String USER_JSON = "/userjson.txt";
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection<Document> collection;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeClass
    public static void init() throws IOException {
        mongoClient = new MongoClient("127.0.0.1", 27017);
        mongoDatabase = mongoClient.getDatabase(DATABASE);
        collection = mongoDatabase.getCollection(COLLECTION);

        collection.drop();

        InputStream inputStream = MongodbAggregationTests.class.getResourceAsStream(USER_JSON);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        reader.lines()
                .forEach(l->collection.insertOne(Document.parse(l)));
        reader.close();
    }


    @org.junit.jupiter.api.Test
    public void fromMongoTemplate() {
        collection = mongoTemplate.getCollection(COLLECTION);
        FindIterable<Document> iterable = collection.find();
        for (Document next : iterable) {
            log.info("user : {}" , next);
        }
    }

    @Test
    public void insertTest() {
        Document document = new Document("_id",10).
                append("name" , "User10").
                append("age",18).
                append("email","test10@qq.com");
        collection.insertOne(document);
    }

    @Test
    public void insertManyTest() {
        collection.drop();
        List<Document> users = Stream.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L ,9L).map(i -> {
            Document user = new Document();
            user.append("_id",i);
            user.append("name" ,"User"+i);
            user.append("age" , 18);
            user.append("email", "test" + i + "@qq.com");
            return user;
        }).collect(Collectors.toList());
        collection.insertMany(users);
    }

    @Test
    public void delTest() {
        Bson filter = Filters.eq("name" , "User1");
        collection.deleteOne(filter);
    }

    @Test
    public void delManyTest() {
        Bson filter = Filters.eq("name" , "User1");
        collection.deleteMany(filter);
    }

    @Test
    public void updateTest() {
        Bson filter = Filters.eq("name" , "User1");
        Document document = new Document("$set" , new Document("age" , 100));
        collection.updateOne(filter , document);
    }

    @Test
    public void updateManyTest() {
        Bson filter = Filters.eq("name" , "User1");
        Document document = new Document("$set" , new Document("age" , 100));
        collection.updateMany(filter , document);
    }

    @Test
    public void findAllTest() {
        FindIterable iterable = collection.find();
        MongoCursor cursor = iterable.cursor();
        while(cursor.hasNext()) {
            log.info("user:",cursor.next());
        }
    }

    @Test
    public void findConditionTest() {
        Bson filter = Filters.eq("name" , "User1");
        FindIterable iterable = collection.find(filter);
        MongoCursor cursor = iterable.cursor();
        while(cursor.hasNext()) {
            log.info("user:",cursor.next());
        }
    }

    @Test
    public void findFirstTest() {
        Document document = collection.find().first();
        log.info("user first :{}" , document);
    }

}

package com.example.mongodb;




import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.count;
import static org.junit.Assert.assertEquals;


@Slf4j
public class MongodbAggregationTests {

    private static final String DATABASE = "test";
    private static final String COLLECTION = "user";
    private static final String USER_JSON = "/userjson.txt";
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection<Document> collection;

    @BeforeClass
    public static void init() throws IOException {
        mongoClient = new MongoClient("192.168.0.61", 27017);
        mongoDatabase = mongoClient.getDatabase(DATABASE);
        collection = mongoDatabase.getCollection(COLLECTION);

        collection.drop();

        InputStream inputStream = MongodbAggregationTests.class.getResourceAsStream(USER_JSON);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        reader.lines()
                .forEach(l->collection.insertOne(Document.parse(l)));
        reader.close();

    }

    @Test
    public void matchCount() {
        Document count = collection.aggregate(
                Arrays.asList(match(Filters.eq("name", "User1")), count()))
                .first();
        assertEquals(9 , count.get("count"));
    }



}

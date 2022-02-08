package com.example.mongodb;




import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.print.Doc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
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
        mongoClient = new MongoClient("127.0.0.1", 27017);
        mongoDatabase = mongoClient.getDatabase(DATABASE);
        collection = mongoDatabase.getCollection(COLLECTION);

        //collection.drop();

//        InputStream inputStream = MongodbAggregationTests.class.getResourceAsStream(USER_JSON);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        reader.lines()
//                .forEach(l->collection.insertOne(Document.parse(l)));
//        reader.close();

    }

    @Test
    public void matchCountTest() {
        Document first = collection.aggregate(
                Arrays.asList(match(Filters.eq("name", "User1")), count()))
                .first();
        log.info("count:{}" , first.get("count"));
        assertEquals(1 , first.get("count"));
    }


    @Test
    public void skipTest() {
        AggregateIterable<Document> iterable = collection.aggregate(Arrays.asList(skip(5)));
        for (Document next : iterable) {
            log.info("user:{}" ,next);
        }

    }

    @Test
    public void groupTest() {
        AggregateIterable<Document> iterable = collection.aggregate(Arrays.asList(
                group("$name" , Accumulators.sum("nameCount" , 1)),
                sort(Sorts.ascending("_id"))
                ));
        for (Document next : iterable) {
            log.info("user:{}" ,next);
        }

    }

    @Test
    public void sortLimitOutTest() {
        collection.aggregate(Arrays.asList(
                sort(Sorts.descending("_id")) ,
                limit(3) ,
                out("new_user"))).toCollection();

        MongoCollection collection = mongoDatabase.getCollection("new_user");
        assertEquals(3 , collection.countDocuments());

        FindIterable<Document> iterable = collection.find();
        for (Document next : iterable) {
            log.info("user : {}" , next);
        }


    }



}

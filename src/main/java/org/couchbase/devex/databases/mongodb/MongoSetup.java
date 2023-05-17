package org.couchbase.devex.databases.mongodb;

import org.couchbase.devex.domain.StoredFileDocument;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;

import redis.clients.jedis.UnifiedJedis;

@Component
public class MongoSetup implements CommandLineRunner {

    private MongoDatabase mongoDatabase;
    private UnifiedJedis client;

    public MongoSetup(MongoDatabase mongoDatabase, UnifiedJedis client) {
        this.mongoDatabase = mongoDatabase;
        this.client = client;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            client.functionFlush();
            mongoDatabase.getCollection(StoredFileDocument.COLLECTION_NAME).drop();
            mongoDatabase.createCollection(StoredFileDocument.COLLECTION_NAME);
            mongoDatabase.getCollection(StoredFileDocument.COLLECTION_NAME)
                    .createIndex(Indexes.text("fulltext"))
                    ;
        } catch (MongoCommandException e) {
            // 
        }
    }

}

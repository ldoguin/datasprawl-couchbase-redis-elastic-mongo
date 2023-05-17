package org.couchbase.devex.databases.mongodb;

import org.couchbase.devex.domain.StoredFileDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Configuration
@ConditionalOnProperty(name = "mongo.enabled", havingValue = "true")
// @ComponentScan(basePackages = { "org.couchbase.devex.databases.mongodb" })
public class MongoDBConfig {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<StoredFileDocument> collection;
    
    @Autowired
    MongoClusterProperties mongoClusterProperties;

    @Bean
    public MongoClient getMongoClient() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(mongoClusterProperties.connectionString());
            
        }
        return mongoClient;
    }

    @Bean
    public MongoDatabase getMongoDatabase(MongoClient mongoClient) {
        if (mongoDatabase == null) {
            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register("org.couchbase.devex.domain").build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
            mongoDatabase = mongoClient.getDatabase(mongoClusterProperties.defaultDatabase()).withCodecRegistry(pojoCodecRegistry);
        }
        return mongoDatabase;
    }
    @Bean
    public MongoCollection<StoredFileDocument> getCollection(MongoDatabase mongoDatabase) {
        if (collection == null) {
            // db.createCollection(StoredFileDocument.COLLECTION_NAME);
            collection = mongoDatabase.getCollection(StoredFileDocument.COLLECTION_NAME, StoredFileDocument.class);
        }
        return collection;
    }

    // import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
    // import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

    // @Override
    // protected void configureClientSettings(MongoClientSettings.Builder builder) {

    // CodecRegistry pojoCodecRegistry =
    // fromProviders(PojoCodecProvider.builder().automatic(true).build());
    // CodecRegistry codecRegistry =
    // fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
    // pojoCodecRegistry);
    // builder.codecRegistry(codecRegistry);
    // }
}
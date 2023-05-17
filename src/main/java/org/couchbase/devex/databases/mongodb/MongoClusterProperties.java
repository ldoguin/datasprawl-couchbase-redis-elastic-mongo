package org.couchbase.devex.databases.mongodb;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mongo")
public record MongoClusterProperties(String connectionString, String username, String password, String defaultDatabase,String defaultCollection, String encryptionKey) {
}

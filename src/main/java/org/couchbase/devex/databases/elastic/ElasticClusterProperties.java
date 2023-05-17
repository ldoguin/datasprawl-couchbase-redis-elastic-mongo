package org.couchbase.devex.databases.elastic;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "elastic")
public record ElasticClusterProperties(String host, int port, String username, String password, String defaultDatabase,String defaultCollection, String encryptionKey) {
}
package org.couchbase.devex.databases.redis;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redis")
public record RedisClusterProperties(String hostname, int port, String username, String password, String defaultDatabase,String defaultCollection, String encryptionKey) {
}


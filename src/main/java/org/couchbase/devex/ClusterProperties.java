package org.couchbase.devex;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "couchbase")
public record ClusterProperties(String connectionString, String username, String password, String defaultBucket,String defaultCollection, String encryptionKey) {
}

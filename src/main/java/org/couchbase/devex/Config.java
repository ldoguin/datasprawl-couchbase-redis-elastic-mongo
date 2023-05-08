package org.couchbase.devex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;

@Configuration
@EnableCouchbaseRepositories(basePackages = { "org.couchbase.devex" })
public class Config extends AbstractCouchbaseConfiguration {

    @Autowired
    ClusterProperties clusterProperties;
    
    @Override
    public String getConnectionString() {
        return clusterProperties.connectionString();
    }

    @Override
    public String getUserName() {
        return clusterProperties.username();
    }

    @Override
    public String getPassword() {
        return clusterProperties.password();
    }

    @Override
    public String getBucketName() {
        return clusterProperties.defaultBucket();
    }

    @Bean
    public Collection getCollection(CouchbaseClientFactory clientFactory) {
        return clientFactory.getDefaultCollection();
}
}
package org.couchbase.devex.databases.couchbase;

import org.couchbase.devex.databases.api.CRUD;
import org.couchbase.devex.databases.api.Query;
import org.couchbase.devex.databases.api.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import com.couchbase.client.java.Collection;

@ComponentScan(basePackages = { "org.couchbase.devex.databases.couchbase" })
@Configuration
@ConditionalOnProperty(name = "couchbase.enabled", havingValue = "true")
public class CouchbaseConfig {

    private Cluster couchbaseCluster;

    private Collection couchbaseCollection;

    @Autowired
    ClusterProperties clusterProperties;

    @Bean
    public Cluster couchbaseCluster(){
        if (couchbaseCluster == null) {
            couchbaseCluster = Cluster.connect(
                clusterProperties.connectionString(),
                ClusterOptions.clusterOptions(clusterProperties.username(), clusterProperties.password()).environment(env -> {
                    // Sets a pre-configured profile called "wan-development" to help avoid
                    // latency issues when accessing Capella from a different Wide Area Network
                    // or Availability Zone (e.g. your laptop).
                    env.applyProfile("wan-development");
                    
                })
            );
        }
        return couchbaseCluster;
    }

    @Bean
    public Collection couchbaseCollection() {
        if (couchbaseCollection == null) {
            couchbaseCluster().bucket(clusterProperties.defaultBucket())
                .collection(clusterProperties.defaultCollection());
        }
        return couchbaseCollection;
    }

    @Bean
    public Query getQuery(Cluster cluster) {
        return new CouchbaseQuery(cluster);
    }

    @Bean
    public CRUD getCrud(Collection collection) {
        return new CouchbaseCRUD(collection);
    }

    @Bean
    public Search getSearch(Cluster cluster) {
        return new CouchbaseSearch(cluster);
    }
}
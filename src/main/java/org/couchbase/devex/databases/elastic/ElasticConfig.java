package org.couchbase.devex.databases.elastic;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@Configuration
@ComponentScan(basePackages = { "org.couchbase.devex.databases.elastic" })
@ConditionalOnProperty(name = "elastic.enabled", havingValue = "true")
public class ElasticConfig {

    private ElasticsearchClient client;

    @Autowired
    ElasticClusterProperties elasticClusterProperties;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        if (client == null) {
            // Create the low-level client
            RestClient restClient = RestClient.builder(
                    new HttpHost(elasticClusterProperties.host(), elasticClusterProperties.port())).build();

            // Create the transport with a Jackson mapper
            ElasticsearchTransport transport = new RestClientTransport(
                    restClient, new JacksonJsonpMapper());

            // And create the API client
            client = new ElasticsearchClient(transport);
        }
        return client;
    }

}
package org.couchbase.devex.databases.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.providers.PooledConnectionProvider;

@Configuration
@ConditionalOnProperty(name = "redis.enabled", havingValue = "true")
@ComponentScan(basePackages = { "org.couchbase.devex.databases.redis" })
public class RedisConfig {

    private UnifiedJedis client;

    @Autowired
    RedisClusterProperties redisClusterProperties;

    @Bean
    public UnifiedJedis jedisClient() {
        if (client == null) {
            HostAndPort config = new HostAndPort(redisClusterProperties.hostname(), redisClusterProperties.port());
            PooledConnectionProvider provider = new PooledConnectionProvider(config);
            client = new UnifiedJedis(provider);
        }
        return client;
    }

}

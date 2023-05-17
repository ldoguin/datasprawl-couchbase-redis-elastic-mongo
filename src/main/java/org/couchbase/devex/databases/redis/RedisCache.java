package org.couchbase.devex.databases.redis;

import org.couchbase.devex.databases.api.Cache;
import org.couchbase.devex.domain.StoredFileDocument;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import redis.clients.jedis.UnifiedJedis;

@Service
public class RedisCache implements Cache {

    private UnifiedJedis client;
    
    public RedisCache(UnifiedJedis client) {
        this.client = client;
    }

    @Override
    public void writeInCache(StoredFileDocument doc) {
        client.set(doc.getFileId().getBytes(), SerializationUtils.serialize(doc));
        client.expire(doc.getFileId().getBytes(), 10000);
    }

    @Override
    public StoredFileDocument readFromCache(String id) {
        StoredFileDocument doc = (StoredFileDocument) SerializationUtils.deserialize( client.get(id.getBytes()));
        return doc;
    }

    @Override
    public void touch(String id) {
        client.touch(id.getBytes());
    }

    @Override
    public void evict(String id) {
        client.del(id.getBytes());
    }
    
}

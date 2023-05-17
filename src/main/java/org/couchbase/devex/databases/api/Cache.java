package org.couchbase.devex.databases.api;

import org.couchbase.devex.domain.StoredFileDocument;

import org.springframework.stereotype.Component;

@Component
public interface Cache {
    
    void writeInCache(StoredFileDocument doc);

    StoredFileDocument readFromCache(String id);

    void touch(String id);

    void evict(String id);
}

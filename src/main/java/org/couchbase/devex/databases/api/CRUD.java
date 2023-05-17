package org.couchbase.devex.databases.api;

import org.couchbase.devex.domain.StoredFileDocument;

import org.springframework.stereotype.Component;

@Component
public interface CRUD {  
    StoredFileDocument read(String id);
    void create(String id, StoredFileDocument doc);
    void update(String id, StoredFileDocument doc);
    void upsert(String id, StoredFileDocument doc);
    void delete(String id);
}

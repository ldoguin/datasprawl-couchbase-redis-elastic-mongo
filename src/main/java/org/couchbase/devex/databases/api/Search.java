package org.couchbase.devex.databases.api;

import java.util.List;
import java.util.Map;

import org.couchbase.devex.domain.StoredFileDocument;
import org.springframework.stereotype.Component;

@Component
public interface Search {
    
    List<Map<String, Object>> search(String term);

    void index(StoredFileDocument doc);

    void delete(String id);
}

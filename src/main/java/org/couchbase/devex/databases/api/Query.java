package org.couchbase.devex.databases.api;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public interface Query {
    
    List<Map<String, Object>> query(String whereClause);    
    
    List<Map<String, Object>> findAll();

}

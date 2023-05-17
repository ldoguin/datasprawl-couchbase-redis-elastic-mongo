package org.couchbase.devex.databases.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.couchbase.devex.databases.api.Query;
import org.couchbase.devex.domain.StoredFileDocument;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;

@Service
public class MongoQuery implements Query {

    private MongoCollection<StoredFileDocument> collection;
    private ObjectMapper om = new ObjectMapper();

    public MongoQuery(MongoCollection<StoredFileDocument> collection) {
        this.collection = collection;
    }

    @Override
    public List<Map<String, Object>> query(String whereClause) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'query'");
    }

    @Override
    public List<Map<String, Object>> findAll() {
        return collection.find().map(d -> {
            Map <String, Object> m =om.convertValue(d, Map. class);
            return m;
        } ).into(new ArrayList<Map<String, Object>>());
    }
    
}

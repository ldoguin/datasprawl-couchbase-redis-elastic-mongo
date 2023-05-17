package org.couchbase.devex.databases.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.conversions.Bson;
import org.couchbase.devex.databases.api.Search;
import org.couchbase.devex.domain.StoredFileDocument;
import org.springframework.stereotype.Service;

import com.couchbase.client.core.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

@Service
public class MongoSearch implements Search {

    private MongoCollection<StoredFileDocument> collection;
    private ObjectMapper om = new ObjectMapper();

    public MongoSearch(MongoCollection<StoredFileDocument> collection) {
        this.collection = collection;
    }

    @Override
    public List<Map<String, Object>> search(String term) {
        Bson filter = Filters.text(term);
        return collection.find(filter).map(doc -> 
        {
            Map <String, Object> m = om.convertValue(doc, Map. class);
            return m;
        }
        ).into(new ArrayList<Map<String, Object>>());
    }

    @Override
    public void index(StoredFileDocument doc) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'index'");
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    
}

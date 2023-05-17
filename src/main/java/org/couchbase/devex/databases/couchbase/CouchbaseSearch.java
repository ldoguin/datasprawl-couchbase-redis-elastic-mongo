package org.couchbase.devex.databases.couchbase;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.couchbase.devex.databases.api.Search;
import org.couchbase.devex.domain.StoredFileDocument;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.QueryScanConsistency;

@Service
@Profile("couchbase")
public class CouchbaseSearch implements Search {

    private Cluster cluster;

    public CouchbaseSearch(Cluster cluster){
        this.cluster = cluster;
    }

    @Override
    public List<Map<String, Object>> search(String term) {
            // SearchQuery ftq = new SearchQuery("file_fulltext", SearchQuery
            // 		.term(term));
            // 		ftq.fields("binaryStoreDigest", "binaryStoreLocation");
            // SearchQueryResult result = bucket.query(ftq);
            // List<Map<String, Object>> filenames = result.hits().strea<m().map(row -> {
            // 	Map<String, Object> m = new HashMap<String, Object>();
            // 	m.put("binaryStoreDigest", row.fields().get("binaryStoreDigest"));
            // 	m.put("binaryStoreLocation", row.fields().get("binaryStoreLocation"));
            // 	m.put("fragment", row.fragments().get("fulltext"));
            // 	return m;
            // }).collect(Collectors.toList());
    
            JsonArray parameter = JsonArray.from(term);
            QueryResult result = cluster.query("SELECT binaryStoreLocation, binaryStoreDigest FROM `default` USE INDEX (USING FTS) WHERE type= 'file' AND file_fulltext LIKE $1",
             QueryOptions.queryOptions().scanConsistency(QueryScanConsistency.REQUEST_PLUS).parameters(parameter));
            
            List<Map<String, Object>> filenames = result.rowsAsObject().stream().map(row -> row.toMap())
                    .collect(Collectors.toList());
    
            return filenames;
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

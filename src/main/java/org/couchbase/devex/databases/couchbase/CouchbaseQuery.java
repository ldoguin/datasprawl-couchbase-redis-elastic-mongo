package org.couchbase.devex.databases.couchbase;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.couchbase.devex.databases.api.Query;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.QueryScanConsistency;

@Service
@Profile("couchbase")
public class CouchbaseQuery implements Query {

    private Cluster cluster;

    public CouchbaseQuery(Cluster cluster){
        this.cluster = cluster;
    }

    @Override
	public List<Map<String, Object>> findAll() {
		
		QueryResult result = cluster.query("SELECT binaryStoreLocation, binaryStoreDigest FROM `default` WHERE type = 'file'",
			QueryOptions.queryOptions().scanConsistency(QueryScanConsistency.REQUEST_PLUS));
		
		List<Map<String, Object>> filenames = result.rowsAsObject().stream().map(row -> row.toMap())
				.collect(Collectors.toList());
		return filenames;
	}
    
    @Override
    public List<Map<String, Object>> query(String whereClause) {
		System.out.println(whereClause);
		JsonArray parameter = JsonArray.from(whereClause);
		QueryResult result = cluster.query("SELECT binaryStoreLocation, binaryStoreDigest FROM `default` WHERE type = 'file' $1",
			QueryOptions.queryOptions().scanConsistency(QueryScanConsistency.REQUEST_PLUS).parameters(parameter));
		
		List<Map<String, Object>> filenames = result.rowsAsObject().stream().map(row -> row.toMap())
				.collect(Collectors.toList());
		return filenames;
    }
    
}

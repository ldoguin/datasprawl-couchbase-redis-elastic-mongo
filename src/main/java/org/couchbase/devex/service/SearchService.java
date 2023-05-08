package org.couchbase.devex.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.QueryScanConsistency;

@Service
public class SearchService {

	private Cluster cluster;

	public SearchService(Cluster cluster) {
		this.cluster = cluster;
	}

	public List<Map<String, Object>> getFiles() {
		QueryResult result = cluster.query("SELECT binaryStoreLocation, binaryStoreDigest FROM `default` WHERE type = 'file'",
			QueryOptions.queryOptions().scanConsistency(QueryScanConsistency.REQUEST_PLUS));
		
		List<Map<String, Object>> filenames = result.rowsAsObject().stream().map(row -> row.toMap())
				.collect(Collectors.toList());
		return filenames;
	}

	public List<Map<String, Object>> searchN1QLFiles(String whereClause) {
		System.out.println(whereClause);
		JsonArray parameter = JsonArray.from(whereClause);
		QueryResult result = cluster.query("SELECT binaryStoreLocation, binaryStoreDigest FROM `default` WHERE type = 'file' $1",
			QueryOptions.queryOptions().scanConsistency(QueryScanConsistency.REQUEST_PLUS).parameters(parameter));
		
		List<Map<String, Object>> filenames = result.rowsAsObject().stream().map(row -> row.toMap())
				.collect(Collectors.toList());
		return filenames;
	}

	public List<Map<String, Object>> searchFulltextFiles(String term) {
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

}

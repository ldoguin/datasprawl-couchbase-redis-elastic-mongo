package org.couchbase.devex.service;

import java.util.List;
import java.util.Map;

import org.couchbase.devex.databases.api.Query;
import org.couchbase.devex.databases.api.Search;
import org.springframework.stereotype.Service;

@Service
public class FileService {

	private Query query;

	private Search search;

	public FileService(Query query, Search search) {
		this.query = query;
		this.search = search;
	}

	public List<Map<String, Object>> getFiles() {
		return query.findAll();
	}

	public List<Map<String, Object>> queryFiles(String whereClause) {
		return query.query(whereClause);
	}

	public List<Map<String, Object>> searchFiles(String term) {
		return search.search(term);
	}
}

package org.couchbase.devex.databases.elastic;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.couchbase.devex.databases.api.Search;
import org.couchbase.devex.domain.StoredFileDocument;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;

@Service
@Primary
public class ElasticSearch implements Search {

    private ElasticsearchClient elasticsearchClient;

    public ElasticSearch(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public List<Map<String, Object>> search(String term) {
        try {
            SearchResponse<Map> search = elasticsearchClient.search(s -> s
                    .index(StoredFileDocument.COLLECTION_NAME)
                    .query(q -> q
                            .term(t -> t
                                    .field("fulltext")
                                    .value(v -> v.stringValue(term)))),
                    Map.class);
            return search.hits().hits().stream().map(s -> (Map<String, Object>) s.source()).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void index(StoredFileDocument doc) {
        try {
            elasticsearchClient.index(i -> i
                    .index(StoredFileDocument.COLLECTION_NAME)
                    .id(doc.getFileId())
                    .document(doc));
        } catch (ElasticsearchException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            elasticsearchClient.delete(d -> d
                    .index(StoredFileDocument.COLLECTION_NAME)
                    .id(id));
        } catch (ElasticsearchException | IOException e) {
            e.printStackTrace();
        }
    }

}

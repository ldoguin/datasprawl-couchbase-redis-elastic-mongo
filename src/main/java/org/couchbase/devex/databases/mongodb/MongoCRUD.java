package org.couchbase.devex.databases.mongodb;

import static com.mongodb.client.model.Filters.eq;

import org.couchbase.devex.databases.api.CRUD;
import org.couchbase.devex.databases.api.Cache;
import org.couchbase.devex.databases.api.Search;
import org.couchbase.devex.domain.StoredFileDocument;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;

@Service
public class MongoCRUD implements CRUD {
    
    private MongoCollection<StoredFileDocument> collection;

    private Cache cache;

    private Search search;

	public MongoCRUD(MongoCollection<StoredFileDocument> collection, Cache cache, Search search) {
		this.collection = collection;
        this.cache = cache;
        this.search = search;
	}
    
    @Override
    public StoredFileDocument read(String id) {
        StoredFileDocument doc = cache.readFromCache(id);
        if (doc == null) {
            System.out.println(id);
            doc = collection.find(eq("fileId", id)).first();
            cache.writeInCache(doc);
        } else {
            cache.touch(id);
        }
        return doc;
    }

    @Override
    public void create(String id, StoredFileDocument doc) {
        doc.setFileId(id);
        collection.insertOne(doc);
        cache.writeInCache(doc);
        search.index(doc);
    }

    @Override
    public void update(String id, StoredFileDocument doc) {
        collection.findOneAndReplace(eq("fileId", id), doc);
        cache.touch(id);
        search.index(doc);
    }

    @Override
    public void upsert(String id, StoredFileDocument doc) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().upsert(true);
        collection.findOneAndReplace(eq("fileId", id), doc, options);
        cache.touch(id);
        search.index(doc);
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(eq("fileId", id));
        cache.evict(id);
        search.delete(id);
    }

}

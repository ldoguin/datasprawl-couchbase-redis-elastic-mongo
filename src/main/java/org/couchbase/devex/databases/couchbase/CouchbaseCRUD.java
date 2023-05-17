package org.couchbase.devex.databases.couchbase;

import org.couchbase.devex.databases.api.CRUD;
import org.couchbase.devex.domain.StoredFileDocument;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;

@Service
@Profile("couchbase")
public class CouchbaseCRUD implements CRUD {

    private Collection collection;

	public CouchbaseCRUD(Collection collection) {
		this.collection = collection;
	}

    @Override
    public StoredFileDocument read(String id) {
        GetResult res = collection.get(id);
        return res.contentAs(StoredFileDocument.class);
    }

    @Override
    public void create(String id, StoredFileDocument doc) {
        MutationResult res = collection.insert(id, doc);
    }

    @Override
    public void update(String id, StoredFileDocument doc) {
        MutationResult res = collection.replace(id, doc);
        
    }

    @Override
    public void upsert(String id, StoredFileDocument doc) {
        MutationResult res = collection.upsert(id, doc);
    }

    @Override
    public void delete(String id) {
        MutationResult res = collection.remove(id);
    }
    
}

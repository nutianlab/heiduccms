package org.heiduc.api.datastore;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.heiduc.api.util.Constants;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

public class DatastoreServiceImpl implements DatastoreService {

	private MongoDatabase database;
	private static final IdWorker idWorker = new IdWorker(1);
	private static final MongoClient mongo = new MongoClient(new MongoClientURI(Constants.DATABASE_URI));
	
	public DatastoreServiceImpl(){
		database = mongo.getDatabase(Constants.DATABASE_NAME);
	}

	@Override
	public PreparedQuery prepare(Query query) {
		return new PreparedQueryImpl(this.database, query);
	}

	@Override
	public Entity get(Key key) throws EntityNotFoundException {
		Entity entity = new Entity(key);
		MongoCollection<Document> collection = database.getCollection(key.getKind());
		FindIterable<Document> documents = collection.find(new BasicDBObject("_id",key.getId()));
		for (Document document : documents) {
			entity.getPropertyMap().putAll(document);
		}
		return entity;
	}

	@Override
	public Map<Key, Entity> get(Iterable<Key> keys) {
		Map<Key, Entity> map = new HashMap<Key, Entity>();
		for (Key key : keys) {
			try {
				map.put(key,get(key));
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	@Override
	public void delete(Key key) {
		MongoCollection<Document> collection = database.getCollection(key.getKind());
		collection.deleteOne(new BasicDBObject("_id",key.getId()));
	}

	@Override
	public void delete(Iterable<Key> keys) {
		for (Key key : keys) {
			delete(key);
		}
	}

	@Override
	public Key put(Entity entity) {
		MongoCollection<Document> collection = database.getCollection(entity.getKey().getKind());
		Document doc = new Document(entity.getPropertyMap());
		if(entity.getKey().getId() == 0L){//插入
			long _id = idWorker.nextId();
			doc.put("_id", _id);
			entity.getKey().setId(_id);
		}
		Bson filter = new BasicDBObject("_id",doc.get("_id")) ;
		collection.replaceOne(filter, doc,new UpdateOptions().upsert(true));
		return entity.getKey();
	}

}
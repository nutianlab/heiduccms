package org.heiduc.api.datastore;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.heiduc.api.util.Constants;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class DatastoreServiceImpl implements DatastoreService {

	private DB database;
//	Datastore datastore;
	private IdWorker idWorker = new IdWorker(1);
	
	public DatastoreServiceImpl(){
		MongoClient mongo = null;
		try {
			mongo = new MongoClient(new MongoClientURI(Constants.DATABASE_URI));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		database = mongo.getDB(Constants.DATABASE_NAME);
	}

	@Override
	public PreparedQuery prepare(Query query) {
		return new PreparedQueryImpl(this.database, query);
	}

	@Override
	public Entity get(Key key) throws EntityNotFoundException {
		/*Entity result = (Entity)get(Collections.singleton(key)).get(key);
		if(result == null)
            throw new EntityNotFoundException(key);
        else
            return result;*/
		
		Entity entity = new Entity(key);
		DBCollection collection = database.getCollection(key.getKind());
		
		DBCursor cursor = collection.find(new BasicDBObject("_id",key.getId()));
		
		if(cursor.hasNext()){
			DBObject dbo = cursor.next();
			entity.getPropertyMap().putAll(dbo.toMap());
			/*Iterator it = dbo.toMap().entrySet().iterator();   
		    while (it.hasNext()) {   
		        Map.Entry entry = (Map.Entry) it.next();   
		        Object _key = entry.getKey();   
		        Object _value = entry.getValue();
		        if (_value instanceof byte[]) {
		        	_value = new Blob((byte[])_value);
				}
		        if(_value != null){
		        	System.out.println("key = "+_key+"\t value="+_value+"\t type="+_value.getClass().getSimpleName());
		        }
		        entity.getPropertyMap().put(_key, _value);
//		        System.out.println("key = "+_key+"\t value="+_value);
		    }*/
			/*try {
				PropertyUtils.copyProperties(entity.getPropertyMap(), dbo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}*/
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
//		datastore.delete(key);
		DBCollection collection = database.getCollection(key.getKind());
//		collection.deleteOne(new BasicDBObject("_id",key.getId()));
		collection.remove(new BasicDBObject("_id",key.getId()));
	}

	@Override
	public void delete(Iterable<Key> keys) {
//		datastore.delete(clazz, ids)
		for (Key key : keys) {
			delete(key);
		}
	}

	@Override
	public Key put(Entity entity) {
		DBCollection collection = database.getCollection(entity.getKey().getKind());
//		collection.insert((DBObject)JSON.parse(JSON.serialize()));
		DBObject doc = new BasicDBObject(entity.getPropertyMap());
		if(entity.getKey().getId() == 0L){//插入
			long _id = idWorker.nextId();
			doc.put("_id", _id);
			entity.getKey().setId(_id);
		}
		collection.save(doc);
		
		return entity.getKey();
	}
	
	/*public List<Key> put(Iterable<Entity> entities)
    {
//		return IteratorUtils.toList(datastore.save(entities).iterator());
//		com.mongodb.util.JSON.p
//		DBObject dbo = new BasicDBObject();
		
		List<Key> list = new ArrayList<Key>();
		
		for (Entity entity : entities) {
			DBCollection collection = db.getCollection(entity.getKey().getKind());
			collection.insert(new BasicDBObject(entity.getPropertyMap()));
			list.add(entity.getKey());
		}
		DBCollection collection = db.getCollection("test");
//		collection.
		return list;
    }*/
	
	

	
	public static void main(String[] args) {
		/*Set set1 = new HashSet();
		for (int i = 0; i < 10000; i++) {
			long l = ByteBuffer.wrap(UUID.randomUUID().toString().getBytes()).getLong();
//			System.out.println(l);
			set1.add(l);
		}
		
		System.out.println(set1.size());*/
		
		/*long s = UUID.randomUUID().getMostSignificantBits();
		System.out.println(s);*/
		
		
		/*Set set1 = new HashSet();
		Set set2 = new HashSet();
		Set set3 = new HashSet();
		Set set4 = new HashSet();
		
		for (int i = 0; i < 1000; i++) {
			
			String sid = IdentifierGenerator.generate();
			System.out.println("String id = "+sid);
			long lid = IdentifierGenerator.bytes2long(sid.getBytes());
			System.out.println("long id = "+lid);
			set1.add(sid);
			set2.add(lid);
			String uuid = IdentifierGenerator.uuid();
			System.out.println("String uuid = "+uuid);
			set3.add(uuid);
			long luid = IdentifierGenerator.bytes2long(uuid.getBytes());
			System.out.println("long uuid = "+luid);
			set4.add(luid);
		}
		
		System.out.println(set1.size());
		System.out.println(set2.size());
		System.out.println(set3.size());
		System.out.println(set4.size());*/
		
		
		
		
	}

}
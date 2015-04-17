package org.heiduc.api.datastore;

import static org.heiduc.api.datastore.FetchOptions.Builder.withDefaults;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.bson.types.Binary;
import org.heiduc.api.datastore.Query.FilterOperator;
import org.heiduc.api.datastore.Query.FilterPredicate;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


public class PreparedQueryImpl implements PreparedQuery {

	private DB db;
	private final Query query;
	
	public PreparedQueryImpl(DB db , Query query) {
		this.db = db;
		this.query = query;
	}

	@Override
	public Iterable<Entity> asIterable(final FetchOptions fetchOptions) {
		return new Iterable<Entity>() {
		      @Override
		      public Iterator<Entity> iterator() {
		        return asIterator(fetchOptions);
		      }
		    };
	}

	@Override
	public Iterable<Entity> asIterable() {
		return asIterable(withDefaults());
	}

	@Override
	public Iterator<Entity> asIterator() {
		return asIterator(withDefaults());
	}

	@Override
	public Iterator<Entity> asIterator(FetchOptions fetchOptions) {
		Collection<Entity> list = new ArrayList<Entity>();
		DBCollection collection = db.getCollection(query.getKind());
		DBCursor cursor = collection.find(createDBObject());
		while(cursor.hasNext()){
			DBObject dbo = cursor.next();
			try{
				Long.valueOf(dbo.get("_id").toString());
			}catch(Exception e){
				System.out.println(dbo.get("_id").toString());
			}
			
			Entity entity = new Entity(new Key(query.getKind(),null,Long.valueOf(dbo.get("_id").toString())));
			entity.getPropertyMap().putAll(dbo.toMap());
			/*Iterator it = dbo.toMap().entrySet().iterator();   
		    while (it.hasNext()) {   
		        Map.Entry entry = (Map.Entry) it.next();   
		        Object _key = entry.getKey();   
		        Object _value = entry.getValue();
		        if (_value instanceof byte[]) {
		        	System.out.println("_value = "+dbo.get(_key.toString()));
		        	_value = new Blob((byte[])_value);
				}
		        if(_value != null){
		        	System.out.println("key = "+_key+"\t value="+_value+"\t type="+_value.getClass().getSimpleName());
		        }
		        entity.getPropertyMap().put(_key, _value);
//		        System.out.println("key = "+_key+"\t value="+_value);
		    }*/ 
			/*for (String k :  dbo.keySet()) {
				
			}*/
			/*try {
				PropertyUtils.copyProperties(entity.getPropertyMap(), dbo.);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}*/
			list.add(entity);
		}
		return list.iterator();
	}
	
	
	private DBObject createDBObject(){
		//
		BasicDBObject doc = new BasicDBObject();
		
		List<FilterPredicate> filterPredicates= query.getFilterPredicates();
		
		for (FilterPredicate filterPredicate : filterPredicates) {
			
			if(filterPredicate.getOperator().equals(FilterOperator.EQUAL)){
				doc.put(filterPredicate.getPropertyName(), filterPredicate.getValue());
				continue;
			}
			BasicDBObject dbo = (BasicDBObject)doc.get(filterPredicate.getPropertyName());
			if(dbo == null)
				dbo = new BasicDBObject();
			
			if(filterPredicate.getOperator().equals(FilterOperator.LESS_THAN)){
				doc.put(filterPredicate.getPropertyName(), dbo.append(FilterOperator.LESS_THAN.toString(), filterPredicate.getValue()));
			}else if(filterPredicate.getOperator().equals(FilterOperator.LESS_THAN_OR_EQUAL)){
				doc.put(filterPredicate.getPropertyName(), dbo.append(FilterOperator.LESS_THAN_OR_EQUAL.toString(), filterPredicate.getValue()));
			}else if(filterPredicate.getOperator().equals(FilterOperator.GREATER_THAN)){
				doc.put(filterPredicate.getPropertyName(), dbo.append(FilterOperator.GREATER_THAN.toString(), filterPredicate.getValue()));
			}else if(filterPredicate.getOperator().equals(FilterOperator.GREATER_THAN_OR_EQUAL)){
				doc.put(filterPredicate.getPropertyName(), dbo.append(FilterOperator.GREATER_THAN_OR_EQUAL.toString(), filterPredicate.getValue()));
			}else if(filterPredicate.getOperator().equals(FilterOperator.NOT_EQUAL)){
				doc.put(filterPredicate.getPropertyName(), dbo.append(FilterOperator.NOT_EQUAL.toString(), filterPredicate.getValue()));
			}else if(filterPredicate.getOperator().equals(FilterOperator.IN)){
				doc.put(filterPredicate.getPropertyName(), dbo.append(FilterOperator.IN.toString(), filterPredicate.getValue()));
			}
			
		}
		return doc;
	}

}

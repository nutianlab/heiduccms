package org.heiduc.api.datastore;

import static org.heiduc.api.datastore.FetchOptions.Builder.withDefaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.heiduc.api.datastore.Query.FilterOperator;
import org.heiduc.api.datastore.Query.FilterPredicate;
import org.heiduc.api.datastore.Query.SortPredicate;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class PreparedQueryImpl implements PreparedQuery {

	private MongoDatabase database;
	private final Query query;
	
	public PreparedQueryImpl(MongoDatabase database , Query query) {
		this.database = database;
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
		MongoCollection<Document> collection = database.getCollection(query.getKind());
		FindIterable<Document> documents = collection.find(createDBObject());
		//排序
		Bson orderBy = orderBy();
		if(orderBy != null){
			documents.sort(orderBy);
		}
		//限制返回行数
		if(fetchOptions.getLimit() != null && fetchOptions.getLimit() > 0){
			documents = documents.limit(fetchOptions.getLimit());
		}
		
		for (Document document : documents) {
			try{
				Long.valueOf(document.get("_id").toString());
			}catch(Exception e){
				System.out.println(document.get("_id").toString());
			}
			Entity entity = new Entity(new Key(query.getKind(),null,Long.valueOf(document.get("_id").toString())));
			entity.getPropertyMap().putAll(document);
			list.add(entity);
		}
		
		/*while(cursor.hasNext() ){
			
			DBObject dbo = cursor.next();
			try{
				Long.valueOf(dbo.get("_id").toString());
			}catch(Exception e){
				System.out.println(dbo.get("_id").toString());
			}
			
			Entity entity = new Entity(new Key(query.getKind(),null,Long.valueOf(dbo.get("_id").toString())));
			entity.getPropertyMap().putAll(dbo.toMap());
			
			list.add(entity);
		}*/
		return list.iterator();
	}
	
	@Override
	public int count(FetchOptions fetchOptions){
		MongoCollection<Document> collection = database.getCollection(query.getKind());
		collection.find(createDBObject());
		return Integer.valueOf(collection.count()+"");
	}
	
	
	private Bson orderBy(){
		List<SortPredicate> sortPredicates = query.getSortPredicates();
		if(sortPredicates.size() == 0){
			return null;
		}
		
		BasicDBObject orderBy = new BasicDBObject();
		//-1 表示倒序
		for (SortPredicate sortPredicate : sortPredicates) {
			orderBy.put(sortPredicate.getPropertyName(), sortPredicate.getDirection() == Query.SortDirection.DESC ? -1 : "");
		}
		return orderBy;
	}
	
	
	private Bson createDBObject(){
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
			//{$regex:'^(?!.*\_default$)'}
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

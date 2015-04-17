package org.heiduc.api.datastore;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DatastoreService {

	PreparedQuery prepare(Query query);

	Entity get(Key key) throws EntityNotFoundException;
	
//	public abstract Entity get(Transaction transaction, Key key) throws EntityNotFoundException;
	
	Map<Key,Entity> get(Iterable<Key> keys);
	
//	public abstract Map<Key,Entity> get(Transaction transaction, Iterable iterable);

	void delete(Key key);
	
//	public abstract void delete(Transaction transaction, Key[] akey);

	void delete(Iterable<Key> keys);
	
//	public abstract void delete(Transaction transaction, Iterable iterable);

	Key put(Entity entity);
	
//	public abstract Key put(Transaction transaction, Entity entity);
	
//	public List<Key> put(Iterable<Entity> entities);

//    public abstract List put(Transaction transaction, Iterable iterable);
    
    
//    public abstract Transaction beginTransaction();

	/**************base**************/

//    public abstract PreparedQuery prepare(Transaction transaction, Query query);

//    public abstract Transaction getCurrentTransaction();

//    public abstract Transaction getCurrentTransaction(Transaction transaction);

//    public abstract Collection<Transaction> getActiveTransactions();

}

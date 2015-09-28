package org.heiduc.api.datastore;

import java.util.Iterator;

import org.heiduc.api.datastore.Entity;
import org.heiduc.api.datastore.FetchOptions;


public interface PreparedQuery {

	Iterable<Entity> asIterable(FetchOptions withChunkSize);

	Iterable<Entity> asIterable();

	Iterator<Entity> asIterator();
	
	Iterator<Entity> asIterator(FetchOptions fetchOptions);

	int count(FetchOptions fetchOptions);

}

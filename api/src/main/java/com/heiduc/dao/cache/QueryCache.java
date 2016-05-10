

package com.heiduc.dao.cache;

import java.util.List;

import com.heiduc.entity.BaseEntity;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public interface QueryCache<T extends BaseEntity> {

	void putQuery(Class<?> clazz, String query, Object[] params, List<T> list);

	List<T> getQuery(Class<?> clazz, String query, Object[] params);
	
	void removeQueries(Class<?> clazz);

	List<T> getQuery(Class<?> clazz, String queryId, int queryLimit, Object[] params);

	void putQuery(Class<?> clazz, String queryId, Object[] params, int queryLimit, List<T> result);
	
}

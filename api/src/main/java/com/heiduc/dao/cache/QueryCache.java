

package com.heiduc.dao.cache;

import java.util.List;

import com.heiduc.entity.BaseEntity;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public interface QueryCache {

	void putQuery(Class clazz, String query, Object[] params, 
			List<BaseEntity> list);

	List<BaseEntity> getQuery(Class clazz, String query, Object[] params);
	
	void removeQueries(Class clazz);
	
}

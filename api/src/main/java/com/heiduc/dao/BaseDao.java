

package com.heiduc.dao;

import java.util.List;

import org.heiduc.api.datastore.Key;


import com.heiduc.entity.BaseEntity;


public interface BaseDao<T extends BaseEntity> 
		extends AbstractDao {

	String getKind();
	
	Key getKey(Long id);

	List<Key> getKeys(List<Long> ids);
	
	T save(final T model);
	
	T saveNoAudit(final T model);

	T getById(final Long id);
	
	List<T> getById(final List<Long> ids);

	void remove(final Long id);
	
	void remove(final List<Long> ids);

	List<T> select();

	void removeAll();
	
	void clearCache();

	int count();
}

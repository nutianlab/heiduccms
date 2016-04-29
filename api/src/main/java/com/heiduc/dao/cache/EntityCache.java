

package com.heiduc.dao.cache;

import java.util.List;
import java.util.Map;

import com.heiduc.entity.BaseEntity;

public interface EntityCache<T extends BaseEntity> {

	void putEntity(Class clazz, Object id, Object entity);
	
	void putEntities(Class clazz, List<T> list);

	Object getEntity(Class clazz, Object id);
	
	Map<Long, T> getEntities(Class clazz, List<Long> ids);

	void removeEntity(Class clazz, Object id);
	
}

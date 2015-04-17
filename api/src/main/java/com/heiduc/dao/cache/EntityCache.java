

package com.heiduc.dao.cache;

import java.util.List;
import java.util.Map;

import com.heiduc.entity.BaseEntity;

public interface EntityCache {

	void putEntity(Class clazz, Object id, Object entity);
	
	void putEntities(Class clazz, List<BaseEntity> list);

	Object getEntity(Class clazz, Object id);
	
	Map<Long, BaseEntity> getEntities(Class clazz, List<Long> ids);

	void removeEntity(Class clazz, Object id);
	
}

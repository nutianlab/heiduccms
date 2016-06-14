

package com.heiduc.dao.cache.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.common.HeiducContext;
import com.heiduc.dao.DaoStat;
import com.heiduc.dao.cache.EntityCache;
import com.heiduc.entity.BaseEntity;
import com.heiduc.global.CacheService;
import com.heiduc.global.SystemService;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class EntityCacheImpl<T extends BaseEntity> implements EntityCache<T>, Serializable {

	protected static final Log logger = LogFactory.getLog(
			EntityCacheImpl.class);
	
	private static DaoStat getDaoStat() {
		return HeiducContext.getInstance().getBusiness().getDao().getDaoStat();
	}

	public EntityCacheImpl() {
	}
	
	private String getEntityKey(Class clazz, Object id) {
		return "entity:" + clazz.getName() + id.toString();
	}
	
	@Override
	public Object getEntity(Class clazz, Object id) {
		try {
			CacheItem item = (CacheItem)getCache(clazz).get(getEntityKey(clazz, id));
			if (item != null) {
				Date globalResetDate = getCache(clazz).getResetDate();
				if (globalResetDate == null 
						|| item.getTimestamp().after(globalResetDate)) {
					getDaoStat().incEntityCacheHits();
					return item.getData();
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Map<Long, T> getEntities(Class clazz, List<Long> ids) {
//		List<String> keys = new ArrayList<String>(); 
		Set<String> keys = new HashSet<String>(); 
		Map<Long, T> result = new HashMap<Long, T>();
		for (Long id : ids) {
			keys.add(getEntityKey(clazz, id));
			result.put(id, null);
		}
		try {
			Map items = getCache(clazz).getAll(keys);
			for (CacheItem item : (Collection<CacheItem>)items.values()) {
				if (item != null) {
					Date globalResetDate = getCache(clazz).getResetDate();
					if (globalResetDate == null 
							|| item.getTimestamp().after(globalResetDate)) {
						getDaoStat().incEntityCacheHits();
						BaseEntity entity = (BaseEntity)item.getData();
						
						result.put(entity.getId(), (T)entity);
					}
					
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	@Override
	public void putEntity(Class clazz, Object id, Object entity) {
		String key = getEntityKey(clazz, id);
		getCache(clazz).put(key, new CacheItem(entity));
		/*if(getEntity(clazz,id) == null){
			System.out.println("putEntity Class:"+clazz.getClass().getName()+" is null.");
		}*/
	}

	@Override
	public void putEntities(Class clazz, List<T> list) {
		Map<String, CacheItem> map = new HashMap<String, CacheItem>(); 
		for (BaseEntity entity : list) {
			logger.debug(entity.getClass().getName());
			map.put(getEntityKey(clazz, entity.getId()), new CacheItem(entity));
		}
		getCache(clazz).putAll(map);
	}

	@Override
	public void removeEntity(Class clazz, Object id) {
		getCache(clazz).remove(getEntityKey(clazz, id));
	}

	public SystemService getSystemService() {
		return HeiducContext.getInstance().getBusiness().getSystemService();
	}

	private CacheService getCache(Class<?> clazz) {
		return getSystemService().getCache(clazz);
	}

}

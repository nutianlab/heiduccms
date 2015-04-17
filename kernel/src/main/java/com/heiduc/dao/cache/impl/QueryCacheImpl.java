

package com.heiduc.dao.cache.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.heiduc.api.datastore.Entity;
import org.heiduc.api.datastore.Key;
import org.heiduc.api.datastore.KeyFactory;

import com.heiduc.common.HeiducContext;
import com.heiduc.dao.DaoStat;
import com.heiduc.dao.cache.EntityCache;
import com.heiduc.dao.cache.QueryCache;
import com.heiduc.entity.BaseEntity;
import com.heiduc.global.CacheService;
import com.heiduc.global.SystemService;
import com.heiduc.utils.EntityUtil;

public class QueryCacheImpl implements QueryCache, Serializable {

	protected static final Log logger = LogFactory.getLog(
			QueryCacheImpl.class);

	private static DaoStat getDaoStat() {
		return HeiducContext.getInstance().getBusiness().getDao().getDaoStat();
	}

	private EntityCache entityCache;
	
	public QueryCacheImpl(EntityCache anEntityCache) {
		entityCache = anEntityCache;
	}

	public SystemService getSystemService() {
		return HeiducContext.getInstance().getBusiness().getSystemService();
	}

	private CacheService getCache() {
		return getSystemService().getCache();
	}

	private EntityCache getEntityCache() {
		return entityCache;
	}
	
	private String getQueryKey(Class clazz, String query, Object[] params) {
		StringBuffer result = new StringBuffer(clazz.getName());
		result.append(query);
		if (params != null) {
			for (Object param : params) {
				result.append(param != null ? param.toString() : "null"); 
			}
		}
		return result.toString();
	}

	private String getClassResetdateKey(Class clazz) {
		return "classResetDate:" + clazz.getName();
	}
	
	private Date getClassResetDate(Class clazz) {
		return (Date)getCache().get(getClassResetdateKey(clazz));
	}
	
	@Override
	public List<BaseEntity> getQuery(Class clazz, String query, 
			Object[] params) {
		try {
			CacheItem item = (CacheItem)getCache().get(getQueryKey(clazz, query, 
					params));
			if (item != null) {
				Date globalResetDate = getCache().getResetDate();
				if (globalResetDate == null 
						|| item.getTimestamp().after(globalResetDate)) {
					Date classResetDate = getClassResetDate(clazz);
					if (classResetDate == null
							|| item.getTimestamp().after(classResetDate)) {
						return getCachedQueryResult(clazz, item);
					}
				}
			}
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return null; 
	}

	private List<BaseEntity> getCachedQueryResult(Class clazz, CacheItem item) {
		getDaoStat().incQueryCacheHits();
		List<Long> ids = (List<Long>)item.getData();
		Map<Long, BaseEntity> cached = getEntityCache().getEntities(clazz, ids);
		List<Key> toLoadKeys = new ArrayList<Key>();
		for (Long id : cached.keySet()) {
			if (cached.get(id) == null) {
				toLoadKeys.add(KeyFactory.createKey(EntityUtil.getKind(clazz), 
						id));
			}
			else {
				getDaoStat().incEntityCacheHits();
			}
		}
		cached.putAll(loadEntities(clazz, toLoadKeys));
		List<BaseEntity> result = new ArrayList<BaseEntity>();
		for (Long id : ids) {
			result.add(cached.get(id));
		}
		return result;
	}
	
	private Map<Long, BaseEntity> loadEntities(Class clazz, List<Key> keys) {
		Map<Long, BaseEntity> result = new HashMap<Long, BaseEntity>();
		try {
			getDaoStat().incGetCalls();
			Map<Key, Entity> loaded = getSystemService().getDatastore().get(keys);
			for (Key key : loaded.keySet()) {
				BaseEntity model = (BaseEntity)clazz.newInstance();
				model.load(loaded.get(key));
				result.put(model.getId(), model);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void putQuery(Class clazz, String query, Object[] params, 
			List<BaseEntity> list) {
		String key = getQueryKey(clazz, query, params);
		List<Long> ids = new ArrayList<Long>();
		for (BaseEntity entity : list) {
			ids.add(entity.getId());
		}
		getCache().put(key, new CacheItem(ids));
		getEntityCache().putEntities(clazz, list);
	}

	@Override
	public void removeQueries(Class clazz) {
		getCache().put(getClassResetdateKey(clazz), new Date());
	}

}

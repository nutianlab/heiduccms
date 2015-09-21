package com.heiduc.global.impl;

import static javax.cache.expiry.Duration.ONE_HOUR;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.integration.CompletionListener;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.spi.CachingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.global.CacheService;
import com.heiduc.utils.ArrayUtil;
import com.heiduc.utils.StrUtil;

public class CacheServiceImpl implements CacheService {

	private static final Log log = LogFactory.getLog(CacheServiceImpl.class);

	private static final long LOCAL_CACHE_TTL = 5000;
	private static final String RESET_DATE_KEY = "cacheResetDate";

	private Cache cache;
	private Map<String, Object> localCache;
	private long localCacheTime;
	private int localHits;
	private int cacheHits;

	public CacheServiceImpl() {
		try {
			/*
			 * cache = CacheManager.getInstance().getCacheFactory().createCache(
			 * Collections.emptyMap());
			 */

			// resolve a cache manager
			synchronized(this) {  
				cache = Caching.getCache("heiducCache",String.class, Object.class);
				if(cache == null){
					CachingProvider cachingProvider = Caching.getCachingProvider();
					CacheManager cacheManager = cachingProvider.getCacheManager();
		
					// configure the cache
					MutableConfiguration<String, Object> config = new MutableConfiguration<String, Object>();
					// uses store by reference
					config.setStoreByValue(false).setTypes(String.class, Object.class)
							.setExpiryPolicyFactory(
									AccessedExpiryPolicy.factoryOf(ONE_HOUR))
							.setStatisticsEnabled(true);
		
	//				cacheManager.destroyCache("heiducCache");
					// create the cache
					cache = cacheManager.createCache("heiducCache", config);
				}
			
			}
			

		} catch (CacheException e) {
			e.printStackTrace();
			log.error("Can't init cache manager. " + e.getMessage());
		}
		localCache = new HashMap<String, Object>();
		localCacheTime = System.currentTimeMillis();
	}

	@Override
	public void clear() {
		localCache.clear();
		cache.clear();
		put(RESET_DATE_KEY, new Date());
	}

	@Override
	public boolean containsKey(Object key) {
		if (localCache.containsKey(key)) {
			return true;
		}
		return cache.containsKey(key);
	}

	@Override
	public Object get(Object key) {
		try {
			if (localCache.containsKey(key)) {
				localHits++;
				return localCache.get(key);
			}
			Object value = cache.get(key);
			localCache.put((String) key, value);
			cacheHits++;
			return value;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public void putAll(Map map) {
		localCache.putAll(map);
		try {
			cache.putAll(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void close() {
		cache.close();
		
	}

	@Override
	public void deregisterCacheEntryListener(CacheEntryListenerConfiguration cacheEntryListenerConfiguration) {
		cache.deregisterCacheEntryListener(cacheEntryListenerConfiguration);
	}

	@Override
	public Map getAll(Set keys) {
		Map result = new HashMap();
		Set memcacheKeys = new HashSet();
		for (Object key : keys) {
			if (localCache.containsKey(key)) {
				result.put(key, localCache.get(key));
			}
			else {
				memcacheKeys.add(key);
			}
		}
		try {
			result.putAll(cache.getAll(memcacheKeys));
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Object getAndPut(Object key, Object value) {
		localCache.remove(key);
		return cache.getAndPut(key, value);
	}

	@Override
	public Object getAndRemove(Object key) {
		localCache.remove(key);
		return cache.getAndRemove(key);
	}

	@Override
	public Object getAndReplace(Object key, Object value) {
		localCache.remove(key);
		return cache.getAndReplace(key, value);
	}

	@Override
	public CacheManager getCacheManager() {
		return cache.getCacheManager();
	}

	@Override
	public Configuration getConfiguration(Class clazz) {
		return cache.getConfiguration(clazz);
	}

	@Override
	public String getName() {
		return cache.getName();
	}

	@Override
	public Object invoke(Object key, EntryProcessor entryProcessor, Object... arguments)
			throws EntryProcessorException {
		return cache.invoke(key, entryProcessor, arguments);
	}

	@Override
	public Map invokeAll(Set keys, EntryProcessor entryProcessor, Object... arguments) {
		return cache.invokeAll(keys, entryProcessor, arguments);
	}

	@Override
	public boolean isClosed() {
		return cache.isClosed();
	}

	@Override
	public Iterator iterator() {
		return cache.iterator();
	}

	@Override
	public void loadAll(Set keys, boolean replaceExistingValues, CompletionListener completionListener) {
		localCache.clear();
		cache.loadAll(keys, replaceExistingValues, completionListener);
		
	}

	@Override
	public void put(Object key, Object value) {
		localCache.put((String)key, value);
		try {
			cache.put(key, value);
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}

	@Override
	public boolean putIfAbsent(Object key, Object value) {
		return cache.putIfAbsent(key, value);
	}

	@Override
	public void registerCacheEntryListener(CacheEntryListenerConfiguration cacheEntryListenerConfiguration) {
		cache.registerCacheEntryListener(cacheEntryListenerConfiguration);
		
	}

	@Override
	public boolean remove(Object key) {
		localCache.remove(key);
		try {
			return cache.remove(key);
		}
		catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean remove(Object key, Object oldValue) {
		localCache.remove(key);
		return cache.remove(key,oldValue);
	}

	@Override
	public void removeAll() {
		localCache.clear();
		cache.removeAll();
	}

	@Override
	public void removeAll(Set keys) {
		for (Object key : keys) {
			if (localCache.containsKey(key)) {
				localCache.remove(key);
			}
		}
		cache.removeAll(keys);
	}

	@Override
	public boolean replace(Object key, Object value) {
		localCache.put(key+"", value);
		return cache.replace(key, value);
	}

	@Override
	public boolean replace(Object key, Object oldValue, Object newValue) {
		if (localCache.containsKey(key) && oldValue.equals(localCache.get(key))) {
			localCache.put(key+"", newValue);
		}
		return cache.replace(key, oldValue, newValue);
	}

	@Override
	public Object unwrap(Class clazz) {
		return cache.unwrap(clazz);
	}
	
	@Override
	public void resetLocalCache() {
		if (System.currentTimeMillis() - localCacheTime > LOCAL_CACHE_TTL) {
			localCache.clear();
			localCacheTime = System.currentTimeMillis();
		}
	}
	
	@Override
	public int getLocalHits() {
		return localHits;
	}

	public int getCacheHits() {
		return cacheHits;
	}

	@Override
	public Cache getMemcache() {
		return cache;
	}

	@Override
	public byte[] getBlob(String key) {
		String chunkList = (String) get(key);
		if (chunkList != null) {
			List<byte[]> data = new ArrayList<byte[]>();
			int size = 0;
			for (String chunkKey : StrUtil.fromCSV(chunkList)) {
				byte[] chunk = (byte[]) get(chunkKey);
				if (chunk == null) {
					return null;
				}
				data.add(chunk);
				size += chunk.length;
			}
			return ArrayUtil.packChunks(data);
		}
		return null;
	}

	public static int CACHE_SIZE_LIMIT = 1000000;

	@Override
	public void putBlob(String key, byte[] data) {
		List<String> chunkList = new ArrayList<String>();
		List<byte[]> chunks = ArrayUtil.makeChunks(data, CACHE_SIZE_LIMIT);
		int i = 0;
		for (byte[] chunk : chunks) {
			String chunkKey = key + String.valueOf(i);
			put(chunkKey, chunk);
			chunkList.add(chunkKey);
			i++;
		}
		put(key, StrUtil.toCSV(chunkList));
	}

	@Override
	public Date getResetDate() {
		return (Date) get(RESET_DATE_KEY);
	}

}

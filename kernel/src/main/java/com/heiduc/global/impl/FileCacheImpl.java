

package com.heiduc.global.impl;

import javax.cache.Cache;

//import net.sf.jsr107cache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.global.FileCache;
import com.heiduc.global.FileCacheItem;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class FileCacheImpl implements FileCache {

	private static final Log logger = LogFactory.getLog(FileCacheImpl.class);
	
	private Cache cache;
	
	public FileCacheImpl(Cache cache) {
		this.cache = cache;
	}
	
	private String getFileKey(String path) {
		return "file:" + path;
	}
	
	@Override
	public FileCacheItem get(String path) {
		return (FileCacheItem)cache.get(getFileKey(path));
	}

	@Override
	public void put(String path, FileCacheItem item) {
		cache.put(getFileKey(path), item);
	}

	@Override
	public void remove(String path) {
		cache.remove(getFileKey(path));
	}

}

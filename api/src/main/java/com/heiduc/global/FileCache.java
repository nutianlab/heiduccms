

package com.heiduc.global;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public interface FileCache {

	void put(String path, FileCacheItem item);
	
	FileCacheItem get(String path);
	
	void remove(String path);
	
}

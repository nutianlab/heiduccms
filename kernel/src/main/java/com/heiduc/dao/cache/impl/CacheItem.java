

package com.heiduc.dao.cache.impl;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class CacheItem implements Serializable {

	private Object data;
	private Date timestamp;
	
	public Object getData() {
		return data;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public CacheItem(Object data) {
		super();
		this.data = data;
		this.timestamp = new Date();
	}

}



package com.heiduc.dao.cache.impl;

import java.io.Serializable;
import java.util.Date;

public class CacheItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4547202076142074670L;
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
//		if(data instanceof Serializable)
		this.data = data;
		this.timestamp = new Date();
	}

}

package com.heiduc.dao.cache.impl;

import java.io.Serializable;
import java.util.Date;

import com.heiduc.utils.StreamUtil;

public class CacheItem implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4547202076142074670L;
	private byte[] data;
	private Date timestamp;
	
	public Object getData() {
		return StreamUtil.toObject(data);
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public CacheItem(Object data) {
		super();
		this.data = StreamUtil.toBytes(data);
		this.timestamp = new Date();
	}

}

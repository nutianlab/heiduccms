

package com.heiduc.global;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class PageCacheItem implements Serializable {
	
	private String content;
	private String contentType;
	private Date timestamp;
	
	public PageCacheItem(String content, String contentType) {
		super();
		timestamp = new Date();
		this.content = content;
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public String getContentType() {
		return contentType;
	}

	public Date getTimestamp() {
		return timestamp;
	}

}

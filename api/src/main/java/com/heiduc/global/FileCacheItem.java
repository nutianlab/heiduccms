

package com.heiduc.global;

import java.io.Serializable;
import java.util.Date;

import com.heiduc.entity.FileEntity;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class FileCacheItem implements Serializable {
	
	private FileEntity file;
	private byte[] content;
	private boolean publicCache;
	private Date timestamp;
	
	public FileCacheItem(FileEntity file, byte[] content, boolean publicCache) {
		super();
		timestamp = new Date();
		this.file = file;
		this.content = content;
		this.publicCache = publicCache;
	}

	public FileEntity getFile() {
		return file;
	}

	public byte[] getContent() {
		return content;
	}

	public boolean isPublicCache() {
		return publicCache;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
}

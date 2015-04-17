

package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.getBlobProperty;
import static com.heiduc.utils.EntityUtil.getIntegerProperty;
import static com.heiduc.utils.EntityUtil.getLongProperty;
import static com.heiduc.utils.EntityUtil.setProperty;

import org.heiduc.api.datastore.Entity;


public class FileChunkEntity extends BaseEntityImpl {
	
	private static final long serialVersionUID = 2L;

	private byte[] content;
	private int index;
	private Long fileId;
	
    public FileChunkEntity() {
    }
    
    public FileChunkEntity(Long fileId, byte[] content, int index) {
		this();
		this.fileId = fileId;
		this.content = content;
		this.index = index;
	}

    @Override
    public void load(Entity entity) {
    	super.load(entity);
    	content = getBlobProperty(entity, "content");
    	index = getIntegerProperty(entity, "index", 0);
    	fileId = getLongProperty(entity, "fileId");
    }
    
    @Override
    public void save(Entity entity) {
    	super.save(entity);
    	setProperty(entity, "content", content);
    	setProperty(entity, "index", index, false);
    	setProperty(entity, "fileId", fileId, true);
    }

    public byte[] getContent() {
		return content;
	}
	
	public void setContent(byte[] content) {
		this.content = content;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	
}



package com.heiduc.entity;

import java.util.Date;

import org.heiduc.api.datastore.Entity;

import static com.heiduc.utils.EntityUtil.*;

import com.heiduc.utils.FolderUtil;

public class FileEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 5L;

	private static final String[] IMAGE_EXTENSIONS = {"jpg","jpeg","png","ico",
	"gif"};

	private String title;
	private String filename;	
	private Long folderId;	
	private String mimeType;
	private Date lastModifiedTime;
	private Integer size;

	public FileEntity() {
	}
	
	@Override
	public void load(Entity entity) {
		super.load(entity);
		title = getStringProperty(entity, "title");
		filename = getStringProperty(entity, "filename");
		folderId = getLongProperty(entity, "folderId");
		mimeType = getStringProperty(entity, "mimeType");
		lastModifiedTime = getDateProperty(entity, "lastModifiedTime");
		size = getIntegerProperty(entity, "size", 0);
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "title", title, false);
		setProperty(entity, "filename", filename, true);
		setProperty(entity, "folderId", folderId, true);
		setProperty(entity, "mimeType", mimeType, false);
		setProperty(entity, "lastModifiedTime", lastModifiedTime, false);
		setProperty(entity, "size", size, false);
	}

	public FileEntity(String aTitle, String aName, Long aFolderId,
			String aMimeType, Date aMdttime, int aSize) {
		this();
		title = aTitle;
		filename = aName;
		folderId = aFolderId;
		mimeType = aMimeType;
		lastModifiedTime = aMdttime;
		size = aSize;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date aLastModifiedTime) {
		this.lastModifiedTime = aLastModifiedTime;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isImage() {
		for (String ext : IMAGE_EXTENSIONS) {
			if (FolderUtil.getFileExt(getFilename()).equals(ext)) {
				return true;
			}
		}
		return false;
	}

}

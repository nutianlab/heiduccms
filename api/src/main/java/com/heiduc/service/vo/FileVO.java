

package com.heiduc.service.vo;

import java.util.Date;

import com.heiduc.entity.FileEntity;

/**
 * Value object to be returned from services.
 * @author Alexander Oleynik
 */
public class FileVO {

	private FileEntity file;
	private String link;
	private boolean textFile;
	private boolean imageFile;
	private String content;
	
	public FileVO(final FileEntity entity) {
		file = entity;
	}

	public String getTitle() {
		return file.getTitle();
	}
	
	public String getName() {
		return file.getFilename();
	}

	public String getMimeType() {
		return file.getMimeType();
	}

	public Long getFolderId() {
		return file.getFolderId();
	}
	
	public int getSize() {
		return file.getSize();
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isTextFile() {
		return textFile;
	}

	public void setTextFile(boolean text) {
		this.textFile = text;
	}

	public boolean isImageFile() {
		return imageFile;
	}

	public void setImageFile(boolean image) {
		this.imageFile = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getModDate() {
		return file.getLastModifiedTime();
	}
	
}

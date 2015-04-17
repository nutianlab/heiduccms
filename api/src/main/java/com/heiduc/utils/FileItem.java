

package com.heiduc.utils;

import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.io.FilenameUtils;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class FileItem {
	
	private byte[] data;
	private String fieldName;
	private String filename;

	public FileItem(FileItemStream item, byte[] aData) {
		super();
		data = aData;
		filename = FilenameUtils.getName(item.getName());
		fieldName = item.getFieldName();
	}

	public FileItem(String aFieldName, String aFilename, byte[] aData) {
		super();
		data = aData;
		filename = aFilename;
		fieldName = aFieldName;
	}

	public byte[] getData() {
		return data;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFilename() {
		return filename;
	}
}



package com.heiduc.business;

import java.util.List;

import com.heiduc.entity.FileEntity;

public interface FileBusiness {

	List<String> validateBeforeUpdate(final FileEntity entity);
	
	FileEntity saveFile(String filename, byte[] data);
	
	byte[] readFile(String filename);
	
	FileEntity findFile(String filename);
	
	/**
	 * Remove file by full pathname.
	 * @param filename - full file pathname
	 */
	void remove(String filename);
	
	void remove(List<Long> ids);
	
	String getFilePath(FileEntity file);
}

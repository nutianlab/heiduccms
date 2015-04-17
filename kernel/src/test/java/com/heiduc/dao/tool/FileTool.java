

package com.heiduc.dao.tool;

import java.util.Date;

import com.heiduc.dao.Dao;
import com.heiduc.entity.FileEntity;
import com.heiduc.entity.FolderEntity;

public class FileTool {

	private Dao dao;
	
	public FileTool(Dao aDao) {
		dao = aDao;
	}

	public FileEntity addFile(final String title, final String name, 
			final String contentType,final byte[] data, 
			final FolderEntity folder) {
		FileEntity file = new FileEntity(title, name, folder.getId(), 
				contentType, new Date(), data.length);
		dao.getFileDao().save(file, data);
		return file;
	}
	
}

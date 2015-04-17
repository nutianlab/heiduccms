

package com.heiduc.dao.tool;

import com.heiduc.dao.Dao;
import com.heiduc.entity.FolderEntity;

public class FolderTool {

	private Dao dao;
	
	public FolderTool(Dao aDao) {
		dao = aDao;
	}
	
	public FolderEntity addFolder(final String name, final Long parent) {
		return dao.getFolderDao().save(new FolderEntity(name, parent));
	}

	public FolderEntity addFolder(final String name) {
		return addFolder(name, null);
	}	
	
}

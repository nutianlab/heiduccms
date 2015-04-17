

package com.heiduc.dao.tool;

import com.heiduc.dao.Dao;
import com.heiduc.entity.FolderPermissionEntity;
import com.heiduc.enums.FolderPermissionType;

public class FolderPermissionTool {

	private Dao dao;
	
	public FolderPermissionTool(Dao aDao) {
		dao = aDao;
	}
	
	public FolderPermissionEntity addPermission(Long folderId, 
			FolderPermissionType perm,	Long aGroupId) {
		return dao.getFolderPermissionDao().save(new FolderPermissionEntity(
				folderId, perm, aGroupId));
	}
	
}

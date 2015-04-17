

package com.heiduc.dao.tool;

import com.heiduc.dao.Dao;
import com.heiduc.entity.ContentPermissionEntity;
import com.heiduc.enums.ContentPermissionType;

public class ContentPermissionTool {

	private Dao dao;
	
	public ContentPermissionTool(Dao aDao) {
		dao = aDao;
	}
	
	public ContentPermissionEntity addPermission(String anUrl, 
			ContentPermissionType perm,	Long aGroupId) {
		return dao.getContentPermissionDao().save(new ContentPermissionEntity(
				anUrl, perm, aGroupId));
	}
	
}

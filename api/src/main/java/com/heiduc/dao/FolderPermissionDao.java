

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.FolderPermissionEntity;

/**
 * @author Alexander Oleynik
 */
public interface FolderPermissionDao 
		extends BaseDao<FolderPermissionEntity> {

	FolderPermissionEntity getByFolderGroup(final Long folderId, 
			final Long groupId);

	List<FolderPermissionEntity> selectByFolder(final Long folderId);
	
	void removeByGroup(final List<Long> groupIds);

}

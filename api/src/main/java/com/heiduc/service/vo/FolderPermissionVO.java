

package com.heiduc.service.vo;

import com.heiduc.entity.FolderPermissionEntity;
import com.heiduc.entity.GroupEntity;

/**
 * Value object to be returned from services.
 * @author Alexander Oleynik
 */
public class FolderPermissionVO {

    private FolderPermissionEntity folderPermission;
    private boolean inherited;
    private GroupEntity group;

	public FolderPermissionVO(final FolderPermissionEntity entity) {
		folderPermission = entity;
	}

	public String getId() {
		return folderPermission.getId() == null ? "null" : 
			folderPermission.getId().toString();
	}

	public boolean isInherited() {
		return inherited;
	}

	public void setInherited(boolean inherited) {
		this.inherited = inherited;
	}

	public GroupEntity getGroup() {
		return group;
	}

	public void setGroup(GroupEntity group) {
		this.group = group;
	}
	
	public String getPermission() {
		return folderPermission.getPermission().name();
	}
	
}

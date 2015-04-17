

package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.*;

import org.heiduc.api.datastore.Entity;

import com.heiduc.enums.FolderPermissionType;

/**
 * @author Alexander Oleynik
 */
public class FolderPermissionEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 3L;

	private Long folderId;
	private FolderPermissionType permission;
    private Long groupId;
	
	public FolderPermissionEntity() {
	}
	
	public FolderPermissionEntity(Long aFolderId) {
		this();
		folderId = aFolderId;
	}
	
	public FolderPermissionEntity(Long aFolderId, FolderPermissionType perm) {
		this(aFolderId);
		permission = perm;
	}

	public FolderPermissionEntity(Long aFolderId, FolderPermissionType perm,
			Long aGroupId) {
		this(aFolderId, perm);
		groupId = aGroupId;
	}

	@Override
	public void load(Entity entity) {
		super.load(entity);
		folderId = getLongProperty(entity, "folderId");
		permission = FolderPermissionType.valueOf(getStringProperty(entity, 
				"permission"));
		groupId = getLongProperty(entity, "groupId");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "folderId", folderId, true);
		setProperty(entity, "permission", permission.name(), false);
		setProperty(entity, "groupId", groupId, true);
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long aFolderId) {
		this.folderId = aFolderId;
	}

	public FolderPermissionType getPermission() {
		return permission;
	}

	public void setPermission(FolderPermissionType permission) {
		this.permission = permission;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public boolean isRead() {
		return permission.equals(FolderPermissionType.READ);
	}

	public boolean isWrite() {
		return permission.equals(FolderPermissionType.WRITE);
	}
	
	public boolean isDenied() {
		return permission.equals(FolderPermissionType.DENIED);
	}

	public boolean isAdmin() {
		return permission.equals(FolderPermissionType.ADMIN);
	}

	public boolean isChangeGranted() {
		return isWrite() || isAdmin();
	}
	
	public boolean isMyPermissionHigher(FolderPermissionEntity perm) {
		return getPermission().ordinal() > perm.getPermission().ordinal();
	}
	
}

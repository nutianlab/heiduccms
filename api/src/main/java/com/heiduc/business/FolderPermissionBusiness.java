

package com.heiduc.business;

import java.util.List;

import com.heiduc.entity.FolderEntity;
import com.heiduc.entity.FolderPermissionEntity;
import com.heiduc.entity.GroupEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.enums.FolderPermissionType;

/**
 * @author Alexander Oleynik
 */
public interface FolderPermissionBusiness {
	
	FolderPermissionEntity getPermission(final FolderEntity folder, 
			final UserEntity user);

	FolderPermissionEntity getGuestPermission(final FolderEntity folder);

	void setPermission(final FolderEntity folder, final GroupEntity group, 
			final FolderPermissionType permission);
	
	List<String> validateBeforeUpdate(final FolderPermissionEntity perm);

	/**
	 * Get inherited permissions for folder, including page permissions.
	 * @param folder.
	 * @return permission list.
	 */
	List<FolderPermissionEntity> getInheritedPermissions(final FolderEntity folder);

}

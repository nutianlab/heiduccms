

package com.heiduc.business;

import java.util.List;

import com.heiduc.entity.ContentPermissionEntity;
import com.heiduc.entity.GroupEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.enums.ContentPermissionType;

/**
 * @author Alexander Oleynik
 */
public interface ContentPermissionBusiness {
	
	ContentPermissionEntity getPermission(final String url, 
			final UserEntity user);

	ContentPermissionEntity getGuestPermission(final String url);

	void setPermission(final String url, final GroupEntity group, 
			final ContentPermissionType permission);

	void setPermission(final String url, final GroupEntity group, 
			final ContentPermissionType permission, final String languages);
	
	List<String> validateBeforeUpdate(final ContentPermissionEntity perm);

	List<ContentPermissionEntity> getInheritedPermissions(final String url);
	
	List<ContentPermissionEntity> selectByUrl(String pageUrl);
	
}

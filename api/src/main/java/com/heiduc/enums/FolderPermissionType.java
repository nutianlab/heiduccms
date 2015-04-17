

package com.heiduc.enums;

/**
 * @author Alexander Oleynik
 */
public enum FolderPermissionType {
	DENIED, READ, WRITE, ADMIN;
	
	public static FolderPermissionType fromContentPermissionType(
			ContentPermissionType perm) {
		if (perm.equals(ContentPermissionType.DENIED)) {
			return DENIED;
		}
		if (perm.equals(ContentPermissionType.READ)) {
			return READ;
		}
		if (perm.equals(ContentPermissionType.ADMIN)) {
			return ADMIN;
		}
		return WRITE;
	}
	
}

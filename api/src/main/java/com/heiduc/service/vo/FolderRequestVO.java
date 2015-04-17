

package com.heiduc.service.vo;

import java.util.Collections;
import java.util.List;

import com.heiduc.entity.FileEntity;
import com.heiduc.entity.FolderEntity;
import com.heiduc.entity.FolderPermissionEntity;

/**
 * Value object to be returned from services.
 * @author Alexander Oleynik
 */
public class FolderRequestVO {

    private FolderEntity folder;
    private FolderEntity parent;
    private List<FolderEntity> ancestors;
    private List<FileEntity> files;
    private List<FolderEntity> children;
    private List<FolderPermissionVO> permissions;
    private List<GroupVO> groups;
    private FolderPermissionEntity folderPermission;
	
    public FolderRequestVO() {
    	files = Collections.EMPTY_LIST;
    	children = Collections.EMPTY_LIST;
    	permissions = Collections.EMPTY_LIST;
    	groups = Collections.EMPTY_LIST;
    	ancestors = Collections.EMPTY_LIST;
    }

	public FolderEntity getFolder() {
		return folder;
	}

	public void setFolder(FolderEntity folder) {
		this.folder = folder;
	}

	public List<FolderEntity> getChildren() {
		return children;
	}

	public void setChildren(List<FolderEntity> children) {
		this.children = children;
	}

	public List<FolderPermissionVO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<FolderPermissionVO> permissions) {
		this.permissions = permissions;
	}

	public List<GroupVO> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupVO> groups) {
		this.groups = groups;
	}

	public FolderPermissionEntity getFolderPermission() {
		return folderPermission;
	}

	public void setFolderPermission(FolderPermissionEntity folderPermission) {
		this.folderPermission = folderPermission;
	}

	public List<FileEntity> getFiles() {
		return files;
	}

	public void setFiles(List<FileEntity> files) {
		this.files = files;
	}

	public List<FolderEntity> getAncestors() {
		return ancestors;
	}

	public void setAncestors(List<FolderEntity> ancestors) {
		this.ancestors = ancestors;
	}

	public FolderEntity getParent() {
		return parent;
	}

	public void setParent(FolderEntity parent) {
		this.parent = parent;
	}
    
}

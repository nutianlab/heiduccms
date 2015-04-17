

package com.heiduc.service.vo;

import java.util.ArrayList;
import java.util.List;

import com.heiduc.entity.ContentPermissionEntity;
import com.heiduc.entity.GroupEntity;

/**
 * Value object to be returned from services.
 * @author Alexander Oleynik
 */
public class ContentPermissionVO {

    private ContentPermissionEntity contentPermission;
    private boolean inherited;
    private GroupEntity group;

	public ContentPermissionVO(final ContentPermissionEntity entity) {
		contentPermission = entity;
	}

	public static List<ContentPermissionVO> create(
			List<ContentPermissionEntity> list) {
		List<ContentPermissionVO> result = new ArrayList<ContentPermissionVO>();
		for (ContentPermissionEntity ContentPermission : list) {
			result.add(new ContentPermissionVO(ContentPermission));
		}
		return result;
	}

	public String getId() {
		return contentPermission.getId().toString();
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
	
	public boolean isAllLanguages() {
		return contentPermission.isAllLanguages();
	}
	
	public String getLanguages() {
		return contentPermission.getLanguages();
	}

	public String getPermission() {
		return contentPermission.getPermission().name();
	}
	
}

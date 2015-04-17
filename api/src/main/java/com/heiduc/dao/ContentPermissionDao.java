

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.ContentPermissionEntity;

/**
 * @author Alexander Oleynik
 */
public interface ContentPermissionDao extends 
		BaseDao<ContentPermissionEntity> {

	ContentPermissionEntity getByUrlGroup(final String url, final Long groupId);

	List<ContentPermissionEntity> selectByUrl(final String url);
	
	void removeByGroup(final List<Long> groupIds);

}

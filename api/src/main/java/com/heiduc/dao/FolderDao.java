

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.FolderEntity;

/**
 * @author Alexander Oleynik
 */
public interface FolderDao extends BaseDao<FolderEntity> {

	FolderEntity getByPath(final String path);

	String getFolderPath(final Long folderId);

	List<FolderEntity> getByParent(final Long id);

	FolderEntity getByParentName(final Long parentid, final String name);
	
	List<FolderEntity> getAncestors(final FolderEntity folder);
}

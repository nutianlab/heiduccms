

package com.heiduc.dao.impl;

import static org.heiduc.api.datastore.Query.FilterOperator.EQUAL;

import java.util.ArrayList;
import java.util.List;

import org.heiduc.api.datastore.Query;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.FolderPermissionDao;
import com.heiduc.entity.FolderPermissionEntity;

/**
 * @author Alexander Oleynik
 */
public class FolderPermissionDaoImpl 
		extends BaseDaoImpl<FolderPermissionEntity> 
		implements FolderPermissionDao {

	public FolderPermissionDaoImpl() {
		super(FolderPermissionEntity.class);
	}

	@Override
	public List<FolderPermissionEntity> selectByFolder(final Long folderId) {
		Query q = newQuery();
		q.addFilter("folderId", EQUAL, folderId);
		return select(q, "selectByFolder", params(folderId));
	}

	@Override
	public FolderPermissionEntity getByFolderGroup(final Long folderId, 
			final Long groupId) {
		Query q = newQuery();
		q.addFilter("folderId", EQUAL, folderId);
		q.addFilter("groupId", EQUAL, groupId);
		return selectOne(q, "getByFolderGroup", params(folderId, groupId));
	}

	private List<FolderPermissionEntity> selectByGroup(final Long groupId) {
		Query q = newQuery();
		q.addFilter("groupId", EQUAL, groupId);
		return select(q, "selectByGroup", params(groupId));
	}

	@Override
	public void removeByGroup(List<Long> groupIds) {
		for (Long groupId : groupIds) {
			List<FolderPermissionEntity> list = selectByGroup(groupId);
			remove(getIds(list));
		}
	}

	private List<Long> getIds(List<FolderPermissionEntity> list) {
		List<Long> result = new ArrayList<Long>();
		for (FolderPermissionEntity e : list) {
			result.add(e.getId());
		}
		return result;
	}

}

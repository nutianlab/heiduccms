

package com.heiduc.dao.impl;

import static org.heiduc.api.datastore.Query.FilterOperator.EQUAL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.heiduc.api.datastore.Query;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.FolderDao;
import com.heiduc.entity.FolderEntity;

public class FolderDaoImpl extends BaseDaoImpl<FolderEntity> 
		implements FolderDao {

	public FolderDaoImpl() {
		super(FolderEntity.class);
	}

	public List<FolderEntity> getByParent(final Long id) {
		Query q = newQuery();
		q.addFilter("parentId", EQUAL, id);
		return select(q, "getByParent", params(id));
	}
	
	public FolderEntity getByParentName(final Long parentId, 
			final String name) {
		Query q = newQuery();
		q.addFilter("parentId", EQUAL, parentId);
		q.addFilter("name", EQUAL, name);
		return selectOne(q, "getByParentName", params(parentId, name));
	}

	@Override
	public FolderEntity getByPath(String path) {
		FolderEntity result = getByParentName(null, "/");
		for (String name : path.split("/")) {
			if (name.equals("")) {
				continue;
			}
			result = getByParentName(result.getId(), name);
			if (result == null) {
				return null;
			}
		}
		return result;
	}

	@Override
	public String getFolderPath(Long folderId) {
		FolderEntity folder = getById(folderId);
		List<String> names = new ArrayList<String>();
		while(folder != null) {
			names.add(folder.getName());
			folder = getById(folder.getParent());
		}
		Collections.reverse(names);
		StringBuffer result = new StringBuffer();
		for (String name : names) {
			if (!name.equals("/")) {
				result.append("/").append(name);
			}
		}
		return result.toString();
	}

	@Override
	public List<FolderEntity> getAncestors(FolderEntity folder) {
		List<FolderEntity> result = new ArrayList<FolderEntity>();
		FolderEntity current = folder;
		while (current.getParent() != null) {
			FolderEntity parent = getById(current.getParent());
			if (parent == null) {
				break;
			}
			result.add(parent);
			current = parent;
		}
		Collections.reverse(result);
		return result;
	}
	
}

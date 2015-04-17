

package com.heiduc.dao.impl;

import static org.heiduc.api.datastore.Query.FilterOperator.EQUAL;

import java.util.ArrayList;
import java.util.List;

import org.heiduc.api.datastore.Query;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.ContentPermissionDao;
import com.heiduc.entity.ContentPermissionEntity;

/**
 * @author Alexander Oleynik
 */
public class ContentPermissionDaoImpl extends 
		BaseDaoImpl<ContentPermissionEntity> 
		implements ContentPermissionDao {

	public ContentPermissionDaoImpl() {
		super(ContentPermissionEntity.class);
	}

	@Override
	public List<ContentPermissionEntity> selectByUrl(final String url) {
		Query q = newQuery();
		q.addFilter("url", EQUAL, url);
		return select(q, "selectByUrl", params(url));
	}

	@Override
	public ContentPermissionEntity getByUrlGroup(final String url, 
			final Long groupId) {
		Query q = newQuery();
		q.addFilter("url", EQUAL, url);
		q.addFilter("groupId", EQUAL, groupId);
		return selectOne(q, "getByUrlGroup", params(url, groupId));
	}

	private List<ContentPermissionEntity> selectByGroup(final Long groupId) {
		Query q = newQuery();
		q.addFilter("groupId", EQUAL, groupId);
		return select(q, "selectByGroup", params(groupId));
	}
		
	@Override
	public void removeByGroup(List<Long> groupIds) {
		for (Long groupId : groupIds) {
			List<ContentPermissionEntity> list = selectByGroup(groupId);
			remove(getIds(list));
		}
	}

	private List<Long> getIds(List<ContentPermissionEntity> list) {
		List<Long> result = new ArrayList<Long>();
		for (ContentPermissionEntity e : list) {
			result.add(e.getId());
		}
		return result;
	}
	
}

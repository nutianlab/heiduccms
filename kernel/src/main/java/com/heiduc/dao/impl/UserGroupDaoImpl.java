

package com.heiduc.dao.impl;

import static org.heiduc.api.datastore.Query.FilterOperator.EQUAL;

import java.util.ArrayList;
import java.util.List;

import org.heiduc.api.datastore.Query;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.UserGroupDao;
import com.heiduc.entity.UserGroupEntity;

/**
 * @author Alexander Oleynik
 */
public class UserGroupDaoImpl extends BaseDaoImpl<UserGroupEntity> 
		implements UserGroupDao {

	public UserGroupDaoImpl() {
		super(UserGroupEntity.class);
	}

	@Override
	public List<UserGroupEntity> selectByUser(Long userId) {
		Query q = newQuery();
		q.addFilter("userId", EQUAL, userId);
		return select(q, "selectByUser", params(userId));
	}

	@Override
	public List<UserGroupEntity> selectByGroup(Long groupId) {
		Query q = newQuery();
		q.addFilter("groupId", EQUAL, groupId);
		return select(q, "selectByGroup", params(groupId));
	}

	@Override
	public UserGroupEntity getByUserGroup(Long groupId, Long userId) {
		Query q = newQuery();
		q.addFilter("userId", EQUAL, userId);
		q.addFilter("groupId", EQUAL, groupId);
		return selectOne(q, "getByUserGroup", params(groupId, userId));
	}

	@Override
	public void removeByGroup(List<Long> groupIds) {
		for (Long groupId : groupIds) {
			List<UserGroupEntity> list = selectByGroup(groupId);
		    remove(getIds(list));	
		}
	}

	private List<Long> getIds(List<UserGroupEntity> list) {
		List<Long> result = new ArrayList<Long>();
		for (UserGroupEntity e : list) {
			result.add(e.getId());
		}
		return result;
	}
	
	@Override
	public void removeByUser(List<Long> userIds) {
		for (Long userId : userIds) {
			List<UserGroupEntity> list = selectByUser(userId);
		    remove(getIds(list));	
		}
	}
	
}

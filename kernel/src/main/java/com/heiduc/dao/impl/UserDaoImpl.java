

package com.heiduc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.heiduc.api.datastore.Query;


import com.heiduc.common.HeiducContext;
import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.UserDao;
import com.heiduc.dao.UserGroupDao;
import com.heiduc.entity.UserEntity;
import com.heiduc.entity.UserGroupEntity;
import com.heiduc.enums.UserRole;

import static org.heiduc.api.datastore.Query.FilterOperator.*;

public class UserDaoImpl extends BaseDaoImpl<UserEntity> 
		implements UserDao {

	public UserDaoImpl() {
		super(UserEntity.class);
	}

	public UserEntity getByEmail(final String email) {
		Query q = newQuery();
		q.addFilter("email", EQUAL, email);
		return selectOne(q, "getByEmail", params(email));
	}
	
	public UserEntity getByName(final String name) {
		Query q = newQuery();
		q.addFilter("name", EQUAL, name);
		return selectOne(q, "getByName", params(name));
	}

	public List<UserEntity> getByRole(final UserRole role) {
		Query q = newQuery();
		q.addFilter("role", EQUAL, role.name());
		return select(q, "getByRole", params(role));
	}

	@Override
	public List<UserEntity> selectByGroup(final Long groupId) {
		List<UserGroupEntity> users = getUserGroupDao().selectByGroup(groupId);
		List<UserEntity> result = new ArrayList<UserEntity>();
		for (UserGroupEntity userGroup : users) {
			UserEntity user = getById(userGroup.getUserId());
			if (user != null) {
				result.add(user);
			}
		}
		return result;
	}

	private UserGroupDao getUserGroupDao() {
		return HeiducContext.getInstance().getBusiness().getDao()
				.getUserGroupDao();
	}

	@Override
	public UserEntity getByKey(String key) {
		if (key == null) {
			return null;
		}
		Query q = newQuery();
		q.addFilter("forgotPasswordKey", EQUAL, key);
		return selectOne(q, "getByKey", params(key));
	}

}

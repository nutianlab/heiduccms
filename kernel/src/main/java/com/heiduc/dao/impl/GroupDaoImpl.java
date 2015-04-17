

package com.heiduc.dao.impl;

import static org.heiduc.api.datastore.Query.FilterOperator.EQUAL;

import org.heiduc.api.datastore.Query;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.GroupDao;
import com.heiduc.entity.GroupEntity;

/**
 * @author Alexander Oleynik
 */
public class GroupDaoImpl extends BaseDaoImpl<GroupEntity> 
		implements GroupDao {

	public GroupDaoImpl() {
		super(GroupEntity.class);
	}

	@Override
	public GroupEntity getByName(String name) {
		Query q = newQuery();
		q.addFilter("name", EQUAL, name);
		return selectOne(q, "getByName", params(name));
	}

	@Override
	public GroupEntity getGuestsGroup() {
		return getByName("guests");
	}

}

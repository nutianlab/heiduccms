

package com.heiduc.dao.impl;

import static org.heiduc.api.datastore.Query.FilterOperator.EQUAL;

import org.heiduc.api.datastore.Query;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.FormDao;
import com.heiduc.entity.FormEntity;

public class FormDaoImpl extends BaseDaoImpl<FormEntity> 
		implements FormDao {

	public FormDaoImpl() {
		super(FormEntity.class);
	}

	@Override
	public FormEntity getByName(final String name) {
		Query q = newQuery();
		q.addFilter("name", EQUAL, name);
		return selectOne(q, "getByName", params(name));
	}

}

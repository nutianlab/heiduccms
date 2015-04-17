

package com.heiduc.dao.impl;


import org.heiduc.api.datastore.Query;
import org.heiduc.api.datastore.Query.FilterOperator;

import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.SeoUrlDao;
import com.heiduc.entity.SeoUrlEntity;

public class SeoUrlDaoImpl extends BaseDaoImpl<SeoUrlEntity> 
		implements SeoUrlDao {

	public SeoUrlDaoImpl() {
		super(SeoUrlEntity.class);
	}

	public SeoUrlEntity getByFrom(final String from) {
		Query q = newQuery();
		q.addFilter("fromLink", FilterOperator.EQUAL, from);
		return selectOne(q, "getByFrom", params(from));
	}

}

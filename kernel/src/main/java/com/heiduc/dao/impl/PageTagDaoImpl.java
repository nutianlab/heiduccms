

package com.heiduc.dao.impl;

import static org.heiduc.api.datastore.Query.FilterOperator.EQUAL;

import org.heiduc.api.datastore.Query;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.PageTagDao;
import com.heiduc.entity.PageTagEntity;

public class PageTagDaoImpl extends BaseDaoImpl<PageTagEntity> 
		implements PageTagDao {

	public PageTagDaoImpl() {
		super(PageTagEntity.class);
	}

	@Override
	public PageTagEntity getByURL(final String url) {
		Query q = newQuery();
		q.addFilter("pageURL", EQUAL, url);
		return selectOne(q, "getByURL", params(url));
	}

}

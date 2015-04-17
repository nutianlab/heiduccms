

package com.heiduc.dao.impl;

import static org.heiduc.api.datastore.Query.FilterOperator.EQUAL;

import java.util.List;

import org.heiduc.api.datastore.Query;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.PageDependencyDao;
import com.heiduc.entity.PageDependencyEntity;

/**
 * @author Alexander Oleynik
 */
public class PageDependencyDaoImpl extends BaseDaoImpl<PageDependencyEntity> 
		implements PageDependencyDao {

	public PageDependencyDaoImpl() {
		super(PageDependencyEntity.class);
	}

	@Override
	public List<PageDependencyEntity> selectByPage(final String pageUrl) {
		Query q = newQuery();
		q.addFilter("page", EQUAL, pageUrl);
		return select(q, "getByPage", params(pageUrl));
	}
	
	@Override
	public List<PageDependencyEntity> selectByDependency(final String pageUrl) {
		Query q = newQuery();
		q.addFilter("dependency", EQUAL, pageUrl);
		return select(q, "getByDependency", params(pageUrl));
	}

	@Override
	public PageDependencyEntity getByPageAndDependency(String pageUrl,
			String dependency) {
		Query q = newQuery();
		q.addFilter("page", EQUAL, pageUrl);
		q.addFilter("dependency", EQUAL, dependency);
		return selectOne(q, "getByPageAndDependency", params(pageUrl, dependency));
	}
	
}

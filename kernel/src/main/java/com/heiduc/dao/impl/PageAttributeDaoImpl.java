

package com.heiduc.dao.impl;

import static org.heiduc.api.datastore.Query.FilterOperator.EQUAL;

import java.util.List;

import org.heiduc.api.datastore.Query;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.PageAttributeDao;
import com.heiduc.entity.PageAttributeEntity;

/**
 * @author Alexander Oleynik
 */
public class PageAttributeDaoImpl extends BaseDaoImpl<PageAttributeEntity> 
		implements PageAttributeDao {

	public PageAttributeDaoImpl() {
		super(PageAttributeEntity.class);
	}

	@Override
	public List<PageAttributeEntity> getByPage(final String pageUrl) {
		return getByPage(pageUrl, null);
	}
	
	@Override
	public List<PageAttributeEntity> getByPageInherited(final String pageUrl) {
		return getByPage(pageUrl, true);
	}

	private List<PageAttributeEntity> getByPage(final String pageUrl, 
			Boolean inherited) {
		Query q = newQuery();
		q.addFilter("pageUrl", EQUAL, pageUrl);
		if (inherited != null) {
			q.addFilter("inherited", EQUAL, inherited);
		}
		return select(q, "getByPage", params(pageUrl, inherited));
	}

	@Override
	public void removeByPage(String url) {
		Query q = newQuery();
		q.addFilter("pageUrl", EQUAL, url);
		removeSelected(q);
	}

	@Override
	public PageAttributeEntity getByPageName(String pageUrl, String name) {
		Query q = newQuery();
		q.addFilter("pageUrl", EQUAL, pageUrl);
		q.addFilter("name", EQUAL, name);
		return selectOne(q, "getByPageName", params(pageUrl, name));
	}
	
}

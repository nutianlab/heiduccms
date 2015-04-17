

package com.heiduc.dao.impl;


import org.heiduc.api.datastore.Query;
import org.heiduc.api.datastore.Query.FilterOperator;

import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.TemplateDao;
import com.heiduc.entity.TemplateEntity;

public class TemplateDaoImpl extends BaseDaoImpl<TemplateEntity> 
		implements TemplateDao {

	public TemplateDaoImpl() {
		super(TemplateEntity.class);
	}

	public TemplateEntity getByUrl(final String url) {
		Query q = newQuery();
		q.addFilter("url", FilterOperator.EQUAL, url);
		return selectOne(q, "getByUrl", params(url));
	}
	
}

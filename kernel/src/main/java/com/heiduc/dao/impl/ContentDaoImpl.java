

package com.heiduc.dao.impl;

import java.util.List;

import org.heiduc.api.datastore.Query;
import org.heiduc.api.datastore.Query.FilterOperator;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.ContentDao;
import com.heiduc.entity.ContentEntity;

public class ContentDaoImpl extends BaseDaoImpl<ContentEntity> 
		implements ContentDao {

	public ContentDaoImpl() {
		super(ContentEntity.class);
	}

	@Override
	public List<ContentEntity> select(final String parentClass, 
			final Long parentKey) {
		Query q = newQuery();
		q.addFilter("parentClass", FilterOperator.EQUAL, parentClass);
		q.addFilter("parentKey", FilterOperator.EQUAL, parentKey);
		return select(q, "select", params(parentClass, parentKey));
	}
	
	@Override
	public ContentEntity getByLanguage(final String parentClass, 
			final Long parentKey, final String language) {
		Query q = newQuery();
		q.addFilter("parentClass", FilterOperator.EQUAL, parentClass);
		q.addFilter("parentKey", FilterOperator.EQUAL, parentKey);
		q.addFilter("languageCode", FilterOperator.EQUAL, language);
		return selectOne(q, "getByLanguage", params(parentClass, parentKey, 
				language));
	}

	@Override
	public void removeById(String className, Long id) {
		Query q = newQuery();
		q.addFilter("parentClass", FilterOperator.EQUAL, className);
		q.addFilter("parentKey", FilterOperator.EQUAL, id);
		removeSelected(q);
	}
	
}

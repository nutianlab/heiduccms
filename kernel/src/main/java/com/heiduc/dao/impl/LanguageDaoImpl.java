

package com.heiduc.dao.impl;


import org.heiduc.api.datastore.Query;
import org.heiduc.api.datastore.Query.FilterOperator;

import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.LanguageDao;
import com.heiduc.entity.LanguageEntity;

public class LanguageDaoImpl extends BaseDaoImpl<LanguageEntity> 
		implements LanguageDao {

	public LanguageDaoImpl() {
		super(LanguageEntity.class);
	}

	public LanguageEntity getByCode(final String code) {
		Query q = newQuery();
		q.addFilter("code", FilterOperator.EQUAL, code);
		return selectOne(q, "getByCode", params(code));
	}

}

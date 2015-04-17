

package com.heiduc.dao.impl;

import java.util.Collections;
import java.util.List;

import org.heiduc.api.datastore.Query;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.FieldDao;
import com.heiduc.entity.FieldEntity;
import com.heiduc.entity.FormEntity;
import com.heiduc.entity.helper.FieldHelper;

import static org.heiduc.api.datastore.Query.FilterOperator.*;

public class FieldDaoImpl extends BaseDaoImpl<FieldEntity> 
		implements FieldDao {

	public FieldDaoImpl() {
		super(FieldEntity.class);
	}

	@Override
	public List<FieldEntity> getByForm(final FormEntity form) {
		Query q = newQuery();
		q.addFilter("formId", EQUAL, form.getId());
		List<FieldEntity> result = select(q, "getByForm", params(form.getId()));
		Collections.sort(result, new FieldHelper.IndexAsc());
		return result;
	}
	
	@Override
	public FieldEntity getByName(final FormEntity form, final String name) {
		Query q = newQuery();
		q.addFilter("formId", EQUAL, form.getId());
		q.addFilter("name", EQUAL, name);
		return selectOne(q, "getByName", params(form.getId(), name));
	}
	
}

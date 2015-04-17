

package com.heiduc.dao.impl;

import static org.heiduc.api.datastore.Query.FilterOperator.EQUAL;

import java.util.List;

import org.heiduc.api.datastore.Query;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.FormDataDao;
import com.heiduc.entity.FormDataEntity;
import com.heiduc.entity.FormEntity;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class FormDataDaoImpl extends BaseDaoImpl<FormDataEntity> 
		implements FormDataDao {

	public FormDataDaoImpl() {
		super(FormDataEntity.class);
	}

	@Override
	public List<FormDataEntity> getByForm(final FormEntity form) {
		Query q = newQuery();
		q.addFilter("formId", EQUAL, form.getId());
		return select(q, "getByForm", params(form.getId()));
	}
	
	
}

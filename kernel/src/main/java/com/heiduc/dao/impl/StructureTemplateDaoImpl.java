

package com.heiduc.dao.impl;

import java.util.List;

import org.heiduc.api.datastore.Query;
import org.heiduc.api.datastore.Query.FilterOperator;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.StructureTemplateDao;
import com.heiduc.entity.StructureTemplateEntity;

/**
 * @author Alexander Oleynik
 */
public class StructureTemplateDaoImpl 
		extends BaseDaoImpl<StructureTemplateEntity> 
		implements StructureTemplateDao {

	public StructureTemplateDaoImpl() {
		super(StructureTemplateEntity.class);
	}

	@Override
	public List<StructureTemplateEntity> selectByStructure(Long structureId) {
		Query q = newQuery();
		q.addFilter("structureId", FilterOperator.EQUAL, structureId);
		return select(q, "selectByStructure", params(structureId));
	}

	@Override
	public StructureTemplateEntity getByName(String name) {
		Query q = newQuery();
		q.addFilter("name", FilterOperator.EQUAL, name);
		return selectOne(q, "getByName", params(name));
	}

	@Override
	public StructureTemplateEntity getByTitle(String title) {
		Query q = newQuery();
		q.addFilter("title", FilterOperator.EQUAL, title);
		return selectOne(q, "getByTitle", params(title));
	}
}

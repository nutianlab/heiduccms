

package com.heiduc.dao.impl;

import java.util.List;

import org.heiduc.api.datastore.Query;
import org.heiduc.api.datastore.Query.FilterOperator;


import com.heiduc.common.HeiducContext;
import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.StructureDao;
import com.heiduc.dao.StructureTemplateDao;
import com.heiduc.entity.StructureEntity;
import com.heiduc.entity.helper.StructureTemplateHelper;

/**
 * @author Alexander Oleynik
 */
public class StructureDaoImpl extends BaseDaoImpl<StructureEntity> 
		implements StructureDao {

	public StructureDaoImpl() {
		super(StructureEntity.class);
	}

	public StructureTemplateDao getStructureTemplateDao() {
		return HeiducContext.getInstance().getBusiness().getDao()
				.getStructureTemplateDao();
	}
	
	@Override
	public StructureEntity getByTitle(String title) {
		Query q = newQuery();
		q.addFilter("title", FilterOperator.EQUAL, title);
		return selectOne(q, "getByTitle", params(title));
	}
	
	@Override
	public void remove(Long id) {
		List<Long> structureTemplateIds = StructureTemplateHelper.createIdList(
				getStructureTemplateDao().selectByStructure(id));
		getStructureTemplateDao().remove(structureTemplateIds);
		super.remove(id);
	}

	@Override
	public void remove(List<Long> ids) {
		for (Long id : ids) {
			remove(id);
		}
	}

}

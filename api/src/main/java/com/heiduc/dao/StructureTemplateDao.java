

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.StructureTemplateEntity;

/**
 * @author Alexander Oleynik
 */
public interface StructureTemplateDao extends 
		BaseDao<StructureTemplateEntity> {

	List<StructureTemplateEntity> selectByStructure(final Long structureId);

	StructureTemplateEntity getByName(final String name);

	StructureTemplateEntity getByTitle(final String title);
	
}

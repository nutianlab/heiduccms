

package com.heiduc.dao;

import com.heiduc.entity.StructureEntity;

/**
 * @author Alexander Oleynik
 */
public interface StructureDao extends BaseDao<StructureEntity> {

	StructureEntity getByTitle(final String title);
	
}

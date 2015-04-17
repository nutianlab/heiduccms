

package com.heiduc.business;

import java.util.List;

import com.heiduc.entity.StructureEntity;

/**
 * @author Alexander Oleynik
 */
public interface StructureBusiness {

	List<String> validateBeforeUpdate(final StructureEntity entity);
	
	/**
	 * Delete structures by id with reference integrity check.
	 * @param ids
	 * @return list of reference integrity check messages.
	 */
	List<String> remove(List<Long> ids);
}

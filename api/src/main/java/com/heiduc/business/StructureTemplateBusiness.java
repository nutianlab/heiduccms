

package com.heiduc.business;

import java.util.List;

import com.heiduc.entity.StructureTemplateEntity;
import com.heiduc.entity.TemplateEntity;

/**
 * @author Alexander Oleynik
 */
public interface StructureTemplateBusiness {

	List<String> validateBeforeUpdate(final StructureTemplateEntity entity);

	/**
	 * Delete structure templates by id with reference integrity check.
	 * @param ids
	 * @return list of reference integrity check messages.
	 */
	List<String> remove(List<Long> ids);
	
	StructureTemplateEntity save(StructureTemplateEntity template);

}

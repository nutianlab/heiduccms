

package com.heiduc.business;

import java.util.List;

import com.heiduc.entity.TemplateEntity;

public interface TemplateBusiness {

	List<String> validateBeforeUpdate(final TemplateEntity template);
	
	/**
	 * Delete templates by id with reference integrity check.
	 * @param ids
	 * @return list of reference integrity check messages.
	 */
	List<String> remove(List<Long> ids);
	
	public TemplateEntity save(TemplateEntity template);

}

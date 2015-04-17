

package com.heiduc.velocity;

import java.util.List;

import com.heiduc.entity.FormDataEntity;

public interface FormVelocityService {

	/**
	 * Render form to be places on the page o template.
	 * @param formName - unique form name.
	 * @return rendered html. 
	 */
	String render(final String formName);
	
	/**
	 * Find form data stored in db. Ordered by modDate DESC
	 * @param formName - form name.
	 * @return - found form data list.
	 */
	List<FormDataEntity> findData(String formName);
}

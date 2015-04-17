

package com.heiduc.business;

import java.util.List;
import java.util.Map;

import com.heiduc.entity.FieldEntity;
import com.heiduc.entity.FormEntity;

public interface FieldBusiness {

	List<String> validateBeforeUpdate(final FieldEntity entity);
	
	/**
	 * Convert from JSON field value object.
	 * @param entity - field entity will be filled by vo's values.
	 * @param vo - from JSON vo map object.
	 * @return - list of errors.
	 */
	List<String> convertFromVO(FieldEntity entity, final Map<String, String> vo);
	
	/**
	 * Check fields order and set index field if required.
	 * @param form
	 * @return form fields ordered by index.
	 */
	List<FieldEntity> checkOrder(final FormEntity form);
}

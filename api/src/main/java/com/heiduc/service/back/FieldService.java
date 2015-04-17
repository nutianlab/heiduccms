

package com.heiduc.service.back;

import java.util.List;
import java.util.Map;

import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.vo.FieldVO;


public interface FieldService extends AbstractService {
	
	ServiceResponse updateField(Map<String, String> field);
	
	List<FieldVO> getByForm(final Long formId);
	
	FieldVO getById(final Long fieldId);
	
	ServiceResponse remove(final List<String> ids);
	
	void moveUp(final Long formId, final Long fieldId);

	void moveDown(final Long formId, final Long fieldId);
}

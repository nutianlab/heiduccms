

package com.heiduc.service.back;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.heiduc.entity.FormConfigEntity;
import com.heiduc.entity.FormDataEntity;
import com.heiduc.entity.FormEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;


public interface FormService extends AbstractService {
	
	ServiceResponse saveForm(Map<String, String> vo);
	
	FormEntity getForm(final Long formId);
	
	List<FormEntity> select();
	
	ServiceResponse deleteForm(final List<String> ids);
	
	FormConfigEntity getFormConfig();
	
	ServiceResponse saveFormConfig(final Map<String, String> vo);

	ServiceResponse restoreFormTemplate() throws IOException;
	
	ServiceResponse restoreFormLetter() throws IOException;
	
	ServiceResponse removeData(List<String> ids);
	
	List<FormDataEntity> getFormData(Long formId);

	ServiceResponse sendFormLetter(Long formDataId);

}

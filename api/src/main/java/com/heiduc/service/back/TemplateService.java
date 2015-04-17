

package com.heiduc.service.back;

import java.util.List;
import java.util.Map;

import com.heiduc.entity.TemplateEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;

public interface TemplateService extends AbstractService {
	
	ServiceResponse updateContent(final Long templateId, final String content);
	
	List<TemplateEntity> getTemplates();

	ServiceResponse deleteTemplates(final List<String> ids);

	TemplateEntity getTemplate(final Long id);
	
	ServiceResponse saveTemplate(final Map<String, String> templateMap);
	
}

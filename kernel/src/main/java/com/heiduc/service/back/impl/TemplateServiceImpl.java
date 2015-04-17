

package com.heiduc.service.back.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.entity.TemplateEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.TemplateService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.utils.StrUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class TemplateServiceImpl extends AbstractServiceImpl 
		implements TemplateService {

	@Override
	public ServiceResponse updateContent(Long templateId, String content) {
		TemplateEntity template = getDao().getTemplateDao().getById(templateId);
		if (template != null) {
			template.setContent(content);
			getBusiness().getTemplateBusiness().save(template);
			return ServiceResponse.createSuccessResponse(
					Messages.get("template.success_save"));
		}
		else {
			return ServiceResponse.createErrorResponse(
					Messages.get("template.not_found", templateId));
		}
	}

	@Override
	public List<TemplateEntity> getTemplates() {
		return getDao().getTemplateDao().select();
	}

	@Override
	public ServiceResponse deleteTemplates(List<String> ids) {
		List<String> errors = getBusiness().getTemplateBusiness().remove(
				StrUtil.toLong(ids));
		if (errors.isEmpty()) {
			return ServiceResponse.createSuccessResponse(
					Messages.get("templates.success_delete"));
		}
		else {
			return ServiceResponse.createErrorResponse(
					Messages.get("errors_occured"), errors);
			
		}
	}

	@Override
	public TemplateEntity getTemplate(Long id) {
		return getDao().getTemplateDao().getById(id);
	}

	@Override
	public ServiceResponse saveTemplate(Map<String, String> vo) {
		TemplateEntity template = null;
		if (!StringUtils.isEmpty(vo.get("id"))) {
			template = getDao().getTemplateDao().getById(Long.valueOf(
					vo.get("id")));
		}
		if (template == null) {
			template = new TemplateEntity();
		}
		template.setTitle(vo.get("title"));
		template.setUrl(vo.get("url"));
		template.setContent(vo.get("content"));
		List<String> errors = getBusiness().getTemplateBusiness()
			.validateBeforeUpdate(template);
		if (errors.isEmpty()) {
			getBusiness().getTemplateBusiness().save(template);
			return ServiceResponse.createSuccessResponse(template.getId()
					.toString());
		}
		else {
			return ServiceResponse.createErrorResponse(
					Messages.get("errors_occured"), errors);			
		}
	}

}



package com.heiduc.service.back.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.impl.SetupBeanImpl;
import com.heiduc.entity.FormConfigEntity;
import com.heiduc.entity.FormDataEntity;
import com.heiduc.entity.FormEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.FormService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.utils.StrUtil;
import com.heiduc.utils.StreamUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class FormServiceImpl extends AbstractServiceImpl 
		implements FormService {

	@Override
	public FormEntity getForm(Long formId) {
		return getDao().getFormDao().getById(formId);
	}

	@Override
	public ServiceResponse saveForm(Map<String, String> vo) {
		FormEntity form = null;
		if (!StringUtils.isEmpty(vo.get("id"))) {
			form = getDao().getFormDao().getById(Long.valueOf(vo.get("id")));
		}
		if (form == null) {
			form = new FormEntity();
		}
		form.setTitle(vo.get("title"));
		form.setName(vo.get("name"));
		form.setEmail(vo.get("email"));
		form.setLetterSubject(vo.get("letterSubject"));
		form.setResetButtonTitle(vo.get("resetButtonTitle"));
		form.setSendButtonTitle(vo.get("sendButtonTitle"));
		form.setShowResetButton(Boolean.valueOf(vo.get("showResetButton")));
		form.setEnableCaptcha(Boolean.valueOf(vo.get("enableCaptcha")));
		form.setEnableSave(Boolean.valueOf(vo.get("enableSave")));
		List<String> errors = getBusiness().getFormBusiness()
			.validateBeforeUpdate(form);
		if (errors.isEmpty()) {
			getDao().getFormDao().save(form);
			return ServiceResponse.createSuccessResponse(form.getId().toString());
		}
		else {
			return ServiceResponse.createErrorResponse(
					Messages.get("errors_occured"), errors);
		}
	}

	@Override
	public List<FormEntity> select() {
		return getDao().getFormDao().select();
	}

	@Override
	public ServiceResponse deleteForm(List<String> ids) {
		getDao().getFormDao().remove(StrUtil.toLong(ids));
		return ServiceResponse.createSuccessResponse(
				Messages.get("form.success_delete"));
	}

	@Override
	public FormConfigEntity getFormConfig() {
		return getDao().getFormConfigDao().getConfig();
	}

	@Override
	public ServiceResponse saveFormConfig(Map<String, String> vo) {
		FormConfigEntity config = getDao().getFormConfigDao().getConfig();
		config.setFormTemplate(vo.get("formTemplate"));
		config.setLetterTemplate(vo.get("letterTemplate"));
		getDao().getFormConfigDao().save(config);
		return ServiceResponse.createSuccessResponse(
				Messages.get("form.config_success_save"));
	}

	@Override
	public ServiceResponse restoreFormLetter() throws IOException {
		FormConfigEntity config = getDao().getFormConfigDao().getConfig();
		config.setLetterTemplate(StreamUtil.getTextResource(
			SetupBeanImpl.FORM_LETTER_FILE));
		getDao().getFormConfigDao().save(config);			
		return ServiceResponse.createSuccessResponse(
				Messages.get("form.letter_success_restore"));
	}

	@Override
	public ServiceResponse restoreFormTemplate() throws IOException {
		FormConfigEntity config = getDao().getFormConfigDao().getConfig();
		config.setFormTemplate(StreamUtil.getTextResource(
			SetupBeanImpl.FORM_TEMPLATE_FILE));
		getDao().getFormConfigDao().save(config);			
		return ServiceResponse.createSuccessResponse(
				Messages.get("form.template_success_restore"));
	}

	@Override
	public ServiceResponse removeData(List<String> ids) {
		getDao().getFormDataDao().remove(StrUtil.toLong(ids));
		return ServiceResponse.createSuccessResponse(
				Messages.get("success"));

	}

	@Override
	public List<FormDataEntity> getFormData(Long formId) {
		FormEntity form = getDao().getFormDao().getById(formId);
		if (form == null) {
			return null;
		}
		return getDao().getFormDataDao().getByForm(form);
	}

	@Override
	public ServiceResponse sendFormLetter(Long formDataId) {
		FormDataEntity formData = getDao().getFormDataDao().getById(
				formDataId);
		if (formData == null) {
			return ServiceResponse.createErrorResponse(Messages.get("not_found"));
		}
		FormEntity form = getDao().getFormDao().getById(formData.getFormId());
		String error = getBusiness().getFormBusiness().sendEmail(formData);
		if (error != null) {
			return ServiceResponse.createErrorResponse(error);
		}
		return ServiceResponse.createSuccessResponse(Messages.get(
				"form.success_send", form.getEmail()));
	}

}

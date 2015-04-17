

package com.heiduc.service.back.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.entity.LanguageEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.LanguageService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.utils.StrUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class LanguageServiceImpl extends AbstractServiceImpl 
		implements LanguageService {

	@Override
	public List<LanguageEntity> select() {
		return getDao().getLanguageDao().select();
	}

	@Override
	public ServiceResponse remove(List<String> ids) {
		getDao().getLanguageDao().remove(StrUtil.toLong(ids));
		return ServiceResponse.createSuccessResponse(
				Messages.get("languages.success_delete"));
	}

	@Override
	public LanguageEntity getById(Long id) {
		return getDao().getLanguageDao().getById(id);
	}

	private List<String> validate(LanguageEntity entity) {
		List<String> errors = new ArrayList<String>();
		LanguageEntity found = getDao().getLanguageDao().getByCode(
				entity.getCode());
		if (found != null && !found.getId().equals(entity.getId())) {
				errors.add(Messages.get("language.code_registered"));
		}
		return errors;
	}
	@Override
	
	public ServiceResponse save(Map<String, String> vo) {
		LanguageEntity language = null;
		if (!StringUtils.isEmpty(vo.get("id"))) {
			language = getDao().getLanguageDao().getById(Long.valueOf(
					vo.get("id")));
		}
		if (language == null) {
			language = new LanguageEntity();
		}
		language.setCode(vo.get("code"));
		language.setTitle(vo.get("title"));
		List<String> errors = validate(language);
		if (errors.isEmpty()) {
			getDao().getLanguageDao().save(language);
			return ServiceResponse.createSuccessResponse(
						Messages.get("language.success_save"));
		}
		else {
			return ServiceResponse.createErrorResponse(
					Messages.get("errors_occured"), errors);
		}
	}

}

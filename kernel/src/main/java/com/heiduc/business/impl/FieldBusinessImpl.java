

package com.heiduc.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.FieldBusiness;
import com.heiduc.entity.FieldEntity;
import com.heiduc.entity.FormEntity;
import com.heiduc.enums.FieldType;
import com.heiduc.i18n.Messages;
import com.heiduc.utils.ParamUtil;

public class FieldBusinessImpl extends AbstractBusinessImpl 
	implements FieldBusiness {

	@Override
	public List<String> validateBeforeUpdate(FieldEntity field) {
		List<String> errors = new ArrayList<String>();
		FormEntity form = getDao().getFormDao().getById(field.getFormId());
		if (form == null) {
			errors.add(Messages.get("form_not_found", field.getFormId()));
		}
		else {		
			if (field.getId() == null) {
				FieldEntity myField = getDao().getFieldDao().getByName(
						form, field.getName());
				if (myField != null) {
					errors.add(Messages.get("form.field_already_exists"));
				}
			}
		}
		if (StringUtils.isEmpty(field.getName())) {
			errors.add(Messages.get("name_is_empty"));
		}
		if (StringUtils.isEmpty(field.getTitle())) {
			errors.add(Messages.get("title_is_empty"));
		}
		return errors;
	}

	@Override
	public List<String> convertFromVO(FieldEntity field, 
			Map<String, String> vo) {
		List<String> errors = new ArrayList<String>();
		try {
			field.setId(ParamUtil.getLong(vo.get("id"), null));
			field.setFormId(Long.valueOf(vo.get("formId")));
			field.setName(vo.get("name"));
			field.setTitle(vo.get("title"));
			field.setFieldType(FieldType.valueOf(vo.get("fieldType")));
			field.setMandatory(Boolean.valueOf(vo.get("mandatory")));
			field.setDefaultValue(vo.get("defaultValue"));
			field.setValues(vo.get("values"));
			field.setRegex(vo.get("regex"));
			field.setRegexMessage(vo.get("regexMessage"));
			field.setHeight(Integer.valueOf(vo.get("height")));
			field.setWidth(Integer.valueOf(vo.get("width")));
			field.setIndex(Integer.valueOf(vo.get("index")));
		}
		catch (Exception e) {
			errors.add(e.getMessage());
		}
		return errors;
	}

	@Override
	public List<FieldEntity> checkOrder(FormEntity form) {
		List<FieldEntity> fields = getDao().getFieldDao().getByForm(form);
		for (int i = 0; i < fields.size(); i++) {
			FieldEntity field = fields.get(i);
			if (i != field.getIndex()) {
				field.setIndex(i);
				getDao().getFieldDao().save(field);
			}
		}
		return getDao().getFieldDao().getByForm(form);
	}
	
}

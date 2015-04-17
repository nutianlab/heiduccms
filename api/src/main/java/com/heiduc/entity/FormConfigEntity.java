

package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.getTextProperty;
import static com.heiduc.utils.EntityUtil.setTextProperty;

import org.heiduc.api.datastore.Entity;


public class FormConfigEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 2L;

	public static final String FORM_TEMPLATE = "formTemplate";
	public static final String LETTER_TEMPLATE = "letterTemplate";

	private String formTemplate;
	private String letterTemplate;

	public FormConfigEntity() {
		setFormTemplate("");
		setLetterTemplate("");
	}
	
	@Override
	public void load(Entity entity) {
		super.load(entity);
		formTemplate = getTextProperty(entity, "formTemplate");
		letterTemplate = getTextProperty(entity, "letterTemplate");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setTextProperty(entity, "formTemplate", formTemplate);
		setTextProperty(entity, "letterTemplate", letterTemplate);
	}

	public String getFormTemplate() {
		return formTemplate;
	}

	public void setFormTemplate(String formTemplate) {
		this.formTemplate = formTemplate;
	}

	public String getLetterTemplate() {
		return letterTemplate;
	}

	public void setLetterTemplate(String letterTemplate) {
		this.letterTemplate = letterTemplate;
	}
	
}

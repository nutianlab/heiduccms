

package com.heiduc.entity;

import org.heiduc.api.datastore.Entity;

import static com.heiduc.utils.EntityUtil.*;

public class LanguageEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 2L;

	public static final String ENGLISH_CODE = "en";
	public static final String ENGLISH_TITLE = "English";
	
	private String code;
	private String title;

	public LanguageEntity() {
		code = "";
		title = "";
	}

	@Override
	public void load(Entity entity) {
		super.load(entity);
		code = getStringProperty(entity, "code");
		title = getStringProperty(entity, "title");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "code", code, true);
		setProperty(entity, "title", title, false);
	}

	public LanguageEntity(final String code, final String title) {
		this.code = code;
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}



package com.heiduc.entity;

import org.heiduc.api.datastore.Entity;

import static com.heiduc.utils.EntityUtil.*;

/**
 * @author Alexander Oleynik
 */
public class MessageEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 2L;

	private String code;
	private String languageCode;
	private String value;

	public MessageEntity() {
	}

	@Override
	public void load(Entity entity) {
		super.load(entity);
		code = getStringProperty(entity, "code");
		languageCode = getStringProperty(entity, "languageCode");
		value = getStringProperty(entity, "value");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "code", code, true);
		setProperty(entity, "languageCode", languageCode, true);
		setProperty(entity, "value", value, false);
	}

	public MessageEntity(final String code, final String languageCode, 
			final String value) {
		this.code = code;
		this.languageCode = languageCode;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}

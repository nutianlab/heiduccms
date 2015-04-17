

package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.getLongProperty;
import static com.heiduc.utils.EntityUtil.getStringProperty;
import static com.heiduc.utils.EntityUtil.getTextProperty;
import static com.heiduc.utils.EntityUtil.setProperty;
import static com.heiduc.utils.EntityUtil.setTextProperty;

import org.heiduc.api.datastore.Entity;


/**
 * @author Alexander Oleynik
 */
public class ContentEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 8L;

	private String parentClass;
	private Long parentKey;
	private String languageCode;
	private String content;
	
	public ContentEntity() {
		content = "";
	}
	
	@Override
	public void load(Entity entity) {
		super.load(entity);
		parentClass = getStringProperty(entity, "parentClass");
		parentKey = getLongProperty(entity, "parentKey");
		languageCode = getStringProperty(entity, "languageCode");
		content = getTextProperty(entity, "content");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "parentClass", parentClass, true);
		setProperty(entity, "parentKey", parentKey, true);
		setProperty(entity, "languageCode", languageCode, true);
		setTextProperty(entity, "content", content);
	}

	public ContentEntity(String parentClass, Long parentKey, 
			String languageCode, String content) {
		this();
		this.parentClass = parentClass;
		this.parentKey = parentKey;
		this.languageCode = languageCode;
		setContent(content);
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getParentClass() {
		return parentClass;
	}

	public void setParentClass(String parentClass) {
		this.parentClass = parentClass;
	}

	public Long getParentKey() {
		return parentKey;
	}

	public void setParentKey(Long parentKey) {
		this.parentKey = parentKey;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	
}

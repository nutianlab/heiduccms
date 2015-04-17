

package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.getBooleanProperty;
import static com.heiduc.utils.EntityUtil.getStringProperty;
import static com.heiduc.utils.EntityUtil.setProperty;

import org.heiduc.api.datastore.Entity;


/**
 * @author Alexander Oleynik
 */
public class PageAttributeEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 8L;

	private String pageUrl;
	private String name;
	private String title;
	private String defaultValue;
	private boolean inherited;

	public String toString() {
		return "PageAttributeEntity(" + pageUrl + ", " + name + ", inherited: " + inherited + ")";
	}
	
	public PageAttributeEntity() {
	}
	
	public PageAttributeEntity(String pageUrl, String name, String title,
			String defaultValue, boolean inherited) {
		super();
		this.pageUrl = pageUrl;
		this.name = name;
		this.title = title;
		this.defaultValue = defaultValue;
		this.inherited = inherited;
	}

	@Override
	public void load(Entity entity) {
		super.load(entity);
		pageUrl = getStringProperty(entity, "pageUrl");
		name = getStringProperty(entity, "name");
		inherited = getBooleanProperty(entity, "inherited", false);
		title = getStringProperty(entity, "title");
		defaultValue = getStringProperty(entity, "defaultValue");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "pageUrl", pageUrl, true);
		setProperty(entity, "name", name, true);
		setProperty(entity, "inherited", inherited, true);
		setProperty(entity, "title", title, false);
		setProperty(entity, "defaultValue", defaultValue, false);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isInherited() {
		return inherited;
	}

	public void setInherited(boolean inherited) {
		this.inherited = inherited;
	}
	
}

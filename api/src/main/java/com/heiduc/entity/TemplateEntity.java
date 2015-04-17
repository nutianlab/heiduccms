

package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.getStringProperty;
import static com.heiduc.utils.EntityUtil.getTextProperty;
import static com.heiduc.utils.EntityUtil.setProperty;
import static com.heiduc.utils.EntityUtil.setTextProperty;

import org.heiduc.api.datastore.Entity;


public class TemplateEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 2L;

	private String title;
	private String url;
	private String content;
	
	public TemplateEntity() {
		content = "";
	}
	
	@Override
	public void load(Entity entity) {
		super.load(entity);
		title = getStringProperty(entity, "title");
		url = getStringProperty(entity, "url");
		content = getTextProperty(entity, "content");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "title", title, false);
		setProperty(entity, "url", url, true);
		setTextProperty(entity, "content", content);
	}

	public TemplateEntity(String title, String content, String url) {
		this(title, content);
		this.url = url;
	}

	public TemplateEntity(String title, String content) {
		this();
		this.title = title;
		this.content = content;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

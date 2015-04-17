

package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.getBooleanProperty;
import static com.heiduc.utils.EntityUtil.getDateProperty;
import static com.heiduc.utils.EntityUtil.getStringProperty;
import static com.heiduc.utils.EntityUtil.getTextProperty;
import static com.heiduc.utils.EntityUtil.setProperty;
import static com.heiduc.utils.EntityUtil.setTextProperty;

import java.util.Date;

import org.heiduc.api.datastore.Entity;


/**
 * @author Alexander Oleynik
 */
public class CommentEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 8L;

	private String pageUrl;
	private String name;
	private String content;
	private Date publishDate;
	private boolean disabled;

	public CommentEntity() {
		publishDate = new Date();
	}
	
	@Override
	public void load(Entity entity) {
		super.load(entity);
		pageUrl = getStringProperty(entity, "pageUrl");
		name = getStringProperty(entity, "name");
		content = getTextProperty(entity, "content");
		publishDate = getDateProperty(entity, "publishDate");
		disabled = getBooleanProperty(entity, "disabled", false);
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "pageUrl", pageUrl, true);
		setProperty(entity, "name", name, false);
		setTextProperty(entity, "content", content);
		setProperty(entity, "publishDate", publishDate, true);
		setProperty(entity, "disabled", disabled, true);
	}

	public CommentEntity(final String aName, final String aContent, 
			final Date aPublishDate, final String aPageUrl) {
		setName(aName);
		setContent(aContent);
		setPublishDate(aPublishDate);
		setPageUrl(aPageUrl);
		setDisabled(false);
	}

	public CommentEntity(final String aName, final String aContent, 
			final Date aPublishDate, final String aPageUrl, 
			final boolean aDisabled) {
		this(aName, aContent, aPublishDate, aPageUrl);
		setDisabled(aDisabled);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
}

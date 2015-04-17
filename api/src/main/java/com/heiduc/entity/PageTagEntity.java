

package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.getListProperty;
import static com.heiduc.utils.EntityUtil.getStringProperty;
import static com.heiduc.utils.EntityUtil.setProperty;

import java.util.ArrayList;
import java.util.List;

import org.heiduc.api.datastore.Entity;


/**
 * @author Alexander Oleynik
 */
public class PageTagEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 1L;

	private String pageURL;
	private List<Long> tags;
	
	public PageTagEntity() {
		tags = new ArrayList<Long>();
	}
	
	@Override
	public void load(Entity entity) {
		super.load(entity);
		pageURL = getStringProperty(entity, "pageURL");
		tags = getListProperty(entity, "tags");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "pageURL", pageURL, true);
		setProperty(entity, "tags", tags);
	}

	public PageTagEntity(String aPageURL) {
		this();
		pageURL = aPageURL;
	}

	public String getPageURL() {
		return pageURL;
	}

	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	public List<Long> getTags() {
		return tags;
	}

	public void setTags(List<Long> tags) {
		this.tags = tags;
	}
	
}

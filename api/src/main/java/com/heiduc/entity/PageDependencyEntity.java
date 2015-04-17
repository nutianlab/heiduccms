

package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.getStringProperty;
import static com.heiduc.utils.EntityUtil.setProperty;

import org.heiduc.api.datastore.Entity;


/**
 * 
 * @author Alexander Oleynik
 *
 */
public class PageDependencyEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 1L;

	private String dependency;
	private String page;

	public PageDependencyEntity() {
		dependency = "";
		page = "";
	}

	@Override
	public void load(Entity entity) {
		super.load(entity);
		dependency = getStringProperty(entity, "dependency");
		page = getStringProperty(entity, "page");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "dependency", dependency, true);
		setProperty(entity, "page", page, true);
	}

	public PageDependencyEntity(String dependency, String pageUrl) {
		setDependency(dependency);
		setPage(pageUrl);
	}

	public String getDependency() {
		return dependency;
	}

	public void setDependency(String url) {
		this.dependency = url;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
}

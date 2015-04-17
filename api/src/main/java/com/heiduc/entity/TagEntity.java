

package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.getListProperty;
import static com.heiduc.utils.EntityUtil.getLongProperty;
import static com.heiduc.utils.EntityUtil.getStringProperty;
import static com.heiduc.utils.EntityUtil.setProperty;

import java.util.ArrayList;
import java.util.List;

import org.heiduc.api.datastore.Entity;


/**
 * @author Alexander Oleynik
 */
public class TagEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 1L;

	private Long parent;
	private String name;
	private String title;
	private List<String> pages;
	
	public TagEntity() {
		pages = new ArrayList<String>();
	}
	
	@Override
	public void load(Entity entity) {
		super.load(entity);
		parent = getLongProperty(entity, "parent");
		name = getStringProperty(entity, "name");
		title = getStringProperty(entity, "title");
		pages = getListProperty(entity, "pages");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "name", name, true);
		setProperty(entity, "title", title, false);
		setProperty(entity, "parent", parent, true);
		setProperty(entity, "pages", pages);
	}

	public TagEntity(Long aParent, String aName, String aTitle) {
		this();
		name = aName;
		title = aTitle;
		parent = aParent;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public List<String> getPages() {
		return pages;
	}

	public void setPages(List<String> pages) {
		this.pages = pages;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}

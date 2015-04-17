

package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.*;

import org.heiduc.api.datastore.Entity;


/**
 * SEO Urls plugin link data.
 * @author Alexander Oleynik
 */
public class SeoUrlEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 2L;

	private String fromLink;
	private String toLink;

	public SeoUrlEntity() {
	}
	
	@Override
	public void load(Entity entity) {
		super.load(entity);
		fromLink = getStringProperty(entity, "fromLink");
		toLink = getStringProperty(entity, "toLink");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "fromLink", fromLink, true);
		setProperty(entity, "toLink", toLink, false);
	}

	public SeoUrlEntity(String aFrom, String aTo) {
		this();
		fromLink = aFrom;
		toLink = aTo;
	}
	
	public String getFromLink() {
		return fromLink;
	}
	
	public void setFromLink(String aFrom) {
		fromLink = aFrom;
	}

	public String getToLink() {
		return toLink;
	}

	public void setToLink(String to) {
		this.toLink = to;
	}

}

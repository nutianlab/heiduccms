

package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.getLongProperty;
import static com.heiduc.utils.EntityUtil.getStringProperty;
import static com.heiduc.utils.EntityUtil.getTextProperty;
import static com.heiduc.utils.EntityUtil.setProperty;
import static com.heiduc.utils.EntityUtil.setTextProperty;

import org.heiduc.api.datastore.Entity;


import com.heiduc.enums.StructureTemplateType;

/**
 * @author Alexander Oleynik
 */
public class StructureTemplateEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 3L;

	private String title;
	private String name;
	private Long structureId;
	private StructureTemplateType type;
	private String content;
	private String headContent;
	
	public StructureTemplateEntity() {
		type = StructureTemplateType.VELOCITY;
	}
	
	@Override
	public void load(Entity entity) {
		super.load(entity);
		title = getStringProperty(entity, "title");
		name = getStringProperty(entity, "name");
		structureId = getLongProperty(entity, "structureId");
		type = StructureTemplateType.valueOf(getStringProperty(entity, "type"));
		content = getTextProperty(entity, "content");
		headContent = getTextProperty(entity, "headContent");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "title", title, true);
		setProperty(entity, "name", name, true);
		setProperty(entity, "structureId", structureId, true);
		setProperty(entity, "type", type.name(), false);
		setTextProperty(entity, "content", content);
		setTextProperty(entity, "headContent", headContent);
	}

	public StructureTemplateEntity(String name, String title, Long structureId, 
			StructureTemplateType type, String content) {
		this();
		this.name = name;
		this.title = title;
		this.structureId = structureId;
		this.type = type;
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

	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

	public StructureTemplateType getType() {
		return type;
	}

	public String getTypeString() {
		return type != null ? type.name() : "null";
	}
	
	public void setType(StructureTemplateType value) {
		this.type = value;
	}

	public boolean isVelocity() {
		return type.equals(StructureTemplateType.VELOCITY);
	}

	public boolean isXSLT() {
		return type.equals(StructureTemplateType.XSLT);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadContent() {
		return headContent;
	}

	public void setHeadContent(String headContent) {
		this.headContent = headContent;
	}
}



package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.getStringProperty;
import static com.heiduc.utils.EntityUtil.getTextProperty;
import static com.heiduc.utils.EntityUtil.setProperty;
import static com.heiduc.utils.EntityUtil.setTextProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.heiduc.api.datastore.Entity;

import com.heiduc.business.vo.StructureFieldVO;

public class StructureEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 2L;

	private String title;
	private String content;
	
	public StructureEntity() {
		content = "";
	}
	
	@Override
	public void load(Entity entity) {
		super.load(entity);
		title = getStringProperty(entity, "title");
		content = getTextProperty(entity, "content");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "title", title, true);
		setTextProperty(entity, "content", content);
	}

	public StructureEntity(String title, String content) {
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

	public List<StructureFieldVO> getFields() {
		try {
			Document doc = DocumentHelper.parseText(getContent());
			List<StructureFieldVO> result = new ArrayList<StructureFieldVO>();
			for (Iterator<Element> i = doc.getRootElement().elementIterator(); 
					i.hasNext(); ) {
					Element element = i.next();
					if (element.getName().equals("field")) {
						result.add(new StructureFieldVO(
								element.elementText("title"),
								element.elementText("name"),
								element.elementText("type")));
					}
			}
			return result;
		} catch (DocumentException e) {
			LOGGER.error(e.getMessage());
			return Collections.EMPTY_LIST;
		}
	}
}



package com.heiduc.business.impl.imex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.heiduc.business.decorators.TreeItemDecorator;
import com.heiduc.business.imex.ExporterFactory;
import com.heiduc.business.imex.TagExporter;
import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.TagEntity;

/**
 * @author Alexander Oleynik
 */
public class TagExporterImpl extends AbstractExporter 
		implements TagExporter {

	public TagExporterImpl(ExporterFactory factory) {
		super(factory);
	}
	
	private List<TreeItemDecorator<TagEntity>> getTagTreeRoots() {
		List<TagEntity> tags = getDao().getTagDao().select();
		Map<Long, TreeItemDecorator<TagEntity>> buf = 
			new HashMap<Long, TreeItemDecorator<TagEntity>>();
		for (TagEntity tag : tags) {
			buf.put(tag.getId(), new TreeItemDecorator<TagEntity>(tag, null));
		}
		List<TreeItemDecorator<TagEntity>> roots = new ArrayList<TreeItemDecorator<TagEntity>>();
		for (Long id : buf.keySet()) {
			TreeItemDecorator<TagEntity> tag = buf.get(id);
			if (tag.getEntity().getParent() == null) {
				roots.add(tag);
			} else {
				TreeItemDecorator<TagEntity> parent = buf.get(tag.getEntity()
						.getParent());
				if (parent != null) {
					parent.getChildren().add(tag);
					tag.setParent(parent);
				}
			}
		}
		return roots;
	}
	
	@Override
	public String createXML() {
		Document doc = DocumentHelper.createDocument();
		Element element = doc.addElement("tags");
		List<TreeItemDecorator<TagEntity>> list = getTagTreeRoots();
		for (TreeItemDecorator<TagEntity> tag : list) {
			createTagXML(element, tag);
		}
		return doc.asXML();
	}

	private void createTagXML(Element element, TreeItemDecorator<TagEntity> tag) {
		Element tagElement = element.addElement("tag");
		tagElement.addElement("name").setText(tag.getEntity().getName());
		String title = tag.getEntity().getTitle();
		if (StringUtils.isEmpty(title)) {
			title = tag.getEntity().getName();
		}
		tagElement.addElement("title").setText(title);
		for (TreeItemDecorator<TagEntity> child : tag.getChildren()) {
			createTagXML(tagElement, child);
		}
	}
	
	public void readTags(Element tagsElement, Long parent) throws DaoTaskException {
		for (Iterator<Element> i = tagsElement.elementIterator(); 
				i.hasNext(); ) {
            Element element = i.next();
            if (element.getName().equals("tag")) {
            	String name = element.elementText("name");
            	String title = element.elementText("title") != null ?
            			element.elementText("title") : name;
            	TagEntity tag = getDao().getTagDao().getByName(parent, name);
            	if (tag == null) {
            		tag = new TagEntity(parent, name, title);
            	}
            	tag.setName(name);
            	getDaoTaskAdapter().tagSave(tag);
            	readTags(element, tag.getId());
            }
		}		
	}
	
	/**
	 * Read and import data from _messages.xml file.
	 * @param xml - _messages.xml file content.
	 * @throws DocumentException
	 * @throws DaoTaskException
	 */
	public void read(String xml) throws DocumentException, 
			DaoTaskException {
		Document doc = DocumentHelper.parseText(xml);
		readTags(doc.getRootElement(), null);
	}
}

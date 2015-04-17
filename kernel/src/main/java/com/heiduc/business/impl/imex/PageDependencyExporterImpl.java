

package com.heiduc.business.impl.imex;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.heiduc.business.imex.PageDependencyExporter;
import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.PageDependencyEntity;

/**
 * @author Alexander Oleynik
 */
public class PageDependencyExporterImpl extends AbstractExporter 
		implements PageDependencyExporter {

	public PageDependencyExporterImpl(ExporterFactoryImpl factory) {
		super(factory);
	}
	
	@Override
	public String createXML() {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("dependencies");
		List<PageDependencyEntity> list = getDao().getPageDependencyDao().select();
		for (PageDependencyEntity entity : list) {
			Element itemElement = root.addElement("item");
			itemElement.addElement("page").setText(entity.getPage());
			itemElement.addElement("dependency").setText(entity.getDependency());
		}
		return doc.asXML();
	}

	public void read(Element rootElement) throws DaoTaskException {
		for (Iterator<Element> i = rootElement.elementIterator(); 
				i.hasNext(); ) {
            Element element = i.next();
            if (element.getName().equals("item")) {
            	String page = element.elementText("page");
            	String dependency = element.elementText("dependency");
            	PageDependencyEntity entity = 
            		getDao().getPageDependencyDao().getByPageAndDependency(
            				page, dependency);
            	
            	if (entity == null) {
            		entity = new PageDependencyEntity(dependency, page);
            	}
            	getDaoTaskAdapter().pageDependencySave(entity);
            }
		}		
	}
	
	/**
	 * Read and import data from _messages.xml file.
	 * @param xml - _messages.xml file content.
	 * @throws DocumentException
	 * @throws DaoTaskException
	 */
	public void readFile(String xml) throws DocumentException, 
			DaoTaskException {
		Document doc = DocumentHelper.parseText(xml);
		read(doc.getRootElement());
	}
}

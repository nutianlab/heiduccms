

package com.heiduc.business.impl.imex;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.heiduc.business.imex.SeoUrlExporter;
import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.SeoUrlEntity;

/**
 * @author Alexander Oleynik
 */
public class SeoUrlsExporterImpl extends AbstractExporter 
		implements SeoUrlExporter {

	public SeoUrlsExporterImpl(ExporterFactoryImpl factory) {
		super(factory);
	}
	
	@Override
	public String createXML() {
		Document doc = DocumentHelper.createDocument();
		Element e = doc.addElement("seo-urls");
		List<SeoUrlEntity> list = getDao().getSeoUrlDao().select();
		for (SeoUrlEntity seo : list) {
			Element seoElement = e.addElement("seo-url");
			seoElement.addElement("from").setText(seo.getFromLink());
			seoElement.addElement("to").setText(seo.getToLink());
		}
		return doc.asXML();
	}

	public void read(Element urlsElement) throws DaoTaskException {
		for (Iterator<Element> i = urlsElement.elementIterator(); 
				i.hasNext(); ) {
            Element element = i.next();
            if (element.getName().equals("seo-url")) {
            	String from = element.elementText("from");
            	String to = element.elementText("to");
            	SeoUrlEntity seourl = getDao().getSeoUrlDao().getByFrom(from);
            	if (seourl == null) {
            		seourl = new SeoUrlEntity(from, to);
            	}
            	getDaoTaskAdapter().seoUrlSave(seourl);
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
		read(doc.getRootElement());
	}
}

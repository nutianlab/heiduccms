

package com.heiduc.business.imex;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.heiduc.dao.DaoTaskException;

/**
 * @author Alexander Oleynik
 */
public interface SeoUrlExporter {

	String createXML();

	void read(Element element) throws DaoTaskException;
	
	/**
	 * Read and import data from _seourls.xml file.
	 * @param xml - _seourls.xml file content.
	 * @throws DocumentException
	 * @throws DaoTaskException
	 */
	void read(String xml) throws DocumentException, 
			DaoTaskException;
}

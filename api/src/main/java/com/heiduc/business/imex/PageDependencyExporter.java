

package com.heiduc.business.imex;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.heiduc.dao.DaoTaskException;

/**
 * @author Alexander Oleynik
 */
public interface PageDependencyExporter {

	String createXML();

	void read(Element element) throws DaoTaskException;
	
	/**
	 * Read and import data from _dependencies.xml file.
	 * @param xml - _dependencies.xml file content.
	 * @throws DocumentException
	 * @throws DaoTaskException
	 */
	void readFile(String xml) throws DocumentException, 
			DaoTaskException;
}

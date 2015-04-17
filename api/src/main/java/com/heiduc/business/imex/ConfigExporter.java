

package com.heiduc.business.imex;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.heiduc.dao.DaoTaskException;

public interface ConfigExporter {

	String createConfigXML();

	void readConfigs(Element configElement) throws DaoTaskException;

	void readLanguages(Element languagesElement) throws DaoTaskException;

	/**
	 * Parse and import data from _config.xml file.
	 * @param xml - _config.xml content.
	 * @throws DocumentException 
	 * @throws DaoTaskException 
	 */
	void readConfigFile(String xml) throws DocumentException, 
			DaoTaskException;
	
}

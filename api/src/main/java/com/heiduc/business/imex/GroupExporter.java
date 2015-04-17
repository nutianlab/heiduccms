

package com.heiduc.business.imex;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.heiduc.dao.DaoTaskException;

/**
 * @author Alexander Oleynik
 */
public interface GroupExporter {

	String createGroupsXML();
	
	void readGroups(Element groupsElement) throws DaoTaskException;
	
	/**
	 * Read and import data from _groups/xml file.
	 * @param xml - _groups.xml file content.
	 * @throws DaoTaskException
	 * @throws DocumentException
	 */
	void readGroupsFile(String xml) throws DaoTaskException, 
			DocumentException;
	
}

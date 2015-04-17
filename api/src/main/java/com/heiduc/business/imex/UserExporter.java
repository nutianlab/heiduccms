

package com.heiduc.business.imex;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.heiduc.dao.DaoTaskException;

/**
 * @author Alexander Oleynik
 */
public interface UserExporter {

	String createUsersXML();
	
	void readUsers(Element usersElement) throws DaoTaskException;
	
	/**
	 * Read and import data from _users.xml.
	 * @param xml - _users.xml content.
	 * @throws DocumentException 
	 * @throws DaoTaskException 
	 */
	void readUsersFile(String xml) throws DocumentException, 
			DaoTaskException;
}

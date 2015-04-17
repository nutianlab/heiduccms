

package com.heiduc.business.imex;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.heiduc.dao.DaoTaskException;

/**
 * @author Alexander Oleynik
 */
public interface MessagesExporter {

	String createMessagesXML();

	void readMessages(Element messagesElement) throws DaoTaskException;
	
	/**
	 * Read and import data from _messages.xml file.
	 * @param xml - _messages.xml file content.
	 * @throws DocumentException
	 * @throws DaoTaskException
	 */
	void readMessagesFile(String xml) throws DocumentException, 
			DaoTaskException;
}

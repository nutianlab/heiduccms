

package com.heiduc.business.imex;

import org.dom4j.DocumentException;

import com.heiduc.dao.DaoTaskException;

/**
 * @author Alexander Oleynik
 */
public interface TagExporter {

	String createXML();

	/**
	 * Read and import data from _tags.xml file.
	 * @param xml - _tags.xml file content.
	 * @throws DocumentException
	 * @throws DaoTaskException
	 */
	void read(String xml) throws DocumentException, DaoTaskException;
}

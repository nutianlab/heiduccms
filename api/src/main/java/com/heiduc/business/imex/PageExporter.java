

package com.heiduc.business.imex;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.PageEntity;

public interface PageExporter {

	String createPageContentXML(PageEntity page);
	
	String createPageCommentsXML(String pageURL);

	String createPagePermissionsXML(String pageURL);

	String createPageTagXML(String pageURL);

	void readPages(Element pages) throws DaoTaskException;
	
	/**
	 * Read and import data from _content.xml file.
	 * @param folderPath - _content.xml file path.
	 * @param xml - _content.xml file content.
	 * @return
	 * @throws DocumentException 
	 * @throws DaoTaskException 
	 */
	boolean readContentFile(String folderPath, String xml) 
			throws DocumentException, DaoTaskException;
	
	/**
	 * Read and import data from _comments.xml file.
	 * @param folderPath - _comments.xml file path.
	 * @param xml - _comments.xml file content.
	 * @return
	 * @throws DocumentException 
	 * @throws DaoTaskException 
	 */
	boolean readCommentsFile(String folderPath, String xml) 
			throws DocumentException, DaoTaskException;
	
	/**
	 * Read and import data from _permissions.xml file.
	 * @param folderPath - _permissions.xml file path.
	 * @param xml - _permissions.xml file content.
	 * @return
	 * @throws DocumentException 
	 */
	boolean readPermissionsFile(String folderPath, String xml) 
			throws DocumentException;

	boolean readPageTagFile(String folderPath, String xml) 
			throws DocumentException, DaoTaskException;
	
}

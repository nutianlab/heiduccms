

package com.heiduc.business.imex;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.FormEntity;

public interface FormExporter {

	String createFormsXML();
	
	void readForms(Element formsElement) throws DaoTaskException;
	
	void readFields(Element formElement, FormEntity form) 
			throws DaoTaskException;

	void readFormConfig(Element configElement) throws DaoTaskException;
	
	/**
	 * Read and import data from _forms.xml file.
	 * @param xml - _forms.xml content.
	 * @throws DocumentException
	 * @throws DaoTaskException
	 */
	void readFormsFile(String xml) throws DocumentException, 
			DaoTaskException;
}

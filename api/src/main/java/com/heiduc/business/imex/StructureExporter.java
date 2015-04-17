

package com.heiduc.business.imex;

import java.io.IOException;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.heiduc.business.imex.task.TaskTimeoutException;
import com.heiduc.business.imex.task.ZipOutStreamTaskAdapter;
import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.StructureEntity;

/**
 * @author Alexander Oleynik
 */
public interface StructureExporter {

	String createStructuresXML();
	
	void readStructures(Element structuresElement) 
			throws DaoTaskException;
	
	/**
	 * Read and import data from _structures.xml file.
	 * @param xml - _structures.xml content.
	 * @throws DocumentException
	 * @throws DaoTaskException
	 */
	void readStructuresFile(String xml) throws DocumentException, 
			DaoTaskException;
	
	void exportStructures(ZipOutStreamTaskAdapter out, 
			List<StructureEntity> structures) 
	throws IOException, TaskTimeoutException;

}

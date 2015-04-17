

package com.heiduc.business.imex;

import org.dom4j.Element;

import com.heiduc.dao.DaoTaskException;

/**
 * @author Alexander Oleynik
 */
public interface FolderExporter {

	void readFolders(Element foldersElement) throws DaoTaskException;
	
}

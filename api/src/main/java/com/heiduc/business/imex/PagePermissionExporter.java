

package com.heiduc.business.imex;

import org.dom4j.Element;

/**
 * @author Alexander Oleynik
 */
public interface PagePermissionExporter {

	void createPagePermissionsXML(final Element permissionsElement, 
			final String friendlyUrl);
	
	void readPagePermissions(Element permissionsElement, final String url);
}

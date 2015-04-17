

package com.heiduc.business.impl.imex;

import static com.heiduc.utils.XmlUtil.notNull;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

import com.heiduc.business.imex.PagePermissionExporter;
import com.heiduc.entity.ContentPermissionEntity;
import com.heiduc.entity.GroupEntity;
import com.heiduc.enums.ContentPermissionType;

/**
 * @author Alexander Oleynik
 */
public class PagePermissionExporterImpl extends AbstractExporter 
		implements PagePermissionExporter {

	public PagePermissionExporterImpl(ExporterFactoryImpl factory) {
		super(factory);
	}
	
	public void createPagePermissionsXML(final Element permissionsElement, 
			final String friendlyUrl) {
		List<ContentPermissionEntity> list = getDao().getContentPermissionDao()
				.selectByUrl(friendlyUrl);
		for (ContentPermissionEntity permission : list) {
			createPagePermissionXML(permissionsElement, permission);
		}
	}

	private void createPagePermissionXML(Element permissionsElement, 
			final ContentPermissionEntity permission) {
		GroupEntity group = getDao().getGroupDao().getById(permission.getGroupId());
		Element permissionElement = permissionsElement.addElement("permission");
		permissionElement.addElement("group").setText(group.getName());
		permissionElement.addElement("permissionType").setText(
				permission.getPermission().name());
		permissionElement.addElement("allLanguages").setText(
				String.valueOf(permission.isAllLanguages()));
		permissionElement.addElement("languages").setText(notNull(
				permission.getLanguages()));
	}
	
	public void readPagePermissions(Element permissionsElement,
			final String url) {
		for (Iterator<Element> i = permissionsElement.elementIterator(); 
				i.hasNext(); ) {
            Element element = i.next();
            if (element.getName().equals("permission")) {
            	String groupName = element.elementText("group");
            	GroupEntity group = getDao().getGroupDao().getByName(groupName);
            	if (group == null) {
            		logger.error("Group " + groupName + " was not found.");
            		continue;
            	}
            	ContentPermissionType permType = ContentPermissionType.valueOf(
            			element.elementText("permissionType"));
            	boolean allLanguages = Boolean.valueOf(element.elementText(
            			"allLanguages"));
            	String languages = element.elementText("languages");
            	getBusiness().getContentPermissionBusiness().setPermission(
            			url, group, permType, languages);
            }
		}		
	}
}

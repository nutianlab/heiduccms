

package com.heiduc.business.impl.imex;

import java.util.Iterator;

import org.dom4j.Element;

import com.heiduc.business.imex.FolderExporter;
import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.FolderEntity;
import com.heiduc.entity.GroupEntity;
import com.heiduc.enums.FolderPermissionType;

/**
 * @author Alexander Oleynik
 */
public class FolderExporterImpl extends AbstractExporter 
		implements FolderExporter {

	public FolderExporterImpl(ExporterFactoryImpl factory) {
		super(factory);
	}
	
	public void readFolders(Element foldersElement) throws DaoTaskException {
		for (Iterator<Element> i = foldersElement.elementIterator(); 
				i.hasNext(); ) {
            Element element = i.next();
            if (element.getName().equals("folder")) {
            	FolderEntity root = getDao().getFolderDao().getByPath("/");
            	readFolder(element, root);
            }
		}
	}
	
	private void readFolder(Element element, final FolderEntity parent) 
			throws DaoTaskException {
		String name = element.elementText("name");
		String title = element.elementText("title");
		FolderEntity folder;
		if (name.equals("/") && parent.isRoot()) {
			folder = getDao().getFolderDao().getByPath("/");
		}
		else {
			folder = getDao().getFolderDao().getByParentName(
					parent.getId(), name);
			if (folder == null) {
				folder = new FolderEntity(title, name, parent.getId());
			}
		}
		folder.setTitle(title);
		getDaoTaskAdapter().folderSave(folder);
		readFolderPermissions(element.element("permissions"), folder);
		for (Iterator<Element> i = element.elementIterator(); i
				.hasNext();) {
			Element e = i.next();
			if (e.getName().equals("folder")) {
				readFolder(e, folder);
			}
		}
	}
	
	private void readFolderPermissions(Element permissionsElement,
			final FolderEntity folder) {
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
            	FolderPermissionType permType = FolderPermissionType.valueOf(
            			element.elementText("permissionType"));
            	getBusiness().getFolderPermissionBusiness().setPermission(
            			folder, group, permType);
            }
		}		
	}
}

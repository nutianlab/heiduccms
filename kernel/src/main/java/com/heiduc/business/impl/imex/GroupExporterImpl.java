

package com.heiduc.business.impl.imex;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.heiduc.business.imex.GroupExporter;
import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.GroupEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.entity.UserGroupEntity;
import com.heiduc.entity.helper.UserHelper;

/**
 * @author Alexander Oleynik
 */
public class GroupExporterImpl extends AbstractExporter 
		implements GroupExporter {

	public GroupExporterImpl(ExporterFactoryImpl factory) {
		super(factory);
	}
	
	@Override
	public String createGroupsXML() {
		Document doc = DocumentHelper.createDocument();
		Element groupsElement = doc.addElement("groups");
		createGroupsXML(groupsElement);
		return doc.asXML();
	}

	private void createGroupsXML(Element groupsElement) {
		List<GroupEntity> list = getDao().getGroupDao().select();
		for (GroupEntity Group : list) {
			createGroupXML(groupsElement, Group);
		}
	}

	private void createGroupXML(Element groupsElement, final GroupEntity group) {
		Element groupElement = groupsElement.addElement("group");
		groupElement.addElement("name").setText(group.getName());
		List<UserGroupEntity> userGroups = getDao().getUserGroupDao()
				.selectByGroup(group.getId());
		Element users = groupElement.addElement("users");
		Map<Long, UserEntity> usersMap = UserHelper.createIdMap(getDao()
						.getUserDao().select()); 
		for (UserGroupEntity userGroup : userGroups) {
			UserEntity user = usersMap.get(userGroup.getUserId());
			if (user != null) {
				users.addElement("user").setText(user.getEmail());
			}
		}
	}
	
	public void readGroups(Element groupsElement) throws DaoTaskException {
		Map<Long, UserEntity> usersMap = UserHelper.createIdMap(getDao()
				.getUserDao().select()); 
		for (Iterator<Element> i = groupsElement.elementIterator(); 
				i.hasNext(); ) {
            Element element = i.next();
            if (element.getName().equals("group")) {
            	String name = element.elementText("name");
            	GroupEntity group = getDao().getGroupDao().getByName(name);
            	if (group == null) {
            		group = new GroupEntity(name);
            	}
            	else {
            		group.setName(name);
            	}
            	getDaoTaskAdapter().groupSave(group);
            	for (Iterator<Element> j = element.element("users").elementIterator();
        				j.hasNext(); ) {
                    Element userElement = j.next();
                    UserEntity user = getDao().getUserDao().getByEmail(
                    		userElement.getText());
                    if (user != null) {
                    	UserGroupEntity userGroup = getDao().getUserGroupDao()
                    			.getByUserGroup(group.getId(), user.getId());
            			if (userGroup == null) {
            				userGroup = new UserGroupEntity(group.getId(), 
            						user.getId());
            			}
                    	getDaoTaskAdapter().userGroupSave(userGroup);
                    }
        		}
            }
		}		
	}
	
	/**
	 * Read and import data from _groups/xml file.
	 * @param xml - _groups.xml file content.
	 * @throws DaoTaskException
	 * @throws DocumentException
	 */
	public void readGroupsFile(String xml) throws DaoTaskException, 
			DocumentException {
		Document doc = DocumentHelper.parseText(xml);
		readGroups(doc.getRootElement());
	}
	
}

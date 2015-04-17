

package com.heiduc.dao;

import com.heiduc.dao.tool.GroupTool;
import com.heiduc.dao.tool.UserTool;
import com.heiduc.entity.GroupEntity;
import com.heiduc.enums.UserRole;

public class GroupDaoTest extends AbstractDaoTest {

	private GroupTool groupTool;
	private UserTool userTool;

	@Override
    public void setUp() throws Exception {
        super.setUp();
        groupTool = new GroupTool(getDao());
        userTool = new UserTool(getDao());
	}    
	
	public void testSelectByGroup() {
		GroupEntity group1 = groupTool.addGroup("group1");
		GroupEntity group2 = groupTool.addGroup("group2");
		groupTool.addUserGroup(group1.getId(), 
				userTool.addUser("roma", UserRole.USER).getId());
		groupTool.addUserGroup(group1.getId(), 
				userTool.addUser("sasha", UserRole.USER).getId());
		groupTool.addUserGroup(group2.getId(), 
				userTool.addUser("alex1", UserRole.USER).getId());
		groupTool.addUserGroup(group2.getId(), 
				userTool.addUser("alex2", UserRole.USER).getId());
		groupTool.addUserGroup(group2.getId(), 
				userTool.addUser("alex3", UserRole.USER).getId());
	}
	
}

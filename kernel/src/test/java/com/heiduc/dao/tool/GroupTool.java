

package com.heiduc.dao.tool;

import com.heiduc.dao.Dao;
import com.heiduc.entity.GroupEntity;
import com.heiduc.entity.UserGroupEntity;

public class GroupTool {

	private Dao dao;
	
	public GroupTool(Dao aDao) {
		dao = aDao;
	}
	
	public GroupEntity addGroup(String name) {
		return dao.getGroupDao().save(new GroupEntity(name));
	}
	
	public void addUserGroup(Long groupId, Long userId) {
		dao.getUserGroupDao().save(new UserGroupEntity(groupId, userId));
	}
	
}

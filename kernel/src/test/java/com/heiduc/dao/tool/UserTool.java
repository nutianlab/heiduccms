

package com.heiduc.dao.tool;

import com.heiduc.dao.Dao;
import com.heiduc.entity.UserEntity;
import com.heiduc.enums.UserRole;

public class UserTool {

	private Dao dao;
	
	public UserTool(Dao aDao) {
		dao = aDao;
	}
	
	public UserEntity addUser(String name, String password, String email, 
			UserRole role) {
		return dao.getUserDao().save(new UserEntity(name, password, email, 
				role));
	}

	public UserEntity addUser(String name, String email, UserRole role) {
		return addUser(name, "", email, role);
	}

	public UserEntity addUser(String name, UserRole role) {
		return addUser(name, name, role);
	}
	
}

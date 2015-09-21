

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.UserEntity;
import com.heiduc.enums.UserRole;

/**
 * @author Alexander Oleynik
 */
public interface UserDao extends BaseDao<UserEntity> {

	UserEntity getByEmail(final String email);
	
	UserEntity getByName(final String name);

	List<UserEntity> getByRole(final UserRole role);

	List<UserEntity> selectByGroup(final Long groupId);
	
	UserEntity getByKey(final String key);
	
}

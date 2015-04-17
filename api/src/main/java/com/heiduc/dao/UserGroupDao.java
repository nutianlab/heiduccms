

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.UserGroupEntity;

/**
 * @author Alexander Oleynik
 */
public interface UserGroupDao extends BaseDao<UserGroupEntity> {

	List<UserGroupEntity> selectByUser(Long userId);

	List<UserGroupEntity> selectByGroup(Long groupId);
	
	UserGroupEntity getByUserGroup(Long groupId, Long userId);
	
	void removeByUser(final List<Long> userIds);
	
	void removeByGroup(final List<Long> groupIds);
}

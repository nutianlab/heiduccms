

package com.heiduc.business;

import java.util.List;

import com.heiduc.entity.GroupEntity;
import com.heiduc.entity.UserEntity;

/**
 * @author Alexander Oleynik
 */
public interface GroupBusiness {

	List<String> validateBeforeUpdate(final GroupEntity Group);
	
	void addUserToGroup(GroupEntity group, UserEntity user);
	
	void remove(final List<Long> ids);
	
}

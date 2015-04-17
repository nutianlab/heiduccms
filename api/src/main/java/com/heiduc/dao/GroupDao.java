

package com.heiduc.dao;

import com.heiduc.entity.GroupEntity;

/**
 * @author Alexander Oleynik
 */
public interface GroupDao extends BaseDao<GroupEntity> {

	GroupEntity getByName(final String name);

	GroupEntity getGuestsGroup();
	
}

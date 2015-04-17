

package com.heiduc.dao;

import com.heiduc.entity.ConfigEntity;

public interface ConfigDao extends BaseDao<ConfigEntity> {

	ConfigEntity getConfig();
	
}

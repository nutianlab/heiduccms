

package com.heiduc.dao;

import com.heiduc.entity.PluginResourceEntity;

/**
 * @author Alexander Oleynik
 */
public interface PluginResourceDao extends BaseDao<PluginResourceEntity> {

	PluginResourceEntity getByUrl(String plugin, String name);
	
}



package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.PluginEntity;

/**
 * @author Alexander Oleynik
 */
public interface PluginDao extends BaseDao<PluginEntity> {

	PluginEntity getByName(String name);
	
	List<PluginEntity> selectEnabled();
}



package com.heiduc.dao.impl;


import org.heiduc.api.datastore.Query;
import org.heiduc.api.datastore.Query.FilterOperator;

import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.PluginResourceDao;
import com.heiduc.entity.PluginResourceEntity;

public class PluginResourceDaoImpl extends 
		BaseDaoImpl<PluginResourceEntity> implements PluginResourceDao {

	public PluginResourceDaoImpl() {
		super(PluginResourceEntity.class);
	}

	@Override
	public PluginResourceEntity getByUrl(String plugin, String url) {
		Query q = newQuery();
		q.addFilter("url", FilterOperator.EQUAL, url);
		q.addFilter("pluginName", FilterOperator.EQUAL, plugin);
		return selectOne(q, "getByUrl", params(url, plugin));
	}

}

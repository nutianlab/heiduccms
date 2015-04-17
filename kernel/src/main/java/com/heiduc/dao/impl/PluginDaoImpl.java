

package com.heiduc.dao.impl;

import java.util.List;

import org.heiduc.api.datastore.Query;
import org.heiduc.api.datastore.Query.FilterOperator;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.PluginDao;
import com.heiduc.entity.PluginEntity;

public class PluginDaoImpl extends BaseDaoImpl<PluginEntity> 
		implements PluginDao {

	public PluginDaoImpl() {
		super(PluginEntity.class);
	}

	@Override
	public PluginEntity getByName(final String name) {
		Query q = newQuery();
		q.addFilter("name", FilterOperator.EQUAL, name);
		return selectOne(q, "getByName", params(name));
	}
	
	@Override
	public List<PluginEntity> selectEnabled() {
		Query q = newQuery();
		q.addFilter("disabled", FilterOperator.EQUAL, false);
		return select(q, "selectEnabled", params(false));
	}

}

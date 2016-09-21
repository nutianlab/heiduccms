package com.heiduc.dao.impl;

import java.util.List;

import com.heiduc.common.HeiducContext;
import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.ConfigDao;
import com.heiduc.entity.ConfigEntity;

public class ConfigDaoImpl extends BaseDaoImpl<ConfigEntity> 
		implements ConfigDao {


    private static final long serialVersionUID = 1L;
    
	public ConfigDaoImpl() {
		super(ConfigEntity.class);
	}

	@Override
	public ConfigEntity getConfig() {
		List<ConfigEntity> list = select();
		if (list.size() > 0) {
			return list.get(0);
		}
		logger.warn("Config for site was not found!");
		return new ConfigEntity();
	}

	@Override
	public ConfigEntity save(ConfigEntity entity) {
		HeiducContext.getInstance().setConfig(entity);
		return super.save(entity);
	}
	
}
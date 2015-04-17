

package com.heiduc.dao.impl;

import java.util.List;

import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.FormConfigDao;
import com.heiduc.entity.FormConfigEntity;

public class FormConfigDaoImpl extends BaseDaoImpl<FormConfigEntity> 
		implements FormConfigDao {

	public FormConfigDaoImpl() {
		super(FormConfigEntity.class);
	}

	@Override
	public FormConfigEntity getConfig() {
		List<FormConfigEntity> list = select();
		if (list.size() > 0) {
			return list.get(0);
		}
		logger.error("Form config for site was not found!");
		return new FormConfigEntity();
	}

}

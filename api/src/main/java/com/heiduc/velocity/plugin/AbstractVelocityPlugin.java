

package com.heiduc.velocity.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.Business;
import com.heiduc.dao.Dao;

public class AbstractVelocityPlugin implements VelocityPlugin {

	protected static final Log logger = LogFactory.getLog(
			AbstractVelocityPlugin.class);

	private Business business;
	
	@Override
	public Business getBusiness() {
		return business;
	}

	@Override
	public Dao getDao() {
		return business.getDao();
	}

	@Override
	public void setBusiness(Business bean) {
		business = bean;		
	}

}

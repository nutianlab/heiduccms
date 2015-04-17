

package com.heiduc.service.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.Business;
import com.heiduc.dao.Dao;
import com.heiduc.service.BackService;
import com.heiduc.service.FrontService;

abstract public class AbstractServicePlugin implements ServicePlugin {

	protected static final Log logger = LogFactory.getLog(
			AbstractServicePlugin.class);

	private Business business;
	private FrontService frontService;
	private BackService backService;
	
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

	@Override
	public FrontService getFrontService() {
		return frontService;
	}

	@Override
	public void setFrontService(FrontService bean) {
		frontService = bean;
	}

	@Override
	public BackService getBackService() {
		return backService;
	}

	@Override
	public void setBackService(BackService bean) {
		backService = bean;
	}
	
}

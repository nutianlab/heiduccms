

package com.heiduc.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.Business;
import com.heiduc.dao.Dao;
import com.heiduc.global.SystemService;

public abstract class AbstractServiceBeanImpl implements 
		AbstractServiceBean {

	protected static final Log logger = LogFactory.getLog(
			AbstractServiceBeanImpl.class);
	
	private Business business;
	
	public AbstractServiceBeanImpl() {
	}

	public AbstractServiceBeanImpl(Business aBusiness) {
		business = aBusiness;
	}
	
	@Override
	public Business getBusiness() {
		return business;
	}
	
	@Override
	public void setBusiness(Business bean) {
		business = bean;
	}
	
	@Override
	public Dao getDao() {
		return getBusiness().getDao();
	}
	
	@Override
	public SystemService getSystemService() {
		return getBusiness().getSystemService();
	}
}

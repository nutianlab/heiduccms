

package com.heiduc.business.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.AbstractBusiness;
import com.heiduc.business.Business;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.global.SystemService;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public abstract class AbstractBusinessImpl implements AbstractBusiness,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final Log LOGGER = LogFactory.getLog(
			AbstractBusinessImpl.class);

	protected Dao getDao() {
		return getBusiness().getDao();
	}

	protected SystemService getSystemService() {
		return getBusiness().getSystemService();
	}

	protected Business getBusiness() {
		return HeiducContext.getInstance().getBusiness();
	}

}

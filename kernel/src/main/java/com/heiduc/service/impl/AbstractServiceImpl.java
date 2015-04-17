

package com.heiduc.service.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.Business;
import com.heiduc.business.mq.MessageQueue;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.service.AbstractService;
import com.heiduc.service.BackService;
import com.heiduc.service.FrontService;

public abstract class AbstractServiceImpl implements AbstractService, 
		Serializable {
	
	protected static Log logger = LogFactory.getLog(AbstractServiceImpl.class);

	protected Dao getDao() {
		return getBusiness().getDao();
	}

	protected Business getBusiness() {
		return HeiducContext.getInstance().getBusiness();
	}

	protected FrontService getFrontService() {
		return HeiducContext.getInstance().getFrontService();
	}

	protected BackService getBackService() {
		return HeiducContext.getInstance().getBackService();
	}
	
	protected MessageQueue getMessageQueue() {
		return HeiducContext.getInstance().getMessageQueue();
	}
	
}

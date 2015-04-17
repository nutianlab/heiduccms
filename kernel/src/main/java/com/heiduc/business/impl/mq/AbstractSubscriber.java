

package com.heiduc.business.impl.mq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.Business;
import com.heiduc.business.mq.MessageQueue;
import com.heiduc.business.mq.Subscriber;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.global.SystemService;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public abstract class AbstractSubscriber implements Subscriber {

	protected static final Log logger = LogFactory.getLog(AbstractSubscriber.class);

	protected Business getBusiness() {
		return HeiducContext.getInstance().getBusiness();
	}

	protected Dao getDao() {
		return getBusiness().getDao();
	}
		
	protected SystemService getSystemService() {
		return getBusiness().getSystemService();
	}
	
	protected MessageQueue getMessageQueue() {
		return HeiducContext.getInstance().getMessageQueue();
	}
	
}

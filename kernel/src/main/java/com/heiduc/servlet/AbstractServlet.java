

package com.heiduc.servlet;

import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.Business;
import com.heiduc.business.mq.MessageQueue;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.global.SystemService;

/**
 * Base servlet.
 * 
 * @author Aleksandr Oleynik
 */
public abstract class AbstractServlet extends HttpServlet {

	protected static final Log logger = LogFactory
			.getLog(AbstractServlet.class);

	/**
	 * Default constructor.
	 */
	public AbstractServlet() {
		super();
	}

	/**
	 * Getter for business Spring bean.
	 * 
	 * @return Business bean.
	 */
	public Business getBusiness() {
		return HeiducContext.getInstance().getBusiness();
	}

	/**
	 * Getter for dao Spring bean.
	 * 
	 * @return Dao bean.
	 */
	public Dao getDao() {
		return getBusiness().getDao();
	}
	
	public SystemService getSystemService() {
		return getBusiness().getSystemService();
	}

	public MessageQueue getMessageQueue() {
		return HeiducContext.getInstance().getMessageQueue();
	}
	
}

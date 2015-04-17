

package com.heiduc.filter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.Business;
import com.heiduc.business.mq.MessageQueue;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.global.SystemService;

/**
 * @author Alexander Oleynik
 */
public abstract class AbstractFilter {
	
    protected static final Log logger = LogFactory.getLog(AbstractFilter.class);

    private FilterConfig config;
	private ServletContext servletContext;

	public AbstractFilter() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
		servletContext = config.getServletContext();
	}
	
	public void destroy() {
	}
	
	protected Dao getDao() {
		return getBusiness().getDao();
	}

	protected Business getBusiness() {
		return HeiducContext.getInstance().getBusiness();
	}
	
	protected MessageQueue getMessageQueue() {
		return HeiducContext.getInstance().getMessageQueue();
	}

	protected SystemService getSystemService() {
		return getBusiness().getSystemService();
	}
	
	protected boolean isLoggedIn(final HttpServletRequest request) {
		return HeiducContext.getInstance().getSession().getString(
				AuthenticationFilter.USER_SESSION_ATTR) != null;
	}

}

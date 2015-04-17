package com.heiduc.common;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heiduc.business.Business;
import com.heiduc.business.mq.MessageQueue;
import com.heiduc.business.page.PageRenderingContext;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.service.BackService;
import com.heiduc.service.FrontService;

/**
 * Store request scoped data in thread local variable and set it in filter.
 * 
 * @author Alexander Oleynik
 */
public class HeiducContext {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private int requestCount;
	private long startTime;
	
	private Session session;
	private Locale locale;
	private UserEntity user;
	private ConfigEntity config;
	private Business business;
	private FrontService frontService;
	private BackService backService;
	private MessageQueue messageQueue;
	private List<String> skipURLs;
	private PageRenderingContext pageRenderingContext;
	
	private HeiducContext() {
		requestCount = 0;
		startTime = System.currentTimeMillis();
		
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
		requestCount++;
		startTime = System.currentTimeMillis();
	}
	
	public String getLanguage() {
		if (locale == null) {
			return getConfig().getDefaultLanguage();
		}
		return locale.getLanguage();
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale aLocale) {
		locale = aLocale;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	private static ThreadLocal<HeiducContext> threadInstance;
 
	public static HeiducContext getInstance() {
		if (threadInstance == null) {
			threadInstance = new ThreadLocal<HeiducContext>() {
				@Override
				protected HeiducContext initialValue() {
					return new HeiducContext();
				}
			};
		}
		return threadInstance.get();
	}
	
	public int getRequestCount() {
		return requestCount;
	}

	public long getStartTime() {
		return startTime;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public FrontService getFrontService() {
		return frontService;
	}

	public void setFrontService(FrontService frontService) {
		this.frontService = frontService;
	}

	public BackService getBackService() {
		return backService;
	}

	public void setBackService(BackService backService) {
		this.backService = backService;
	}

	public MessageQueue getMessageQueue() {
		return messageQueue;
	}

	public void setMessageQueue(MessageQueue messageQueue) {
		this.messageQueue = messageQueue;
	}

	public List<String> getSkipURLs() {
		return skipURLs;
	}

	public void setSkipURLs(List<String> skipURLs) {
		this.skipURLs = skipURLs;
	}
	
	public boolean isSkipUrl(final String url) {
    	for (String u : skipURLs) {
    		if (url.startsWith(u)) {
    			return true;
    		}
    	}
    	return false;
    }

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public ConfigEntity getConfig() {
		if (config == null) {
			config = getBusiness().getDao().getConfigDao().getConfig();
			if (config == null) {
				config = new ConfigEntity();
			}
		}
		return config;
	}

	public void setConfig(ConfigEntity config) {
		this.config = config;
	}

	public PageRenderingContext getPageRenderingContext() {
		if (pageRenderingContext == null) {
			pageRenderingContext = new PageRenderingContext();
		}
		return pageRenderingContext;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}

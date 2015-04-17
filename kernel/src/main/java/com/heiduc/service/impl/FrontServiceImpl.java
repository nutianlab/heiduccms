

package com.heiduc.service.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jabsorb.JSONRPCBridge;

import com.heiduc.business.Business;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.entity.PluginEntity;
import com.heiduc.service.FrontService;
import com.heiduc.service.front.ChannelApiService;
import com.heiduc.service.front.CommentService;
import com.heiduc.service.front.FormService;
import com.heiduc.service.front.LoginService;
import com.heiduc.service.front.SearchService;
import com.heiduc.service.front.impl.ChannelApiServiceImpl;
import com.heiduc.service.front.impl.CommentServiceImpl;
import com.heiduc.service.front.impl.FormServiceImpl;
import com.heiduc.service.front.impl.LoginServiceImpl;
import com.heiduc.service.front.impl.SearchServiceImpl;
import com.heiduc.service.plugin.PluginServiceManager;

public class FrontServiceImpl implements FrontService, Serializable {

	private static final Log log = LogFactory.getLog(FrontServiceImpl.class);

	private LoginService loginService;
	private FormService formService;
	private CommentService commentService;
	private SearchService searchService;
	private ChannelApiService channelApiService;
	
	@Override
	public void register(JSONRPCBridge bridge) {
		bridge.registerObject("loginFrontService", getLoginService());
		bridge.registerObject("formFrontService", getFormService());
		bridge.registerObject("commentFrontService", getCommentService());
		bridge.registerObject("searchFrontService", getSearchService());
		bridge.registerObject("channelApiFrontService", getChannelApiService());
		registerPluginServices(bridge);
	}
	
	@Override
	public void unregister(JSONRPCBridge bridge) {
		bridge.unregisterObject("loginFrontService");
		bridge.unregisterObject("formFrontService");
		bridge.unregisterObject("commentFrontService");
		bridge.unregisterObject("searchFrontService");
		bridge.unregisterObject("channelApiFrontService");
		unregisterPluginServices(bridge);
	}

	@Override
	public FormService getFormService() {
		if (formService == null) {
			formService = new FormServiceImpl();
		}
		return formService;
	}

	@Override
	public void setFormService(FormService bean) {
		formService = bean;
	}

	@Override
	public LoginService getLoginService() {
		if (loginService == null) {
			loginService = new LoginServiceImpl();
		}
		return loginService;
	}

	@Override
	public void setLoginService(LoginService bean) {
		loginService = bean;
	}

	@Override
	public CommentService getCommentService() {
		if (commentService == null) {
			commentService = new CommentServiceImpl();
		}
		return commentService;
	}

	@Override
	public void setCommentService(CommentService bean) {
		commentService = bean;
	}

	private Business getBusiness() {
		return HeiducContext.getInstance().getBusiness();
	}

	private Dao getDao() {
		return getBusiness().getDao();
	}
	
	private void registerPluginServices(JSONRPCBridge bridge) {
		for (PluginEntity plugin : getDao().getPluginDao().selectEnabled()) {
			try {
				PluginServiceManager manager = getBusiness()
					.getPluginBusiness().getFrontServices(plugin);
				if (manager != null) {
					manager.register(bridge);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public void unregisterPluginServices(JSONRPCBridge bridge) {
		for (PluginEntity plugin : getDao().getPluginDao().selectEnabled()) {
			try {
				PluginServiceManager manager = getBusiness()
					.getPluginBusiness().getFrontServices(plugin);
				if (manager != null) {
					manager.unregister(bridge);
				}				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public SearchService getSearchService() {
		if (searchService == null) {
			searchService = new SearchServiceImpl();
		}
		return searchService;
	}

	@Override
	public void setSearchService(SearchService bean) {
		searchService = bean;
	}

	@Override
	public ChannelApiService getChannelApiService() {
		if (channelApiService == null) {
			channelApiService = new ChannelApiServiceImpl();
		}
		return channelApiService;
	}

	@Override
	public void setChannelApiService(ChannelApiService bean) {
		channelApiService = bean;
	}

}

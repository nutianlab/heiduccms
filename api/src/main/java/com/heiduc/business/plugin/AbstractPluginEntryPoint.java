

package com.heiduc.business.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;

import org.jboss.resteasy.spi.Registry;

import com.heiduc.business.Business;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.rest.RestManager;
import com.heiduc.service.BackService;
import com.heiduc.service.FrontService;
import com.heiduc.service.plugin.PluginServiceManager;

public abstract class AbstractPluginEntryPoint implements PluginEntryPoint {

	private Business business;
	private FrontService frontService;
	private BackService backService;
	private Map<String, HttpServlet> servlets;
	private List<PluginCronJob> jobs;
	private boolean headInclude;
	
	public AbstractPluginEntryPoint() {
		servlets = new HashMap<String, HttpServlet>();
		jobs = new ArrayList<PluginCronJob>();
	}
	
	@Override
	public void init() {
	}
	
	@Override
	public void refresh(){
	}
	
	@Override
	public void uninstall() {
	}

	@Override
	public PluginServiceManager getPluginBackService() {
		return null;
	}

	@Override
	public PluginServiceManager getPluginFrontService() {
		return null;
	}

	@Override
	public Object getPluginVelocityService() {
		return null;
	}

	@Override
	public BackService getBackService() {
		return backService;
	}

	@Override
	public Business getBusiness() {
		return business;
	}

	protected Dao getDao() {
		return getBusiness().getDao();
	}

	@Override
	public FrontService getFrontService() {
		return frontService;
	}

	@Override
	public void setBackService(BackService bean) {
		backService = bean;
	}

	@Override
	public void setBusiness(Business bean) {
		business = bean;
	}

	@Override
	public void setFrontService(FrontService bean) {
		frontService = bean;
	}

	@Override
	public Map<String, HttpServlet> getServlets() {
		return servlets;
	}

	@Override
	public List<PluginCronJob> getJobs() {
		return jobs;
	}
	
	@Override
	public String getHeadBeginInclude() {
		return "";
	}
	
	@Override
	public String getBundleName() {
		return null;
	}

	@Override
	public boolean isHeadInclude() {
		return headInclude;
	}
	
	@Override
	public void setHeadInclude(boolean value) {
		headInclude = value;
	}

	@Override
	public Map<String,String> getRewriteRules() {
		return Collections.EMPTY_MAP;
	}
	
	@Override
	public Registry getRegistry() {
		return (Registry)HeiducContext.getInstance().getRequest().getServletContext().getAttribute(Registry.class.getName());
	}
}

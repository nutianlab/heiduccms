

package com.heiduc.business.plugin;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;

import org.jboss.resteasy.spi.Registry;

import com.heiduc.business.Business;
import com.heiduc.service.BackService;
import com.heiduc.service.FrontService;
import com.heiduc.service.plugin.PluginServiceManager;

public interface PluginEntryPoint {

	/**
	 * Plugin initialization. Called after setting dao, business and services.
	 */
	public void init();
	
	/**
	 * Plugin uninstall callback. Called before uninstall process.
	 */
	public void uninstall();
	
	Business getBusiness();
	void setBusiness(Business bean);

	BackService getBackService();
	void setBackService(BackService bean);
	
	FrontService getFrontService();
	void setFrontService(FrontService bean);

	PluginServiceManager getPluginBackService();

	PluginServiceManager getPluginFrontService();
	
	Object getPluginVelocityService();
	
	/**
	 * Get plugin servlets map. 
	 * All plugin servlets mapped to url /plugin/PLUGIN_NAME/PATH
	 * Map key is PATH element.
	 * @return
	 */
	Map<String, HttpServlet> getServlets();
	
	List<PluginCronJob> getJobs();
	
	/**
	 * Get html head tag include fragment. All pages will include this fragment in 
	 * head tag.
	 * @return head tag fragment.
	 */
	String getHeadBeginInclude();
	
	boolean isHeadInclude();
	
	void setHeadInclude(boolean value);
	
	/**
	 * Get message bundle class path. For example: com.heiduc.resources.messages
	 * @return bundle name.
	 */
	String getBundleName();
	
	Map<String,String> getRewriteRules();
	
	/**
	 * restful
	 * @return
	 */

	Registry getRegistry();
}

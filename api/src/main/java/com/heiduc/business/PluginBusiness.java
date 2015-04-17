

package com.heiduc.business;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;

import com.heiduc.business.plugin.PluginEntryPoint;
import com.heiduc.business.vo.PluginPropertyVO;
import com.heiduc.common.PluginException;
import com.heiduc.entity.PluginEntity;
import com.heiduc.service.plugin.PluginServiceManager;

/**
 * @author Alexander Oleynik
 */
public interface PluginBusiness {

	/**
	 * Installs war file as Vosao plugin.
	 * @param filename
	 * @param data
	 * @throws IOException 
	 * @throws PluginException 
	 * @throws DocumentException 
	 */
	void install(String filename, byte[] data) 
			throws IOException, PluginException, DocumentException;
	
	/**
	 * Remove plugin from system.
	 * @param plugin - plugin for remove.
	 */
	void uninstall(PluginEntity plugin);
	
	/**
	 * Get plugin entry point.
	 * @param plugin - plugin.
	 * @return - Plugin entry point service.
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	PluginEntryPoint getEntryPoint(PluginEntity plugin);

	/**
	 * Get plugin Velocity service for page rendering.
	 * @param plugin - plugin.
	 * @return - Velocity service.
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	Object getVelocityPlugin(PluginEntity plugin) 
			throws ClassNotFoundException, InstantiationException, 
			IllegalAccessException;
	
	/**
	 * Reset all plugin's resources and clear cache.
	 * @param plugin - plugin for reset.
	 */
	void resetPlugin(PluginEntity plugin);
	
	/**
	 * Get plugin config XML structure. Config values are stored as XML in 
	 * plugin.getConfigData()
	 * @param plugin - plugin for structure.
	 * @return - list of config structure properties.
	 */
	List<PluginPropertyVO> getProperties(PluginEntity plugin);

	/**
	 * Get plugin config XML structure. Config values are stored as XML in 
	 * plugin.getConfigData()
	 * @param plugin - plugin for structure.
	 * @return - map of config structure properties. Property name as key.
	 */
	Map<String, PluginPropertyVO> getPropertiesMap(PluginEntity plugin);
	
	PluginServiceManager getBackServices(PluginEntity plugin)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException;

	PluginServiceManager getFrontServices(PluginEntity plugin)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException;
	
	HttpServlet getPluginServlet(HttpServletRequest request);
	
	/**
	 * Schedule and run if necessary plugins cron jobs. 
	 * @param date - run datetime.
	 */
	void cronSchedule(Date date);
	
	ClassLoader getClassLoader(PluginEntity plugin);

	Class loadClass(String name);
}

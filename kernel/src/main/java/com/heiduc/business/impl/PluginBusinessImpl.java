

package com.heiduc.business.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.heiduc.business.PluginBusiness;
import com.heiduc.business.impl.plugin.PluginClassLoaderFactory;
import com.heiduc.business.impl.plugin.PluginLoader;
import com.heiduc.business.plugin.PluginCronJob;
import com.heiduc.business.plugin.PluginEntryPoint;
import com.heiduc.business.plugin.PluginResourceCache;
import com.heiduc.business.vo.PluginPropertyVO;
import com.heiduc.common.HeiducContext;
import com.heiduc.common.PluginException;
import com.heiduc.entity.PluginEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.service.BackService;
import com.heiduc.service.FrontService;
import com.heiduc.service.plugin.PluginServiceManager;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class PluginBusinessImpl extends AbstractBusinessImpl 
	implements PluginBusiness {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PluginLoader pluginLoader;
	private PluginClassLoaderFactory pluginClassLoaderFactory;
	private static final Map<String, PluginEntryPoint> plugins = new HashMap<String, PluginEntryPoint>();
	private static final Map<String, PluginEntity> pluginTimestamps = new HashMap<String, PluginEntity>();
	
	private static byte[] lock = new byte[0];
	
	public PluginBusinessImpl() {
		
	}
	
	/**
	 * Plugin installation:
	 * - PluginEntity created
	 * - All resources are placed to /plugins/PLUGIN_NAME/
	 * - all classes are placed to PluginResourceEntity
	 */
	@Override
	public void install(String filename, byte[] data) throws IOException, 
			PluginException, DocumentException {
		getPluginLoader().install(filename, data);
		Messages.resetCache();
	}
	
	@Override
	public Object getVelocityPlugin(PluginEntity plugin) 
			throws ClassNotFoundException, InstantiationException, 
			IllegalAccessException {
		PluginEntryPoint entryPoint = getEntryPoint(plugin);
		return entryPoint == null ? null : entryPoint.getPluginVelocityService();
	}

	@Override
	public void resetPlugin(PluginEntity plugin) {
		plugins.remove(plugin.getName());
		pluginTimestamps.remove(plugin.getName());
		getPluginClassLoaderFactory().resetPlugin(plugin.getName());
		getCache().reset(plugin.getName());
	}

	public PluginClassLoaderFactory getPluginClassLoaderFactory() {
		if (pluginClassLoaderFactory == null) {
			pluginClassLoaderFactory = PluginClassLoaderFactory.getInstance();
		}
		return pluginClassLoaderFactory;
	}

	public void setPluginClassLoaderFactory(PluginClassLoaderFactory bean) {
		this.pluginClassLoaderFactory = bean;
	}

	@Override
	public void uninstall(PluginEntity plugin) {
		getBusiness().getRewriteUrlBusiness().removeRules(
				getEntryPoint(plugin).getRewriteRules());
		
		getEntryPoint(plugin).uninstall();
		getPluginLoader().uninstall(plugin);
		resetPlugin(plugin);
	}
	
	public PluginLoader getPluginLoader() {
		if (pluginLoader == null) {
			pluginLoader = new PluginLoader(getDao(), getBusiness());
		}
		return pluginLoader;
	}

	public PluginResourceCache getCache() {
		return pluginClassLoaderFactory.getCache();
	}

	@Override
	public List<PluginPropertyVO> getProperties(PluginEntity plugin) {
		List<PluginPropertyVO> result = new ArrayList<PluginPropertyVO>();
		try {
			Document doc = DocumentHelper.parseText(plugin.getConfigStructure());
			for (Element e : (List<Element>)doc.getRootElement().elements()) {
				if (e.getName().equals("param")) {
					PluginPropertyVO p = new PluginPropertyVO();
					if (e.attributeValue("name") == null) {
						LOGGER.error("There must be name attribute for param tag.");
						continue;
					}
					if (e.attributeValue("type") == null) {
						LOGGER.error("There must be type attribute for param tag.");
						continue;
					}
					p.setName(e.attributeValue("name"));
					p.setType(e.attributeValue("type"));
					p.setDefaultValue(e.attributeValue("value"));
					if (e.attributeValue("title") == null) {
						p.setTitle(p.getName());
					}
					else {
						p.setTitle(e.attributeValue("title"));
					}
					result.add(p);
				}
			}
			return result;
		}
		catch (DocumentException e) {
			LOGGER.error(e.getMessage());
			return Collections.emptyList();
		}
	}

	@Override
	public Map<String, PluginPropertyVO> getPropertiesMap(PluginEntity plugin) {
		Map<String, PluginPropertyVO> map = 
				new HashMap<String, PluginPropertyVO>();
		List<PluginPropertyVO> list = getProperties(plugin);
		for (PluginPropertyVO p : list) {
			map.put(p.getName(), p);
		}
		return map;
	}

	@Override
	public PluginServiceManager getBackServices(PluginEntity plugin)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		PluginEntryPoint entryPoint = getEntryPoint(plugin);
		return entryPoint== null ? null : entryPoint.getPluginBackService();
	}

	@Override
	public PluginServiceManager getFrontServices(PluginEntity plugin)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		PluginEntryPoint entryPoint = getEntryPoint(plugin);
		return entryPoint == null ? null : entryPoint.getPluginFrontService();
	}

	private boolean isNeedRefresh(PluginEntity plugin) {
		if (pluginTimestamps.containsKey(plugin.getName())) {
			return !pluginTimestamps.get(plugin.getName()).getModDate()
					.equals(plugin.getModDate());
		}
		return true;
	}
	
	@Override
	public PluginEntryPoint getEntryPoint(PluginEntity plugin) {
		synchronized (lock) {
			if (!plugins.containsKey(plugin.getName()) 
					|| isNeedRefresh(plugin)) {
					
					try {
						resetPlugin(plugin);
						ClassLoader pluginClassLoader = getPluginClassLoaderFactory()
							.getClassLoader(plugin.getName());
						Class entryPointClass = pluginClassLoader
							.loadClass(plugin.getEntryPointClass());
						PluginEntryPoint entryPoint = (PluginEntryPoint)entryPointClass
							.newInstance();
						entryPoint.setBusiness(getBusiness());
						entryPoint.setFrontService(getFrontService());
						entryPoint.setBackService(getBackService());
						
						entryPoint.init();
						
						plugins.put(plugin.getName(), entryPoint);
						pluginTimestamps.put(plugin.getName(), plugin);
						getBusiness().getRewriteUrlBusiness().addRules(
								entryPoint.getRewriteRules());
					}
					catch (ClassNotFoundException e) {
						LOGGER.error("Class not found " + e.getMessage());
					}
					catch (Exception e) {
						LOGGER.error(e.getMessage());
					}
				}
		}
		return plugins.get(plugin.getName());
	}

	public FrontService getFrontService() {
		return HeiducContext.getInstance().getFrontService();
	}

	public BackService getBackService() {
		return HeiducContext.getInstance().getBackService();
	}

	@Override
	public HttpServlet getPluginServlet(HttpServletRequest request) {
		String url = request.getServletPath();
		if (!url.startsWith("/_ah/plugin")) {
			return null;
		}
		String[] tokens = request.getRequestURI().toString().split("/");
		if (tokens.length < 4) {
			return null;
		}
		String pluginName = tokens[3];
		String servlet = tokens[4];
		PluginEntity plugin = getDao().getPluginDao().getByName(pluginName);
		if (plugin == null || plugin.isDisabled()) {
			return null;
		}
		try {
			PluginEntryPoint entryPoint = getEntryPoint(plugin);
			return entryPoint == null ? null : 
				entryPoint.getServlets().get(servlet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void cronSchedule(Date date) {
		for (PluginEntity plugin : getDao().getPluginDao().selectEnabled()) {
			PluginEntryPoint entry = getBusiness().getPluginBusiness()
					.getEntryPoint(plugin);
			if (entry == null) {
				return;
			}
			for (PluginCronJob job : entry.getJobs()) {
				try {
					if (job.isShowTime(date)) {
						job.run();
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public ClassLoader getClassLoader(PluginEntity plugin) {
		return getPluginClassLoaderFactory().getClassLoader(plugin.getName());
	}

	@Override
	public Class loadClass(String name) {
		List<PluginEntity> plugins = getDao().getPluginDao().selectEnabled();
		for (PluginEntity plugin : plugins) {
			ClassLoader classLoader = getClassLoader(plugin);
			try {
				return classLoader.loadClass(name);
			}
			catch (ClassNotFoundException e) {
			}
		}
		return null;
	}

}



package com.heiduc.business.impl.plugin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.plugin.PluginClassLoaderFactory;
import com.heiduc.business.plugin.PluginResourceCache;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.global.SystemService;

public class PluginClassLoaderFactoryImpl implements PluginClassLoaderFactory {

	private static final Log logger = LogFactory.getLog(PluginClassLoader.class);
	
	private Map<String, ClassLoader> classLoaders;
	private PluginResourceCache cache;
	
	public ClassLoader getClassLoader(String pluginName) {
		if (!getClassLoaders().containsKey(pluginName)) {
			logger.info("creating class loader " + pluginName);
			PluginClassLoader classLoader = new PluginClassLoader(
					getSystemService(), getDao(), getCache(), pluginName);
			getClassLoaders().put(pluginName, classLoader);			
		}
		return getClassLoaders().get(pluginName);
	}

	public void resetPlugin(String pluginName) {
		getClassLoaders().remove(pluginName);
	}
	
	private Map<String, ClassLoader> getClassLoaders() {
		if (classLoaders == null) {
			classLoaders = new HashMap<String, ClassLoader>();
		}
		return classLoaders;
	}
	
	public SystemService getSystemService() {
		return HeiducContext.getInstance().getBusiness().getSystemService();
	}

	public Dao getDao() {
		return HeiducContext.getInstance().getBusiness().getDao();
	}

	public PluginResourceCache getCache() {
		if (cache == null) {
			cache = new PluginResourceCacheImpl();
		}
		return cache;
	}

	public void setCache(PluginResourceCache cache) {
		this.cache = cache;
	}
	
}



package com.heiduc.business.impl.plugin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.plugin.PluginResourceCache;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.global.SystemService;

public class PluginClassLoaderFactory {

	private static final Log logger = LogFactory.getLog(PluginClassLoader.class);
	
	private static final Map<String, ClassLoader> classLoaders = new HashMap<String, ClassLoader>();
	private static final PluginResourceCache cache = new PluginResourceCacheImpl();
	private static PluginClassLoaderFactory instance;
	private PluginClassLoaderFactory (){
		
	}
	
	public static synchronized PluginClassLoaderFactory getInstance(){
		if(instance == null){
			logger.info("PluginClassLoaderFactory instance is null. ");
			instance = new PluginClassLoaderFactory();
			logger.info("init instance hashCode : "+instance.hashCode());
		}
		return instance;
	}
	
	public synchronized ClassLoader getClassLoader(String pluginName) {
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
		/*if(classLoaders == null){
			classLoaders = new HashMap<String, ClassLoader>();
		}*/
		return classLoaders;
	}
	
	public SystemService getSystemService() {
		return HeiducContext.getInstance().getBusiness().getSystemService();
	}

	public Dao getDao() {
		return HeiducContext.getInstance().getBusiness().getDao();
	}

	public PluginResourceCache getCache() {
		/*if (cache == null) {
			cache = new PluginResourceCacheImpl();
		}*/
		return cache;
	}

	/*public void setCache(PluginResourceCache cache) {
		this.cache = cache;
	}*/
	
}

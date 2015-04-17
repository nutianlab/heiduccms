

package com.heiduc.business.plugin;

public interface PluginClassLoaderFactory {

	ClassLoader getClassLoader(String pluginName);

	void resetPlugin(String pluginName);
	
	PluginResourceCache getCache();

	void setCache(PluginResourceCache cache);

}

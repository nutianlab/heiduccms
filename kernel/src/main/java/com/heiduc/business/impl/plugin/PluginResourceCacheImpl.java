

package com.heiduc.business.impl.plugin;

import java.util.ArrayList;
import java.util.List;

import com.heiduc.business.plugin.PluginResourceCache;
import com.heiduc.common.HeiducContext;
import com.heiduc.global.SystemService;

public class PluginResourceCacheImpl implements PluginResourceCache {

	private static final String PLUGIN_RESOURCE = "pluginResourceList"; 

	private SystemService getSystemService() {
		return HeiducContext.getInstance().getBusiness().getSystemService();
	}
	
	private String getPluginResourcesListKey(String pluginName) {
		return pluginName + PLUGIN_RESOURCE;
	}
	
	private List<String> getPluginResourcesList(String pluginName) {
		List<String> resourceList = new ArrayList<String>();
		
		String key = getPluginResourcesListKey(pluginName);
		ClassLoader classloader = getClassLoader(pluginName);
		if (!getSystemService().getCache(classloader).containsKey(key)) {
			getSystemService().getCache(classloader).put(key, resourceList);
		}else{
			resourceList = (List<String>)getSystemService().getCache(classloader).get(key);
		}
		return resourceList;
	}
	
	@Override
	public boolean contains(String pluginName, String key) {
		ClassLoader classloader = getClassLoader(pluginName);
		return getPluginResourcesList(pluginName).contains(key) 
			&& getSystemService().getCache(classloader).containsKey(key);
	}
	
	@Override
	public byte[] get(String pluginName, String key) {
		ClassLoader classloader = getClassLoader(pluginName);
		if (contains(pluginName, key)) {
			return (byte[])getSystemService().getCache(classloader).get(key);
		}
		return null;
	}
	
	@Override
	public void put(String pluginName, String key, byte[] data) {
		ClassLoader classloader = getClassLoader(pluginName);
		List<String> list = getPluginResourcesList(pluginName);
		list.add(key);
		getSystemService().getCache(classloader).put(getPluginResourcesListKey(pluginName), list);
		getSystemService().getCache(classloader).put(key, data);
	}
	
	@Override
	public void reset(String pluginName) {
		ClassLoader classloader = getClassLoader(pluginName);
		if(getSystemService().getCache(classloader) != null){
			getSystemService().getCache(classloader).clear();
		}
		/*String resourceKey = getPluginResourcesListKey(pluginName);
		
		List<String> resourceList = (List<String>) getSystemService().getCache().get(resourceKey);
		if(null != resourceList){
			Set<String> resourceSet = new HashSet<String>(resourceList);
			getSystemService().getCache().removeAll(resourceSet);//移除插件资源文件内容
		}
		getSystemService().getCache().remove(resourceKey);//移除资源文件名列表
*/	}
	
	private static ClassLoader getClassLoader(String pluginName){
		return PluginClassLoaderFactory.getInstance().getClassLoader(pluginName);
	}

}

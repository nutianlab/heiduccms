

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
		String key = getPluginResourcesListKey(pluginName);
		if (!getSystemService().getCache().containsKey(key)) {
			getSystemService().getCache().put(key, new ArrayList<String>());
		}
		List<String> list = (List<String>)getSystemService().getCache().get(key);
		return list;
	}
	
	@Override
	public boolean contains(String pluginName, String key) {
		return getPluginResourcesList(pluginName).contains(key) 
			&& getSystemService().getCache().containsKey(key);
	}
	
	@Override
	public byte[] get(String pluginName, String key) {
		if (contains(pluginName, key)) {
			return (byte[])getSystemService().getCache().get(key);
		}
		return null;
	}
	
	@Override
	public void put(String pluginName, String key, byte[] data) {
		List<String> list = getPluginResourcesList(pluginName);
		list.add(key);
		getSystemService().getCache().put(getPluginResourcesListKey(pluginName), 
				list);
		getSystemService().getCache().put(key, data);
	}
	
	@Override
	public void reset(String pluginName) {
		getSystemService().getCache().remove(getPluginResourcesListKey(pluginName));
	}

}

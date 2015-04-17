

package com.heiduc.business.impl.plugin;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.heiduc.business.plugin.PluginResourceCache;
import com.heiduc.dao.Dao;
import com.heiduc.entity.PluginResourceEntity;
import com.heiduc.global.SystemService;

public class PluginClassLoader extends ClassLoader {

	private static final Log logger = LogFactory.getLog(PluginClassLoader.class);
	
	private SystemService systemService;
	private Dao dao;
	private PluginResourceCache cache;
	private String pluginName;
	
	public PluginClassLoader() {
		super(PluginClassLoader.class.getClassLoader());
	}
	
	public PluginClassLoader(SystemService systemService, Dao dao,
			PluginResourceCache cache, String pluginName) {
		super(PluginClassLoader.class.getClassLoader());
		this.systemService = systemService;
		this.dao = dao;
		this.cache = cache;
		this.pluginName = pluginName;
	}

	@Override
	public Class findClass(String name) throws ClassNotFoundException {
		Class cls = findLoadedClass(name);
		if (cls != null) {
			return cls;
		}
		try {
			byte[] b = findPluginResource(name);
			if (b == null) {
				throw new ClassNotFoundException(name);
			}
			return defineClass(name, b, 0, b.length);
		}
		catch (SecurityException e) {
			return super.loadClass(name);
		}
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		byte[] b = findPluginResource(name);
		if (b == null) {
			throw new ResourceNotFoundException(name);
		}
		return new ByteArrayInputStream(b);
	}
	
	private byte[] findPluginResource(String name) {
		if (!getCache().contains(pluginName, name)) {
			byte[] data = loadPluginResource(name);
			if (data != null) {
				getCache().put(pluginName, name, data);
			}
			else {
				return null;
			}
		}
		return getCache().get(pluginName, name);
	}

	private byte[] loadPluginResource(String name) {
		PluginResourceEntity resource = getDao().getPluginResourceDao()
				.getByUrl(pluginName, name);
		if (resource != null) {
			return resource.getContent(); 
		}
		return null;
	}
	
	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	public PluginResourceCache getCache() {
		return cache;
	}

	public void setCache(PluginResourceCache bean) {
		cache = bean;
	}
	
}

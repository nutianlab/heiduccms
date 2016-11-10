

package com.heiduc.business.impl.plugin;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.heiduc.business.plugin.PluginResourceCache;
import com.heiduc.dao.Dao;
import com.heiduc.entity.PluginResourceEntity;
import com.heiduc.global.SystemService;

public class PluginClassLoader extends ClassLoader {

	private static final Logger logger = LoggerFactory.getLogger(PluginClassLoader.class);
	
	private SystemService systemService;
	private Dao dao;
	private PluginResourceCache cache;
	private String pluginName;
	
	public PluginClassLoader() {
		super(Thread.currentThread().getContextClassLoader());
	}
	
	public PluginClassLoader(SystemService systemService, Dao dao,
			PluginResourceCache cache, String pluginName) {
		super(Thread.currentThread().getContextClassLoader());
		this.systemService = systemService;
		this.dao = dao;
		this.cache = cache;
		this.pluginName = pluginName;
	}

	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> cls = findLoadedClass(name);
		if (cls != null) {
			return cls;
		}
		logger.info("PluginClassLoader findClass:"+name);
		try {
			byte[] b = findPluginResource(name);
			if (b == null) {
				throw new ClassNotFoundException(name);
			}
			return defineClass(name, b, 0, b.length);
		}
		catch (SecurityException e) {
			logger.error(e.getMessage());
			return loadClass(name);
		}
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		logger.info("PluginClassLoader getResourceAsStream:"+name);
		byte[] b = findPluginResource(name);
		if (b == null) {
			throw new ResourceNotFoundException(name);
		}
		return new ByteArrayInputStream(b);
	}
	
	@Override
	public String toString() {
		return this.pluginName;
	}

	private byte[] findPluginResource(String name) {
		byte[] data = getCache().get(pluginName, name);
		
		if (!getCache().contains(pluginName, name)) {
			data = loadPluginResource(name);
			if (data != null) {
				getCache().put(pluginName, name, data);
			}
			else {
				return null;
			}
		}
		return data;
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

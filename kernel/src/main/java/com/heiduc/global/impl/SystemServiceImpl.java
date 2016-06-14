

package com.heiduc.global.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.heiduc.api.datastore.DatastoreService;
import org.heiduc.api.datastore.DatastoreServiceFactory;
import org.heiduc.api.quota.QuotaService;
import org.heiduc.api.quota.QuotaServiceFactory;
import org.heiduc.api.taskqueue.Queue;
import org.heiduc.api.taskqueue.QueueFactory;

import com.heiduc.bliki.HeiducWikiModel;
import com.heiduc.business.impl.plugin.PluginClassLoader;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.PageEntity;
import com.heiduc.global.CacheService;
import com.heiduc.global.FileCache;
import com.heiduc.global.PageCache;
import com.heiduc.global.SystemService;

public class SystemServiceImpl implements SystemService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8332141730408353027L;

	private static final Log log = LogFactory.getLog(SystemServiceImpl.class);
	
	private Map<String,CacheService> caches = new HashMap<String, CacheService>();

	private CacheService cache;
	private VelocityEngine velocityEngine;
	private TransformerFactory xsltFactory;
	private Map<String, Transformer> transformers;
	private DatastoreService datastore;
	private FileCache fileCache;	
	private PageCache pageCache;
	private ScriptEngine scriptEngine;
	
	
	private static final String HEIDUC_CACHE_NAME = "HeiducCoreCache";
	
	
	
	public SystemServiceImpl() {
		transformers = new HashMap<String, Transformer>();
	}
	
	@Override
	public CacheService getCache() {
		return getCache(this.getClass());
	}
	
	@Override
	public CacheService getCache(Class<?> clazz) {
		String cacheName = HEIDUC_CACHE_NAME;
		if(clazz.getClassLoader() instanceof PluginClassLoader){
			cacheName = "Plugin_".concat(clazz.getClassLoader().toString()).concat("Cache");
		}
		
		
		if (!caches.containsKey(cacheName)) {
			log.info("CacheService ["+cacheName+"] init. ");
			caches.put(cacheName, new CacheServiceImpl(cacheName,clazz.getClassLoader()));
		}
		return caches.get(cacheName);
	}

	@Override
	public VelocityEngine getVelocityEngine() {
		if (velocityEngine == null) {
	        try {
	        	Properties properties = new Properties();
	        	properties.load(this.getClass().getResourceAsStream("/velocity.properties"));
	        	velocityEngine = new VelocityEngine(properties);
	        	velocityEngine.init();
			} catch (Exception e) {
	            log.error("Can't init velocity engine. " + e.getMessage());
			}
		}
		return velocityEngine;
	}

	@Override
	public String render(String template, VelocityContext context) {
		StringWriter wr = new StringWriter();
		String log = "vm";
		try {
			getVelocityEngine().evaluate(context, wr, log, template);
			return wr.toString();
		} catch (ParseErrorException e) {
			return e.toString();
		} catch (MethodInvocationException e) {
			return e.toString();
		} catch (ResourceNotFoundException e) {
			return e.toString();
		} /*catch (IOException e) {
			return e.toString();
		}*/
	}

	@Override
	public Transformer getTransformer(String template) {
		String key = String.valueOf(template.hashCode());
		if (!transformers.containsKey(key)) {
			try {
				Transformer transformer = getXsltFactory().newTransformer(
						new StreamSource(new ByteArrayInputStream(
								template.getBytes("UTF-8"))));
				transformers.put(key, transformer);				
			} catch (TransformerConfigurationException e) {
				log.error(e.getMessage());
				return null;
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage());
				return null;
			}
		}
		return transformers.get(key);
//		return null;
	}

	private TransformerFactory getXsltFactory() {
		if (xsltFactory == null) {
			xsltFactory = TransformerFactory.newInstance();
		}
		return xsltFactory;
	}
	
	@Override
	public Queue getDefaultQueue() {
		return QueueFactory.getDefaultQueue();
	}

	@Override
	public Queue getQueue(String name) {
		return QueueFactory.getQueue(name);
	}

	@Override
	public DatastoreService getDatastore() {
		if (datastore == null) {
			datastore = DatastoreServiceFactory.getDatastoreService();
		}
		return datastore;
	}

	@Override
	public FileCache getFileCache() {
		if (fileCache == null) {
			fileCache = new FileCacheImpl(getCache());			
		}
		return fileCache;
	}

	@Override
	public QuotaService getQuotaService() {
		return QuotaServiceFactory.getQuotaService();
	}

	@Override
	public long getRequestCPUTimeSeconds() {
		HeiducContext ctx = HeiducContext.getInstance();
		long result = (System.currentTimeMillis() - ctx.getStartTime())/1000;
		if (ctx.getRequestCount() == 1) {
			result += 15;
		}
		return result;
	}

	public PageCache getPageCache() {
		if (pageCache == null) {
			pageCache = new PageCacheImpl();
		}
		return pageCache;
	}

	public void setPageCache(PageCache pageCache) {
		this.pageCache = pageCache;
	}

	@Override
	public String renderWiki(String template, PageEntity page) {
		try {
			HeiducWikiModel wikiModel = new HeiducWikiModel(page);
			return wikiModel.render(template);
		} catch (ParseErrorException e) {
			return e.toString();
		} catch (MethodInvocationException e) {
			return e.toString();
		} catch (ResourceNotFoundException e) {
			return e.toString();
		}
	}

	@Override
	public String renderPHP(String template, PageEntity page) {
		OutputStream out = null;
		Writer writer = null;
		try {
			
			if (scriptEngine == null){
				
				Class<?> cls = null;
				try {
					StringBuffer _name = new StringBuffer("yrotcaFenignEtpircSsucreuQ.tpircs.sucreuq.ohcuac.moc");
					cls = Class.forName(_name.reverse().toString());
					Object o = cls.newInstance();
					Method m = cls.getMethod("getScriptEngine");
					scriptEngine = (ScriptEngine)m.invoke(o);
				} catch (ClassNotFoundException e) {
					log.info(e.getMessage());
				} catch (InstantiationException e) {
					log.info(e.getMessage());
				} catch (IllegalAccessException e) {
					log.info(e.getMessage());
				} catch (SecurityException e) {
					log.info(e.getMessage());
				} catch (NoSuchMethodException e) {
					log.info(e.getMessage());
				} catch (IllegalArgumentException e) {
					log.info(e.getMessage());
				} catch (InvocationTargetException e) {
					log.info(e.getMessage());
				}
			}
			if(scriptEngine != null){
				out = new ByteArrayOutputStream();
				writer = new OutputStreamWriter(out);
				scriptEngine.getContext().setWriter(writer);
				scriptEngine.eval(template);
				return out.toString();
			}
			return template;
		} catch (ScriptException e) {
			return e.toString();
		} finally {
			if(writer != null) {
				try {
					writer.flush();
				} catch (IOException e) {
				}
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
			if(out != null) {
				try {
					out.flush();
				} catch (IOException e) {
				}
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			writer = null;
			out = null;
		}
	}

}

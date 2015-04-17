

package com.heiduc.global.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
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

	private CacheService cache;
	private VelocityEngine velocityEngine;
	private TransformerFactory xsltFactory;
	private Map<String, Transformer> transformers;
	private DatastoreService datastore;
	private FileCache fileCache;	
	private PageCache pageCache;
	
	public SystemServiceImpl() {
		transformers = new HashMap<String, Transformer>();
	}
	
	@Override
	public CacheService getCache() {
		if (cache == null) {
			cache = new CacheServiceImpl();
		}
		return cache;
	}

	@Override
	public VelocityEngine getVelocityEngine() {
		if (velocityEngine == null) {
	        try {
	        	Properties properties = new Properties();
	        	properties.setProperty("velocimacro.permissions.allow.inline.to.replace.global", "true");
	        	properties.setProperty("velocimacro.permissions.allowInline", "true");
	        	properties.setProperty("velocimacro.context.localscope", "true");
	        	properties.setProperty("input.encoding", "UTF-8");
	        	properties.setProperty("output.encoding", "UTF-8");
	        	velocityEngine = new VelocityEngine(properties);
	        	velocityEngine.init();
	        	//TODO  从文件加载配置
//	        	System.out.println("Velocity.FILE_RESOURCE_LOADER_PATH="+Velocity.FILE_RESOURCE_LOADER_PATH);
	        	/*velocityEngine = new VelocityEngine("velocity.properties");
				velocityEngine.init();*/
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
		} catch (IOException e) {
			return e.toString();
		}
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

}



package com.heiduc.global;

import javax.xml.transform.Transformer;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.heiduc.api.datastore.DatastoreService;
import org.heiduc.api.quota.QuotaService;
import org.heiduc.api.taskqueue.Queue;

import com.heiduc.entity.PageEntity;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public interface SystemService {
	
	CacheService getCache();
	
	CacheService getCache(Class<?> clazz);
	
	FileCache getFileCache();
	
	PageCache getPageCache();
	
	Queue getDefaultQueue();

	Queue getQueue(String name);

	VelocityEngine getVelocityEngine();
	
	QuotaService getQuotaService();

	/**
	 * Render velocity template in specified context. 
	 * @param template - template to render.
	 * @param content - context to use.
	 * @return rendered html.
	 */
	String render(final String template, final VelocityContext context);
	
	Transformer getTransformer(String template);
	
	DatastoreService getDatastore();
	
	/**
	 * Time duration of current request in seconds. Used in tasks to control
	 * 30 sec limit.
	 * @return request seconds.
	 */
	long getRequestCPUTimeSeconds();
	
	/**
	 * Render wiki text. 
	 * @param template - template to render.
	 * @param page - page to use.
	 * @return rendered html.
	 */
	String renderWiki(String template, PageEntity page);
	
	String renderPHP(String template, PageEntity page);
	
}

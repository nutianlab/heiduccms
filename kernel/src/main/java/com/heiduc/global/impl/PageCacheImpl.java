

package com.heiduc.global.impl;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.entity.LanguageEntity;
import com.heiduc.global.CacheService;
import com.heiduc.global.PageCache;
import com.heiduc.global.PageCacheItem;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class PageCacheImpl implements PageCache {

	private static final Log logger = LogFactory.getLog(PageCacheImpl.class);

	private String getPageKey(String url, String language) {
		return "page:" + url + ":" + language;
	}
	
	private CacheService getCache() {
		return HeiducContext.getInstance().getBusiness().getSystemService()
				.getCache();
	}
	
	private Dao getDao() {
		return HeiducContext.getInstance().getBusiness().getDao();
	}
	
	@Override
	public PageCacheItem get(String url, String language) {
		try {
			PageCacheItem item = (PageCacheItem)getCache().get(getPageKey(url,
					language));
			if (item != null) {
				if (getCache().getResetDate() == null 
						|| item.getTimestamp().after(getCache()
								.getResetDate())) {
					return item;
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public void put(String url, String language, String content,
			String contentType) {
		getCache().put(getPageKey(url, language), 
				new PageCacheItem(content, contentType));
	}

	@Override
	public void remove(String url) {
		for (LanguageEntity lang : getDao().getLanguageDao().select()) {
			getCache().remove(getPageKey(url, lang.getCode()));		
		}
	}

	@Override
	public boolean contains(String url) {
		for (LanguageEntity lang : getDao().getLanguageDao().select()) {
			if (getCache().containsKey(getPageKey(url, lang.getCode()))) {
				return true;
			}
		}
		return false;
	}

}

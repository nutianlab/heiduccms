

package com.heiduc.search.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.Business;
import com.heiduc.business.mq.QueueSpeed;
import com.heiduc.business.mq.Topic;
import com.heiduc.business.mq.message.IndexMessage;
import com.heiduc.business.mq.message.PageMessage;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.entity.LanguageEntity;
import com.heiduc.entity.PageEntity;
import com.heiduc.search.Hit;
import com.heiduc.search.SearchEngine;
import com.heiduc.search.SearchIndex;
import com.heiduc.search.SearchResult;
import com.heiduc.search.SearchResultFilter;
import com.heiduc.utils.ListUtil;

/**
 *  
 * @author Alexander Oleynik
 *
 */

public class SearchEngineImpl implements SearchEngine {

	private static final Log logger = LogFactory.getLog(
			SearchEngineImpl.class);

	private Map<String, SearchIndex> indexes;

	private SearchIndex getSearchIndex(String language) {
		if (indexes == null) {
			indexes = new HashMap<String, SearchIndex>();
		}
		if (!indexes.containsKey(language)) {
			indexes.put(language, new SearchIndexImpl(language));
		}
		return indexes.get(language);
	}
	
	@Override
	public void reindex() {
		for (LanguageEntity language : getDao().getLanguageDao().select()) {
			getSearchIndex(language.getCode()).clear();
			getSearchIndex(language.getCode()).saveIndex();
		}
		getBusiness().getMessageQueue().publish(new IndexMessage());
	}
	
	@Override
	public SearchResult search(SearchResultFilter filter, String query, 
			int start, int count, String language, int textSize) {
		// Search in language index first for all results
		List<Hit> hits = getSearchIndex(language).search(filter, query, textSize);
		// Search in all other languages for all results
		for (LanguageEntity lang : getDao().getLanguageDao().select()) {
			if (!lang.getCode().equals(language)) {
				hits.addAll(getSearchIndex(lang.getCode()).search(filter, query, 
						textSize));
			}
		}
		// paginate result and return
		SearchResult result = new SearchResult();
		result.setCount(hits.size());
		int startIndex = start < hits.size() ? start : hits.size();
		int endIndex = startIndex + count;
		if (count == -1) {
			endIndex = hits.size();
		}
		if (endIndex > hits.size()) {
			endIndex = hits.size();
		}
		result.setHits(ListUtil.slice(hits, startIndex, count));
		return result;
	}

	@Override
	public void updateIndex(Long pageId) {
		for (LanguageEntity language : getDao().getLanguageDao().select()) {
			getSearchIndex(language.getCode()).updateIndex(pageId);
		}
	}

	@Override
	public void removeFromIndex(Long pageId) {
		for (LanguageEntity language : getDao().getLanguageDao().select()) {
			getSearchIndex(language.getCode()).removeFromIndex(pageId);
		}
	}

	@Override
	public void saveIndex() {
		for (LanguageEntity language : getDao().getLanguageDao().select()) {
			getSearchIndex(language.getCode()).saveIndex();
		}
	}
	
	private Business getBusiness() {
		return HeiducContext.getInstance().getBusiness();
	}
	
	private Dao getDao() {
		return getBusiness().getDao();
	}
}

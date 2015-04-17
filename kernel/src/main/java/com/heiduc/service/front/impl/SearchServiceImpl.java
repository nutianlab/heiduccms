

package com.heiduc.service.front.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import com.heiduc.search.Hit;
import com.heiduc.search.SearchResult;
import com.heiduc.service.front.SearchService;
import com.heiduc.service.impl.AbstractServiceImpl;

/**
 * @author Alexander Oleynik
 */
public class SearchServiceImpl extends AbstractServiceImpl 
		implements SearchService {

	@Override
	public SearchResult searchFilter(List<String> sections, String text, int start, 
			int count, int textSize) {
		String query = text.toLowerCase();
		String language = getBusiness().getLanguage();
		String defaultLanguage = getBusiness().getDefaultLanguage();
		SearchResult result = getBusiness().getSearchEngine().search(
				new SectionSearchFilter(sections),
				query, start, count, language, textSize);
		
		if (!language.equals(defaultLanguage)) {
			SearchResult enResult = getBusiness().getSearchEngine().search(
					new SectionSearchFilter(sections),
					query, start, count, defaultLanguage, textSize);
			
			for (Hit hit : enResult.getHits()) {
				hit.setLocalTitle(hit.getTitle());
				hit.setUrl(hit.getUrl() + "?language=" + defaultLanguage);
			}
			result.setCount(result.getCount() + enResult.getCount());
			result.getHits().addAll(enResult.getHits());
		}
		return result;
	}

	@Override
	public SearchResult search(String query) {
		return search(query, 0, -1, DEFAULT_TEXT_SIZE);
	}

	@Override
	public SearchResult search(String query, int start,
			int count, int textSize) {
		return searchFilter(Arrays.asList("/"), query, start, count, textSize);
	}

	@Override
	public SearchResult searchFilter(List<String> sections, String query) {
		return searchFilter(sections, query, 0, -1, DEFAULT_TEXT_SIZE);
	}
	
}

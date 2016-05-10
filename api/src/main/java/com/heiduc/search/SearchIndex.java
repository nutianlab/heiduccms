

package com.heiduc.search;

import java.io.IOException;
import java.util.List;

import com.heiduc.entity.PageEntity;

/**
 * Search index of all site pages for one language.
 * 
 * @author Alexander Oleynik
 *
 */
public interface SearchIndex {

	void updateIndex(Long pageId) throws IOException;

	void removeFromIndex(Long pageId);

	List<Hit> search(SearchResultFilter filter, String query, int textSize);
	
	List<PageEntity> search(SearchResultFilter filter, String query);
	
	void saveIndex() throws IOException;
	
	String getLanguage();

	void clear();
}

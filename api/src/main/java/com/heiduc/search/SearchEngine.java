

package com.heiduc.search;

import java.io.IOException;
import java.util.List;

import com.heiduc.entity.PageEntity;


public interface SearchEngine {

	void updateIndex(Long pageId) throws IOException;

	void removeFromIndex(Long pageId);

	SearchResult search(SearchResultFilter filter, String query, int start, 
			int count, String language, int textSize);
	
	List<PageEntity> search(SearchResultFilter filter, String query, 
			int start, int count, String language);
	
	/**
	 * Start index creation procedure. Create index generator task.
	 */
	void reindex();
	
	void saveIndex() throws IOException;
}

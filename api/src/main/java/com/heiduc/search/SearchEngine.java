

package com.heiduc.search;


public interface SearchEngine {

	void updateIndex(Long pageId);

	void removeFromIndex(Long pageId);

	SearchResult search(SearchResultFilter filter, String query, int start, 
			int count, String language, int textSize);
	
	/**
	 * Start index creation procedure. Create index generator task.
	 */
	void reindex();
	
	void saveIndex();
}

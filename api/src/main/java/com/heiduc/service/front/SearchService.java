

package com.heiduc.service.front;

import java.util.List;

import com.heiduc.entity.PageEntity;
import com.heiduc.search.SearchResult;
import com.heiduc.service.AbstractService;

/**
 * @author Alexander Oleynik
 */
public interface SearchService extends AbstractService {
	
	static final int DEFAULT_TEXT_SIZE = 256;
	
	SearchResult search(String query, int start, int count, int textSize);

	SearchResult search(String query);

	SearchResult searchFilter(List<String> sections, String query, int start, 
			int count, int textSize);

	SearchResult searchFilter(List<String> sections, String query);
	
	List<PageEntity> searchFilter(List<String> sections, String query, int start, 
			int count); 

}



package com.heiduc.search;

import com.heiduc.entity.PageEntity;

/**
 * @author Alexander Oleynik
 */
public interface SearchResultFilter {

	boolean check(PageEntity page);
	
}

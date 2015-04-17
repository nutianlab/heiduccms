

package com.heiduc.service.front.impl;

import java.util.Collections;
import java.util.List;

import com.heiduc.entity.PageEntity;
import com.heiduc.search.SearchResultFilter;

/**
 * @author Alexander Oleynik
 */
public class SectionSearchFilter implements SearchResultFilter {

	private List<String> sections;
	
	public SectionSearchFilter(List<String> sections) {
		this.sections = sections != null ? sections : Collections.EMPTY_LIST;
	}

	@Override
	public boolean check(PageEntity page) {
		if (page == null) {
			return false;
		}
		for (String url : sections) {
			if (page.getFriendlyURL().startsWith(url)) {
				return true;
			}
		}
		return false;
	}
	
}

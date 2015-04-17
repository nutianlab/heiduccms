

package com.heiduc.business.impl.pagefilter;

import com.heiduc.entity.PageEntity;

public interface PageFilter {

	String apply(final String content, final PageEntity page);
	
}

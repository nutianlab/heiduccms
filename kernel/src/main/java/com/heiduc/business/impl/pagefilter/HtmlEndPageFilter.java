

package com.heiduc.business.impl.pagefilter;

import com.heiduc.business.Business;
import com.heiduc.entity.PageEntity;

public class HtmlEndPageFilter extends AbstractPageFilter 
		implements PageFilter {

	public HtmlEndPageFilter(Business business) {
		super(business);
	}
	
	@Override
	public String apply(String content, PageEntity page) {
		return applyTag(content, page, "</html");
	}

}

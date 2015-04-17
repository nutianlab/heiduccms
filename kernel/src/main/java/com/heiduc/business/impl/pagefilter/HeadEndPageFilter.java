

package com.heiduc.business.impl.pagefilter;


import com.heiduc.business.Business;
import com.heiduc.business.impl.pagefilter.fragments.GoogleAnalyticsFragment;
import com.heiduc.business.impl.pagefilter.fragments.PageHeadHtmlFragment;
import com.heiduc.entity.PageEntity;

public class HeadEndPageFilter extends AbstractPageFilter 
		implements PageFilter {

	public HeadEndPageFilter(Business business) {
		super(business);
		getFragments().add(new PageHeadHtmlFragment());
		getFragments().add(new GoogleAnalyticsFragment());
	}
	
	@Override
	public String apply(String content, PageEntity page) {
		return applyTag(content, page, "</head");
	}

}

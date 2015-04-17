

package com.heiduc.business.impl.pagefilter;


import com.heiduc.business.Business;
import com.heiduc.business.impl.pagefilter.fragments.JavaScriptFragment;
import com.heiduc.business.impl.pagefilter.fragments.PluginHeadFragment;
import com.heiduc.entity.PageEntity;

public class HeadBeginPageFilter extends AbstractPageFilter 
		implements PageFilter {

	public HeadBeginPageFilter(Business business) {
		super(business);
		getFragments().add(new JavaScriptFragment());
		getFragments().add(new PluginHeadFragment());
	}
	
	@Override
	public String apply(String content, PageEntity page) {
		return applyTag(content, page, "<head");
	}

}



package com.heiduc.business.impl.pagefilter;


import com.heiduc.business.Business;
import com.heiduc.business.impl.pagefilter.fragments.EditorPanelFragment;
import com.heiduc.entity.PageEntity;

public class BodyBeginPageFilter extends AbstractPageFilter 
		implements PageFilter {

	public BodyBeginPageFilter(Business business) {
		super(business);
		getFragments().add(new EditorPanelFragment());
	}
	
	@Override
	public String apply(String content, PageEntity pageEntity) {
		return applyTag(content, pageEntity, "<body");
	}

}

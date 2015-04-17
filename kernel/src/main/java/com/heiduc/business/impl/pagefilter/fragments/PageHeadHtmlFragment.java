

package com.heiduc.business.impl.pagefilter.fragments;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.Business;
import com.heiduc.business.impl.pagefilter.ContentFragment;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.PageEntity;

public class PageHeadHtmlFragment implements ContentFragment {

	@Override
	public String get(Business business, PageEntity page) {
		StringBuilder b = new StringBuilder("");
		if (!StringUtils.isEmpty(page.getHeadHtml())) {
			b.append(page.getHeadHtml());
		}
		for (String h : HeiducContext.getInstance().getPageRenderingContext()
					.getHeadContents()) {
			b.append(h).append("\n");
		}
		return b.toString();
	}

}

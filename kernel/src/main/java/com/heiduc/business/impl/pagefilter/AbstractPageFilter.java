

package com.heiduc.business.impl.pagefilter;

import java.util.ArrayList;
import java.util.List;

import com.heiduc.business.Business;
import com.heiduc.entity.PageEntity;

public abstract class AbstractPageFilter implements PageFilter {

	private Business business;
	private List<ContentFragment> fragments;
	
	public AbstractPageFilter(Business business) {
		super();
		this.business = business;
		this.fragments = new ArrayList<ContentFragment>();
	}

	public Business getBusiness() {
		return business;
	}
	
	public void setBusiness(Business bean) {
		this.business = bean;
	}

	public List<ContentFragment> getFragments() {
		return fragments;
	}

	public void setFragments(List<ContentFragment> fragments) {
		this.fragments = fragments;
	}
	
	public String getFragmentsContent(PageEntity page) { 
		StringBuffer code = new StringBuffer();
		for (ContentFragment fragment : getFragments()) {
			code.append(fragment.get(getBusiness(), page)).append("\n");
		}
		return code.toString();
	}
	
	public int findTagInsertPosition(String tagName, StringBuffer page) {
		String tag = tagName.toLowerCase();
		String tagUpper = tagName.toUpperCase();
		if (page.indexOf(tagUpper) != -1) {
			tag = tagUpper;
		}
		int bodyStart = page.indexOf(tag);
		if (tagName.startsWith("</")) {
			return bodyStart;
		}
		else {
			int result = page.indexOf(">", bodyStart) + 1;
			if (result >= page.length()) {
				page.append(" ");
			}
			return result;
		}
	}

	public String applyTag(String content, PageEntity page, String tag) {
		StringBuffer buffer = new StringBuffer(content);
		int index = findTagInsertPosition(tag, buffer);
		if (index > 0) {
			buffer.insert(index, getFragmentsContent(page));
		}
		return buffer.toString();
	}
	
}

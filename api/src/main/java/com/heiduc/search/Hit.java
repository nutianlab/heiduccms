

package com.heiduc.search;

import com.heiduc.common.HeiducContext;
import com.heiduc.entity.PageEntity;

public class Hit {

	private String content;
	private String title;
	private String localTitle;
	private String url;
	
	public Hit(PageEntity page, String aContent, String language) {
		super();
		title = page.getTitle();
		localTitle = page.getLocalTitle(language);
		url = page.getFriendlyURL() + "?language=" + language;
		content = aContent;
	}

	public String getContent() {
		return content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLocalTitle() {
		return localTitle;
	}

	public void setLocalTitle(String localTitle) {
		this.localTitle = localTitle;
	}
	
}

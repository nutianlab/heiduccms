

package com.heiduc.dao.tool;

import java.text.ParseException;

import com.heiduc.dao.Dao;
import com.heiduc.entity.PageEntity;
import com.heiduc.enums.PageState;
import com.heiduc.utils.DateUtil;

public class PageTool {

	private Dao dao;
	
	public PageTool(Dao aDao) {
		dao = aDao;
	}
	
	public PageEntity addPage(final String title, 
			final String url) {
		return addPage(title, url, PageState.APPROVED);
	}
	
	public PageEntity addPage(final String name) {
		return addPage(name, "/" + name);
	}

	public PageEntity addPage(final String title, final String url, 
			PageState state) {
		PageEntity page = new PageEntity(title, url);
		page.setState(state);
		try {
			page.setPublishDate(DateUtil.toDate("01.01.2010"));
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		dao.getPageDao().save(page);
		return page;
	}
	
}

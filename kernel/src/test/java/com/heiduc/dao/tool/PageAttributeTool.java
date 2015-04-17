

package com.heiduc.dao.tool;

import com.heiduc.dao.Dao;
import com.heiduc.entity.PageAttributeEntity;

public class PageAttributeTool {

	private Dao dao;
	
	public PageAttributeTool(Dao aDao) {
		dao = aDao;
	}
	
	public PageAttributeEntity addPageAttribute(String url, String name, 
			boolean inherited, String defaultValue) {
		return dao.getPageAttributeDao().save(new PageAttributeEntity(
				url, name, name, defaultValue, inherited));
	}
	
}

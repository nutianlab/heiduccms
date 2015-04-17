

package com.heiduc.dao;

import com.heiduc.entity.PageTagEntity;

public class PageTagDaoTest extends AbstractDaoTest {

	private PageTagEntity addPageTag(String url) {
		return getDao().getPageTagDao().save(new PageTagEntity(url));
	}
	
	public void testGetByURL() {
		addPageTag("/test1");
		addPageTag("/test2");
		addPageTag("/test3");
		PageTagEntity tag = getDao().getPageTagDao().getByURL("/test1");
		assertNotNull(tag);
		assertEquals("/test1", tag.getPageURL());
	}	
	
}

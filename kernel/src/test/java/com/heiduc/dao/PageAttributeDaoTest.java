

package com.heiduc.dao;

import java.util.List;

import com.heiduc.dao.tool.PageAttributeTool;
import com.heiduc.entity.PageAttributeEntity;

public class PageAttributeDaoTest extends AbstractDaoTest {

	private PageAttributeTool tool;
	
	@Override
    public void setUp() throws Exception {
        super.setUp();
        tool = new PageAttributeTool(getDao());
	}    
	
	public void testSave() {
		tool.addPageAttribute("/", "name", true, "");
		List<PageAttributeEntity> list = getDao().getPageAttributeDao().select();
		assertEquals(1, list.size());
		PageAttributeEntity attr1 = list.get(0);
		assertEquals("name", attr1.getName());
	}	

	public void testGetByPage() {
		tool.addPageAttribute("/", "name1", true, "");
		tool.addPageAttribute("/", "name2", true, "");
		tool.addPageAttribute("/", "dog1", false, "");
		tool.addPageAttribute("/", "dog2", false, "");
		tool.addPageAttribute("/", "dog3", false, "");
		List<PageAttributeEntity> list = getDao().getPageAttributeDao()
				.getByPage("/");
		assertEquals(5, list.size());
		list = getDao().getPageAttributeDao().getByPageInherited("/");
		assertEquals(2, list.size());
	}
	
}

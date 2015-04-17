

package com.heiduc.dao;

import com.heiduc.entity.PluginEntity;

public class PluginDaoTest extends AbstractDaoTest {

	private PluginEntity addPlugin(String name, String title) {
		return getDao().getPluginDao().save(new PluginEntity(name, title, "", 
				""));
	}
	
	public void testGetByName() {
		addPlugin("sitemap", "sitemap");
		addPlugin("black", "black");
		addPlugin("super", "ordinal");
		PluginEntity s = getDao().getPluginDao().getByName("black");
		assertNotNull(s);
		assertEquals("black", s.getName());
		s = getDao().getPluginDao().getByName(null);
		assertNull(s);
		s = getDao().getPluginDao().getByName("megahit");
		assertNull(s);
	}	
	
}

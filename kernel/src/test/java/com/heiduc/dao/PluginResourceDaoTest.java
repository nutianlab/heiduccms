

package com.heiduc.dao;

import com.heiduc.entity.PluginResourceEntity;

public class PluginResourceDaoTest extends AbstractDaoTest {

	private PluginResourceEntity addPluginResource(String plugin, String url) {
		byte[] c = new byte[1];
		return getDao().getPluginResourceDao().save(
				new PluginResourceEntity(plugin, url, c));
	}
	
	public void testGetByUrl() {
		addPluginResource("sitemap", "file1");
		addPluginResource("black", "file2");
		addPluginResource("super", "file1");
		PluginResourceEntity s = getDao().getPluginResourceDao().getByUrl(
				"sitemap", "file1");
		assertNotNull(s);
		assertEquals("file1", s.getUrl());
		s = getDao().getPluginResourceDao().getByUrl(null, null);
		assertNull(s);
		s = getDao().getPluginResourceDao().getByUrl("sitemap", null);
		assertNull(s);
		s = getDao().getPluginResourceDao().getByUrl("megahit", "test");
		assertNull(s);
	}	
	
}

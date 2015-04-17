

package com.heiduc.dao;

import com.heiduc.entity.ConfigEntity;

public class ConfigDaoTest extends AbstractDaoTest {

	public void testGetConfig() {
		ConfigEntity config = getDao().getConfigDao().getConfig();
		assertNull(config.getId());
		config.setEditExt("gif");
		config.setAttribute("host", "test");
		getDao().getConfigDao().save(config);
		config = getDao().getConfigDao().getConfig();
		assertEquals("gif", config.getEditExt());		
		assertEquals("test", config.getAttribute("host"));		
	}
	
}

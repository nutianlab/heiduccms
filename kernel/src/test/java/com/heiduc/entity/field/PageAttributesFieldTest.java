

package com.heiduc.entity.field;

import com.heiduc.common.HeiducContext;
import com.heiduc.dao.AbstractDaoTest;
import com.heiduc.entity.ConfigEntity;

public class PageAttributesFieldTest extends AbstractDaoTest {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		HeiducContext.getInstance().setConfig(new ConfigEntity());
		HeiducContext.getInstance().getConfig().setDefaultLanguage("en");
	}
	
	public void testParse() {
		PageAttributesField field = new PageAttributesField(
				"{test:{ru:'russian', en:'english'}}");
		assertEquals(1, field.size());	
		assertEquals("english", field.get("test"));
	}

	public void testPut() {
		PageAttributesField field = new PageAttributesField(null);
		field.set("test", "en", "value");	
		assertEquals(1, field.size());	
		assertEquals("value", field.get("test"));
		assertNotNull(field.asJSON());
	}
}

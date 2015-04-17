

package com.heiduc.dao;

import com.heiduc.dao.tool.FormTool;
import com.heiduc.entity.FormConfigEntity;
import com.heiduc.entity.FormEntity;

public class FormDaoTest extends AbstractDaoTest {

	private FormTool formTool;

	@Override
    public void setUp() throws Exception {
        super.setUp();
        formTool = new FormTool(getDao());
	}    
	
	public void testGetByName() {
		formTool.addForm("john");
		formTool.addForm("test");
		formTool.addForm("jack");
		FormEntity f = getDao().getFormDao().getByName(null);
		assertNull(f);
		f = getDao().getFormDao().getByName("test");
		assertEquals("test", f.getName());
	}

	public void testGetConfig() {
		FormConfigEntity c = getDao().getFormConfigDao().getConfig();
		assertNotNull(c);
		c.setFormTemplate("template");
		getDao().getFormConfigDao().save(c);
		c = getDao().getFormConfigDao().getConfig();
		assertEquals("template", c.getFormTemplate());
		c.setFormTemplate("template2");
		getDao().getFormConfigDao().save(c);
		c = getDao().getFormConfigDao().getConfig();
		assertEquals("template2", c.getFormTemplate());
	}
	
}

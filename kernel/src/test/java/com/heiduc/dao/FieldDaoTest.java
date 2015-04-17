

package com.heiduc.dao;

import java.util.List;

import com.heiduc.dao.tool.FieldTool;
import com.heiduc.dao.tool.FormTool;
import com.heiduc.entity.FieldEntity;
import com.heiduc.entity.FormEntity;
import com.heiduc.enums.FieldType;

public class FieldDaoTest extends AbstractDaoTest {

	private FormTool formTool;
	private FieldTool fieldTool;

	@Override
    public void setUp() throws Exception {
        super.setUp();
        formTool = new FormTool(getDao());
        fieldTool = new FieldTool(getDao());
	}    
	
	public void testSave() {
		FormEntity form = formTool.addForm("form");
		fieldTool.addField("field1", FieldType.TEXT, "text", form);
		List<FieldEntity> fields = getDao().getFieldDao().getByForm(form);
		assertEquals(1, fields.size());
		FieldEntity field1 = fields.get(0);
		assertEquals("field1", field1.getName());
	}	
	
	public void testGetById() {
		FormEntity form = formTool.addForm("form");
		FieldEntity field = fieldTool.addField("field1", FieldType.TEXT, "text", 
				form);
		assertNotNull(field.getId());
		FieldEntity field2 = getDao().getFieldDao().getById(field.getId());
		assertEquals(field.getTitle(), field2.getTitle());
	}	

	public void testUpdate() {
		FormEntity form = formTool.addForm("form");
		FieldEntity field = fieldTool.addField("field1", FieldType.TEXT, "text", 
				form);
		assertNotNull(field.getId());
		FieldEntity field2 = getDao().getFieldDao().getById(field.getId());
		field2.setTitle("update");
		getDao().getFieldDao().save(field2);
		FieldEntity field3 = getDao().getFieldDao().getById(field.getId());
		assertEquals("update", field3.getTitle());
	}
	
	public void testGetByForm() {
		FormEntity form = formTool.addForm("form");
		fieldTool.addField("field1", FieldType.TEXT, "text1", form);
		fieldTool.addField("field2", FieldType.TEXT, "text2", form);
		fieldTool.addField("field3", FieldType.TEXT, "text3", form);
		FormEntity form2 = formTool.addForm("form");
		fieldTool.addField("field21", FieldType.TEXT, "text21", form2);
		fieldTool.addField("field22", FieldType.TEXT, "text22", form2);
		List<FieldEntity> fields = getDao().getFieldDao().getByForm(form);
		assertEquals(3, fields.size());
		fields = getDao().getFieldDao().getByForm(form2);
		assertEquals(2, fields.size());
	}
	
	public void testGetByName() {
		FormEntity form = formTool.addForm("form");
		fieldTool.addField("field1", FieldType.TEXT, "text1", form);
		fieldTool.addField("field2", FieldType.TEXT, "text2", form);
		fieldTool.addField("field3", FieldType.TEXT, "text3", form);
		FormEntity form2 = formTool.addForm("form");
		fieldTool.addField("field21", FieldType.TEXT, "text21", form2);
		fieldTool.addField("field22", FieldType.TEXT, "text22", form2);
		FieldEntity field = getDao().getFieldDao().getByName(form, "field3");
		assertNotNull(field);
		assertEquals("field3", field.getName());
	}
	
}



package com.heiduc.dao.tool;

import com.heiduc.dao.Dao;
import com.heiduc.entity.FieldEntity;
import com.heiduc.entity.FormEntity;
import com.heiduc.enums.FieldType;

public class FieldTool {

	private Dao dao;
	
	public FieldTool(Dao aDao) {
		dao = aDao;
	}

	public FieldEntity addField(final String name, final FieldType type,
			final String defaultValue, final FormEntity form) {
		FieldEntity field = new FieldEntity(form.getId(), name, name, type, 
				false, defaultValue);
		dao.getFieldDao().save(field);
		return field;
	}
	
	
}

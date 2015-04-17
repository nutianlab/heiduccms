

package com.heiduc.dao.tool;

import com.heiduc.dao.Dao;
import com.heiduc.entity.FormEntity;

public class FormTool {

	private Dao dao;
	
	public FormTool(Dao aDao) {
		dao = aDao;
	}

	public FormEntity addForm(final String name) {
		FormEntity form = new FormEntity(name, name, name, name);
		dao.getFormDao().save(form);
		return form;
	}
	
	
}

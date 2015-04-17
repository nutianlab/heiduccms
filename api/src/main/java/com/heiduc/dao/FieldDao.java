

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.FieldEntity;
import com.heiduc.entity.FormEntity;

public interface FieldDao extends BaseDao<FieldEntity> {

	List<FieldEntity> getByForm(final FormEntity form);

	FieldEntity getByName(final FormEntity form, final String name);

}

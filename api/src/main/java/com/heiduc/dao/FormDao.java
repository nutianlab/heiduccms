

package com.heiduc.dao;

import com.heiduc.entity.FormEntity;

public interface FormDao extends BaseDao<FormEntity> {

	FormEntity getByName(final String name);

}

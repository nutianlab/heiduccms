

package com.heiduc.dao;

import com.heiduc.entity.LanguageEntity;

public interface LanguageDao extends BaseDao<LanguageEntity> {

	LanguageEntity getByCode(final String code);

}



package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.ContentEntity;

public interface ContentDao extends BaseDao<ContentEntity> {

	List<ContentEntity> select(final String parentClass, 
			final Long parentKey);

	ContentEntity getByLanguage(final String parentClass, 
			final Long parentKey, final String language);
	
	void removeById(final String className, final Long id);
}

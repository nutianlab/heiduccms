

package com.heiduc.dao;

import com.heiduc.entity.TemplateEntity;

public interface TemplateDao extends BaseDao<TemplateEntity> {

	TemplateEntity getByUrl(final String url);

}
